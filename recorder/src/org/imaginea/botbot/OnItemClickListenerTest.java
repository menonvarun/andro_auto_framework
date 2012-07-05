package org.imaginea.botbot;

import java.util.ArrayList;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;

public class OnItemClickListenerTest implements AdapterView.OnItemClickListener {

	ArrayList<AdapterView.OnItemClickListener> listener = new ArrayList<AdapterView.OnItemClickListener>();
	
	public OnItemClickListenerTest(OnItemClickListener l) {
		if(l!=null){
			listener.add(l);
		}
	}
	
	@Override
	public void onItemClick(AdapterView<?> parent, View v, int aPosition,
			long arg3) {
		for(OnItemClickListener l : listener){
			l.onItemClick(parent, v, aPosition, arg3);
		}
		
	}

}
