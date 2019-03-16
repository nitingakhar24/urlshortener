package com.neueda.urlshortener.util;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * Validates URL as per https://www.ietf.org/rfc/rfc2396.txt
 * 
 */
public class URLValidator 
{
	public static boolean validateURL(final String url) 
	{
		try 
		{
			new URL(url).toURI();
			return true;
		} catch (URISyntaxException uriSyntaxException) 
		{
			return false;
		} catch (MalformedURLException malformedURIException) 
		{
			return false;
		}
	}
}