package ua.tqs.pollution_tqs.ServiceTest;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.tqs.pollution_tqs.Cache.Cache;
import ua.tqs.pollution_tqs.Model.Particles;
import ua.tqs.pollution_tqs.Model.PollutionData;
import ua.tqs.pollution_tqs.Repository.PollutionRepository;
import ua.tqs.pollution_tqs.Service.PollutionService;

import java.util.logging.Level;
import java.util.logging.Logger;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PollutionServiceUTest {

    private final double RES_LAT = 24.35;
    private final double RES_LON = -8;
    private final long DATE_TIME = 162043200000L;
    @Mock
    private PollutionRepository pollutionRepository;

    @Mock
    private Cache cache;

    @InjectMocks
    private PollutionService pollutionService;

    private Logger logger = Logger.getLogger(PollutionServiceUTest.class.getName());
    @Test
    void whenGetsPollutionFromRepositoryData_ReturnPollutionData(){
        PollutionData data = initialize();
        when(this.pollutionRepository.getCurrentPollution(RES_LAT,RES_LON)).thenReturn(data);
        assertThat(this.pollutionService.getCurrentPollution(RES_LAT,RES_LON)).isNotNull();
        assertThat(this.pollutionService.getCurrentPollution(RES_LAT,RES_LON)).isInstanceOf(PollutionData.class);
        assertThat(this.pollutionService.getCurrentPollution(RES_LAT,RES_LON).getLat()).isEqualTo(24.35);
    }

    @Test
    void whenGetsPollutionFromCacheData_ReturnPollutionData(){
        PollutionData data = initialize();
        String loc = RES_LAT+","+RES_LON;
        logger.log(Level.INFO,loc);
        this.cache.saveResult(loc, data);
        when(this.cache.getAndCheckRequest(loc)).thenReturn(data);
        assertThat(this.pollutionService.getCurrentPollution(RES_LAT,RES_LON));
        verify(cache,times(1)).getAndCheckRequest(anyString());
    }

    private PollutionData initialize(){
        Particles part = new Particles(12,2,21,0.3,221,150,32.1,3.7,2,DATE_TIME);
        return new PollutionData( 24.35,-8, part);
    }
}
