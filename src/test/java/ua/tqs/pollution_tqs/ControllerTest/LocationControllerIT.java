package ua.tqs.pollution_tqs.ControllerTest;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class LocationControllerIT {

    private final String BEJA = "beja";
    private final String BEJA_NO_COUNTRY = "Béja";
    private final String COUNTRY = "pt";
    private final String INVALID_LOC = "aaaaaaaaa";
    private final String TUNISIA = "TN";
    @Autowired
    private MockMvc mvc;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void whenGetLocationByCityAndCountry_thenGetLocation() throws Exception {
        mvc.perform(get("/api/location/city_country?city="+BEJA+"&country="+COUNTRY)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name",is("Beja")))
                .andExpect(jsonPath("$.country",is(COUNTRY.toUpperCase(Locale.ROOT))));
    }

    @Test
    void whenGetLocationByCity_thenGetLocation() throws Exception {
        mvc.perform(get("/api/location/city?city="+BEJA)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name",is(BEJA_NO_COUNTRY)))
                .andExpect(jsonPath("$.country",is(TUNISIA)));
    }

    @Test
    void whenGetLocationBadCity_thenGetNull() throws Exception {
        mvc.perform(get("/api/location/city_country?city="+INVALID_LOC+"&country="+COUNTRY)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.lat",is((double)0)))
                .andExpect(jsonPath("$.lng",is((double)0)));

    }

    @Test
    void whenGetLocationBadCityAndCountry_thenGetNull() throws Exception {
        mvc.perform(get("/api/location/city_country?city="+INVALID_LOC+"&country="+INVALID_LOC)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.lat",is((double)0)))
                .andExpect(jsonPath("$.lng",is((double)0)));

    }

    @Test
    void whenGetPollutionBadCountry_thenIgnoresCountry_returnsLocation() throws Exception {
        mvc.perform(get("/api/location/city_country?city="+BEJA+"&country="+INVALID_LOC)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name",is(BEJA_NO_COUNTRY)))
                .andExpect(jsonPath("$.country",is(TUNISIA)));
    }
}
