
package org.imaginea.botbot;

import java.util.*;
import android.util.Log;
 
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.view.*;
import android.widget.*;
import android.gesture.*;

import org.imaginea.botbot.*;
 
aspect OnCreate
{
	
	static String firstActivity;
	static int count=0;
    pointcut captureOnCreate() : (execution(* onCreate(Bundle)));
    
    before(): captureOnCreate()
   	{
   		Object target = thisJoinPoint.getTarget();
   		count++;
   		if (firstActivity == null) firstActivity = target.getClass().getName();
   	}
    
	after(): captureOnCreate()
	{
		Object target = thisJoinPoint.getTarget();

		if (!(target instanceof Activity))
			return;
		if (firstActivity == null)
			return;
		if (firstActivity.contentEquals(target.getClass().getName()) && count==1) {
			Activity a = (Activity) target;

			ListenerAdder la = new ListenerAdder();
			la.processView(a.getWindow().getDecorView().getRootView());
			firstActivity = null;
		}
		count--;
	}
    
    pointcut captureCreate() : (call(* create()));

    after() returning (AlertDialog d): captureCreate()
	{
	}    
    
}

