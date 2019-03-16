package com.neueda.urlshortener.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import redis.clients.jedis.Jedis;

@Service ("urlStatRepository")
public class URLStatRepository {

	private final Jedis jedis;
	private final String statKey;
	
	 private static final Logger LOGGER = LoggerFactory.getLogger(URLStatRepository.class);
	
	 public URLStatRepository() {
	        this.jedis = new Jedis();
	        statKey = "statKey";
	    }

	    public URLStatRepository(Jedis jedis, String id, String statKey) {
	        this.jedis = jedis;
	        this.statKey = statKey;
	    }
	    
	    public void saveURLStatInfo(final String id)
	    {
			long result = jedis.incr(id);
			jedis.hset(statKey, id, String.valueOf(result));
	    }
	    
	    public String fetchURLStatInfo(String id)
	    {
	    	LOGGER.info("Retrieving urlAccessCounter for {}", id);
	        String counterValue = jedis.hget(statKey, id);
	        return counterValue;
	    }

}
