package com.ceiba.parking.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Table(name = "vehicle_type")
public class VehicleType extends ParentEntity {

	private static final long serialVersionUID = -7550335338465069059L;

	@NotNull
	@NotBlank
	@Column(name = "description", length = 50)
	private String description;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
