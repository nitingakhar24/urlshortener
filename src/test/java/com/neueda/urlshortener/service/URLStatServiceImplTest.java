package com.neueda.urlshortener.service;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.neueda.urlshortener.repository.URLStatRepository;
import com.neueda.urlshortener.service.URLStatServiceImpl;

@RunWith(MockitoJUnitRunner.class)
public class URLStatServiceImplTest 
{

	private static final String ID = "mockID";

	@Mock
	private URLStatRepository urlStatRepository;

	@InjectMocks
	private URLStatServiceImpl urlStatService;

	@Test
	public void shouldSaveURLStatInfo() 
	{
		// when
		urlStatService.saveURLStatInfo(ID);
		// then
		verify(urlStatRepository, times(1)).saveURLStatInfo(ID);

	}
	
	@Test
	public void shouldfetchURLStatInfo() 
	{
		// when
		urlStatService.fetchURLStatInfo(ID);
		// then
		verify(urlStatRepository, times(1)).fetchURLStatInfo(ID);

	}

}
