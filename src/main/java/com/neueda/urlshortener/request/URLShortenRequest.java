package com.neueda.urlshortener.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class URLShortenRequest{
    private String url;

    @JsonCreator
    public URLShortenRequest() {

    }

    @JsonCreator
    public URLShortenRequest(@JsonProperty("url") String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}