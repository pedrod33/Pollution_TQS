package ua.tqs.pollution_tqs.Cache;


import com.fasterxml.jackson.annotation.JsonIgnore;
import ua.tqs.pollution_tqs.Model.PollutionData;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Cache {
    private long ttl;
    private int numberOfRequests;
    private int numberHits;
    private int numberMisses;

    Logger logger = Logger.getLogger(Cache.class.getName());
    @JsonIgnore
    private Map<String, PollutionData> requests;

    @JsonIgnore
    private Map<String, Long> requestsExpiration;

    public Cache(long defaultExpire) {
        this.requests = new HashMap<>();
        this.requestsExpiration = new HashMap<>();
        this.ttl = defaultExpire;
    }

    public void saveResult(String location, PollutionData data) {
        this.requests.put(location, data);
        this.requestsExpiration.put(location, getCurrentTimeInMillis() + this.ttl * 1000);
    }

    public PollutionData getAndCheckRequest(String name) {
        this.numberOfRequests++;
        PollutionData request = null;
        String print;
        if (!exists(name)) {
            this.numberMisses++;
            print="Amount of misses: "+this.numberMisses;
            logger.log(Level.INFO,print);
        } else if (hasExpired(name)) {
            logger.log(Level.INFO,"expired");
            this.removeExpiredRequest(name);
            this.numberMisses++;
        } else {
            this.numberHits++;
            print="Amount of hits: "+this.numberHits;
            logger.log(Level.INFO,print);

            request = this.requests.get(name);
        }

        return request;
    }

    private boolean exists(String name) {
        return this.requestsExpiration.containsKey(name);
    }

    private boolean hasExpired(String name) {
        Long expireTime = this.requestsExpiration.get(name);
        return getCurrentTimeInMillis() > expireTime;
    }

    private void removeExpiredRequest(String name) {
        this.requests.remove(name);
        this.requestsExpiration.remove(name);
    }

    public int getNumberOfRequests() {
        return numberOfRequests;
    }

    public int getNumberOfHits() {
        return numberHits;
    }

    public int getNumberOfMisses() {
        return numberMisses;
    }

    private long getCurrentTimeInMillis() {
        return System.currentTimeMillis();
    }

}