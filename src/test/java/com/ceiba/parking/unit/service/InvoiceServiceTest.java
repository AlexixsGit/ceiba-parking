package com.ceiba.parking.unit.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ceiba.parking.model.Admin;
import com.ceiba.parking.model.Invoice;
import com.ceiba.parking.model.Parking;
import com.ceiba.parking.model.Price;
import com.ceiba.parking.model.VehicleType;
import com.ceiba.parking.repository.AdminRepository;
import com.ceiba.parking.repository.InvoiceRepository;
import com.ceiba.parking.repository.ParkingRepository;
import com.ceiba.parking.repository.PriceRepository;
import com.ceiba.parking.repository.VehicleTypeRepository;
import com.ceiba.parking.service.InvoiceServiceImpl;
import com.ceiba.parking.testdatabuilder.AdminTestDataBuilder;
import com.ceiba.parking.testdatabuilder.InvoiceTestDataBuilder;
import com.ceiba.parking.testdatabuilder.ParkingTestDataBuilder;
import com.ceiba.parking.testdatabuilder.PriceTestDataBuilder;
import com.ceiba.parking.testdatabuilder.VehicleTypeTestDataBuilder;

@RunWith(SpringJUnit4ClassRunner.class)
public class InvoiceServiceTest {

	@InjectMocks
	private InvoiceServiceImpl invoiceService;

	@Mock
	AdminRepository adminRepository;

	@Mock
	private ParkingRepository parkingRepository;

	@Mock
	private VehicleTypeRepository vehicleTypeRepository;

	@Mock
	private PriceRepository priceRepository;

	@Mock
	private InvoiceRepository invoiceRepository;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void verifySaveWithoutPlaqueTest() {
		// Arrange
		ParkingTestDataBuilder parkingTestDataBuilder = new ParkingTestDataBuilder();
		parkingTestDataBuilder.withPlaque("");

		Parking parking = parkingTestDataBuilder.build();

		// Act
		boolean isValid = this.invoiceService.validate(parking);

		// Assert
		assertFalse(isValid);
	}

	@Test
	public void verifySaveWithoutVehicleTypeTest() {
		// Arrange
		ParkingTestDataBuilder parkingTestDataBuilder = new ParkingTestDataBuilder();
		parkingTestDataBuilder.withVehicleType(null);

		Parking parking = parkingTestDataBuilder.build();

		// Act
		boolean isValid = this.invoiceService.validate(parking);

		// Assert
		assertFalse(isValid);
	}

	@Test
	public void verifySaveWithoutParkingIdTest() {
		// Arrange
		ParkingTestDataBuilder parkingTestDataBuilder = new ParkingTestDataBuilder();

		Parking parking = parkingTestDataBuilder.build();
		parking.setId(null);

		// Act
		boolean isValid = this.invoiceService.validate(parking);

		// Assert
		assertFalse(isValid);
	}

	@Test
	public void verifySaveCompleteDataTest() {
		// Arrange
		Calendar calendar = Calendar.getInstance();
		calendar.set(2018, 02, 27, 11, 00);
		Date entryDate = calendar.getTime();

		calendar = Calendar.getInstance();
		calendar.set(2018, 02, 27, 12, 00);
		Date departureDate = calendar.getTime();

		ParkingTestDataBuilder parkingTestDataBuilder = new ParkingTestDataBuilder();
		InvoiceTestDataBuilder invoiceTestDataBuilder = new InvoiceTestDataBuilder();
		AdminTestDataBuilder adminTestDataBuilder = new AdminTestDataBuilder();
		PriceTestDataBuilder priceTestDataBuilder = new PriceTestDataBuilder();
		VehicleTypeTestDataBuilder vehicleTypeTestDataBuilder = new VehicleTypeTestDataBuilder();

		parkingTestDataBuilder.withEntryDate(entryDate);
		parkingTestDataBuilder.withDepartureDate(departureDate);

		Parking parking = parkingTestDataBuilder.build();
		Invoice invoice = invoiceTestDataBuilder.build();
		Admin admin = adminTestDataBuilder.build();
		Price price = priceTestDataBuilder.build();
		VehicleType vehicleType = vehicleTypeTestDataBuilder.build();

		when(this.vehicleTypeRepository.findOne(parking.getVehicleType().getId())).thenReturn(vehicleType);
		when(this.adminRepository.findByVehicleType(parking.getVehicleType().getId())).thenReturn(admin);
		when(this.priceRepository.findByVehicleType(parking.getVehicleType())).thenReturn(price);

		// Act
		Invoice newInvoice = this.invoiceService.complete(parking, invoice);

		// Assert
		assertEquals(invoice, newInvoice);
	}

