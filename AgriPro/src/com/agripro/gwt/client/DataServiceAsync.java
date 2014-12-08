package com.agripro.gwt.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface DataServiceAsync {
	// get meta data
	void getMetaData(int requestID, String selection, String year, String country, AsyncCallback<Data> callback);
	// get data
	void getData(int requestID, String selection, String year, String country, String seed, AsyncCallback<Data> callback);
}