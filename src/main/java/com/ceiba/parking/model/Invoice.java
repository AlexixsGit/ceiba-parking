package com.ceiba.parking.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
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

	@NotNull
	@Column(name = "creation_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date creationDate;

	@NotNull
	@Column(name = "entry_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date entryDate;

	@NotNull
	@Column(name = "departure_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date departureDate;

	@Column(name = "total_time", length = 10)
	private Long totalTime;

	public Invoice() {

	}

	public Invoice(String plaque, String descVehicleType, Integer engineCapacity, String employee, Double iva,
			Double additionalCost, Double subtotal, Double total, Date creationDate, Date entryDate, Date departureDate,
			Long totalTime) {
		this.plaque = plaque;
		this.descVehicleType = descVehicleType;
		this.engineCapacity = engineCapacity;
		this.employee = employee;
		this.iva = iva;
		this.additionalCost = additionalCost;
		this.subtotal = subtotal;
		this.total = total;
		this.creationDate = creationDate;
		this.entryDate = entryDate;
		this.departureDate = departureDate;
		this.totalTime = totalTime;
	}

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

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Date getEntryDate() {
		return entryDate;
	}

	public void setEntryDate(Date entryDate) {
		this.entryDate = entryDate;
	}

	public Date getDepartureDate() {
		return departureDate;
	}

	public void setDepartureDate(Date departureDate) {
		this.departureDate = departureDate;
	}

	public Long getTotalTime() {
		return totalTime;
	}

	public void setTotalTime(Long totalTime) {
		this.totalTime = totalTime;
	}

}
