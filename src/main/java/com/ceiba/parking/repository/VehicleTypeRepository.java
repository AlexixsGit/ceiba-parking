package com.ceiba.parking.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ceiba.parking.model.VehicleType;

public interface VehicleTypeRepository extends JpaRepository<VehicleType, Long> {

}
