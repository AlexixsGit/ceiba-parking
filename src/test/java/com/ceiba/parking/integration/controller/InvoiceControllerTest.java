package com.ceiba.parking.integration.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Calendar;
import java.util.Date;

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
public class InvoiceControllerTest {

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

		Calendar calendar = Calendar.getInstance();
		calendar.set(2018, 02, 27, 11, 00);
		Date entryDate = calendar.getTime();

		calendar = Calendar.getInstance();
		calendar.set(2018, 02, 28, 12, 10);
		Date departureDate = calendar.getTime();

		this.parking = new Parking(vehicleType, "P3D3W8", entryDate, "11:00", departureDate, "12:10", 200);
	}

	@Test
	@Sql({ "/delete-parking.sql", "/multiple-parking-inserts.sql" })
	public void verifySaveSuccessfulForMotoHighEngineTest() throws Exception {
		this.parking.setEngineCapacity(1000);
		String json = this.mapper.writeValueAsString(this.parking);

		mockMvc.perform(MockMvcRequestBuilders.post("/invoice/saveOrUpdate").contentType(MediaType.APPLICATION_JSON)
				.content(json).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.entity").exists()).andExpect(jsonPath("$.entity.id").isNotEmpty())
				.andExpect(jsonPath("$.entity.plaque").isNotEmpty())
				.andExpect(jsonPath("$.entity.descVehicleType").isNotEmpty())
				.andExpect(jsonPath("$.entity.additionalCost").isNotEmpty())
				.andExpect(jsonPath("$.entity.subtotal").isNotEmpty())
				.andExpect(jsonPath("$.entity.total").isNotEmpty())
				.andExpect(jsonPath("$.entity.creationDate").isNotEmpty()).andDo(print());
	}

	@Test
	@Sql({ "/delete-parking.sql", "/multiple-parking-inserts.sql" })
	public void verifySaveSuccessfulForMotoLowEngineTest() throws Exception {
		String json = this.mapper.writeValueAsString(this.parking);

		mockMvc.perform(MockMvcRequestBuilders.post("/invoice/saveOrUpdate").contentType(MediaType.APPLICATION_JSON)
				.content(json).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.entity").exists()).andExpect(jsonPath("$.entity.id").isNotEmpty())
				.andExpect(jsonPath("$.entity.plaque").isNotEmpty())
				.andExpect(jsonPath("$.entity.descVehicleType").isNotEmpty())
				.andExpect(jsonPath("$.entity.additionalCost").value(0.0))
				.andExpect(jsonPath("$.entity.subtotal").isNotEmpty())
				.andExpect(jsonPath("$.entity.total").isNotEmpty())
				.andExpect(jsonPath("$.entity.creationDate").isNotEmpty()).andDo(print());
	}

	@Test
	public void verifySaveWithoutPlaqueTest() throws Exception {
		this.parking.setPlaque("");
		String json = this.mapper.writeValueAsString(this.parking);

		mockMvc.perform(MockMvcRequestBuilders.post("/invoice/saveOrUpdate").contentType(MediaType.APPLICATION_JSON)
				.content(json).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.responseCode").value(HttpStatus.NOT_ACCEPTABLE.value()))
				.andExpect(jsonPath("$.message").value(ApplicationMessages.REQUIRED_FIELDS_ARE_EMPTY)).andDo(print());
	}

	@Test
	public void verifySaveWithoutVehicleTypeTest() throws Exception {
		this.parking.setVehicleType(null);
		String json = this.mapper.writeValueAsString(this.parking);

		mockMvc.perform(MockMvcRequestBuilders.post("/invoice/saveOrUpdate").contentType(MediaType.APPLICATION_JSON)
				.content(json).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.responseCode").value(HttpStatus.NOT_ACCEPTABLE.value()))
				.andExpect(jsonPath("$.message").value(ApplicationMessages.REQUIRED_FIELDS_ARE_EMPTY)).andDo(print());
	}

	@Test
	@Sql("/multiple-parking-inserts.sql")
	public void verifySaveWithNotExistingPlaqueTest() throws Exception {
		this.parking.setPlaque("H9T6U7111");
		String json = this.mapper.writeValueAsString(this.parking);

		mockMvc.perform(MockMvcRequestBuilders.post("/invoice/saveOrUpdate").contentType(MediaType.APPLICATION_JSON)
				.content(json).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.responseCode").value(HttpStatus.NOT_ACCEPTABLE.value()))
				.andExpect(jsonPath("$.message").value(ApplicationMessages.PLAQUE_NOT_EXISTS)).andDo(print());
	}

}
