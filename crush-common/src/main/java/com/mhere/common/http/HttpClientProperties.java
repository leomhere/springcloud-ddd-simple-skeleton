package com.mhere.common.http;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@ConfigurationProperties(prefix = "crush")
public class HttpClientProperties {

    @Value("${crush.gate-way.server-address}")
    private String gatewayServerAddress;

    static String GATEWAY_SERVER_ADDRESS;




    @PostConstruct
    public void init() {
        GATEWAY_SERVER_ADDRESS = gatewayServerAddress;
    }
}
