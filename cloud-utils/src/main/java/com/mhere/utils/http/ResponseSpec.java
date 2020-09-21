package com.mhere.utils.http;

import com.mhere.base.error.AppException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseSpec {

    public interface ResponseOperationSpec<T> {
        HttpStatus httpStatus();
        HttpHeaders httpHeaders();
        ResponseEntity<T> entity();
        T body();
        AppException exception();
    }

    public static class SuccessResponseOperationSpec<T> implements ResponseOperationSpec<T> {

        private final ResponseEntity<T> entity;
        public SuccessResponseOperationSpec(ResponseEntity<T> entity) {
            this.entity = entity;
        }

        @Override
        public HttpStatus httpStatus() {
            return entity.getStatusCode();
        }

        @Override
        public HttpHeaders httpHeaders() {
            return entity.getHeaders();
        }

        @Override
        public ResponseEntity<T> entity() {
            return entity;
        }

        @Override
        public T body() {
            return entity.getBody();
        }

        @Override
        public AppException exception() {
            return null;
        }
    }

    public static class ErrorResponseOperationSpec<T> implements ResponseOperationSpec<T> {

        private final AppException exception;
        public ErrorResponseOperationSpec(AppException exception) {
            this.exception = exception;
        }

        @Override
        public HttpStatus httpStatus() {
            return HttpStatus.resolve(exception.getCode());
        }

        @Override
        public HttpHeaders httpHeaders() {
            return null;
        }

        @Override
        public ResponseEntity<T> entity() {
            return null;
        }

        @Override
        public T body() {
            return null;
        }

        @Override
        public AppException exception() {
            return exception;
        }
    }
}
