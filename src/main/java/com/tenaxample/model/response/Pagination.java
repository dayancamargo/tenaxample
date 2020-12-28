package com.tenaxample.model.response;

import com.tenaxample.model.BaseModel;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Pagination extends BaseModel {
    private Integer page;
    private Integer size;
    private Long totalElements;
    private Integer totalPages;

    public Pagination(){
        page = 0;
    }

    public Pagination(Integer page, Integer size){
        this.page = page;
        this.size = size;
    }
}