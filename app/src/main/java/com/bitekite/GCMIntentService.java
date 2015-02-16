/**
h * This class is used for notification
 */
package com.bitekite;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.bitekite.driver.MyService;
import com.bitekite.driver.OrderScreen;
import com.bitekite.utils.Data;
import com.google.android.gcm.GCMBaseIntentService;

public class GCMIntentService extends GCMBaseIntentService {

	private static final String TAG = "GCMIntentService";
	public static String stringMessage = "", flag = "", orderId = "",
			dateTime = "", price = "", meals = "";

    public GCMIntentService(){}


    /**
	 * Method called on device registered
	 **/
	@Override
	protected void onRegistered(Context context, String registrationId) {

		// Get Global Controller Class object (see application tag in
		// AndroidManifest.xml)

		Log.i(TAG, "OnRegistered: regId = " + registrationId);
		Data.regId = registrationId;
	}

	/**
	 * Method called on device unregistred
	 * */
	@Override
	protected void onUnregistered(Context context, String registrationId) {

		Log.i(TAG, "Device unregistered");

	}

	/**
	 * Method called on Receiving a new message from GCM server
	 * */
	@Override
	protected void onMessage(Context context, Intent intent) {

		Log.i(TAG, "Received message");
		String message = intent.getExtras().getString("message");
		JSONObject jsonObject = null;
		Log.i("push received", "message" + message);

		try {
			jsonObject = new JSONObject(message);
			stringMessage = jsonObject.getString("message");
			flag = jsonObject.getString("flag");
			Data.saveInfoOf(getApplicationContext(), "OD", flag);
			// Log.e("REs",""+jsonObject.getString("message")+"=="+jsonObject.getString("flag")+"=="+jsonObject.getString("order_id"));

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (OrderScreen.pushUpdater != null && flag.equals("15")) { // New Order
			Log.e("Call", "pushUpdater");
			try {
				orderId = jsonObject.getString("order_id");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			OrderScreen.pushUpdater.pushUpdater();
		} else if (flag.equals("19") && OrderScreen.pushUpdater != null) // Check
																			// Out
		{
//			stopService(new Intent(getApplicationContext(),
//					MyService.class));
//			stopService(new Intent(Data.activity,MyService.class));
			OrderScreen.pushUpdater.pushUpdater();

		} else if (OrderScreen.pushUpdater != null && flag.equals("25")) // admin
																			// Check
																			// In
		{
			OrderScreen.pushUpdater.pushUpdater();
		} else if (OrderScreen.pushUpdater != null && flag.equals("16")) // Reallocation
		{
			OrderScreen.pushUpdater.pushUpdater();
		} else if (OrderScreen.pushUpdater != null && flag.equals("42")) // approv
		{
			OrderScreen.pushUpdater.pushUpdater();
		} else if (OrderScreen.pushUpdater != null && flag.equals("43")) // decline
		{
			OrderScreen.pushUpdater.pushUpdater();
		} else if (flag.equals("40")) // rating
		{
			Data.saveInfoOf(getApplicationContext(), "GCM", "40");
			Data.saveInfoOf(getApplicationContext(), Data.OrderPlacedFlag, "0");
			try {
				orderId = jsonObject.getString("order_id");
				dateTime = jsonObject.getString("time");
				price = jsonObject.getString("price");
				meals = jsonObject.getString("meals");

				Data.saveInfoOf(getApplicationContext(), "Rating_orderId",
						orderId);
				Data.saveInfoOf(getApplicationContext(), "Rating_dateTime",
						dateTime);
				Data.saveInfoOf(getApplicationContext(), "Rating_price", price);
				Data.saveInfoOf(getApplicationContext(), "Rating_meals", meals);

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if ((ListofMeals.pushUpdater != null)) {
				ListofMeals.pushUpdater.pushUpdater();
			} else {
				generateNotification(context, stringMessage);
			}

		} else if (flag.equals("41")) // driver on the way
		{
			Data.saveInfoOf(getApplicationContext(), "GCM", "41");
			try {
				// ListofMeals.textViewOrderPlaced.setText("Your order is being processed"
				// +
				// "\nYour server will arrive in "+Data.getInfoOf(getApplicationContext(),
				// "time")
				// +" minutes, \nclick here to check your sever's location");

				orderId = jsonObject.getString("order_id");
				Data.saveInfoOf(getApplicationContext(),
						Data.customer_estimate_time,
						jsonObject.getString("estimated_time"));

			} catch (JSONException e) {
				e.printStackTrace();
			}
			if ((ListofMeals.pushUpdater != null)) {
				ListofMeals.pushUpdater.pushUpdater();
			} else {
				generateNotification(context, stringMessage);
			}
		} else {
			generateNotification(context, stringMessage);
		}
		Log.e("message", message);

	}

	/**
	 * Method called on Error
	 * */
	@Override
	public void onError(Context context, String errorId) {

		Log.i(TAG, "Received error: " + errorId);

	}

	@Override
	protected boolean onRecoverableError(Context context, String errorId) {

		// log message
		Log.i(TAG, "Received recoverable error: " + errorId);

		return super.onRecoverableError(context, errorId);
	}

	/**
	 * Create a notification to inform the user that server has sent a message.
	 */
	@SuppressWarnings("deprecation")
	private static void generateNotification(Context context, String message) {
		int icon = R.drawable.notification_icon;
		long when = System.currentTimeMillis();
		NotificationManager notificationManager = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);
		Notification notification = new Notification(icon, message, when);

		String title = context.getString(R.string.app_name);
		Intent notificationIntent;
		if (flag.equals("40")) {
			notificationIntent = new Intent(context, Receipt.class);
		} else if (flag.equals("41")) {
			notificationIntent = new Intent(context, DriverLocationFinder.class);
		} else if (flag.equals("42")) {
			notificationIntent = new Intent(context, SplashScreen.class);
		} else {
			notificationIntent = new Intent(context, SplashScreen.class);
		}

		// set intent so it does not start a new activity
		notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
				| Intent.FLAG_ACTIVITY_SINGLE_TOP);
		PendingIntent intent = PendingIntent.getActivity(context, 0,
				notificationIntent, 0);
		notification.setLatestEventInfo(context, title, message, intent);
		notification.flags |= Notification.FLAG_AUTO_CANCEL;

		// Play default notification sound
		notification.defaults |= Notification.DEFAULT_SOUND;

		// notification.sound = Uri.parse("android.resource://" +
		// context.getPackageName() + "your_sound_file_name.mp3");

		// Vibrate if vibrate is enabled
		notification.defaults |= Notification.DEFAULT_VIBRATE;
		notificationManager.notify(0, notification);

	}

}
