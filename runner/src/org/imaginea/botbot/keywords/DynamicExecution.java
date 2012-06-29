package org.imaginea.botbot.keywords;

import java.util.Arrays;

import org.imaginea.botbot.common.Command;
import org.imaginea.botbot.common.Prefrences;

import android.util.Log;


public class DynamicExecution extends BaseKeywordDefinitions{
	private Prefrences prefrences;
	private Object context;
	public DynamicExecution(Prefrences prefrences){
		this.prefrences=prefrences;
		this.context=prefrences.getExecutionContext();
		collectSupportedMethods(context.getClass());
	}
	
	@Override
	public boolean methodSUpported(Command command) {
		if (methodMap.containsKey(command.getName())) {
			return true;
		}
		return false;
	}

	@Override
	public void execute(Command command) {
		invoker(context, command.getName(), Arrays.asList(command.getParameters()));
	}

}
