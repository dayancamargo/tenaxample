package com.tenaxample.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.tenaxample.model.entity.Batata;
import com.tenaxample.repository.BatataRepository;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class BatataService {

    private BatataRepository batataRepository;

    @Autowired
    public BatataService(BatataRepository batataRepository){
        this.batataRepository = batataRepository;
    }

    public List findAll() {
//        try {
//            Thread.sleep(5000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        return batataRepository.findAll();
    }

    public Page<Batata> pageableFindAll(Pageable page) {
        return batataRepository.findAll(page);
    }
}
