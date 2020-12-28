package com.tenaxample.exception.model;

import lombok.Getter;

import java.util.Collections;
import java.util.List;

import com.tenaxample.model.BaseModel;

@Getter
public class Error extends BaseModel {

    private final String title;
    private final String code;
    private String detail;
    private List<Field> fields;

    public Error(String title) {
        this.code = "999";
        this.title = title;
    }

    public Error(String title, String code) {
        this.code = code;
        this.title = title;
    }

    public Error(String title, String code, String detail) {
        this.code = code;
        this.detail = detail;
        this.title = title;
    }

    public Error(String title, String code, String detail, String field) {
        this.code = code;
        this.detail = detail;
        this.title = title;
        this.fields = Collections.singletonList(new Field(field));
    }

    public Error(String title, String code, String detail, List<Field> fields) {
        this.code = code;
        this.detail = detail;
        this.title = title;
        this.fields = fields;
    }
}