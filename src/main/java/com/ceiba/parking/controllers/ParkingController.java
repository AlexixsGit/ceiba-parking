package com.ceiba.parking.controllers;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ceiba.parking.model.Parking;
import com.ceiba.parking.service.ParkingService;
import com.ceiba.parking.util.RestResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class ParkingController {

	@Autowired
	protected ParkingService parkingService;

	protected ObjectMapper mapper;

	@RequestMapping(value = "/parking/saveOrUpdate", method = RequestMethod.POST)
	public RestResponse saveOrUpdate(@RequestBody String json) throws IOException {
		this.mapper = new ObjectMapper();

		Parking parking = this.mapper.readValue(json, Parking.class);

		if (!this.validate(parking)) {
			return new RestResponse(HttpStatus.NOT_ACCEPTABLE.value(),
					"Los campos obligatorios no estan diligenciados");
		}
		this.complete(parking);
		try {
			this.parkingService.save(parking);
		} catch (Exception e) {
			return new RestResponse(HttpStatus.CONFLICT.value(), "Error interno del sistema");
		}

		return new RestResponse(HttpStatus.OK.value(), "Operacion exitosa");
	}

	private boolean validate(Parking parking) {
		boolean isValid = true;

		if (parking.getVehicleType() == null || parking.getVehicleType().getId() == null) {
			isValid = false;
		}

		if (StringUtils.trimToNull(parking.getPlaque()) == null) {
			isValid = false;
		}

		return isValid;
	}

	private void complete(Parking parking) {
		Date date = new Date();
		Calendar calendar = GregorianCalendar.getInstance();
		calendar.setTime(date);

		int hours = calendar.get(Calendar.HOUR);
		int minutes = calendar.get(Calendar.MINUTE);

		if (parking.getId() == null) {
			parking.setEntryDate(date);
			parking.setEntryHour(hours + ":" + minutes);
		} else {
			parking.setDepartureDate(date);
			parking.setDepartureHour(hours + ":" + minutes);
		}
	}

}
