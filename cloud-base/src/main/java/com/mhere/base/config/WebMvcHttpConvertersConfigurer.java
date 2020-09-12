package com.mhere.base.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

public class WebMvcHttpConvertersConfigurer implements WebMvcConfigurer {
    private final HttpMessageConverters httpMessageConverters;

    @Autowired
    public WebMvcHttpConvertersConfigurer(HttpMessageConverters httpMessageConverters) {
        this.httpMessageConverters = httpMessageConverters;
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.clear();
        converters.addAll(httpMessageConverters.getConverters());
    }
}
