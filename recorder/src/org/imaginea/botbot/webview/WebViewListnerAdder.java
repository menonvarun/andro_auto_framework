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
		
		//Commenting the following code, which was used for Phonegap based apps.
		/*if(CordovaWebView.class.isAssignableFrom(wview.getClass())){
			wview.loadUrl("javascript:(function(){if(typeof jQuery=='undefined'){"+this.openJs("jquery1.7.2.js")+"}})();");
			wview.loadUrl("javascript:"+this.openJs("recorder.js"));
			wview.loadUrl("javascript:"+"botbot.addListener();");
			//wview.getSettings().setJavaScriptEnabled(true);
			CordovaWebViewClient cClient=getCordovaWebViewClient(wview);
			CordovaInterface cIntf=getCordovaInterface(cClient);
			CordovaWebView cView=getCordovaView(cClient);
			if(IceCreamCordovaWebViewClient.class.isAssignableFrom(cClient.getClass())){
				wview.setWebViewClient(new EventAdderICSCorodovaClient(cIntf, cView, cClient));				
			}else{
				wview.setWebViewClient(new EventAdderCorodovaClient(cIntf, cView, cClient));
			}
		}else{
			wview.setWebViewClient(new EventAdderClient(wview));
			wview.setWebChromeClient(new WebChromeClient() {
				@Override
				public boolean onConsoleMessage(ConsoleMessage cm) {
					Log.i("bot-bot", cm.message()+ " -- From line "
							+ cm.lineNumber() + " of " + cm.toString());
					return true;
				}
			});
		}*/
		
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
	/*private CordovaInterface getCordovaInterface(CordovaWebViewClient client) {
		CordovaInterface cInterface = null;
		//CordovaWebViewClient client=null;
		Class<? extends Object> klass = client.getClass();
		// Checking for WebView Class
		while (!klass.equals(CordovaWebViewClient.class)) {
			klass = klass.getSuperclass();
		}
		try {
			// Checking for mCallbackProxy
			Field cInterfaceField = klass.getDeclaredField("cordova");
			cInterfaceField.setAccessible(true);
			cInterface = (CordovaInterface)cInterfaceField.get(client);

		} catch (Exception e) {
			Log.i("bot-bot", e.getMessage());
		}
		return cInterface;
		
	}
	
	private CordovaWebView getCordovaView(CordovaWebViewClient client) {

		CordovaWebView cAppView = null;
		// CordovaWebViewClient client=null;
		Class<? extends Object> klass = client.getClass();
		// Checking for WebView Class
		while (!klass.equals(CordovaWebViewClient.class)) {
			klass = klass.getSuperclass();
		}
		try {
			// Checking for mCallbackProxy

			Field cAppViewField = klass.getDeclaredField("appView");
			cAppViewField.setAccessible(true);
			cAppView = (CordovaWebView) cAppViewField.get(client);

		} catch (Exception e) {
			Log.i("bot-bot", e.getMessage());
		}
		return cAppView;

	}
	
	private CordovaWebViewClient getCordovaWebViewClient(WebView wview) {

		CordovaWebViewClient client = null;
		Class<? extends Object> klass = wview.getClass();
		// Checking for WebView Class
		while (!klass.equals(CordovaWebView.class)) {
			klass = klass.getSuperclass();
		}
		try {
			// Checking for mCallbackProxy
			Field f = klass.getDeclaredField("viewClient");
			f.setAccessible(true);
			client = (CordovaWebViewClient)f.get(wview);

		} catch (Exception e) {
			Log.i("bot-bot", e.getMessage());
		}
		return client;
	}
*/	
}
