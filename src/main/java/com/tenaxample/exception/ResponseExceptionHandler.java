package com.tenaxample.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.tenaxample.model.response.Response;

import lombok.extern.log4j.Log4j2;

@ControllerAdvice
@RestController
@Log4j2
public class ResponseExceptionHandler {
    //Returns code 400
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = ServletRequestBindingException.class)
    public Response handleServletRequestException(ServletRequestBindingException exc) {
        return Response.build().withErrors(ErrorFactory.errorFromRequestBindingException(exc)).create();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = MethodArgumentTypeMismatchException.class)
    public Response handleMethodArgumentException(MethodArgumentTypeMismatchException matmex) {
        return  Response.build().withErrors(ErrorFactory.errorFromTypeMismatchException(matmex)).create();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public Response handleMethodArgumentNotValidException(MethodArgumentNotValidException manvexc) {
        return  Response.build().withErrors(ErrorFactory.errorFromValidationException(manvexc)).create();
    }

    //Returns code 500
    //this will cathes all exceptions un-handled
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = Exception.class)
    public Response handleException(Exception ex) {
        log.error(ex.getMessage(), ex);
        return Response.build().withErrors(ErrorFactory.errorFromException(ex)).create();
    }
}
