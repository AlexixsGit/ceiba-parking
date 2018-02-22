package com.ceiba.parking.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ceiba.parking.repository.VehicleTypeRepository;

@Service
public class VehicleTypeServiceImpl implements VehicleTypeService {

	@Autowired
	protected VehicleTypeRepository vehicleTypeRepository;
}
