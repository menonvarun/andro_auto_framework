package org.selenium.androframework.keywords;

import java.util.ArrayList;
import java.util.List;

import org.selenium.androframework.api.DefaultProperties;
import org.testng.Assert;
import com.google.android.testing.nativedriver.client.AndroidNativeDriver;
import com.jayway.android.robotium.solo.Solo;


public class KeywordCaller {
	private AndroidNativeDriver driver = null;
	private Solo solo =null;
	private List<IKeywords> keywordDefinitions = new ArrayList<IKeywords>();
	IKeywords kd;
	private String framework;

	private void initializeFrameworks() {
		framework = DefaultProperties.getDefaultProperty()
				.getValueFromProperty("FRAMEWORK");
		keywordDefinitions.add(new NativeDriverKeywordDefinitions(this.driver));
		keywordDefinitions.add(new RobotiumKeywordDefinition(this.solo));
		
		for (IKeywords keywordDefinition : keywordDefinitions) {
			if (keywordDefinition.isSupported(framework)) {
				kd = keywordDefinition;
				break;
			}
		}
		if (kd == null) {
			if (framework.equalsIgnoreCase(""))
				Assert.fail("Either you havent defined the variable \"FRAMEWORK\" under default.properties or you had done this intentionally to test me.");
			else
				Assert.fail("Sorry the framework that you entered \""
						+ framework
						+ "\" is not currently supported by us at this moment.");
		}
	}

	public KeywordCaller(AndroidNativeDriver driver) {
		this.driver = driver;
		this.initializeFrameworks();
	}
	
	public KeywordCaller(Solo solo) {
		this.solo = solo;
		this.initializeFrameworks();
	}
	
	private enum Keywords {
		openapp, checkbuttonpresent, clickbutton, clickmenu, checktextpresent, assertbuttonpresent,
		asserttextpresent,assertpartialtextpresent,checklocatorpresent,assertlocatorpresent,entertext,clickback,
		verifyscreen,waitforscreen,assertmenuitem,assertradiobuttonpresent,checkradiobuttonpresent,
		assertspinnerpresent,clickspinner,clickradiobutton,clickbyid,clicktext,scrollup,scrolldown

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
			break;
		case checktextpresent:
			kd.checktextpresent(argument1);
			break;
		case checkbuttonpresent:
			kd.checkbuttonpresent(argument1);
			break;
		case checklocatorpresent:
			kd.checklocatorpresent(locator);
			break;
		case assertbuttonpresent:
			kd.assertbuttonpresent(argument1);
			break;
		case assertlocatorpresent:
			kd.assertlocatorpresent(locator);
			break;
		case assertpartialtextpresent:
			kd.assertpartialtextpresent(argument1);
			break;
		case asserttextpresent:
			kd.asserttextpresent(argument1);
			break;
		case clickback:
			kd.clickback();
			break;
		case clickbutton:
			kd.clickbutton(argument1);
			break;
		case clickmenu:
			kd.clickmenu();
			break;
		case entertext:
			if (locator=="" || argument1=="") {
				Assert.fail("entertext keyword take 2 arguments locator and the value in argument1.");
			}
			try {
				kd.entertext(Integer.parseInt(locator), argument1);
			} catch (NumberFormatException e) {
				kd.entertext(locator, argument1);
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
			kd.assertMenuItem(argument1);
			break;
		case assertradiobuttonpresent:
			kd.assertradiobuttonpresent(argument1);
			break;
		case checkradiobuttonpresent:
			kd.checkradiobuttonpresent(argument1);
			break;
		case assertspinnerpresent:
			kd.assertspinnerpresent(argument1);
			break;
		case clickspinner:
			kd.clickspinner(locator, argument1);
			break;
		case clickradiobutton:
			kd.clickradiobutton(argument1);
			break;
		case clickbyid:
			kd.clickbyid(locator);
			break;
		case clicktext:
			kd.clicktext(argument1);
			break;
		case scrollup:
			int noOfTimes;
			if (argument1 == "" || args == null) {
				noOfTimes = 1;
			} else
				noOfTimes = Integer.parseInt(argument1);
			kd.scrollup(noOfTimes);
			break;
		case scrolldown:
			int noOfTimesDown;
			if (argument1 == "" || args == null) {
				noOfTimesDown = 1;
			} else
				noOfTimesDown = Integer.parseInt(argument1);
			kd.scrollup(noOfTimesDown);
			break;
		default:
			Assert.fail("Mentioned keyword not supported: " + key);
		}
	}
}