	@Test
	public void verifySaveSuccessfulTest() {
		// Arrange
		InvoiceTestDataBuilder invoiceTestDataBuilder = new InvoiceTestDataBuilder();
		Invoice invoice = invoiceTestDataBuilder.build();
		invoice.setId(1l);

		when(this.invoiceRepository.save(invoice)).thenReturn(invoice);
		// Act
		Invoice newInvoice = this.invoiceService.save(invoice);

		// Assert
		assertEquals(invoice.getId(), newInvoice.getId());

	}

	@Test
	public void verifyCalculateAdditionalCostWithLowerEngineTest() {
		// Arrange
		ParkingTestDataBuilder parkingTestDataBuilder = new ParkingTestDataBuilder();
		AdminTestDataBuilder adminTestDataBuilder = new AdminTestDataBuilder();
		PriceTestDataBuilder priceTestDataBuilder = new PriceTestDataBuilder();

		Parking parking = parkingTestDataBuilder.build();
		Admin admin = adminTestDataBuilder.build();
		Price price = priceTestDataBuilder.build();

		when(this.adminRepository.findByVehicleType(parking.getVehicleType().getId())).thenReturn(admin);
		when(this.priceRepository.findByVehicleType(parking.getVehicleType())).thenReturn(price);
		// Act
		Double value = this.invoiceService.calculateAdditionalCost(parking);
		Double expectedValue = 0d;

		// Assert
		assertEquals(expectedValue, value);

	}

	@Test
	public void verifyCalculateAdditionalCostWithHigherEngineTest() {
		// Arrange
		ParkingTestDataBuilder parkingTestDataBuilder = new ParkingTestDataBuilder();
		AdminTestDataBuilder adminTestDataBuilder = new AdminTestDataBuilder();
		PriceTestDataBuilder priceTestDataBuilder = new PriceTestDataBuilder();

		parkingTestDataBuilder.withEngineCapacity(650);

		Parking parking = parkingTestDataBuilder.build();
		Admin admin = adminTestDataBuilder.build();
		Price price = priceTestDataBuilder.build();

		when(this.adminRepository.findByVehicleType(parking.getVehicleType().getId())).thenReturn(admin);
		when(this.priceRepository.findByVehicleType(parking.getVehicleType())).thenReturn(price);
		// Act
		Double totalValue = this.invoiceService.calculateAdditionalCost(parking);
		Double expectedValue = price.getHighEnginePrice();
		// Assert
		assertEquals(expectedValue, totalValue);

	}

	@Test
	public void verifyCalculateAdditionalCostWithEqualEngineTest() {
		// Arrange
		ParkingTestDataBuilder parkingTestDataBuilder = new ParkingTestDataBuilder();
		AdminTestDataBuilder adminTestDataBuilder = new AdminTestDataBuilder();
		PriceTestDataBuilder priceTestDataBuilder = new PriceTestDataBuilder();
		adminTestDataBuilder.withEngineCapacity(500);

		Admin admin = adminTestDataBuilder.build();

		parkingTestDataBuilder.withEngineCapacity(admin.getEngineCapacity());

		Parking parking = parkingTestDataBuilder.build();
		Price price = priceTestDataBuilder.build();

		when(this.adminRepository.findByVehicleType(parking.getVehicleType().getId())).thenReturn(admin);
		when(this.priceRepository.findByVehicleType(parking.getVehicleType())).thenReturn(price);
		// Act
		Double totalValue = this.invoiceService.calculateAdditionalCost(parking);
		Double expectedValue = 0d;

		// Assert
		assertEquals(expectedValue, totalValue);

	}

