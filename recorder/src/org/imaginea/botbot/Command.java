package org.imaginea.botbot;

import android.content.res.Resources.NotFoundException;
import android.view.View;

public class Command {
	ViewClasses vc = new ViewClasses();

	public Command() {

	}

	String userAction;
	Object view;
	Object[] args;
	int id = 0;
	String rid = "";
	String viewClassName = "";

	public void add(String command, Object view, Object... args) {
		this.userAction = command;
		this.view = view;
		this.args = args;
		if (view.getClass().getName().contains("Button")) {
			this.userAction = "clickbutton";
		}

	}

	public void add(String command, View view, Object... args) {
		this.userAction = command;
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
				this.userAction = "clickbyid";
			}
		} catch (NotFoundException e) {
			this.rid = "";
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

	public String getData() {
		String data = "";
		int argLength;
		data = data.concat("\"command\":\"" + this.userAction + "\"");
		data = data.concat(",\"rid\":\"" + this.rid + "\"");
		data = data.concat(",\"viewClassName\":\"" + this.viewClassName + "\"");
		try {
			argLength = args.length;
		} catch (NullPointerException e) {
			argLength=0;
		}
		for (int i = 0; i < argLength; i++) {

			data = data.concat(",\"args[" + i + "]\":\"" + this.args[i] + "\"");
		}

		data = data.replace("\"", "\\\"");
		data = "{".concat(data).concat("}");
		return data;
	}

	public void add(String command) {
		this.userAction = command;

	}

	@Override
	public String toString() {
		String retText;
		try {
			retText = "command =" + this.userAction + "; view=" + view.toString()
					+ "; viewClassName=" + this.viewClassName + "; id=" + id
					+ "; rid=" + rid + "; args[0]=" + args[0].toString()
					+ "; args[1]=" + args[1].toString();
		} catch (ArrayIndexOutOfBoundsException e) {
			retText = "command =" + this.userAction + "; view=" + view.toString()
					+ "; viewClassName=" + this.viewClassName + "; id=" + id
					+ "; rid=" + rid + "; args=" + args.toString();
		}
		return retText;
	}
}
