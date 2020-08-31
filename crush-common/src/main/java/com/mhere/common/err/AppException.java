package com.mhere.common.err;

public class AppException extends RuntimeException {

    private AppError errors;
    private Object[] params;
    private String formattedMessage;
    private String causeMessage;

    public AppException(String message){
        super(message);
    }

    public AppException(Throwable cause, AppError errors, Object...params){
        super(cause);
        this.params = params;
        this.errors = errors;
    }

    public AppException(AppError errors, Object...params){
        this(null, errors, params);
    }

    public AppException(Throwable cause){
        this(cause, Errors.INTERNAL_ERROR);
    }

    public int getCode() {
        return errors.getCode();
    }
    public int getHttpStatus() {
        return errors.getHttpStatus();
    }
    public String getCauseMessage(){return causeMessage;}

    public void setCauseMessage(String causeMessage) {
        this.causeMessage = causeMessage;
    }

    @Override
    public String getMessage() {
        return formattedMessage == null ? errors.getMessage(this.params) : formattedMessage;
    }
}
