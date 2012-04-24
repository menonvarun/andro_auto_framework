package org.imaginea.botbot.common;

import java.util.ArrayList;
import java.util.List;

import org.imaginea.botbot.keywords.RobotiumKeywordDefinition;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import android.content.Context;
import android.test.AndroidTestRunner;
import android.util.Log;

public class BotBotTestRunner extends AndroidTestRunner{
	private List<TestCase> botBotTestCases;
	private String dataDrivenClasses="#DATA_DRIVEN_TESTS#";
	private ArrayList<Class<?>> dataDrivenTests= new ArrayList<Class<?>>();
	Context context;
	private String appPackageName="#APP_TEST_PACKAGE#";

	public BotBotTestRunner(Context context){
		addDataDrivenTests();
		this.setTest(getDataDrivenTest());
		this.context=context;
		RobotiumKeywordDefinition.storeRId(context, appPackageName);
	}
	
	private void addDataDrivenTests(){
		String classNames[] = dataDrivenClasses.split(",");
		for (String className : classNames) {
			try {
				Class cls = Class.forName(className);
				dataDrivenTests.add(cls);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	@Override
	public void setTest(Test test) {
		List<TestCase> tempTestCases=super.getTestCases();
		super.setTest(test);
		botBotTestCases=getTestCases();
		if(tempTestCases!=null){
			botBotTestCases.addAll(tempTestCases);
		}
		TestSuite suite = new TestSuite();
		for(TestCase testcase:botBotTestCases){
			suite.addTest(testcase);
		}
		
		Log.i("Testcases", botBotTestCases.toString());
		super.setTest(suite);
    }
	
	private TestSuite getDataDrivenTest(){
		TestSuite suite=new TestSuite();
		for (Class<?> cls : dataDrivenTests) {
			if (TestCase.class.isAssignableFrom(cls)) {
				try {
					suite.addTest((TestCase)cls.newInstance());
				} catch (InstantiationException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		}
		return suite;
	}
}
