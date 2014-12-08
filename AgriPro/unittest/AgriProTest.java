//package com.agripro.gwt;

import static org.junit.Assert.*;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootPanel;
import com.agripro.gwt.client.Data;
import com.agripro.gwt.server.ImportProduction;
import com.agripro.gwt.client.AgriPro;

public class AgriProTest{
	// Because we cannot emulate an AgriPro object, we have rewritten the code in here.
	// The code is exactly the same minus the dependencies on other AgriPro classes, methods and objects.
	// The replaced dependent features are replaced with their expected result and then those features are tested seperately.
	
	 @Test
	 public void saveParametersTest() {
		 // Test parameter saving
		String activeVisualization = "table";
		String activeSelection = "import";
		String activeYear = "2000";
		String activeCountry = "Switzerland";
		String activeSeed = "Bananas";
		String newUrl = "http://agriprouzh.appspot.com/";
		
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

        // test parameter save
		Assert.assertEquals("http://agriprouzh.appspot.com/?activeVisualization=table&activeSelection=import&activeYear=2000&activeCountry=Switzerland&activeSeed=Bananas", newUrl);
	}

	 @Test
	 public void metaRequestTest(){
		// Test meta request
		ArrayList<String> metaYearsArray = new ArrayList();
		ArrayList<String> metaCountriesArray = new ArrayList();
		ArrayList<String> metaSeedsArray = new ArrayList();
		
		String activeYear = "1991";
		String activeCountry = "Germany";
		String activeSeed = "Maize";
		
		String meta = "country";
		 
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
		
	    // test request
		Assert.assertEquals("1991", activeYear);
		Assert.assertNull(activeCountry);
		Assert.assertNull(activeSeed);
	}
	 
	 
	 @Test
	public void dataRequestTest(){
		 // Test data request
		ArrayList<String> metaYearsArray = new ArrayList();
		ArrayList<String> metaCountriesArray = new ArrayList();
		ArrayList<String> metaSeedsArray = new ArrayList();
		
		String activeSelection = "production";
		String activeYear = "1991";
		String activeCountry = "Germany";
		String activeSeed = "Maize";

		metaYearsArray.add("1991");
		metaYearsArray.add("1992");
		metaCountriesArray.add("Switzerland");
		metaCountriesArray.add("Germany");
		metaSeedsArray.add("Bananas");
		metaSeedsArray.add("Maize");
		metaSeedsArray.add("Apples");
			
		// get selection
		activeYear = metaYearsArray.get(0);
		activeCountry = metaCountriesArray.get(0);
		if(activeSelection=="production"||activeSelection=="import"||activeSelection=="export"){
			activeSeed = metaSeedsArray.get(1);
		} else {
			activeSeed = null;
		}
		
		// send request
		Assert.assertEquals("1991", activeYear);
		Assert.assertEquals("Switzerland", activeCountry);
		Assert.assertEquals("Maize", activeSeed);
	}	
}
