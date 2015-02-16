package com.bitekite;

import org.json.JSONException;
import org.json.JSONObject;

import rmn.androidscreenlibrary.ASSL;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bitekite.driver.Httppost_Links;
import com.bitekite.driver.PushUpdater;
import com.bitekite.driver.Utils;
import com.bitekite.utils.Data;
import com.bitekite.utils.DialogPopup;
import com.bitekite.utils.InternetCheck;
import com.google.analytics.tracking.android.EasyTracker;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class ChangePasswordCustomer extends Activity implements PushUpdater{

	TextView textViewCurrentPassword,textViewNewPassword,textViewConfirmPassword,textViewHeader;
	EditText editTextCurrentPassword,editTextNewPassword,editTextConfirmPassword;
	RelativeLayout relativeLayout;
	Button buttonSave,buttonCancel;
	ImageView imageViewLoader;
//	ProgressBar progressBar;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_change_password_customer);
		
		relativeLayout = (RelativeLayout) findViewById(R.id.RelativeLayout1);
		new ASSL(this, relativeLayout, 1134, 720, false);
		
		buttonSave=(Button)findViewById(R.id.button1);
		buttonCancel=(Button)findViewById(R.id.buttonCancel);
		textViewCurrentPassword=(TextView)findViewById(R.id.textView1);
		textViewNewPassword=(TextView)findViewById(R.id.textView2);
		textViewConfirmPassword=(TextView)findViewById(R.id.textView3);
		textViewHeader = (TextView) findViewById(R.id.textViewCreateProfile);
		
		editTextCurrentPassword=(EditText)findViewById(R.id.editText1);
		editTextNewPassword=(EditText)findViewById(R.id.editText2);
		editTextConfirmPassword=(EditText)findViewById(R.id.editText3);
//		progressBar= (ProgressBar) findViewById(R.id.progressBar1);
		imageViewLoader=(ImageView)findViewById(R.id.imageViewLoader);
		
		textViewCurrentPassword.setTypeface(Data.bariol_regular(getApplicationContext()));
		textViewNewPassword.setTypeface(Data.bariol_regular(getApplicationContext()));
		textViewConfirmPassword.setTypeface(Data.bariol_regular(getApplicationContext()));
		textViewHeader.setTypeface(Data.bariol_bold(getApplicationContext()));
		editTextCurrentPassword.setTypeface(Data.bariol_regular(getApplicationContext()));
		editTextNewPassword.setTypeface(Data.bariol_regular(getApplicationContext()));
		editTextConfirmPassword.setTypeface(Data.bariol_regular(getApplicationContext()));
		buttonSave.setTypeface(Data.bariol_regular(getApplicationContext()));
		buttonCancel.setTypeface(Data.bariol_regular(getApplicationContext()));
		
		buttonCancel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(ChangePasswordCustomer.this,AccountUser.class);
				startActivity(intent);
				finish();
				overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
			}
		});
		
		buttonSave.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				if(editTextCurrentPassword.getText().toString().isEmpty())
				{
					new DialogPopup()
					.alertPopup(ChangePasswordCustomer.this, "",
							"Please enter current password");
				}
				else
				{
					if(editTextNewPassword.getText().toString().isEmpty())
					{
						new DialogPopup()
						.alertPopup(ChangePasswordCustomer.this, "",
								"Please enter new password");
					}
					else
					{
						if(editTextNewPassword.getText().toString().length() < 6) {
							new DialogPopup()
							.alertPopup(ChangePasswordCustomer.this, "",
									"Password must be 6 characters long");
						}
						else
						{
						
						if(editTextConfirmPassword.getText().toString().isEmpty())
						{
							new DialogPopup()
							.alertPopup(ChangePasswordCustomer.this, "",
									"Please confirm password");
						}
						else
						{
							if(editTextConfirmPassword.getText().toString().length() < 6) {
								new DialogPopup()
								.alertPopup(ChangePasswordCustomer.this, "",
										"Password must be 6 characters long");
							}
							else
							{
							
							if(editTextNewPassword.getText().toString().equals(editTextConfirmPassword.getText().toString()))
							{
								if (InternetCheck.getInstance(getApplicationContext()).isOnline(
			 							getApplicationContext())) {
									serverCallChangePassword();
			 					} else {
			 						new DialogPopup().alertPopup(ChangePasswordCustomer.this,
			 								"", "Check your internet connection");
			 					}
								
							}
							else
							{
								new DialogPopup()
								.alertPopup(ChangePasswordCustomer.this, "",
										"Sorry, the passwords do not match");

							}
							}
						}
						}
					}
				}
				
			}
		});
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		Intent intent = new Intent(ChangePasswordCustomer.this,AccountUser.class);
		startActivity(intent);
		finish();
		overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.change_password_customer, menu);
		return true;
	}
	
	void serverCallChangePassword() {
		RequestParams params = new RequestParams();
//		progressBar.setVisibility(View.VISIBLE);
		imageViewLoader.setVisibility(View.VISIBLE);
		params.put("access_token", ""+Data.getAccessToken(getApplicationContext()));// +Data.ACCESS_TOKEN);
		params.put("old_password", editTextCurrentPassword.getText().toString().trim());
		params.put("new_password", editTextNewPassword.getText().toString().trim());
		AsyncHttpClient client = new AsyncHttpClient();
 
		client.setTimeout(Httppost_Links.SERVER_TIME_OUT_TIME);
		client.post(Httppost_Links.Baselink + "change_password", params,
				new AsyncHttpResponseHandler() {

					@Override
					public void onSuccess(String response) {
						// Log.i("request succesfull", "response = " +
						// response);
						// fillServerData(response);
//						progressBar.setVisibility(View.INVISIBLE);
						imageViewLoader.setVisibility(View.INVISIBLE);
						JSONObject res;
						try {
							res = new JSONObject(response);
							
							if(res.getString("status").equals("2"))
							{
//								Data.alertMessageBox(SplashScreen.this,popup.getString("title"),popup.getString("text"),Data.getInfoOf(getApplicationContext(), Data.loginFrom));
							Intent intent = new Intent(ChangePasswordCustomer.this,MainActivity.class);
							startActivity(intent);
							finish();
							}
							else
							{
							Log.i("Response", "Response" + res.toString(2));
							
							if (res.has("error")) {
								String error = res.getString("error");
								Log.i("Error ", "-----------" + error);
								new DialogPopup()
								.alertPopup(ChangePasswordCustomer.this, "",
										error);
							} else {
							Intent intent = new Intent(ChangePasswordCustomer.this,AccountUser.class);
							startActivity(intent);
							overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
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
//						progressBar.setVisibility(View.INVISIBLE);
						imageViewLoader.setVisibility(View.INVISIBLE);
						new DialogPopup()
						.alertPopup(ChangePasswordCustomer.this, "",
								"Server not responding\nTry again.");
					}
				});
	}
	protected void onResume() {
		ListofMeals.pushUpdater = this;
		super.onResume();
		
	}
	
	
	
	@Override
	public void pushUpdater() {
		new Thread(new Runnable() {

			@Override
			public void run() {

				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						if (GCMIntentService.flag.equals("41")) {
						Intent intent = new Intent(ChangePasswordCustomer.this,DriverLocationFinder.class);
						startActivity(intent); 
						overridePendingTransition(R.anim.slide_in_right,
								R.anim.hold);
						}
					}
				});
			}
		}).start();		
	}
	protected void onPause() {
		super.onPause();
		Log.d("onPause", "onPause");
		ListofMeals.pushUpdater = null;
	}
	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		Utils.buildAlertMessageNoGps(ChangePasswordCustomer.this);
		super.onWindowFocusChanged(hasFocus);
	}
	
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		EasyTracker.getInstance(ChangePasswordCustomer.this).activityStart(this);
//		super.onStart();
	}
	
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		EasyTracker.getInstance(ChangePasswordCustomer.this).activityStop(this);
//		super.onStart();
	}
}
