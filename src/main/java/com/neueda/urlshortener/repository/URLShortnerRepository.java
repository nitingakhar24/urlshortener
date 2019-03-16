package com.neueda.urlshortener.repository;

import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.neueda.urlshortener.exception.URLShortnerException;

import redis.clients.jedis.Jedis;

@Repository
public class URLShortnerRepository 
{
    private final Jedis jedis;
    private final String idKey;
    private final String urlKey;
    private final static String URL_AT_KEY = "URL at key ";
    private final static String NOT_EXIST = " does not exist";
    private static final Logger LOGGER = LoggerFactory.getLogger(URLShortnerRepository.class);

    public URLShortnerRepository() {
        this.jedis = new Jedis();
        this.idKey = "id";
        this.urlKey = "url:";
    }

    public URLShortnerRepository(Jedis jedis, String idKey, String urlKey) {
        this.jedis = jedis;
        this.idKey = idKey;
        this.urlKey = urlKey;
    }

    public Long incrementID() {
        Long id = jedis.incr(idKey);
        LOGGER.info("Incrementing generated id: {}", id);
        return id;
    }

    public void saveUrl(Long key, String longUrl) {
        LOGGER.info("Saving: {} at {}", longUrl, key);
        jedis.hset(urlKey, String.valueOf(key), longUrl);
    }

    public String getUrl(Long id) throws URLShortnerException {
        LOGGER.info("Retrieving ID {}", id);
        String url = jedis.hget(urlKey, String.valueOf(id));
        if (url == null) {
            throw new URLShortnerException(URL_AT_KEY + id + NOT_EXIST);
        }
        return jedis.hget(urlKey, String.valueOf(id));
    }
    
	public Optional<String> fetchIDForExistingURL(String longURL) {
		Map<String, String> map = jedis.hgetAll(urlKey);
		Optional<String> key = map.entrySet().stream().filter(e -> e.getValue().equals(longURL)).map(Map.Entry::getKey)
				.findFirst();
		return key;
	}
    
   
}
