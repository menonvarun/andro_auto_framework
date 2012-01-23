package org.imaginea.botbot;

import java.util.HashMap;

public class ViewClasses {
	private HashMap<String, String> supportedViews = new HashMap<String, String>();

	public ViewClasses() {
		supportedViews.put("Button", "android.widget.Button");
		supportedViews.put("CheckBox", "android.widget.CheckBox");
		supportedViews.put("CheckedTextView", "android.widget.CheckedTextView");
		supportedViews.put("EditText", "android.widget.EditText");
		supportedViews.put("RadioButton", "android.widget.RadioButton");
		supportedViews.put("TextView", "android.widget.TextView");
		supportedViews.put("ToggleButton", "android.widget.ToggleButton");
		supportedViews.put("WebView", "android.webkit.WebView");
		supportedViews.put("Spinner", "android.widget.Spinner");
	}

	public boolean isSupportedClass(String className) {
		for (String supportedTypes : supportedViews.keySet()) {
			if (className.contains(supportedTypes)) {
				return true;
			}
		}
		return false;
	}
	public String getFullClassName(String className) {
		for (String supportedTypes : supportedViews.keySet()) {
			if (className.contains(supportedTypes)) {
				return supportedViews.get(supportedTypes);
			}
		}
		return "";
	}
}
