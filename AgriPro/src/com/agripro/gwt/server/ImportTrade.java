package com.agripro.gwt.server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.Arrays;
import java.util.Scanner;
import java.util.ArrayList;
import java.io.Serializable;

import com.agripro.gwt.client.Data;

public class ImportTrade implements Serializable {
	private ArrayList dataArray = new ArrayList();

	public ImportTrade(String year, String country, String seed) {		
		System.out.println("****LOADING: TRADE ****");
		try{
			Class.forName("com.mysql.jdbc.GoogleDriver");
			Connection conn = DriverManager.getConnection("jdbc:google:mysql://agriprouzh:db/db?user=root");
			ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM trade WHERE YEAR = '"+year+"' AND AreaName = '"+country+"' AND ItemName = '"+seed+"' LIMIT 100");
			while (rs.next()) {	
				// loop through results
				ArrayList tmpArray = new ArrayList();
				tmpArray.add(rs.getString(1));
				tmpArray.add(rs.getString(2));
				tmpArray.add(rs.getString(3));
				tmpArray.add(rs.getString(4));
				tmpArray.add(rs.getString(5));
				tmpArray.add(rs.getString(6));
				tmpArray.add(rs.getString(7));
				tmpArray.add(rs.getString(8));
				tmpArray.add(rs.getString(9));
				tmpArray.add(rs.getString(10));
				tmpArray.add(rs.getString(11));
				tmpArray.add(rs.getString(12));
				tmpArray.add(rs.getString(13));
				dataArray.add(tmpArray);	
			}
		conn.close();
		} catch (Exception e) {
			System.out.println("ERROR:");
			System.out.println(e.toString());
		}
		System.out.println("****LOADED TRADE ****");
	}

	public ArrayList getRawData() {
		return dataArray;
	}
}
