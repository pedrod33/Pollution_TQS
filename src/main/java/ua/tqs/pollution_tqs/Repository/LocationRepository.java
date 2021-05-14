package ua.tqs.pollution_tqs.Repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import ua.tqs.pollution_tqs.Model.Location;


@Repository
public class LocationRepository {

    private static final String BASE_URL = "http://api.openweathermap.org/geo/1.0/direct";
    private static final String KEY = "51712582a53bc7395faca9f6973a69c6";

    private final RestTemplate restTemp = new RestTemplateBuilder().build();

    public Location getLocationRepoCC(String city, String country){
        ResponseEntity<String> response = null;
        Location location = null;
        String url = BASE_URL+"?q="+city+","+country+"&limit=1&appid="+KEY;
        location = this.getLocationMapping(url);

        return location;
    }
    public Location getLocationRepoC(String city){
        Location location = null;
        String url = BASE_URL+"?q="+city+"&limit=1&appid="+KEY;
        location = this.getLocationMapping(url);
        return location;
    }

    private Location getLocationMapping(String url){
        JsonNode loc = null;
        ResponseEntity<String> response = null;
        ObjectMapper mapper = new ObjectMapper();
        Location retLocation = null;
        try {
            response = this.restTemp.getForEntity(url, String.class);
            JsonNode root = mapper.readTree(response.getBody());
            loc = root.get(0);
            retLocation = new Location(loc.path("lat").asDouble(), loc.path("lon").asDouble(), loc.path("name").asText(), loc.path("country").asText());
        }
        catch(HttpClientErrorException e) {
            return null; //bad request
        }
        catch (JsonProcessingException e) {
            return null; //json format
        }
        catch (NullPointerException e){
            return null;
        }
        return retLocation;
    }
}
