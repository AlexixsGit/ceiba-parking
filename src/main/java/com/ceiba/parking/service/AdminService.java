package com.ceiba.parking.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.ceiba.parking.model.Admin;

@Transactional
public interface AdminService {

	List<Admin> findAll();

	Admin findByVehicleType(Long vehicleTypeId);
}
