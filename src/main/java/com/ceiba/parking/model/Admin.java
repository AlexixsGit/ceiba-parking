package com.ceiba.parking.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "admin")
public class Admin extends ParentEntity {

	private static final long serialVersionUID = 3894661849543696045L;

	@Column(name = "hours_for_a_day")
	private Integer hoursForADay;

	@Column(name = "capacity")
	private Integer capacity;

	@NotNull
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "vehicle_type")
	private VehicleType vehicleType;

	@Column(name = "engine_capacity")
	private Integer engineCapacity;

	@Column(name = "restrict_plaque_letter", length = 1)
	private String restrictPlaqueLetter;

	public Admin() {

	}

	public Admin(Integer hoursForADay, Integer capacity, VehicleType vehicleType, Integer engineCapacity,
			String restrictPlaqueLetter) {
		this.hoursForADay = hoursForADay;
		this.capacity = capacity;
		this.vehicleType = vehicleType;
		this.engineCapacity = engineCapacity;
		this.restrictPlaqueLetter = restrictPlaqueLetter;
	}

	public Integer getHoursForADay() {
		return hoursForADay;
	}

	public Integer getEngineCapacity() {
		return engineCapacity;
	}

	public String getRestrictPlaqueLetter() {
		return restrictPlaqueLetter;
	}

	public Integer getCapacity() {
		return capacity;
	}
}
