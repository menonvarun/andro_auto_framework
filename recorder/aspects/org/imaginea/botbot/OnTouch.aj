
package org.imaginea.botbot; 
import android.view.MotionEvent;
import android.view.View;

aspect OnTouch
{
    pointcut captureOnTouch() : (execution(* onTouch(View, MotionEvent)));

	// AspectJ compiler does not like a tab for indenting this line?!?!?!
	// (And presumably many others)
    before(): captureOnTouch()
	{
		View view = (View) thisJoinPoint.getArgs()[0];
		

		Recorder.record("touch",view);
    }
}

