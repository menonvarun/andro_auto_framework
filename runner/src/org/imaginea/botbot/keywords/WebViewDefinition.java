package org.imaginea.botbot.keywords;

import java.util.ArrayList;
import java.util.Arrays;

import junit.framework.Assert;

import org.imaginea.botbot.common.Command;
import org.imaginea.botbot.common.Prefrences;
import org.imaginea.botbot.webview.WebViewInfo;
import org.imaginea.botbot.webview.WebViewRunnerClient;
import org.imaginea.botbot.webview.WebViewRunnerInterface;
import org.imaginea.botbot.webview.WebViewUtil;

import android.os.SystemClock;
import android.view.View;
import android.webkit.WebView;

import com.jayway.android.robotium.solo.Solo;

public class WebViewDefinition extends BaseKeywordDefinitions {
	private Solo solo;
	//private HashMap<WebView, WebViewClient> webViewClientMap=new HashMap<WebView, WebViewClient>();
	//private HashMap<WebView, WebViewRunnerInterface> webViewInterfaceMap=new HashMap<WebView, WebViewRunnerInterface>();
	private ArrayList<WebViewInfo> viewInfoList = new ArrayList<WebViewInfo>();
	private WebViewUtil webUtil=new WebViewUtil();
	private int TIMEOUT=30000;
	
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
		ArrayList<View> views = solo.getViews();
		boolean available = false;
		if (views.size() > 0) {
			for (View view : views) {
				if (view.getClass().isAssignableFrom(WebView.class)) {
					available = true;
					WebViewRunnerInterface webIntrfc = new WebViewRunnerInterface();
					WebViewRunnerClient webClient = new WebViewRunnerClient();
					
					((WebView) view).addJavascriptInterface(webIntrfc,"ibotbot");
					//((WebView) view).setWebViewClient(webClient);
					viewInfoList.add(new WebViewInfo().setView((WebView)view).setRunnerClient(webClient).setRunnerInterface(webIntrfc));
				}
			}
		}
		return available;
	}
	
	public void clickwebelement(String locator){
		this.clickwebelement(locator, 0);
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
					Thread.sleep(500);
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
	
	public void clickwebelement(String locator,int index){
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
	
	public void enterwebtext(String locator,String text){
		this.enterwebtext(locator, 0, text);
	}
	
	public void enterwebtext(String locator,int index,String text){
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

}
