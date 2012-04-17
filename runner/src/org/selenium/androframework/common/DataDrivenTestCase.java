package org.selenium.androframework.common;


public abstract class DataDrivenTestCase extends RobotiumBaseClass {

	String customTestName="";

	public String getCustomTestName() {
		return customTestName;
	}

	public void setCustomTestName(String customTestName) {
		this.customTestName = customTestName;
	}
	
}
