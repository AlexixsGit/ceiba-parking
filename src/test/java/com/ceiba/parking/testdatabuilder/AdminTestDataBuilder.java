package com.ceiba.parking.testdatabuilder;

import com.ceiba.parking.model.Admin;
import com.ceiba.parking.model.VehicleType;

public class AdminTestDataBuilder {

	private Integer hoursForADay;
	private Integer capacity;
	private VehicleType vehicleType;
	private Integer engineCapacity;
	private String restrictPlaqueLetter;

	public AdminTestDataBuilder() {
		this.hoursForADay = 9;
		this.capacity = 10;
		this.vehicleType = new VehicleType(1l, "Moto");
		this.engineCapacity = 200;
		this.restrictPlaqueLetter = "A";
	}

	public AdminTestDataBuilder withHoursForADay(Integer hoursForADay) {
		this.hoursForADay = hoursForADay;
		return this;
	}

	public AdminTestDataBuilder withCapacity(Integer capacity) {
		this.capacity = capacity;
		return this;
	}

	public AdminTestDataBuilder withVehicleType(VehicleType vehicleType) {
		this.vehicleType = vehicleType;
		return this;
	}

	public AdminTestDataBuilder withEngineCapacity(Integer engineCapacity) {
		this.engineCapacity = engineCapacity;
		return this;
	}

	public AdminTestDataBuilder withRestrictPlaqueLetter(String restrictPlaqueLetter) {
		this.restrictPlaqueLetter = restrictPlaqueLetter;
		return this;
	}

	public Admin build() {
		return new Admin(this.hoursForADay, this.capacity, this.vehicleType, this.engineCapacity,
				this.restrictPlaqueLetter);
	}

}
