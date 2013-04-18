package org.imaginea.botbot.webview;

import junit.framework.Assert;
/**
 * THe java script interface to be used along with the webview
 * 
 * @author Varun Menon
 *
 */
public class WebViewRunnerInterface {
	private boolean successfull;
	private boolean elementFound;
	private boolean textFound;
	private String message;
	
	public WebViewRunnerInterface(){
		resetValues();
	}
	
	private void resetValues(){
		this.successfull=false;
		this.elementFound=false;
		this.textFound=false;
		this.message="No message defined";
	}
	
	public void assertMethod(String value,String msg){
		Assert.assertTrue(msg, value.contentEquals("true"));
	}
	
	public void successfull(){
		this.successfull=true;
	}
	
	public boolean isSuccessfull(){
		boolean temp=successfull;
		resetValues();
		return temp;
	}
	
	public void setMessage(String message){
		this.message=message;
	}
	
	public String getMessage(){
		String temp=this.message;
		resetValues();
		return temp;
	}
	
	public void elementFound(){
		this.elementFound=true;
	}
	
	public void textFound(){
		this.textFound=true;
	}
	
	public boolean isElementFound(){
		boolean temp=this.elementFound;
		resetValues();
		return temp;
	}
	
	public boolean isTextFound(){
		boolean temp=this.textFound;
		resetValues();
		return temp;
	}
}
