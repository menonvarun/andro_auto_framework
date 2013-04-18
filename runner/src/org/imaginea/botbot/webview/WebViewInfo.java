package org.imaginea.botbot.webview;

import android.webkit.WebView;
/**
 * Stores the information of a webview
 * 
 * @author Varun Menon
 *
 */
public class WebViewInfo {
	private WebView view;
	private WebViewRunnerClient runnerClient;
	private WebViewRunnerInterface runnerInterface;
	
	public WebView getView() {
		return view;
	}
	public WebViewInfo setView(WebView view) {
		this.view = view;
		return this;
	}
	public WebViewRunnerClient getRunnerClient() {
		return runnerClient;
	}
	public WebViewInfo setRunnerClient(WebViewRunnerClient runnerClient) {
		this.runnerClient = runnerClient;
		return this;
	}
	public WebViewRunnerInterface getRunnerInterface() {
		return runnerInterface;
	}
	public WebViewInfo setRunnerInterface(WebViewRunnerInterface runnerInterface) {
		this.runnerInterface = runnerInterface;
		return this;
	}
	
}
