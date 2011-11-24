package org.selenium.androframework.api;

import java.io.FileReader;
import java.util.List;


import au.com.bytecode.opencsv.CSVReader;

public class TestCSVReader {
	CSVReader reader = null;
	List<String[]> rows=null;
	public TestCSVReader(String filePath) throws Exception{
		reader = new CSVReader((new FileReader(filePath)),',');
		rows=reader.readAll();
	}
	
	public String getData(int row,int column){
		String data=null;
		if(row < 0 && row > rows.size()){
			return "";
		}
		String[] rowData= rows.get(row);
		if(column < 0 || column >=rowData.length){
			return "";
		}
		
		data=rowData[column];
		
		return data;
	}
	
	public int getLines(){
		return rows.size();
	}
	
}
