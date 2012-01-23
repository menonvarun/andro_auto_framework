
package org.imaginea.botbot;
 
import org.aspectj.lang.Signature;
import org.aspectj.lang.JoinPoint;

import android.text.format.DateFormat;
import android.view.View;
import android.widget.DatePicker;

import org.imaginea.botbot.*; 
aspect OnDateChanged
{
    pointcut captureOnDateChanged() : (execution(* onDateChanged(DatePicker, int, int, int)));

    before(): captureOnDateChanged()
	{
		DatePicker view = (DatePicker) thisJoinPoint.getArgs()[0];
		String year = ((Integer) thisJoinPoint.getArgs()[1]).toString();	
		String monthOfYear = ((Integer) thisJoinPoint.getArgs()[2]).toString();	
		String dayOfMonth = ((Integer) thisJoinPoint.getArgs()[3]).toString();	

		Recorder.record("EnterText", view, year + " " + monthOfYear + " " + dayOfMonth);
		
    }
}