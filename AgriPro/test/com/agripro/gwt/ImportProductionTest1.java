package com.agripro.gwt;

import static org.junit.Assert.*;

import org.junit.Test;

import static org.junit.Assert.*;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.agripro.gwt.server.ImportProduction;


public class ImportProductionTest1 {

	@Test
	public void importProductionTest() {
        try {
        	// test if .csv file with data exists and usable for InputStream (production.csv)
            InputStream is = getClass().getResourceAsStream("productionKopie.csv");
            Assert.assertNotNull(is);
            
            
            ImportProduction productionData = new ImportProduction();
            
            // test if data successfully loaded and an object is returned
            Assert.assertNotNull(productionData.getRawData());
            
            
        } catch (Exception pEx) {
            pEx.printStackTrace();
            Assert.fail("Should not have thrown an exception");
        }
	}
	
	@Test
	public void getRawDataTest() {
        try {
            ImportProduction productionData = new ImportProduction();
            
            // test if data successfully loaded and an object is returned
            Assert.assertNotNull(productionData.getRawData());

            ArrayList testRow = (ArrayList) productionData.getRawData().get(0);
            // test if we can grab the 0th item of the 0th row ("Domain Code").
            Assert.assertEquals("Domain Code", testRow.get(0));
            // test if we can grab the 6th item of the 0th row ("ItemCode").
            Assert.assertEquals("ItemCode", testRow.get(6));

            
            ArrayList testRow2 = (ArrayList) productionData.getRawData().get(1);
            // test if we can grab the 0th item of the 1th row ("QC").
            Assert.assertEquals("QC", testRow2.get(0));
            // test if we can grab the 6th item of the 1th row ("15").
            Assert.assertEquals("15", testRow2.get(6));
            
            
        } catch (Exception pEx) {
            pEx.printStackTrace();
            Assert.fail("Should not have thrown an exception");
        }
	}

}
