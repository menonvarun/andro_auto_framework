package org.selenium.androframework.keywords;

import java.util.ArrayList;
import java.util.Arrays;

import junit.framework.Assert;

import org.selenium.androframework.common.Command;
import org.selenium.androframework.common.Prefrences;

import android.widget.RadioButton;

import com.jayway.android.robotium.solo.Solo;

public class RobotiumKeywordDefinition extends BaseKeywordDefinitions implements IKeywords {
	private Solo solo;
	
	public RobotiumKeywordDefinition(Prefrences prefrences) {
		Object executionContext=prefrences.getExecutionContext();
		if(executionContext instanceof Solo){
			this.solo=(Solo) executionContext;
		}else{
			this.solo=null;
		}
		collectSupportedMethods(this.getClass());
	}
	
	@Override
	public boolean isSupported(String type) {
		if (type.equalsIgnoreCase("robotium"))
			return true;

		return false;
	}

	@Override
	public void assertbuttonpresent(String buttonText) {
		boolean found = solo.searchButton(buttonText);
		Assert.assertTrue("Unable to find button with the said text.",found);
	}

	@Override
	public void assertlocatorpresent(String locator) {
		Assert.fail("assertlocatorpresent not supported at this moment.");

	}

	@Override
	public void assertMenuItem(String menuText) {
		Assert.fail("assertMenuItem not supported at this moment.");

	}

	@Override
	public void assertpartialtextpresent(String text) {
		boolean found = solo.searchText(text);
		Assert.assertTrue("Unable to find button with the said text.",found);
	}

	@Override
	public void assertradiobuttonpresent(String buttonText) {
		ArrayList<RadioButton> radioButtons = solo.getCurrentRadioButtons();
		boolean found=false;
		for(RadioButton radioButton:radioButtons){
			if(radioButton.getText()==buttonText){
				found =true;
				break;
			}
		}
		Assert.assertTrue("Not able to find the radio button withe text: "+buttonText,found);
	}

	@Override
	public void assertspinnerpresent(String spinnerText) {
		boolean found =solo.isSpinnerTextSelected(spinnerText);
		Assert.assertTrue("Unable to find a spinner with the said text: "+spinnerText,found);
	}

	@Override
	public void asserttextpresent(String text) {
		boolean found =solo.searchText(text);
		Assert.assertTrue("Unable to find said text: "+text,found);
	}

	@Override
	public void checkbuttonpresent(String buttonText) {
		boolean found =solo.searchButton(buttonText);
		if (!found) {
			System.out.println("Unable to find button with said text: "
					+ buttonText + ". Continuing with the execution.");
		}
	}

	@Override
	public void checklocatorpresent(String locator) {
		Assert.fail("checklocatorpresent is not supported at this moment.");
	}

	@Override
	public void checkradiobuttonpresent(String buttonText) {
		ArrayList<RadioButton> radioButtons = solo.getCurrentRadioButtons();
		boolean found=false;
		for(RadioButton radioButton:radioButtons){
			if(radioButton.getText()==buttonText){
				found =true;
				break;
			}
		}
		if (!found) {
			System.out.println("Unable to find radio button with said text: "
					+ buttonText + ". Continuing with the execution.");
		}
	}

	@Override
	public void checktextpresent(String text) {
		boolean found =solo.searchText(text);
		if (!found) {
			System.out.println("Unable to find said text: "
					+ text + ". Continuing with the execution.");
		}
	}

	@Override
	public void clickback() {
		solo.goBack();
	}

	@Override
	public void clickbutton(String buttonText) {
		solo.clickOnButton(buttonText);
	}

	@Override
	public void clickbyid(String id) {
		Assert.fail("clickbyid is not supported at this moment.");
	}

	@Override
	public void clickmenu() {
		solo.sendKey(Solo.MENU);
	}

	@Override
	public void clickradiobutton(String buttonText) {
		ArrayList<RadioButton> radioButtons = solo.getCurrentRadioButtons();
		boolean found=false;
		int i=0;
		for(RadioButton radioButton:radioButtons){
			if(radioButton.getText()==buttonText){
				found =true;
				solo.clickOnRadioButton(i);
				break;
			}
			i++;
		}
		Assert.assertTrue("Unable to find radio button with said text: "
					+ buttonText,found);
	}

	@Override
	public void clickspinner(String rid, String value) {
		Assert.fail("clickspinner is not supported at this moment.");
	}

	@Override
	public void clicktext(String text) {
		solo.clickOnText(text);
	}

	@Override
	public void entertext(int locator, String text) {
		solo.enterText(locator, text);
	}

	@Override
	public void entertext(String locator, String text) {
		int index;
		try{
			index=Integer.parseInt(locator);
			Assert.assertTrue("Unable to find the element after waiting at index: "+index, waitForEditText(index));
			solo.enterText(index, text);
		}catch(NumberFormatException e){
			Assert.fail("entertext based on locator is not supported at this moment.");
		}
	}
	
	private boolean waitForEditText(int index){
		boolean found = true;
		for(int i=0;;i++){
			if(i>=60)
				break;
			else if(solo.getEditText(index)!=null){
				found=true;
				break;
			}
			solo.sleep(1000);
		}
		return found;
	}

	@Override
	public void openapp() {
		Assert.fail("openapp is not supported at this moment.");
	}

	@Override
	public void scrollDown(int noOfTimes) {
		for(int i=0;i<noOfTimes;i++){
			solo.sendKey(Solo.DOWN);
		}

	}

	@Override
	public void scrollup(int noOfTimes) {
		for(int i=0;i<noOfTimes;i++){
			solo.sendKey(Solo.UP);
		}
	}
	
	@Override
	public void verifyscreen(String imagePath) {
		
	}
	
	@Override
	public void waitForScreen(String imagePath, Double time) {
		
	}

	@Override
	public boolean methodSUpported(Command command) {
		boolean supported = false;
		if (this.solo != null && methodMap.containsKey(command.getName())) {
			supported = true;
		}
		return supported;
	
	}
	
	@Override
	public void execute(Command command) {
		invoker(this, command.getName(), Arrays.asList(command.getParameters()));
	}

}
