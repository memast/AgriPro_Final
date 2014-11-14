package com.agripro.gwt.client;
//hello test
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
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;

import java.util.Iterator;
import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;

import com.google.gwt.core.client.JsonUtils;

public class AgriPro implements EntryPoint {
	private Data activeData;
	private static final String SERVER_ERROR = "An error occurred while attempting to contact the server. Please check your network connection and try again.";

	// create data service
	private final DataServiceAsync dataService = GWT.create(DataService.class);

	public void onModuleLoad() {
		// *********************** FORM HTML *********************** //
		// Add Selection Buttons
		final Button evaluationImportExportButton = new Button(
				"Import & Export", new ClickHandler() {
					public void onClick(ClickEvent event) {
						Window.alert("Momentan nocht nicht verfuegbar");
					}
				});

		RootPanel.get("evaluation-button-container").add(evaluationImportExportButton);
		final Button evaluationProductionButton = new Button("Produktion",
				new ClickHandler() {
					public void onClick(ClickEvent event) {
						Window.alert("Standardmaessig ausgewaehlt");
					}
				});
		RootPanel.get("evaluation-button-container").add(evaluationProductionButton);
		final Button evaluationPopulationButton = new Button("Bevoelkerung",
				new ClickHandler() {
					public void onClick(ClickEvent event) {
						Window.alert("Momentan nocht nicht verfuegbar");
					}
				});
		RootPanel.get("evaluation-button-container").add(evaluationPopulationButton);
		
		// Add Visualization Buttons
		final Button visualizationTableButton = new Button("Tabelle",
				new ClickHandler() {
					public void onClick(ClickEvent event) {
						RootPanel.get("result-box").clear();
						visualizeTable("Wheat");
					}
				});
		RootPanel.get("visualization-button-container").add(visualizationTableButton);
		final Button visualizationMapButton = new Button("Karte",
				new ClickHandler() {
					public void onClick(ClickEvent event) {
						RootPanel.get("result-box").clear();
						visualizeCard();
					}
				});
		RootPanel.get("visualization-button-container").add(visualizationMapButton);
		// *********************** HTML FORMED *********************** //

		// request production data
		dataService.getData("production", new DataCallBack());

		// not needed yet. uncommenting the next line only increases the loading
		// time.
		// dataService.getData("population", new DataCallBack());
		// not needed yet. uncommenting the next line only increases the loading
		// time.
		// dataService.getData("trade", new DataCallBack());
	}

	private void getUniqueSeeds() {
		// gets all unique Seeds
		ArrayList allSeeds = activeData.getData();
		final ArrayList<String> uniqueSeeds = new ArrayList<String>();

		// loop through all seeds
		// // add to an array if not in array
		for (int i = 0; i < allSeeds.size(); i++) {
			ArrayList currentLine = (ArrayList) allSeeds.get(i);
			if (currentLine.size() > 8) {
				String seed = currentLine.get(7).toString();
				if (!uniqueSeeds.contains(seed)) {
					uniqueSeeds.add(seed);
				}
			}
		}

		// Make a new list box, adding a few items to it.

		// boolean isMultipleSelect = true;
		final ListBox lb = new ListBox();
		String currentSeed;

		// loops through all the items in uniqueSeeds and adds it to the ListBox
		for (String temp : uniqueSeeds) {
			if (!temp.equals("ItemName")) {
				lb.addItem(temp, temp);
			}
		}

		// Make enough room for all seeds (setting this value to 1 turns it
		// into a drop-down list).
		lb.setVisibleItemCount(1);

		// Add it to the root panel.
		RootPanel.get("evaluation-seed-container").add(lb);

		System.out.println("Unique Seeds: "+uniqueSeeds.toString());
		System.out.println("Amount of unique seeds: "+uniqueSeeds.size());
		
		lb.addChangeHandler(new ChangeHandler() {
				 
				         
				    public void onChange(ChangeEvent event) {
				 
				    // Get the index of the selected Item
				 
				    int item = lb.getSelectedIndex();
				    //String value = lb.getValue(item).toString();
				    System.out.println(item);
				    System.out.println(uniqueSeeds.get(item + 1));
				    RootPanel.get("result-box").clear();
				    visualizeTable(uniqueSeeds.get(item + 1));
				    }
		});
				            
				            

	}

	public void visualizeTable(String selectedSeed) {
		// This method is extremely slow. Fix?
		// Visualize as table
		final FlexTable tabelle = new FlexTable();
		
		String seed = selectedSeed;

		for (int i = 0; i < activeData.getData().size() - 15; i++) {
			ArrayList currentLine = (ArrayList) activeData.getData().get(i);
			for (int j = 1; j < currentLine.size() - 2; j++) {
				//System.out.println(currentLine.get(7).toString());
					tabelle.getColumnFormatter().setWidth(j, "6%");
				if (currentLine.get(7).toString().equals(selectedSeed)){
					tabelle.setText(i, j, currentLine.get(j).toString());
				}
				
			}
		}

		// ...and set it's column span so that it takes up the whole row.
		RootPanel.get("result-box").add(tabelle);
	}

	private void visualizeCard() {
		HTML html = new HTML("Hier wird in Zukunft eine Kartendarstellung sichtbar sein");
		RootPanel.get("result-box").add(html);
	}

	private class DataCallBack implements AsyncCallback<Data> {
		@Override
		public void onFailure(Throwable caught) {
			/* server side error occured */
			System.out.println("Unable to obtain server response: " + caught.getMessage());
		}

		@Override
		public void onSuccess(Data result) {
			// We received data
			System.out.println("Received data from the server.");
			activeData = result;
			// Let's visualize our table straight away
			getUniqueSeeds();
			visualizeTable("Wheat");
		}
	}
}
