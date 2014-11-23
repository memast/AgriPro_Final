package com.agripro.gwt;

import static org.junit.Assert.*;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.agripro.gwt.server.ImportTrade;


public class ImportTradeTest {

	@Test
	public void importTradeTest() {
        try {
        	// test if .csv file with data exists and usable for InputStream (trade.csv)
            InputStream is = getClass().getResourceAsStream("trade.csv");
            Assert.assertNotNull(is);
            
            
            ImportTrade tradeData = new ImportTrade();
            
            // test if data successfully loaded and an object is returned
            Assert.assertNotNull(tradeData.getRawData());
            
            
        } catch (Exception pEx) {
            pEx.printStackTrace();
            Assert.fail("Should not have thrown an exception");
        }
	}
	
	@Test
	public void getRawDataTest() {
        try {
        	ImportTrade tradeData = new ImportTrade();
            
            // test if data successfully loaded and an object is returned
            Assert.assertNotNull(tradeData.getRawData());

            ArrayList testRow = (ArrayList) tradeData.getRawData().get(0);
            // test if we can grab the 1th item of the 0th row ("Domain").
            Assert.assertEquals("Domain", testRow.get(1));
            // test if we can grab the 6th item of the 0th row ("ItemCode").
            Assert.assertEquals("ItemCode", testRow.get(6));

            
            ArrayList testRow2 = (ArrayList) tradeData.getRawData().get(1);
            // test if we can grab the 0th item of the 1th row ("TP").
            Assert.assertEquals("TP", testRow2.get(0));
            // test if we can grab the 6th item of the 1th row ("15").
            Assert.assertEquals("15", testRow2.get(6));
            
            
        } catch (Exception pEx) {
            pEx.printStackTrace();
            Assert.fail("Should not have thrown an exception");
        }
	}

}
