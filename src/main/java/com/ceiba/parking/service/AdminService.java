package com.ceiba.parking.service;

import java.util.List;

import com.ceiba.parking.model.Admin;

public interface AdminService {

	List<Admin> findAll();

	Admin findByVehicleType(Long vehicleTypeId);
}
