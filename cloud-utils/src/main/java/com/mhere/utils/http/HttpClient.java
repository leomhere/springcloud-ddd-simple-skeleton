package com.mhere.utils.http;

import com.mhere.utils.http.loadbalance.LoadBalanceConfigure;
import org.springframework.web.client.RestTemplate;

public class HttpClient {

    public static AbstractRequestBuilder create(){
        return new WebClientRequestBuilder(LoadBalanceConfigure.URI_BUILDER_FACTORY, LoadBalanceConfigure.WEB_CLIENT_BUILDER);
    }

    public static AbstractRequestBuilder create(RestTemplate restTemplate) {
        return new RestTemplateRequestBuilder(LoadBalanceConfigure.URI_BUILDER_FACTORY, LoadBalanceConfigure.REST_TEMPLATE);
    }
}
