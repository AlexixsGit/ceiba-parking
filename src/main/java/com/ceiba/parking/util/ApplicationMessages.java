package com.ceiba.parking.util;

public class ApplicationMessages {

	// SYSTEM MESSAGES
	public static final String INTERNAL_ERROR = "Error interno del sistema";
	public static final String SUCCESS_OPERATION = "Operacion exitosa";

	// PARKING MESSAGES
	public static final String REQUIRED_FIELDS_ARE_EMPTY = "Los campos obligatorios no estan diligenciados";
	public static final String VEHICLE_TYPE_NOT_EXISTS = "El tipo de vehiculo no existe";
	public static final String PLAQUE_ALREADY_EXISTS = "La placa ya se encuentra registrada";
	public static final String THERE_IS_NOT_SPACE = "No hay cupos disponibles";
	public static final String VEHICLE_ACCESS_RESTRICTED = "El vehiculo no puede ingresar";

	// INVOICE MESSAGES
	public static final String PLAQUE_NOT_EXISTS = "La placa ingresada no existe";
	public static final String VEHICLE_NOT_PARKED = "El vehiculo no se encuentra en el parqueadero";

	private ApplicationMessages() {
	}
}
