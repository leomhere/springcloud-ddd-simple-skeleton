package com.mhere.utils.http;

import com.alibaba.fastjson.JSON;
import com.mhere.base.error.AppException;
import com.mhere.base.error.ErrorResponse;
import com.mhere.base.error.Errors;
import com.mhere.utils.http.loadbalance.LoadBalanceConfigure;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.reactive.ClientHttpRequest;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriBuilderFactory;
import reactor.core.publisher.Mono;

import java.io.InputStream;
import java.io.SequenceInputStream;
import java.net.URI;
import java.util.Objects;

public class WebClientRequestBuilder extends AbstractRequestBuilder<WebClientRequestBuilder>
        implements RequestSpec.RequestSpecGatherer {

    private WebClient.Builder webClientBuilder;

    protected WebClientRequestBuilder(UriBuilderFactory uriBuilderFactory, WebClient.Builder webClientBuilder) {
        super(uriBuilderFactory);
        this.webClientBuilder = webClientBuilder;
    }

    @Override
    public RequestSpec.AsyncRequestOperationSpec async() {
        return new RequestSpec.AsyncRequestOperationSpec() {
            @Override
            public <T> Mono<ResponseSpec.ResponseOperationSpec<T>> method(HttpMethod method, Class<T> responseType) {
                return exchange(method, responseType);
            }

            @Override
            public <T> Mono<ResponseSpec.ResponseOperationSpec<T>> method(HttpMethod method, ParameterizedTypeReference<T> responseType) {
                return exchange(method, responseType);
            }
        };
    }

    @Override
    public <T> ResponseSpec.ResponseOperationSpec<T> method(HttpMethod method, Class<T> responseType) {
        return this.exchange(method, responseType).block();
    }

    @Override
    public <T> ResponseSpec.ResponseOperationSpec<T> method(HttpMethod method, ParameterizedTypeReference<T> responseType) {
        return this.exchange(method, responseType).block();
    }

    private <R> Mono<ResponseSpec.ResponseOperationSpec<R>> exchange(HttpMethod httpMethod, ParameterizedTypeReference<R> responseType) {
        return exchange(httpMethod)
                .flatMap(response -> response.toEntity(responseType))
                .map(entity -> (ResponseSpec.ResponseOperationSpec<R>) new ResponseSpec.SuccessResponseOperationSpec<>(entity))
                .onErrorResume(AppException.class, e -> {
                    if (isThrowWhenException())
                        throw e;
                    return Mono.just(new ResponseSpec.ErrorResponseOperationSpec<R>(e));
                });
    }

    private <R> Mono<ResponseSpec.ResponseOperationSpec<R>> exchange(HttpMethod httpMethod, Class<R> responseType) {
        return exchange(httpMethod)
                .flatMap(response -> response.toEntity(responseType))
                .map(entity -> (ResponseSpec.ResponseOperationSpec<R>) new ResponseSpec.SuccessResponseOperationSpec<>(entity))
                .onErrorResume(AppException.class, e -> {
                    if (isThrowWhenException())
                        throw e;
                    return Mono.just(new ResponseSpec.ErrorResponseOperationSpec<>(e));
                });

    }

    private Mono<ClientResponse> exchange(HttpMethod httpMethod) {
        URI uri = super.getUri();
        return webClientBuilder.build()
                .method(httpMethod)
                .uri(uri)
                .headers(headers -> headers.putAll(super.getHeaders()))
                .cookies(cookies -> cookies.putAll(super.getCookies()))
                .body(this.getBodyInserter())
                .exchange()
                .retry(3)
                .flatMap(this::handleClientResponseError);
    }

    private BodyInserter<?, ? super ClientHttpRequest> getBodyInserter() {
        Object body = super.getBody();
        if (Objects.isNull(body))
            return BodyInserters.empty();
        if (body instanceof BodyInserter)
            return (BodyInserter<?, ? super ClientHttpRequest>) body;
        return BodyInserters.fromObject(body);
    }

    private Mono<ClientResponse> handleClientResponseError(ClientResponse response) {
        if (!response.statusCode().isError()) {
            return Mono.just(response);
        }
        return response
                .body((inputMessage, context) -> inputMessage.getBody()
                        .collect(InputStreamCollector::new, (t, buffer) -> t.collectInputStream(buffer.asInputStream())))
                .map(inputStreamCollector -> {
                    ErrorResponse errorResponse;
                    try {
                        errorResponse = JSON.parseObject(inputStreamCollector.getInputStream(), ErrorResponse.class);
                    } catch (Exception ex) {
                        throw Errors.INTERNAL_ERROR.createWithCause(ex);
                    }
                    throw Errors.fromErrorResponse(response.statusCode().value(), errorResponse);
                });
    }

    @Override
    public AbstractRequestBuilder<WebClientRequestBuilder> lb() {
        this.webClientBuilder = LoadBalanceConfigure.LOAD_BALANCE_WEB_CLIENT_BUILDER;
        return this;
    }

    private static class InputStreamCollector {
        private InputStream inputStream;

        private void collectInputStream(InputStream inputStream) {
            if (this.inputStream == null) {
                this.inputStream = inputStream;
            }
            this.inputStream = new SequenceInputStream(this.inputStream, inputStream);
        }

        private InputStream getInputStream() {
            return this.inputStream;
        }
    }
}
