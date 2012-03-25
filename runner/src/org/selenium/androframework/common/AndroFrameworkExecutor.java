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
	
	public AndroFrameworkExecutor(AndroidNativeDriver driver){
		this.driver=driver;
	}
	
	public AndroFrameworkExecutor(Solo solo){
		this.solo=solo;
	}

	public void androExecutor(String filePath) {
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
	}
	public void androExecutor(AndroidNativeDriver driver,String filePath) {
		kc = new KeywordCaller(driver);
		this.executor(kc, filePath);
	}
	
	public void androExecutor(Solo solo,InputStream filePath) {
		kc = new KeywordCaller(solo);
		this.executor(kc, filePath);
	}
	
	
	private void executor(KeywordCaller kc,String filePath){
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

	}
	
	private void executor(KeywordCaller kc,InputStream ipPath){
		try {
			reader = new TestCSVReader(ipPath);
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
