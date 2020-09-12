package com.mhere.utils.http;

import org.reactivestreams.Publisher;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ClientHttpRequest;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.util.UriBuilder;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.nio.charset.Charset;
import java.time.ZonedDateTime;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

public class RequestSpec {

    public interface RequestOperationSpec {
        AsyncRequestOperationSpec async();
        <T> ResponseSpec.ResponseOperationSpec<T> method(HttpMethod method, Class<T> responseType);
        <T> ResponseSpec.ResponseOperationSpec<T> method(HttpMethod method, ParameterizedTypeReference<T> responseType);
        default <T> ResponseSpec.ResponseOperationSpec<T> get(Class<T> responseType) {
            return this.method(HttpMethod.GET, responseType);
        }
        default <T> ResponseSpec.ResponseOperationSpec<T> get(ParameterizedTypeReference<T> responseType) {
            return this.method(HttpMethod.GET, responseType);
        }
        default <T> ResponseSpec.ResponseOperationSpec<T> post(Class<T> responseType) {
            return this.method(HttpMethod.POST, responseType);
        }
        default <T> ResponseSpec.ResponseOperationSpec<T> post(ParameterizedTypeReference<T> responseType) {
            return this.method(HttpMethod.POST, responseType);
        }
        default <T> ResponseSpec.ResponseOperationSpec<T> patch(Class<T> responseType) {
            return this.method(HttpMethod.PATCH, responseType);
        }
        default <T> ResponseSpec.ResponseOperationSpec<T> patch(ParameterizedTypeReference<T> responseType) {
            return this.method(HttpMethod.PATCH, responseType);
        }
        default ResponseSpec.ResponseOperationSpec<Void> put() {
            return this.method(HttpMethod.PUT, Void.class);
        }
        default ResponseSpec.ResponseOperationSpec<Void> delete() {
            return this.method(HttpMethod.DELETE, Void.class);
        }
        default ResponseSpec.ResponseOperationSpec<Void> head() {
            return this.method(HttpMethod.HEAD, Void.class);
        }
        default ResponseSpec.ResponseOperationSpec<Void> options() {
            return this.method(HttpMethod.OPTIONS, Void.class);
        }
    }

    public interface AsyncRequestOperationSpec {
        <T> Mono<ResponseSpec.ResponseOperationSpec<T>> method(HttpMethod method, Class<T> responseType);
        <T> Mono<ResponseSpec.ResponseOperationSpec<T>> method(HttpMethod method, ParameterizedTypeReference<T> responseType);
        default <T> Mono<ResponseSpec.ResponseOperationSpec<T>> get(Class<T> responseType){
            return this.method(HttpMethod.GET, responseType);
        }
        default <T> Mono<ResponseSpec.ResponseOperationSpec<T>> get(ParameterizedTypeReference<T> responseType) {
            return this.method(HttpMethod.GET, responseType);
        }
        default <T> Mono<ResponseSpec.ResponseOperationSpec<T>> post(Class<T> responseType) {
            return this.method(HttpMethod.POST, responseType);
        }
        default <T> Mono<ResponseSpec.ResponseOperationSpec<T>> post(ParameterizedTypeReference<T> responseType) {
            return this.method(HttpMethod.POST, responseType);
        }
        default <T> Mono<ResponseSpec.ResponseOperationSpec<T>> patch(Class<T> responseType) {
            return this.method(HttpMethod.PATCH, responseType);
        }
        default <T> Mono<ResponseSpec.ResponseOperationSpec<T>> patch(ParameterizedTypeReference<T> responseType) {
            return this.method(HttpMethod.PATCH, responseType);
        }
        default Mono<ResponseSpec.ResponseOperationSpec<Void>> put() {
            return this.method(HttpMethod.PUT, Void.class);
        }
        default Mono<ResponseSpec.ResponseOperationSpec<Void>> delete() {
            return this.method(HttpMethod.DELETE, Void.class);
        }
        default Mono<ResponseSpec.ResponseOperationSpec<Void>> head() {
            return this.method(HttpMethod.HEAD, Void.class);
        }
        default Mono<ResponseSpec.ResponseOperationSpec<Void>> options() {
            return this.method(HttpMethod.OPTIONS, Void.class);
        }
    }

    public interface RequestHeadersSpec<T extends RequestHeadersSpec<T>> extends RequestOperationSpec{
        T accept(MediaType... acceptableMediaType);

        T acceptCharset(Charset... acceptableCharset);

        T cookie(String name, String value);

        T cookies(Map<String, String> cookies);

        T cookies(MultiValueMap<String, String> cookies);

        T cookies(Consumer<MultiValueMap<String, String>> cookiesConsumer);

        T ifModifiedSince(ZonedDateTime isModifiedSince);

        T ifNoneMatch(String... ifNoneMatches);

        T header(String name, String... values);

        T headers(Map<String, String> headers);

        T headers(MultiValueMap<String, String> headers);

        T headers(Consumer<HttpHeaders> headersConsumer);

        T noThrow();
    }

    public interface RequestBodySpec extends RequestHeadersSpec<RequestBodySpec> {
        RequestBodySpec contentLength(long contentLength);

        RequestBodySpec contentType(MediaType mediaType);

        RequestHeadersSpec<RequestBodySpec> body(Object body);

        RequestHeadersSpec<RequestBodySpec> body(BodyInserter<?, ? super ClientHttpRequest> inserter);

        <T, P extends Publisher<T>> RequestHeadersSpec<RequestBodySpec> body(P publisher, ParameterizedTypeReference<T> typeReference);

        <T, P extends Publisher<T>> RequestHeadersSpec<RequestBodySpec> body(P publisher, Class<T> elementClass);

    }

    public interface UriSpec<T extends RequestHeadersSpec<?>> {
        T uri(URI uri);

        T uri(String uri, Object... uriVariables);

        T uri(String uri, Map<String, ?> uriVariables);

        T uri(Function<UriBuilder, URI> uriFunction);
    }

    public interface RequestHeadersUriSpec<T extends RequestHeadersSpec<T>> extends UriSpec<T>, RequestHeadersSpec<T> {}

    public interface RequestBodyUriSpec extends RequestBodySpec, RequestHeadersUriSpec<RequestBodySpec> {}
}
