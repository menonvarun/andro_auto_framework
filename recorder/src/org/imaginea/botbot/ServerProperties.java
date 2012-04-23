package org.imaginea.botbot;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.test.mock.MockContext;

public class ServerProperties {
	String serverIP = "10.0.2.2";
	String port = "8080";
	String sessionName = "bot-bot-recorder";
	String serverName = "bot-bot-server";
	static Resources resources;
	
	public ServerProperties() {
		

		// Read from the /assets directory
		try {
			AssetManager assetManager = resources.getAssets();
			InputStream inputStream = assetManager.open("recorder.properties");
			Properties properties = new Properties();
			properties.load(inputStream);
			serverIP=properties.getProperty("SERVER_IP","10.0.2.2");
			port=properties.getProperty("SERVER_PORT","8080");
			sessionName=properties.getProperty("SESSION_NAME","bot-bot-recorder");
			serverName=properties.getProperty("SERVER_NAME","");
			System.out.println("The properties are now loaded");
			System.out.println("properties: " + properties);
		} catch (Exception err) {
			System.err.println("Failed to open microlog property file");
			err.printStackTrace();
		}
	}
	
	public static void setResources(Context context){
		ServerProperties.resources = context.getResources();
	}
}
