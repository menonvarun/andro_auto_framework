package org.imaginea.botbot;

import java.util.ArrayList;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;

public class OnItemSelectedListenerTest implements
		AdapterView.OnItemSelectedListener {
	int lastposition = 0;
	ArrayList<AdapterView.OnItemSelectedListener> listener = new ArrayList<AdapterView.OnItemSelectedListener>();
	public OnItemSelectedListenerTest(OnItemSelectedListener l,int dPos){
		if(l!=null){
			listener.add(l);
		}
		lastposition=dPos;
	}
	@Override
	public void onItemSelected(AdapterView<?> parent, View v, int aPosition,
			long arg3) {
		for(OnItemSelectedListener l : listener){
			l.onItemSelected(parent, v, aPosition, arg3);
		}
		if (lastposition != aPosition) {
			AdapterView parent1 = (AdapterView) parent;
			View view = (View) v;
			int rowNum = (Integer) aPosition;
			long rowID = (Long) arg3;
			Recorder.record("clickspinner", parent1, view, rowNum, rowID);
			lastposition=aPosition;
		}

	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub

	}

}
