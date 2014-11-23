package com.agripro.gwt.client;

import java.util.ArrayList;

import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootPanel;

public class DataTableVisualisation {
	public void visualizeTable(String selectedSeed, Data activeData) {
		// This method is extremely slow. Fix?
		// Visualize as table
		final FlexTable tabelle = new FlexTable();
		
	
		for (int i = 0; i < activeData.getData().size() - 15; i++) {
			ArrayList currentLine = (ArrayList) activeData.getData().get(i);
			
			// check if currentLine has at least 7 elements
	//	if(currentLine.size()>7){
				// check if current seed matches selected seed
				if (currentLine.get(7).toString().equals(selectedSeed)){
					// add the line to our table
					for (int j = 1; j < currentLine.size() - 2; j++) {
						//System.out.println(currentLine.get(7).toString());
						tabelle.getColumnFormatter().setWidth(j, "6%");
						tabelle.setText(i, j, currentLine.get(j).toString());
					}
				}
			}
	//	}

		// ...and set it's column span so that it takes up the whole row.
		RootPanel.get("result-box").add(tabelle);
	}
	


	
	
}
