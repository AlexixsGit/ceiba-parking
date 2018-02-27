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
	}

	public InvoiceTestDataBuilder withPlaque(String plaque) {
		this.plaque = plaque;
		return this;
	}

	public InvoiceTestDataBuilder withDescVehicleType(String descVehicleType) {
		this.descVehicleType = descVehicleType;
		return this;
	}

	public InvoiceTestDataBuilder withEngineCapacity(Integer engineCapacity) {
		this.engineCapacity = engineCapacity;
		return this;
	}

	public InvoiceTestDataBuilder withEmployee(String employee) {
		this.employee = employee;
		return this;
	}

	public InvoiceTestDataBuilder withIva(Double iva) {
		this.iva = iva;
		return this;
	}

	public InvoiceTestDataBuilder withAdditionalCost(Double additionalCost) {
		this.additionalCost = additionalCost;
		return this;
	}

	public InvoiceTestDataBuilder withSubtotal(Double subtotal) {
		this.subtotal = subtotal;
		return this;
	}

	public InvoiceTestDataBuilder withTotal(Double total) {
		this.total = total;
		return this;
	}

	public InvoiceTestDataBuilder withCreationDate(Date creationDate) {
		this.creationDate = creationDate;
		return this;
	}

	public Invoice build() {
		return new Invoice(this.plaque, this.descVehicleType, this.engineCapacity, this.employee, this.iva,
				this.additionalCost, this.subtotal, this.total, this.creationDate);
	}
}
