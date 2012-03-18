package org.selenium.androframework.keywords;

import com.google.android.testing.nativedriver.client.AndroidNativeDriver;
import com.google.android.testing.nativedriver.common.AndroidKeys;

public interface IKeywords {
	public boolean isSupported(String type);
	
	public void assertbuttonpresent(AndroidNativeDriver driver,
			String buttonText);

	public void assertlocatorpresent(AndroidNativeDriver driver, String locator);

	public void assertMenuItem(AndroidNativeDriver driver, String menuText);

	public void assertpartialtextpresent(AndroidNativeDriver driver, String text);

	public void assertradiobuttonpresent(AndroidNativeDriver driver,
			String buttonText);

	public void assertspinnerpresent(AndroidNativeDriver driver,
			String spinnerText);

	public void asserttextpresent(AndroidNativeDriver driver, String text);

	public void checkbuttonpresent(AndroidNativeDriver driver, String buttonText);

	public void checklocatorpresent(AndroidNativeDriver driver, String locator);

	public void checkradiobuttonpresent(AndroidNativeDriver driver,
			String buttonText);

	public void checktextpresent(AndroidNativeDriver driver, String text);
	
	public void clickback(AndroidNativeDriver driver);

	public void clickbutton(AndroidNativeDriver driver, String buttonText);
	
	public void clickbyid(AndroidNativeDriver driver, String id);
	
	public void clickElementByText(AndroidNativeDriver driver,
			String className, String elementText);
	
	public void clickmenu(AndroidNativeDriver driver);
	
	public void clickradiobutton(AndroidNativeDriver driver, String buttonText);

	public void clickspinner(AndroidNativeDriver driver, String rid,
			String value);

	public void clicktext(AndroidNativeDriver driver, String text);
	
	public void entertext(AndroidNativeDriver driver, int locator, String text);
	
	public void entertext(AndroidNativeDriver driver, String locator,
			String text);
	
	public void openapp();
	
	public void scrollDown(AndroidNativeDriver driver, int noOfTimes);

	public void scrollup(AndroidNativeDriver driver, int noOfTimes);
	
	public boolean verifyElementTextByClassName(AndroidNativeDriver driver,
			String className, String buttonText);
	
	public void verifyscreen(String imagePath) ;
	
	public void waitForScreen(String imagePath, Double time);

}
