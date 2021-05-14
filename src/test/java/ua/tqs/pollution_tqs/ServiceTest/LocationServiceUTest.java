package ua.tqs.pollution_tqs.ServiceTest;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.tqs.pollution_tqs.Model.Location;
import ua.tqs.pollution_tqs.Model.PollutionData;
import ua.tqs.pollution_tqs.Repository.LocationRepository;
import ua.tqs.pollution_tqs.Service.LocationService;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LocationServiceUTest {


    @Mock
    private LocationRepository locationRepository;

    private final double VALID_LAT = 40;
    private final double VALID_LON = -8;
    private final String CITY = "Aveiro";
    private final String COUNTRY = "PT";

    @InjectMocks
    private LocationService locationService;

    @Test
    void whenGetsLocationByCity_ReturnsLocation() throws Exception {
        Location response = initialize();

        when(locationRepository.getLocationRepoC(CITY)).thenReturn(response);
        assertThat(locationService.getLocationServC(CITY)).isInstanceOf(Location.class);
    }

    @Test
    void whenGetsLocationByCityAndCountryCode_ReturnsLocation() throws Exception {
        Location response = initialize();

        when(locationRepository.getLocationRepoCC(CITY,COUNTRY)).thenReturn(response);
        assertThat(locationService.getLocationServCC(CITY,COUNTRY)).isInstanceOf(Location.class);
    }

    private Location initialize(){
        return new Location(VALID_LAT, VALID_LON, CITY, COUNTRY);
    }
}
