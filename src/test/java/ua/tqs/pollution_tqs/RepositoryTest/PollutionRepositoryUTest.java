package ua.tqs.pollution_tqs.RepositoryTest;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.tqs.pollution_tqs.Model.Location;
import ua.tqs.pollution_tqs.Model.PollutionData;
import ua.tqs.pollution_tqs.Repository.PollutionRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class PollutionRepositoryUTest {

    private double AVEIRO_LAT = 40.6443;
    private double AVEIRO_LON = -8.6455;

    private double WRONG_COORD = -190;
    @InjectMocks
    private PollutionRepository pollutionRepository;

    @Test
    void validLatAndLon(){
        PollutionData pol = pollutionRepository.getCurrentPollution(AVEIRO_LAT,AVEIRO_LON);
        assertThat(pol).isInstanceOf(PollutionData.class);
        assertThat(pol.getLat()).isEqualTo(AVEIRO_LAT);
        assertThat(pol.getParticlesAndAQI()).isNotNull();
    }
    @Test
    void invalidLatAndValidLon(){
        PollutionData data = pollutionRepository.getCurrentPollution(WRONG_COORD,AVEIRO_LON);
        assertThat(data).isNull();
    }
    @Test
    void validLatAndInvalidLon(){
        PollutionData data = pollutionRepository.getCurrentPollution(AVEIRO_LAT,WRONG_COORD);
        assertThat(data).isNull();
    }
    @Test
    void invalidLatAndInvalidLon(){
        PollutionData data = pollutionRepository.getCurrentPollution(WRONG_COORD,WRONG_COORD);
        assertThat(data).isNull();
    }
}
