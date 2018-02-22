package com.ceiba.parking.service;

import java.util.List;

import com.ceiba.parking.model.Parking;

public interface ParkingService {

	/**
	 * Metodo que guarda o actualiza un registro de vehiculos
	 * 
	 * @param parking
	 */
	void save(Parking parking);

	/**
	 * 
	 * Devuelve los registros que esten en el parqueadero
	 * 
	 * @param vehicleTypeId
	 * @return
	 */
	List<Parking> findAllByType(Long vehicleTypeId);
}
