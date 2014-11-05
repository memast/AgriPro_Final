
package com.agripro.gwt.server;

import java.io.FileReader;
import java.util.Arrays;
import java.util.Scanner;
import java.io.BufferedReader;

public class ImportProduction {
    
    static String xStrPath;
    static String[][] myArray;
    static final int POS = 7;
    
    static void setUpMyCSVArray(){
        myArray=new String[250000][20];
        
        Scanner scanIn = null;
        int rowc=0;
        int row = 0;
        int colc = 0;
        int col = 0;
        String InputLine = "";
        double xnum = 0;
        String xfileLocation;
        
        xfileLocation = "/Users/melinamast/Desktop/Datensätze/production.csv";
        
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
        
        for(int i = 0; i < 244612; i++){
            for(int j=0;j<13;j++){
                System.out.print(myArray[i][j]);
                System.out.print(" ");
                
            }
            System.out.println();
        }
        
        System.out.println("Saatgüter:");
        saatgut(myArray);
        
        
        return;
    }
    
    static public void saatgut(String[][] parsedProduction){
        String[] saatgut = new String[1000];
        int x = 1; //hier wird das naechste saatgut eingefuegt
        int contains = 0; // zero is false, one is true
        saatgut[0]=parsedProduction[1][POS];
        
        
        for(int i = 2; i < 724; i++){
            if(parsedProduction[i][POS].equals(parsedProduction[i-1][POS])){
                //do nothing
                
            }
            else{ //first check if it is already in saatgut
                int k = 0;
                
                while((saatgut[k] != parsedProduction[i][POS])  && (k < x)){
                    k++;
                }
                if(k == x){
                    saatgut[x] = parsedProduction[i][POS];
                    x = x+1;
                }
            } 
        }
        
        for(int j=0; j < 1000; j++){
            System.out.println(saatgut[j]);
        }
        
        
    }
    
    
    public static void main(String[] args) {
        setUpMyCSVArray();
        
    }
}
