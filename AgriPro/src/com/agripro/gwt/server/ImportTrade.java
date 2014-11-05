package com.agripro.gwt.server;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Scanner;


public class ImportTrade {
	
	static String xStrPath;
	static String[][] myArray;

	static void setUpMyCSVArray(){
		myArray=new String[830000][14];
		
		Scanner scanIn = null;
		int rowc=0;
		int row = 0;
		int colc = 0; 
		int col = 0; 
		String InputLine = "";
		double xnum = 0;
		String xfileLocation;
		
		xfileLocation = "/Users/melinamast/Desktop/Datensätze/trade.csv";
		
		System.out.println("\n ****setup array****");
		
		try{
			scanIn = new Scanner(new BufferedReader (new FileReader(xfileLocation)));
			
			while (scanIn.hasNextLine()){
				InputLine = scanIn.nextLine();
				String[] InArray = InputLine.replaceAll("^\"", "").split("\"?(,|$)(?=(([^\"]*\"){2})*[^\"]*$) *\"?");
				
				for(int x = 0; x < InArray.length; x++){
					myArray[rowc][x] = (InArray[x]);
				}
				
				rowc++;
			}
		} catch (Exception e)
		{
			System.out.println(e);
		}
		
		for(int i = 0; i < 830000; i++){
			for(int j=0;j<13;j++){
				System.out.print(myArray[i][j]);
				System.out.print("   ");

			}
			System.out.println();
		}
		
		
		
		return;
	}
	
	
	public static void main(String[] args) {
		setUpMyCSVArray();

	}
}
