package com.agripro.gwt.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.Timer;

import java.util.Iterator;
import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;

import com.google.gwt.core.client.JsonUtils;

public class AgriPro extends Visualisation implements EntryPoint {
	///////////////////////////////////////////////////////////////////////////////////////////////////
	//	SYSTEM
	private int requestID;
	private Data activeData;
	private static final String SERVER_ERROR = "An error occurred while attempting to contact the server. Please check your network connection and try again.";

	// Currently selected mode, year, country and seed
	private String activeSelection = "production"; // standard selection is set to production
	private String activeYear;
	private String activeCountry;
	private String activeSeed;
	
	public String getActiveSelection() { return activeSelection; }
	public String getActiveYear() { return activeYear; }
	public String getActiveCountry() { return activeCountry; }
	public String getActiveSeed() { return activeSeed; }
	public void setActiveSelection(String activeSelection) { this.activeSelection = activeSelection; }
	public void setActiveYear(String activeYear) { this.activeYear = activeYear; }
	public void setActiveCountry(String activeCountry) { this.activeCountry = activeCountry; }
	public void setActiveSeed(String activeSeed) { this.activeSeed = activeSeed; }
	
	// Available selections for Years, Countries, Seeds
	private ArrayList<String> metaYearsArray = new ArrayList();
	private ArrayList<String> metaCountriesArray = new ArrayList();
	private ArrayList<String> metaSeedsArray = new ArrayList();
	public ArrayList<String> getMetaYearsArray() { return metaYearsArray; }
	public ArrayList<String> getMetaCountriesArray() { return metaCountriesArray; }
	public ArrayList<String> getMetaSeedsArray() { return metaSeedsArray; }

	// create data service
	private final DataServiceAsync dataService = GWT.create(DataService.class);
	
	
	///////////////////////////////////////////////////////////////////////////////////////////////////
	// GUI
	ListBox yearLb = new ListBox();
	ListBox countryLb = new ListBox();
	ListBox seedLb = new ListBox();
	
	
	///////////////////////////////////////////////////////////////////////////////////////////////////
	// START
	public void onModuleLoad() {
		debug("Started");
		
		// request standard selection: production
		metaRequest("year");
		
		// *********************** FORM HTML *********************** //
		// Add Selection Buttons
		final Button evaluationImportExportButton = new Button(
				"Handel", new ClickHandler() {
					public void onClick(ClickEvent event) {
						setSelection("trade");
					}
				});

		RootPanel.get("evaluation-button-container").add(evaluationImportExportButton);
		final Button evaluationProductionButton = new Button("Produktion",
				new ClickHandler() {
					public void onClick(ClickEvent event) {
						setSelection("production");						
					}
				});
		RootPanel.get("evaluation-button-container").add(evaluationProductionButton);
		final Button evaluationPopulationButton = new Button("Bevoelkerung",
				new ClickHandler() {
					public void onClick(ClickEvent event) {
						setSelection("population");						
					}
				});
		RootPanel.get("evaluation-button-container").add(evaluationPopulationButton);
		
		// Add Visualization Buttons
		final Button visualizationTableButton = new Button("Tabelle",
				new ClickHandler() {
					public void onClick(ClickEvent event) {
						dataRequest();
					}
				});
		RootPanel.get("visualization-button-container").add(visualizationTableButton);
		
		final Button visualizationMapButton = new Button("Karte",
				new ClickHandler() {
					public void onClick(ClickEvent event) {
						dataRequest();
					}
				});
		RootPanel.get("visualization-button-container").add(visualizationMapButton);
		

		/////////////////////////////////////////////////////////////////
		// AUSWAHL
		yearLb.addChangeHandler(new ChangeHandler() {
		    public void onChange(ChangeEvent event) {
				activeYear = metaYearsArray.get(yearLb.getSelectedIndex());
				metaRequest("country");
		    }
		});
		countryLb.addChangeHandler(new ChangeHandler() {
		    public void onChange(ChangeEvent event) {
				activeCountry = metaCountriesArray.get(countryLb.getSelectedIndex());
				if(activeSelection=="production"||activeSelection=="trade"){
					metaRequest("seed");
				} else {
					dataRequest();
				}
		    }
		});
		seedLb.addChangeHandler(new ChangeHandler() {
		    public void onChange(ChangeEvent event) {
				activeSeed = metaSeedsArray.get(seedLb.getSelectedIndex());
				dataRequest();
		    }
		});

		/////////////////////////////////////////////////////////////////
		// DARSTELLUNG
		

		/////////////////////////////////////////////////////////////////
		// VISUALISIERUNG
	    InlineLabel visualisationTitle = new InlineLabel();
	    visualisationTitle.setText("Visualisierung");
		RootPanel.get("result-title").add(visualisationTitle);
		
		
		
		
		// *********************** HTML FORMED *********************** //
		
		// DEBUG
		final Button debugHideButton = new Button("Hide Debugger",
				new ClickHandler() {
					public void onClick(ClickEvent event) {
						RootPanel.get("debug").setStyleName("invisible");
					}
				});
		RootPanel.get("debug-hide").add(debugHideButton);
		
	}
	
		
	// display card
	private void visualizeCard() {
		HTML html = new HTML("Hier wird in Zukunft eine Kartendarstellung sichtbar sein");
		RootPanel.get("result-box").add(html);
	}

