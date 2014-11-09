package com.agripro.gwt.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.agripro.gwt.client.Data;
import com.agripro.gwt.client.DataService;
import java.util.ArrayList;

public class DataServiceImpl extends RemoteServiceServlet 
   implements DataService{

   private static final long serialVersionUID = 1L;

   public Data getData(String input) {
	   if(input.equals("production")){
			System.out.println("entered production mode");
			// --> production data mode
			 
			// create data
			ProductionData productionData = new ProductionData();
			
			// store data
			Data data = new Data();
			data.setData(productionData.dataArray);
			System.out.println("data stored");
			
			// return data
		return data;
	   } else if(input.equals("trade")){
		    /* *********************************************** TO DO *********************************************** */
			System.out.println("entered trade mode");
			// --> trade data mode
			 
			// create data
			ImportTrade importTrade = new ImportTrade();
			
			// store data
			Data data = new Data();
			data.setData(importTrade.dataArray);
			System.out.println("data stored");
			
			// return data
			return data;
		 
	   } else if(input.equals("population")){
		    /* *********************************************** TO DO *********************************************** */
			System.out.println("entered population mode");
			// --> population data mode
			 
			// create data
			ImportPopulation populationData = new ImportPopulation();
			
			// store data
			Data data = new Data();
			data.setData(populationData.dataArray);
			System.out.println("data stored");
			
			// return data
			return data;
		    /* *********************************************** TO DO *********************************************** */
	   } else {
		   // --> unknown mode
		  System.out.println("ERROR: input must be either production, importexport, population! Your input was: "+input);
		  Data data = new Data();
		  return data;
	   }
   }   
}