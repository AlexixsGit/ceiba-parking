package com.ceiba.parking.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.ceiba.parking.model.Invoice;
import com.ceiba.parking.model.Parking;

@Transactional
public interface InvoiceService {

	/**
	 * Valida los campos obligatorios
	 * 
	 * @param parking
	 * @return true si todos los campos estan diligenciados
	 */
	boolean validate(Parking parking);

	/**
	 * Completa la informacion de la factura
	 * 
	 * @param parking
	 * @return
	 */
	Invoice complete(Parking parking);

	/**
	 * Calcular costo adicional
	 * 
	 * @param parking
	 * @return
	 */
	Double calculateAdditionalCost(Parking parking);

	/**
	 * Calcular el subtotal de la factura
	 * 
	 * @param parking
	 * @return
	 */
	Double calculateSubtotal(Parking parking);

	/**
	 * Devuelve los tiempos en dias, horas y minutos de la resta de dos fechas
	 * 
	 * @param parking
	 * @return
	 */
	List<Long> getTimes(Parking parking);

	/**
	 * Metodo que se encarga de guardar la factura
	 * 
	 * @param invoice
	 * @return
	 */
	Invoice save(Invoice invoice);

	/**
	 * Valida que la placa exista
	 * 
	 * @return true si la placa existe
	 */
	boolean validateIfPlaqueExists(String plaque);

}
