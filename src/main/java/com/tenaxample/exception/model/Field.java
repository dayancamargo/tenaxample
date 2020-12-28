package com.tenaxample.exception.model;

import java.util.Objects;

import com.tenaxample.model.BaseModel;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class Field extends BaseModel {
    @NonNull private String parameter;
    private String value;

    public Field(String parameter, Object value) {
        this.parameter = parameter;
        this.value = Objects.toString(value, "null");
    }
}