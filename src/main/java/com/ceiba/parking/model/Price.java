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

	public Price() {

	}

	public Price(VehicleType vehicleType, Double hourPrice, Double dayPrice, Double highEnginePrice) {
		this.vehicleType = vehicleType;
		this.hourPrice = hourPrice;
		this.dayPrice = dayPrice;
		this.highEnginePrice = highEnginePrice;
	}

	public Double getHourPrice() {
		return hourPrice;
	}

	public Double getDayPrice() {
		return dayPrice;
	}

	public Double getHighEnginePrice() {
		return highEnginePrice;
	}

}
