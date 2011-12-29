
package org.imaginea.botbot;
 
import android.view.View;
import android.util.Log;
import android.widget.*;

import org.imaginea.botbot.*; 
aspect OnItemLongClick
{
    /*pointcut captureOnItemLongClick() : (execution(* onItemLongClick(AdapterView, View, int, long)));

	before(): captureOnItemLongClick()
	{
		if (Recorder.isPlayingBack()) return;

		AdapterView parent = (AdapterView) thisJoinPoint.getArgs()[0];
		View view = (View) thisJoinPoint.getArgs()[1];
		int rowNum = (Integer) thisJoinPoint.getArgs()[2];
		long rowID = (Long) thisJoinPoint.getArgs()[3];
		Object target = thisJoinPoint.getTarget();

		WidgetReference wref = WidgetReference.create(view, parent, rowNum, null, rowID);
		wref.operation = Operation.LongClick;

		Recorder.addEvent(wref);

        Log.log("OnItemLongClick: " + wref);
    }*/
}

