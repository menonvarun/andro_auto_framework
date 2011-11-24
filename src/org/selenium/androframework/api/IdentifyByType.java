package org.selenium.androframework.api;

import org.openqa.selenium.By;

import junit.framework.Assert;

import com.google.android.testing.nativedriver.common.AndroidNativeBy;

public class IdentifyByType {
	private enum SupportedByType {
		xpath, css, id, classname, tagname, name, linktext, partiallinktext,text,partialtext
	}

	public static By getLocatorType(String locator) {
		SupportedByType sbt=null;
		String[] st = locator.split(",");
		
		if (st.length > 1) {
			locator=st[1];
			try{
			sbt =SupportedByType.valueOf(st[0]);
			}catch(IllegalArgumentException e){
				Assert.fail("Locator type mentioned "+st[0]+" not supported.");
			}
			switch (sbt) {
			case xpath:
				return By.xpath(locator);
			case css:
				return By.cssSelector(locator);
			case id:
				return By.id(locator);
			case classname:
				return By.className(locator);
			case tagname:
				return By.tagName(locator);
			case name:
				return By.name(locator);
			case linktext:
				return By.linkText(locator);
			case partiallinktext:
				return By.partialLinkText(locator);
			case text:
				return AndroidNativeBy.text(locator);
			case partialtext:
				return AndroidNativeBy.partialText(locator);
			default:
				return null;
			}
		}else{
			return By.id(locator);
		}
	}
}
