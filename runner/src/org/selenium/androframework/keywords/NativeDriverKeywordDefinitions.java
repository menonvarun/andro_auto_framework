package org.selenium.androframework.keywords;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.selenium.androframework.api.IdentifyByType;
import org.selenium.androframework.api.UsefulFunctions;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Screen;
import org.testng.Assert;

import com.google.android.testing.nativedriver.client.AndroidNativeDriver;
import com.google.android.testing.nativedriver.client.AndroidNativeElement;
import com.google.android.testing.nativedriver.client.ClassNames;
import com.google.android.testing.nativedriver.common.AndroidKeys;
import com.google.android.testing.nativedriver.common.AndroidNativeBy;

public class NativeDriverKeywordDefinitions implements IKeywords{
	UsefulFunctions uf = new UsefulFunctions();
	
	public boolean isSupported(String type){
		if(type.equalsIgnoreCase("nativedriver"))
			return true;
		
		return false;
	}
	
	public void openapp() {

	}

	public void checktextpresent(AndroidNativeDriver driver, String text) {
		boolean result = uf.waitForElementPresent(driver,
				AndroidNativeBy.partialText(text));
		if (!result) {
			System.out.println("Conintuing the execution even the text :"
					+ text + " is not found.");
		}
	}

	public void checkbuttonpresent(AndroidNativeDriver driver, String buttonText) {
		boolean found = verifyElementTextByClassName(driver, ClassNames.BUTTON,
				buttonText);
		if (!found) {
			System.out.println("Unable to find the button with said text: "
					+ buttonText + ". Continuing with the execution.");
		}
	}

	public void checklocatorpresent(AndroidNativeDriver driver, String locator) {
		boolean result = uf.waitForElementPresent(driver,
				IdentifyByType.getLocatorType(locator));
		if (!result) {
			System.out.println("Conintuing the execution even the text :"
					+ locator + " is not found.");
		}
	}

	public void assertbuttonpresent(AndroidNativeDriver driver,
			String buttonText) {
		boolean found = verifyElementTextByClassName(driver, ClassNames.BUTTON,
				buttonText);
		if (!found) {
			Assert.fail("Unable to find button with text :" + buttonText);
		}

	}

	public void assertlocatorpresent(AndroidNativeDriver driver, String locator) {
		boolean result = uf.waitForElementPresent(driver,
				IdentifyByType.getLocatorType(locator));
		Assert.assertTrue(result, "Test failed. Unable to find locator: "
				+ locator);
	}

	public void assertpartialtextpresent(AndroidNativeDriver driver, String text) {
		boolean result = uf.waitForElementPresent(driver,
				AndroidNativeBy.partialText(text));
		Assert.assertTrue(result, "Test failed. Unable to find text: " + text);
	}

	public void asserttextpresent(AndroidNativeDriver driver, String text) {
		boolean result = uf.waitForElementPresent(driver,
				AndroidNativeBy.partialText(text));
		String elementText = driver.findElement(
				AndroidNativeBy.partialText(text)).getText();
		Assert.assertTrue(result, "Test failed. Unable to find text: " + text);
		Assert.assertTrue(text.contentEquals(elementText),
				"Test failed. Text dont match expected: " + text
						+ " but present is: " + elementText);
	}

	public void clickback(AndroidNativeDriver driver) {
		driver.getKeyboard().sendKeys(AndroidKeys.BACK);
	}

	public void clickbutton(AndroidNativeDriver driver, String buttonText) {
		clickElementByText(driver, ClassNames.BUTTON, buttonText);
	}

	public void clickradiobutton(AndroidNativeDriver driver, String buttonText) {
		clickElementByText(driver, ClassNames.RADIO_BUTTON, buttonText);
	}

	public void clickspinner(AndroidNativeDriver driver, String rid,
			String value) {
		clickbyid(driver, rid);
		asserttextpresent(driver, value);
		clicktext(driver, value);
	}

	public void clicktext(AndroidNativeDriver driver, String text) {
		WebElement element = driver.findElementByPartialText(text);
		element.click();
	}

	public void assertspinnerpresent(AndroidNativeDriver driver,
			String spinnerText) {
		boolean found = verifyElementTextByClassName(driver,
				"android.widget.Spinner", spinnerText);
		if (!found) {
			Assert.fail("Unable to find spinner with text :" + spinnerText);
		}
	}

	public void clickmenu(AndroidNativeDriver driver) {
		driver.getKeyboard().sendKeys(AndroidKeys.MENU);
	}

