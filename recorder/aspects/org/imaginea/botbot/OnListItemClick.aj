package org.imaginea.botbot;//package com.gorillalogic.fonemonkey.aspects;
//
//import android.app.Activity;
//import android.view.MenuItem;
//import android.view.View;
//import android.widget.AdapterView;
//import android.widget.ListView;
//
//import com.gorillalogic.fonemonkey.Log;
//import com.gorillalogic.fonemonkey.Operation;
//import com.gorillalogic.fonemonkey.Recorder;
//import com.gorillalogic.fonemonkey.WidgetReference;
//	 
//	aspect OnListItemClick
//	{
//	    pointcut captureListItemClick() : (execution(* onListItemClick(ListView, View, int, long)));
//	
//	    before(): captureListItemClick()
//		{
//			if (Recorder.isPlayingBack()) return;
//
//			ListView parent = (ListView) thisJoinPoint.getArgs()[0];
//			View view = (View) thisJoinPoint.getArgs()[1];
//			int rowNum = (Integer) thisJoinPoint.getArgs()[2];
//			long rowID = (Long) thisJoinPoint.getArgs()[3];
//			Object target = thisJoinPoint.getTarget();
//
//			Object obj = parent.getItemAtPosition(rowNum);
//
//			if (!(obj instanceof String)) obj = null;
//
//			WidgetReference wref = WidgetReference.create(view, parent, rowNum, (String) obj, rowID);
//			wref.operation = Operation.Click;
//
//			Recorder.addEvent(wref);
//
//	        Log.log("OnListItemClick: " + wref);
//	    }
//	}
//	


