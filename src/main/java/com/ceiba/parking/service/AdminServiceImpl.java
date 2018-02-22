package com.ceiba.parking.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ceiba.parking.model.Admin;
import com.ceiba.parking.repository.AdminRepository;

@Service
public class AdminServiceImpl implements AdminService {

	@Autowired
	protected AdminRepository adminRepository;

	@Override
	public List<Admin> findAll() {
		return adminRepository.findAll();
	}

	@Override
	public Admin findByVehicleType(Long vehicleTypeId) {
		return this.adminRepository.findByVehicleType(vehicleTypeId);
	}
}
