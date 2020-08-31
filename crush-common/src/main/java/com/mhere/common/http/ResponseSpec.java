package com.mhere.common.http;

import com.mhere.common.err.AppException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseSpec {

    public interface ResponseOperationSpec<T> {
        ResponseEntity<T> entity();
        AppException exception();
        HttpStatus httpStatus();
        HttpHeaders headers();
        T body();
    }

    public static class SuccessResponseSpec<T> implements ResponseOperationSpec<T> {
        private final ResponseEntity<T> entity;

        public SuccessResponseSpec(ResponseEntity<T> entity) {
            this.entity = entity;
        }

        @Override
        public ResponseEntity<T> entity() {
            return entity;
        }

        @Override
        public AppException exception() {
            return null;
        }

        @Override
        public HttpStatus httpStatus() {
            return entity.getStatusCode();
        }

        @Override
        public HttpHeaders headers() {
            return entity.getHeaders();
        }

        @Override
        public T body() {
            return entity.getBody();
        }
    }

    public static class ErrorResponseSpec<T> implements ResponseOperationSpec<T> {
        private final AppException exception;

        public ErrorResponseSpec(AppException exception) {
            this.exception = exception;
        }

        @Override
        public ResponseEntity<T> entity() {
            return null;
        }

        @Override
        public AppException exception() {
            return exception;
        }

        @Override
        public HttpStatus httpStatus() {
            return HttpStatus.resolve(exception.getCode());
        }

        @Override
        public HttpHeaders headers() {
            return null;
        }

        @Override
        public T body() {
            return null;
        }
    }
}
