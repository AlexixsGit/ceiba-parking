package com.ceiba.parking.unit.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ceiba.parking.model.Admin;
import com.ceiba.parking.model.Parking;
import com.ceiba.parking.model.VehicleType;
import com.ceiba.parking.repository.AdminRepository;
import com.ceiba.parking.repository.ParkingRepository;
import com.ceiba.parking.repository.VehicleTypeRepository;
import com.ceiba.parking.service.ParkingServiceImpl;
import com.ceiba.parking.testdatabuilder.AdminTestDataBuilder;
import com.ceiba.parking.testdatabuilder.ParkingTestDataBuilder;
import com.ceiba.parking.testdatabuilder.VehicleTypeTestDataBuilder;

@RunWith(SpringJUnit4ClassRunner.class)
public class ParkingServiceTest {

	@InjectMocks
	private ParkingServiceImpl parkingService;

	@Mock
	AdminRepository adminRepository;

	@Mock
	private ParkingRepository parkingRepository;

	@Mock
	private VehicleTypeRepository vehicleTypeRepository;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}

	@After
	public void finish() {
		this.adminRepository = null;
		this.parkingRepository = null;
		this.vehicleTypeRepository = null;
		this.parkingService = null;
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
		when(adminRepository.findByVehicleType(parking.getVehicleType().getId())).thenReturn(admin);
		when(parkingRepository.findAllByType(parking.getVehicleType().getId())).thenReturn(parkingList);

		boolean isThereCapacity = this.parkingService.validateCapacity(parking);

		// Assert
		assertTrue(isThereCapacity);

	}

	@Test
	public void thereIsNotSpaceInTheParkingTest() {
		// Arrange
		ParkingTestDataBuilder parkingTestDataBuilder = new ParkingTestDataBuilder();
		AdminTestDataBuilder adminTestDataBuilder = new AdminTestDataBuilder();
		adminTestDataBuilder.withCapacity(0);

		Parking parking = parkingTestDataBuilder.build();
		Admin admin = adminTestDataBuilder.build();

		List<Parking> parkingList = new ArrayList<>();

		// Act
		when(adminRepository.findByVehicleType(parking.getVehicleType().getId())).thenReturn(admin);
		when(parkingRepository.findAllByType(parking.getVehicleType().getId())).thenReturn(parkingList);

		boolean isThereCapacity = this.parkingService.validateCapacity(parking);

		// Assert
		assertFalse(isThereCapacity);

	}

	@Test
	public void plaqueLetterWithAOnSundayTest() {

		// Arrange
		ParkingTestDataBuilder parkingTestDataBuilder = new ParkingTestDataBuilder();
		AdminTestDataBuilder adminTestDataBuilder = new AdminTestDataBuilder();

		Calendar cal = Calendar.getInstance();
		cal.set(2018, Calendar.MARCH, 4); // Year, month and day of month
		Date date = cal.getTime();

		parkingTestDataBuilder.withPlaque("ABC123");
		parkingTestDataBuilder.withEntryDate(date);

		Parking parking = parkingTestDataBuilder.build();
		Admin admin = adminTestDataBuilder.build();

		// Act
		when(adminRepository.findByVehicleType(parking.getVehicleType().getId())).thenReturn(admin);

		boolean isPermitted = this.parkingService.validateIfPlaqueIsPermitted(parking);

		// Assert
		assertTrue(isPermitted);
	}

	@Test
	public void plaqueLetterWithAOnMondayTest() {

		// Arrange
		ParkingTestDataBuilder parkingTestDataBuilder = new ParkingTestDataBuilder();
		AdminTestDataBuilder adminTestDataBuilder = new AdminTestDataBuilder();

		Calendar cal = Calendar.getInstance();
		cal.set(2018, Calendar.MARCH, 5); // Year, month and day of month
		Date date = cal.getTime();

		parkingTestDataBuilder.withPlaque("ABC123");
		parkingTestDataBuilder.withEntryDate(date);

		Parking parking = parkingTestDataBuilder.build();
		Admin admin = adminTestDataBuilder.build();

		// Act
		when(adminRepository.findByVehicleType(parking.getVehicleType().getId())).thenReturn(admin);

		boolean isPermitted = this.parkingService.validateIfPlaqueIsPermitted(parking);

		// Assert
		assertTrue(isPermitted);
	}

	@Test
	public void plaqueLetterWithAOnTuesdayTest() {

		// Arrange
		ParkingTestDataBuilder parkingTestDataBuilder = new ParkingTestDataBuilder();
		AdminTestDataBuilder adminTestDataBuilder = new AdminTestDataBuilder();

		Calendar cal = Calendar.getInstance();
		cal.set(2018, Calendar.MARCH, 6); // Year, month and day of month
		Date date = cal.getTime();

		parkingTestDataBuilder.withPlaque("ABC123");
		parkingTestDataBuilder.withEntryDate(date);

		Parking parking = parkingTestDataBuilder.build();
		Admin admin = adminTestDataBuilder.build();

		// Act
		when(adminRepository.findByVehicleType(parking.getVehicleType().getId())).thenReturn(admin);

		boolean isPermitted = this.parkingService.validateIfPlaqueIsPermitted(parking);

		// Assert
		assertFalse(isPermitted);
	}

	@Test
	public void validateWithoutVehicleTest() {
		// Arrange
		ParkingTestDataBuilder parkingTestDataBuilder = new ParkingTestDataBuilder();
		parkingTestDataBuilder.withVehicleType(null);

		Parking parking = parkingTestDataBuilder.build();
		// Act
		boolean isValid = this.parkingService.validate(parking);

		// Assert
		assertFalse(isValid);
	}

	@Test
	public void validateWithoutPlaqueTest() {
		// Arrange
		ParkingTestDataBuilder parkingTestDataBuilder = new ParkingTestDataBuilder();
		parkingTestDataBuilder.withPlaque("");

		Parking parking = parkingTestDataBuilder.build();
		// Act
		boolean isValid = this.parkingService.validate(parking);

		// Assert
		assertFalse(isValid);
	}

	@Test
	public void validateWithAllDataTest() {
		// Arrange
		ParkingTestDataBuilder parkingTestDataBuilder = new ParkingTestDataBuilder();

		Parking parking = parkingTestDataBuilder.build();
		// Act
		boolean isValid = this.parkingService.validate(parking);

		// Assert
		assertTrue(isValid);
	}

	@Test
	public void vehicleTypeNotExistsTest() {
		// Arrange
		ParkingTestDataBuilder parkingTestDataBuilder = new ParkingTestDataBuilder();
		parkingTestDataBuilder.withVehicleType(new VehicleType(10l, "Camion"));

		Parking parking = parkingTestDataBuilder.build();
		when(this.vehicleTypeRepository.findOne(parking.getVehicleType().getId())).thenReturn(null);
		// Act
		boolean vehicleTypeExists = this.parkingService.validateVehicleType(parking.getVehicleType());

		// Assert
		assertFalse(vehicleTypeExists);
	}

	@Test
	public void vehicleTypeExistsTest() {
		// Arrange
		ParkingTestDataBuilder parkingTestDataBuilder = new ParkingTestDataBuilder();
		VehicleTypeTestDataBuilder vehicleTypeTestDataBuilder = new VehicleTypeTestDataBuilder();

		VehicleType VehicleType = vehicleTypeTestDataBuilder.build();

		Parking parking = parkingTestDataBuilder.build();
		when(this.vehicleTypeRepository.findOne(parking.getVehicleType().getId())).thenReturn(VehicleType);
		// Act
		boolean vehicleTypeExists = this.parkingService.validateVehicleType(parking.getVehicleType());

		// Assert
		assertTrue(vehicleTypeExists);
	}

	@Test
	public void completeInfoTest() {

		// Arrange
		ParkingTestDataBuilder parkingTestDataBuilder = new ParkingTestDataBuilder();

		Parking parking = parkingTestDataBuilder.build();

		// Act
		Parking newParking = this.parkingService.complete(parking);

		// Assert
		assertEquals(parking, newParking);
	}

	@Test
	public void verifyFindAllTest() {
		// Arrange
		ParkingTestDataBuilder parkingTestDataBuilder = new ParkingTestDataBuilder();
		Parking parking = parkingTestDataBuilder.build();

		List<Parking> expectedParkingList = new ArrayList<>();
		expectedParkingList.add(parking);

		when(this.parkingRepository.findAll()).thenReturn(expectedParkingList);
		// Act
		List<Parking> newParkingList = this.parkingService.findAll();

		// Assert
		assertEquals(expectedParkingList, newParkingList);
	}

	@Test
	public void verifyFindAllByPlaqueTest() {
		// Arrange
		ParkingTestDataBuilder parkingTestDataBuilder = new ParkingTestDataBuilder();
		Parking parking = parkingTestDataBuilder.build();

		List<Parking> expectedParkingList = new ArrayList<>();
		expectedParkingList.add(parking);

		when(this.parkingRepository.findByPlaque(parking.getPlaque())).thenReturn(expectedParkingList);
		// Act
		List<Parking> newParkingList = this.parkingService.findByPlaque(parking.getPlaque());

		// Assert
		assertEquals(expectedParkingList, newParkingList);
	}

	@Test
	public void verifyFindAllByWithoutPlaqueTest() {
		// Arrange
		ParkingTestDataBuilder parkingTestDataBuilder = new ParkingTestDataBuilder();
		parkingTestDataBuilder.withPlaque("");
		Parking parking = parkingTestDataBuilder.build();

		List<Parking> expectedParkingList = new ArrayList<>();

		when(this.parkingRepository.findByPlaque(parking.getPlaque())).thenReturn(expectedParkingList);
		// Act
		List<Parking> newParkingList = this.parkingService.findByPlaque(parking.getPlaque());

		// Assert
		assertEquals(expectedParkingList, newParkingList);
	}

}
