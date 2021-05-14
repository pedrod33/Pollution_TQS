package ua.tqs.pollution_tqs.ControllerTest;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.logging.Level;
import java.util.logging.Logger;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class PollutionControllerIT {

    private final double LAT = 24.35;
    private final double LON = -8;
    private final double INVALID_COORD = -200;

    Logger logger = Logger.getLogger(PollutionControllerIT.class.getName());

    @Autowired
    private MockMvc mvc;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void whenGetPollution_thenGetPollution() throws Exception {
        getPollution();
    }

    @Test
    void whenGetPollutionBadCoords_thenGetNull() throws Exception {
        mvc.perform(get("/api/pollution/current?lat="+INVALID_COORD+"&lng="+LON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }


    //testing cache should print some logs with format similar to this
    //INFO 12725 --- [           main] ua.tqs.pollution_tqs.Cache.Cache         : 24.35,-8.0
    //INFO 12725 --- [           main] ua.tqs.pollution_tqs.Cache.Cache         : 24.35,-8.0
    //INFO 12873 --- [           main] u.t.p.C.PollutionControllerIT            : second request checks cache
    //INFO 12725 --- [           main] ua.tqs.pollution_tqs.Cache.Cache         : 24.35,-8.0
    //INFO 12725 --- [           main] u.t.p.Service.PollutionService           : PollutionData{lat=24.35, lon=-8.0}
    @Test
    void whenGetPollutionTwice_thenGetPollutionTwice() throws Exception {
        getPollution();
        logger.log(Level.INFO,"second request checks cache");
        getPollution();
    }

    private void getPollution() throws Exception {
        mvc.perform(get("/api/pollution/current?lat="+LAT+"&lng="+LON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.lat",is(LAT)))
                .andExpect(jsonPath("$.lon",is(LON)));
    }


}
