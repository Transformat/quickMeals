package com.bitekite.driver;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;
import android.widget.Toast;

import com.bitekite.R;
import com.bitekite.driver.OrderDelivered.CreatePathAsyncTask;
import com.bitekite.utils.Data;
import com.bitekite.utils.LocationFetcherBackground;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.internal.m;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MyService extends Service {

	LocationFetcherBackground locationFetcherBackground;
//	LocationManager locationManager;
	
	public MyService() {

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
		
		if(locationFetcherBackground == null){
			Log.d("onStart", "onStart");
			locationFetcherBackground = new LocationFetcherBackground(MyService.this, 10000, 10000);
//			 locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
//			locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5*1000,1, myListener);
		}

	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {

		if (isMyServiceRunning()) {
			Log.e("service already running", "-===");
		} else {
			Log.e("service not running", "-===");
		}

		super.onStartCommand(intent, flags, startId);
		return Service.START_STICKY;
	}

	@Override
	public void onTaskRemoved(Intent rootIntent) {
		Log.e("onTaskRemoved", "=" + rootIntent);
		Intent restartService = new Intent(getApplicationContext(),
				this.getClass());
		restartService.setPackage(getPackageName());
		PendingIntent restartServicePI = PendingIntent.getService(
				getApplicationContext(), 1, restartService,
				PendingIntent.FLAG_ONE_SHOT);
		AlarmManager alarmService = (AlarmManager) getApplicationContext()
				.getSystemService(Context.ALARM_SERVICE);
		alarmService.set(AlarmManager.ELAPSED_REALTIME,
				SystemClock.elapsedRealtime() + 1000, restartServicePI);

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

		if(locationFetcherBackground != null){
			locationFetcherBackground.destroy();
			locationFetcherBackground = null;
		}
//		Log.d("onDestroyService","onDestroyService");
//		Toast.makeText(this, "My Service Stopped", Toast.LENGTH_LONG).show();
//		if(locationManager != null){
//			locationManager.removeUpdates(myListener);
//		}
	}
	
	
	
//	LocationListener myListener = new LocationListener() {
//
//		@Override
//		public void onStatusChanged(String provider, int status, Bundle extras) {
//		}
//
//		@Override
//		public void onProviderEnabled(String provider) {
//		}
//
//		@Override
//		public void onProviderDisabled(String provider) {
//		}
//
//		@Override
//		public void onLocationChanged(final Location location) {
//			Toast.makeText(MyService.this, "onLocationChanged", Toast.LENGTH_SHORT).show();
//			new Thread(new Runnable() {
//				@Override
//				public void run() {
//					try {
//						
//					Log.d("onStartonLocationChanged", "onStartonLocationChanged");
//								
//					String accessToken =Data.getAccessToken(getApplicationContext());
//					String serverUrl = Data.getInfoOf(getApplicationContext(), "BASE_URL");
//					ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
//					nameValuePairs.add(new BasicNameValuePair("access_token", accessToken));
//					nameValuePairs.add(new BasicNameValuePair("latitude", ""+location.getLatitude()));
//					nameValuePairs.add(new BasicNameValuePair("longitude", ""+location.getLongitude()));
//					nameValuePairs.add(new BasicNameValuePair("order_id", ""+Data.getInfoOf(getApplicationContext(), Data.ORDERID)));
//					nameValuePairs.add(new BasicNameValuePair("estimated_time", ""+Data.getInfoOf(getApplicationContext(), Data.ESTIMATED_TIME)));
//					
//					Log.i("access_token", accessToken);
//					Log.i("latitude", ""+location.getLatitude());
//					Log.i("longitude", ""+location.getLongitude());
//					Log.i("order_id", "order id === "+Data.getInfoOf(getApplicationContext(), Data.ORDERID));
//					Log.i("estimated_time", "estimated_time == "+Data.getInfoOf(getApplicationContext(), Data.ESTIMATED_TIME));
//					
//					Data.saveInfoOf(getApplicationContext(), "LAT", ""+location.getLatitude());
//					Data.saveInfoOf(getApplicationContext(), "LONG", ""+location.getLongitude());
//					
//					Log.e("nameValuePairs in location pi reciever","="+nameValuePairs);
//					SimpleJSONParserGet simpleJSONParser = new SimpleJSONParserGet();
//					String result = simpleJSONParser.getJSONFromUrlParams(serverUrl+"update_driver_location", nameValuePairs);
//					
//					Log.e("result    ","="+result);
//		//			Log.writeLogToFile(""+DateOperations.getCurrentTime() + "<>" + result);
//					
//					simpleJSONParser = null;
//					nameValuePairs = null;
//					} catch (Exception e) {
//						e.printStackTrace();
//					}
//				}
//			}).start();
//		}
//	};
}
