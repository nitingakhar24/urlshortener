package com.neueda.urlshortener.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.neueda.urlshortener.repository.URLStatRepository;

@Service("urlStatService")
public class URLStatServiceImpl implements URLStatService {

	@Autowired
	private final URLStatRepository urlStatRepository;

	public URLStatServiceImpl(URLStatRepository urlStatRepository) {
		this.urlStatRepository = urlStatRepository;
	}

	@Override
	public void saveURLStatInfo(String id) {
		urlStatRepository.saveURLStatInfo(id);
	}

	@Override
	public String fetchURLStatInfo(String id) {

		return urlStatRepository.fetchURLStatInfo(id);
	}

}
