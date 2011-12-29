
package org.imaginea.botbot;  
import android.view.View;
import android.widget.*;
import android.gesture.*;

 
aspect OnGesturePerformed
{
/*
    pointcut captureOnGesturePerformed() : (execution(* onGesturePerformed(GestureOverlayView, Gesture)));

    before(): captureOnGesturePerformed()
	{
		if (Recorder.isPlayingBack()) return;

		GestureOverlayView view = (GestureOverlayView) thisJoinPoint.getArgs()[0];
		Gesture gesture = (Gesture) thisJoinPoint.getArgs()[1];
		Object target = thisJoinPoint.getTarget();

		WidgetReference wref = WidgetReference.create(view);
		wref.operation = Operation.Gesture;

		Recorder.addGestureEvent(wref, gesture);

        Log.log("OnGesturePerformed: " + wref);
    }
*/
}

