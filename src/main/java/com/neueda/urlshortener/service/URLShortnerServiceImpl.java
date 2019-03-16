package com.neueda.urlshortener.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.neueda.urlshortener.exception.URLShortnerException;
import com.neueda.urlshortener.repository.URLShortnerRepository;
import com.neueda.urlshortner.util.IDMapper;

@Service
public class URLShortnerServiceImpl implements URLShortnerService 
{
	private static final Logger LOGGER = LoggerFactory.getLogger(URLShortnerServiceImpl.class);
	@Autowired
	final URLShortnerRepository urlShortnerRepository;

	public URLShortnerServiceImpl(URLShortnerRepository urlShortnerRepository) 
	{
		this.urlShortnerRepository = urlShortnerRepository;
	}

	@Override
	public String shortenLongURL(final String localURL, final String longUrl) 
	{

		String key = fetchIDForExistingURL(localURL, longUrl);
		if (key != null) 
		{
			LOGGER.info("Retuning existing url {}", key);
			long unquieID = Long.parseLong(key);
			return prepareFormattedShortURL(localURL, IDMapper.INSTANCE.createUniqueID(unquieID));
		}
		LOGGER.info("Shortening {}", longUrl);
		Long id = urlShortnerRepository.incrementID();
		String uniqueID = IDMapper.INSTANCE.createUniqueID(id);
		urlShortnerRepository.saveUrl(id, longUrl);
		return prepareFormattedShortURL(localURL, uniqueID);

	}

	@Override
	public String getOriginalURLfromID(final String uniqueID) throws URLShortnerException 
	{
		Long dictionaryKey = IDMapper.INSTANCE.getDictionaryKeyFromUniqueID(uniqueID);
		String longUrl = urlShortnerRepository.getUrl(dictionaryKey);
		LOGGER.info("Converting shortened URL back to {}", longUrl);
		return longUrl;
	}

	@Override
	public String fetchIDForExistingURL(String localURL, String longURL) 
	{
		Optional<String> id = urlShortnerRepository.fetchIDForExistingURL(longURL);
		if (id.isPresent()) 
		{
			return id.get();
		}
		return null;
	}

	private String prepareFormattedShortURL(String localURL, String uniqueID) 
	{
		StringBuilder sb = new StringBuilder();
		sb.append(localURL);
		sb.append("/");
		sb.append(uniqueID);
		return sb.toString();
	}

}