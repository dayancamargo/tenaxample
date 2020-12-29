package com.tenaxample.controller;

import org.apache.logging.log4j.Level;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tenaxample.model.entity.Batata;
import com.tenaxample.model.response.Response;
import com.tenaxample.service.BatataService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;

@RestController()
@RequestMapping("batata")
@Log4j2
@Api(value = "Batatas", tags = "Batata")
public class BatataController {

    private BatataService batataService;

    @Autowired
    public BatataController(BatataService batataService) {
        this.batataService = batataService;
    }

    @ApiOperation(value = "Simple find all batatas with pagination")
    @GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE }, path = "/paged")
    public Response<Batata, Error> pageableFind(Pageable page) {

        log.debug("Page {}", page);

        return Response.build()
                .withPagination(batataService.pageableFindAll(page))
                .create();
    }

    @ApiOperation(value = "Simple find all batatas")
    @GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE })
    public Response<Batata, Error> find() {

        return Response.build()
                .withBody(batataService.findAll())
                .create();
    }
}