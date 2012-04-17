package test.dummy;
import java.util.HashMap;
import java.util.Iterator;

import org.selenium.androframework.common.DataDrivenTestCase;
import org.selenium.androframework.common.RobotiumBaseClass;

import junit.framework.Assert;
import junit.framework.Test;
import junit.framework.TestSuite;


import android.widget.EditText;

public class NormalTest extends RobotiumBaseClass{
	String medicine="";
	String submedicine="";
	
	public NormalTest(){
		super();
	}

	public void testTrue(){
		
		Assert.assertTrue("Verifying true failed",true);
	}
	
	public void testFailure(){
		
		Assert.assertTrue("Verifying true failed",false);
	}

}