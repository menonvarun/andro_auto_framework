package org.imaginea.botbot;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.content.res.AssetManager;
import android.util.Log;

public class Utils {
	
	public String openJs(String fileName) {
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
