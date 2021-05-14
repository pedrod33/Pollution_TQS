package ua.tqs.pollution_tqs.ControllerTest;

import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ua.tqs.pollution_tqs.Controller.PollutionController;
import ua.tqs.pollution_tqs.Model.Particles;
import ua.tqs.pollution_tqs.Model.PollutionData;
import ua.tqs.pollution_tqs.Service.PollutionService;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(PollutionController.class)
class PollutionControllerUTest {

    private double LAT = 40;
    private double LON = -8;
    private final long DATE_TIME = 162043200000L;
    private double BAD_LAT = 240;
    private double BAD_LON = 200;

    @Autowired
    MockMvc mvc;

    @MockBean
    private PollutionService pollutionService;

    @Test
    void getPollutionDataFromService() throws Exception {
        when(pollutionService.getCurrentPollution(LAT,LON)).thenReturn(new PollutionData(LAT,LON, new Particles(12,2,21,0.3,221,150,32.1,3.7,2,DATE_TIME)));
        mvc.perform(get("/api/pollution/current?lat="+LAT+"&lng="+LON).contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.lat", is(LAT)))
            .andExpect(jsonPath("$.lon", is(LON)));
    }
    @Test
    void getPollutionDataFromServiceBadCoord() throws Exception {
        when(pollutionService.getCurrentPollution(LAT,LON)).thenReturn(null);
        mvc.perform(get("/api/pollution/current?lat="+BAD_LAT+"&lng="+BAD_LON).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.lat", CoreMatchers.is((double)0)))
                    .andExpect(jsonPath("$.lon", CoreMatchers.is((double)0)));
        ;
    }
}
