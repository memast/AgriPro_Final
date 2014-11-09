package com.agripro.gwt.server;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Arrays;
import java.util.Scanner;
import java.util.ArrayList;
import java.io.Serializable;

public class ProductionData implements Serializable{
	// BEWARE: You may need to shorten the csv files because they might be too big. Do this if the App doesn't work.
	public ArrayList dataArray = new ArrayList();
    private String productionDataPath = "/Users/melinamast/Documents/git_repository/AgriPro/data/productionKopie.csv"; // path to production.csv. Replace / with \ to get a working path.
    final int POS = 7; // POS = Position Of Seedname
    
    
    ProductionData(){
	    Scanner scanIn = null;
	    int numberOfLines = 0;
	    String InputLine = "";
	    double xnum = 0;
	    String xfileLocation;
	    String result = "";
	    
	    System.out.println("\n ****LOADING... "+productionDataPath+"****");
	    try{
	        scanIn = new Scanner(new BufferedReader (new FileReader(productionDataPath)));
	        
	        while (scanIn.hasNextLine()){
	            InputLine = scanIn.nextLine();
            	ArrayList tmpArray = new ArrayList(Arrays.asList(InputLine.replaceAll("^\"", "").split("\"?(,|$)(?=(([^\"]*\"){2})*[^\"]*$) *\"?")));
            	dataArray.add(tmpArray);
	        }
	    } catch (Exception e)
	    {
		    System.out.println("ImportProduction.java ERROR:");
	        System.out.println(e);
	    }
	    System.out.println("\n ****LOADED: "+productionDataPath+"****");
    }
}
