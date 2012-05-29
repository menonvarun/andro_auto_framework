package com.zutubi.android.junitreport;

import junit.framework.TestCase;

public class TestKeeper {
	private String testname;
	private long startTime;
	private long endTime;
	private String status="PASS";
	private Throwable error;
	private TestCase test;
	private boolean isFailed=false;
	private String tag;
	
	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public void setFailed(boolean isFailed) {
		this.isFailed = isFailed;
	}

	public TestCase getTest() {
		return test;
	}

	public void setTest(TestCase test) {
		this.test = test;
	}

	public String getTestname() {
		return testname;
	}

	public void setTestname(String testname) {
		this.testname = testname;
	}

	public long getStartTime() {
		return startTime;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	public long getEndTime() {
		return endTime;
	}

	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Throwable getError() {
		return error;
	}

	public void setError(Throwable error) {
		isFailed=true;
		this.error = error;
	}
	
	public boolean isFailed(){
		return isFailed;
	}
	
	
}
