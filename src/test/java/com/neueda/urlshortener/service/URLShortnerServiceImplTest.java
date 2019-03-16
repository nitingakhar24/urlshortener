package com.neueda.urlshortener.service;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.neueda.urlshortener.exception.URLShortnerException;
import com.neueda.urlshortener.repository.URLShortnerRepository;
import com.neueda.urlshortener.service.URLShortnerServiceImpl;
import com.neueda.urlshortener.util.IDMapper;

@RunWith(MockitoJUnitRunner.class)
public class URLShortnerServiceImplTest 
{
	
	private static final String LOCAL_URL = "http://localhost:8080/shortener";
	private static final String LONG_URL = "https://averyyyyyyyyyylonggggurl.com/letstestthisurlshorteningapplication";
	private static final String UNIQUE_ID = "a";
	
	@Mock
	private URLShortnerRepository urlShortnerRepository;
	
	@Mock
	private IDMapper idMapper;
	
	@InjectMocks
	private URLShortnerServiceImpl urlShortnerService;
	
	@Test
	public void shouldSaveLongUrlAndReturnShortUrlForNonExistingURL()
	{
		//given
		when(urlShortnerService.fetchIDForExistingURL(LOCAL_URL, LONG_URL)).thenReturn(null);
		when(urlShortnerRepository.fetchIDForExistingURL(LONG_URL)).thenReturn(Optional.empty());
		long id = Long.valueOf(1);
		when(urlShortnerRepository.incrementID()).thenReturn(id);
		//when
		urlShortnerService.shortenLongURL(LOCAL_URL, LONG_URL);
		//then
		 verify(urlShortnerRepository, times(1)).saveUrl(id, LONG_URL);
		 
	}
	
	@Test
	public void shouldfetchIDforExistingURL()
	{
		//given
		String id = "2";
		when(urlShortnerRepository.fetchIDForExistingURL(LONG_URL)).thenReturn(Optional.of(id));
		//when
		String shortURL = urlShortnerService.fetchIDForExistingURL(LOCAL_URL, LONG_URL);
		//then
		 assertNotNull(shortURL);
		 
	}
	
	//@Test
	public void shouldGetLongURLfromUniqueID() throws URLShortnerException
	{
		//given
		Long dictionaryKey = Long.valueOf(2);
		when(urlShortnerRepository.getUrl(dictionaryKey)).thenReturn(LONG_URL);
		//when
		String longURL = urlShortnerService.getOriginalURLfromID(UNIQUE_ID);
		//then
		 assertNotNull(longURL);
		 
	}
	
}
