package com.tenaxample.exception;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.tenaxample.exception.model.Error;

public class ErrorFactory {

    /**
     * Create error list from a MethodArgumentNotValidException
     *
     * @param valException exception to be converted to list
     * @return list of errors
     */
    public static Error errorFromValidationException(MethodArgumentNotValidException valException) {
        String code = "400";
        String title = "Incorrect request format";
        String errors = "";
        valException.getBindingResult().getFieldErrors().forEach( err -> {
            errors.concat("\n" + err.getField() + " - " + err.getRejectedValue());
        });

        return new Error(code, title, errors);
    }

    /**
     * Create error object from a ServletRequestBindingException
     *
     * @param bindExc exception to be converted to list
     * @return list of errors
     */
    public static Error errorFromRequestBindingException(ServletRequestBindingException bindExc) {
        return new Error("400","Incorrect request format", bindExc.getMessage());
    }

    /**
     * Create error object from a MethodArgumentTypeMismatchException
     *
     * @param typeExc exception to be converted to list
     * @return list of errors
     */
    public static Error errorFromTypeMismatchException(MethodArgumentTypeMismatchException typeExc) {
        return new Error("400","Incorrect request format",typeExc.getParameter().getParameterName() + " - " + typeExc.getValue());
    }

    /**
     * Create error object from a Exception, all non-expected exceptions should pass here
     *
     * @param exc exception to be converted to error object
     * @return internal error
     */
    public static Error errorFromException(Exception exc) {

        return new Error(" Something went wrong", "500", exc.getMessage());
    }
    public static Error errorFromBaseException(BaseException exc) {
        return exc.getError();
    }
}
