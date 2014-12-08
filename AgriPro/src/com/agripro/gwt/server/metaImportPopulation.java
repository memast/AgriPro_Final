package com.agripro.gwt.server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.Arrays;
import java.util.Scanner;
import java.util.ArrayList;
import java.io.Serializable;

import com.agripro.gwt.client.Data;

public class metaImportPopulation implements Serializable {
	private ArrayList dataArray = new ArrayList();

	// fills dataArray with meta data for population based on year and country
	public metaImportPopulation(String year, String country) {		
		try{
			// create db connection
			Class.forName("com.mysql.jdbc.GoogleDriver");
			Connection conn = DriverManager.getConnection("jdbc:google:mysql://agriprouzh:db/db?user=root");
			ResultSet rs;
			
			// request data from sql database
			if(year==null&&country==null){
				rs = conn.createStatement().executeQuery("SELECT DISTINCT Year FROM population ORDER BY Year Desc");
				while (rs.next()) {	
					// loop through results
					if(rs.getString(1)==null){continue;}
					if(Integer.parseInt(rs.getString(1))==0){continue;}
					dataArray.add(rs.getString(1));				
				}
			}
			if(year!=null&&country==null){
				dataArray.add("Global");
				rs = conn.createStatement().executeQuery("SELECT DISTINCT AreaName FROM population WHERE YEAR = '"+year+"' ORDER BY AreaName ASC");
				while (rs.next()) {	
					// loop through results
					if(rs.getString(1)==null){continue;}
					dataArray.add(rs.getString(1));				
				}
			}
			if(year!=null&&country!=null){
				if(country.equals("Global")){
					rs = conn.createStatement().executeQuery("SELECT DISTINCT ItemName FROM population WHERE YEAR = '"+year+"' ORDER BY Itemname ASC");					
				} else {
					rs = conn.createStatement().executeQuery("SELECT DISTINCT ItemName FROM population WHERE YEAR = '"+year+"' AND AreaName= '"+country+"' ORDER BY Itemname ASC");
				}
				while (rs.next()) {	
					// loop through results
					if(rs.getString(1)==null){continue;}
					dataArray.add(rs.getString(1));				
				}
			}
			
			// close db connection to reduce db load
			conn.close();
		} catch (Exception e) {
			System.out.println("ERROR:");
			System.out.println(e.toString());
		}
	}

	
	// returns stored sql data of this object
	public ArrayList getRawData() {
		return dataArray;
	}
}
