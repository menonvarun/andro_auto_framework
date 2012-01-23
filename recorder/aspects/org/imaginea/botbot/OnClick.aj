package org.imaginea.botbot;

import org.aspectj.lang.Signature;
import android.util.Log;
import org.aspectj.lang.JoinPoint;

import android.content.DialogInterface;
import android.view.View;
import android.widget.*;

import org.imaginea.botbot.Recorder;

aspect OnClick {
	pointcut captureOnClick() : (execution(* onClick(View)));

	before(): captureOnClick()
	{

		Recorder.record("click", (View) thisJoinPoint.getArgs()[0]);

	}

	pointcut captureDialogOnClick() : (execution(* onClick(DialogInterface, int)));

	before(): captureDialogOnClick()
	{
		Recorder.record("clickbyid", thisJoinPoint.getArgs());
	}

}
