package com.mhere.utils.http.loadbalance;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.DefaultUriBuilderFactory;
import org.springframework.web.util.UriBuilderFactory;

import javax.annotation.PostConstruct;

@Configuration
public class LoadBalanceConfigure {

    public static UriBuilderFactory URI_BUILDER_FACTORY = new DefaultUriBuilderFactory();
    public static WebClient.Builder WEB_CLIENT_BUILDER = WebClient.builder();
    public static RestTemplate REST_TEMPLATE = new RestTemplate();
    public static WebClient.Builder LOAD_BALANCE_WEB_CLIENT_BUILDER;
    public static RestTemplate LOAD_BALANCE_REST_TEMPLATE;

    @Bean
    @LoadBalanced
    public WebClient.Builder loadBalancedWebClientBuilder() {
        return WebClient.builder();
    }

    @Bean
    @LoadBalanced
    public RestTemplate loadBalancedRestTemplate() {
        return new RestTemplate();
    }

    @PostConstruct
    public void init() {
        LOAD_BALANCE_WEB_CLIENT_BUILDER = loadBalancedWebClientBuilder();
        LOAD_BALANCE_REST_TEMPLATE = loadBalancedRestTemplate();
    }
}
