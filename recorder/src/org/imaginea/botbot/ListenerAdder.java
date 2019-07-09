package org.imaginea.botbot;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

import org.imaginea.botbot.OnClickListenerTest;
import org.imaginea.botbot.webview.WebViewListnerAdder;

import android.content.Context;
import android.content.res.AssetManager;
import android.text.InputType;
import android.util.Log;
import android.view.*;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

public class ListenerAdder {
	static Set<View> processedView = new HashSet<View>();
	private static Context context;
	public void processView(View view) {
		if (!processedView.contains(view)) {
			if(context==null){
				context=view.getContext();
				ServerProperties.setResources(context);
			}
			addListeners(view);
		}
		if (view instanceof ViewGroup) {
			int noOfChilds = ((ViewGroup) view).getChildCount();
			for (int i = 0; i < noOfChilds; i++) {
				processView(((ViewGroup) view).getChildAt(i));
			}
		}

	}
	
	//Returns whether Click Listner is added or not
	public boolean containOnClikListener(View v) {
		Object temp = null;
		Class klass = v.getClass();
		//Checking for View Class
		while (!klass.equals(View.class)) {
			klass = klass.getSuperclass();
		}		
		try {
			//Checking for mOnClickListner higher Android version do not have this field, handling NoSuchField
			Field f = klass.getDeclaredField("mOnClickListener");
			f.setAccessible(true);
			temp = f.get(v);			
		} catch (NoSuchFieldException e) {
			try {
				//Checking for method hasOnClickListners if present
				Method methodHasOnClickListner=klass.getMethod("hasOnClickListeners");
				return (Boolean)methodHasOnClickListner.invoke(v, new Object[] {});
				} catch (Exception exception) {
					System.out.println("Exception in "+exception);
				}
		}
		catch (IllegalAccessException e) {
			Log.e("Illegal Access", "Found Excpetion: " + e);
		}		
		return temp != null;
	}

	public OnItemSelectedListener containItemSelectedListener(View v) {
		Object temp = null;
		Class klass = v.getClass();

		while (!klass.equals(AdapterView.class)) {
			klass = klass.getSuperclass();
		}

		try {
			Field f = klass.getDeclaredField("mOnItemSelectedListener");
			f.setAccessible(true);
			temp = f.get(v);
		} catch (Exception e) {
			return null;
		}
		return (OnItemSelectedListener) temp;
	}
	
	public OnItemClickListener containOnItemClickListener(View v) {
		Object temp = null;
		Class klass = v.getClass();

		while (!klass.equals(AdapterView.class)) {
			klass = klass.getSuperclass();
		}

		try {
			Field f = klass.getDeclaredField("mOnItemClickListener");
			f.setAccessible(true);
			temp = f.get(v);
		} catch (Exception e) {
			return null;
		}
		return (OnItemClickListener) temp;
	}

	public void addListeners(View view) {
		boolean containsClick = containOnClikListener(view);

		if(view instanceof WebView){
			WebView wview=(WebView)view;
			WebViewListnerAdder wListner=new WebViewListnerAdder(wview);
			wListner.addListner();
			processedView.add(view);
			return;
		}
		
		if (containsClick || (view instanceof ImageView)) {
			processedView.add(view);
			return;
		} else {
			if (view instanceof Spinner) {
				int dPos = ((Spinner) view).getSelectedItemPosition();
				OnItemSelectedListener tmp = containItemSelectedListener(view);

				((Spinner) view)
						.setOnItemSelectedListener(new OnItemSelectedListenerTest(
								tmp, dPos));
				processedView.add(view);
				return;
			} else if (AdapterView.class.isAssignableFrom(view.getClass())) {
				/*OnItemClickListener tmp = containOnItemClickListener(view);
				((AdapterView) view)
						.setOnItemClickListener(new OnItemClickListenerTest(tmp));*/
				processedView.add(view);
				return;
			}
			System.out.println("Adding onClickListener for :"+ view.getClass().getName());
			view.setOnClickListener(new OnClickListenerTest());
			if (view instanceof EditText) {
				//((EditText) view).setInputType(InputType.TYPE_NULL);
				((TextView) view).addTextChangedListener(new TextListner(
						(TextView) view));
				processedView.add(view);
			}
		}
	}
	
}
