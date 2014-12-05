package com.agripro.gwt.client;

import java.io.Serializable;
import java.util.ArrayList;

public class Data implements Serializable {
	private static final long serialVersionUID = 1L;
	private String type;
	private String meta;
	private int requestID;
	
	public Data() {
	};

	private ArrayList dataArray = new ArrayList();

	public void setType(String type) {
		this.type = type;
	}
	public String getType() {
		return type;
	}
	
	public String getMeta() {
		return meta;
	}
	public void setMeta(String meta) {
		this.meta = meta;
	}

	public int getRequestID() {
		return requestID;
	}
	public void setRequestID(int requestID) {
		this.requestID = requestID;
	}
	
	public void setData(ArrayList array) {
		this.dataArray = array;
	}
	public ArrayList getData() {
		return dataArray;
	}
	
}