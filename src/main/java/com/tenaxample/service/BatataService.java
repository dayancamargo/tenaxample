package com.tenaxample.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.tenaxample.model.entity.Batata;
import com.tenaxample.repository.BatataRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class BatataService {

    private BatataRepository batataRepository;

    @Autowired
    public BatataService(BatataRepository batataRepository){
        this.batataRepository = batataRepository;
    }

    public List findAll() {
       return batataRepository.findAll();
    }

    public Page<Batata> pageableFindAll(Pageable page) {
        return batataRepository.findAll(page);
    }
}
