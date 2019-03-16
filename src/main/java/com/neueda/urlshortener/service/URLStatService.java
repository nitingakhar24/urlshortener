package com.neueda.urlshortener.service;

public interface URLStatService 
{
	
	public void saveURLStatInfo(final String id);
	
	public String fetchURLStatInfo(final String id);

}
