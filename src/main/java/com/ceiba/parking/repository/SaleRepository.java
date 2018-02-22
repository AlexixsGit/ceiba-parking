package com.ceiba.parking.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ceiba.parking.model.Sale;

public interface SaleRepository extends JpaRepository<Sale, Long> {

}
