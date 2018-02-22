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

	public Integer getHoursForADay() {
		return hoursForADay;
	}

	public void setHoursForADay(Integer hoursForADay) {
		this.hoursForADay = hoursForADay;
	}

	public Integer getEngineCapacity() {
		return engineCapacity;
	}

	public void setEngineCapacity(Integer engineCapacity) {
		this.engineCapacity = engineCapacity;
	}

	public String getRestrictPlaqueLetter() {
		return restrictPlaqueLetter;
	}

	public void setRestrictPlaqueLetter(String restrictPlaqueLetter) {
		this.restrictPlaqueLetter = restrictPlaqueLetter;
	}

	public Integer getCapacity() {
		return capacity;
	}

	public void setCapacity(Integer capacity) {
		this.capacity = capacity;
	}

	public VehicleType getVehicleType() {
		return vehicleType;
	}

	public void setVehicleType(VehicleType vehicleType) {
		this.vehicleType = vehicleType;
	}

}
