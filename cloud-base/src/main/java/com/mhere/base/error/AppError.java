package com.mhere.base.error;

public interface AppError {

    int getCode();

    int getHttpStatus();

    String getMessage(Object... params);

    AppException create(Object... params);

    AppException createWithCause(Throwable cause, Object... params);
}
