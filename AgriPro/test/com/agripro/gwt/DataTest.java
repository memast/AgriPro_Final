package com.agripro.gwt;

import static org.junit.Assert.*;

import java.io.InputStream;
import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Test;

import com.agripro.gwt.client.Data;
import com.agripro.gwt.server.ImportProduction;


public class DataTest {

	@Test
	public void dataTest() {
        try {
        	// try if Constructor gets created
            Data createData = new Data();
            Assert.assertNotNull(createData);
            
            
        } catch (Exception pEx) {
            pEx.printStackTrace();
            Assert.fail("Should not have thrown an exception");
        }
	}
	

	@Test
	public void setDataTest() {
        try {
        	// try if I can give an imput to setData
            Data createData = new Data();
            ImportProduction productionData = new ImportProduction();
            productionData.getRawData();
            
            ArrayList dates = (ArrayList) productionData.getRawData();
            
			createData.setData(dates);
            Assert.assertNotNull(createData);
            
            
        } catch (Exception pEx) {
            pEx.printStackTrace();
            Assert.fail("Should not have thrown an exception");
        }
	}
	
	public void getDataTest() {
        try {
        	// try if my return of the getData() method is the same as the imported Data from ImportProduction
            Data createData = new Data();
            ImportProduction productionData = new ImportProduction();
         
            ArrayList dates = (ArrayList) productionData.getRawData();
       
            ArrayList test = createData.getData();
            Assert.assertEquals(dates, test);

            
        } catch (Exception pEx) {
            pEx.printStackTrace();
            Assert.fail("Should not have thrown an exception");
        }
	}
	
	
}

