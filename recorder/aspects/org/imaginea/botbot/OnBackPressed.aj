
package org.imaginea.botbot; 
import android.view.View;
import android.util.Log;
import android.widget.ListView;

import org.imaginea.botbot.*;
	 
	aspect OnBackPressed
	{
	    pointcut captureOnBackPressed() : (execution(* onBackPressed()));
	
	    before(): captureOnBackPressed()
		{
			Recorder.record("clickback");

	    }
	}
	


