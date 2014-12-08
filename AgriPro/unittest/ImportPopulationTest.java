//package com.agripro.gwt;

import static org.junit.Assert.*;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;



import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.agripro.gwt.client.Data;
import com.agripro.gwt.server.ImportPopulation;


public class ImportPopulationTest {
	
	@Test
	public void importPopulationTest(){
		// test importPopulation constructor
		ImportPopulation populationData;
		populationData = new ImportPopulation("1995", "France");
		Assert.assertNotNull(populationData);
	}
	
	@Test
	public void getRawDataTest(){
		// test importPopulation constructor & getRawData
		ImportPopulation populationData;
		populationData = new ImportPopulation("1995", "France");
		Assert.assertNotNull(populationData);
		
		ArrayList list;
		list = populationData.getRawData();

		Assert.assertNotNull(list);
		Assert.assertEquals(0, list.size());
	}
}

