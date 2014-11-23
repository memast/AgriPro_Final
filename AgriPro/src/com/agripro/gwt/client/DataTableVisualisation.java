package com.agripro.gwt.client;

import java.util.ArrayList;

import javax.xml.bind.Binder;







import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.Widget;




public class DataTableVisualisation {
	public void visualizeTable(String selectedSeed, Data activeData) {
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
		if(AgriPro.getMode() == "trade"){
			System.out.println("right");
//			fills header
			for(int i= 0; i<1; i++){
				ArrayList header1 = (ArrayList) activeData.getData().get(i);
				for (int j = 0; j < header1.size() - 2; j++) {
					tabelle.getColumnFormatter().setWidth(j, "6%");
					tabelle.setText(i, j, header1.get(j).toString());
				}
			}				
				
			for (int i = 1; i < activeData.getData().size() - 15; i++) {
				ArrayList currentLine = (ArrayList) activeData.getData().get(i);
					if (currentLine.get(7).toString().equals(selectedSeed)){
						// add the line to our table
						for (int j = 0; j < currentLine.size() - 2; j++) {
							//System.out.println(currentLine.get(7).toString());
							tabelle.getColumnFormatter().setWidth(j, "6%");
							tabelle.setText(i, j, currentLine.get(j).toString());
						}
					}
				}
			
	    }
		
		
		else if(AgriPro.getMode() == "production"){
			//fills header
			for(int i= 0; i<1; i++){
				ArrayList header1 = (ArrayList) activeData.getData().get(i);
				for (int j = 0; j < header1.size() - 2; j++) {
					tabelle.getColumnFormatter().setWidth(j, "6%");
					tabelle.setText(i, j, header1.get(j).toString());
				}
			}				
			for (int i = 1; i < activeData.getData().size(); i++) {
				ArrayList currentLine = (ArrayList) activeData.getData().get(i);
					if (currentLine.get(7).toString().equals(selectedSeed)){
						// add the line to our table
						for (int j = 0; j < currentLine.size() - 2; j++) {
							//System.out.println(currentLine.get(7).toString());
							tabelle.getColumnFormatter().setWidth(j, "6%");
							tabelle.setText(i, j, currentLine.get(j).toString());
						}
					}
				}
			
		}
		
		else if(AgriPro.getMode() == "population"){
			//fills header
			for(int i= 0; i<1; i++){
				ArrayList header1 = (ArrayList) activeData.getData().get(i);
				for (int j = 0; j < header1.size() - 2; j++) {
					tabelle.getColumnFormatter().setWidth(j, "6%");
					tabelle.setText(i, j, header1.get(j).toString());
				}
			}
			for (int i = 1; i < activeData.getData().size() - 15; i++) {
				ArrayList currentLine = (ArrayList) activeData.getData().get(i);
					if (currentLine.get(7).toString().equals(selectedSeed)){
						// add the line to our table
						for (int j = 0; j < currentLine.size() - 2; j++) {
							//System.out.println(currentLine.get(7).toString());
							tabelle.getColumnFormatter().setWidth(j, "6%");
							tabelle.setText(i, j, currentLine.get(j).toString());
						}
					}
				}
			
		}
		  
	    RootPanel.get("result-box").add(tabelle); 
	}
	


	
	
}
