//package com.agripro.gwt;

import static org.junit.Assert.*;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;



import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.agripro.gwt.client.Data;
import com.agripro.gwt.server.metaImportTrade;


public class metaImportTradeTest {
	
	@Test
	public void metaImportTradeTest(){
		// test importTrade constructor
		metaImportTrade tradeData;
		tradeData = new metaImportTrade("1995", "France", "Wheat");
		Assert.assertNotNull(tradeData);
	}
	
	@Test
	public void getRawDataTest(){
		// test importTrade constructor & getRawData
		metaImportTrade tradeData;
		tradeData = new metaImportTrade("1995", "France", "Wheat");
		Assert.assertNotNull(tradeData);
		
		ArrayList list;
		list = tradeData.getRawData();

		Assert.assertNotNull(list);
		Assert.assertEquals(0, list.size());
	}
}