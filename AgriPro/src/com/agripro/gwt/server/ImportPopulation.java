package com.agripro.gwt.server;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Arrays;
import java.util.Scanner;
import java.util.ArrayList;
import java.io.Serializable;

public class ImportPopulation implements Serializable {
	// BEWARE: You may need to shorten the csv files because they might be too
	// big. Do this if the App doesn't work.
	private ArrayList dataArray = new ArrayList();
	private String importPopulationPath = "data/population.csv";

	public ImportPopulation() {
		Scanner scanIn = null;
		String InputLine = "";

		System.out.println("\n ****LOADING... " + importPopulationPath + "****");
		try {
			scanIn = new Scanner(new BufferedReader(new FileReader(importPopulationPath)));

			while (scanIn.hasNextLine()) {
				InputLine = scanIn.nextLine();
				ArrayList tmpArray = new ArrayList(Arrays.asList(InputLine.replaceAll("\"", "").split("\"?(,|$)(?=(([^\"]*\"){2})*[^\"]*$) *\"?")));
				dataArray.add(tmpArray);
			}
		} catch (Exception e) {
			System.out.println("ImportPopulation.java ERROR:");
			System.out.println(e);
		}
		System.out.println("\n ****LOADED: " + importPopulationPath + "****");
	

	}

	public ArrayList getRawData() {
		return dataArray;
	}
}
