package com.ceiba.parking.controllers;

import java.io.IOException;

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

		if (!this.parkingService.validate(parking)) {
			return new RestResponse(HttpStatus.NOT_ACCEPTABLE.value(),
					"Los campos obligatorios no estan diligenciados");
		}
		if (!this.parkingService.validateVehicleType(parking.getVehicleType())) {
			return new RestResponse(HttpStatus.NOT_ACCEPTABLE.value(), "El tipo de vehiculo no existe");
		}
		if (this.parkingService.validateIfPlaqueExist(parking)) {
			return new RestResponse(HttpStatus.NOT_ACCEPTABLE.value(),
					"La placa " + parking.getPlaque() + " ya se encuentra registrada");
		}
		if (!this.parkingService.validateCapacity(parking)) {
			return new RestResponse(HttpStatus.NOT_ACCEPTABLE.value(), "No hay cupos disponibles");
		}

		parking = this.parkingService.complete(parking);

		if (this.parkingService.validateIfPlaqueIsRestricted(parking)) {
			return new RestResponse(HttpStatus.NOT_ACCEPTABLE.value(),
					"La placa " + parking.getPlaque() + " no puede ingresar");
		}
		try {
			parking = this.parkingService.save(parking);
		} catch (Exception e) {
			return new RestResponse(HttpStatus.CONFLICT.value(), "Error interno del sistema");
		}

		return new RestResponse(parking, HttpStatus.OK.value(), "Operacion exitosa");
	}
}
