package org.selenium.androframework.common;

import com.jayway.android.robotium.solo.Solo;

import junit.framework.Assert;
import android.test.ActivityInstrumentationTestCase2;

public class RobotiumBaseClass extends ActivityInstrumentationTestCase2 {
	public static final String APP_TEST_PACKAGE = "#APP_TEST_PACKAGE#";
	public static final String APP_MAIN_ACTIVITY_CLASS = "#APP_MAIN_ACTIVITY_CLASS#";
	protected Solo solo;
	
	public RobotiumBaseClass(String pkg, Class activityClass) {
		super(APP_TEST_PACKAGE, getActivityClass());
	}

	private static Class getActivityClass() {
		try {
			return Class.forName(APP_TEST_PACKAGE + "."
					+ APP_MAIN_ACTIVITY_CLASS);
		} catch (ClassNotFoundException e) {
			Assert.fail("Class not found : ");
			return null;
		}
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		solo = new Solo(getInstrumentation(), getActivity());
	}

	@Override
	protected void tearDown() throws Exception {
		solo.finishOpenedActivities();
	}

}
