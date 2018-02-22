package com.ceiba.parking.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "price")
public class Price extends ParentEntity {

	private static final long serialVersionUID = 5839571799334159690L;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "vehicle_type")
	private VehicleType vehicleType;

	@NotNull
	@Column(name = "hour_price")
	private Double hourPrice;

	@NotNull
	@Column(name = "day_price")
	private Double dayPrice;

	@Column(name = "high_engine_price")
	private Double highEnginePrice;

	public VehicleType getVehicleType() {
		return vehicleType;
	}

	public void setVehicleType(VehicleType vehicleType) {
		this.vehicleType = vehicleType;
	}

	public Double getHourPrice() {
		return hourPrice;
	}

	public void setHourPrice(Double hourPrice) {
		this.hourPrice = hourPrice;
	}

	public Double getDayPrice() {
		return dayPrice;
	}

	public void setDayPrice(Double dayPrice) {
		this.dayPrice = dayPrice;
	}

	public Double getHighEnginePrice() {
		return highEnginePrice;
	}

	public void setHighEnginePrice(Double highEnginePrice) {
		this.highEnginePrice = highEnginePrice;
	}

}
