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

	// fills dataArray with trade data based on year, country and seed
	public ImportTrade(String selection, String year, String country, String seed) {	
		try{
			// create db connection
			Class.forName("com.mysql.jdbc.GoogleDriver");
			Connection conn = DriverManager.getConnection("jdbc:google:mysql://agriprouzh:db/db?user=root");
			ResultSet rs;
			String selectionString;
			
			if(selection.equals("import")){
				selectionString = "Import Quantity";
			} else {
				selectionString = "Export Quantity";
			}

			// request data from sql database
			if(country.equals("Global")){
				rs = conn.createStatement().executeQuery("SELECT * FROM trade WHERE ElementName = '"+selectionString+"' AND YEAR = '"+year+"' AND ItemName = '"+seed+"'");
			} else {
				rs = conn.createStatement().executeQuery("SELECT * FROM trade WHERE ElementName = '"+selectionString+"' AND YEAR = '"+year+"' AND AreaName = '"+country+"' AND ItemName = '"+seed+"'");	
			}
			
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
