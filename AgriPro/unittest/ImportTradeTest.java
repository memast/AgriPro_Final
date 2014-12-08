//package com.agripro.gwt;

import static org.junit.Assert.*;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;



import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.agripro.gwt.client.Data;
import com.agripro.gwt.server.ImportTrade;


public class ImportTradeTest {
	
	@Test
	public void importTradeTest(){
		// test importTrade constructor
		ImportTrade tradeData;
		tradeData = new ImportTrade("import", "1995", "France", "Wheat");
		Assert.assertNotNull(tradeData);
	}
	
	@Test
	public void getRawDataTest(){
		// test importTrade constructor & getRawData
		ImportTrade tradeData;
		tradeData = new ImportTrade("export", "1995", "France", "Wheat");
		Assert.assertNotNull(tradeData);
		
		ArrayList list;
		list = tradeData.getRawData();

		Assert.assertNotNull(list);
		Assert.assertEquals(0, list.size());
	}
}
