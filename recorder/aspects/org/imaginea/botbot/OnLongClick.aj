
package org.imaginea.botbot; 
import android.view.View;
import android.util.Log;
import android.widget.*;

import org.imaginea.botbot.*;
aspect OnLongClick
{
    pointcut captureOnLongClick() : (execution(* onLongClick(View)));

	// AspectJ compiler does not like a tab for indenting this line?!?!?!
	// (And presumably many others)
    before(): captureOnLongClick()
	{
		View view = (View) thisJoinPoint.getArgs()[0];
		

		Object target = thisJoinPoint.getTarget();

		Recorder.record("longclick",target);
    }
}

