//package com.agripro.gwt;

import static org.junit.Assert.*;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;



import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.agripro.gwt.client.Data;
import com.agripro.gwt.server.metaImportPopulation;


public class metaImportPopulationTest {
	
	@Test
	public void metaImportPopulationTest(){
		// test importPopulation constructor
		metaImportPopulation populationData;
		populationData = new metaImportPopulation("1995", "France");
		Assert.assertNotNull(populationData);
	}
	
	@Test
	public void getRawDataTest(){
		// test importPopulation constructor & getRawData
		metaImportPopulation populationData;
		populationData = new metaImportPopulation("1995", "France");
		Assert.assertNotNull(populationData);
		
		ArrayList list;
		list = populationData.getRawData();

		Assert.assertNotNull(list);
		Assert.assertEquals(0, list.size());
	}
}