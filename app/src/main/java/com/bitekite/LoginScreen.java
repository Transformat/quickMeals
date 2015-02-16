package com.bitekite;

import org.json.JSONObject;

import rmn.androidscreenlibrary.ASSL;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bitekite.driver.Httppost_Links;
import com.bitekite.driver.OrderScreen;
import com.bitekite.driver.Utils;
import com.bitekite.utils.Data;
import com.bitekite.utils.DialogPopup;
import com.bitekite.utils.EmailFormatValidator;
import com.bitekite.utils.InternetCheck;
import com.flurry.android.FlurryAgent;
import com.google.analytics.tracking.android.EasyTracker;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class LoginScreen extends Activity {

	RelativeLayout relativeLayoutSignIn, relativeLayoutBack;
	EditText emailLogin;
	EditText passwordLogin;
	EmailFormatValidator check;
	TextView textViewSignIn, textViewForgot;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login_screen);

		new ASSL(this, (ViewGroup) findViewById(R.id.root), 1134, 720, false);
		FlurryAgent.init(this, Data.flurryKey);
		relativeLayoutSignIn = (RelativeLayout) findViewById(R.id.signInLayout);
		relativeLayoutBack = (RelativeLayout) findViewById(R.id.backBtnLayout);
		emailLogin = (EditText) findViewById(R.id.editTextEmail);
		passwordLogin = (EditText) findViewById(R.id.editTextPassword);
		textViewSignIn = (TextView) findViewById(R.id.signInTxt);
		textViewForgot = (TextView) findViewById(R.id.forgotpassword);

		emailLogin.setTypeface(Data.bariol_regular(getApplicationContext()));
		passwordLogin.setTypeface(Data.bariol_regular(getApplicationContext()));
		textViewSignIn.setTypeface(Data.bariol_bold(getApplicationContext()));
		textViewForgot.setTypeface(Data.bariol_bold(getApplicationContext()));

		Data.totalValue = 0;
		check = new EmailFormatValidator();
		relativeLayoutBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(LoginScreen.this, MainActivity.class);
				startActivity(intent);
				overridePendingTransition(R.anim.slide_in_left,
						R.anim.slide_out_right);
				finish();

			}
		});

		textViewForgot.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(LoginScreen.this,
						ForgotPassword.class);
				startActivity(intent);
				overridePendingTransition(R.anim.slide_in_left,
						R.anim.slide_out_right);
				finish();
			}
		});

		relativeLayoutSignIn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				int currentapiVersion = android.os.Build.VERSION.SDK_INT;
				String device_name = android.os.Build.DEVICE;

				Data.OS_VERSION = "" + currentapiVersion;
				Data.DEVICE_NAME = device_name;

				if (emailLogin.getText().toString().isEmpty()) {
					new DialogPopup().alertPopup(LoginScreen.this, "",
							"Please enter your email address");
				} else {
					Boolean validate = check.validate(emailLogin.getText()
							.toString());
					if (validate) {
						if (passwordLogin.getText().toString().isEmpty()) {
							new DialogPopup().alertPopup(LoginScreen.this, "",
									"Oops Please add your password");
						} else {

							if (InternetCheck.getInstance(
									getApplicationContext()).isOnline(
									getApplicationContext())) {
								try {
									InputMethodManager imm = (InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
						            imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0); // hide
								} catch (Exception e) {
									// TODO: handle exception
								}
								serverCallLogin();
							} else {
								new DialogPopup().alertPopup(LoginScreen.this,
										"", "Check your internet connection");
							}
						}
					} else {
						new DialogPopup().alertPopup(LoginScreen.this, "",
								"Enter valid email id.");
					}
				}
			}
		});

	}

	void serverCallLogin() {
		try {
			Data.loading_box(this, "Loading...");
		} catch (Exception e) {
		}
		RequestParams params = new RequestParams();

		params.put("email", "" + emailLogin.getText().toString());// +Data.EMAIL);
		params.put("password", "" + passwordLogin.getText().toString());// +Data.PASSWORD);
		params.put("device_type", "0");
		params.put("device_token", "" + Data.regId);//
		params.put("latitude", "" + Data.currentLatitude);//
		params.put("longitude", "" + Data.currentLongitude);//
		params.put("country", "US");//
		params.put("device_name", "" + Data.DEVICE_NAME);// ?
		params.put("os_version", "" + Data.OS_VERSION);// ?
		params.put("app_version", "" + Data.APP_VERSION);// ?

		Log.d("email", "email = " + emailLogin.getText().toString());// +Data.EMAIL);
		Log.d("password", "password = " + passwordLogin.getText().toString());// +Data.PASSWORD);
		Log.d("device_type", "0");
		Log.d("device_token", "device_token = " + Data.regId);//
		Log.d("latitude", "latitude = " + Data.currentLatitude);//
		Log.d("longitude", "longitude = " + Data.currentLongitude);//
		Log.d("country", "US");//
		Log.d("device_name", "device_name = " + Data.DEVICE_NAME);// ?
		Log.d("os_version", "os_version = " + Data.OS_VERSION);// ?
		Log.d("app_version", "app_version = " + Data.APP_VERSION);// ?
		Log.d("device_token", "device_token = " + Data.regId);//

		AsyncHttpClient client = new AsyncHttpClient();
		client.setTimeout(Httppost_Links.SERVER_TIME_OUT_TIME);
		client.post(Httppost_Links.Baselink + "email_login", params,
				new AsyncHttpResponseHandler() {

					@Override
					public void onSuccess(String response) {
						Data.loading_box_stop();
						JSONObject res;
						try {
							res = new JSONObject(response);
							Log.d("response", "response" + res.toString(2));

							if (res.getString("status").equals("2")) {
								Intent intent = new Intent(LoginScreen.this,
										MainActivity.class);
								startActivity(intent);
								finish();
							} else {
								if (res.has("error")) {
									Log.d("Data.SERVER_ERROR_MSG",
											"Data.SERVER_ERROR_MSG"
													+ res.getString("error"));
									new DialogPopup().alertPopup(
											LoginScreen.this, "OOPS!",
											"" + res.getString("error"));

								} else {

									JSONObject user_data = res
											.getJSONObject("user_data");

									Data.oldResponse = "";
									Data.saveInfoOf(getApplicationContext(),
											Data.USER_NAME,
											user_data.getString("user_name"));
									Data.saveInfoOf(getApplicationContext(),
											Data.ACCESS_TOKEN,
											user_data.getString("access_token"));
									Data.saveAccessToken(
											user_data.getString("access_token"),
											getApplicationContext());
									Log.i("ACCCCCC",
											"="
													+ Data.getAccessToken(getApplicationContext()));
									Data.saveInfoOf(getApplicationContext(),
											Data.EMAIL,
											user_data.getString("email"));
									Data.saveInfoOf(getApplicationContext(),
											Data.PH_NO,
											user_data.getString("phone_no"));
									Data.saveInfoOf(
											getApplicationContext(),
											Data.CURRENT_USER_STATUS,
											user_data
													.getString("current_user_status"));
									Data.saveInfoOf(getApplicationContext(),
											Data.USER_IMAGE_URL,
											user_data.getString("user_image"));

									if (user_data.getString(
											"current_user_status").equals("2")) {
										Data.saveInfoOf(
												getApplicationContext(),
												Data.loginFrom, "customer");
										Data.saveInfoOf(
												getApplicationContext(),
												Data.LAST_4,
												user_data.getString("last_4"));
										Data.saveInfoOf(
												getApplicationContext(),
												Data.ADDRESS,
												user_data.getString("address"));
										Data.saveInfoOf(
												getApplicationContext(),
												Data.CUSTOMER_ID,
												user_data
														.getString("customer_id"));
										Data.saveInfoOf(
												getApplicationContext(),
												Data.CHECK_CARD,
												user_data
														.getString("card_check"));
										Data.saveInfoOf(
												getApplicationContext(),
												Data.REFERRAL_CODE,
												user_data
														.getString("referral_code"));
									} else {
										Data.saveInfoOf(
												getApplicationContext(),
												Data.loginFrom, "driver");
										Data.saveInfoOf(
												getApplicationContext(),
												Data.FLAG_REQUEST,
												res.getString("flag"));
										Data.saveInfoOf(
												getApplicationContext(),
												Data.CHECKINVALUE,
												user_data
														.getString("checked_in"));
									}

									if (res.getString("popup").equals("0")) {
										if (user_data.getString(
												"current_user_status").equals(
												"2")) {
											Intent intent = new Intent(
													LoginScreen.this,
													ListofMeals.class);
											startActivity(intent);
											overridePendingTransition(
													R.anim.slide_in_right,
													R.anim.slide_out_left);
											finish();
										} else {
											Intent intent = new Intent(
													LoginScreen.this,
													OrderScreen.class);
											startActivity(intent);
											overridePendingTransition(
													R.anim.slide_in_right,
													R.anim.slide_out_left);
											finish();
										}
									} else {
										JSONObject popup = res
												.getJSONObject("popup");
										String force = popup
												.getString("is_force");
										Log.d("-- loginFrom --",
												"-- loginFrom -- "
														+ Data.getInfoOf(
																getApplicationContext(),
																Data.loginFrom));
										if (force.equals("0")) {
											Data.alertMessageBox(
													LoginScreen.this,
													popup.getString("title"),
													popup.getString("text"),
													user_data
															.getString("current_user_status"));
										} else {
											Data.alertMessageBoxForce(
													LoginScreen.this,
													popup.getString("title"),
													popup.getString("text"));
										}
									}
								}
							}
						} catch (Exception e) {
							e.printStackTrace();
							Log.v("exception ", e.toString());
						}
					}

					@Override
					public void onFailure(Throwable arg0) {
						Log.e("request fail", arg0.toString());
						Data.loading_box_stop();
						new DialogPopup().alertPopup(LoginScreen.this, "",
								"Server not responding\nTry again.");
					}
				});
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		Intent intent = new Intent(LoginScreen.this, MainActivity.class);
		startActivity(intent);
		overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
		finish();

	}

	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		Utils.buildAlertMessageNoGps(LoginScreen.this);
		super.onWindowFocusChanged(hasFocus);
	}

	@Override
	protected void onStart() {
		super.onStart();
		EasyTracker.getInstance(LoginScreen.this).activityStart(this);
		FlurryAgent.onStartSession(LoginScreen.this, Data.flurryKey);
	}

	@Override
	protected void onStop() {
		super.onStop();
		EasyTracker.getInstance(LoginScreen.this).activityStop(this);
		FlurryAgent.onEndSession(this);
		FlurryAgent.endTimedEvent("login screen");
	}
}
