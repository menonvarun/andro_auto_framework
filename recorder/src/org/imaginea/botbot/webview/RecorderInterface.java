package org.imaginea.botbot.webview;

import org.imaginea.botbot.Recorder;

import android.util.Log;

public class RecorderInterface {
	
	public void record(String data){
		Recorder.webViewRecord(data);
	}
	
	public void recorderAdded(){
		Log.i("bot-bot", "Bot-Bot Recorder integrated");
	}
	
	public void printHtml(String html){
		Log.i("bot-bot",html);
	}
	
}
