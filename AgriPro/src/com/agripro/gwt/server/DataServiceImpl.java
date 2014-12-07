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

	private static final long serialVersionUID = 1L;
	private ImportProduction productionData;
	private ImportTrade tradeData;
	private ImportPopulation populationData;
	private metaImportProduction metaProductionData;
	private metaImportTrade metaTradeData;
	private metaImportPopulation metaPopulationData;
	

	public Data getMetaData(int requestID, String selection, String year, String country) {
		// META
		System.out.println("****Meta Data Service started with Parameter: " + selection + ", " + year +", " + country + "****");
		
		Data data = new Data();
		data.setRequestID(requestID);
		data.setType(selection);
		
		if(year==null&&country==null){data.setMeta("year");}
		if(year!=null&&country==null){data.setMeta("country");}
		if(year!=null&&country!=null){data.setMeta("seed");}

		if(selection.equals("population")){
			// meta: population
			metaPopulationData = new metaImportPopulation(year, country);

			// store data
			data.setData(metaPopulationData.getRawData());
			return data;			
		}
		if(selection.equals("production")){
			// meta: production
			metaProductionData = new metaImportProduction(year, country);

			// store data
			data.setData(metaProductionData.getRawData());
			return data;			
		}
		if(selection.equals("import")||selection.equals("export")){
			// meta: trade
			metaTradeData = new metaImportTrade(selection, year, country);

			// store data
			data.setData(metaTradeData.getRawData());
			return data;		
		}
		
		// if we are still here --> unknown mode
		System.out.println("ERROR: unrecognized data request. Your input was: " + selection + ", " + year +", " + country + "****");
		return data;
	}
	

	public Data getData(int requestID, String selection, String year, String country, String seed){
		// PRODUCTION or TRADE
		System.out.println("****Data Service started with Parameter: " + selection + "****");

		Data data = new Data();
		data.setRequestID(requestID);
		data.setType(selection);
		
		if (selection.equals("population")) {
			// population mode
			populationData = new ImportPopulation(year, country);
			data.setData(populationData.getRawData());
			return data;
		}
		if (selection.equals("production")) {
			// production mode
			/*
			// check whether we need to import data or if already imported
			if (productionData == null) {
				// import data
				productionData = new ImportProduction();
			}
			*/
			productionData = new ImportProduction(year, country, seed);
			data.setData(productionData.getRawData());
			return data;
		}
		if (selection.equals("import")||selection.equals("export")) {
			// trade mode
			tradeData = new ImportTrade(selection, year, country, seed);
			data.setData(tradeData.getRawData());
			return data;
		}	
		// if we are still here --> unknown mode
		System.out.println("ERROR: unrecognized data request. Your input was: '"+ selection + "'.");
		return data;	
	}
}