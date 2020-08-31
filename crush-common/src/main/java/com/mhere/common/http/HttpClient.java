package com.mhere.common.http;

import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.DefaultUriBuilderFactory;
import org.springframework.web.util.UriBuilderFactory;

public class HttpClient {

    static UriBuilderFactory URI_BUILDER_FACTORY = new DefaultUriBuilderFactory();
    static WebClient.Builder WEB_CLIENT_BUILDER = WebClient.builder();

    public static AbstractRequestBuilder create(){
        return new WebClientRequestBuilder(WEB_CLIENT_BUILDER, URI_BUILDER_FACTORY);
    }

    public static AbstractRequestBuilder create(RestTemplate restTemplate) {
        return new RestTemplateRequestBuilder(restTemplate, URI_BUILDER_FACTORY);
    }

}
