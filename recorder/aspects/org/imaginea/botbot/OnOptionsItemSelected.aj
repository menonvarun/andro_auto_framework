
package org.imaginea.botbot; 
import android.view.MenuItem;
 
aspect OnOptionsItemSelected
{
    pointcut captureOnOptionItemSelected() : (execution(* onOptionsItemSelected(MenuItem)));

    before(): captureOnOptionItemSelected()
	{
    	MenuItem item = (MenuItem) thisJoinPoint.getArgs()[0];
    	Recorder.record("optionsmenu",item);
    }
}

