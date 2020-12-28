package com.tenaxample.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import com.tenaxample.exception.model.Error;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@RestController
@Slf4j
public class ResponseExceptionHandler {
    //Returns code 400
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = ServletRequestBindingException.class)
    public Error handleServletRequestException(ServletRequestBindingException exc) {
        return ErrorFactory.errorFromRequestBindingException(exc);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = MethodArgumentTypeMismatchException.class)
    public Error handleMethodArgumentException(MethodArgumentTypeMismatchException matmex) {
        return ErrorFactory.errorFromTypeMismatchException(matmex);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public Error handleMethodArgumentNotValidException(MethodArgumentNotValidException manvexc) {
        return ErrorFactory.errorFromValidationException(manvexc);
    }

    //Returns code 500
    //this will cathes all exceptions un-handled
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = Exception.class)
    public Error handleException(Exception ex) {
        log.error(ex.getMessage(), ex);
        return ErrorFactory.errorFromException(ex);
    }
}
