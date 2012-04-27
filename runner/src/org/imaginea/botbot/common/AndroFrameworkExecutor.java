package org.imaginea.botbot.common;

import java.io.InputStream;
import java.util.ArrayList;

import junit.framework.Assert;

import org.imaginea.botbot.api.TestCSVReader;
import org.imaginea.botbot.keywords.KeywordCaller;

import com.google.android.testing.nativedriver.client.AndroidNativeDriver;
import com.google.android.testing.nativedriver.client.AndroidNativeDriverBuilder;
import com.jayway.android.robotium.solo.Solo;

public class AndroFrameworkExecutor {
	TestCSVReader reader = null;
	AndroidNativeDriver driver = null;
	Solo solo = null;
	KeywordCaller kc = null;
	Prefrences prefrences=null;
	
	public AndroFrameworkExecutor(Prefrences prefrences){
		this.prefrences=prefrences;
	}
	
	public void androExecutor(String filePath) {
		kc = new KeywordCaller(prefrences);
		try {
			reader = new TestCSVReader(filePath);
		} catch (Exception e) {
			Assert.fail("Unable to read the csv file :"+ e.toString());
		}
		this.execute();
	}
	
	public void androExecutor(InputStream filePath) {
		kc = new KeywordCaller(prefrences);
		try {
			reader = new TestCSVReader(filePath);
		} catch (Exception e) {
			Assert.fail("Unable to read the csv file :"+ e.toString());
		}
		this.execute();
	}

	private void execute(){
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
				parameters=new String[row.length-1];
				System.arraycopy(row, 1, parameters, 0, row.length-1);
			}
			command.setName(row[0]);
			command.setParameters(parameters);
			kc.execute(command);
		}
	}
	/*
	protected AndroidNativeDriver getDriver() {
		return new AndroidNativeDriverBuilder().withDefaultServer().build();
	}

	private void startActivity() {
		driver.startActivity("com.scanfu.android." + "activities.RegistrationAlertActivity");
	}*/
	/*public static void main(String[] args){
		AndroFrameworkExecutor afe = new AndroFrameworkExecutor();
		afe.androExecutor("test2.csv");
	}*/
}
