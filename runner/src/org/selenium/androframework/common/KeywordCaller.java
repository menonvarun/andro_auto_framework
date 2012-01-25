package org.selenium.androframework.common;

import java.util.ArrayList;

import org.testng.Assert;

import com.google.android.testing.nativedriver.client.AndroidNativeDriver;

public class KeywordCaller {
	AndroidNativeDriver driver = null;
	KeywordDefinitions kd = new KeywordDefinitions();

	public KeywordCaller(AndroidNativeDriver driver) {
		this.driver = driver;
	}

	public void methodCaller(String key,ArrayList<String> args) {
		Keywords keyValue = null;
		try {
			keyValue = Keywords.valueOf(key);
		} catch (IllegalArgumentException e) {
			Assert.fail("Metioned keyword not found :" + key);
		}
		switch (keyValue) {
		case openapp:
		case checktextpresent:
			kd.checktextpresent(driver, args.get(0));
			break;
		case checkbuttonpresent:
			kd.checkbuttonpresent(driver, args.get(0));
			break;
		case checklocatorpresent:
			kd.checklocatorpresent(driver, args.get(0));
			break;
		case assertbuttonpresent:
			kd.assertbuttonpresent(driver, args.get(0));
			break;
		case assertlocatorpresent:
			kd.assertlocatorpresent(driver, args.get(0));
			break;
		case assertpartialtextpresent:
			kd.assertpartialtextpresent(driver, args.get(0));
			break;
		case asserttextpresent:
			kd.asserttextpresent(driver, args.get(0));
			break;
		case clickback:
			kd.clickback(driver);
			break;
		case clickbutton:
			kd.clickbutton(driver, args.get(0));
			break;
		case clickmenu:
			kd.clickmenu(driver);
			break;
		case entertext:
			if(args.size()!=2){
				Assert.fail("entertext keyword take 2 arguments locator and the value.");
			}
			try{
				kd.entertext(driver, Integer.parseInt(args.get(0)), args.get(1));
			}catch(NumberFormatException e){
				kd.entertext(driver, args.get(0), args.get(1));
			}
			break;
		case verifyscreen:
			kd.verifyscreen(args.get(0));
			break;
		case waitforscreen:
			if (args.get(1) == "") {
				kd.waitForScreen(args.get(0), null);
			} else {
				kd.waitForScreen(args.get(0), Double.parseDouble(args.get(1)));
			}
			break;
		case assertmenuitem:
			kd.assertMenuItem(driver, args.get(0));
			break;
		case assertradiobuttonpresent:
			kd.assertradiobuttonpresent(driver, args.get(0));
			break;
		case checkradiobuttonpresent:
			kd.checkradiobuttonpresent(driver, args.get(0));
			break;
		case assertspinnerpresent:
			kd.assertspinnerpresent(driver, args.get(0));
			break;
		case clickspinner:
			kd.clickspinner(driver, args.get(0),args.get(1));
			break;
		case clickradiobutton:
			kd.clickradiobutton(driver,  args.get(0));
			break;
		case clickbyid:
			kd.clickbyid(driver, args.get(0));
			break;
		case clicktext:
			kd.clicktext(driver, args.get(0));
			break;
		case scrollup:
			int noOfTimes;
			if(args.get(0)=="" || args ==null){
				noOfTimes=1;
			}else
				noOfTimes=Integer.parseInt(args.get(0));
			kd.scrollup(driver, noOfTimes);
			break;
		case scrolldown:
			int noOfTimesDown;
			if(args.get(0)=="" || args ==null){
				noOfTimesDown=1;
			}else
				noOfTimesDown=Integer.parseInt(args.get(0));
			kd.scrollup(driver, noOfTimesDown);
			break;
		default:
			Assert.fail("Mentioned keyword not supported: "+key);
		}
	}
}
