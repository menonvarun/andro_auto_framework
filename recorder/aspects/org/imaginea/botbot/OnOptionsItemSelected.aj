
package org.imaginea.botbot; 
import android.view.MenuItem;
import android.util.Log;
import android.app.Activity;
import android.widget.*;

import org.imaginea.botbot.*;
 
aspect OnOptionsItemSelected
{
    pointcut captureOnOptionItemSelected() : (execution(* onOptionsItemSelected(MenuItem)));

    before(): captureOnOptionItemSelected()
	{
    	

		MenuItem item = (MenuItem) thisJoinPoint.getArgs()[0];
		Object target = thisJoinPoint.getTarget();

		Recorder.record("optionsmenu",target);
    }
}

