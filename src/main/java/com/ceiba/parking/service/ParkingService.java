package com.ceiba.parking.service;

import org.springframework.transaction.annotation.Transactional;

import com.ceiba.parking.model.Parking;

@Transactional
public interface ParkingService {

	/**
	 * Metodo que guarda o actualiza un registro de vehiculos
	 * 
	 * @param parking
	 */
	Parking save(Parking parking);

	/**
	 * Valida el cupo
	 * 
	 * @param parking
	 * @return verdadero si tiene cupos disponibles
	 */
	boolean validateCapacity(Parking parking);

	/**
	 * Valida que no pueda ingresar vehiculos con placas de la letra configurada
	 * 
	 * @param parking
	 * @return true si no puede ingresar
	 */
	boolean validateIfPlaqueIsRestricted(Parking parking);

	/**
	 * Valida campos obligatorios
	 * 
	 * @param parking
	 * @return
	 */
	boolean validate(Parking parking);

	/**
	 * Completa la informacion necesaria
	 * 
	 * @param parking
	 */
	Parking complete(Parking parking);

	/**
	 * Metodo que valida si un vehiculo por la placa ya existe siempre y cuando se
	 * este tratando de ingresar uno nuevo
	 * 
	 * @return
	 */
	boolean validateIfPlaqueExist(Parking parking);

}
