package com.ceiba.parking.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ceiba.parking.model.Parking;

public interface ParkingRepository extends JpaRepository<Parking, Long> {

}
