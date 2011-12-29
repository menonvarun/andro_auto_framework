package org.imaginea.botbot;

import java.util.ArrayList;
import java.util.logging.Logger;

import android.util.Log;
import android.view.Menu;
import android.view.View;

public class Recorder {
	static ArrayList<Command> commandList = new ArrayList<Command>();
	private static final String LOG_TAG = "debugger";
	private static CommandTransmitter ct = new CommandTransmitter();

	public static void record(String command, Object view, Object... args) {
		if (command.contentEquals("entertext")) {
			Command temp = commandList.get(commandList.size() - 1);
			if ((temp.command.contentEquals("entertext"))
					&& (temp.view.equals(view))) {
				commandList.remove(temp);
			}
		}
		Command comm = new Command();
		try {
			comm.add(command, ((View) view), args);

		} catch (ClassCastException e) {
			comm.add(command, view, args);
		}
		ct.publish(comm.getJson());
		commandList.add(comm);
		Log.i(LOG_TAG, "command received: " + comm.toString());
	}

	public static void record(String command) {
		Command comm = new Command();
		comm.add(command);
		commandList.add(comm);
	}

	public static void record(String command, View view) {
		Command comm = new Command();
		comm.add(command, view, "");
		commandList.add(comm);
		Log.i(LOG_TAG, "command received: " + comm.toString());

	}

	public static void record(String command, Menu menu) {
		Command comm = new Command();
		comm.add(command);
		commandList.add(comm);
	}
}
