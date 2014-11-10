package com.agripro.gwt.server;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Arrays;
import java.util.Scanner;
import java.util.ArrayList;
import java.io.Serializable;

public class ImportTrade implements Serializable {
	// BEWARE: You may need to shorten the csv files because they might be too
	// big. Do this if the App doesn't work.
	private ArrayList dataArray = new ArrayList();
	private String importTradePath = "C:/Users/Manuel/Dropbox/UZH/Software Engineering/Übung/Uebung2_Ausgabe/Datensaetze 1990-2011/trade.csv";

	ImportTrade() {
		Scanner scanIn = null;
		int numberOfLines = 0;
		String InputLine = "";
		double xnum = 0;
		String xfileLocation;
		String result = "";

		System.out.println("\n ****LOADING... " + importTradePath + "****");
		try {
			scanIn = new Scanner(new BufferedReader(new FileReader(importTradePath)));
			while (scanIn.hasNextLine()) {
				InputLine = scanIn.nextLine();
				ArrayList tmpArray = new ArrayList(Arrays.asList(InputLine.replaceAll("^\"", "").split("\"?(,|$)(?=(([^\"]*\"){2})*[^\"]*$) *\"?")));
				dataArray.add(tmpArray);
			}
		} catch (Exception e) {
			System.out.println("ImportProduction.java ERROR:");
			System.out.println(e);
		}
		System.out.println("\n ****LOADED: " + importTradePath + "****");

	}

	public ArrayList getRawData() {
		return dataArray;
	}
}
