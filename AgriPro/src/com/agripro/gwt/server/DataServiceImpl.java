package com.agripro.gwt.server;

import com.google.appengine.api.utils.SystemProperty;

import java.io.*;
import java.sql.*;

import javax.servlet.http.*;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.agripro.gwt.client.Data;
import com.agripro.gwt.client.DataService;

import java.util.ArrayList;
public class DataServiceImpl extends RemoteServiceServlet implements DataService {

	// Variables for meta data & data import
	private static final long serialVersionUID = 1L;
	private ImportProduction productionData;
	private ImportTrade tradeData;
	private ImportPopulation populationData;
	private metaImportProduction metaProductionData;
	private metaImportTrade metaTradeData;
	private metaImportPopulation metaPopulationData;
	

	// getMetaData
	//
	// requestID: the ID of this request. required.
	// selection: the desired selection (production, import, export, population). required.
	// year: the desired year. required.
	// country: the desired country. required.
	//
	// returns a data object containing meta data
	public Data getMetaData(int requestID, String selection, String year, String country) {
		// create data object
		Data data = new Data();
		data.setRequestID(requestID);
		data.setType(selection);
		
		if(year==null&&country==null){data.setMeta("year");}
		if(year!=null&&country==null){data.setMeta("country");}
		if(year!=null&&country!=null){data.setMeta("seed");}

		if(selection.equals("population")){
			// meta: population
			metaPopulationData = new metaImportPopulation(year, country);

			// store & return data
			data.setData(metaPopulationData.getRawData());
			return data;			
		}
		if(selection.equals("production")){
			// meta: production
			metaProductionData = new metaImportProduction(year, country);

			// store & return data
			data.setData(metaProductionData.getRawData());
			return data;			
		}
		if(selection.equals("import")||selection.equals("export")){
			// meta: trade
			metaTradeData = new metaImportTrade(selection, year, country);

			// store & return data
			data.setData(metaTradeData.getRawData());
			return data;		
		}
		
		// if we are still here --> unknown mode --> return empty data object
		return data;
	}
	

	// getData
	//
	// requestID: the ID of this request. required.
	// selection: the desired selection (production, import, export, population). required.
	// year: the desired year. required.
	// country: the desired country. required.
	// seed: the desired seed. required if selection is production, import or export. for population, it is required but currently not used - for future use.
	//
	// returns a data object containing data
	public Data getData(int requestID, String selection, String year, String country, String seed){
		// create data object
		Data data = new Data();
		data.setRequestID(requestID);
		data.setType(selection);
		
		if (selection.equals("population")) {
			// population mode
			populationData = new ImportPopulation(year, country);
			// store & return data
			data.setData(populationData.getRawData());
			return data;
		}
		if (selection.equals("production")) {
			// production mode
			productionData = new ImportProduction(year, country, seed);
			// store & return data
			data.setData(productionData.getRawData());
			return data;
		}
		if (selection.equals("import")||selection.equals("export")) {
			// trade mode
			tradeData = new ImportTrade(selection, year, country, seed);
			// store & return data
			data.setData(tradeData.getRawData());
			return data;
		}	
		
		// if we are still here --> unknown mode --> return empty data object
		return data;	
	}
}