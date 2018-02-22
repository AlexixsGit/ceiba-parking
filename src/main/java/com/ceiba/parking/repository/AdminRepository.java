package com.ceiba.parking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ceiba.parking.model.Admin;

public interface AdminRepository extends JpaRepository<Admin, Long> {

	@Query("select a from Admin a where vehicleType.id =:vehicleTypeId")
	Admin findByVehicleType(@Param("vehicleTypeId") Long vehicleTypeId);
}
