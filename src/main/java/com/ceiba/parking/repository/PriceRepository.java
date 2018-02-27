package com.ceiba.parking.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ceiba.parking.model.Price;
import com.ceiba.parking.model.VehicleType;

public interface PriceRepository extends JpaRepository<Price, Long> {

	Price findByVehicleType(VehicleType vehicleType);
}
