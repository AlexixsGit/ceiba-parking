package com.ceiba.parking.controllers;

import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ceiba.parking.model.Invoice;
import com.ceiba.parking.model.Parking;
import com.ceiba.parking.service.InvoiceService;
import com.ceiba.parking.util.ApplicationMessages;
import com.ceiba.parking.util.RestResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class InvoiceController {

	@Autowired
	protected InvoiceService invoiceService;
	protected ObjectMapper mapper;

	@RequestMapping(value = "/invoice/saveOrUpdate", consumes = MediaType.APPLICATION_JSON, method = RequestMethod.POST)
	public RestResponse saveOrUpdate(@RequestBody String json) {

		this.mapper = new ObjectMapper();
		Invoice invoice = new Invoice();

		try {
			Parking parking = this.mapper.readValue(json, Parking.class);

			if (!this.invoiceService.validate(parking)) {
				return new RestResponse(HttpStatus.NOT_ACCEPTABLE.value(),
						ApplicationMessages.REQUIRED_FIELDS_ARE_EMPTY);
			}

			if (!this.invoiceService.validateIfParkingIdExists(parking.getId())) {
				return new RestResponse(HttpStatus.NOT_ACCEPTABLE.value(),
						ApplicationMessages.VEHICLE_NOT_PARKED);
			}
			if (!this.invoiceService.validateIfPlaqueExists(parking.getPlaque())) {
				return new RestResponse(HttpStatus.NOT_ACCEPTABLE.value(), ApplicationMessages.PLAQUE_NOT_EXISTS);
			}
			invoice = this.invoiceService.complete(parking, invoice);
			this.invoiceService.save(invoice);
		} catch (Exception e) {
			return new RestResponse(HttpStatus.CONFLICT.value(), ApplicationMessages.INTERNAL_ERROR);
		}

		return new RestResponse(invoice, HttpStatus.OK.value(), ApplicationMessages.SUCCESS_OPERATION);
	}
}
