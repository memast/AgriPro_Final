//package com.agripro.gwt;

import static org.junit.Assert.*;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;





import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.agripro.gwt.client.Data;
import com.agripro.gwt.server.DataServiceImpl;
import com.agripro.gwt.server.metaImportPopulation;
import com.agripro.gwt.server.metaImportProduction;
import com.agripro.gwt.server.metaImportTrade;


public class DataServiceImplTest {

	@Test
	public void getMetaDataTest(){
		// test MetaData request
		Data data = new Data();
		DataServiceImpl dataService = new DataServiceImpl();
		data = dataService.getMetaData(1, "production", "2002", "Switzerland");
		
		// test if not null
		Assert.assertNotNull(data);
		
		// test request id
		Assert.assertEquals(1, data.getRequestID());
		
		// test meta
		Assert.assertEquals("seed", data.getMeta());
		
		// test type
		Assert.assertEquals("production", data.getType());
		
		// test if data is contained
		Assert.assertNotNull(data.getData());		
	}
	
	@Test
	public void getDataTest(){
		// test Data request
		Data data = new Data();
		DataServiceImpl dataService = new DataServiceImpl();
		data = dataService.getData(2, "production", "1993", "Germany", "Maize");
		
		// test if not null
		Assert.assertNotNull(data);
		
		// test request id
		Assert.assertEquals(2, data.getRequestID());
		
		// test meta
		Assert.assertNull(data.getMeta());
		
		// test type
		Assert.assertEquals("production", data.getType());
		
		// test if data is contained
		Assert.assertNotNull(data.getData());		
	}
}
