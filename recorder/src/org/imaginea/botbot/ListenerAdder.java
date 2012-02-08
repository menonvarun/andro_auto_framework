package org.imaginea.botbot;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

import org.imaginea.botbot.OnClickListenerTest;

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
			Field f = klass.getDeclaredField("mOnClickListener");
			f.setAccessible(true);
			temp = f.get(v);
			Log.i("debugger", "Found temp: " + temp.toString());
		} catch (Exception e) {
		}
		return temp != null;
	}

	public OnItemSelectedListener containItemSelectedListener(View v) {
		Object temp = null;
		Class klass = v.getClass();

		while (!klass.equals(View.class)) {
			klass = klass.getSuperclass();
		}

		try {
			Field f = klass.getDeclaredField("mOnItemSelectedListener");
			f.setAccessible(true);
			temp = f.get(v);
			Log.i("debugger", "Found temp: " + temp.toString());
		} catch (Exception e) {
		}
		return (OnItemSelectedListener) temp;
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
			}
		}
	}

}
