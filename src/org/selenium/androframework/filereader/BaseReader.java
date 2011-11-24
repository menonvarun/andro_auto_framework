package org.selenium.androframework.filereader;

public interface BaseReader {
	
	public void loadReaderFile(String filePath);
	public void openFile(String fileName);
	public String getDataForKey(String locatorName);
	public void storeKeyValue(String locatorName, String newLocator);
	public void modifyValueForKey(String locatorName, String newLocatorValue);
	public boolean supportsFileType(String filePath);
}
