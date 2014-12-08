package com.agripro.gwt.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("data")
public interface DataService extends RemoteService {
	// Get meta data
	Data getMetaData(int requestID, String selection, String year, String country);
	// Get data
	Data getData(int requestID, String selection, String year, String country, String seed);
}