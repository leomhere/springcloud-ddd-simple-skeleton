package com.mhere.utils.http;

import com.mhere.base.error.AppException;
import com.mhere.utils.http.loadbalance.LoadBalanceConfigure;
import com.sun.net.httpserver.Headers;
import org.reactivestreams.Publisher;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.reactive.ClientHttpRequest;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.util.UriBuilderFactory;

import java.net.URI;
import java.util.List;

public class RestTemplateRequestBuilder extends AbstractRequestBuilder<RestTemplateRequestBuilder>
        implements RequestSpec.RequestSpecGatherer {
    private RestTemplate restTemplate;

    protected RestTemplateRequestBuilder(UriBuilderFactory uriBuilderFactory, RestTemplate restTemplate) {
        super(uriBuilderFactory);
        this.restTemplate = restTemplate;
    }

    @Override
    public AbstractRequestBuilder<RestTemplateRequestBuilder> lb() {
        this.restTemplate = LoadBalanceConfigure.LOAD_BALANCE_REST_TEMPLATE;
        return this;
    }

    @Override
    public AbstractRequestBuilder<RestTemplateRequestBuilder> body(BodyInserter<?, ? super ClientHttpRequest> inserter) {
        throw new UnsupportedOperationException("RestTemplateRequestBuilder not support async operation.");
    }

    @Override
    public <T, P extends Publisher<T>> AbstractRequestBuilder<RestTemplateRequestBuilder> body(P publisher, ParameterizedTypeReference<T> typeReference) {
        throw new UnsupportedOperationException("RestTemplateRequestBuilder not support async operation.");
    }

    @Override
    public <T, P extends Publisher<T>> AbstractRequestBuilder<RestTemplateRequestBuilder> body(P publisher, Class<T> elementClass) {
        throw new UnsupportedOperationException("RestTemplateRequestBuilder not support async operation.");
    }

    private RestTemplate getCustomizedRestTemplate(URI uri) {
        return restTemplate;
    }

    @Override
    public <R> ResponseSpec.ResponseOperationSpec<R> method(HttpMethod httpMethod, Class<R> responseType) {
        try {
            URI uri = getUri();
            RequestEntity<?> requestEntity = getRequestEntity(httpMethod, uri);
            ResponseEntity<R> result = getCustomizedRestTemplate(uri).exchange(requestEntity, responseType);
            return new ResponseSpec.SuccessResponseOperationSpec<>(result);
        } catch (AppException e) {
            return handleException(e);
        }
    }

    @Override
    public <R> ResponseSpec.ResponseOperationSpec<R> method(HttpMethod httpMethod, ParameterizedTypeReference<R> responseType) {
        try {
            URI uri = getUri();
            RequestEntity<?> requestEntity = getRequestEntity(httpMethod, uri);
            ResponseEntity<R> result = getCustomizedRestTemplate(uri).exchange(requestEntity, responseType);
            return new ResponseSpec.SuccessResponseOperationSpec<R>(result);
        } catch (AppException e) {
            return handleException(e);
        }
    }

    private <R> ResponseSpec.ResponseOperationSpec<R> handleException(AppException e) {
        if (isThrowWhenException()) {
            throw e;
        }
        return new ResponseSpec.ErrorResponseOperationSpec<>(e);
    }

    // Async handler
    @Override
    public RequestSpec.AsyncRequestOperationSpec async() {
        throw new UnsupportedOperationException("RestTemplateRequestBuilder not support async operation.");
    }

    private RequestEntity<?> getRequestEntity(HttpMethod httpMethod, URI uri) {
        RequestEntity.BodyBuilder builder = RequestEntity.method(httpMethod, uri);
        for (Headers.Entry<String, List<String>> header : getHeaders().entrySet()) {
            String key = header.getKey();
            for (String value : header.getValue()) {
                builder.header(key, value);
            }
        }
        Object body = getBody();
        if (body == null)
            return builder.build();
        else
            return builder.body(body);
    }
}
