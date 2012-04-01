package org.selenium.androframework.common;

public class Command {
	String name;
	String[] parameters={};
	
	public String getName(){
		return name;
	}
	
	public String[] getParameters(){
		return parameters;
	}
	
	public void setName(String name){
		this.name=name;
	}
	
	public void setParameters(String[] parameters){
		this.parameters=parameters;
	}
	
	public int noOfParameters(){
		return parameters.length;
	}
	
}
