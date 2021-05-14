package ua.tqs.pollution_tqs.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.tqs.pollution_tqs.Model.Location;
import ua.tqs.pollution_tqs.Service.LocationService;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/location/")
public class LocationController {

    @Autowired
    private LocationService locationService;

    @GetMapping(path = "city")
    public ResponseEntity<Location> getLocationCity(@RequestParam String city){
        Location res = this.locationService.getLocationServC(city);
        if(res==null){
            return new ResponseEntity<>(new Location(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(this.locationService.getLocationServC(city), HttpStatus.OK);
    }

    @GetMapping(path = "city_country")
    public ResponseEntity<Location> getLocationCityCountry(@RequestParam String city, @RequestParam String country){
        Location res = this.locationService.getLocationServCC(city,country);
        if(res==null){
            return new ResponseEntity<>(new Location(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(this.locationService.getLocationServCC(city, country), HttpStatus.OK);
    }
}
