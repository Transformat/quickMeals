package com.bitekite;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import rmn.androidscreenlibrary.ASSL;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bitekite.driver.Httppost_Links;
import com.bitekite.driver.Utils;
import com.bitekite.utils.Data;
import com.bitekite.utils.DialogPopup;
import com.facebook.AppEventsLogger;
import com.flurry.android.FlurryAgent;
import com.google.analytics.tracking.android.EasyTracker;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class CreateProfile extends Activity {

	EditText editTextFirstName, editTextLastName;
	TextView textViewCreateProfile;// textViewAddress
	Button buttonNext, buttonCancel;
	// ImageView imageViewLocation;
	RelativeLayout relativeLayoutLocation, relativeLayoutCreateProfile;
	ProgressBar progressBar;
	AppEventsLogger logger;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		logger = AppEventsLogger.newLogger(this);
		FlurryAgent.init(this, Data.flurryKey);
		setContentView(R.layout.activity_create_profile);

		relativeLayoutCreateProfile = (RelativeLayout) findViewById(R.id.relativeLayoutCreateProfile);
		new ASSL(this, relativeLayoutCreateProfile, 1134, 720, false);

		editTextFirstName = (EditText) findViewById(R.id.editTextFirstName);
		editTextLastName = (EditText) findViewById(R.id.editTextLastName);
		textViewCreateProfile = (TextView) findViewById(R.id.textViewCreateProfile);
		buttonNext = (Button) findViewById(R.id.buttonNext);
		buttonCancel = (Button) findViewById(R.id.buttonCancel);

		editTextFirstName.setTypeface(Data
				.bariol_regular(getApplicationContext()));
		editTextLastName.setTypeface(Data
				.bariol_regular(getApplicationContext()));
		textViewCreateProfile.setTypeface(Data
				.bariol_bold(getApplicationContext()));
		buttonNext.setTypeface(Data.bariol_regular(getApplicationContext()));
		buttonCancel.setTypeface(Data.bariol_regular(getApplicationContext()));

		progressBar = (ProgressBar) findViewById(R.id.progressBar1);
		progressBar.setVisibility(View.INVISIBLE);

		editTextFirstName.addTextChangedListener(new TextWatcher() {

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
				if (!editTextLastName.getText().toString().equals("")) {
					buttonNext.setVisibility(View.VISIBLE);
				}
			}
		});

		editTextLastName.addTextChangedListener(new TextWatcher() {

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
				if (!editTextFirstName.getText().toString().equals("")) {
					buttonNext.setVisibility(View.VISIBLE);
				}
			}
		});

		buttonCancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(CreateProfile.this,
						CreateAccount.class);
				startActivity(intent);
				overridePendingTransition(R.anim.slide_in_left,
						R.anim.slide_out_right);
				finish();
			}
		});

		int currentapiVersion = android.os.Build.VERSION.SDK_INT;
		String device_name = android.os.Build.DEVICE;

		Data.OS_VERSION = "" + currentapiVersion;
		Data.DEVICE_NAME = device_name;

		buttonNext.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				String error;
				if (editTextFirstName.getText().toString().trim().isEmpty()) {
					error = "Please enter first name";
					new DialogPopup().alertPopup(CreateProfile.this, "OOPS!",
							"" + error);
					editTextFirstName.setFocusable(true);
				} else {
					if (editTextLastName.getText().toString().trim().isEmpty()) {
						error = "Please enter last name";
						new DialogPopup().alertPopup(CreateProfile.this,
								"OOPS!", "" + error);
						editTextLastName.setFocusable(true);

					} else {
						String username = editTextFirstName.getText()
								.toString().trim()
								+ " "
								+ editTextLastName.getText().toString().trim();
						Data.S_USER_NAME = username.trim();
						Log.d("", "" + Data.S_USER_NAME);
						try {
							InputMethodManager imm = (InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
				            imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0); // hide
						} catch (Exception e) {
							// TODO: handle exception
						}
						serverCallCreateCustomerAccount();
					}
				}

			}
		});

	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		Intent intent = new Intent(CreateProfile.this, CreateAccount.class);
		startActivity(intent);
		overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
		finish();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	public List<Address> getAddress() {
		try {
			Geocoder geocoder;
			List<Address> addresses;
			geocoder = new Geocoder(getApplicationContext());
			if (Data.currentLatitude != 0 || Data.currentLongitude != 0) {
				addresses = geocoder.getFromLocation(Data.currentLatitude,
						Data.currentLongitude, 1);
				String address = addresses.get(0).getAddressLine(0);
				String city = addresses.get(0).getAddressLine(1);
				String country = addresses.get(0).getAddressLine(2);
				Log.d("TAG", "address = " + address + ", city =" + city
						+ ", country = " + country);
				Data.S_ADDRESS = "" + address + ", " + city + ", " + country;
				Data.selectedplace = Data.S_ADDRESS;
				return addresses;
			} else {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		Utils.buildAlertMessageNoGps(CreateProfile.this);
		super.onWindowFocusChanged(hasFocus);
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		EasyTracker.getInstance(CreateProfile.this).activityStart(this);
		FlurryAgent.onStartSession(CreateProfile.this, Data.flurryKey);
		// super.onStart();
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		EasyTracker.getInstance(CreateProfile.this).activityStop(this);
		FlurryAgent.onEndSession(this);
		FlurryAgent.endTimedEvent("Customer signup  screen");
		// super.onStart();
	}

	// ********** This hit is to create account as customer **********//
	void serverCallCreateCustomerAccount() {
		progressBar.setVisibility(View.VISIBLE);
		RequestParams params = new RequestParams();

		params.put("user_name", "" + Data.S_USER_NAME);
		params.put("ph_no", "" + Data.S_PH_NO);
		params.put("email", "" + Data.S_EMAIL);
		params.put("password", "" + Data.S_PASSWORD);
		params.put("device_type", "0");
		params.put("device_token", "" + Data.regId);//
		params.put("country", "US");//
		params.put("device_name", "" + Data.DEVICE_NAME);// ?
		params.put("app_version", "" + Data.APP_VERSION);// ?
		params.put("os_version", "" + Data.OS_VERSION);// ?

		Log.d("user_name", "user_name = " + Data.S_USER_NAME);
		Log.d("ph_no", "ph_no = " + Data.S_PH_NO);
		Log.d("email", "email = " + Data.S_EMAIL);
		Log.d("password", "password = " + Data.S_PASSWORD);
		Log.d("device_type", "0");
		Log.d("device_token", "device_token = " + Data.regId);//
		Log.d("country", "US");//
		Log.d("device_name", "device_name = " + Data.DEVICE_NAME);// ?
		Log.d("app_version", "app_version = " + Data.APP_VERSION);// ?
		Log.d("os_version", "os_version = " + Data.OS_VERSION);// ?

		AsyncHttpClient client = new AsyncHttpClient();

		client.setTimeout(Httppost_Links.SERVER_TIME_OUT_TIME);
		client.post(Httppost_Links.Baselink + "customer_registeration", params,
				new AsyncHttpResponseHandler() {

					@Override
					public void onSuccess(String response) {
						Log.i("request succesfull", "response = " + response);
						progressBar.setVisibility(View.INVISIBLE);
						JSONObject obj;
						try {
							obj = new JSONObject(response.toString());

							if (obj.getString("status").equals("2")) {
								Intent intent = new Intent(CreateProfile.this,
										MainActivity.class);
								startActivity(intent);
								finish();
							} else {
								if (obj.has("error")) {
									Log.d("Data.SERVER_ERROR_MSG",
											"Data.SERVER_ERROR_MSG"
													+ obj.getString("error"));
									String msg = obj.getString("error");
									new DialogPopup().alertPopup(
											CreateProfile.this, "OOPS!", ""
													+ msg);

								} else {

									logger.logEvent("customer registration");
									Data.oldResponse = "";
									Data.S_PH_NO = "";
									Data.S_EMAIL = "";
									Data.S_PASSWORD = "";
									Data.S_ADDRESS = "";
									Data.S_USER_NAME = "";
									JSONObject user_data = obj
											.getJSONObject("user_data");
									Data.saveInfoOf(getApplicationContext(),
											Data.loginFrom, "customer");
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
									Data.saveInfoOf(getApplicationContext(),
											Data.CHECK_CARD,
											user_data.getString("card_check"));
									Data.saveInfoOf(getApplicationContext(),
											Data.REFERRAL_CODE, user_data
													.getString("referral_code"));
									if (obj.getString("popup").equals("0")) {
										Intent intent = new Intent(
												CreateProfile.this,
												ListofMeals.class);
										startActivity(intent);
										overridePendingTransition(
												R.anim.slide_in_right,
												R.anim.slide_out_left);
										finish();
									} else {
										JSONObject popup = obj
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
													CreateProfile.this,
													popup.getString("title"),
													popup.getString("text"),
													user_data
															.getString("current_user_status"));
										} else {
											Data.alertMessageBoxForce(
													CreateProfile.this,
													popup.getString("title"),
													popup.getString("text"));
										}
									}

								}
							}
						} catch (JSONException e) {
							e.printStackTrace();
							progressBar.setVisibility(View.INVISIBLE);
						}
					}

					@Override
					public void onFailure(Throwable arg0) {
						Log.e("request fail", arg0.toString());
						new DialogPopup().alertPopup(CreateProfile.this, "",
								"Server not responding\nTry again.");
					}
				});

	}
}
