package org.imaginea.botbot.keywords;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.imaginea.botbot.api.DefaultProperties;
import org.imaginea.botbot.common.Command;
import org.imaginea.botbot.common.Prefrences;

import android.util.Log;

import com.google.android.testing.nativedriver.client.AndroidNativeDriver;
import com.jayway.android.robotium.solo.Solo;


public class KeywordCaller {
	private List<BaseKeywordDefinitions> keywordDefinitions = new ArrayList<BaseKeywordDefinitions>();
	IKeywords kd;
	private String framework;
	Prefrences prefrences;

	private void initializeDefinitions(){
		framework = "#FRAMEWORK#";
		prefrences.setFramework(framework);
		keywordDefinitions.add(new NativeDriverKeywordDefinitions(prefrences));
		keywordDefinitions.add(new RobotiumKeywordDefinition(prefrences));
		keywordDefinitions.add(new DynamicExecution(prefrences));
	}

	public KeywordCaller(Prefrences prefrences){
		this.prefrences=prefrences;
		System.out.println(prefrences.getExecutionContext().getClass().getName());
		initializeDefinitions();
	}

	public void execute(Command command){
		boolean found=false;
		for(BaseKeywordDefinitions definition:keywordDefinitions){
			if(definition.methodSUpported(command)){				
				definition.execute(command);
				found=true;
				break;
			}
		}
		Assert.assertTrue("Unable to find definition for the command: "+command.getName(),found);
	}

}