	// request meta
	public void setSelection(String newSelection){
		activeSelection = newSelection;
		
		// clear results
	    RootPanel.get("result-box").clear();
	    
	    // start meta request
		metaRequest("year");
	}
	public void metaRequest(String meta){
		
		// reset dependent data & get value
		if(meta=="year"){
			metaYearsArray = new ArrayList();
			metaCountriesArray = new ArrayList();
			metaSeedsArray = new ArrayList();
			activeYear = null;
			activeCountry = null;
			activeSeed = null;
		}
		if(meta=="country"){
			metaCountriesArray = new ArrayList();
			metaSeedsArray = new ArrayList();
			activeCountry = null;
			activeSeed = null;
		}
		if(meta=="seed"){
			metaSeedsArray = new ArrayList();
			activeSeed = null;
		}
		
	    // send request
		debug("Meta Request started: "+meta+" with parameters "+activeYear+", "+activeCountry);
		dataService.getMetaData(++requestID, activeSelection, activeYear, activeCountry, new DataCallBack());
	}
	
	// request data
	public void dataRequest(){
		
		// get selection
		activeYear = metaYearsArray.get(yearLb.getSelectedIndex());
		activeCountry = metaCountriesArray.get(countryLb.getSelectedIndex());
		if(activeSelection=="production"||activeSelection=="trade"){
			activeSeed = metaSeedsArray.get(seedLb.getSelectedIndex());
		} else {
			activeSeed = null;
		}
		
		debug("Data Request started: "+activeSelection+ " with parameters "+activeYear+", "+activeCountry+", "+activeSeed);
		
		// clear results
	    RootPanel.get("result-box").clear();
		
		// send request
		if(activeSelection=="population"){
			dataService.getData(++requestID, activeSelection, activeYear, activeCountry, activeSeed, new DataCallBack());			
		}
		if(activeSelection=="production"){
			dataService.getData(++requestID, activeSelection, activeYear, activeCountry, activeSeed, new DataCallBack());			
		}
		if(activeSelection=="trade"){
			dataService.getData(++requestID, activeSelection, activeYear, activeCountry, activeSeed, new DataCallBack());
		}
	}	
	

	private class DataCallBack implements AsyncCallback<Data> {
		@Override
		public void onFailure(Throwable caught) {
			/* server side error occured */
			debug("DataCallBackError: "+caught.getMessage());
		}

		@Override
		public void onSuccess(Data result) {
			debug("--> " +activeSelection + " DataCallBack Success");
			debug("---> " +"Type: "+result.getType());
			debug("---> " +"Meta: "+result.getMeta());
			debug("---> " +"Data: "+result.getData().toString());
			
			// verify request id: is this request the active one, or was another request called?
			if(result.getRequestID()!=requestID){ return; }
			
			// update selection
			activeSelection = result.getType();
			
			// meta data
			if(result.getMeta()!= null){
				if(result.getMeta().equals("year")){
					metaYearsArray = result.getData();
					guiUpdateSelection();
					activeYear = metaYearsArray.get(yearLb.getSelectedIndex());
					metaRequest("country");
				}
				if(result.getMeta().equals("country")){
					metaCountriesArray = result.getData();
					guiUpdateSelection();
					activeCountry = metaCountriesArray.get(countryLb.getSelectedIndex());
					if(result.getType()=="production"||result.getType()=="trade"){
						metaRequest("seed");
					} else {
						dataRequest();
					}
				}
				if(result.getMeta().equals("seed")){
					metaSeedsArray = result.getData();
					guiUpdateSelection();
					activeSeed = metaSeedsArray.get(seedLb.getSelectedIndex());
					dataRequest();
				}
				return;
			}
			
			// normal data
			activeData = result;
			visualizeTable(activeData);
		}
	}
	
	

	
	
	
	
	
	public void guiUpdateVisualisationTitle(String title){
	    InlineLabel visualisationTitle = new InlineLabel();
	    visualisationTitle.setText("Visualisierung: "+title);
	    RootPanel.get("result-title").clear();
	    RootPanel.get("result-title").add(visualisationTitle);
	}
	
