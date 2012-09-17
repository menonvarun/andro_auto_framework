package org.imaginea.botbot.webview;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.imaginea.botbot.ServerProperties;

import android.content.res.AssetManager;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class EventAdderClient extends WebViewClient{
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
		view.loadUrl("javascript:"+this.openJs("jquery1.7.2.js"));
		view.loadUrl("javascript:"+this.openJs("recorder.js"));
		view.loadUrl("javascript:"+"botbot.addListener();");
	}
	
	@Override
	public boolean shouldOverrideUrlLoading(WebView view, String url) {
		view.loadUrl(url);
		return false;
	}
}
