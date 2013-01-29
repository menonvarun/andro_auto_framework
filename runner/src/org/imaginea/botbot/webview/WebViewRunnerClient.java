package org.imaginea.botbot.webview;

import android.graphics.Bitmap;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebViewRunnerClient extends WebViewClient{
	private WebView cView;
	private boolean loaded=false;
	@Override
	public void onPageFinished(WebView view, String url) {
		this.cView=view;
		this.loaded=true;
	}
	
	@Override
	public boolean shouldOverrideUrlLoading(WebView view, String url) {
		view.loadUrl(url);
		return false;
	}
	
	@Override
	public void onPageStarted(WebView view, String url, Bitmap favicon) {
		this.loaded=false;
    }
	
	public WebView getStroedView(){
		return cView;
	}
	
	public boolean pageLoaded(){
		return this.loaded;
	}
}
