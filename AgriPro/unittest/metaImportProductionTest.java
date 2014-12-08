//package com.agripro.gwt;

import static org.junit.Assert.*;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;



import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.agripro.gwt.client.Data;
import com.agripro.gwt.server.metaImportProduction;


public class metaImportProductionTest {
	
	@Test
	public void metaImportProductionTest(){
		// test importProduction constructor
		metaImportProduction productionData;
		productionData = new metaImportProduction("1995", "France");
		Assert.assertNotNull(productionData);
	}
	
	@Test
	public void getRawDataTest(){
		// test importProduction constructor & getRawData
		metaImportProduction productionData;
		productionData = new metaImportProduction("1995", "France");
		Assert.assertNotNull(productionData);
		
		ArrayList list;
		list = productionData.getRawData();

		Assert.assertNotNull(list);
		Assert.assertEquals(0, list.size());
	}
}