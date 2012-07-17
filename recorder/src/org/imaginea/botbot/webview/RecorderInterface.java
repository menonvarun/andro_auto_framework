package org.imaginea.botbot.webview;

import org.imaginea.botbot.Recorder;

import android.util.Log;

public class RecorderInterface {
	String htmlContent="";
	
	public void record(String action, String args){
		
		Recorder.record(action, args);
	}
	
	public void recorderAdded(){
		Log.i("bot-bot", "recorder integrated");
	}
	
	public void printHtml(String html){
		Log.i("bot-bot",html);
	}
	
	public void storeHtmlContent(String html){
		this.htmlContent=html;
	}
	
	public boolean searchText(String text){
		return htmlContent.contains(text);
	}
}