	public void guiUpdateSelection() {
		if(activeSelection=="population"){
			guiUpdateVisualisationTitle("Bevoelkerung");


			// add year selection
			yearLb.clear();
			for (String temp : metaYearsArray) {
				yearLb.addItem(temp, temp);
			}
			yearLb.setVisibleItemCount(1); // Make enough room for all seeds (setting this value to 1 turns it into a drop-down list).
		    RootPanel.get("evaluation-years-container").clear();
			RootPanel.get("evaluation-years-container").add(yearLb);
			
			if(metaCountriesArray.size()>0){
				// add country selection
				countryLb.clear();
				for (String temp : metaCountriesArray) {
					countryLb.addItem(temp, temp);
				}
				countryLb.setVisibleItemCount(1); // Make enough room for all seeds (setting this value to 1 turns it into a drop-down list).
			    RootPanel.get("evaluation-countries-container").clear();
				RootPanel.get("evaluation-countries-container").add(countryLb);
			}
			
			// remove seed selection
			RootPanel.get("super-seed-container").setStyleName("invisible");
			seedLb.clear();	
		    
		}
		if(activeSelection=="production"){
			guiUpdateVisualisationTitle("Produktion");
		    
			// add year selection
			yearLb.clear();
			for (String temp : metaYearsArray) {
				yearLb.addItem(temp, temp);
			}
			yearLb.setVisibleItemCount(1); // Make enough room for all seeds (setting this value to 1 turns it into a drop-down list).
		    RootPanel.get("evaluation-years-container").clear();
			RootPanel.get("evaluation-years-container").add(yearLb);

			if(metaCountriesArray.size()>0){
				// add country selection
				countryLb.clear();
				for (String temp : metaCountriesArray) {
					countryLb.addItem(temp, temp);
				}
				countryLb.setVisibleItemCount(1); // Make enough room for all seeds (setting this value to 1 turns it into a drop-down list).
			    RootPanel.get("evaluation-countries-container").clear();
				RootPanel.get("evaluation-countries-container").add(countryLb);
			}

			if(metaSeedsArray.size()>0){
				// add seed selection
				RootPanel.get("super-seed-container").removeStyleName("invisible");		// boolean isMultipleSelect = true;
				seedLb.clear();
				for (String temp : metaSeedsArray) {
					seedLb.addItem(temp, temp);
				}
				seedLb.setVisibleItemCount(1); // Make enough room for all seeds (setting this value to 1 turns it into a drop-down list).
			    RootPanel.get("evaluation-seeds-container").clear();
				RootPanel.get("evaluation-seeds-container").add(seedLb);
			}
		}
		if(activeSelection=="trade"){
			guiUpdateVisualisationTitle("Handel");


			// add year selection
			yearLb.clear();
			for (String temp : metaYearsArray) {
				yearLb.addItem(temp, temp);
			}
			yearLb.setVisibleItemCount(1); // Make enough room for all seeds (setting this value to 1 turns it into a drop-down list).
		    RootPanel.get("evaluation-years-container").clear();
			RootPanel.get("evaluation-years-container").add(yearLb);

			if(metaCountriesArray.size()>0){
				// add country selection
				countryLb.clear();
				for (String temp : metaCountriesArray) {
					countryLb.addItem(temp, temp);
				}
				countryLb.setVisibleItemCount(1); // Make enough room for all seeds (setting this value to 1 turns it into a drop-down list).
			    RootPanel.get("evaluation-countries-container").clear();
				RootPanel.get("evaluation-countries-container").add(countryLb);
			}

			if(metaSeedsArray.size()>0){
				// add seed selection
				RootPanel.get("super-seed-container").removeStyleName("invisible");		// boolean isMultipleSelect = true;
				seedLb.clear();
				for (String temp : metaSeedsArray) {
					seedLb.addItem(temp, temp);
				}
				seedLb.setVisibleItemCount(1); // Make enough room for all seeds (setting this value to 1 turns it into a drop-down list).
			    RootPanel.get("evaluation-seeds-container").clear();
				RootPanel.get("evaluation-seeds-container").add(seedLb);
			}	
		}
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	
	public void debug(String input){
		HTML html = new HTML(input + "<br />");
		RootPanel.get("debug").add(html);
	}
}
