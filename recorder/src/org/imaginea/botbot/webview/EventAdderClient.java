package org.imaginea.botbot.webview;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.imaginea.botbot.ServerProperties;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.webkit.HttpAuthHandler;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class EventAdderClient extends WebViewClient{
private WebViewClient existingClient;
	
	public EventAdderClient(WebView view){
		this.existingClient=getExistingWebViewClient(view);
	}

	@Override
	public void onLoadResource(WebView view, String url) {
		if (this.existingClient != null) {
			this.existingClient.onLoadResource(view, url);
		} else
			super.onLoadResource(view, url);
	}
	
	@Override
	public void onPageStarted(WebView view, String url, Bitmap favicon) {
		if (this.existingClient != null) {
			this.existingClient.onPageStarted(view, url, favicon);
		} else
			super.onPageStarted(view, url, favicon);
    }


	@Override
	public void onReceivedError(WebView view, int errorCode,
			String description, String failingUrl) {
		if (this.existingClient != null) {
			this.existingClient.onReceivedError(view, errorCode, description,
					failingUrl);
		} else
			super.onReceivedError(view, errorCode, description, failingUrl);
	}

	@Override
	public void onFormResubmission(WebView view, Message dontResend,
			Message resend) {
		if (this.existingClient != null) {
			this.existingClient.onFormResubmission(view, dontResend, resend);
		} else
			super.onFormResubmission(view, dontResend, resend);
	}

	@Override
	public void doUpdateVisitedHistory(WebView view, String url,
			boolean isReload) {
		if (this.existingClient != null) {
			this.existingClient.doUpdateVisitedHistory(view, url, isReload);
		} else
			super.doUpdateVisitedHistory(view, url, isReload);
	}

	@Override
	public void onReceivedSslError(WebView view, SslErrorHandler handler,
			SslError error) {
		if (this.existingClient != null) {
			this.existingClient.onReceivedSslError(view, handler, error);
		} else
			super.onReceivedSslError(view, handler, error);
	}

	@Override
	public void onReceivedHttpAuthRequest(WebView view,
			HttpAuthHandler handler, String host, String realm) {
		if (this.existingClient != null) {
			this.existingClient.onReceivedHttpAuthRequest(view, handler, host,
					realm);
		} else
			super.onReceivedHttpAuthRequest(view, handler, host, realm);
	}

	@Override
	public boolean shouldOverrideKeyEvent(WebView view, KeyEvent event) {
		if (this.existingClient != null) {
			return this.existingClient.shouldOverrideKeyEvent(view, event);
		} else
			return super.shouldOverrideKeyEvent(view, event);
	}

	@Override
	public void onUnhandledKeyEvent(WebView view, KeyEvent event) {
		if (this.existingClient != null) {
			this.existingClient.onUnhandledKeyEvent(view, event);
		} else
			super.onUnhandledKeyEvent(view, event);
	}

	@Override
	public void onScaleChanged(WebView view, float oldScale, float newScale) {
		if (this.existingClient != null) {
			this.existingClient.onScaleChanged(view, oldScale, newScale);
		} else
			super.onScaleChanged(view, oldScale, newScale);
	}
	
	private WebViewClient getExistingWebViewClient(WebView wview) {
		Object parentObject = null;
		WebViewClient client=null;
		Class<? extends Object> klass = wview.getClass();
		// Checking for WebView Class
		while (!klass.equals(WebView.class)) {
			klass = klass.getSuperclass();
		}
		try {
			// Checking for mCallbackProxy
			Field f = klass.getDeclaredField("mCallbackProxy");
			f.setAccessible(true);
			parentObject = f.get(wview);

			Class<? extends Object> parentClass = parentObject.getClass();
			Method mth = parentClass.getDeclaredMethod("getWebViewClient");
			client = (WebViewClient) mth.invoke(parentObject);

		} catch (Exception e) {
			Log.i("bot-bot", e.getMessage());
		}
		return client;
	}

	private String openJs(String fileName) {
		String jscontent="";
		try{
		AssetManager assetManager = ServerProperties.getResources().getAssets();
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
	@Override
	public void onPageFinished(WebView view, String url) {
		if(this.existingClient!=null){
			this.existingClient.onPageFinished(view, url);
		}
		view.loadUrl("javascript:"+this.openJs("jquery1.7.2.js"));
		view.loadUrl("javascript:"+this.openJs("recorder.js"));
		view.loadUrl("javascript:"+"botbot.addListener();");
		view.getSettings().setJavaScriptEnabled(true);
	}
	
	@Override
	public boolean shouldOverrideUrlLoading(WebView view, String url) {
		//view.loadUrl(url);
		if(this.existingClient!=null){
			return this.existingClient.shouldOverrideUrlLoading(view, url);
		} else
			return false;
	}
}
