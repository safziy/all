package com.safziy.controller.base;

import com.safziy.constants.ErrCode;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler implements WebSupport {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.OK)
    public Resp<Object> buildException(HttpServletRequest request, HttpServletResponse response, Exception exception) {
        log.error(exception.getMessage(), exception);
        return error(ErrCode.InternalError, exception.getMessage());
    }
}
