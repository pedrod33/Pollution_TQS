package ua.tqs.pollution_tqs.RepositoryTest;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.tqs.pollution_tqs.Model.Location;
import ua.tqs.pollution_tqs.Repository.LocationRepository;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class LocationRepositoryUTest {

    private String AVEIRO = "Aveiro";
    private String PORTUGAL = "PT";

    private String INVALID_NAME = "-1";

    @InjectMocks
    private LocationRepository locationRepository;

    @Test
    void validCityAndCountry(){
        Location loc = locationRepository.getLocationRepoCC(AVEIRO, PORTUGAL);
        assertThat(loc).isNotNull();
        assertThat(loc.getName()).isEqualTo(AVEIRO);
        assertThat(loc).isInstanceOf(Location.class);
    }

    @Test
    void validCity(){
        assertThat(locationRepository.getLocationRepoC(AVEIRO)).isInstanceOf(Location.class);
    }

    @Test
    void invalidCity(){
        assertThat(locationRepository.getLocationRepoC(INVALID_NAME)).isNull();
    }
    @Test
    void invalidCityAndValidCountry(){
        assertThat(locationRepository.getLocationRepoCC(INVALID_NAME,INVALID_NAME)).isNull();
    }
}