package com.agripro.gwt;

import static org.junit.Assert.*;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;




import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.agripro.gwt.server.DataServiceImpl;
import com.agripro.gwt.server.ImportProduction;


public class DataServiceImplTest {

	@Test
	public void getDataTest() {
		
		DataServiceImpl dataType = new DataServiceImpl();
		
		/**Idee: Ich teste  f¸r all diese Strings, ob nach der Ausf¸hrung die ArrayList leer ist*/
	 	//These strings will be tested
		String production = "production";
		String trade = "trade";
		String population = "population";
		String fail = "anotherString";
		
		
		
		try {
			ArrayList  productionList = (ArrayList) dataType.getData(production).getData();
			Assert.assertTrue(productionList.size() > 0);
			
		} catch (Exception pEx) {
            pEx.printStackTrace();
            Assert.fail("For the String 'production' the arrayList shouldn't be empty");
		}
		
		
	}



}
