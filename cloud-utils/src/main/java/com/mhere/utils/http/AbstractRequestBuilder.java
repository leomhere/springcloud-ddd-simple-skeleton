package com.mhere.utils.http;

import org.reactivestreams.Publisher;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ClientHttpRequest;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.util.UriBuilder;
import org.springframework.web.util.UriBuilderFactory;

import java.net.URI;
import java.nio.charset.Charset;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

abstract public class AbstractRequestBuilder<T extends RequestSpec.RequestBodyUriSpec> implements RequestSpec.RequestBodyUriSpec {

    private final UriBuilderFactory uriBuilderFactory;

    protected AbstractRequestBuilder(UriBuilderFactory uriBuilderFactory) {
        this.uriBuilderFactory = uriBuilderFactory;
    }

    @Nullable
    private URI uri = null;
    @Nullable
    private HttpHeaders headers = null;
    @Nullable
    private MultiValueMap<String, String> cookies = null;
    @Nullable
    private Object body = null;

    private boolean throwWhenException = true;

    protected URI getUri() {
        return uri != null
                ? uri
                : uriBuilderFactory.expand("");
    }

    protected HttpHeaders getHeaders() {
        if (headers == null){
            headers = new HttpHeaders();
        }
        return headers;
    }

    protected MultiValueMap<String, String> getCookies() {
        if (cookies == null){
            cookies = new LinkedMultiValueMap<>(4);
        }
        return cookies;
    }

    protected boolean isThrowWhenException(){
        return throwWhenException;
    }
    
    @Nullable
    protected Object getBody() {
        return body;
    }

    @Override
    public AbstractRequestBuilder<T> uri(URI uri) {
        this.uri = uri;
        return this;
    }

    @Override
    public AbstractRequestBuilder<T> uri(String uriTemplate, Object... uriVariables) {
        if (uriTemplate.contains("lb:")) {
            uriTemplate = uriTemplate.replace("lb:", HttpClientProperties.GATEWAY_SERVER_ADDRESS);
        }
        this.uri = uriBuilderFactory.expand(uriTemplate, uriVariables);
        return this;
    }

    @Override
    public AbstractRequestBuilder<T> uri(String uriTemplate, Map<String, ?> uriVariables) {
        this.uri = uriBuilderFactory.expand(uriTemplate, uriVariables);
        return this;
    }

    @Override
    public AbstractRequestBuilder<T> uri(Function<UriBuilder, URI> uriFunction) {
        AbstractRequestBuilder.this.uri(uriFunction.apply(uriBuilderFactory.builder()));
        return this;
    }


    @Override
    public AbstractRequestBuilder<T> header(String headerName, String... headerValues) {
        for (String headerValue : headerValues) {
            getHeaders().add(headerName, headerValue);
        }
        return this;
    }

    @Override
    public AbstractRequestBuilder<T> headers(@NonNull Map<String, String> httpHeaders) {
        HttpHeaders headers = getHeaders();
        httpHeaders.forEach(headers::add);
        return this;
    }

    @Override
    public AbstractRequestBuilder<T> headers(@NonNull MultiValueMap<String, String> httpHeaders) {
        getHeaders().addAll(httpHeaders);
        return this;
    }

    @Override
    public AbstractRequestBuilder<T> headers(Consumer<HttpHeaders> headersConsumer) {
        headersConsumer.accept(getHeaders());
        return this;
    }

    @Override
    public AbstractRequestBuilder<T> accept(MediaType... acceptableMediaTypes) {
        getHeaders().setAccept(Arrays.asList(acceptableMediaTypes));
        return this;
    }

    @Override
    public AbstractRequestBuilder<T> acceptCharset(Charset... acceptableCharsets) {
        getHeaders().setAcceptCharset(Arrays.asList(acceptableCharsets));
        return this;
    }

    @Override
    public AbstractRequestBuilder<T> contentType(MediaType contentType) {
        getHeaders().setContentType(contentType);
        return this;
    }

    @Override
    public AbstractRequestBuilder<T> contentLength(long contentLength) {
        getHeaders().setContentLength(contentLength);
        return this;
    }

    @Override
    public AbstractRequestBuilder<T> cookie(String name, String value) {
        getCookies().add(name, value);
        return this;
    }

    @Override
    public AbstractRequestBuilder<T> cookies(@NonNull Map<String, String> cookies) {
        MultiValueMap<String, String> thisCookies = getCookies();
        cookies.forEach(thisCookies::add);
        return this;
    }

    @Override
    public AbstractRequestBuilder<T> cookies(@NonNull MultiValueMap<String, String> cookies) {
        getCookies().addAll(cookies);
        return this;
    }

    @Override
    public AbstractRequestBuilder<T> cookies(Consumer<MultiValueMap<String, String>> cookiesConsumer) {
        cookiesConsumer.accept(getCookies());
        return this;
    }

    @Override
    public AbstractRequestBuilder<T> ifModifiedSince(ZonedDateTime ifModifiedSince) {
        ZonedDateTime gmt = ifModifiedSince.withZoneSameInstant(ZoneId.of("GMT"));
        String headerValue = DateTimeFormatter.RFC_1123_DATE_TIME.format(gmt);
        getHeaders().set(HttpHeaders.IF_MODIFIED_SINCE, headerValue);
        return this;
    }

    @Override
    public AbstractRequestBuilder<T> ifNoneMatch(String... ifNoneMatches) {
        getHeaders().setIfNoneMatch(Arrays.asList(ifNoneMatches));
        return this;
    }

    @Override
    public AbstractRequestBuilder<T> body(Object body) {
        this.body = body;
        return this;
    }

    @Override
    public AbstractRequestBuilder<T> body(BodyInserter<?, ? super ClientHttpRequest> inserter) {
        this.body = inserter;
        return this;
    }

    @Override
    public <R, P extends Publisher<R>> AbstractRequestBuilder<T> body(P publisher, ParameterizedTypeReference<R> typeReference) {
        this.body = BodyInserters.fromPublisher(publisher, typeReference);
        return this;
    }

    @Override
    public <R, P extends Publisher<R>> AbstractRequestBuilder<T> body(P publisher, Class<R> elementClass) {
        this.body = BodyInserters.fromPublisher(publisher, elementClass);
        return this;
    }

    @Override
    public AbstractRequestBuilder<T> noThrow() {
        this.throwWhenException = false;
        return this;
    }
}
