package com.ceiba.parking.controllers;

import java.io.IOException;

import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ceiba.parking.model.Parking;
import com.ceiba.parking.service.ParkingService;
import com.ceiba.parking.util.ApplicationMessages;
import com.ceiba.parking.util.QueryResult;
import com.ceiba.parking.util.RestResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class ParkingController {

	@Autowired
	protected ParkingService parkingService;

	protected ObjectMapper mapper;

	@RequestMapping(value = "/parking/saveOrUpdate", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON)
	public RestResponse saveOrUpdate(@RequestBody String json) throws IOException {
		this.mapper = new ObjectMapper();

		Parking parking = this.mapper.readValue(json, Parking.class);

		if (!this.parkingService.validate(parking)) {
			return new RestResponse(HttpStatus.NOT_ACCEPTABLE.value(), ApplicationMessages.REQUIRED_FIELDS_ARE_EMPTY);
		}
		if (!this.parkingService.validateVehicleType(parking.getVehicleType())) {
			return new RestResponse(HttpStatus.NOT_ACCEPTABLE.value(), ApplicationMessages.VEHICLE_TYPE_NOT_EXISTS);
		}
		if (this.parkingService.validateIfPlaqueExist(parking)) {
			return new RestResponse(HttpStatus.NOT_ACCEPTABLE.value(), ApplicationMessages.PLAQUE_ALREADY_EXISTS);
		}
		if (!this.parkingService.validateCapacity(parking)) {
			return new RestResponse(HttpStatus.NOT_ACCEPTABLE.value(), ApplicationMessages.THERE_IS_NOT_SPACE);
		}
		parking = this.parkingService.complete(parking);

		if (this.parkingService.validateIfPlaqueIsRestricted(parking)) {
			return new RestResponse(HttpStatus.NOT_ACCEPTABLE.value(), ApplicationMessages.VEHICLE_ACCESS_RESTRICTED);
		}
		try {
			parking = this.parkingService.save(parking);
		} catch (Exception e) {
			return new RestResponse(HttpStatus.CONFLICT.value(), ApplicationMessages.INTERNAL_ERROR);
		}

		return new RestResponse(parking, HttpStatus.OK.value(), ApplicationMessages.SUCCESS_OPERATION);
	}

	@RequestMapping(value = "/parking/getParkingList", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	public QueryResult<Parking> getParkingList() {
		return new QueryResult<>(this.parkingService.findAll());
	}

	@RequestMapping(value = "/parking/getParkingListByPlaque", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
	public QueryResult<Parking> getParkingListByPlaque(@RequestParam("plaque") String plaque) {
		return new QueryResult<>(this.parkingService.findByPlaque(plaque));
	}
}
