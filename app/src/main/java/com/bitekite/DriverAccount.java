package com.bitekite;

import org.json.JSONException;
import org.json.JSONObject;

import rmn.androidscreenlibrary.ASSL;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bitekite.driver.Httppost_Links;
import com.bitekite.driver.Utils;
import com.bitekite.utils.Data;
import com.bitekite.utils.DialogPopup;
import com.bitekite.utils.EmailFormatValidator;
import com.bitekite.utils.InternetCheck;
import com.google.analytics.tracking.android.EasyTracker;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class DriverAccount extends Activity {

	TextView textViewCustomer, textViewDriverAccount, textViewTapHere,
			textViewPolicy, textView11, textView12, textViewTerms;
	EditText editTextEmail, editTextPhone, editTextPassword;
	EmailFormatValidator check;
	Button buttonNext, buttonCancel;
	RelativeLayout relativeLayoutDriverAccount;
	RelativeLayout relativeLayoutDriver;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_driver_account);

		relativeLayoutDriverAccount = (RelativeLayout) findViewById(R.id.relativeLayoutDriverAccount);
		new ASSL(this, relativeLayoutDriverAccount, 1134, 720, false);

		textViewCustomer = (TextView) findViewById(R.id.textViewCustomer);
		textViewDriverAccount = (TextView) findViewById(R.id.textViewDriverAccount);
		textViewTapHere = (TextView) findViewById(R.id.textViewTapHere);
		editTextEmail = (EditText) findViewById(R.id.editTextEmailDriver);
		editTextPhone = (EditText) findViewById(R.id.editTextPhoneDriver);
		editTextPassword = (EditText) findViewById(R.id.editTextPasswordDriver);
		buttonNext = (Button) findViewById(R.id.buttonNext);
		buttonCancel = (Button) findViewById(R.id.buttonCancel);
		relativeLayoutDriver = (RelativeLayout) findViewById(R.id.relativeLayoutDriver);

		textViewPolicy = (TextView) findViewById(R.id.textViewPolicy);
		textView11 = (TextView) findViewById(R.id.textView11);
		textView12 = (TextView) findViewById(R.id.textView12);
		textViewTerms = (TextView) findViewById(R.id.textViewTerms);

		textViewPolicy.setTypeface(Data.bariol_bold(getApplicationContext()));
		textView11.setTypeface(Data.bariol_bold(getApplicationContext()));
		textView12.setTypeface(Data.bariol_bold(getApplicationContext()));
		textViewTerms.setTypeface(Data.bariol_bold(getApplicationContext()));
		textViewPolicy.setText(Html.fromHtml("<u>Privacy Policy</u>"));
		textViewTerms.setText(Html.fromHtml("<u>Terms of Service</u>"));

		textViewCustomer.setTypeface(Data
				.bariol_regular(getApplicationContext()));
		editTextEmail.setTypeface(Data.bariol_regular(getApplicationContext()));
		editTextPhone.setTypeface(Data.bariol_regular(getApplicationContext()));
		editTextPassword.setTypeface(Data
				.bariol_regular(getApplicationContext()));
		buttonNext.setTypeface(Data.bariol_regular(getApplicationContext()));
		buttonCancel.setTypeface(Data.bariol_regular(getApplicationContext()));
		textViewDriverAccount.setTypeface(Data
				.bariol_bold(getApplicationContext()));
		textViewTapHere.setTypeface(Data.bariol_bold(getApplicationContext()));

		check = new EmailFormatValidator();

		textViewPolicy.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				CreateAccount.Flag = 0;
				Intent intent = new Intent(DriverAccount.this, Policy.class);
				startActivity(intent);
				overridePendingTransition(R.anim.slide_in_right,
						R.anim.slide_out_left);

			}
		});

		textViewTerms.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				CreateAccount.Flag = 1;
				Intent intent = new Intent(DriverAccount.this, Policy.class);
				startActivity(intent);
				overridePendingTransition(R.anim.slide_in_right,
						R.anim.slide_out_left);

			}
		});

		relativeLayoutDriver.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(DriverAccount.this,
						CreateAccount.class);
				startActivity(intent);
				overridePendingTransition(R.anim.hold, R.anim.hold);
				finish();
			}
		});

		buttonCancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(DriverAccount.this,
						MainActivity.class);
				startActivity(intent);
				overridePendingTransition(R.anim.slide_in_left,
						R.anim.slide_out_right);
				finish();
			}
		});

		editTextEmail.setText("" + Data.S_EMAIL);
		editTextPhone.setText("" + Data.S_PH_NO);

		editTextEmail.addTextChangedListener(new TextWatcher() {

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
				if (editTextEmail.getText().toString().equals("")) {
					// error="Enter email";
					editTextEmail.setFocusable(true);
				} else {
					Boolean validate = check.validate(editTextEmail.getText()
							.toString());
					if (validate) {

						if (!editTextPassword.getText().toString().equals("")
								&& !editTextPhone.getText().toString()
										.equals("")) {
							buttonNext.setVisibility(View.VISIBLE);
						}
					}
				}
			}
		});

		editTextPhone.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {
				if (editTextPhone.getText().toString().equals("")) {
					editTextPhone.setFocusable(true);
				} else {
					if (editTextPhone.getText().toString().length() < 10) {
						editTextPhone.setFocusable(true);
					} else {
						if (!editTextEmail.getText().toString().equals("")
								&& !editTextPassword.getText().toString()
										.equals("")) {
							buttonNext.setVisibility(View.VISIBLE);
						}
					}
				}
			}
		});

		InputFilter filter = new InputFilter() {

			@Override
			public CharSequence filter(CharSequence source, int start, int end,
					Spanned dest, int dstart, int dend) {
				if (source.length() > 0) {

					if (!Character.isDigit(source.charAt(0))) {
						Log.e("start", "start");
						return "";
					}

					else {
						if (dstart == 3) {
							return source + ") ";
						} else if (dstart == 0) {
							return "(" + source;
						} else if ((dstart == 5) || (dstart == 9))
							return "-" + source;
						else if (dstart >= 14)
							return "";
					}

				} else {

				}

				return null;

			}
		};

		editTextPhone.setFilters(new InputFilter[] { filter });

		editTextPassword.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {
				if (editTextPassword.getText().toString().equals("")) {
					editTextPassword.setFocusable(true);
				} else {
					if (!editTextEmail.getText().toString().equals("")
							&& !editTextPhone.getText().toString().equals("")) {
						buttonNext.setVisibility(View.VISIBLE);
					}
				}
			}
		});

		buttonNext.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				String error;
				if (editTextEmail.getText().toString().equals("")) {
					error = "Please enter your email";
					new DialogPopup().alertPopup(DriverAccount.this, "OOPS!",
							"" + error);
					editTextEmail.setFocusable(true);
				} else {
					Boolean validate = check.validate(editTextEmail.getText()
							.toString());
					if (validate) {
						if (editTextPhone.getText().toString().equals("")) {
							error = "Please enter you phone number ";
							new DialogPopup().alertPopup(DriverAccount.this,
									"OOPS!", "" + error);
							editTextPhone.setFocusable(true);
						} else {
							if (editTextPhone.getText().toString().length() < 14) {
								error = "Phone number less than 10 digits";
								new DialogPopup()
										.alertPopup(DriverAccount.this,
												"OOPS!", "" + error);
								editTextPhone.setFocusable(true);
							} else {
								if (editTextPassword.getText().toString()
										.equals("")) {
									error = "Please enter your password";
									new DialogPopup().alertPopup(
											DriverAccount.this, "OOPS!", ""
													+ error);
									editTextPassword.setFocusable(true);
								} else {

									if (editTextPassword.getText().toString()
											.length() >= 6) {
										Data.S_EMAIL = editTextEmail.getText()
												.toString();
										Data.S_PH_NO = editTextPhone.getText()
												.toString();
										Data.S_PASSWORD = editTextPassword
												.getText().toString();
										if (InternetCheck
												.getInstance(
														getApplicationContext())
												.isOnline(
														getApplicationContext())) {
											serverCallEmailValidation();
										} else {
											new DialogPopup()
													.alertPopup(
															DriverAccount.this,
															"",
															"Check your internet connection");
										}

									} else {
										error = "Password must be 6 characters long";
										new DialogPopup().alertPopup(
												DriverAccount.this, "OOPS!", ""
														+ error);
										editTextPassword.setFocusable(true);
									}
								}
							}
						}
					} else {
						error = "Invalid email address";
						new DialogPopup().alertPopup(DriverAccount.this,
								"OOPS!", "" + error);
						editTextEmail.setFocusable(true);
					}
				}

			}
		});

	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();

		Intent intent = new Intent(DriverAccount.this, MainActivity.class);
		startActivity(intent);
		overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
		finish();
	}

	void serverCallEmailValidation() {
		RequestParams params = new RequestParams();

		params.put("email", "" + editTextEmail.getText().toString());// +Data.ACCESS_TOKEN);
		AsyncHttpClient client = new AsyncHttpClient();

		client.setTimeout(Httppost_Links.SERVER_TIME_OUT_TIME);
		client.post(Httppost_Links.Baselink + "validate_email", params,
				new AsyncHttpResponseHandler() {

					@Override
					public void onSuccess(String response) {
						JSONObject res;
						try {
							res = new JSONObject(response);
							Log.i("Response", "Response" + res.toString(2));
							if (res.getString("status").equals("2")) {
								Intent intent = new Intent(DriverAccount.this,
										MainActivity.class);
								startActivity(intent);
								finish();
							} else {
								if (res.has("error")) {
									Log.d("Data.SERVER_ERROR_MSG",
											"Data.SERVER_ERROR_MSG"
													+ res.getString("error"));
									new DialogPopup().alertPopup(
											DriverAccount.this, "OOPS!", ""
													+ res.getString("error"));

								} else {
									Intent intent = new Intent(
											DriverAccount.this,
											DriverProfile.class);
									startActivity(intent);
									overridePendingTransition(
											R.anim.slide_in_right,
											R.anim.slide_out_left);
									finish();
								}
							}
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}

					@Override
					public void onFailure(Throwable arg0) {
						Log.e("request fail", arg0.toString());
						new DialogPopup().alertPopup(DriverAccount.this, "",
								"Server not responding\nTry again.");
					}
				});
	}

	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		Utils.buildAlertMessageNoGps(DriverAccount.this);
		super.onWindowFocusChanged(hasFocus);
	}

	@Override
	protected void onStart() {
		super.onStart();
		EasyTracker.getInstance(DriverAccount.this).activityStart(this);
	}

	@Override
	protected void onStop() {
		super.onStop();
		EasyTracker.getInstance(DriverAccount.this).activityStop(this);
	}
}
