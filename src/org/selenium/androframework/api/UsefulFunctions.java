package org.selenium.androframework.api;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import com.google.android.testing.nativedriver.client.AndroidNativeDriver;

public class UsefulFunctions {
	
	public boolean waitForElementPresent(AndroidNativeDriver driver, By by) {
		int timeOut = 60;//Integer.parseInt(locator.getLocator("TIMEOUT"));
		for (int second = 0;; second++) {
			if (second >= timeOut) {
				return false;
			}
			if (this.isElementPresent(driver, by)) {
					return true;
				}
			try {
				System.out.println("Waiting for element for second: " + second);
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				Assert.fail("Failed due to exception: " + e.toString());
			}
		}
	}
	
	public boolean waitForElementEnabled(WebElement element) {
		int timeOut = 60;//Integer.parseInt(locator.getLocator("TIMEOUT"));
		for (int second = 0;; second++) {
			if (second >= timeOut) {
				return false;
			}
			if (element.isEnabled()) {
					return true;
				}
			try {
				System.out.println("Waiting for element for second: " + second);
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				Assert.fail("Failed due to exception: " + e.toString());
			}
		}
	}
	
	public boolean isElementPresent(AndroidNativeDriver driver,By by){
		try{
			driver.findElement(by);
			return true;
		}catch(NoSuchElementException e){
			return false;
		}
	}
}
