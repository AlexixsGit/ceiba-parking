package com.ceiba.parking.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "invoice")
public class Invoice extends ParentEntity {

	private static final long serialVersionUID = 3894661849543696045L;

	@NotNull
	@Column(name = "plaque", length = 10)
	private String plaque;

	@NotNull
	@Column(name = "desc_vehicle_type", length = 50)
	private String descVehicleType;

	@Column(name = "engine_capacity")
	private Integer engineCapacity;

	@NotNull
	@Column(name = "employee", length = 100)
	private String employee;

	@Column(name = "iva")
	private Double iva;

	@Column(name = "additional_cost")
	private Double additionalCost;

	@NotNull
	@Column(name = "subtotal")
	private Double subtotal;

	@NotNull
	@Column(name = "total")
	private Double total;

	public String getPlaque() {
		return plaque;
	}

	public void setPlaque(String plaque) {
		this.plaque = plaque;
	}

	public String getDescVehicleType() {
		return descVehicleType;
	}

	public void setDescVehicleType(String descVehicleType) {
		this.descVehicleType = descVehicleType;
	}

	public Integer getEngineCapacity() {
		return engineCapacity;
	}

	public void setEngineCapacity(Integer engineCapacity) {
		this.engineCapacity = engineCapacity;
	}

	public String getEmployee() {
		return employee;
	}

	public void setEmployee(String employee) {
		this.employee = employee;
	}

	public Double getIva() {
		return iva;
	}

	public void setIva(Double iva) {
		this.iva = iva;
	}

	public Double getAdditionalCost() {
		return additionalCost;
	}

	public void setAdditionalCost(Double additionalCost) {
		this.additionalCost = additionalCost;
	}

	public Double getSubtotal() {
		return subtotal;
	}

	public void setSubtotal(Double subtotal) {
		this.subtotal = subtotal;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

}