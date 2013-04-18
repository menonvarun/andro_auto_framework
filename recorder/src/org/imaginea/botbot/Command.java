package org.imaginea.botbot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.res.Resources.NotFoundException;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

public class Command {
	ViewClasses vc = new ViewClasses();
	JSONObject json=new JSONObject();
	String userAction="";
	Object view;
	List<Object> arguments;
	int id = 0;
	String viewClassName = "";
	String commandData = "";

	public Command() {

	}
	
	public void add(String command, Object view, Object... args) {
		this.userAction = command;
		this.view = view;
		if(args.length==1 && args[0].equals("")){
			this.arguments= new ArrayList<Object>();
		}
		else{
			this.arguments = new ArrayList<Object>(Arrays.asList(args));
		}
		if (view.getClass().getName().contains("Button")) {
			this.userAction = "clickbutton";
		}
		if(String.class.isAssignableFrom(view.getClass())){
			arguments.add(0, (String)view);
			this.userAction="clicktext";
		}
	}
	
	public void add(String command, String... args) {
		this.userAction = command;
		if(args.length==1 && args[0].equals("")){
			this.arguments= new ArrayList<Object>();
		}
		else{
			this.arguments = new ArrayList<Object>(Arrays.asList(args));
		}
	}

	public void add(String command, View view, Object... args) {
		this.userAction = command;
		this.view = view;
		if(args.length==1 && args[0].equals("")){
			this.arguments= new ArrayList<Object>();
		}
		else{
			this.arguments = new ArrayList<Object>(Arrays.asList(args));
		}
		String className = view.getClass().getName();
		if (vc.isSupportedClass(className)) {
			this.viewClassName = vc.getFullClassName(className);
		} else {
			this.viewClassName = "";
		}

		this.id = view.getId();

		try {
			if(arguments.size()>=1){
				arguments.add(0, getStringIdFromResource(view, id));
			}else{
				arguments.add(getStringIdFromResource(view, id));
			}
			if (command.contentEquals("click")) {
				this.userAction = "clickbyid";
			}
			
		} catch (NotFoundException e) {
			//Escaping in case the rid resource is not found
		}
	}
	
	public void add(String command, MenuItem item) {
		this.view = item;		
		this.arguments= new ArrayList<Object>();
		this.userAction="clicktext";
		arguments.add(item.getTitle());
	}
	

	private String getStringIdFromResource(View view, int id)
			throws NotFoundException {
		String rid = "";
		String tmp = (String) view.getContext().getResources()
				.getResourceName(id);
		if (tmp.contains("/")) {
			rid = tmp.substring(tmp.lastIndexOf("/") + 1);
		}
		return rid;
	}
	
	private void createCommandData(){
		try {
			json.put("command", this.userAction);

			json.put("viewClassName", this.viewClassName);
			for (int i = 0; i < arguments.size(); i++) {
				json.put("args[" + i + "]", arguments.get(i));
			}
			commandData=json.toString();
			commandData = commandData.replace("\"", "\\\"");
			
			Log.i("bot-bot","command data is:"+commandData);
		} catch (JSONException e) {
			Log.i("bot-bot", "unable to generate the JSON data in command.");
			Log.i("bot-bot", e.getMessage());
		}
	}

	public String getData() {
		if(commandData.contentEquals("")) createCommandData();
		return commandData;
	}

	public void add(String data) {
		try{
			JSONObject testJson= new JSONObject(data);
			this.commandData=data;
		}catch(JSONException jse){
			json=new JSONObject();
			try{
				json.put("command", data);
			}catch(JSONException e){
				Log.i("bot-bot", "unable to generate the JSON data in command.");
				Log.i("bot-bot", e.getMessage());
			}
			this.commandData=json.toString();
		}		
		Log.i("bot-bot","Web Command data is:"+commandData);
		this.commandData = this.commandData.replace("\\\"", "");
		this.commandData = this.commandData.replace("\"", "\\\"");
	}

	@Override
	public String toString() {
		String retText;
		try {
			retText = "command =" + this.userAction + ";";
					if(view!=null) retText=retText+"view=" + view.toString();
					retText=retText+ "; viewClassName=" + this.viewClassName + "; id=" + id
					+ "; args[0]=" + arguments.get(0).toString()
					+ "; args[1]=" + arguments.get(1).toString()
					+ "; args[2]=" + arguments.get(2).toString();
		} catch (IndexOutOfBoundsException e) {
			retText = "command =" + this.userAction + ";";
			if(view!=null) retText=retText+"view=" + view.toString();
			retText=retText	+ "; viewClassName=" + this.viewClassName + "; id=" + id
					+ "; args=" + arguments.toString();
		}
		return retText;
	}
}
