package org.imaginea.botbot.keywords;

public interface IKeywords {
	public boolean isSupported(String type);
	
	public void assertbuttonpresent(
			String buttonText);

	public void assertlocatorpresent( String locator);

	public void assertMenuItem( String menuText);

	public void assertpartialtextpresent( String text);

	public void assertradiobuttonpresent(
			String buttonText);

	public void assertspinnerpresent(
			String spinnerText);

	public void asserttextpresent( String text);

	public void checkbuttonpresent( String buttonText);

	public void checklocatorpresent( String locator);

	public void checkradiobuttonpresent(
			String buttonText);

	public void checktextpresent( String text);
	
	public void clickback();

	public void clickbutton( String buttonText);
	
	public void clickbyid( String id);
	
	public void clickmenu();
	
	public void clickradiobutton( String buttonText);

	public void clickspinner( String rid,
			String value);

	public void clicktext( String text);
	
	public void entertext( int locator, String text);
	
	public void entertext( String locator,
			String text);
	
	public void openapp();
	
	public void scrollDown( int noOfTimes);

	public void scrollup( int noOfTimes);
	
	public void verifyscreen(String imagePath) ;
	
	public void waitForScreen(String imagePath, Double time);

}
