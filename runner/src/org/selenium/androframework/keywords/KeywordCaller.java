package org.selenium.androframework.keywords;

import java.util.ArrayList;

import org.testng.Assert;

import com.google.android.testing.nativedriver.client.AndroidNativeDriver;

public class KeywordCaller {
	AndroidNativeDriver driver = null;
	KeywordDefinitions kd = new KeywordDefinitions();

	public KeywordCaller(AndroidNativeDriver driver) {
		this.driver = driver;
	}

	public void methodCaller(String key, ArrayList<String> args) {
		Keywords keyValue = null;
		String locator, argument1, argument2;
		try {
			locator = args.get(0);
		} catch (IndexOutOfBoundsException e) {
			locator = "";
		}
		try {
			argument1=args.get(1);
		} catch (IndexOutOfBoundsException e) {
			argument1 = "";
		}
		try {
			argument2=args.get(2);
		} catch (IndexOutOfBoundsException e) {
			argument2 = "";
		}
		
		
		try {
			keyValue = Keywords.valueOf(key);
		} catch (IllegalArgumentException e) {
			Assert.fail("Metioned keyword not found :" + key);
		}
		switch (keyValue) {
		case openapp:
		case checktextpresent:
			kd.checktextpresent(driver, argument1);
			break;
		case checkbuttonpresent:
			kd.checkbuttonpresent(driver, argument1);
			break;
		case checklocatorpresent:
			kd.checklocatorpresent(driver, locator);
			break;
		case assertbuttonpresent:
			kd.assertbuttonpresent(driver, argument1);
			break;
		case assertlocatorpresent:
			kd.assertlocatorpresent(driver, locator);
			break;
		case assertpartialtextpresent:
			kd.assertpartialtextpresent(driver, argument1);
			break;
		case asserttextpresent:
			kd.asserttextpresent(driver, argument1);
			break;
		case clickback:
			kd.clickback(driver);
			break;
		case clickbutton:
			kd.clickbutton(driver, argument1);
			break;
		case clickmenu:
			kd.clickmenu(driver);
			break;
		case entertext:
			if (locator=="" || argument1=="") {
				Assert.fail("entertext keyword take 2 arguments locator and the value in argument1.");
			}
			try {
				kd.entertext(driver, Integer.parseInt(locator), argument1);
			} catch (NumberFormatException e) {
				kd.entertext(driver, locator, argument1);
			}
			break;
		case verifyscreen:
			kd.verifyscreen(argument1);
			break;
		case waitforscreen:
			if (argument2 == "") {
				kd.waitForScreen(argument1, null);
			} else {
				kd.waitForScreen(argument1, Double.parseDouble(argument2));
			}
			break;
		case assertmenuitem:
			kd.assertMenuItem(driver, argument1);
			break;
		case assertradiobuttonpresent:
			kd.assertradiobuttonpresent(driver, argument1);
			break;
		case checkradiobuttonpresent:
			kd.checkradiobuttonpresent(driver, argument1);
			break;
		case assertspinnerpresent:
			kd.assertspinnerpresent(driver, argument1);
			break;
		case clickspinner:
			kd.clickspinner(driver, locator, argument1);
			break;
		case clickradiobutton:
			kd.clickradiobutton(driver, argument1);
			break;
		case clickbyid:
			kd.clickbyid(driver, locator);
			break;
		case clicktext:
			kd.clicktext(driver, argument1);
			break;
		case scrollup:
			int noOfTimes;
			if (argument1 == "" || args == null) {
				noOfTimes = 1;
			} else
				noOfTimes = Integer.parseInt(argument1);
			kd.scrollup(driver, noOfTimes);
			break;
		case scrolldown:
			int noOfTimesDown;
			if (argument1 == "" || args == null) {
				noOfTimesDown = 1;
			} else
				noOfTimesDown = Integer.parseInt(argument1);
			kd.scrollup(driver, noOfTimesDown);
			break;
		default:
			Assert.fail("Mentioned keyword not supported: " + key);
		}
	}
}
