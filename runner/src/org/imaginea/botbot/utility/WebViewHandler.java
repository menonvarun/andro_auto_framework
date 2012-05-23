package org.imaginea.botbot.utility;

import java.util.ArrayList;
import java.util.Iterator;

import android.util.Log;
import android.view.View;
import android.webkit.ConsoleMessage;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import com.jayway.android.robotium.solo.Solo;

public class WebViewHandler {

	// Clicking on link in web view
	public static void clickOnLinkInWebView(WebView browser, String link,
			Solo solo) {
		browser.getSettings().setJavaScriptEnabled(true);

		// Javascript to click on anchor link in web view
		browser.loadUrl("javascript:links=document.getElementsByTagName('a');var linkPresent=false;"
				+ "for(var i=0;i<links.length;i++){"
				+ "if(links[i].innerHTML=='"
				+ link
				+ "'){ linkPresent=true;"
				+ "var event = document.createEvent('MouseEvents');"
				+ "event.initMouseEvent('click', true, window, 1, 0, 0, 0, 0, false, false, false, 1, document.body.parentNode);"
				+ "links[i].dispatchEvent(event);"
				+ "}"
				+ "} if(linkPresent==false){console.log('ConsoleMessage'+'LinkNotPresent');}");

		// Checking if link is not present in web view
		if (isConsoleMessage(browser, "LinkNotPresent", solo)) {
			throw new junit.framework.AssertionFailedError();
		}
		solo.sleep(10000);
	}

	// Checking for text present in web view
	public static boolean isTextPresentInWebView(WebView browser, String text,
			Solo solo) {
		ChromeClient client = ChromeClient.getChromeClient();
		browser.setWebChromeClient(client);
		int myWait = 0;
		browser.getSettings().setJavaScriptEnabled(true);
		client.setFinalText(text);
		// Passing entire html doc to console log
		browser.loadUrl("javascript:console.log('ConsoleMessage'+document.getElementsByTagName('html')[0].innerHTML);");
		browser.setWebChromeClient(client);
		// waiting for console message for 30 seconds
		while (client.isTextPresent == false) {
			solo.sleep(500);
			myWait++;
			if (myWait > 60)
				return false;
		}
		return client.isTextPresent();
	}

	// returning instance of web view if not present retruning null
	public static WebView getInstanceOfWebView(Solo solo) {
		ArrayList<View> listViews = solo.getViews();
		Iterator<View> iterator = listViews.iterator();
		while (iterator.hasNext()) {
			View view = iterator.next();
			if (view instanceof WebView) {
				return (WebView) view;
			}
		}
		return null;
	}

	// checking for console messages
	static boolean isConsoleMessage(WebView browser, String text, Solo solo) {
		ChromeClient client = ChromeClient.getChromeClient();
		browser.setWebChromeClient(client);
		int myWait = 0;
		browser.getSettings().setJavaScriptEnabled(true);
		client.setFinalText(text);
		browser.setWebChromeClient(client);
		while (client.isTextPresent == false) {
			solo.sleep(500);
			myWait++;
			if (myWait > 60)
				return false;
		}

		return client.isTextPresent();
	}

	// Webchrome client
	static class ChromeClient extends WebChromeClient {

		private String finalText;
		boolean isTextPresent = false;
		static ChromeClient client;
		// Creating singleton object
		private ChromeClient() {
			// TODO Auto-generated constructor stub
		}

		static ChromeClient getChromeClient() {
			if (client == null)
				client = new ChromeClient();
			return client;
		}

		void setFinalText(String text) {
			finalText = text;
			isTextPresent = false;
		}

		@Override
		public boolean onConsoleMessage(ConsoleMessage cmsg) {
			// checking prefix

			if (cmsg.message().startsWith("ConsoleMessage")) {
				String msg = cmsg.message().substring(14); // strip off prefix
				isTextPresent = msg.contains(finalText);

				return true;
			}
			return false;
		}

		boolean isTextPresent() {
			return isTextPresent;
		}

	}

}
