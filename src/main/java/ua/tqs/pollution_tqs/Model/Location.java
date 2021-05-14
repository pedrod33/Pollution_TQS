package ua.tqs.pollution_tqs.Model;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Location {
    private double lat;
    private double lng;
    private String name;
    private String country;
    public Location(double lat, double lng, String name, String country) {
        this.lat = lat;
        this.lng = lng;
        this.name = name;
        this.country = country;
    }
}
