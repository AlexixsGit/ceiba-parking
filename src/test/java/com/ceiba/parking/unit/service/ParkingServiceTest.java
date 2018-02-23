package com.ceiba.parking.unit.service;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ceiba.parking.model.Admin;
import com.ceiba.parking.model.Parking;
import com.ceiba.parking.repository.AdminRepository;
import com.ceiba.parking.repository.ParkingRepository;
import com.ceiba.parking.service.AdminServiceImpl;
import com.ceiba.parking.service.ParkingServiceImpl;
import com.ceiba.parking.testdatabuilder.AdminTestDataBuilder;
import com.ceiba.parking.testdatabuilder.ParkingTestDataBuilder;

@RunWith(SpringJUnit4ClassRunner.class)
public class ParkingServiceTest {

	@InjectMocks
	private ParkingServiceImpl parkingService;

	@InjectMocks
	private AdminServiceImpl adminService;

	@Mock
	AdminRepository adminRepository;

	@Mock
	private ParkingRepository parkingRepository;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
//		this.parkingService = new ParkingServiceImpl(this.adminService);
	}

	@Test
	public void plaqueExistTest() {

		// Arrange
		ParkingTestDataBuilder parkingTestDataBuilder = new ParkingTestDataBuilder();
		parkingTestDataBuilder.withPlaque("AIH123");

		Parking parking = parkingTestDataBuilder.build();

		List<Parking> parkingList = new ArrayList<>();
		parkingList.add(parking);

		// Act
		when(parkingRepository.findByPlaque(parking.getPlaque())).thenReturn(parkingList);

		boolean exists = this.parkingService.validateIfPlaqueExist(parking);

		// Assert
		assertTrue(exists);

	}

	@Test
	public void plaqueNotExistTest() {

		// Arrange
		ParkingTestDataBuilder parkingTestDataBuilder = new ParkingTestDataBuilder();

		Parking parking = parkingTestDataBuilder.build();

		List<Parking> parkingList = new ArrayList<>();

		// Act
		when(parkingRepository.findByPlaque(parking.getPlaque())).thenReturn(parkingList);

		boolean exists = this.parkingService.validateIfPlaqueExist(parking);

		// Assert
		assertFalse(exists);

	}

	@Test
	public void thereIsSpaceInTheParkingTest() {
		// Arrange
		ParkingTestDataBuilder parkingTestDataBuilder = new ParkingTestDataBuilder();
		AdminTestDataBuilder adminTestDataBuilder = new AdminTestDataBuilder();

		Parking parking = parkingTestDataBuilder.build();
		Admin admin = adminTestDataBuilder.build();

		List<Parking> parkingList = new ArrayList<>();

		// Act
//		when(adminRepository.findByVehicleType(parking.getVehicleType().getId())).thenReturn(admin);
		when(parkingRepository.findAllByType(parking.getVehicleType().getId())).thenReturn(parkingList);

		boolean isThereCapacity = this.parkingService.validateCapacity(parking);

		// Assert
		assertTrue(isThereCapacity);

	}

}