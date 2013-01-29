package org.imaginea.botbot.webview;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import junit.framework.Assert;

import android.content.res.AssetManager;
import android.util.Log;
import android.webkit.WebView;

public class WebViewUtil {
	private static AssetManager assetManager;
	
	public static void setAssetManager(AssetManager assetManager){
		WebViewUtil.assetManager=assetManager;
	}
	
	private String openJs(String fileName) {
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
	
	private String getLoadedScripts(){
		String script="";
		script+=this.openJs("jquery1.7.2.js");
		script+=this.openJs("runner.js");
		return script;
	}
	
	public void clickElement(WebViewInfo viewInfo,String locator,int index){
		WebView view=viewInfo.getView();
		WebViewRunnerInterface runnerInterface=viewInfo.getRunnerInterface();
		
		String executeScript="javascript:";
		executeScript+=this.getLoadedScripts();
		executeScript+="botbotrunner.clickwebelement('"+locator+"',"+index+");";
		
		view.loadUrl(executeScript);
		
		Assert.assertTrue(runnerInterface.getMessage(), runnerInterface.isSuccessfull());
	}
	
	public void enterText(WebViewInfo viewInfo,String locator,int index,String text){
		WebView view=viewInfo.getView();
		WebViewRunnerInterface runnerInterface=viewInfo.getRunnerInterface();
		
		String executeScript="javascript:";
		executeScript+=this.getLoadedScripts();
		executeScript+="botbotrunner.enterwebtext('"+locator+"',"+index+",'"+text+"');";
		
		view.loadUrl(executeScript);
		
		Assert.assertTrue(runnerInterface.getMessage(), runnerInterface.isSuccessfull());
	}
	
	public boolean isElementPresent(WebViewInfo viewInfo,String locator, int index){
		WebView view=viewInfo.getView();
		WebViewRunnerInterface runnerInterface=viewInfo.getRunnerInterface();
		
		String executeScript="javascript:";
		executeScript+=this.getLoadedScripts();
		executeScript+="botbotrunner.iselementpresent('"+locator+"',"+index+");";
		view.loadUrl(executeScript);
		return runnerInterface.isElementFound();
	}
}
