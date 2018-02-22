package com.ceiba.parking.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ceiba.parking.model.Parking;
import com.ceiba.parking.repository.ParkingRepository;

@Service
public class ParkingServiceImpl implements ParkingService {

	@Autowired
	protected ParkingRepository parkingRepository;

	@Override
	public void save(Parking parking) {
		this.parkingRepository.save(parking);
	}

	@Override
	public List<Parking> findAllByType(Long vehicleTypeId) {
		return this.parkingRepository.findAllByType(vehicleTypeId);
	}
}
