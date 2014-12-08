package com.agripro.gwt.client;

import java.util.ArrayList;

import javax.xml.bind.Binder;


import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.dom.client.Element;




import com.google.gwt.visualization.client.DataTable;
import com.google.gwt.visualization.client.AbstractDataTable.ColumnType;
import com.google.gwt.visualization.client.visualizations.*;
import com.google.gwt.visualization.client.visualizations.GeoMap.Options;


public class Visualisation {
	
	// Visualize as Map
	public void visualizeMap(Data activeData){
		// reset results
		RootPanel.get("result-box").clear();
		
		// create options for map
	    final Options options = Options.create();
	    options.setDataMode(GeoMap.DataMode.REGIONS);
	    options.setHeight(650);
	    options.setWidth(1000);
	    options.setShowLegend(true);
	    options.setColors(0xFF8747, 0xFFB581, 0xc06000);
	    options.setRegion("world");

	    // create data table for map
	    final DataTable dataTable = DataTable.create();
	    dataTable.addColumn(ColumnType.STRING, "Country");
	    dataTable.addColumn(ColumnType.NUMBER, activeData.getType().substring(0, 1).toUpperCase() + activeData.getType().substring(1));
	    
	    // add data to data table
	    dataTable.addRows(activeData.getData().size());
		for (int i = 0; i < activeData.getData().size() ; i++) {
			ArrayList currentLine = (ArrayList) activeData.getData().get(i);
		    dataTable.setValue(i, 0, currentLine.get(3).toString());
		    dataTable.setValue(i, 1, currentLine.get(10).toString());
			}

		// create & insert map
	    final GeoMap geo = new GeoMap(dataTable, options);
	    RootPanel.get("result-box").add(geo);
	}
	
	// Visualize as Table
	public void visualizeTable(Data activeData) {
		// reset results
		RootPanel.get("result-box").clear();
		
		//  variables for table
		final ScrollPanel scrollPanel = new ScrollPanel();		
		final FlexTable tabelle = new FlexTable();
		final DockLayoutPanel dock = new DockLayoutPanel(Unit.EM); 
		final FlowPanel header = new FlowPanel(); 
		
		// sets the column span so that it takes up the whole row
		tabelle.setWidth("100%");
		scrollPanel.add(tabelle);
		scrollPanel.setSize("300", "200");
		dock.add(tabelle);
		
		// add header
		 ArrayList tableHeader = new ArrayList <String>(20);
		 tableHeader.add("Code");
		 tableHeader.add("Area Name");
		 tableHeader.add("Element Nr.");
		 tableHeader.add("Element Name");
		 tableHeader.add("Item Nr."); 
		 tableHeader.add("Item Name");
		 tableHeader.add("Year");
		 tableHeader.add("Unit");
		 tableHeader.add("Value");
		 tableHeader.add("Unit");
		 tableHeader.add("Value");
		 tableHeader.add("Flag");
		 tableHeader.add("Flag D");
		 tableHeader.trimToSize();
		 for (int j = 0; j < tableHeader.size() - 2; j++) {
			 tabelle.getColumnFormatter().setWidth(j, "6%");
			 tabelle.setHTML(0, j, "<span class='b'>"+tableHeader.get(j).toString()+"</span");
		 }
		
		 // add data to table
		 for (int i = 0; i < activeData.getData().size() ; i++) {
			ArrayList currentLine = (ArrayList) activeData.getData().get(i);
			// add the line to our table
			for (int j = 0; j < currentLine.size() - 2; j++) {
				tabelle.getColumnFormatter().setWidth(j, "6%");
				tabelle.setText(i+1, j, currentLine.get(j).toString());
			}
		}
		 
		// add table
	    RootPanel.get("result-box").add(tabelle); 
	}
}
