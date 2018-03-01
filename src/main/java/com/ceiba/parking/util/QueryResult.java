package com.ceiba.parking.util;

import java.util.List;

public class QueryResult<T> {

	private int totalRecords;
	private List<T> list;

	public QueryResult(List<T> list) {
		this.list = list;
		this.totalRecords = list.size();
	}

	public int getTotalRecords() {
		return totalRecords;
	}

	public List<T> getList() {
		return list;
	}
}