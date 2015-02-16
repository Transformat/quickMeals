package com.bitekite.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.bitekite.Log;

/**
 * Public class with static variables to check internet connection
 * 
 */

public class InternetCheck {

	private static InternetCheck instance = new InternetCheck();
	static Context context;
	ConnectivityManager connectivityManager;
	NetworkInfo wifiInfo, mobileInfo;
	boolean connected = false;

	public static InternetCheck getInstance(Context ctx) {
		context = ctx;
		return instance;
	}

	/**
	 * Method to check if internet is connected
	 * 
	 * @param con
	 *            (activity context)
	 * @return
	 */
	public boolean isOnline(Context con) {
		try {
			connectivityManager = (ConnectivityManager) con
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo networkInfo = connectivityManager
					.getActiveNetworkInfo();
			connected = networkInfo != null && networkInfo.isAvailable()
					&& networkInfo.isConnected();
			return connected;

		} catch (Exception e) {
			System.out
					.println("CheckConnectivity Exception: " + e.getMessage());
			Log.v("connectivity", e.toString());
		}
		return connected;
	}
}
