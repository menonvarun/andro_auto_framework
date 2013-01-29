package org.imaginea.botbot.webview;

/**
 * Events for blocking runnable executing on UI thread
 * 
 * @author
 * 
 */
public interface UiRunnableListener {

	/**
	 * Code to execute on UI thread
	 */
	public void onRunOnUIThread();

}
