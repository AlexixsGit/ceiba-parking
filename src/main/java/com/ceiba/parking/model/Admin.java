package com.ceiba.parking.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "admin")
public class Admin extends ParentEntity {

	private static final long serialVersionUID = 3894661849543696045L;

	@Column(name = "hours_for_a_day")
	private Integer hoursForADay;

	@Column(name = "cars_capacity")
	private Integer carsCapacity;

	@Column(name = "moto_capacity")
	private Integer motoCapacity;

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

	public Integer getCarsCapacity() {
		return carsCapacity;
	}

	public void setCarsCapacity(Integer carsCapacity) {
		this.carsCapacity = carsCapacity;
	}

	public Integer getMotoCapacity() {
		return motoCapacity;
	}

	public void setMotoCapacity(Integer motoCapacity) {
		this.motoCapacity = motoCapacity;
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

}
