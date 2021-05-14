package ua.tqs.pollution_tqs.Model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class PollutionData {
    private double lat;
    private double lon;
    private Particles particlesAndAQI;
    public PollutionData(double lat, double lon, Particles particles) {
        this.lat = lat;
        this.lon = lon;
        this.particlesAndAQI = particles;
    }
    @Override
    public String toString() {
        return "PollutionData{" +
                "lat=" + lat +
                ", lon=" + lon +
                '}';
    }
}
