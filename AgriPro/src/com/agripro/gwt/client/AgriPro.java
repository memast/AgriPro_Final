package com.agripro.gwt.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JsArray;
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
import com.google.gwt.user.client.ui.*;




public class AgriPro extends Visualisation implements EntryPoint {
	///////////////////////////////////////////////////////////////////////////////////////////////////
	//	SYSTEM VARIABLES
	private int requestID;
	private Data activeData;
	private static final String SERVER_ERROR = "An error occurred while attempting to contact the server. Please check your network connection and try again.";

	// Currently selected mode, year, country and seed
	private String activeSelection = "production"; // standard selection is set to production
	private String activeYear;
	private String activeCountry;
	private String activeSeed;
	private String activeVisualization = "table"; // standard visualization is set to table

	// If we load the system using a bookmark link, use these variables to override the values once.
	private String overrideYear = null;
	private String overrideCountry = null;
	private String overrideSeed = null;
	
	// get & setter for selected mode, year, country and seed
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
	// GUI VARIABLES
	ListBox yearLb = new ListBox();
	ListBox countryLb = new ListBox();
	ListBox seedLb = new ListBox();

	Button evaluationProductionButton;
	Button evaluationImportButton;
	Button evaluationExportButton;
	Button evaluationPopulationButton;
	
	Hyperlink bookmark;
	
