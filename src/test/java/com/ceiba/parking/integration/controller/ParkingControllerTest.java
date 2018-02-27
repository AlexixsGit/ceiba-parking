package com.ceiba.parking.integration.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Calendar;
import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.ceiba.parking.ParkingApplication;
import com.ceiba.parking.model.Parking;
import com.ceiba.parking.model.VehicleType;
import com.ceiba.parking.repository.ParkingRepository;
import com.ceiba.parking.util.ApplicationMessages;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ParkingApplication.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ParkingControllerTest {

	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext wac;

	@Autowired
	protected ParkingRepository parkingRepository;

	private ObjectMapper mapper;

	private Parking parking;

	@Before
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
		this.mapper = new ObjectMapper();
		VehicleType vehicleType = new VehicleType(1l, "Moto");
		this.parking = new Parking(vehicleType, "HCB123", null, "", null, "", 200);
	}

	@Test
	@Sql({ "/delete-parking.sql" })
	public void verifyEnterVehicleSuccessfulTest() throws Exception {
		String json = this.mapper.writeValueAsString(this.parking);

		mockMvc.perform(MockMvcRequestBuilders.post("/parking/saveOrUpdate").contentType(MediaType.APPLICATION_JSON)
				.content(json).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.entity").exists()).andExpect(jsonPath("$.entity.id").isNotEmpty())
				.andExpect(jsonPath("$.entity.entryDate").isNotEmpty())
				.andExpect(jsonPath("$.entity.entryHour").isNotEmpty())
				.andExpect(jsonPath("$.entity.departureDate").isEmpty()).andDo(print());
	}

	@Test
	@Sql({ "/delete-parking.sql", "/multiple-parking-inserts.sql" })
	public void verifyDepartureVehicleSuccessfulTest() throws Exception {
		this.parking.setId(this.parkingRepository.findAll().get(0).getId());

		String json = this.mapper.writeValueAsString(this.parking);

		mockMvc.perform(MockMvcRequestBuilders.post("/parking/saveOrUpdate").contentType(MediaType.APPLICATION_JSON)
				.content(json).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.entity").exists()).andExpect(jsonPath("$.entity.id").isNotEmpty())
				.andExpect(jsonPath("$.entity.entryDate").isNotEmpty())
				.andExpect(jsonPath("$.entity.entryHour").isNotEmpty())
				.andExpect(jsonPath("$.entity.departureDate").isNotEmpty())
				.andExpect(jsonPath("$.entity.departureHour").isNotEmpty()).andDo(print());
	}

	@Test
	public void verifySaveWithoutPlaqueTest() throws Exception {
		this.parking.setPlaque("");
		String json = this.mapper.writeValueAsString(this.parking);

		mockMvc.perform(MockMvcRequestBuilders.post("/parking/saveOrUpdate").contentType(MediaType.APPLICATION_JSON)
				.content(json).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.responseCode").value(HttpStatus.NOT_ACCEPTABLE.value()))
				.andExpect(jsonPath("$.message").value(ApplicationMessages.REQUIRED_FIELDS_ARE_EMPTY)).andDo(print());
	}

	@Test
	public void verifySaveWithoutVehicleTypeTest() throws Exception {
		this.parking.setVehicleType(null);
		String json = this.mapper.writeValueAsString(this.parking);

		mockMvc.perform(MockMvcRequestBuilders.post("/parking/saveOrUpdate").contentType(MediaType.APPLICATION_JSON)
				.content(json).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.responseCode").value(HttpStatus.NOT_ACCEPTABLE.value()))
				.andExpect(jsonPath("$.message").value(ApplicationMessages.REQUIRED_FIELDS_ARE_EMPTY)).andDo(print());
	}

	@Test
	public void verifySaveWithNotPermittedVehicleTypeTest() throws Exception {
		this.parking.setVehicleType(new VehicleType(0l, "Camion"));
		String json = this.mapper.writeValueAsString(this.parking);

		mockMvc.perform(MockMvcRequestBuilders.post("/parking/saveOrUpdate").contentType(MediaType.APPLICATION_JSON)
				.content(json).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.responseCode").value(HttpStatus.NOT_ACCEPTABLE.value()))
				.andExpect(jsonPath("$.message").value(ApplicationMessages.VEHICLE_TYPE_NOT_EXISTS)).andDo(print());
	}

	@Test
	@Sql("/multiple-parking-inserts.sql")
	public void verifySaveWithAnExistingPlaqueTest() throws Exception {
		this.parking.setPlaque("H9T6U7");
		String json = this.mapper.writeValueAsString(this.parking);

		mockMvc.perform(MockMvcRequestBuilders.post("/parking/saveOrUpdate").contentType(MediaType.APPLICATION_JSON)
				.content(json).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.responseCode").value(HttpStatus.NOT_ACCEPTABLE.value()))
				.andExpect(jsonPath("$.message").value(ApplicationMessages.PLAQUE_ALREADY_EXISTS)).andDo(print());
	}

	@Test
	@Sql({ "/delete-parking.sql", "/multiple-parking-inserts.sql" })
	public void verifySaveWithoutCapacityForCarTest() throws Exception {
		this.parking.setVehicleType(new VehicleType(2l, "Carro"));
		String json = this.mapper.writeValueAsString(this.parking);

		mockMvc.perform(MockMvcRequestBuilders.post("/parking/saveOrUpdate").contentType(MediaType.APPLICATION_JSON)
				.content(json).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.responseCode").value(HttpStatus.NOT_ACCEPTABLE.value()))
				.andExpect(jsonPath("$.message").value(ApplicationMessages.THERE_IS_NOT_SPACE)).andDo(print());
	}

	@Test
	@Sql({ "/delete-parking.sql", "/multiple-parking-inserts.sql" })
	public void verifySaveWithoutCapacityForMotorcycleTest() throws Exception {
		this.parking.setVehicleType(new VehicleType(1l, "Moto"));
		String json = this.mapper.writeValueAsString(this.parking);

		mockMvc.perform(MockMvcRequestBuilders.post("/parking/saveOrUpdate").contentType(MediaType.APPLICATION_JSON)
				.content(json).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.responseCode").value(HttpStatus.NOT_ACCEPTABLE.value()))
				.andExpect(jsonPath("$.message").value(ApplicationMessages.THERE_IS_NOT_SPACE)).andDo(print());
	}

	@Test
	@Sql({ "/delete-parking.sql" })
	public void verifySaveWithPlaqueRestrictedTest() throws Exception {

		Calendar cal = Calendar.getInstance();
		cal.set(2018, Calendar.MARCH, 4); // Year, month and day of month
		Date date = cal.getTime();
		this.parking.setPlaque("ABC123");
		this.parking.setEntryDate(date);

		String json = this.mapper.writeValueAsString(this.parking);

		mockMvc.perform(MockMvcRequestBuilders.post("/parking/saveOrUpdate").contentType(MediaType.APPLICATION_JSON)
				.content(json).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.responseCode").value(HttpStatus.NOT_ACCEPTABLE.value()))
				.andExpect(jsonPath("$.message").value(ApplicationMessages.VEHICLE_ACCESS_RESTRICTED)).andDo(print());
	}

	@After
	public void finish() {
		this.parking = null;
	}
}
