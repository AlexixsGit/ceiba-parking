package com.ceiba.parking.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ceiba.parking.model.Price;

public interface PriceRepository extends JpaRepository<Price, Long> {

}
