package ua.tqs.pollution_tqs.ControllerTest;

import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ua.tqs.pollution_tqs.Controller.LocationController;
import ua.tqs.pollution_tqs.Model.Location;
import ua.tqs.pollution_tqs.Service.LocationService;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(LocationController.class)
class LocationControllerUTest {

    private final String CITY = "Beja";
    private final String COUNTRY = "PT";
    private double LAT = 40;
    private double LON = -8;
    private final String W_CITY = "aaaaaaaaa";
    @Autowired
    MockMvc mvc;

    @MockBean
    LocationService locationService;

    @Test
    void getLocationByCity() throws Exception {
        when(locationService.getLocationServC(CITY)).thenReturn(new Location(LAT, LON, CITY, COUNTRY));
        mvc.perform(get("/api/location/city?city="+CITY).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.lat", is(LAT)))
                .andExpect(jsonPath("$.lng", is(LON)))
                .andExpect(jsonPath("$.name", is(CITY)))
                .andExpect(jsonPath("$.country", is(COUNTRY)));
    }

    @Test
    void getLocationByCityAndCountryCode() throws Exception {
        when(locationService.getLocationServCC(CITY, COUNTRY)).thenReturn(new Location(LAT, LON, CITY, COUNTRY));
        mvc.perform(get("/api/location/city_country?city="+CITY+"&country="+COUNTRY).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.lat", is(LAT)))
                .andExpect(jsonPath("$.lng", is(LON)))
                .andExpect(jsonPath("$.name", is(CITY)))
                .andExpect(jsonPath("$.country", is(COUNTRY)));
    }

    @Test
    void getLocationByInvalidCityAndCountryCode() throws Exception {
        when(locationService.getLocationServCC(W_CITY, COUNTRY)).thenReturn(null);
        mvc.perform(get("/api/location/city_country?city="+W_CITY+"&country="+COUNTRY).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.lat", CoreMatchers.is((double)0)))
                .andExpect(jsonPath("$.lng", CoreMatchers.is((double)0)));
        ;
    }

    @Test
    void getLocationByInvalidCity() throws Exception {
        when(locationService.getLocationServC(W_CITY)).thenReturn(null);
        mvc.perform(get("/api/location/city?city="+W_CITY).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.lat", CoreMatchers.is((double)0)))
                .andExpect(jsonPath("$.lng", CoreMatchers.is((double)0)));
    }
}
