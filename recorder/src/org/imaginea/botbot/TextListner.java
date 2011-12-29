package org.imaginea.botbot;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.TextView;

public class TextListner implements TextWatcher{
	TextView view;
	private static final String LOG_TAG = "debugger";
	public TextListner(TextView tv)
	{
		view = tv;
	}
	@Override
	public void afterTextChanged(Editable arg0) {
		
	}

	@Override
	public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
			int arg3) {
		
	}

	@Override
	public void onTextChanged(CharSequence s, int start, int count, int after) {
		Recorder.record("entertext", view, s.toString());
		Log.d(LOG_TAG,"textentered: " + s.toString());
	}

}
