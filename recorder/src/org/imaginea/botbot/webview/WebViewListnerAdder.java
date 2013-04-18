package org.imaginea.botbot.webview;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.imaginea.botbot.ServerProperties;

import android.content.res.AssetManager;
import android.util.Log;
import android.webkit.WebView;

public class WebViewListnerAdder {
	WebView wview=null;
	public WebViewListnerAdder(WebView wview){
		this.wview=wview;
	}
	
	public void addListner(){
		wview.getSettings().setJavaScriptEnabled(true);
		wview.addJavascriptInterface(new RecorderInterface(), "irecorder");
		wview.loadUrl("javascript:(function(){if(typeof jQuery=='undefined'){"+this.openJs("jquery1.7.2.js")+"}})();");
		wview.loadUrl("javascript:"+this.openJs("recorder.js"));
		wview.loadUrl("javascript:"+"botbot.addListener();");
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
}
