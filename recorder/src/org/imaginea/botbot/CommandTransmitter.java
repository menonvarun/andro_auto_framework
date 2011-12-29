package org.imaginea.botbot;

import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import org.json.JSONObject;

public class CommandTransmitter {
	Socket soc = null;
	PrintStream ps = null;

	public CommandTransmitter() {
		try {
			soc = new ServerSocket(7000).accept();
			ps= new PrintStream(soc.getOutputStream());
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void publish(JSONObject obj){
		ps.print(obj.toString());
		ps.flush();
	}
}
