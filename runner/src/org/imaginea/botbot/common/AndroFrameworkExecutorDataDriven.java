package org.imaginea.botbot.common;

import java.io.InputStream;
import java.util.HashMap;

import junit.framework.Assert;

import org.imaginea.botbot.api.TestCSVReader;
import org.imaginea.botbot.keywords.KeywordCaller;

import android.util.Log;

public class AndroFrameworkExecutorDataDriven extends AndroFrameworkExecutor{
	
	
	public AndroFrameworkExecutorDataDriven(Prefrences prefrences){
		super(prefrences);
	}
	
	//For Data driven test cases 
	
		public void androExecutor(InputStream filePath,HashMap<String, String> testData) {
			kc = new KeywordCaller(prefrences);
			try {
				reader = new TestCSVReader(filePath);
			} catch (Exception e) {
				Assert.fail("Unable to read the csv file :"+ e.toString());
			}
			this.execute(testData);
		}
		
		//For Data driven test cases executing test cases with putting data in parameters. 
		
		private void execute(HashMap<String, String> testData){
			Log.e("Keys", testData.keySet().toString());
			String[] parameters;
			int rows = reader.getLines();
			for (int i = 1; i < rows; i++) {
				Command command = new Command();
				String[] row =reader.getRow(i);
				if(row.length<=1){
					parameters=new String[0];
				}else if(row.length==2 && row[1]==""){
					parameters=new String[0];
				}else{
					//Skipping Command check
					for(int count=1;count<row.length;count++)
					{
						if(row[count].startsWith("$"))
						{				
							Log.e("Parameters",row[count].substring(1));
							row[count]=testData.get(row[count].substring(1));
							
						
						}
					}
					parameters=new String[row.length-1];
					System.arraycopy(row, 1, parameters, 0, row.length-1);
				}
				
				command.setName(row[0]);
				command.setParameters(parameters);
				kc.execute(command);
			}
		}

}
