package com.bitekite.driver;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;

import com.bitekite.Log;
import com.bitekite.MainActivity;
import com.bitekite.utils.Data;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.bitekite.R;
public class Utils {

	public static void logout() {
		Data.loading_box(Data.activity, "Loading...");
		RequestParams params = new RequestParams();

		Log.d("Data.getAccessToken(Data.activity)", "Data.getAccessToken(Data.activity)"+Data.getAccessToken(Data.activity));
		params.put("access_token", ""+Data.getAccessToken(Data.activity));// +Data.ACCESS_TOKEN);
		AsyncHttpClient client = new AsyncHttpClient();
 
		client.setTimeout(Httppost_Links.SERVER_TIME_OUT_TIME);
		client.post(Httppost_Links.Baselink + "logout", params,
				new AsyncHttpResponseHandler() {

					@Override
					public void onSuccess(String response) {
						Data.loading_box_stop();
						// Log.i("request succesfull", "response = " +
						// response);
						// fillServerData(response);
						JSONObject res;
						try {
							res = new JSONObject(response);
							
							if(res.getString("status").equals("2"))
							{
//								Data.alertMessageBox(SplashScreen.this,popup.getString("title"),popup.getString("text"),Data.getInfoOf(getApplicationContext(), Data.loginFrom));
							Intent intent = new Intent(Data.activity,MainActivity.class);
							Data.activity.startActivity(intent);
							Data.activity.finish();
							}
							else
							{
							Log.i("Response", "Response" + res.toString(2));
							Data.saveAccessToken("", Data.activity);
							Data.saveInfoOf(Data.activity, Data.ACTIVATEFLAG,
									"");
							Data.saveInfoOf(Data.activity, Data.loginFrom, "");
							Data.saveInfoOf(Data.activity, Data.ACCESS_TOKEN,"");
							Data.saveInfoOf(Data.activity,Data.FLAG_REQUEST,"");
							Intent intent = new Intent(Data.activity,MainActivity.class);
							Data.activity.startActivity(intent);
							Data.activity.finish();
							
							Data.activity.overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
							}
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}

					@Override
					public void onFailure(Throwable arg0) {
						Data.loading_box_stop();
						Log.e("request fail", arg0.toString());
					}
				});
	}

	public static void  buildAlertMessageNoGps(final Activity activity) {

		if (!((LocationManager) activity.getSystemService(Context.LOCATION_SERVICE))
				.isProviderEnabled(LocationManager.GPS_PROVIDER)) {

			if (gpsDialogAlert != null && gpsDialogAlert.isShowing()) {

			}

			else {

				AlertDialog.Builder builder = new AlertDialog.Builder(activity);

				builder.setMessage(
						"The app needs active GPS connection. Enable it from Settings.")

						.setCancelable(false)

						.setPositiveButton("Go to Settings",
								new DialogInterface.OnClickListener() {

									public void onClick(
											final DialogInterface dialog,
											final int id) {

										activity.startActivity(new Intent(
												android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
										
									}

								})

				// .setNegativeButton("No", new
				// DialogInterface.OnClickListener() {

				// public void onClick(final DialogInterface dialog, final int
				// id) {

				// dialog.cancel();

				// }

				// })

				;

				gpsDialogAlert = null;

				gpsDialogAlert = builder.create();

				gpsDialogAlert.show();

			}

		}

		else {
			if (gpsDialogAlert != null && gpsDialogAlert.isShowing()) {
				
				gpsDialogAlert.dismiss();
				
			}

		}

	}
	static AlertDialog gpsDialogAlert;
}
