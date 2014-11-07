package com.agripro.gwt.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface DataServiceAsync {
   void getData(String input, AsyncCallback<Data> callback);
}