package com.bitekite.driver;
import com.bitekite.R;
import org.json.JSONObject;

import rmn.androidscreenlibrary.ASSL;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bitekite.DriverLocationFinder;
import com.bitekite.GCMIntentService;
import com.bitekite.MainActivity;
import com.bitekite.utils.Data;
import com.bitekite.utils.DialogPopup;
import com.bitekite.utils.InternetCheck;
import com.google.analytics.tracking.android.EasyTracker;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class ChangePassword extends Activity implements PushUpdater {

	Button buttonSave;
	TextView textViewCancel, textViewEdit,textViewCurrentPassword,textViewNewPassword,textViewConfirmPassword;
	EditText editTextCurrentPassword, editTextNewPassword,
			editTextconfirmPassword;
	ProgressBar progressBar;

	String stringOldPassword, stringNewPassword, stringConfirmPassword;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_change_password);
		new ASSL(this, (ViewGroup) findViewById(R.id.root), 1134, 720, false);

		textViewEdit = (TextView) findViewById(R.id.EDIT);
		textViewCancel = (TextView) findViewById(R.id.CancelTxt);
		textViewCurrentPassword = (TextView) findViewById(R.id.currentPasswordTxt);
		textViewNewPassword = (TextView) findViewById(R.id.NewPasswordTxt);
		textViewConfirmPassword = (TextView) findViewById(R.id.ConfirmPasswordTxt);
		buttonSave = (Button) findViewById(R.id.SaveBtn);
		editTextconfirmPassword = (EditText) findViewById(R.id.ConfirmPasswordEdt);
		editTextCurrentPassword = (EditText) findViewById(R.id.currentPasswordEdt);
		editTextNewPassword = (EditText) findViewById(R.id.NewPasswordEdt);
		progressBar = (ProgressBar) findViewById(R.id.progressBar1);
		
		
		textViewEdit.setTypeface(Data.bariol_regular(getApplicationContext()));
		textViewCancel.setTypeface(Data.bariol_regular(getApplicationContext()));
		textViewCurrentPassword.setTypeface(Data.bariol_bold(getApplicationContext()));
		textViewNewPassword.setTypeface(Data.bariol_bold(getApplicationContext()));
		textViewConfirmPassword.setTypeface(Data.bariol_bold(getApplicationContext()));
		buttonSave.setTypeface(Data.bariol_regular(getApplicationContext()));
		editTextconfirmPassword.setTypeface(Data.bariol_regular(getApplicationContext()));
		editTextCurrentPassword.setTypeface(Data.bariol_regular(getApplicationContext()));
		editTextNewPassword.setTypeface(Data.bariol_regular(getApplicationContext()));
		
		

		textViewEdit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (textViewEdit.getText().toString().equals("Edit")) {
					textViewEdit.setVisibility(View.INVISIBLE);
					editTextconfirmPassword.setEnabled(true);
					editTextCurrentPassword.setEnabled(true);
					editTextNewPassword.setEnabled(true);
					textViewCancel.setVisibility(View.VISIBLE);

				} else {

				}
			}
		});

		textViewCancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				textViewEdit.setVisibility(View.VISIBLE);
				editTextconfirmPassword.setEnabled(false);
				editTextCurrentPassword.setEnabled(false);
				editTextNewPassword.setEnabled(false);
				textViewCancel.setVisibility(View.INVISIBLE);
				Intent intent = new Intent(ChangePassword.this,
						AccountDriver.class);
				startActivity(intent);
				finish();
				overridePendingTransition(R.anim.slide_in_left,
						R.anim.slide_out_right);

			}
		});

		buttonSave.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if (!textViewEdit.isShown()) {
					stringOldPassword = editTextCurrentPassword.getText()
							.toString();
					stringNewPassword = editTextNewPassword.getText()
							.toString();
					stringConfirmPassword = editTextconfirmPassword.getText()
							.toString();

					if (stringOldPassword.isEmpty()) {
						new DialogPopup().alertPopup(ChangePassword.this, "",
								"Please enter current password");
					} else {
						if (stringOldPassword.length() < 6) {
							new DialogPopup()
									.alertPopup(ChangePassword.this, "",
											"Password must be 6 characters long");
						} else {
							if (stringNewPassword.isEmpty()) {
								new DialogPopup().alertPopup(
										ChangePassword.this, "",
										"Please enter new password");
							} else {
								if (stringNewPassword.length() < 6) {
									new DialogPopup()
											.alertPopup(ChangePassword.this,
													"",
													"Password must be 6 characters long");
								} else {
									if (stringNewPassword
											.equals(stringOldPassword)) {
										new DialogPopup()
												.alertPopup(
														ChangePassword.this,
														"",
														"New password cannot be same as old password.");
									} else {
										if (stringConfirmPassword.isEmpty()) {
											new DialogPopup().alertPopup(
													ChangePassword.this, "",
													"Please confirm password");
										} else {
											if (stringNewPassword
													.equals(stringConfirmPassword)) {
												hideSoftKeyboard();
												
												if (InternetCheck.getInstance(getApplicationContext()).isOnline(
														getApplicationContext())) {
													changePasswordServerCall();
												} else {
													new DialogPopup().alertPopup(ChangePassword.this,
															"", "Check your internet connection");
												}
												
			

											} else {
												new DialogPopup().alertPopup(
														ChangePassword.this,
														"",
														"Sorry, the passwords do not match");
											}
										}
									}
								}
							}
						}
					}
				}

			}
		});

	}

	public void changePasswordServerCall() {
		RequestParams params = new RequestParams();
		progressBar.setVisibility(View.VISIBLE);
		params.put("access_token",
				Data.getInfoOf(getApplicationContext(), Data.ACCESS_TOKEN));
		params.put("old_password", stringOldPassword);
		params.put("new_password", stringNewPassword);

		AsyncHttpClient client = new AsyncHttpClient();
		client.setTimeout(Httppost_Links.SERVER_TIME_OUT_TIME);
		client.post(Httppost_Links.CHANGE_PASSWORD, params,
				new AsyncHttpResponseHandler() {
					@Override
					public void onSuccess(String response) {
						Log.e("resp val", "=" + response);
						try {
							JSONObject job = new JSONObject(response);
							progressBar.setVisibility(View.INVISIBLE);
							
							if(job.getString("status").equals("2"))
							{
//								Data.alertMessageBox(SplashScreen.this,popup.getString("title"),popup.getString("text"),Data.getInfoOf(getApplicationContext(), Data.loginFrom));
							Intent intent = new Intent(ChangePassword.this,MainActivity.class);
							startActivity(intent);
							finish();
							}
							else
							{
							if (response.contains("error")) {
								String error = job.getString("error");
								Log.i("Error ", "-----------" + error);
								new DialogPopup()
								.alertPopup(ChangePassword.this, "",
										error);
							} else {
								textViewEdit.setVisibility(View.VISIBLE);
								editTextconfirmPassword.setEnabled(false);
								editTextCurrentPassword.setEnabled(false);
								editTextNewPassword.setEnabled(false);
								textViewCancel.setVisibility(View.INVISIBLE);
								Intent intent = new Intent(ChangePassword.this,
										AccountDriver.class);
								startActivity(intent);
								overridePendingTransition(R.anim.slide_in_left,
										R.anim.slide_out_right);
								finish();
								
							}
							}
						} catch (Exception e) {
							Log.v("Login Data Error", "=" + e.toString());
						}
					}

					@Override
					public void onFailure(Throwable arg0) {
						// TODO Auto-generated method stub
						progressBar.setVisibility(View.INVISIBLE);
						Log.v("Login Server Error", "fail: " + arg0);
						new DialogPopup()
						.alertPopup(ChangePassword.this, "",
								"Server not responding\nTry again.");
					}
				});
	}

	@Override
	public void onBackPressed() {
		Intent intent = new Intent(ChangePassword.this, AccountDriver.class);
		startActivity(intent);
		overridePendingTransition(R.anim.slide_in_left,
				R.anim.slide_out_right);
		finish();
		
	}

	@Override
	public void onResume() {
		super.onResume();
		Data.activity = this;
		OrderScreen.pushUpdater = this;
		if(Data.getInfoOf(getApplicationContext(), "OD").equals("19"))
		{
			pushUpdater();
			
		}
		DriverLocationFinder.cancelNotification(getApplicationContext(), 0);
	}

	@Override
	public void pushUpdater() {
		Log.e("pushUpdater", "=");
		new Thread(new Runnable() {

			@Override
			public void run() {

				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						if(GCMIntentService.flag.equals("15"))
						{
							new DialogPopup().alertPopup(ChangePassword.this, "",
									GCMIntentService.stringMessage);
						}
						else if(Data.getInfoOf(getApplicationContext(), "OD").equals("19"))
						{
							Data.saveInfoOf(Data.activity,
									Data.CHECKINVALUE, "0");
							Intent intent = new Intent(ChangePassword.this,
									OrderScreen.class);
							intent.putExtra("checkout_admin", true);
							startActivity(intent);
							finish();
							overridePendingTransition(R.anim.slide_in_left,
									R.anim.hold);
							stopService(new Intent(Data.activity,
									MyService.class));
						}
						else if(GCMIntentService.flag.equals("25"))
						{
							Data.saveInfoOf(Data.activity,
									Data.CHECKINVALUE, "1");
							new DialogPopup().alertPopup(ChangePassword.this, "",
									GCMIntentService.stringMessage);
						}
						else if(GCMIntentService.flag.equals("16"))
						{
							new DialogPopup().alertPopup(ChangePassword.this, "",
									GCMIntentService.stringMessage);
						}
						Data.saveInfoOf(getApplicationContext(), "OD", "");
					}
				});
			}
		}).start();

	}

	@Override
	protected void onPause() {
		super.onPause();
		Log.d("onPause", "onPause");
		OrderScreen.pushUpdater = null;
		
		
	}
	public void hideSoftKeyboard() {
	    if(getCurrentFocus()!=null) {
	        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
	        inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
	    }
	}
	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		Utils.buildAlertMessageNoGps(ChangePassword.this);
		super.onWindowFocusChanged(hasFocus);
	}
	
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		EasyTracker.getInstance(ChangePassword.this).activityStart(this);
//		super.onStart();
	}
	
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		EasyTracker.getInstance(ChangePassword.this).activityStop(this);
//		super.onStart();
	}
}
