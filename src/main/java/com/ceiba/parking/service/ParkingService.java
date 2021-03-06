package com.ceiba.parking.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.ceiba.parking.model.Parking;
import com.ceiba.parking.model.VehicleType;

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
	 * Valida que pueda ingresar vehiculos con placas de la letra configurada los
	 * dias permitidos
	 * 
	 * @param parking
	 * @return true si puede ingresar
	 */
	boolean validateIfPlaqueIsPermitted(Parking parking);

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

	/**
	 * Valida que el tipo de vehiculo exista
	 * 
	 * @param vehicleType
	 * @return true si el vehiculo existe
	 */
	boolean validateVehicleType(VehicleType vehicleType);

	/**
	 * Obtiene todos los registros del parqueadero
	 * 
	 * @return
	 */
	List<Parking> findAll();

	/**
	 * Obtiene todos los registros por placa
	 * 
	 * @param plaque
	 * @return
	 */
	List<Parking> findByPlaque(String plaque);

}
