package ua.tqs.pollution_tqs.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.tqs.pollution_tqs.Cache.Cache;
import ua.tqs.pollution_tqs.Model.PollutionData;
import ua.tqs.pollution_tqs.Repository.PollutionRepository;

import java.util.logging.Level;
import java.util.logging.Logger;


@Service
public class PollutionService {

    private Cache cache = new Cache(60L);
    private static final Logger logger = Logger.getLogger(PollutionService.class.getName());

    @Autowired
    private PollutionRepository pollutionRepository;

    public PollutionData getCurrentPollution(double lat, double lon) {
        String compare = lat+","+lon;
        PollutionData result = cache.getAndCheckRequest(compare);
        if(result!=null){
            compare+= "is present in cache";
            logger.log(Level.INFO, compare);
            return result;
        }
        result = this.pollutionRepository.getCurrentPollution(lat, lon);
        cache.saveResult(compare,result);
        return result;
    }
}
