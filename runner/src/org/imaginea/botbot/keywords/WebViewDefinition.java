package org.imaginea.botbot.keywords;

import java.util.ArrayList;
import java.util.Arrays;

import junit.framework.Assert;

import org.imaginea.botbot.common.Command;
import org.imaginea.botbot.common.Prefrences;
import org.imaginea.botbot.webview.UIThreadRunnerUtil;
import org.imaginea.botbot.webview.UiRunnableListener;
import org.imaginea.botbot.webview.WebViewInfo;
import org.imaginea.botbot.webview.WebViewRunnerClient;
import org.imaginea.botbot.webview.WebViewRunnerInterface;
import org.imaginea.botbot.webview.WebViewUtil;

import android.os.SystemClock;
import android.view.View;
import android.app.Activity;
import android.webkit.WebView;

import com.jayway.android.robotium.solo.Solo;

public class WebViewDefinition extends BaseKeywordDefinitions {
	private Solo solo;
	private ArrayList<WebViewInfo> viewInfoList = new ArrayList<WebViewInfo>();
	private WebViewUtil webUtil=new WebViewUtil();
	private int TIMEOUT=30000;
	private Activity prevActivity;
	
	public WebViewDefinition(Prefrences prefrences){
		Object executionContext=prefrences.getExecutionContext();
		if(executionContext instanceof Solo){
			this.solo=(Solo) executionContext;
		}else{
			this.solo=null;
		}
		collectSupportedMethods(this.getClass());
	}
	
	@Override
	public boolean methodSUpported(Command command) {
		boolean supported = false;
		if (this.solo != null && methodMap.containsKey(command.getName()) && isWebViewAvailable()) {
			supported = true;
		}
		return supported;
	}

	@Override
	public void execute(Command command) {
		invoker(this, command.getName(), Arrays.asList(command.getParameters()));
	}
	
	private boolean isWebViewAvailable(){
		if(prevActivity!=null && prevActivity==solo.getCurrentActivity())
			return true;
		
		boolean available = false;
		final long endTime = SystemClock.uptimeMillis() + 10000;
		while((SystemClock.uptimeMillis() < endTime) & !available){
			ArrayList<View> views = solo.getViews();
			prevActivity=solo.getCurrentActivity();
			viewInfoList.clear();
			if (views.size() > 0) {
				for (View view : views) {
					if (view.getClass().isAssignableFrom(WebView.class)) {
						WebView wview=(WebView)view;
						available = true;
						WebViewRunnerInterface webIntrfc = new WebViewRunnerInterface();
						WebViewRunnerClient webClient = new WebViewRunnerClient((WebView)view);
						
						Activity currentActivity=(Activity) view.getContext();
						
						InitWebView initWebView = new InitWebView(wview, webIntrfc, webClient);
						UIThreadRunnerUtil scriptRunUtil=new UIThreadRunnerUtil(currentActivity,initWebView);
						scriptRunUtil.startOnUiAndWait();						
						
						viewInfoList.add(new WebViewInfo().setView(wview).setRunnerClient(webClient).setRunnerInterface(webIntrfc));
					}
				}
			}
		}
		return available;
	}
	
	public void clickwebelement(String locator){
		this.clickwebelement(locator, 0);
	}
	
	public void clickwebtext(String text){
		this.clickwebtext(text, 0);
	}

	public void waitforelementpresent(String locator, int index,long timeout){
		final long endTime = SystemClock.uptimeMillis() + timeout;
		boolean found = false;
		try {
			while (SystemClock.uptimeMillis() < endTime) {
				for (WebViewInfo viewInfo : viewInfoList) {
					found = webUtil.isElementPresent(viewInfo, locator, index);
					if (found)
						return;
					Thread.sleep(200);
				}
			}
		} catch (InterruptedException e) {
		}
		Assert.assertTrue("Unable to locate element with locator: "+locator+" and index: "+index, found);
	}
	
