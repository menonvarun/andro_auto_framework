package org.imaginea.botbot;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

import org.imaginea.botbot.OnClickListenerTest;
import org.wordpress.android.EditContent;
import org.wordpress.android.EditPost;
import org.wordpress.android.WordPress;

import android.content.Intent;
import android.util.Log;
import android.view.*;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

public class ListenerAdder {
	static Set<View> processedView = new HashSet<View>();

	public void processView(View view) {

		if (!processedView.contains(view)) {
			addListeners(view);
		}
		if (view instanceof ViewGroup) {
			int noOfChilds = ((ViewGroup) view).getChildCount();
			for (int i = 0; i < noOfChilds; i++) {
				processView(((ViewGroup) view).getChildAt(i));
			}
		}

	}

	public boolean containOnClikListener(View v) {
		Object temp = null;
		Class klass = v.getClass();

		while (!klass.equals(View.class)) {
			klass = klass.getSuperclass();
		}

		try {
			// Obviously this is not the best way to test if a listener
			// exists. But it's all we've got ...
			Field f = klass.getDeclaredField("mOnClickListener");
			f.setAccessible(true);
			temp = f.get(v);
			Log.i("debugger", "Found temp: " + temp.toString());
		} catch (Exception e) {
			// Log.i("debugger", "Adding listener error. " + e);
		}
		return temp != null;
		/*
		 * Class c = view.getClass(); Method[] methods = c.getDeclaredMethods();
		 * for(Method m : methods){ if(m.getName().contentEquals("onClick")){
		 * return true; } } return false;
		 */
	}

	public OnItemSelectedListener containItemSelectedListener(View v) {
		Object temp = null;
		Class klass = v.getClass();

		while (!klass.equals(View.class)) {
			klass = klass.getSuperclass();
		}

		try {
			// Obviously this is not the best way to test if a listener
			// exists. But it's all we've got ...
			Field f = klass.getDeclaredField("mOnItemSelectedListener");
			f.setAccessible(true);
			temp = f.get(v);
			Log.i("debugger", "Found temp: " + temp.toString());
		} catch (Exception e) {
			// Log.i("debugger", "Adding listener error. " + e);
		}
		return (OnItemSelectedListener) temp;
		/*
		 * Class c = view.getClass(); Method[] methods = c.getDeclaredMethods();
		 * for(Method m : methods){ if(m.getName().contentEquals("onClick")){
		 * return true; } } return false;
		 */
	}

	public void addListeners(View view) {
		boolean containsClick = containOnClikListener(view);
		// Log.i("debugger", "Found View: " + view);
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
			} else if (view instanceof AdapterView) {
				((AdapterView) view)
						.setOnItemClickListener(new OnItemClickListenerTest());
				processedView.add(view);
				return;
			}
			view.setOnClickListener(new OnClickListenerTest());
			if (view instanceof EditText) {
				((TextView) view).addTextChangedListener(new TextListner(
						(TextView) view));
				processedView.add(view);
				/*
				 * if (view.getOnFocusChangeListener() == null) { ((EditText)
				 * view) .setOnFocusChangeListener(new
				 * View.OnFocusChangeListener() {
				 * 
				 * @Override public void onFocusChange(View view, boolean
				 * hasFocus) { } });
				 */
			}
		}
	}

}
