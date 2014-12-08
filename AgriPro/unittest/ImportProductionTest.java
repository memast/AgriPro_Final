//package com.agripro.gwt;

import static org.junit.Assert.*;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;



import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.agripro.gwt.client.Data;
import com.agripro.gwt.server.ImportProduction;


public class ImportProductionTest {
	
	@Test
	public void importProductionTest(){
		// test importproduction constructor
		ImportProduction productionData;
		productionData = new ImportProduction("1995", "France", "Wheat");
		Assert.assertNotNull(productionData);
	}
	
	@Test
	public void getRawDataTest(){
		// test importProduction constructor & getRawData
		ImportProduction productionData;
		productionData = new ImportProduction("1995", "France", "Wheat");
		Assert.assertNotNull(productionData);
		
		ArrayList list;
		list = productionData.getRawData();

		Assert.assertNotNull(list);
		Assert.assertEquals(0, list.size());
	}
}
