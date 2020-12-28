package com.tenaxample.exception;

import java.util.List;

import com.tenaxample.exception.model.Field;
import com.tenaxample.exception.model.Error;
import lombok.Getter;

@Getter
public class BaseException  extends RuntimeException {

    private final Error error;

    public BaseException(String title) {
        this.error = new Error(title);
    }

    public BaseException(String code, String title) {
        this.error = new Error(code, title);
    }

    public BaseException(String code, String detail, String title) {
        this.error = new Error(title, code, detail);
    }

    public BaseException(String code, String detail, String title, String pointer) {
        this.error = new Error(code, detail, title, pointer);
    }

    public BaseException(String code, String detail, String title, List<Field> fields) {
        this.error = new Error(code, detail, title, fields);
    }
}