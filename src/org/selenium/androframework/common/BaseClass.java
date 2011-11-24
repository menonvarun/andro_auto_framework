package org.selenium.androframework.common;

import org.testng.annotations.*;

import com.google.android.testing.nativedriver.client.AndroidNativeDriver;
import com.google.android.testing.nativedriver.client.AndroidNativeDriverBuilder;

public class BaseClass {
	protected AndroidNativeDriver driver = null;

	protected AndroidNativeDriver getDriver() {
		/*if (driver == null) {
			driver = new AndroidNativeDriverBuilder().withDefaultServer().build();
			this.startActivity();
			return driver;
		} else
			return driver;
			*/
		driver = new AndroidNativeDriverBuilder().withDefaultServer().build();
		return driver;
	}

	private void startActivity() {
		driver.startActivity("com.scanfu.android." + "activities.RegistrationAlertActivity");
	}
	
	@BeforeMethod
	public void beforeMethod(){
		driver = this.getDriver();
		this.startActivity();
	}
	
	@AfterMethod
	public void afterMethod(){
		driver.quit();
	}
	
	@BeforeSuite
	public void beforeTest() {
	//	driver = this.getDriver();
		//this.startActivity();
	}

	@AfterSuite
	public void afterTest() {
		//driver.quit();
	}
}