	public void entertext(AndroidNativeDriver driver, String locator,
			String text) {
		boolean result = uf.waitForElementPresent(driver,
				IdentifyByType.getLocatorType(locator));
		if (!result) {
			Assert.fail("Unable to find element :" + locator);
		}
		AndroidNativeElement element = driver.findElement(IdentifyByType
				.getLocatorType(locator));
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			Assert.fail(e.getMessage());
		}
		element.click();
		element.clear();
		element.sendKeys(text);
		// element.click();

	}

	public void entertext(AndroidNativeDriver driver, int locator, String text) {
		List<WebElement> textFields = driver
				.findElementsByClassName(ClassNames.EDIT_TEXT);
		if (textFields.size() > locator) {
			Assert.fail("Unable to find test edit box at position " + locator);
		}
		WebElement textElement = textFields.get(locator - 1);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			Assert.fail(e.getMessage());
		}
		textElement.click();
		textElement.clear();
		textElement.sendKeys(text);
		// element.click();

	}

	public void verifyscreen(String imagePath) {
		Screen s = new Screen();
		try {
			s.wait(imagePath, 30);
		} catch (FindFailed e) {
			System.out.println(e);
			Assert.fail("Unable to verify the screen");
		}
	}

	public void waitForScreen(String imagePath, Double time) {
		Screen s = new Screen();
		if (time == null) {
			time = (double) 30;
		}
		try {
			s.wait(imagePath, time);
		} catch (FindFailed e) {
			System.out.println(e);
			Assert.fail("Unable to verify the screen");
		}
	}

	public void assertMenuItem(AndroidNativeDriver driver, String menuText) {
		List<WebElement> menuItems = driver
				.findElementsByClassName("android.view.ContextMenu");
		boolean found = false;
		for (WebElement menuItem : menuItems) {
			String text = menuItem.getText();
			if (menuText.contentEquals(text)) {
				found = true;
				break;
			}
		}
		if (!found) {
			Assert.fail("Unable to find menu with text :" + menuText);
		}

	}

	public void assertradiobuttonpresent(AndroidNativeDriver driver,
			String buttonText) {
		boolean found = verifyElementTextByClassName(driver,
				ClassNames.RADIO_BUTTON, buttonText);
		if (!found) {
			Assert.fail("Unable to find radio button with text :" + buttonText);
		}

	}

	public void checkradiobuttonpresent(AndroidNativeDriver driver,
			String buttonText) {
		boolean found = verifyElementTextByClassName(driver,
				ClassNames.RADIO_BUTTON, buttonText);
		if (!found) {
			System.out.println("Unable to find radio button with said text: "
					+ buttonText + ". Continuing with the execution.");
		}
	}

	public boolean verifyElementTextByClassName(AndroidNativeDriver driver,
			String className, String buttonText) {
		List<WebElement> buttons = driver.findElementsByClassName(className);
		boolean found = false;
		for (WebElement button : buttons) {
			String text = button.getText();
			if (buttonText.contentEquals(text)) {
				found = true;
				break;
			}
		}
		return found;
	}

	public void clickElementByText(AndroidNativeDriver driver,
			String className, String elementText) {
		List<WebElement> elements = driver.findElementsByClassName(className);
		boolean found = false;
		for (WebElement element : elements) {
			String text = element.getText();
			if (elementText.contentEquals(text)) {
				element.click();
				found = true;
				break;
			}
		}
		if (!found) {
			Assert.fail("Unable to find element with text- " + elementText
					+ " for clicking");
		}
	}

	public void clickbyid(AndroidNativeDriver driver, String id) {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			Assert.fail(e.getMessage());
		}
		boolean result = uf.waitForElementPresent(driver,
				IdentifyByType.getLocatorType(id));
		if (!result) {
			Assert.fail("Unable to find element with id: "
					+ id);
		}
		AndroidNativeElement element = driver.findElement(AndroidNativeBy
				.id(id));
		element.click();
	}

	public void scrollup(AndroidNativeDriver driver, int noOfTimes) {
		for (int i = 1; i <= noOfTimes; i++) {
			driver.getKeyboard().sendKeys(AndroidKeys.DPAD_UP);
		}

	}

	public void scrollDown(AndroidNativeDriver driver, int noOfTimes) {
		for (int i = 1; i <= noOfTimes; i++) {
			driver.getKeyboard().sendKeys(AndroidKeys.DPAD_DOWN);
		}

	}
}