	public void waitforelementpresent(String locator, int index){
		this.waitforelementpresent(locator, index, TIMEOUT);
	}
	
	public void waitforelementpresent(String locator){
		this.waitforelementpresent(locator, 0, TIMEOUT);
	}
	
	public void waitfortextpresent(String text, int index,long timeout){
		final long endTime = SystemClock.uptimeMillis() + timeout;
		boolean found = false;
		try {
			while (SystemClock.uptimeMillis() < endTime) {
				for (WebViewInfo viewInfo : viewInfoList) {
					found = webUtil.isTextPresent(viewInfo, text, index);
					if (found)
						return;
					Thread.sleep(200);
				}
			}
		} catch (InterruptedException e) {
		}
		Assert.assertTrue("Unable to locate element with text: "+text+" and index: "+index, found);
	}
	
	public void waitfortextpresent(String locator, int index){
		this.waitfortextpresent(locator, index, TIMEOUT);
	}
	
	
	
	public void clickwebelement(String locator,int index){
		waitforelementpresent(locator, index);
		boolean executed=false;
			for (WebViewInfo viewInfo : viewInfoList) {
				if(webUtil.isElementPresent(viewInfo, locator, index) && !executed){
					webUtil.clickElement(viewInfo, locator, index);
					executed=true;
				}
			}
			String message="Unable to click on web element with locator: "+locator+" and index: "+index+". Element not found or it is not visible.";
			Assert.assertTrue(message, executed);
	}
	
	public void clickwebtext(String text,int index){
		waitfortextpresent(text, index);
		boolean executed=false;
			for (WebViewInfo viewInfo : viewInfoList) {
				if(webUtil.isTextPresent(viewInfo, text, index) && !executed){
					webUtil.clickElementBasedOnText(viewInfo, text, index);
					executed=true;
				}
			}
			String message="Unable to click on web element with text: "+text+" and index: "+index+". Element not found or it is not visible.";
			Assert.assertTrue(message, executed);
	}
	
	public void enterwebtext(String locator,String text){
		this.enterwebtext(locator, 0, text);
	}
	
	public void enterwebtext(String locator,int index,String text){
		waitforelementpresent(locator, index);
		boolean executed=false;
		for (WebViewInfo viewInfo : viewInfoList) {
			if(webUtil.isElementPresent(viewInfo, locator, index) && !executed){
				webUtil.enterText(viewInfo, locator, index, text);
				executed=true;
			}
			String message="Unable to enter text on web element with locator: "+locator+" and index: "+index+". Element not found or it is not visible.";
			Assert.assertTrue(message, executed);
		}
	}
	
	public void waitForPageToLoad(long timeout){
		final long endTime = SystemClock.uptimeMillis() + timeout;
		boolean loaded = false;
		try {
			while (SystemClock.uptimeMillis() < endTime) {
				for (WebViewInfo viewInfo : viewInfoList) {
					loaded = viewInfo.getRunnerClient().pageLoaded();
					if (loaded)
						return;
					Thread.sleep(500);
				}
			}
		} catch (InterruptedException e) {
		}
	}
	
	private class InitWebView implements UiRunnableListener {
		private WebView view;
		private WebViewRunnerInterface webIntrfc;
		private WebViewRunnerClient webClient;

		public InitWebView(WebView view, WebViewRunnerInterface webIntrfc,
				WebViewRunnerClient webClient) {
			this.view = view;
			this.webIntrfc = webIntrfc;
			this.webClient = webClient;
		}

		public void onRunOnUIThread() {
			view.addJavascriptInterface(webIntrfc, "ibotbot");
			/*view.setWebViewClient(webClient);
			view.setWebChromeClient(new WebChromeClient() {
				@Override
				public boolean onConsoleMessage(ConsoleMessage cm) {
					Log.i("bot-bot",
							cm.message() + " -- From line " + cm.lineNumber()
									+ " of " + cm.toString());
					return true;
				}
			});*/
			view.reload();
		}

	}

}
