package com.mhere.common.err;

public enum  Errors implements AppError {

    INTERNAL_ERROR                  (500, 500, "服务器内部错误"),
    TOKEN_EXPIRE                    (403, -22, "TOKEN已失效或者不存在"),
    UN_AUTHORIZED                    (401, 401, "unauthorized"),
    INVALID_USER                    (401, 401, "请先去注册"),
    INVALID_PWD                    (401, 401, "账号或密码错误"),
    ;

    private int httpStatus;
    private int code;
    private String template;

    Errors(int httpStatus, int code, String template) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.template = template;
    }

    public static AppException fromErrorResponse(int httpStatus, ErrorResponse res){
        return new GenericAppError(httpStatus, res.getCode(), res.getMessage()+(res.getDetailMessage()!=null&&!"".equals(res.getDetailMessage())?"："+res.getDetailMessage():"")).create();
    }
    public static AppException fromErrorResponse(Throwable cause, int httpStatus, ErrorResponse res){
        return new GenericAppError(httpStatus, res.getCode(), res.getMessage()).createWithCause(cause);
    }
    public static AppException fromCodeMessage(int httpStatus, int code, String message){
        return new GenericAppError(httpStatus, code, message).create();
    }
    public static AppException fromCodeMessage(Throwable cause, int httpStatus, int code, String message){
        return new GenericAppError(httpStatus, code, message).createWithCause(cause);
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
        return String.format(getTemplate(), params);
    }

    private String getTemplate() {
        return template;
    }

    @Override
    public AppException create(Object... params) {
        return createWithCause(null, params);
    }

    @Override
    public AppException createWithCause(Throwable cause, Object... params) {
        return  new AppException(cause, this, params);
    }
}
