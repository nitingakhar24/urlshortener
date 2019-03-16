package com.neueda.urlshortener.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.neueda.urlshortner.service.URLStatService;

@RestController
@RequestMapping("/shortener/stats/")
public class URLStatsController 
{
    private final URLStatService urlStatService;
    
    public URLStatsController(URLStatService urlStatService) 
    {
        this.urlStatService = urlStatService;
    }
    
    @RequestMapping(value = "{id}", method=RequestMethod.GET)
    public String fetchURLStat(@PathVariable String id)
    {
    	String urlAccessCounter = urlStatService.fetchURLStatInfo(id);
    	return urlAccessCounter;
    }

}
