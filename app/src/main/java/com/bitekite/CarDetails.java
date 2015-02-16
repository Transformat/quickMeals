package com.bitekite;

import org.json.JSONException;
import org.json.JSONObject;

import rmn.androidscreenlibrary.ASSL;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import com.bitekite.driver.Httppost_Links;
import com.bitekite.driver.OrderScreen;
import com.bitekite.driver.Utils;
import com.bitekite.utils.Data;
import com.bitekite.utils.DialogPopup;
import com.bitekite.utils.InternetCheck;
import com.facebook.AppEventsLogger;
import com.flurry.android.FlurryAgent;
import com.google.analytics.tracking.android.EasyTracker;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class CarDetails extends Activity {

	EditText editTextCarModel, editTextCarMaker;
	TextView textViewCarDetail;
	Button buttonFinish, buttonCancel;
	RelativeLayout relativeLayoutCarDetails;
	ProgressBar progressBar;
	AppEventsLogger logger;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_car_details);

		relativeLayoutCarDetails = (RelativeLayout) findViewById(R.id.relativeLayoutCarDetails);
		new ASSL(this, relativeLayoutCarDetails, 1134, 720, false);
		logger = AppEventsLogger.newLogger(this);
		FlurryAgent.init(this, Data.flurryKey);
		editTextCarModel = (EditText) findViewById(R.id.editTextCarModel);
		editTextCarMaker = (EditText) findViewById(R.id.editTextCarMaker);
		textViewCarDetail = (TextView) findViewById(R.id.textViewCarDetail);
		buttonFinish = (Button) findViewById(R.id.buttonFinish);
		buttonCancel = (Button) findViewById(R.id.buttonCancel);
		final WebView webView = (WebView) findViewById(R.id.webView1);
		progressBar = (ProgressBar) findViewById(R.id.progressBar1);

		editTextCarModel.setTypeface(Data
				.bariol_regular(getApplicationContext()));
		editTextCarMaker.setTypeface(Data
				.bariol_regular(getApplicationContext()));
		textViewCarDetail
				.setTypeface(Data.bariol_bold(getApplicationContext()));
		buttonFinish.setTypeface(Data.bariol_regular(getApplicationContext()));
		buttonCancel.setTypeface(Data.bariol_regular(getApplicationContext()));

		editTextCarModel.setText("" + Data.CAR_MODEL);
		editTextCarMaker.setText("" + Data.CAR_MAKER);

		buttonCancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(CarDetails.this, DriverProfile.class);
				startActivity(intent);
				overridePendingTransition(R.anim.slide_in_left,
						R.anim.slide_out_right);
				finish();
			}
		});
		editTextCarMaker.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				if (!editTextCarModel.getText().toString().equals("")) {
					buttonFinish.setVisibility(View.VISIBLE);
				}
			}
		});

		editTextCarModel.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				if (!editTextCarMaker.getText().toString().equals("")) {
					buttonFinish.setVisibility(View.VISIBLE);
				}
			}
		});

		editTextCarMaker
				.setOnEditorActionListener(new OnEditorActionListener() {

					@Override
					public boolean onEditorAction(TextView v, int actionId,
							KeyEvent event) {
						// TODO Auto-generated method stub

						if (!editTextCarModel.getText().toString().equals("")) {
							buttonFinish.setVisibility(View.VISIBLE);
						}
						return false;
					}
				});

		editTextCarModel
				.setOnEditorActionListener(new OnEditorActionListener() {

					@Override
					public boolean onEditorAction(TextView v, int actionId,
							KeyEvent event) {
						// TODO Auto-generated method stub

						if (!editTextCarMaker.getText().toString().equals("")) {
							buttonFinish.setVisibility(View.VISIBLE);
						}
						return false;
					}
				});

		buttonFinish.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				if (editTextCarModel.getText().toString().equals("")
						|| editTextCarMaker.getText().toString().equals("")) {
					new DialogPopup().alertPopup(CarDetails.this, "OOPS!",
							"Please fill in all fields.");

				} else {
					Data.CAR_MODEL = editTextCarModel.getText().toString()
							.trim();
					Data.CAR_MAKER = editTextCarMaker.getText().toString()
							.trim();
					int currentapiVersion = android.os.Build.VERSION.SDK_INT;
					String device_name = android.os.Build.DEVICE;

					Data.OS_VERSION = "" + currentapiVersion;
					Data.DEVICE_NAME = device_name;
					android.webkit.CookieManager.getInstance()
							.removeAllCookie();
					webView.clearHistory();
					webView.clearCache(true);

					webView.clearView();
					webView.setVisibility(View.VISIBLE);
					progressBar.setVisibility(View.VISIBLE);
					webView.getSettings().setJavaScriptEnabled(true);
					webView.loadUrl(Httppost_Links.STRIPE_URL_DRIVER);// "https://connect.stripe.com/oauth/authorize?response_type=code&client_id=ca_4d5O857j3dGcn4I2TK8ur8N7t9WOY3Ek&scope=read_write&state=1234");

					webView.setWebViewClient(new WebViewClient() {
						@Override
						public void onReceivedError(WebView view,
								int errorCode, String description,
								String failingUrl) {
							// TODO Auto-generated method stub
							super.onReceivedError(view, errorCode, description,
									failingUrl);
							Log.d("failingUrl", "failingUrl" + failingUrl
									+ description);
						}

						@Override
						public void onPageFinished(WebView view, String url) {
							// TODO Auto-generated method stub
							super.onPageFinished(view, url);
							progressBar.setVisibility(View.INVISIBLE);
							Log.d("url2", "url2" + url);

							String s[] = url.split("/");
							for (int i = 0; i < s.length; i++) {
								// Log.d("  i  ","  i  =  "+i);
								if (s[i].equalsIgnoreCase("user_image"))// Httppost_Links.GOOGLE_STRIPE_URL))//"www.google.com"))
								{
									// Log.d("si ","s"+i+" = "+s[i].toString());
									String s2[] = url.split("&code=");
									// Log.d("s2","s2"+s2[1].toString());
									String fin[] = s2[1].split("&");
									// Log.d("fin","fin"+fin[0].toString());
									if (InternetCheck.getInstance(
											getApplicationContext()).isOnline(
											getApplicationContext())) {
										stripeToken(fin[0].toString());
									} else {
										new DialogPopup()
												.alertPopup(CarDetails.this,
														"",
														"Check your internet connection");
									}

								}
							}

						}

					});
				}
			}
		});

	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		Log.d("2222", "2222");
		Intent intent = new Intent(CarDetails.this, DriverProfile.class);
		startActivity(intent);
		overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
		finish();
	}

	// *********** This method is used to get Stripe Secret key which we have to
	// pass to server *********//
	public void stripeToken(String token) {

		RequestParams params = new RequestParams();

		params.put("client_secret", "" + Data.APP_SECRET_KEY);// Secret key for
																// App by
																// bhargava.ravish00@gmail.com
																// (uber awe)
		params.put("code", token);
		params.put("grant_type", "authorization_code");
		Log.d("reg id ", "" + token);
		AsyncHttpClient client = new AsyncHttpClient();

		client.setTimeout(100000);
		client.post(Httppost_Links.STRIPE_TOKEN_URL + params,
				new AsyncHttpResponseHandler() {

					@Override
					public void onSuccess(String response) {
						Log.i("request succesfull", "response = " + response);
						JSONObject res;
						try {
							res = new JSONObject(response.toString());
							String access_token = res.getString("access_token");
							Log.d("access_token", "access_token = "
									+ access_token.toString());
							Data.STRIPE_SECRET_KEY = access_token;
							if (InternetCheck.getInstance(
									getApplicationContext()).isOnline(
									getApplicationContext())) {
								serverCallCreateDriverAccount();
							} else {
								new DialogPopup().alertPopup(CarDetails.this,
										"", "Check your internet connection");
							}

						} catch (JSONException e) {
							e.printStackTrace();
						}
					}

					@Override
					public void onFailure(Throwable arg0) {
						Log.e("request fail", arg0.toString());
						new DialogPopup().alertPopup(CarDetails.this, "",
								"Server not responding\nTry again.");
					}
				});

	}

	// ********** This hit is to create account as driver **********//
	void serverCallCreateDriverAccount() {

		RequestParams params = new RequestParams();

		params.put("user_name", "" + Data.S_USER_NAME);
		params.put("ph_no", "" + Data.S_PH_NO);
		params.put("email", "" + Data.S_EMAIL);
		params.put("password", "" + Data.S_PASSWORD);
		params.put("device_type", "0");
		params.put("device_token", "" + Data.regId);//
		params.put("latitude", "" + Data.currentLatitude);//
		params.put("longitude", "" + Data.currentLongitude);//
		params.put("country", "US");//
		params.put("device_name", "" + Data.DEVICE_NAME);// ?
		params.put("app_version", "" + Data.APP_VERSION);// ?
		params.put("os_version", "" + Data.OS_VERSION);// ?

		params.put("stripe_secret_key", "" + Data.STRIPE_SECRET_KEY);// ?
		params.put("car_model", "" + Data.CAR_MODEL);// ?
		params.put("car_maker", "" + Data.CAR_MAKER);

		Log.d("user_name", "user_name = " + Data.S_USER_NAME);
		Log.d("ph_no", "ph_no = " + Data.S_PH_NO);
		Log.d("email", "email = " + Data.S_EMAIL);
		Log.d("password", "password = " + Data.S_PASSWORD);
		Log.d("device_type", "0");
		Log.d("device_token", "device_token = " + Data.regId);//
		Log.d("latitude", "latitude = " + Data.currentLatitude);//
		Log.d("longitude", "longitude = " + Data.currentLongitude);//
		Log.d("country", "US");//
		Log.d("device_name", "device_name = " + Data.DEVICE_NAME);// ?
		Log.d("app_version", "app_version = " + Data.APP_VERSION);// ?
		Log.d("os_version", "os_version = " + Data.OS_VERSION);// ?
		Log.d("stripe_secret_key", "stripe_secret_key = "
				+ Data.STRIPE_SECRET_KEY);// ?
		Log.d("car_model", "car_model = " + Data.CAR_MODEL);// ?
		Log.d("car_maker", "car_maker = " + Data.CAR_MAKER);

		AsyncHttpClient client = new AsyncHttpClient();

		client.setTimeout(Httppost_Links.SERVER_TIME_OUT_TIME);
		client.post(Httppost_Links.Baselink + "driver_signup", params,
				new AsyncHttpResponseHandler() {

					@Override
					public void onSuccess(String response) {
						Log.i("request succesfull", "response = " + response);
						JSONObject res;
						try {
							res = new JSONObject(response.toString());
							if (res.getString("status").equals("2")) {
								Intent intent = new Intent(CarDetails.this,
										MainActivity.class);
								startActivity(intent);
								finish();
							} else {
								if (res.has("error")) {
									Log.d("Data.SERVER_ERROR_MSG",
											"Data.SERVER_ERROR_MSG"
													+ res.getString("error"));
									new DialogPopup().alertPopup(
											CarDetails.this, "OOPS!",
											"" + res.getString("error"));

								} else {

									Data.S_PH_NO = "";
									Data.S_EMAIL = "";
									Data.S_PASSWORD = "";
									Data.S_ADDRESS = "";
									Data.S_USER_NAME = "";
									logger.logEvent("driver registration");
									JSONObject user_data = res
											.getJSONObject("user_data");

									Data.saveInfoOf(getApplicationContext(),
											Data.USER_NAME,
											user_data.getString("user_name"));
									Data.saveAccessToken(
											user_data.getString("access_token"),
											getApplicationContext());
									Data.saveInfoOf(getApplicationContext(),
											Data.ACCESS_TOKEN,
											user_data.getString("access_token"));
									Data.saveInfoOf(getApplicationContext(),
											Data.loginFrom, "driver");
									Data.saveInfoOf(getApplicationContext(),
											Data.FLAG_REQUEST,
											res.getString("flag"));
									Data.saveInfoOf(getApplicationContext(),
											Data.CHECKINVALUE,
											user_data.getString("checked_in"));
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

									progressBar.setVisibility(View.INVISIBLE);

									if (res.getString("popup").equals("0")) {

										Intent intent = new Intent(
												CarDetails.this,
												OrderScreen.class);
										startActivity(intent);
										overridePendingTransition(
												R.anim.slide_in_right,
												R.anim.slide_out_left);
										finish();
									} else {
										JSONObject popup = res
												.getJSONObject("popup");
										Log.d("-- loginFrom --",
												"-- loginFrom -- "
														+ Data.getInfoOf(
																getApplicationContext(),
																Data.loginFrom));
										String force = popup
												.getString("is_force");

										if (force.equals("0")) {
											Data.alertMessageBox(
													CarDetails.this,
													popup.getString("title"),
													popup.getString("text"),
													user_data
															.getString("current_user_status"));
										} else {
											Data.alertMessageBoxForce(
													CarDetails.this,
													popup.getString("title"),
													popup.getString("text"));
										}
									}

								}
							}
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}

					@Override
					public void onFailure(Throwable arg0) {
						Log.e("request fail", arg0.toString());
						new DialogPopup().alertPopup(CarDetails.this, "",
								"Server not responding\nTry again.");
					}
				});

	}

	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		Utils.buildAlertMessageNoGps(CarDetails.this);
		super.onWindowFocusChanged(hasFocus);
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		EasyTracker.getInstance(CarDetails.this).activityStart(this);
		FlurryAgent.onStartSession(CarDetails.this, Data.flurryKey);
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		EasyTracker.getInstance(CarDetails.this).activityStop(this);
		FlurryAgent.onEndSession(this);
		FlurryAgent.endTimedEvent("driver signup screen");
	}
}
