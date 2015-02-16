package com.bitekite;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

import com.bitekite.driver.Httppost_Links;
import com.bitekite.driver.SimpleJSONParser;
import com.bitekite.utils.Data;
import com.google.android.gms.maps.model.LatLng;

public class MyServiceCust extends Service {

	static Timer timer;
	static TimerTask timerTask;

	public MyServiceCust() {

		timer = new Timer();

		timerTask = new TimerTask() {

			@Override
			public void run() {

				ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
				nameValuePairs.add(new BasicNameValuePair("access_token", Data
						.getAccessToken(getApplicationContext())));
				nameValuePairs.add(new BasicNameValuePair("order_id", ""
						+ Data.getInfoOf(getApplicationContext(), "order_id")));

				Log.i("access_token",
						"=" + Data.getAccessToken(getApplicationContext()));
				Log.i("order_id",
						"="
								+ Data.getInfoOf(getApplicationContext(),
										"order_id"));

				String response = new SimpleJSONParser().getJSONFromUrlParams(
						Httppost_Links.Baselink + "get_driver_location",
						nameValuePairs);

				if (SimpleJSONParser.SERVER_TIMEOUT.equalsIgnoreCase(response)) {

				} else {
					Log.e("resp val", response);
					try {
						final JSONObject job = new JSONObject(response);
						if (response.contains("error")) {
							String error = job.getString("error");
							Log.i("Error ", "-----------" + error);
						} else {

							DriverLocationFinder.activity
									.runOnUiThread(new Runnable() {

										@Override
										public void run() {
											try {
												Data.driverLatitude = job
														.getDouble("current_latitude");
												Data.driverLongitude = job
														.getDouble("current_longitude");
												Data.saveInfoOf(
														getApplicationContext(),
														Data.customer_estimate_time,
														job.getString("estimated_time"));
												Log.e("" + Data.driverLatitude,
														""
																+ Data.driverLongitude
																+ ""
																+ Data.customer_estimate_time);
												DriverLocationFinder
														.addMarker(new LatLng(
																Data.driverLatitude,
																Data.driverLongitude));

											} catch (JSONException e) {
												// TODO Auto-generated catch
												// block
												e.printStackTrace();
											}

										}
									});
						}
					} catch (Exception e) {
						Log.v("Login Data Error", e.toString());
					}
				}

			}
		};

	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO: Return the communication channel to the service.
		throw new UnsupportedOperationException("Not yet implemented");
	}

	@Override
	public void onCreate() {
	}

	@Override
	public void onStart(Intent intent, int startId) {
		// For time consuming an long tasks you can launch a new thread here...

		try {
			timer.scheduleAtFixedRate(timerTask, 0, 20 * 1000);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {

		if (isMyServiceRunning()) {
			Log.e("service already running", "-===");
		} else {
			Log.e("service running not", "-===");
		}

		super.onStartCommand(intent, flags, startId);
		return Service.START_STICKY;
	}

	private boolean isMyServiceRunning() {
		ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
		for (RunningServiceInfo service : manager
				.getRunningServices(Integer.MAX_VALUE)) {
			if (MyServiceCust.class.getName().equals(
					service.service.getClassName())) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void onDestroy() {
		timer.cancel();
		timerTask.cancel();

	}
}
