
package org.imaginea.botbot; 
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
	 
	aspect OnBackPressed
	{
	    pointcut captureOnBackPressed() : (execution(* onBackPressed()));
	
	    before(): captureOnBackPressed()
		{
			Recorder.record("clickback");

	    }
	    
	    pointcut captureOnKeyEvent() : (execution(* onKeyDown(int, KeyEvent)));
		
	    before(): captureOnKeyEvent()
		{	
	    	Integer keycode = (Integer) thisJoinPoint.getArgs()[0];
	    	KeyEvent event = (KeyEvent) thisJoinPoint.getArgs()[1];
	    	if (keycode == KeyEvent.KEYCODE_BACK || keycode == KeyEvent.KEYCODE_ESCAPE) 
	        {
	    		Recorder.record("clickback");
	        }

	    }
	}
	


