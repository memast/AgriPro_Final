package com.agripro.gwt.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.agripro.gwt.client.Data;
import com.agripro.gwt.client.DataService;
import java.util.ArrayList;

public class DataServiceImpl extends RemoteServiceServlet implements DataService {

	private static final long serialVersionUID = 1L;
	private ImportProduction productionData;
	private ImportTrade tradeData;
	private ImportPopulation populationData;

	public Data getData(String input) {
		if (input.equals("production")) {
			// production mode

			// check whether we need to import data or if already imported
			if (productionData == null) {
				// import data
				productionData = new ImportProduction();
			}

			// store data
			Data data = new Data();
			data.setData(productionData.getRawData());

			// return data
			return data;
		} else if (input.equals("trade")) {
			// trade mode

			// check whether we need to import data or if already imported
			if (tradeData == null) {
				// import data
				tradeData = new ImportTrade();
			}

			// store data
			Data data = new Data();
			data.setData(tradeData.getRawData());

			// return data
			return data;

		} else if (input.equals("population")) {
			// population mode

			// check whether we need to import data or if already imported
			if (populationData == null) {
				// import data
				populationData = new ImportPopulation();
			}

			// store data
			Data data = new Data();
			data.setData(populationData.getRawData());

			// return data
			return data;
		} else {
			// --> unknown mode
			System.out.println("ERROR: input must be either 'production', 'trade' or 'population'. Your input was: '"+ input + "'.");
			Data data = new Data();
			return data;
		}
	}
}