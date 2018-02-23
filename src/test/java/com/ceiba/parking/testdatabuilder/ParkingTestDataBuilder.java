package com.ceiba.parking.testdatabuilder;

import java.util.Date;

import com.ceiba.parking.model.Parking;
import com.ceiba.parking.model.VehicleType;

public class ParkingTestDataBuilder {

	private VehicleType vehicleType;
	private String plaque;
	private Date entryDate;
	private String entryHour;
	private Date departureDate;
	private String departureHour;
	private Integer engineCapacity;

	public ParkingTestDataBuilder() {
		this.vehicleType = new VehicleType(1l, "Moto");
		this.plaque = "CIH123";
		this.entryDate = new Date();
		this.entryHour = "8:36";
		this.departureDate = null;
		this.departureHour = "";
		this.engineCapacity = 200;
	}

	public ParkingTestDataBuilder withVehicleType(VehicleType vehicleType) {
		this.vehicleType = vehicleType;
		return this;
	}

	public ParkingTestDataBuilder withPlaque(String plaque) {
		this.plaque = plaque;
		return this;
	}

	public ParkingTestDataBuilder withEntryDate(Date entryDate) {
		this.entryDate = entryDate;
		return this;
	}

	public ParkingTestDataBuilder withEntryHour(String entryHour) {
		this.entryHour = entryHour;
		return this;
	}

	public ParkingTestDataBuilder withDepartureDate(Date departureDate) {
		this.departureDate = departureDate;
		return this;
	}

	public ParkingTestDataBuilder withDepartureHour(String departureHour) {
		this.departureHour = departureHour;
		return this;
	}

	public ParkingTestDataBuilder withEngineCapacity(Integer engineCapacity) {
		this.engineCapacity = engineCapacity;
		return this;
	}

	public Parking build() {
		return new Parking(this.vehicleType, this.plaque, this.entryDate, this.entryHour, this.departureDate,
				this.departureHour, this.engineCapacity);
	}
}
