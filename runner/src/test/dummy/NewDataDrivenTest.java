package test.dummy;
import java.util.HashMap;
import java.util.Iterator;

import org.selenium.androframework.common.DataDrivenTestCase;

import junit.framework.Assert;
import junit.framework.Test;
import junit.framework.TestSuite;


import android.widget.EditText;

public class NewDataDrivenTest extends DataDrivenTestCase{
	String medicine="";
	String submedicine="";
	boolean expected;
	public NewDataDrivenTest(){
		super();
	}
	public NewDataDrivenTest(String medicine,String submedicine){
		super();
		this.medicine=medicine;
		this.submedicine=submedicine;
		this.setName("medicineTest");
		this.setCustomTestName(medicine);
	}
	
	public NewDataDrivenTest(String testName,boolean expected){
		super();
		this.setName("dummyTest");
		this.setCustomTestName(testName);
		this.expected=expected;
	}
	
	public static Test suite(){
		TestSuite suite = new TestSuite();
		HashMap<String, Boolean> hm = new HashMap<String, Boolean>();
		hm.put("Expected true", true);
		hm.put("Expected false", false);
		Iterator<String> it=hm.keySet().iterator();
		while(it.hasNext()) {
				String testName = it.next();
				boolean expected=hm.get(testName);
				suite.addTest(new NewDataDrivenTest(testName,expected));
			}
		return suite;
		
	}
	
	public void dummyTest(){
		Assert.assertTrue(expected);
	}

}