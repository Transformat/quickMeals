package com.bitekite;

import rmn.androidscreenlibrary.ASSL;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bitekite.driver.Utils;
import com.bitekite.utils.Data;
import com.google.analytics.tracking.android.EasyTracker;

public class MainActivity extends Activity {

	private RelativeLayout relativeLayoutSignIn, relativeLayoutSignUp;
	LinearLayout LinearLayoutMain;
	TextView textViewsignIn, textViewregister;// ,textViewPolicy,textView11,textView12,textViewTerms;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		LinearLayoutMain = (LinearLayout) findViewById(R.id.LinearLayoutMain);
		new ASSL(this, LinearLayoutMain, 1134, 720, false);

		relativeLayoutSignIn = (RelativeLayout) findViewById(R.id.SignInLayout);
		relativeLayoutSignUp = (RelativeLayout) findViewById(R.id.signUpLayout);
		textViewsignIn = (TextView) findViewById(R.id.signInTxt);
		textViewregister = (TextView) findViewById(R.id.signUpTxt);

		textViewsignIn.setTypeface(Data.bariol_bold(getApplicationContext()));
		textViewregister.setTypeface(Data.bariol_bold(getApplicationContext()));

		relativeLayoutSignIn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent intent = new Intent(MainActivity.this, LoginScreen.class);
				startActivity(intent);
				overridePendingTransition(R.anim.slide_in_right,
						R.anim.slide_out_left);
				finish();
			}
		});

		relativeLayoutSignUp.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this,
						CreateAccount.class);
				startActivity(intent);
				overridePendingTransition(R.anim.slide_in_right,
						R.anim.slide_out_left);
				finish();
			}
		});

	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
		finish();
	}

	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		Utils.buildAlertMessageNoGps(MainActivity.this);
		super.onWindowFocusChanged(hasFocus);
	}

	@Override
	protected void onStart() {
		super.onStart();
		EasyTracker.getInstance(MainActivity.this).activityStart(this);
	}

	@Override
	protected void onStop() {
		super.onStop();
		EasyTracker.getInstance(MainActivity.this).activityStop(this);
	}
}
