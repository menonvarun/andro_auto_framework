
package org.imaginea.botbot; 
import org.aspectj.lang.Signature;
import android.util.Log;
import org.aspectj.lang.JoinPoint;

import android.view.View;
import android.widget.*;
import org.imaginea.botbot.*;

aspect OnItemClick
{
    pointcut captureOnItemClick() : (execution(* onItemClick(AdapterView, View, int, long)));

	before(): captureOnItemClick()
	{
		

		AdapterView parent = (AdapterView) thisJoinPoint.getArgs()[0];
		View view = (View) thisJoinPoint.getArgs()[1];
		int rowNum = (Integer) thisJoinPoint.getArgs()[2];
		long rowID = (Long) thisJoinPoint.getArgs()[3];
		Object target = thisJoinPoint.getTarget();
		try{
		View tmpview=(View) parent.getItemAtPosition(rowNum);
		Recorder.record("click",tmpview);
		}catch(ClassCastException e){
			Object obj = parent.getItemAtPosition(rowNum);
			Recorder.record("click",obj,"");
		}

    }
}

