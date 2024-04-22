package com.rentitnow.rent.repository;

import com.rentitnow.rent.domain.Rent;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RentRepository extends CrudRepository<Rent, Long> {

    List<Rent> findAll();
}
