package org.imaginea.botbot;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;

public class RecordText implements TextWatcher{
	View v;
	String text="";
	public RecordText(View v){
		this.v =v;
	}
	@Override
	public void afterTextChanged(Editable arg0) {
		Log.i("debugger", "Adding text: " + text);
		Recorder.record("entertext", v, text);
	}

	@Override
	public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
			int arg3) {
		
	}

	@Override
	public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
		
		text=arg0.toString();
		
	}

}
