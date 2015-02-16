package com.bitekite;

import org.json.JSONObject;

import rmn.androidscreenlibrary.ASSL;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
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

public class ForgotPassword extends Activity {

	RelativeLayout relativeLayoutRequest, relativeLayoutBack;
	EditText emailLogin;
	EmailFormatValidator check;
	TextView textViewRequest, textViewContent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_forgot_password);

		new ASSL(this, (ViewGroup) findViewById(R.id.root), 1134, 720, false);
		relativeLayoutRequest = (RelativeLayout) findViewById(R.id.requestLayout);
		relativeLayoutBack = (RelativeLayout) findViewById(R.id.backBtnLayout);
		emailLogin = (EditText) findViewById(R.id.editTextEmail);
		textViewRequest = (TextView) findViewById(R.id.requestTxt);
		textViewContent = (TextView) findViewById(R.id.TXT);

		emailLogin.setTypeface(Data.bariol_regular(getApplicationContext()));
		textViewRequest.setTypeface(Data.bariol_bold(getApplicationContext()));
		textViewContent.setTypeface(Data
				.bariol_regular(getApplicationContext()));
		check = new EmailFormatValidator();

		relativeLayoutBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(ForgotPassword.this,
						LoginScreen.class);
				startActivity(intent);
				overridePendingTransition(R.anim.slide_in_left,
						R.anim.slide_out_right);
				finish();

			}
		});

		relativeLayoutRequest.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if (emailLogin.getText().toString().isEmpty()) {
					new DialogPopup().alertPopup(ForgotPassword.this, "",
							"Enter email.");
				} else {
					Boolean validate = check.validate(emailLogin.getText()
							.toString());
					if (validate) {
						if (InternetCheck.getInstance(getApplicationContext())
								.isOnline(getApplicationContext())) {
							serverCallForgotPassword();
						} else {
							new DialogPopup().alertPopup(ForgotPassword.this,
									"", "Check your internet connection");
						}
					} else {
						new DialogPopup().alertPopup(ForgotPassword.this, "",
								"Enter valid email id.");
					}

				}
			}
		});
	}

	void serverCallForgotPassword() {
		Data.loading_box(this, "Sending...");
		RequestParams params = new RequestParams();

		params.put("email", "" + emailLogin.getText().toString());// +Data.EMAIL);

		AsyncHttpClient client = new AsyncHttpClient();

		client.setTimeout(Httppost_Links.SERVER_TIME_OUT_TIME);
		client.post(Httppost_Links.FORGOT_PASSWORD, params,
				new AsyncHttpResponseHandler() {

					@Override
					public void onSuccess(String response) {
						Data.loading_box_stop();
						JSONObject res;
						try {
							res = new JSONObject(response);
							Log.d("response", "response" + res.toString(2));
							if (res.has("error")) {
								Log.d("Data.SERVER_ERROR_MSG",
										"Data.SERVER_ERROR_MSG"
												+ res.getString("error"));
								new DialogPopup().alertPopup(
										ForgotPassword.this, "OOPS!",
										"" + res.getString("error"));

							} else {
								new DialogPopup()
										.alertPopup(
												ForgotPassword.this,
												"OOPS!",
												" Email has been sent successfully. Please check your mailbox to reset password.");

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
						new DialogPopup().alertPopup(ForgotPassword.this, "",
								"Server not responding\nTry again.");
					}
				});

	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		Intent intent = new Intent(ForgotPassword.this, LoginScreen.class);
		startActivity(intent);
		overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
		finish();

	}

	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		Utils.buildAlertMessageNoGps(ForgotPassword.this);
		super.onWindowFocusChanged(hasFocus);
	}

	@Override
	protected void onStart() {
		super.onStart();
		EasyTracker.getInstance(ForgotPassword.this).activityStart(this);
	}

	@Override
	protected void onStop() {
		super.onStop();
		EasyTracker.getInstance(ForgotPassword.this).activityStop(this);
	}
}
