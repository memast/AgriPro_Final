package com.agripro.gwt.client;

import com.agripro.gwt.shared.FieldVerifier;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;

import com.google.gwt.core.client.JsonUtils;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class AgriPro implements EntryPoint {
	
	private Data activeData;
	
	/**
	 * The message displayed to the user when the server cannot be reached or
	 * returns an error.
	 */
	private static final String SERVER_ERROR = "An error occurred while "
			+ "attempting to contact the server. Please check your network "
			+ "connection and try again.";

	/**
	 * Create a remote service proxy to talk to the server-side Greeting service.
	 */
	private final DataServiceAsync dataService = GWT.create(DataService.class);

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		// Add Selection Buttons
		final Button evaluationImportExportButton = new Button("Import & Export");
		RootPanel.get("evaluation-button-container").add(evaluationImportExportButton);
		final Button evaluationProductionButton = new Button("Produktion");
		RootPanel.get("evaluation-button-container").add(evaluationProductionButton);
		final Button evaluationPopulationButton = new Button("Bevoelkerung");
		RootPanel.get("evaluation-button-container").add(evaluationPopulationButton);
		
		   
		// Add Visualization Buttons
		final Button visualizationTableButton = new Button("Tabelle");
		RootPanel.get("visualization-button-container").add(visualizationTableButton);
		final Button visualizationMapButton = new Button("Karte");
		RootPanel.get("visualization-button-container").add(visualizationMapButton);
		
		
		// request production data
        dataService.getData("production", new DataCallBack());
	}

	
	
	  public void visualizeTable(){
		   // Visualize as table
		   // Melina's Code goes here ;)
		   // Data is stored in activeData as ArrayList (multi-dimensional)
		   // ATTENTION: Make your production csv smaller so it has only about 2000 lines!
		   final FlexTable tabelle = new FlexTable();
		   
		   
		   
		   
		   for(int i = 0; i< activeData.getData().size()-15; i++){
			   	 
				 ArrayList currentLine = (ArrayList) activeData.getData().get(i);
				 for(int j= 1; j < currentLine.size()-2; j++){
					 tabelle.getColumnFormatter().setWidth(j, "6%");
					 
					 tabelle.setText(i, j, currentLine.get(j).toString());
					 
				
				 }
		   }	
		   
		   // ...and set it's column span so that it takes up the whole row.
		   


		   RootPanel.get("result-box").add(tabelle);
		   
	   }
	  
   private class DataCallBack implements AsyncCallback<Data> {
	      @Override
	      public void onFailure(Throwable caught) {
	         /* server side error occured */
	         System.out.println("Unable to obtain server response: " + caught.getMessage());	
	      }
	      @Override
	      public void onSuccess(Data result) {
		      System.out.println("Success! Received results");
	    	  activeData = result;
	    	  
	    	  // let's visualize our table straight away
	    	  visualizeTable();
	      }	   
	   }
   
 
   
}
