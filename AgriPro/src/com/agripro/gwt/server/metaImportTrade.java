package com.agripro.gwt.server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.Arrays;
import java.util.Scanner;
import java.util.ArrayList;
import java.io.Serializable;

import com.agripro.gwt.client.Data;

public class metaImportTrade implements Serializable {
	private ArrayList dataArray = new ArrayList();

	public metaImportTrade(String year, String country) {		
		System.out.println("****LOADING: META-TRADE ****");
		try{
			Class.forName("com.mysql.jdbc.GoogleDriver");
			Connection conn = DriverManager.getConnection("jdbc:google:mysql://agriprouzh:db/db?user=root");
			ResultSet rs;

			if(year==null&&country==null){
				rs = conn.createStatement().executeQuery("SELECT DISTINCT Year FROM trade ORDER BY Year Desc");
				while (rs.next()) {	
					// loop through results
					if(rs.getString(1)==null){continue;}
					dataArray.add(rs.getString(1));				
				}
			}
			if(year!=null&&country==null){
				rs = conn.createStatement().executeQuery("SELECT DISTINCT AreaName FROM trade WHERE YEAR = '"+year+"' ORDER BY AreaName ASC");
				while (rs.next()) {	
					// loop through results
					if(rs.getString(1)==null){continue;}
					dataArray.add(rs.getString(1));				
				}
			}
			if(year!=null&&country!=null){
				rs = conn.createStatement().executeQuery("SELECT DISTINCT ItemName FROM trade WHERE YEAR = '"+year+"' AND AreaName= '"+country+"' ORDER BY Itemname ASC");
				while (rs.next()) {	
					// loop through results
					if(rs.getString(1)==null){continue;}
					dataArray.add(rs.getString(1));				
				}
			}
		conn.close();
		} catch (Exception e) {
			System.out.println("ERROR:");
			System.out.println(e.toString());
		}
		System.out.println("****LOADED META->TRADE ****");
	}

	
	
	
	public ArrayList getRawData() {
		return dataArray;
	}
}
