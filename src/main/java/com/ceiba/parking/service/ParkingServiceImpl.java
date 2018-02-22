package com.ceiba.parking.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ceiba.parking.repository.ParkingRepository;

@Service
public class ParkingServiceImpl implements ParkingService {

	@Autowired
	protected ParkingRepository parkingRepository;
}
