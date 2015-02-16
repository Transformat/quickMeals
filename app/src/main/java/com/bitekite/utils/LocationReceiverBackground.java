package com.bitekite.utils;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.util.Log;

import com.bitekite.driver.SimpleJSONParser;
import com.google.android.gms.location.LocationClient;

public class LocationReceiverBackground extends BroadcastReceiver {
	
	
	Context context;

    @Override
    public void onReceive(Context context, Intent intent) {
    	Log.d("staaaaart","staaaaaart");
    	
    	PowerManager powerManager = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
		WakeLock wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "MyWakelockTag2");
		wakeLock.acquire();
		this.context= context;
    	try {
			Location location = (Location) intent.getExtras().get(LocationClient.KEY_LOCATION_CHANGED);
			Log.e("location changer in reciever","="+location);
			String accessToken =Data.getAccessToken(context);
			String serverUrl = Data.getInfoOf(context, "BASE_URL");
			Data.saveInfoOf(context, "LAT", ""+location.getLatitude());
			Data.saveInfoOf(context, "LONG", ""+location.getLongitude());
			sendLocationToServer(location, accessToken, serverUrl);
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
    	wakeLock.release();
    }
    
    public void sendLocationToServer(final Location location, final String accessToken,final String serverUrl){
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					
					ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
					nameValuePairs.add(new BasicNameValuePair("access_token", accessToken));
					nameValuePairs.add(new BasicNameValuePair("latitude", ""+location.getLatitude()));
					nameValuePairs.add(new BasicNameValuePair("longitude", ""+location.getLongitude()));
					nameValuePairs.add(new BasicNameValuePair("order_id", ""+Data.getInfoOf(context, Data.ORDERID)));
					nameValuePairs.add(new BasicNameValuePair("estimated_time", ""+Data.getInfoOf(context, Data.ESTIMATED_TIME)));
//					
					
					Log.i("access_token", accessToken);
					Log.i("latitude", ""+location.getLatitude());
					Log.i("longitude", ""+location.getLongitude());
					Log.i("order_id", "order id === "+Data.getInfoOf(context, Data.ORDERID));
					Log.i("estimated_time", "estimated_time == "+Data.getInfoOf(context, Data.ESTIMATED_TIME));
					
					Data.saveInfoOf(context, "LAT", ""+location.getLatitude());
					Data.saveInfoOf(context, "LONG", ""+location.getLongitude());
					
					Log.e("nameValuePairs in location pi reciever","="+nameValuePairs);
					
					SimpleJSONParser simpleJSONParser = new SimpleJSONParser();
					String result = simpleJSONParser.getJSONFromUrlParams(serverUrl+"update_driver_location", nameValuePairs);
					
					Log.e("result    ","="+result);
//					Log.writeLogToFile(""+DateOperations.getCurrentTime() + "<>" + result);
					
					simpleJSONParser = null;
					nameValuePairs = null;
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();
	}
}