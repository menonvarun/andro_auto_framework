package org.imaginea.botbot.webview;

import android.app.Activity;

/**
 * Used to execute commands on the UI thread.
 * 
 */

public class UIThreadRunnerUtil {
	
	// Activity
	private Activity activity;

	// Event Listener
	private UiRunnableListener listener;

	// UI runnable
	private Runnable uiRunnable;

	/**
	 * Class initialization
	 * 
	 * @param activity
	 *            Activity
	 * @param listener
	 *            Event listener
	 */
	public UIThreadRunnerUtil(Activity activity, UiRunnableListener listener) {
		this.activity = activity;
		this.listener = listener;

		uiRunnable = new Runnable() {
			public void run() {
				// Execute custom code
				if (UIThreadRunnerUtil.this.listener != null)
					UIThreadRunnerUtil.this.listener.onRunOnUIThread();

				synchronized (this) {
					this.notify();
				}
			}
		};
	}

	/**
	 * Start runnable on UI thread and wait until finished
	 */
	public void startOnUiAndWait() {
		synchronized (uiRunnable) {
			// Execute code on UI thread
			activity.runOnUiThread(uiRunnable);

			// Wait until runnable finished
			try {
				uiRunnable.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
