package com.bitekite;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONArray;
import org.json.JSONObject;

import com.bitekite.driver.Httppost_Links;
import com.bitekite.utils.Data;
import com.bitekite.utils.DialogPopup;
import com.bitekite.utils.InternetCheck;
import com.bitekite.utils.LocationFetcherForeground;
import com.flurry.android.FlurryAgent;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;

//import android.widget.Toast;

public class MyService extends Service {

	static Timer timer;
	static TimerTask timerTask;
	static int count = 0;
	LocationFetcherForeground locationFetcherForeground;
	Handler mHandler;
	ArrayList<Address> retList;

	public MyService() {

		timer = new Timer();
		timerTask = new TimerTask() {

			@Override
			public void run() {
				Log.e("service running", "instance" + (count++));
				Data.activity.runOnUiThread(new Runnable() {
					@Override
					public void run() {
						checkinfo();
					}
				});
			}

		};
		count = 0;
	}

	@Override
	public IBinder onBind(Intent intent) {
		throw new UnsupportedOperationException("Not yet implemented");
	}

	@Override
	public void onCreate() {
	}

	@Override
	public void onStart(Intent intent, int startId) {
		try {
			timer.scheduleAtFixedRate(timerTask, 1000, 60 * 1000);
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
			if (MyService.class.getName()
					.equals(service.service.getClassName())) {
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

	void stateChange() {

		Log.e("service on stateChange", "on");
		RequestParams params = new RequestParams();

		params.put("access_token", Data.getAccessToken(MyService.this));
		params.put("order_id","" + Data.getInfoOf(getApplicationContext(), "order_id"));
		params.put("latitude", "" + Data.currentLatitude);
		params.put("longitude", "" + Data.currentLongitude);
        params.put("utc_date", "" + Data.startTime);

		Log.d("access_token", Data.getAccessToken(MyService.this));
		Log.d("order_id","order id = "+ Data.getInfoOf(getApplicationContext(),"order_id"));
		Log.d("latitude", "" + Data.currentLatitude);
		Log.d("longitude", "" + Data.currentLongitude);
        Log.d("utc_date", "" + Data.startTime);

		AsyncHttpClient client = new AsyncHttpClient();

		client.addHeader("Content-type", "application/json");
		client.addHeader("Accept", "application/json");
		client.setTimeout(100000);
		client.post(Httppost_Links.Baselink + "get_order_state", params,
				new AsyncHttpResponseHandler() {

					@Override
					public void onSuccess(String response) {

						JSONObject res;
						try {

							res = new JSONObject(response);
							Log.e("response", "response" + response);
							Log.d("service response", "service response" + res);
							Log.d("message","message" + res.getString("message"));
							Log.d("order_status","order_status"	+ res.getString("order_status"));
							Log.d("open_status","open_status"+ res.getString("open_status"));

							String message = res.getString("message");

							Data.ORDERSTATUS = res.getString("order_status");
							Data.OPENSTATUS = res.getString("open_status");
							Data.OUTOFZONE = res.getString("zone_status");
							String zoneMessage = res.getString("zone_message");
							Data.saveInfoOf(getApplicationContext(),
									Data.customer_estimate_time,
									res.getString("estimated_time"));

							Log.d("estimated_time",
									"estimated_time********* === "
											+ res.getString("estimated_time"));
							
							if (Data.ORDERSTATUS.equals("1")) {

								GCMIntentService.flag = "41";
								Data.saveInfoOf(getApplicationContext(), "GCM", "41");
								ListofMeals.pushUpdater.pushUpdater();
							} else if (Data.ORDERSTATUS.equals("2")) {
								Data.saveInfoOf(getApplicationContext(),
										"Rating_orderId",
										res.getString("order_id"));
								Data.saveInfoOf(getApplicationContext(),
										"Rating_dateTime",
										res.getString("time"));
								Data.saveInfoOf(getApplicationContext(),
										"Rating_price", res.getString("price"));
								Data.saveInfoOf(getApplicationContext(),
										"Rating_meals", res.getString("meals"));
								Data.saveInfoOf(getApplicationContext(), "GCM", "40");
								GCMIntentService.flag = "40";
								ListofMeals.pushUpdater.pushUpdater();
							} else {
								if (Data.getInfoOf(getApplicationContext(),
										Data.OrderPlacedFlag).equals("1")) {
									Log.d("banner you are out of zone","banner you are out of zone");
								} else {

									if (Data.OPENSTATUS.equals("1")) {
										if (Data.OUTOFZONE.equals("0")) {
											Log.d("banner you are out of zone","banner you are out of zone");
											ListofMeals.relativeLayoutOrderPlaced
													.setVisibility(View.VISIBLE);

											ListofMeals.textViewOrderPlaced
													.setText(zoneMessage);
											ListofMeals.textViewOrderPlaced
													.setTextColor(Color.WHITE);
											ListofMeals.relativeLayoutOrderPlaced
													.setBackgroundResource(R.color.zone_color);
										} else {
											Log.d("banner you are in  zone","banner you are in zone");
											ListofMeals.relativeLayoutOrderPlaced
													.setVisibility(View.GONE);
										}
									} else {
										Log.d("banner we are closed","banner we are closed");
										ListofMeals.relativeLayoutOrderPlaced
												.setVisibility(View.VISIBLE);
										ListofMeals.textViewOrderPlaced
												.setText(message);
										ListofMeals.textViewOrderPlaced
												.setTextColor(Color.WHITE);
										ListofMeals.relativeLayoutOrderPlaced
												.setBackgroundResource(R.color.zone_color);
									}
								}
							}
//							if (!Data.getInfoOf(getApplicationContext(),
//									Data.customer_estimate_time).equals("")
//									) {
//								int est = (int) Math.ceil(Integer.parseInt(Data
//										.getInfoOf(getApplicationContext(),
//												Data.customer_estimate_time)) / 60.0);
//							}

						} catch (Exception e) {
							Log.e("ServiceException", e.toString());
						}
					}

					@Override
					public void onFailure(Throwable arg0) {
						Log.e("Service request fail", arg0.toString());
					}
				});

	}

	void checkinfo() {

		if (locationFetcherForeground != null) {
			Data.currentLatitude = locationFetcherForeground.getLatitude();
			Data.currentLongitude = locationFetcherForeground.getLongitude();
			Log.i("Current Location", "currentLatitude::"
					+ Data.currentLatitude + "  currentLongitude"
					+ Data.currentLongitude);
		}
		mHandler = new Handler();
		mHandler.postDelayed(new Runnable() {
			public void run() {
				if (Data.compareDouble(Data.currentLatitude, 0.0) != 0
						&& Data.compareDouble(Data.currentLongitude, 0.0) != 0) {
					Log.d("currentLongitude", "currentLongitude  "
							+ Data.currentLatitude + ", "
							+ Data.currentLongitude);
					stateChange();
					Log.e("Access Token==",
							Data.getAccessToken(getApplicationContext())
									+ Data.getInfoOf(getApplicationContext(),
											Data.loginFrom).equalsIgnoreCase(
											"driver"));

					if (Data.getAccessToken(getApplicationContext()).isEmpty()) {
					} else {

					}
				} else {
					if (locationFetcherForeground != null) {
						Data.currentLatitude = locationFetcherForeground
								.getLatitude();
						Data.currentLongitude = locationFetcherForeground
								.getLongitude();
						Log.i("Current Location", "currentLatitude::"
								+ Data.currentLatitude + "  currentLongitude"
								+ Data.currentLongitude);
					}
					mHandler.postDelayed(this, 3000);
				}
			}
		}, 3000);

	}

	void convertZip() {
		getAddressGivenLatLongFrom(Data.currentLatitude, Data.currentLongitude);

	}

	public List<Address> getAddressGivenLatLongFrom(double latitude,
			double longitude) {
		Log.d("getAddressGivenLatLongFrom", "getAddressGivenLatLongFrom");
		RequestParams params = new RequestParams();
		String uri = "http://maps.google.com/maps/api/geocode/json?latlng="
				+ latitude + "," + longitude + "&sensor=false?key="
				+ Httppost_Links.BrowserKey;
		uri = uri.replaceAll(" ", "%20");
		Log.d("uri", "uri" + uri);
		AsyncHttpClient client = new AsyncHttpClient();
		client.setTimeout(Httppost_Links.SERVER_TIME_OUT_TIME);
		client.post(uri, params, new AsyncHttpResponseHandler() {

			@Override
			public void onSuccess(String response) {
				try {
					JSONObject job = new JSONObject(response);
					if (response.contains("error")) {
						String error = job.getString("error");
						Log.i("Error ", "-----------" + error);
					} else {
						String indiStr = "";
						List<String> ziparray = new ArrayList<String>();

						JSONObject jsonObject = new JSONObject(response);
						retList = new ArrayList<Address>();
						JSONArray results = jsonObject.getJSONArray("results");
						for (int i = 0; i < results.length(); i++) {
							JSONObject result = results.getJSONObject(i);
							indiStr = result.getString("formatted_address");

							JSONArray array = result
									.getJSONArray("address_components");
							for (int j = 0; j < array.length(); j++) {
								// Log.d("222","222");
								JSONObject js = array.getJSONObject(j);
								// Log.d("js","js"+js.toString());
								JSONArray jsoo = js.getJSONArray("types");
								// String comp= jsoo.getString(0);
								// Log.d(" jsoo.getString(0)"," jsoo.getString(0)"+
								// jsoo.getString(0));
								if (jsoo.getString(0).equals("postal_code")) {
									// Log.d("333","333");
									// Log.d(" jsoo.getString(0)"," jsoo.getString(0)"+
									// jsoo.getString(0)+" = "+js.getString("long_name"));

									ziparray.add(js.getString("long_name"));
									break;
								}

							}

							Address addr = new Address(Locale.getDefault());
							addr.setAddressLine(0, indiStr);
							retList.add(addr);
						}
						List<Address> addresses;
						addresses = retList;

						String address = addresses.get(0).getAddressLine(0);
						String city = addresses.get(0).getAddressLine(1);
						String country = addresses.get(0).getAddressLine(2);
						stateChange();
					}
				} catch (Exception exception) {
					exception.printStackTrace();
				}
			}

			@Override
			public void onFailure(Throwable arg0) {
			}

		});
		return retList;
	}
}
