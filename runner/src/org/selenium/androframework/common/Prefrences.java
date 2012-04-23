package org.selenium.androframework.common;

public class Prefrences {
	Object executionContext;
	String framework="";
	
	public void setExecutionContext(Object executionContext){
		this.executionContext=executionContext;
	}
	
	public void setFramework(String framework){
		this.framework=framework;
	}
	
	public String getFramework(){
		return framework;
	}
	
	public Object getExecutionContext(){
		return executionContext;
	}
}
