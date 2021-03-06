package com.bitekite;

/**
 * Project Name :- You Are a CEO Purpose of file :- Custom log class overrides
 * Android Log Developed by Clicklabs Pvt. Ltd. Developer:- Gurmail Singh Kang.
 * Link:- http://www.click-labs.com
 */
public class Log {

	private static final boolean print = true; // true for printing and false
												// for not

	public Log() {
	}

	public static void i(String TAG, String message) {
		if (print) {
			android.util.Log.i(TAG, message);
		}
	}

	public static void d(String TAG, String message) {
		if (print) {
			android.util.Log.d(TAG, message);
		}
	}

	public static void e(String TAG, String message) {
		if (print) {
			android.util.Log.e(TAG, message);
		}
	}

	public static void v(String TAG, String message) {
		if (print) {
			android.util.Log.v(TAG, message);
		}
	}

	public static void w(String TAG, String message) {
		if (print) {
			android.util.Log.w(TAG, message);
		}
	}

	public static void e(String TAG, String message, Exception e) {
		if (print) {
			android.util.Log.e(TAG, message, e);
		}
	}

	public static void d(String TAG, String message, Exception e) {
		if (print) {
			android.util.Log.d(TAG, message, e);
		}
	}

	public static void i(String TAG, String message, Exception e) {
		if (print) {
			android.util.Log.i(TAG, message, e);
		}
	}

	public static void v(String TAG, String message, Exception e) {
		if (print) {
			android.util.Log.v(TAG, message, e);
		}
	}
}
