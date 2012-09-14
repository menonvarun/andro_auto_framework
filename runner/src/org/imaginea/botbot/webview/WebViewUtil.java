package org.imaginea.botbot.webview;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import junit.framework.Assert;

import android.content.res.AssetManager;
import android.os.SystemClock;
import android.util.Log;
import android.webkit.WebView;

public class WebViewUtil {
	
	private static AssetManager assetManager;
	private static String jqueryScript,runnerScript,jsScript;
	public static void setAssetManager(AssetManager assetManager){
		WebViewUtil.assetManager=assetManager;
		//WebViewUtil.jsScript=getLoadedScripts();
		WebViewUtil.jqueryScript=openJs("jquery1.7.2.js");
		WebViewUtil.runnerScript=openJs("testrunner.js");
	}
	
	private static String openJs(String fileName) {
		String jscontent="";
		try{
		InputStream inputStream = assetManager.open(fileName);
		InputStreamReader isr = new InputStreamReader(inputStream);
        BufferedReader br = new BufferedReader(isr);
        
        String line;
        while (( line = br.readLine()) != null) {
            jscontent += line;
        }
        inputStream.close(); 
		}catch(Exception e){
			Log.e("bot-bot",e.getMessage());
		}
		return jscontent;
	}
	
	private boolean waitForElement(WebViewRunnerInterface runnerInterface){
		final long endTime = SystemClock.uptimeMillis() + 10000;
		boolean found = false;
		try {
			while (SystemClock.uptimeMillis() < endTime) {
					found = runnerInterface.isElementFound();
					if (found)
						break;
					Thread.sleep(200);
			}
		} catch (InterruptedException e) {
		}
		return found;
	}
	
	private boolean waitForCommandSuccess(WebViewRunnerInterface runnerInterface){
		final long endTime = SystemClock.uptimeMillis() + 10000;
		boolean found = false;
		try {
			while (SystemClock.uptimeMillis() < endTime) {
					found = runnerInterface.isSuccessfull();
					if (found)
						break;
					Thread.sleep(200);
			}
		} catch (InterruptedException e) {
		}
		return found;
	}
	
	private static String getLoadedScripts(){
		String script="";
		script+=openJs("jquery1.7.2.js");
		script+=openJs("testrunner.js");
		return script;
	}
	
	private void loadScripts(WebView view){
		view.loadUrl("javascript: "+jqueryScript);
		view.loadUrl("javascript: "+runnerScript);
	}
	
	public void clickElement(WebViewInfo viewInfo,String locator,int index){
		locator=locator.replace("'", "\"");
		WebView view=viewInfo.getView();
		WebViewRunnerInterface runnerInterface=viewInfo.getRunnerInterface();
		loadScripts(view);
		String executeScript="javascript:";
		executeScript+=" botbotrunner.clickwebelement('"+locator+"',"+index+");";
		view.loadUrl(executeScript);
		
		boolean success= waitForCommandSuccess(runnerInterface);
		String errorMsg=runnerInterface.getMessage();
		Assert.assertTrue(errorMsg,success );
	}
	
	public void clickElementBasedOnText(WebViewInfo viewInfo,String text,int index){
		text=text.replace("'", "\"");
		WebView view=viewInfo.getView();
		WebViewRunnerInterface runnerInterface=viewInfo.getRunnerInterface();
		loadScripts(view);
		String executeScript="javascript:";
		executeScript+=" botbotrunner.clickwebtext('"+text+"',"+index+");";
		view.loadUrl(executeScript);
		
		boolean success= waitForCommandSuccess(runnerInterface);
		String errorMsg=runnerInterface.getMessage();
		Assert.assertTrue(errorMsg,success );
	}
	
	public void enterText(WebViewInfo viewInfo,String locator,int index,String text){
		locator=locator.replace("'", "\"");
		WebView view=viewInfo.getView();
		WebViewRunnerInterface runnerInterface=viewInfo.getRunnerInterface();
		loadScripts(view);
		String executeScript="javascript:";
		executeScript+=" botbotrunner.enterwebtext('"+locator+"',"+index+",'"+text+"');";
		view.loadUrl(executeScript);
		
		boolean success= waitForCommandSuccess(runnerInterface);
		String errorMsg=runnerInterface.getMessage();
		Assert.assertTrue(errorMsg,success );
	}
	
	public boolean isElementPresent(WebViewInfo viewInfo,String locator, int index){
		locator=locator.replace("'", "\"");
		WebView view=viewInfo.getView();
		WebViewRunnerInterface runnerInterface=viewInfo.getRunnerInterface();
		loadScripts(view);
		String executeScript="javascript:";
		executeScript+=" botbotrunner.iselementpresent('"+locator+"',"+index+");";
		view.loadUrl(executeScript);
		return waitForElement(runnerInterface);
	}
	
	public boolean isTextPresent(WebViewInfo viewInfo,String text, int index){
		text=text.replace("'", "\"");
		WebView view=viewInfo.getView();
		WebViewRunnerInterface runnerInterface=viewInfo.getRunnerInterface();
		loadScripts(view);
		String executeScript="javascript:";
		executeScript+=" botbotrunner.istextpresent('"+text+"',"+index+");";
		view.loadUrl(executeScript);
		return waitForElement(runnerInterface);
	}
}
