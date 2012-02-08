package org.imaginea.botbot;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.Socket;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

import android.os.AsyncTask;
import android.util.Log;



public class CommandTransmitter {
	Socket soc = null;
	PrintStream ps = null;
	static String sessionID = null;
	static String serverIP = IServerProperties.serverIP;
	static String port=IServerProperties.port;
	static String sessionName=IServerProperties.sessionName;
	static String serverName=IServerProperties.serverName;
	BlockingQueue<String> queue = new LinkedBlockingQueue<String>();

	public CommandTransmitter() {

		System.out.println("Creating session....");
		new CreateSessionTask().execute();

	}

	public void publish(Command command) {
		queue.offer(command.getData());
		
	}

	public boolean checkSession(String id) {
		try {
			URL url = null;
			url = new URL("http://" + CommandTransmitter.serverIP
					+ ":"+port+"/"+serverName+"/api/recordsessions/" + id);
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

	
	/**
	 * @author moiz
	 * 
	 * CreateSessionTask is a sub class to create an AsyncTask
	 * AsyncTask is a thread which runs  parallel to the main UI thread.
	 *  
	 *
	 */
	

	class CreateSessionTask extends AsyncTask<Void, Void, Void> {
		int recordID=1;
		int prevRecord=1;
		/**
		 * This method is called when CreateSessionTask.execute() is invoked.
		 * This method performs two actions, first it creates a session and then it waits for population of
		 * queue in a while loop, when queue is not empty it removes the head and writes the data to the server.
		 */
		@Override
		protected Void doInBackground(Void... nothing) {

			// TODO Auto-generated method stub
			// creating session
			try {
				System.out.println("In background");
				recordID=1;
				prevRecord=0;
				URL url = null;
				url = new URL("http://" + CommandTransmitter.serverIP
						+ ":"+port+"/"+serverName+"/api/recordsessions");
				HttpURLConnection connection = (HttpURLConnection) url
						.openConnection();
				connection.setDoOutput(true);
				connection.setRequestProperty("Accept", "application/json");
				connection.setRequestProperty("Content-Type",
						"application/json; charset=UTF-8");
				OutputStreamWriter out = new OutputStreamWriter(
						connection.getOutputStream());
				out.write("{\n\"name\":\""+sessionName+"\"\n}");
				out.close();

				Map<String, List<String>> respHeaders = connection
						.getHeaderFields();

				if (connection.getResponseCode() == 201) {
					String temp = respHeaders.get("Location").get(0);
					System.out.print(temp);

					CommandTransmitter.sessionID = temp.substring(temp
							.lastIndexOf("/") + 1);
					Log.i("TASK", "Session ID received: "
							+ CommandTransmitter.sessionID);
					System.out.println("Connected ......"
							+ CommandTransmitter.sessionID);
				} else {
					System.out.println("Invalid Response");

				}
			} catch (Exception e) {
				System.out.println("Unable to create session because of task:"
						+ e);
				System.out
						.println("System will continue to work without recording.");

			}

			while (true) {

				try {
					//it waits until queue is populated
					postRecord(queue.take());
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

		}
		//Performs writing data to the server

		void postRecord(String data) {
			// TODO Auto-generated method stub
			try {
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String currentDateTime = df.format(new Date());
				System.out.println("In create record Async task"
						+ CommandTransmitter.sessionID + data);
				Log.i("Async DAta", data);
				URL url = null;
				url = new URL("http://" + CommandTransmitter.serverIP
						+ ":"+port+"/"+serverName+"/api/recordentries");
				HttpURLConnection connection = (HttpURLConnection) url
						.openConnection();
				connection.setDoOutput(true);
				connection.setRequestProperty("Accept", "application/json");
				connection.setRequestProperty("Content-Type",
						"application/json; charset=UTF-8");
				OutputStreamWriter out = new OutputStreamWriter(
						connection.getOutputStream());
				out.write("{\"entryNo\":\""+recordID+"\""+"\"prevEntryNo\":\""+prevRecord+"\",\"recordSession\":{\"id\":\""
						+ CommandTransmitter.sessionID
						+ "\"},\"entryTime\":\""+currentDateTime+"\",\"payload\":\""
						+ data + "\"");
				out.close();
				prevRecord=recordID;
				recordID++;
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
				System.out.println("Unable to create record because of :" + e);
				System.out
						.println("System will continue to work without recording.");
			}

		}
	}

}



