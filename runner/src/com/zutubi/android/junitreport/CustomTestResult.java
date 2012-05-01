package com.zutubi.android.junitreport;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.test.AssertionFailedError;

import junit.framework.Test;

public class CustomTestResult {
	private List<Test> testcases = new ArrayList<Test>();
	private HashMap<Test, Throwable> error= new HashMap<Test, Throwable>();
	private int errorCount=0;
	private int failureCount=0;
	
	public void add(Test test) {
		testcases.add(test);
	}
	
	public void addProblem(Test test,Throwable throwable) {
		error.put(test, throwable);
		if(AssertionFailedError.class.isAssignableFrom(throwable.getClass())){
			failureCount++;
		}else{
			errorCount++;
		}
	}
	
	public int getErrorCount(){
		return errorCount;
	}
	
	public int getFailureCount(){
		return failureCount;
	}
	
	public List<Test> getTestList(){
		return testcases;
	}
	
	public boolean notPassed(Test test){
		return error.containsKey(test);
	}
	
	public Throwable getFailure(Test test){
		return error.get(test);
	}
}
