package com.agripro.gwt.client;

import java.util.ArrayList;

import javax.xml.bind.Binder;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.user.client.ui.*;




public class Visualisation {
	
	public void visualizeTable(Data activeData) {	    
	    
		// This method is extremely slow. Fix?
		// Visualize as table
		final ScrollPanel scrollPanel = new ScrollPanel();		
		final FlexTable tabelle = new FlexTable();
	
		final DockLayoutPanel dock = new DockLayoutPanel(Unit.EM); 
		final FlowPanel header = new FlowPanel(); 
		
		
		//sets the column span so that it takes up the whole row
		tabelle.setWidth("100%");
		scrollPanel.add(tabelle);
		scrollPanel.setSize("300", "200");
		dock.add(tabelle);
		
		
		//checks wich mode is selected 
		if(activeData.getType() == "trade"){
			for (int i = 0; i < activeData.getData().size() ; i++) {
				ArrayList currentLine = (ArrayList) activeData.getData().get(i);
					// add the line to our table
					for (int j = 0; j < currentLine.size() - 2; j++) {
						//System.out.println(currentLine.get(7).toString());
						tabelle.getColumnFormatter().setWidth(j, "6%");
						tabelle.setText(i, j, currentLine.get(j).toString());
					}
				}
			
	    }
		
		
		else if(activeData.getType() == "production"){
			for (int i = 0; i < activeData.getData().size() ; i++) {
				ArrayList currentLine = (ArrayList) activeData.getData().get(i);
					// add the line to our table
					for (int j = 0; j < currentLine.size() - 2; j++) {
						//System.out.println(currentLine.get(7).toString());
						tabelle.getColumnFormatter().setWidth(j, "6%");
						tabelle.setText(i, j, currentLine.get(j).toString());
					}
				}
			
		}
		
		else if(activeData.getType() == "population"){
			for (int i = 0; i < activeData.getData().size() ; i++) {
				ArrayList currentLine = (ArrayList) activeData.getData().get(i);
					// add the line to our table
					for (int j = 0; j < currentLine.size() - 2; j++) {
						//System.out.println(currentLine.get(7).toString());
						tabelle.getColumnFormatter().setWidth(j, "6%");
						tabelle.setText(i, j, currentLine.get(j).toString());
					}
				}
			
		}
		  
	    RootPanel.get("result-box").add(tabelle); 
	}
	
	
}
