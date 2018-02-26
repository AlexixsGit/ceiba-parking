package com.ceiba.parking.util;

public class RestResponse {

	private Object entity;
	private Integer responseCode;
	private String message;

	public RestResponse(Integer responseCode) {
		super();
		this.responseCode = responseCode;
	}

	public RestResponse(Integer responseCode, String message) {
		super();
		this.responseCode = responseCode;
		this.message = message;
	}

	public RestResponse(Object entity, Integer responseCode, String message) {
		super();
		this.entity = entity;
		this.responseCode = responseCode;
		this.message = message;
	}

	public Integer getResponseCode() {

		return responseCode;
	}

	public void setResponseCode(Integer responseCode) {
		this.responseCode = responseCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getEntity() {
		return entity;
	}

	public void setEntity(Object entity) {
		this.entity = entity;
	}
}
