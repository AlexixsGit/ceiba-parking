package com.ceiba.parking.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ceiba.parking.model.Parking;

public interface ParkingRepository extends JpaRepository<Parking, Long> {

	@Query("select p from Parking p where p.vehicleType.id = :vehicleTypeId and departureDate is null")
	List<Parking> findAllByType(@Param("vehicleTypeId") Long vehicleTypeId);

	@Query("select p from Parking p where p.plaque = :plaque and departureDate is null")
	List<Parking> findByPlaque(@Param("plaque") String plaque);
}
