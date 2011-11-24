package org.selenium.androframework.filereader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Properties;

import org.testng.Assert;

public class PropertiesReader implements BaseReader {

	Properties propLocator = new Properties();

	@Override
	public void loadReaderFile(String filePath) {
		File locatorFile = new File(filePath);
		try {
		this.openFile(locatorFile);
		}catch(Exception e){
			System.out.println(e.toString());
			Assert.fail("Unable to open file : " + filePath);
		}

	}
	
	public void openFile(File file) throws Exception {
		boolean result = true;
		HashMap<String, String> hm = new HashMap<String, String>();
		FileInputStream fin= new FileInputStream(file);
		if (propLocator.isEmpty()) {
			try {
				propLocator.load(fin);

			} catch (IOException e) {
				System.out.println(e.toString());
			}
		} else {
			Properties tempProp = new Properties();
			try {
				tempProp.load(fin);
			} catch (IOException e) {
				System.out.println(e.toString());
			}
			for (Enumeration e = tempProp.keys(); e.hasMoreElements();) {
				String key = (String) e.nextElement();
				if (propLocator.containsKey(key)) {
					result = false;
					hm.put(key, tempProp.getProperty(key, ""));

				}
			}
			if (!result) {
				throw new Exception(
						"Duplicate entries found in the properties file. Following are the values: \n"
								+ hm.toString());
			}else{
				propLocator.load(fin);
			}
		}

	}

	@Override
	public void openFile(String fileName) {
		File file= new File(fileName);
		try {
			this.openFile(file);
			}catch(Exception e){
				System.out.println(e.toString());
			}
	}

	@Override
	public String getDataForKey(String key) {
		String data=propLocator.getProperty(key, "");
		if(data.equalsIgnoreCase("")){
			Assert.fail("Key not found for property: "+ key);
		}
		return data;

	}

	@Override
	public void storeKeyValue(String locatorName, String newLocator) {
		propLocator.setProperty(locatorName, newLocator);

	}

	@Override
	public void modifyValueForKey(String locatorName, String newLocatorValue) {
		propLocator.setProperty(locatorName, newLocatorValue);

	}

	@Override
	public boolean supportsFileType(String filePath) {
		if ((new File(filePath).isFile()) && filePath.endsWith(".properties")) {
			return true;
		} else {
			return false;
		}

	}
	

}
