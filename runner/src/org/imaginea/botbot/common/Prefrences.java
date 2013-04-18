package org.imaginea.botbot.common;

public class Prefrences {
	Object executionContext;
	Object assetManager;
	public Object getAssetManager() {
		return assetManager;
	}

	public void setAssetManager(Object assetManager) {
		this.assetManager = assetManager;
	}

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
