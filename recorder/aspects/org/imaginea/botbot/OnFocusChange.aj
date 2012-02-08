import org.aspectj.lang.Signature;
import android.util.Log;
import org.aspectj.lang.JoinPoint;

import android.content.DialogInterface;
import android.view.View;
import android.widget.*;

import org.imaginea.botbot.Recorder;

aspect OnFocusChange {
	pointcut captureOnChangeFocus() : (execution(* onFocusChange(View,boolean)));

	String text = "";
	String tempText = "";

	before(): captureOnChangeFocus()
	{
		View view = (View) thisJoinPoint.getArgs()[0];
		if ((view.getClass().getName().contains("Text"))) {
			text = ((EditText) view).getText().toString();
		}
	}

	after(): captureOnChangeFocus()
	{
		View view = (View) thisJoinPoint.getArgs()[0];
		if ((view.getClass().getName().contains("Text"))) {
			tempText = ((EditText) view).getText().toString();
			if (!text.contentEquals(tempText)) {
				Recorder.record("entertext", view, tempText);
				Log.i("debugger",
						"command received enter text and the text is: "
								+ tempText);
			}
		}
	}

}
