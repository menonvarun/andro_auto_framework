package org.imaginea.botbot.webview;

import android.webkit.WebView;
/**
 * Used to execute java script methods on the webview
 * @author Varun Menon
 *
 */
public class WebViewCommandRunner implements UiRunnableListener{
	
	private WebView view;
	private String script;
	
	public WebViewCommandRunner(WebView view,String script){
		this.view=view;
		this.script=script;
	}
	
	public void onRunOnUIThread(){
		this.view.loadUrl(script);		
	}
}