	@Test
	public void verifyCorrectTimesTest() {
		// Arrange

		Calendar calendar = Calendar.getInstance();
		calendar.set(2018, 02, 27, 7, 00);
		Date entryDate = calendar.getTime();

		calendar = Calendar.getInstance();
		calendar.set(2018, 02, 28, 17, 10);
		Date departureDate = calendar.getTime();

		ParkingTestDataBuilder parkingTestDataBuilder = new ParkingTestDataBuilder();
		AdminTestDataBuilder adminTestDataBuilder = new AdminTestDataBuilder();
		parkingTestDataBuilder.withEntryDate(entryDate);
		parkingTestDataBuilder.withDepartureDate(departureDate);

		Admin admin = adminTestDataBuilder.build();

		Parking parking = parkingTestDataBuilder.build();

		List<Long> times = new ArrayList<>();
		times.add(1l);
		times.add(1l);
		times.add(0l);
		times.add(0l);

		when(this.adminRepository.findByVehicleType(parking.getVehicleType().getId())).thenReturn(admin);
		// Act
		List<Long> newTimes = this.invoiceService.getTimes(parking);

		// Assert
		assertEquals(times, newTimes);

	}

	@Test
	public void verifyCorrectSubTotalCalculationTest() {
		// Arrange

		Calendar calendar = Calendar.getInstance();
		calendar.set(2018, 02, 27, 7, 00);
		Date entryDate = calendar.getTime();

		calendar = Calendar.getInstance();
		calendar.set(2018, 02, 28, 17, 10);
		Date departureDate = calendar.getTime();

		ParkingTestDataBuilder parkingTestDataBuilder = new ParkingTestDataBuilder();
		AdminTestDataBuilder adminTestDataBuilder = new AdminTestDataBuilder();
		PriceTestDataBuilder priceTestDataBuilder = new PriceTestDataBuilder();

		parkingTestDataBuilder.withEntryDate(entryDate);
		parkingTestDataBuilder.withDepartureDate(departureDate);

		Admin admin = adminTestDataBuilder.build();

		Parking parking = parkingTestDataBuilder.build();
		Price price = priceTestDataBuilder.build();

		when(this.priceRepository.findByVehicleType(parking.getVehicleType())).thenReturn(price);
		when(this.adminRepository.findByVehicleType(parking.getVehicleType().getId())).thenReturn(admin);
		// Act
		Double subtotal = this.invoiceService.calculateSubtotal(parking);
		Double expectedValue = 8000d;

		// Assert
		assertEquals(expectedValue, subtotal);

	}

	@Test
	public void verifyPlaqueExistsTest() {
		// Arrange

		ParkingTestDataBuilder parkingTestDataBuilder = new ParkingTestDataBuilder();
		List<Parking> parkingList = new ArrayList<>();
		parkingList.add(new Parking());

		parkingTestDataBuilder.withPlaque("ABC123");
		parkingTestDataBuilder.withDepartureDate(new Date());
		Parking parking = parkingTestDataBuilder.build();

		when(this.parkingRepository.findByPlaqueWithDepartureDate(parking.getPlaque())).thenReturn(parkingList);
		// Act
		boolean isValid = this.invoiceService.validateIfPlaqueExists(parking.getPlaque());
		// Assert
		assertTrue(isValid);

	}

	@Test
	public void verifyPlaqueNotExistsTest() {
		// Arrange

		ParkingTestDataBuilder parkingTestDataBuilder = new ParkingTestDataBuilder();
		List<Parking> parkingList = new ArrayList<>();

		parkingTestDataBuilder.withPlaque("ABC12356");
		parkingTestDataBuilder.withDepartureDate(new Date());
		Parking parking = parkingTestDataBuilder.build();

		when(this.parkingRepository.findByPlaqueWithDepartureDate(parking.getPlaque())).thenReturn(parkingList);
		// Act
		boolean isValid = this.invoiceService.validateIfPlaqueExists(parking.getPlaque());
		// Assert
		assertFalse(isValid);

	}

	@Test
	public void verifyPlaqueNotExistsWhenDepartureDateIsNullTest() {
		// Arrange

		ParkingTestDataBuilder parkingTestDataBuilder = new ParkingTestDataBuilder();
		List<Parking> parkingList = new ArrayList<>();

		parkingTestDataBuilder.withPlaque("ABC123");

		Parking parking = parkingTestDataBuilder.build();

		when(this.parkingRepository.findByPlaqueWithDepartureDate(parking.getPlaque())).thenReturn(parkingList);
		// Act
		boolean isValid = this.invoiceService.validateIfPlaqueExists(parking.getPlaque());
		// Assert
		assertFalse(isValid);

	}
}
