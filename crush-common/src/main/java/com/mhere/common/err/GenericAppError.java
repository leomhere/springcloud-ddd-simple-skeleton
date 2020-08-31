package com.mhere.common.err;

public class GenericAppError implements AppError {

    private int httpStatus;
    private int code;
    private String message;
    private String causeMessage;

    public GenericAppError(int httpStatus, int code, String message, String causeMessage) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
        this.causeMessage = causeMessage;
    }

    public GenericAppError(int httpStatus, int code, String message) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public int getHttpStatus() {
        return httpStatus;
    }

    @Override
    public String getMessage(Object... params) {
        return message;
    }

    @Override
    public AppException create(Object... params) {
        return createWithCause(null, params);
    }

    @Override
    public AppException createWithCause(Throwable cause, Object... params) {
        return new AppException(cause, this, params);
    }
}
