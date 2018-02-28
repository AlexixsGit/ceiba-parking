package com.ceiba.parking.testdatabuilder;

import com.ceiba.parking.model.VehicleType;
import com.ceiba.parking.util.Constants;

public class VehicleTypeTestDataBuilder {

	private String description;

	public VehicleTypeTestDataBuilder() {
		this.description = Constants.MOTORCYCLE;
	}

	public VehicleType build() {
		return new VehicleType(this.description);
	}
}
