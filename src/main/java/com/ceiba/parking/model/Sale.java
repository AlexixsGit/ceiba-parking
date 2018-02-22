package com.ceiba.parking.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

public class Sale extends ParentEntity {

	private static final long serialVersionUID = -2842742485129007645L;

	@NotNull
	@Column(name = "entry_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date entryDate;

	@NotNull
	@Column(name = "departure_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date departureDate;

	@NotNull
	@Column(name = "total_time", length = 10)
	private String totalTime;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "invoice")
	private Invoice invoice;

	public Date getEntryDate() {
		return entryDate;
	}

	public void setEntryDate(Date entryDate) {
		this.entryDate = entryDate;
	}

	public Date getDepartureDate() {
		return departureDate;
	}

	public void setDepartureDate(Date departureDate) {
		this.departureDate = departureDate;
	}

	public String getTotalTime() {
		return totalTime;
	}

	public void setTotalTime(String totalTime) {
		this.totalTime = totalTime;
	}

	public Invoice getInvoice() {
		return invoice;
	}

	public void setInvoice(Invoice invoice) {
		this.invoice = invoice;
	}
}
