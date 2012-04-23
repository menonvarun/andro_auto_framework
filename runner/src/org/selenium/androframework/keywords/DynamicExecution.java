package org.selenium.androframework.keywords;

import java.util.Arrays;

import org.selenium.androframework.common.Command;
import org.selenium.androframework.common.Prefrences;


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
		if(methodMap.containsKey(command.getName())){
			if(methodMap.get(command.getName())==command.noOfParameters())
				return true;
		}
		return false;
	}

	@Override
	public void execute(Command command) {
		invoker(context, command.getName(), Arrays.asList(command.getParameters()));
	}

}
