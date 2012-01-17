package org.imaginea.botbot;

import android.app.AlertDialog;
import android.content.res.Resources.NotFoundException;
import android.view.View;
import android.widget.Button;

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
	
	public void addalert(String command, Object[] view, Object... args) {
		if(view[0] instanceof AlertDialog){
			this.view=view[0];
			Integer tmp=(Integer)view[1];
			if((view[1] instanceof Integer)&&((tmp==-1)||(tmp==-2)||(tmp==-3))){
				this.command="clickbutton";
				Button bt = ((AlertDialog)view[0]).getButton(tmp);
				this.args[0]=bt.getText();
			}
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

	public String getData() {
		String data = "";
		data=data.concat("\"command\":\""+this.command+"\"");
		if(!rid.contentEquals("")){
			data=data.concat("\"rid\":\""+this.rid+"\"");
		}else{
			data=data.concat("\"viewClassName\":\""+this.viewClassName+"\"");
		}
		
		for(int i=0;args.length>i;i++){
			data=data.concat("\"args["+i+"]\":\""+this.args[i]+"\"");
		}
		data=data.replace("\"", "\\\"");
		data="{".concat(data).concat("}");
		return data;	
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
