package com.bitekite;

import rmn.androidscreenlibrary.ASSL;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bitekite.driver.Utils;
import com.bitekite.utils.Data;
import com.bitekite.utils.DialogPopup;
import com.google.analytics.tracking.android.EasyTracker;

public class DriverProfile extends Activity {

	EditText editTextFirstNameDriver, editTextLastNameDriver;
	TextView textViewDriverProfile;
	Button buttonNext, buttonCancel;
	RelativeLayout RelativeLayoutDriverProfile;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_driver_profile);

		RelativeLayoutDriverProfile = (RelativeLayout) findViewById(R.id.RelativeLayoutDriverProfile);
		new ASSL(this, RelativeLayoutDriverProfile, 1134, 720, false);

		editTextFirstNameDriver = (EditText) findViewById(R.id.editTextFirstNameDriver);
		editTextLastNameDriver = (EditText) findViewById(R.id.editTextLastNameDriver);
		textViewDriverProfile = (TextView) findViewById(R.id.textViewDriverProfile);
		buttonNext = (Button) findViewById(R.id.buttonNext);
		buttonCancel = (Button) findViewById(R.id.buttonCancel);

		editTextFirstNameDriver.setTypeface(Data
				.bariol_regular(getApplicationContext()));
		editTextLastNameDriver.setTypeface(Data
				.bariol_regular(getApplicationContext()));
		textViewDriverProfile.setTypeface(Data
				.bariol_bold(getApplicationContext()));
		buttonNext.setTypeface(Data.bariol_regular(getApplicationContext()));
		buttonCancel.setTypeface(Data.bariol_regular(getApplicationContext()));

		buttonCancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(DriverProfile.this,
						DriverAccount.class);
				startActivity(intent);
				overridePendingTransition(R.anim.slide_in_left,
						R.anim.slide_out_right);
				finish();
			}
		});

		editTextFirstNameDriver.addTextChangedListener(new TextWatcher() {

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
				// TODO Auto-generated method stub
				if (!editTextLastNameDriver.getText().toString().equals("")) {
					buttonNext.setVisibility(View.VISIBLE);
				}
			}
		});

		editTextLastNameDriver.addTextChangedListener(new TextWatcher() {

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
				// TODO Auto-generated method stub
				if (!editTextFirstNameDriver.getText().toString().equals("")) {
					buttonNext.setVisibility(View.VISIBLE);
				}
			}
		});

		buttonNext.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				String error;
				if (editTextFirstNameDriver.getText().toString().equals("")) {
					error = "Please enter first name";
					new DialogPopup().alertPopup(DriverProfile.this, "OOPS!",
							"" + error);
					editTextFirstNameDriver.setFocusable(true);
				} else {
					if (editTextLastNameDriver.getText().toString().equals("")) {
						error = "Please enter last name";
						new DialogPopup().alertPopup(DriverProfile.this,
								"OOPS!", "" + error);
						editTextLastNameDriver.setFocusable(true);

					} else {
						String username = editTextFirstNameDriver.getText()
								.toString().trim()
								+ " "
								+ editTextLastNameDriver.getText().toString()
										.trim();
						Data.S_USER_NAME = username.trim();
						Log.d("", "" + Data.S_USER_NAME);

						Intent intent = new Intent(DriverProfile.this,
								CarDetails.class);
						startActivity(intent);
						overridePendingTransition(R.anim.slide_in_right,
								R.anim.slide_out_left);
						finish();
					}
				}
			}
		});
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		Intent intent = new Intent(DriverProfile.this, DriverAccount.class);
		startActivity(intent);
		overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
		finish();
	}

	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		Utils.buildAlertMessageNoGps(DriverProfile.this);
		super.onWindowFocusChanged(hasFocus);
	}

	@Override
	protected void onStart() {
		super.onStart();
		EasyTracker.getInstance(DriverProfile.this).activityStart(this);
	}

	@Override
	protected void onStop() {
		super.onStop();
		EasyTracker.getInstance(DriverProfile.this).activityStop(this);
	}
}
