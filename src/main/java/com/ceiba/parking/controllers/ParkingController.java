package com.ceiba.parking.controllers;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ceiba.parking.model.Admin;
import com.ceiba.parking.model.Parking;
import com.ceiba.parking.service.AdminService;
import com.ceiba.parking.service.ParkingService;
import com.ceiba.parking.util.RestResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class ParkingController {

	@Autowired
	protected ParkingService parkingService;

	@Autowired
	protected AdminService adminService;

	protected ObjectMapper mapper;

	protected Admin admin;

	private Calendar calendar;

	@RequestMapping(value = "/parking/saveOrUpdate", method = RequestMethod.POST)
	public RestResponse saveOrUpdate(@RequestBody String json) throws IOException {
		this.mapper = new ObjectMapper();
		calendar = GregorianCalendar.getInstance();
		calendar.setTime(new Date());

		Parking parking = this.mapper.readValue(json, Parking.class);

		if (!this.validate(parking)) {
			return new RestResponse(HttpStatus.NOT_ACCEPTABLE.value(),
					"Los campos obligatorios no estan diligenciados");
		}
		if (!this.validateCapacity(parking.getVehicleType().getId())) {
			return new RestResponse(HttpStatus.NOT_ACCEPTABLE.value(), "No hay cupos disponibles");
		}
		if (this.validateRestrictedPlaque(parking)) {
			return new RestResponse(HttpStatus.NOT_ACCEPTABLE.value(),
					"La placa " + parking.getPlaque() + " no puede ingresar");
		}
		this.complete(parking);
		try {
			this.parkingService.save(parking);
		} catch (Exception e) {
			return new RestResponse(HttpStatus.CONFLICT.value(), "Error interno del sistema");
		}

		return new RestResponse(HttpStatus.OK.value(), "Operacion exitosa");
	}

	/**
	 * Valida el cupo
	 * 
	 * @param vehicleTypeId
	 * @return
	 */
	private boolean validateCapacity(Long vehicleTypeId) {

		this.admin = this.adminService.findByVehicleType(vehicleTypeId);

		List<Parking> parkingList = this.parkingService.findAllByType(vehicleTypeId);
		return parkingList.size() < this.admin.getCapacity();
	}

	/**
	 * Valida que no pueda ingresar vehiculos con placas de la letra configurada
	 * 
	 * @param parking
	 * @return true si no puede ingresar
	 */
	private boolean validateRestrictedPlaque(Parking parking) {
		int day = calendar.get(Calendar.DAY_OF_WEEK);

		return parking.getPlaque().substring(0, 1).toUpperCase()
				.contains(this.admin.getRestrictPlaqueLetter().toUpperCase())
				&& (day == Calendar.SUNDAY || day == Calendar.MONDAY);
	}

	/**
	 * Valida campos obligatorios
	 * 
	 * @param parking
	 * @return
	 */
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

	/**
	 * Completa la informacion necesaria
	 * 
	 * @param parking
	 */
	private void complete(Parking parking) {

		int hours = calendar.get(Calendar.HOUR);
		int minutes = calendar.get(Calendar.MINUTE);

		if (parking.getId() == null) {
			parking.setEntryDate(new Date());
			parking.setEntryHour(hours + ":" + minutes);
		} else {
			parking.setDepartureDate(new Date());
			parking.setDepartureHour(hours + ":" + minutes);
		}
	}

	// TODO: Crear metodo para validar que la placa no este estacionada en el
	// momento

}
