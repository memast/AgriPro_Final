package com.agripro.gwt;

import static org.junit.Assert.*;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootPanel;
import com.agripro.gwt.client.Data;
import com.agripro.gwt.server.ImportProduction;

public class AgriProTest{
	
	@Test
	public void createUniqueSeedTest(){
		try{
			ArrayList<String> uniqueSeeds = new ArrayList<String>();
			 
			
			ImportProduction productionData;
			// get data
			productionData = new ImportProduction();
			// store data
			Data activeData = new Data();
			activeData.setData(productionData.getRawData());
			ArrayList allSeeds = activeData.getData();

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
			
			Assert.assertTrue(uniqueSeeds.get(0).equals("ItemName"));
			Assert.assertTrue(uniqueSeeds.get(1).equals("Wheat"));

		} catch (Exception pEx) {
            pEx.printStackTrace();
            Assert.fail("Should not have thrown an exception");
        }
	 
	}

}
