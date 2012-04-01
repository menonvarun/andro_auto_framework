package org.selenium.androframework.common;

import java.io.InputStream;
import java.util.ArrayList;

import org.selenium.androframework.api.TestCSVReader;
import org.selenium.androframework.keywords.KeywordCaller;

import com.google.android.testing.nativedriver.client.AndroidNativeDriver;
import com.google.android.testing.nativedriver.client.AndroidNativeDriverBuilder;
import com.jayway.android.robotium.solo.Solo;

public class AndroFrameworkExecutor {
	TestCSVReader reader = null;
	AndroidNativeDriver driver = null;
	Solo solo = null;
	KeywordCaller kc = null;
	Prefrences prefrences=null;
	
	public AndroFrameworkExecutor(AndroidNativeDriver driver){
		this.driver=driver;
	}
	
	public AndroFrameworkExecutor(Solo solo){
		this.solo=solo;
	}
	
	public AndroFrameworkExecutor(Prefrences prefrences){
		this.prefrences=prefrences;
	}

	/*public void androExecutor(String filePath) {
		driver=this.getDriver();
		kc = new KeywordCaller(driver);
		this.startActivity();
		try {
			reader = new TestCSVReader(filePath);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		int rows = reader.getLines();
		for (int i = 1; i < rows; i++) {
			String keyword = reader.getData(i, 0);
			ArrayList<String> arg = new ArrayList<String>();
			arg.add(reader.getData(i, 1));
			arg.add(reader.getData(i, 2));
			kc.methodCaller(keyword, arg);
			
		}
		driver.quit();
	}*/
	
	public void androExecutor(String filePath) {
		kc = new KeywordCaller(prefrences);
		try {
			reader = new TestCSVReader(filePath);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.executor();
	}
	
	public void androExecutor(InputStream filePath) {
		kc = new KeywordCaller(prefrences);
		try {
			reader = new TestCSVReader(filePath);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.execute();
	}
	
	public void androExecutor(AndroidNativeDriver driver,String filePath) {
		kc = new KeywordCaller(driver);
		try {
			reader = new TestCSVReader(filePath);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.execute();
	}
	
	public void androExecutor(Solo solo,InputStream filePath) {
		kc = new KeywordCaller(solo);
		try {
			reader = new TestCSVReader(filePath);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.executor();
	}
	
	
	private void executor(){
		int rows = reader.getLines();
		for (int i = 1; i < rows; i++) {
			String keyword = reader.getData(i, 0);
			ArrayList<String> arg = new ArrayList<String>();
			arg.add(reader.getData(i, 1));
			arg.add(reader.getData(i, 2));
			kc.methodCaller(keyword, arg);
		}

	}
	private void execute(){
		int rows = reader.getLines();
		for (int i = 1; i < rows; i++) {
			Command command = new Command();
			String[] row =reader.getRow(i);
			String[] parameters=new String[row.length-1];
			command.setName(row[0]);
			System.arraycopy(row, 1, parameters, 0, row.length-1);
			command.setParameters(parameters);
			kc.execute(command);
		}

	}
	
	protected AndroidNativeDriver getDriver() {
		return new AndroidNativeDriverBuilder().withDefaultServer().build();
	}

	private void startActivity() {
		driver.startActivity("com.scanfu.android." + "activities.RegistrationAlertActivity");
	}
	/*public static void main(String[] args){
		AndroFrameworkExecutor afe = new AndroFrameworkExecutor();
		afe.androExecutor("test2.csv");
	}*/
}
