package com.ceiba.parking.testdatabuilder;

import java.util.Date;

import com.ceiba.parking.model.Invoice;
import com.ceiba.parking.util.Constants;

public class InvoiceTestDataBuilder {

	private String plaque;
	private String descVehicleType;
	private Integer engineCapacity;
	private String employee;
	private Double iva;
	private Double additionalCost;
	private Double subtotal;
	private Double total;
	private Date creationDate;
	private Date entryDate;
	private Date departureDate;
	private Long totalTime;

	public InvoiceTestDataBuilder() {
		this.plaque = "CIH123";
		this.descVehicleType = "Moto";
		this.engineCapacity = 200;
		this.employee = Constants.EMPLOYEE;
		this.iva = 0d;
		this.additionalCost = 0d;
		this.subtotal = 500d;
		this.total = 500d;
		this.creationDate = new Date();
		this.entryDate = new Date();
		this.departureDate = new Date();
		this.totalTime = 2l;
	}

	public Invoice build() {
		return new Invoice(this.plaque, this.descVehicleType, this.engineCapacity, this.employee, this.iva,
				this.additionalCost, this.subtotal, this.total, this.creationDate, this.entryDate, this.departureDate,
				this.totalTime);
	}
}
