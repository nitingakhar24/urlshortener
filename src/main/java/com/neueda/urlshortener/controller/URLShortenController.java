package com.neueda.urlshortener.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import com.neueda.urlshortener.exception.URLShortnerException;
import com.neueda.urlshortener.request.URLShortenRequest;
import com.neueda.urlshortener.service.URLShortnerService;
import com.neueda.urlshortener.service.URLStatService;
import com.neueda.urlshortener.util.URLValidator;

@RestController
@RequestMapping("/shortener")
public class URLShortenController 
{
	private static final Logger LOGGER = LoggerFactory.getLogger(URLShortenController.class);
	private final URLShortnerService urlShortnerService;
	@Autowired
	private URLStatService urlStatService;
	private static final String VALID_URL = "Please enter a valid URL";

	public URLShortenController(URLShortnerService urlShortnerService) 
	{
		this.urlShortnerService = urlShortnerService;
	}

	public void setUrlStatService(URLStatService urlStatService) 
	{
		this.urlStatService = urlStatService;
	}

	@RequestMapping(method = RequestMethod.POST, consumes = { "application/json" })
	public String shortenUrl(@RequestBody @Valid final URLShortenRequest shortenRequest, HttpServletRequest request)
			throws URLShortnerException 
	{
		String longUrl = shortenRequest.getUrl();
		LOGGER.info("Received url to shorten: " + longUrl);
		if (URLValidator.validateURL(longUrl)) 
		{
			String localURL = request.getRequestURL().toString();
			String shortenedUrl = urlShortnerService.shortenLongURL(localURL, longUrl);
			LOGGER.info("Shortened url to: ", shortenedUrl);
			return shortenedUrl;
		}
		throw new URLShortnerException(VALID_URL);
	}

	@RequestMapping(value = "{id}", method = RequestMethod.GET)
	public RedirectView redirectUrl(@PathVariable String id, HttpServletRequest request, HttpServletResponse response)
			throws URLShortnerException 
	{
		String redirectUrlString = urlShortnerService.getOriginalURLfromID(id);
		LOGGER.info("Redirecting to original URL " + redirectUrlString);
		RedirectView redirectView = new RedirectView();
		redirectView.setUrl(redirectUrlString);
		LOGGER.info("Calling URL stats service");
		urlStatService.saveURLStatInfo(id);
		return redirectView;
	}
}
