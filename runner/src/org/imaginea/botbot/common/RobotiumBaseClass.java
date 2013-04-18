package org.imaginea.botbot.common;

import java.io.IOException;
import java.io.InputStream;

import org.imaginea.botbot.webview.WebViewUtil;

import com.jayway.android.robotium.solo.Solo;

import junit.framework.Assert;
import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.test.ActivityInstrumentationTestCase2;

public class RobotiumBaseClass extends ActivityInstrumentationTestCase2 {
	public static final String APP_TEST_PACKAGE = "#APP_TEST_PACKAGE#";
	public static final String APP_MAIN_ACTIVITY_CLASS = "#APP_MAIN_ACTIVITY_CLASS#";
	protected AssetManager assetManager;
	protected Solo solo;
	protected Prefrences prefrences=new Prefrences();
	
	public RobotiumBaseClass() {
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
	
	protected InputStream getFile(String filepath){
		try{
			return assetManager.open(filepath);
			
		}catch (IOException e) {
			Assert.fail("Unable to open file: "+filepath);
			return null;
		}
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		solo = new Solo(getInstrumentation(), getActivity());
		Context context=getInstrumentation().getContext();
		Resources resources =context.getResources();
		assetManager = resources.getAssets();
		prefrences.setAssetManager(assetManager);
		prefrences.setExecutionContext(solo);
		WebViewUtil.setAssetManager(assetManager);
		solo.waitForActivity(solo.getCurrentActivity().getClass().toString());
	}

	@Override
	protected void tearDown() throws Exception {
		solo.finishOpenedActivities();
	}

}
