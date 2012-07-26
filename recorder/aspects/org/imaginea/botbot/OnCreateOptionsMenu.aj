
package org.imaginea.botbot;
import android.view.Menu;
 
aspect OnCreateOptionsMenu
{
    pointcut captureOnCreateOptionsMenu() : (execution(* onCreateOptionsMenu(Menu)));

    before(): captureOnCreateOptionsMenu()
 	{   
		Menu menu = (Menu) thisJoinPoint.getArgs()[0];
		Recorder.record("clickmenu",menu);
		
    }
}
