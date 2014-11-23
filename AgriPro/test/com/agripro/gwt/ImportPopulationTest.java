package com.agripro.gwt;

import static org.junit.Assert.*;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.agripro.gwt.server.ImportPopulation;


public class ImportPopulationTest {

	@Test
	public void importPopulationTest() {
        try {
        	// test if .csv file with data exists and usable for InputStream (population.csv)
            InputStream is = getClass().getResourceAsStream("population.csv");
            Assert.assertNotNull(is);
            
            
            ImportPopulation populationData = new ImportPopulation();
            
            // test if data successfully loaded and an object is returned
            Assert.assertNotNull(populationData.getRawData());
            
            
        } catch (Exception pEx) {
            pEx.printStackTrace();
            Assert.fail("Should not have thrown an exception");
        }
	}
	
	@Test
	public void getRawDataTest() {
        try {
        	ImportPopulation populationData = new ImportPopulation();
            
            // test if data successfully loaded and an object is returned
            Assert.assertNotNull(populationData.getRawData());

            ArrayList testRow = (ArrayList) populationData.getRawData().get(0);
            // test if we can grab the 1th item of the 0th row ("Domain").
            Assert.assertEquals("Domain", testRow.get(1));
            // test if we can grab the 6th item of the 0th row ("ItemCode").
            Assert.assertEquals("ItemCode", testRow.get(6));

            
            ArrayList testRow2 = (ArrayList) populationData.getRawData().get(1);
           // test if we can grab the 0th item of the 1th row ("OA").
            Assert.assertEquals("OA", testRow2.get(0));
            // test if we can grab the 6th item of the 1th row ("15").
            Assert.assertEquals("3010", testRow2.get(6));
            
            
        } catch (Exception pEx) {
            pEx.printStackTrace();
            Assert.fail("Should not have thrown an exception");
        }
	}

}
