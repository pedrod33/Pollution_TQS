package ua.tqs.pollution_tqs.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.tqs.pollution_tqs.Model.Location;
import ua.tqs.pollution_tqs.Repository.LocationRepository;

@Service
public class LocationService {

    @Autowired
    private LocationRepository locationRepository;

    public Location getLocationServC(String city) {
        return this.locationRepository.getLocationRepoC(city);
    }
    public Location getLocationServCC(String city, String country) {
        return this.locationRepository.getLocationRepoCC(city, country);
    }
}
