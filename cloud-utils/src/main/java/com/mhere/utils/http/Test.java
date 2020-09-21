package com.mhere.utils.http;

import reactor.core.publisher.Mono;

public class Test {

    public static void main(String[] args) {
        testA();
    }

    private static void testA() {
        Mono<String> body = HttpClient
                .create()
                .uri("http://www.henjiwuxian.com/")
                .async()
                .get(String.class)
                .map(ResponseSpec.ResponseOperationSpec::body);
        System.out.println(body.block());
    }
}
