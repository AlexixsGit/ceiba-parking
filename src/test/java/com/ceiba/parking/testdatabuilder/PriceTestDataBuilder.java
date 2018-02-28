package com.ceiba.parking.testdatabuilder;

import com.ceiba.parking.model.Price;
import com.ceiba.parking.model.VehicleType;
import com.ceiba.parking.util.Constants;

public class PriceTestDataBuilder {

	private VehicleType vehicleType;
	private Double hourPrice;
	private Double dayPrice;
	private Double highEnginePrice;

	public PriceTestDataBuilder() {
		this.vehicleType = new VehicleType(1l, Constants.MOTORCYCLE);
		this.hourPrice = 500d;
		this.dayPrice = 4000d;
		this.highEnginePrice = 2000d;
	}

	public Price build() {
		return new Price(this.vehicleType, this.hourPrice, this.dayPrice, this.highEnginePrice);
	}
}
