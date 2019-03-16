package com.neueda.urlshortener.service;

import com.neueda.urlshortener.exception.URLShortnerException;

public interface URLShortnerService 
{
	public String shortenLongURL(final String localURL, final String longURL);

	public String getOriginalURLfromID(final String uniqueID) throws URLShortnerException;

	public String fetchIDForExistingURL(final String localURL, final String longURL);

}
