package com.ceiba.parking.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "parking")
public class Parking extends ParentEntity {

	private static final long serialVersionUID = 3894661849543696045L;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "vehicle_type")
	private VehicleType vehicleType;

	@NotNull
	@Column(name = "plaque", length = 10)
	private String plaque;

	@NotNull
	@Column(name = "entry_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date entryDate;

	@NotNull
	@Column(name = "entry_hour", length = 10)
	private String entryHour;

	@Column(name = "departure_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date departureDate;

	@Column(name = "departure_hour", length = 10)
	private String departureHour;

	@Column(name = "engine_capacity")
	private Integer engineCapacity;

	@Transient
	private boolean isNew;

	public VehicleType getVehicleType() {
		return vehicleType;
	}

	public void setVehicleType(VehicleType vehicleType) {
		this.vehicleType = vehicleType;
	}

	public String getPlaque() {
		return plaque;
	}

	public void setPlaque(String plaque) {
		this.plaque = plaque;
	}

	public Date getEntryDate() {
		return entryDate;
	}

	public void setEntryDate(Date entryDate) {
		this.entryDate = entryDate;
	}

	public String getEntryHour() {
		return entryHour;
	}

	public void setEntryHour(String entryHour) {
		this.entryHour = entryHour;
	}

	public Date getDepartureDate() {
		return departureDate;
	}

	public void setDepartureDate(Date departureDate) {
		this.departureDate = departureDate;
	}

	public String getDepartureHour() {
		return departureHour;
	}

	public void setDepartureHour(String departureHour) {
		this.departureHour = departureHour;
	}

	public Integer getEngineCapacity() {
		return engineCapacity;
	}

	public void setEngineCapacity(Integer engineCapacity) {
		this.engineCapacity = engineCapacity;
	}

	public boolean isNew() {
		return this.getId() == null;
	}
}
