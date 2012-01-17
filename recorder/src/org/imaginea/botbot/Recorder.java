package org.imaginea.botbot;

import java.util.ArrayList;
import java.util.logging.Logger;

import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;

public class Recorder {
	static ArrayList<Command> commandList = new ArrayList<Command>();
	static Command textCommand=null;
	private static final String LOG_TAG = "debugger";
	private static CommandTransmitter ct = new CommandTransmitter();

	public static void record(String command, Object view, Object... args) {
		if (view instanceof Spinner){
			Object arg = args[0];
			if(arg instanceof TextView){
				String text = ((TextView) arg).getText().toString();
				args[0]=text;
			}
		}
		Command comm = new Command();
		try {
			comm.add(command, ((View) view), args);

		} catch (ClassCastException e) {
			if(view instanceof Object[]){
				comm.addalert(command, (Object[])view, args);
			}else{
				comm.add(command, view, args);
			}
		}

		checkTextCommand(comm);
		commandList.add(comm);
		Log.i(LOG_TAG, "command received: " + comm.toString());
	}
	private static void checkTextCommand(Command comm){
		if (textCommand!=null && (comm.command).contentEquals("entertext") && (comm.view.equals(textCommand.view))) {
			textCommand=comm;
		}else if((comm.command).contentEquals("entertext")){
			if(textCommand!=null)
				ct.publish(textCommand);
			textCommand=comm;
		}else if(textCommand!=null){
			ct.publish(textCommand);
			ct.publish(comm);
		}else{
			ct.publish(comm);
		}
		
	}
	public static void record(String command) {
		Command comm = new Command();
		comm.add(command);
		checkTextCommand(comm);
		commandList.add(comm);
	}

	public static void record(String command, View view) {
		Command comm = new Command();
		comm.add(command, view, "");
		checkTextCommand(comm);
		commandList.add(comm);
		Log.i(LOG_TAG, "command received: " + comm.toString());

	}

	public static void record(String command, Menu menu) {
		Command comm = new Command();
		comm.add(command);
		checkTextCommand(comm);
		ct.publish(comm);
		commandList.add(comm);
	}
}
