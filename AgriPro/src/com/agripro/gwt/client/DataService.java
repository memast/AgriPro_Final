package com.agripro.gwt.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("data")
public interface DataService extends RemoteService {
	Data getMetaData(int requestID, String selection, String year, String country);
	Data getData(int requestID, String selection, String year, String country, String seed);
}