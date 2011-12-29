package org.imaginea.botbot;

import org.json.JSONException;
import org.json.JSONObject;

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
	String tag = "";
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
			this.tag = (String) view.getContext().getResources()
					.getResourceName(id);
		} catch (NotFoundException e) {
			this.tag = "";
		}
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
			json.put("tag", this.tag);
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
		String retText = "command =" + this.command + "; view="
				+ view.toString() + "; viewClassName=" + this.viewClassName
				+ "; id=" + id + "; tag=" + tag + "; args=" + args;
		return retText;
	}
}
