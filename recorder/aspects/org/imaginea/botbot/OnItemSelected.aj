
package org.imaginea.botbot;
 
import java.util.ArrayList;
import android.util.Log;

import org.aspectj.lang.Signature;
import org.aspectj.lang.JoinPoint;

import android.view.View;
import android.widget.*;

import org.imaginea.botbot.*; 
aspect OnItemSelected
{
   /*pointcut captureOnItemSelected() : (execution(* onItemSelected(AdapterView, View, int, long)));

	before(): captureOnItemSelected()
	{
		
		if (AutomationManager.record(Operation.Select.toString(), thisJoinPoint.getArgs())) {	
			return;
		}
		
		AdapterView parent = (AdapterView) thisJoinPoint.getArgs()[0];
		View view = (View) thisJoinPoint.getArgs()[1];
		int rowNum = (Integer) thisJoinPoint.getArgs()[2];
		long rowID = (Long) thisJoinPoint.getArgs()[3];

		
		if (Recorder.isPlayingBack()) return;


		Object target = thisJoinPoint.getTarget();

		Object obj = parent.getItemAtPosition(rowNum);

		if (!(obj instanceof String)) obj = null;


		Recorder.addEvent(wref);
		
		AdapterView parent = (AdapterView) thisJoinPoint.getArgs()[0];
		View view = (View) thisJoinPoint.getArgs()[1];
		int rowNum = (Integer) thisJoinPoint.getArgs()[2];
		long rowID = (Long) thisJoinPoint.getArgs()[3];
		Object target = thisJoinPoint.getTarget();

		Recorder.record("click",parent,view,rownum,rowid,target);

        Log.log("OnItemSelected: ");
    }*/
}

