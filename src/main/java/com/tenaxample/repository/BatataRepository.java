package com.tenaxample.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tenaxample.model.entity.Batata;

@Repository
public interface BatataRepository extends JpaRepository<Batata, Integer> {
}
