package ua.tqs.pollution_tqs.Repository;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;
import ua.tqs.pollution_tqs.Model.Particles;
import ua.tqs.pollution_tqs.Model.PollutionData;

import java.util.logging.Level;
import java.util.logging.Logger;


@Repository
public class PollutionRepository {

    private static final String BASE_URL = "http://api.openweathermap.org/data/2.5/air_pollution";
    private static final String KEY = "51712582a53bc7395faca9f6973a69c6";
    private Logger logger = Logger.getLogger(PollutionRepository.class.getName());
    private final RestTemplate restTemp = new RestTemplateBuilder().build();

    public PollutionData getCurrentPollution(double lat, double lon) {
        ResponseEntity<String> response = null;
        ObjectMapper mapper = new ObjectMapper();
        String url = BASE_URL + "?lat=" + lat + "&lon=" + lon + "&limit=1&appid=" + KEY;
        PollutionData ret = null;
        try {
            response = this.restTemp.getForEntity(url, String.class);
            JsonNode root = mapper.readTree(response.getBody());
            JsonNode item = root.path("list").get(0);
            JsonNode particles = item.path("components");
            Particles part = this.getParticles(particles, item.path("main").path("aqi").asInt(), item.path("dt").asLong() * 1000);
            ret = new PollutionData(root.path("coord").path("lat").asDouble(),root.path("coord").path("lon").asDouble(),part);
        }
        catch(Exception e){
            logger.log(Level.WARNING,e.toString());
        }
        return ret;
    }

    public Particles getParticles(JsonNode particleStats, int aqi, long dt){
        return new Particles(particleStats.path("co").asDouble(),particleStats.path("no").asDouble(),particleStats.path("no2").asDouble(),particleStats.path("o3").asDouble(),particleStats.path("so2").asDouble(),particleStats.path("pm2_5").asDouble(),particleStats.path("pm10").asDouble(),particleStats.path("nh3").asDouble(),aqi, dt);
    }
}
