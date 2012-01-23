
package org.imaginea.botbot;

import android.app.Activity;
import android.view.Menu;

import org.imaginea.botbot.*;
 
aspect OnCreateOptionsMenu
{
    pointcut captureOnCreateOptionsMenu() : (execution(* onCreateOptionsMenu(Menu)));

    before(): captureOnCreateOptionsMenu()
 	{   
		Menu menu = (Menu) thisJoinPoint.getArgs()[0];
		Recorder.record("clickmenuitem",menu);
		
    }
}
