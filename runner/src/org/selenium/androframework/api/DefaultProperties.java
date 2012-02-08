package org.selenium.androframework.api;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.testng.Assert;

public class DefaultProperties {
	Properties prop = new Properties();

	public DefaultProperties() {
		try {
			prop.load(new FileInputStream("resources/default.properties"));
		} catch (FileNotFoundException e1) {
			Assert.fail("Unable to load file due to error :" + e1.toString());
		} catch (IOException e1) {
			Assert.fail("Unable to load file due to error :" + e1.toString());
		}
	}
	
	public Properties getDefaultProperty(){
		return prop;
	}
	
	public String getValueFromProperty(String locator){
		String value= prop.getProperty(locator, "");
		return value;
	}
}
