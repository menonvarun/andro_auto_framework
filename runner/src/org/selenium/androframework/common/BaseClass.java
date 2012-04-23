package org.selenium.androframework.common;


import org.selenium.androframework.api.DefaultProperties;
import org.testng.annotations.*;

import com.google.android.testing.nativedriver.client.AndroidNativeDriver;
import com.google.android.testing.nativedriver.client.AndroidNativeDriverBuilder;

public class BaseClass {
	protected AndroidNativeDriver driver = null;
	DefaultProperties prop = DefaultProperties.getDefaultProperty();
	String appPackage = prop.getValueFromProperty("APP_PACKAGE").concat(".")
			.concat(prop.getValueFromProperty("DEFAULT_ACTIVITY"));
	protected Prefrences prefrences=new Prefrences();

	protected AndroidNativeDriver getDriver() {
		/*
		 * if (driver == null) { driver = new
		 * AndroidNativeDriverBuilder().withDefaultServer() .build(); return
		 * driver; } else return driver;
		 */

		driver = new AndroidNativeDriverBuilder().withDefaultServer().build();
		return driver;

	}

	private void startActivity() {
		/*
		 * String appPackage = prop.getValueFromProperty("APP_PACKAGE");
		 * appPackage.concat(".");
		 * appPackage.concat(prop.getValueFromProperty("DEFAULT_ACTIVITY"));
		 */
		driver.startActivity(appPackage);
	}

	@BeforeMethod
	public void beforeMethod() {
		driver = this.getDriver();
		prefrences.setExecutionContext(driver);
		this.startActivity();
	}

	@AfterMethod
	public void afterMethod() {
		driver.quit();
	}

	@BeforeSuite
	public void beforeTest() {
		// driver = this.getDriver();
		// this.startActivity();
	}

	@AfterSuite
	public void afterTest() {
		// driver.quit();
	}
}
