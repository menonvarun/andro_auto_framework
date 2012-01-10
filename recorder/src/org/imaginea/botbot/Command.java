package org.imaginea.botbot;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.content.res.Resources.NotFoundException;
import android.view.View;

public class Command {
	ViewClasses vc = new ViewClasses();

	public Command() {

	}

	String command;
	Object view;
	Object[] args;
	int id = 0;
	String rid = "";
	String viewClassName = "";

	public void add(String command, Object view, Object... args) {
		this.command = command;
		this.view = view;
		this.args = args;
		if (view.getClass().getName().contains("Button")) {
			this.command = "clickbutton";
		}
		

	}

	public void add(String command, View view, Object... args) {
		this.command = command;
		this.view = view;
		this.args = args;
		String className = view.getClass().getName();
		if (vc.isSupportedClass(className)) {
			this.viewClassName = vc.getFullClassName(className);
		} else {
			this.viewClassName = "";
		}
		
		this.id = view.getId();
		
		try {
			this.rid = getStringIdFromResource(view, id);
			if (command.contentEquals("click")) {
				this.command = "clickbyid";
			}
		} catch (NotFoundException e) {
			this.rid="";
		}
	}

	private String getStringIdFromResource(View view, int id)
			throws NotFoundException {
		String rid = "";
		String tmp = (String) view.getContext().getResources()
				.getResourceName(id);
		if (tmp.contains(":id")) {
			rid = tmp.substring(tmp.lastIndexOf(":id") + 4);
		}
		return rid;
	}

	public JSONObject getJson() {
		JSONObject json = new JSONObject();
		try {
			json.put("command", this.command);
			if (this.view == null) {
				json.put("view", "");
			} else {
				json.put("view", this.view);
			}
			if (this.args == null) {
				json.put("args", "");
			} else {
				json.put("args", this.args);
			}
			json.put("viewClassName", this.viewClassName);
			json.put("id", this.id);
			json.put("tag", this.rid);
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
		return json;
	}

	public void add(String command) {
		this.command = command;

	}

	@Override
	public String toString() {
		String retText;
		try {
			retText = "command =" + this.command + "; view=" + view.toString()
					+ "; viewClassName=" + this.viewClassName + "; id=" + id
					+ "; rid=" + rid + "; args[0]=" + args[0].toString()
					+ "; args[1]=" + args[1].toString();
		} catch (ArrayIndexOutOfBoundsException e) {
			retText = "command =" + this.command + "; view=" + view.toString()
					+ "; viewClassName=" + this.viewClassName + "; id=" + id
					+ "; rid=" + rid + "; args=" + args.toString();
		}
		return retText;
	}
}
