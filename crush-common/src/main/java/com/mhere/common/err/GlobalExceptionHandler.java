package com.mhere.common.err;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;

// 接口异常统一处理器

@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    //处理自定义的异常
    @ExceptionHandler(AppException.class)
    @ResponseBody
    public ErrorResponse appExceptionHandler(HttpServletResponse response, AppException e) {
        response.setStatus(e.getHttpStatus());
        if (logger.isDebugEnabled()) {
            logger.debug(e.toString(), e);
        }
        return new ErrorResponse(e);
    }

    //其他未处理的异常
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ErrorResponse exceptionHandler(HttpServletResponse response, Exception e) {
        logger.error(e.toString(), e);
        return appExceptionHandler(response, Errors.INTERNAL_ERROR.createWithCause(e));
    }
}
