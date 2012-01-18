package org.imaginea.botbot;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.Socket;
import java.net.URL;
import java.util.List;
import java.util.Map;


public class CommandTransmitter {
	Socket soc = null;
	PrintStream ps = null;
	static String sessionID = null;
	String desktopIP = "10.0.2.2";

	public CommandTransmitter() {
		sessionID = createSession();
	}

	public void publish(Command command) {
		createRecord(sessionID, command.getData());
	}

	public boolean checkSession(String id) {
		try {
			URL url = null;
			url = new URL("http://" + desktopIP
					+ ":8080/bot-bot-server/api/recordsessions/" + id);
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			connection.setDoInput(true);
			connection.setRequestProperty("Accept", "application/json");
			connection.setRequestProperty("Content-Type",
					"application/json; charset=UTF-8");
			BufferedReader in = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));
			String inputLine;
			while ((inputLine = in.readLine()) != null)
				System.out.println(inputLine);
			in.close();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public void createRecord(String sessionId, String data) {
		try {
			URL url = null;
			url = new URL("http://" + desktopIP
					+ ":8080/bot-bot-server/api/recordentries");
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			connection.setDoOutput(true);
			connection.setRequestProperty("Accept", "application/json");
			connection.setRequestProperty("Content-Type",
					"application/json; charset=UTF-8");
			OutputStreamWriter out = new OutputStreamWriter(
					connection.getOutputStream());
			out.write("{\"entryNo\":\"6\",\"recordSession\":{\"id\":\""
					+ sessionId
					+ "\"},\"entryTime\":\"2011-02-02 11:11:11\",\"payload\":\""
					+ data + "\"");
			out.close();

			Map<String, List<String>> respHeaders = connection
					.getHeaderFields();

			if (connection.getResponseCode() == 201) {
				System.out.print(respHeaders.get("Location"));
			} else {
				System.out.println("Invalid Response");
			}
			BufferedReader in = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));
			String inputLine;
			while ((inputLine = in.readLine()) != null)
				System.out.println(inputLine);
			in.close();
		} catch (Exception e) {
			System.out.println("Unable to create record because of :"
					+ e.getMessage());
			System.out
					.println("System will continue to work without recording.");
		}
	}

	public String createSession() {
		try {
			URL url = null;
			url = new URL("http://" + desktopIP
					+ ":8080/bot-bot-server/api/recordsessions");
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			connection.setDoOutput(true);
			connection.setRequestProperty("Accept", "application/json");
			connection.setRequestProperty("Content-Type",
					"application/json; charset=UTF-8");
			OutputStreamWriter out = new OutputStreamWriter(
					connection.getOutputStream());
			out.write("{\n\"name\":\"bot-bot record\"\n}");
			out.close();

			Map<String, List<String>> respHeaders = connection
					.getHeaderFields();

			if (connection.getResponseCode() == 201) {
				String temp = respHeaders.get("Location").get(0);
				System.out.print(temp);
				return temp.substring(temp.lastIndexOf("/") + 1);
			} else {
				System.out.println("Invalid Response");
				return null;
			}
		} catch (Exception e) {
			System.out.println("Unable to create session because of :"
					+ e.getMessage());
			System.out
					.println("System will continue to work without recording.");
			return null;
		}
	}
}
