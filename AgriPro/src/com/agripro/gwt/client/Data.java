package com.agripro.gwt.client;

import java.io.Serializable;
import java.util.ArrayList;

public class Data implements Serializable {

	private static final long serialVersionUID = 1L;

	public Data() {
	};

	private ArrayList dataArray = new ArrayList();

	public void setData(ArrayList dataArray) {
		this.dataArray = dataArray;
	}

	public ArrayList getData() {
		return dataArray;
	}
}