	///////////////////////////////////////////////////////////////////////////////////////////////////
	// START THE PROGRAM
	public void onModuleLoad() {
		// *********************** FORM HTML *********************** //
		// SELECTION
		// Add Selection Buttons
		evaluationProductionButton = new Button("Produktion",
				new ClickHandler() {
					public void onClick(ClickEvent event) {
						setSelection("production");	
						RootPanel.get("evaluation-button-production-container").addStyleName("active");
						RootPanel.get("evaluation-button-import-container").removeStyleName("active");
						RootPanel.get("evaluation-button-export-container").removeStyleName("active");
						RootPanel.get("evaluation-button-population-container").removeStyleName("active");
					}
				});
		RootPanel.get("evaluation-button-production-container").add(evaluationProductionButton);

		evaluationImportButton = new Button(
				"Import", new ClickHandler() {
					public void onClick(ClickEvent event) {
						setSelection("import");
						RootPanel.get("evaluation-button-production-container").removeStyleName("active");
						RootPanel.get("evaluation-button-import-container").addStyleName("active");
						RootPanel.get("evaluation-button-export-container").removeStyleName("active");
						RootPanel.get("evaluation-button-population-container").removeStyleName("active");
					}
				});
		RootPanel.get("evaluation-button-import-container").add(evaluationImportButton);
		
		evaluationExportButton = new Button(
				"Export", new ClickHandler() {
					public void onClick(ClickEvent event) {
						setSelection("export");
						RootPanel.get("evaluation-button-production-container").removeStyleName("active");
						RootPanel.get("evaluation-button-import-container").removeStyleName("active");
						RootPanel.get("evaluation-button-export-container").addStyleName("active");
						RootPanel.get("evaluation-button-population-container").removeStyleName("active");
					}
				});
		RootPanel.get("evaluation-button-export-container").add(evaluationExportButton);
		
		evaluationPopulationButton = new Button("Bevoelkerung",
				new ClickHandler() {
					public void onClick(ClickEvent event) {
						setSelection("population");	
						RootPanel.get("evaluation-button-production-container").removeStyleName("active");
						RootPanel.get("evaluation-button-import-container").removeStyleName("active");
						RootPanel.get("evaluation-button-export-container").removeStyleName("active");
						RootPanel.get("evaluation-button-population-container").addStyleName("active");
					}
				});
		RootPanel.get("evaluation-button-population-container").add(evaluationPopulationButton);
		
		// Add Visualization Buttons
		final Button visualizationTableButton = new Button("Tabelle",
				new ClickHandler() {
					public void onClick(ClickEvent event) {
						activeVisualization = "table";
						RootPanel.get("visualization-button-table-container").addStyleName("active");
						RootPanel.get("visualization-button-map-container").removeStyleName("active");
						visualizeTable(activeData);
					}
				});
		RootPanel.get("visualization-button-table-container").add(visualizationTableButton);
		
		final Button visualizationMapButton = new Button("Karte",
				new ClickHandler() {
					public void onClick(ClickEvent event) {
						activeVisualization = "map";
						RootPanel.get("visualization-button-map-container").addStyleName("active");
						RootPanel.get("visualization-button-table-container").removeStyleName("active");
						visualizeMap(activeData);
					}
				});
		RootPanel.get("visualization-button-map-container").add(visualizationMapButton);
		
		// Add change handlers
		yearLb.addChangeHandler(new ChangeHandler() {
		    public void onChange(ChangeEvent event) {
				activeYear = metaYearsArray.get(yearLb.getSelectedIndex());
				metaRequest("country");
		    }
		});
		countryLb.addChangeHandler(new ChangeHandler() {
		    public void onChange(ChangeEvent event) {
				activeCountry = metaCountriesArray.get(countryLb.getSelectedIndex());
				if(activeSelection=="production"||activeSelection=="import"||activeSelection=="export"){
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

		
		// VISUALIZATION
	    InlineLabel visualisationTitle = new InlineLabel();
	    visualisationTitle.setText("Visualisierung");
		RootPanel.get("result-title").add(visualisationTitle);
		

		// FOOTER
		bookmark = new Hyperlink("Bookmark", "javascript:void(0);");
		bookmark.addClickHandler(new ClickHandler() {
	       public void onClick(ClickEvent event) {
	           saveParameters();
	       }
		});
		RootPanel.get("bookmark-container").add(bookmark);
		
		// *********************** HTML FORMED *********************** //
		
		

		// START THE PROGRAM
		// auto load get parameters
		loadParameters();
		// start meta request
		metaRequest("year");
	}
	
	
	// Load selection from GET variables from bookmark link
	public void loadParameters(){
		if(Window.Location.getParameter("activeVisualization")=="table"||Window.Location.getParameter("activeVisualization")=="map"){
			activeVisualization = Window.Location.getParameter("activeVisualization");
		}
		if(Window.Location.getParameter("activeSelection")=="production"||Window.Location.getParameter("activeSelection")=="import"||Window.Location.getParameter("activeSelection")=="export"||Window.Location.getParameter("activeSelection")=="population"){
			activeSelection = Window.Location.getParameter("activeSelection");
		}
		if(Window.Location.getParameter("activeYear")!=null){
			overrideYear = Window.Location.getParameter("activeYear");
		}
		if(Window.Location.getParameter("activeCountry")!=null){
			overrideCountry = Window.Location.getParameter("activeCountry");
		}
		if(Window.Location.getParameter("activeSeed")!=null){
			overrideSeed = Window.Location.getParameter("activeSeed");
		}
	}
	

	// Save current selection as link
	public void saveParameters(){
        String newUrl = "http://"+Window.Location.getHost()+"/";
        newUrl += "?activeVisualization="+activeVisualization;
        
        if(activeSelection!=null){
        	newUrl += "&activeSelection="+activeSelection;
        }
        if(activeYear!=null){
        	newUrl += "&activeYear="+activeYear;
        }
        if(activeCountry!=null){
        	newUrl += "&activeCountry="+activeCountry;
        }
        if(activeSeed!=null){
        	newUrl += "&activeSeed="+activeSeed;
        }
        
        Window.alert("Your current configuration is now saved under this link: " + newUrl);
        Window.Location.assign(newUrl);
	}
	
	

	// Set active selection: either production, import, export or population.
	public void setSelection(String newSelection){
		activeSelection = newSelection;
		
		// clear results
	    RootPanel.get("result-box").clear();
	    
	    // start meta request
		metaRequest("year");
	}

	// Request Meta Data for either year, country or seed
	public void metaRequest(String meta){
		// reset results
	    RootPanel.get("result-title").clear();
		RootPanel.get("result-box").clear();
		HTML html = new HTML("<br /><center><img src='loading2.gif' /></center>");
		RootPanel.get("result-box").add(html);
		
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
		dataService.getMetaData(++requestID, activeSelection, activeYear, activeCountry, new DataCallBack());
	}
	
	// Request Table/Map data
	public void dataRequest(){
		// reset results
		if(activeSelection=="population"){
			guiUpdateVisualisationTitle(activeSelection.substring(0, 1).toUpperCase() + activeSelection.substring(1) + " of " + activeCountry + ", " + activeYear);
			
		} else {
			guiUpdateVisualisationTitle(activeSelection.substring(0, 1).toUpperCase() + activeSelection.substring(1) + " of " + activeSeed + " in " + activeCountry + ", " + activeYear);
		}
		RootPanel.get("result-box").clear();
		HTML html = new HTML("<br /><center><img src='loading2.gif' /></center>");
		RootPanel.get("result-box").add(html);
		
		
		// get selection
		activeYear = metaYearsArray.get(yearLb.getSelectedIndex());
		activeCountry = metaCountriesArray.get(countryLb.getSelectedIndex());
		if(activeSelection=="production"||activeSelection=="import"||activeSelection=="export"){
			activeSeed = metaSeedsArray.get(seedLb.getSelectedIndex());
		} else {
			activeSeed = null;
		}
		
		// send request
		if(activeSelection=="population"){
			dataService.getData(++requestID, activeSelection, activeYear, activeCountry, activeSeed, new DataCallBack());			
		}
		if(activeSelection=="production"){
			dataService.getData(++requestID, activeSelection, activeYear, activeCountry, activeSeed, new DataCallBack());			
		}
		if(activeSelection=="export"||activeSelection=="import"){
			dataService.getData(++requestID, activeSelection, activeYear, activeCountry, activeSeed, new DataCallBack());
		}
	}	
	

	// Callback function for meta & data requests
	private class DataCallBack implements AsyncCallback<Data> {
		@Override
		public void onFailure(Throwable caught) {
			/* server side error occured */
		}

		@Override
		public void onSuccess(Data result) {			
			// verify request id: is this request the currently active request, or was another request called in the meantime?
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
					if(result.getType()=="production"||result.getType()=="import"||result.getType()=="export"){
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
			if(activeVisualization=="table"){
				visualizeTable(activeData);
			}
			if(activeVisualization=="map"){
				visualizeMap(activeData);
			}
		}
	}
	
	

	
	// Update Visualization title
	public void guiUpdateVisualisationTitle(String title){
	    HTML html = new HTML(title);
	    RootPanel.get("result-title").clear();
	    RootPanel.get("result-title").add(html);
	}
	
	
	// Update GUI
	public void guiUpdateSelection() {
		
		// Update Table/Map
		if(activeVisualization=="table"){
			RootPanel.get("visualization-button-table-container").addStyleName("active");
			RootPanel.get("visualization-button-map-container").removeStyleName("active");			
		}
		if(activeVisualization=="map"){
			RootPanel.get("visualization-button-map-container").addStyleName("active");	
			RootPanel.get("visualization-button-table-container").removeStyleName("active");			
		}

		
		if(activeSelection=="population"){
			RootPanel.get("evaluation-button-production-container").removeStyleName("active");
			RootPanel.get("evaluation-button-import-container").removeStyleName("active");
			RootPanel.get("evaluation-button-export-container").removeStyleName("active");
			RootPanel.get("evaluation-button-population-container").addStyleName("active");

			// add year selection
			yearLb.clear();
			for (String temp : metaYearsArray) {
				yearLb.addItem(temp, temp);
				if(activeYear!=null && temp==activeYear){
					yearLb.setSelectedIndex(yearLb.getItemCount()-1);
				}
				if(overrideYear!=null && temp==overrideYear){
					overrideYear = null;
					yearLb.setSelectedIndex(yearLb.getItemCount()-1);
				}
			}
			yearLb.setVisibleItemCount(1); // Make enough room for all seeds (setting this value to 1 turns it into a drop-down list).
		    RootPanel.get("evaluation-years-container").clear();
			RootPanel.get("evaluation-years-container").add(yearLb);
			
			if(metaCountriesArray.size()>0){
				// add country selection
				countryLb.clear();
				for (String temp : metaCountriesArray) {
					countryLb.addItem(temp, temp);
					if(activeCountry!=null && temp==activeCountry){
						countryLb.setSelectedIndex(countryLb.getItemCount()-1);
					}
					if(overrideCountry!=null && temp==overrideCountry){
						overrideCountry = null;
						countryLb.setSelectedIndex(countryLb.getItemCount()-1);
					}
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
			RootPanel.get("evaluation-button-import-container").removeStyleName("active");
			RootPanel.get("evaluation-button-export-container").removeStyleName("active");
			RootPanel.get("evaluation-button-production-container").addStyleName("active");
			RootPanel.get("evaluation-button-population-container").removeStyleName("active");
		    
			// add year selection
			yearLb.clear();
			for (String temp : metaYearsArray) {
				yearLb.addItem(temp, temp);
				if(activeYear!=null && temp==activeYear){
					yearLb.setSelectedIndex(yearLb.getItemCount()-1);
				}
				if(overrideYear!=null && temp==overrideYear){
					overrideYear = null;
					yearLb.setSelectedIndex(yearLb.getItemCount()-1);
				}
			}
			yearLb.setVisibleItemCount(1); // Make enough room for all seeds (setting this value to 1 turns it into a drop-down list).
		    RootPanel.get("evaluation-years-container").clear();
			RootPanel.get("evaluation-years-container").add(yearLb);

			if(metaCountriesArray.size()>0){
				// add country selection
				countryLb.clear();
				for (String temp : metaCountriesArray) {
					countryLb.addItem(temp, temp);
					if(activeCountry!=null && temp==activeCountry){
						countryLb.setSelectedIndex(countryLb.getItemCount()-1);
					}
					if(overrideCountry!=null && temp==overrideCountry){
						overrideCountry = null;
						countryLb.setSelectedIndex(countryLb.getItemCount()-1);
					}
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
					if(activeSeed!=null && temp==activeSeed){
						seedLb.setSelectedIndex(seedLb.getItemCount()-1);
					}
					if(overrideSeed!=null && temp==overrideSeed){
						overrideSeed = null;
						seedLb.setSelectedIndex(seedLb.getItemCount()-1);
					}
				}
				seedLb.setVisibleItemCount(1); // Make enough room for all seeds (setting this value to 1 turns it into a drop-down list).
			    RootPanel.get("evaluation-seeds-container").clear();
				RootPanel.get("evaluation-seeds-container").add(seedLb);
			}
		}
		if(activeSelection=="import"){
			RootPanel.get("evaluation-button-import-container").addStyleName("active");
			RootPanel.get("evaluation-button-export-container").removeStyleName("active");
			RootPanel.get("evaluation-button-production-container").removeStyleName("active");
			RootPanel.get("evaluation-button-population-container").removeStyleName("active");


			// add year selection
			yearLb.clear();
			for (String temp : metaYearsArray) {
				yearLb.addItem(temp, temp);
				if(activeYear!=null && temp==activeYear){
					yearLb.setSelectedIndex(yearLb.getItemCount()-1);
				}
				if(overrideYear!=null && temp==overrideYear){
					overrideYear = null;
					yearLb.setSelectedIndex(yearLb.getItemCount()-1);
				}
			}
			yearLb.setVisibleItemCount(1); // Make enough room for all seeds (setting this value to 1 turns it into a drop-down list).
		    RootPanel.get("evaluation-years-container").clear();
			RootPanel.get("evaluation-years-container").add(yearLb);

			if(metaCountriesArray.size()>0){
				// add country selection
				countryLb.clear();
				for (String temp : metaCountriesArray) {
					countryLb.addItem(temp, temp);
					if(activeCountry!=null && temp==activeCountry){
						countryLb.setSelectedIndex(countryLb.getItemCount()-1);
					}
					if(overrideCountry!=null && temp==overrideCountry){
						overrideCountry = null;
						countryLb.setSelectedIndex(countryLb.getItemCount()-1);
					}
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
					if(activeSeed!=null && temp==activeSeed){
						seedLb.setSelectedIndex(seedLb.getItemCount()-1);
					}
					if(overrideSeed!=null && temp==overrideSeed){
						overrideSeed = null;
						seedLb.setSelectedIndex(seedLb.getItemCount()-1);
					}
				}
				seedLb.setVisibleItemCount(1); // Make enough room for all seeds (setting this value to 1 turns it into a drop-down list).
			    RootPanel.get("evaluation-seeds-container").clear();
				RootPanel.get("evaluation-seeds-container").add(seedLb);
			}	
		}
		if(activeSelection=="export"){
			RootPanel.get("evaluation-button-export-container").addStyleName("active");
			RootPanel.get("evaluation-button-import-container").removeStyleName("active");
			RootPanel.get("evaluation-button-production-container").removeStyleName("active");
			RootPanel.get("evaluation-button-population-container").removeStyleName("active");


			// add year selection
			yearLb.clear();
			for (String temp : metaYearsArray) {
				yearLb.addItem(temp, temp);
				if(activeYear!=null && temp==activeYear){
					yearLb.setSelectedIndex(yearLb.getItemCount()-1);
				}
				if(overrideYear!=null && temp==overrideYear){
					overrideYear = null;
					yearLb.setSelectedIndex(yearLb.getItemCount()-1);
				}
			}
			yearLb.setVisibleItemCount(1); // Make enough room for all seeds (setting this value to 1 turns it into a drop-down list).
		    RootPanel.get("evaluation-years-container").clear();
			RootPanel.get("evaluation-years-container").add(yearLb);

			if(metaCountriesArray.size()>0){
				// add country selection
				countryLb.clear();
				for (String temp : metaCountriesArray) {
					countryLb.addItem(temp, temp);
					if(activeCountry!=null && temp==activeCountry){
						countryLb.setSelectedIndex(countryLb.getItemCount()-1);
					}
					if(overrideCountry!=null && temp==overrideCountry){
						overrideCountry = null;
						countryLb.setSelectedIndex(countryLb.getItemCount()-1);
					}
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
					if(activeSeed!=null && temp==activeSeed){
						seedLb.setSelectedIndex(seedLb.getItemCount()-1);
					}
					if(overrideSeed!=null && temp==overrideSeed){
						overrideSeed = null;
						seedLb.setSelectedIndex(seedLb.getItemCount()-1);
					}
				}
				seedLb.setVisibleItemCount(1); // Make enough room for all seeds (setting this value to 1 turns it into a drop-down list).
			    RootPanel.get("evaluation-seeds-container").clear();
				RootPanel.get("evaluation-seeds-container").add(seedLb);
			}	
		}
		
	}
}
