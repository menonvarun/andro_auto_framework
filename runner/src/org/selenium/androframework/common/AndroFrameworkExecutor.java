package org.selenium.androframework.common;

import java.util.ArrayList;

import org.selenium.androframework.api.TestCSVReader;

import com.google.android.testing.nativedriver.client.AndroidNativeDriver;
import com.google.android.testing.nativedriver.client.AndroidNativeDriverBuilder;

public class AndroFrameworkExecutor {
	TestCSVReader reader = null;
	AndroidNativeDriver driver = null;
	KeywordCaller kc = null;

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
