package com.mhere.common.err;

public class ErrorResponse {

    private int code;
    private String message;
    private String detailMessage;
    public ErrorResponse(AppException ex){
        this.code = ex.getCode();
        this.message = ex.getMessage();
        Throwable cause = ex.getCause();
        if(cause != null)
            this.detailMessage = cause.getMessage();
    }
    public ErrorResponse(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public ErrorResponse(Integer code, String message, String detailMessage) {
        this.code = code;
        this.message = message;
        this.detailMessage = detailMessage;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDetailMessage() {
        return detailMessage;
    }

    public void setDetailMessage(String detailMessage) {
        this.detailMessage = detailMessage;
    }

}
