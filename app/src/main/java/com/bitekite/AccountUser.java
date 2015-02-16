package com.bitekite;

import java.io.File;
import java.io.FileNotFoundException;

import org.json.JSONException;
import org.json.JSONObject;

import rmn.androidscreenlibrary.ASSL;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.InputFilter;
import android.text.Spanned;
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
import com.kbeanie.imagechooser.api.ChooserType;
import com.kbeanie.imagechooser.api.ChosenImage;
import com.kbeanie.imagechooser.api.ImageChooserListener;
import com.kbeanie.imagechooser.api.ImageChooserManager;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.squareup.picasso.Picasso;

public class AccountUser extends Activity implements PushUpdater,
		ImageChooserListener {

	EditText editTextEmail, editTextPhone, editTextUsername;
	TextView textViewAccount, textViewAccountHeder, textViewEmail,
			textViewMobile, textViewSUCCESS;
	RelativeLayout relativeLayoutAccountUser, relativeLayoutBack,
			relativeLayoutUsername, relativeLayoutSuccess,
			relativeLayoutProgress;
	Button buttonNext, buttonChangePassword;
	ImageView imageView, imageViewLoader;
	private int takePicture = 1;
	private Uri imageUri;
	String picturePathString;
	File file;
	int user_pic = 0;
	Dialog popup;
	Button buttonCamera, buttonGallery, buttonOK;
	TextView textViewTitle;
	ImageChooserManager imageChooserManager, imageChooserManager2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_account_user);

		relativeLayoutAccountUser = (RelativeLayout) findViewById(R.id.relativeLayoutAccountUser);
		new ASSL(this, relativeLayoutAccountUser, 1134, 720, false);

		editTextEmail = (EditText) findViewById(R.id.editText1);
		editTextPhone = (EditText) findViewById(R.id.editText2);
		buttonNext = (Button) findViewById(R.id.buttonNext);
		textViewAccount = (TextView) findViewById(R.id.textView1);
		textViewEmail = (TextView) findViewById(R.id.textView2);
		textViewMobile = (TextView) findViewById(R.id.textView3);
		textViewSUCCESS = (TextView) findViewById(R.id.SUCCESStxt);
		textViewAccountHeder = (TextView) findViewById(R.id.textViewCreateAccount);
		editTextUsername = (EditText) findViewById(R.id.textViewUserName);
		buttonChangePassword = (Button) findViewById(R.id.button1);
		imageView = (ImageView) findViewById(R.id.imageView2);
		relativeLayoutBack = (RelativeLayout) findViewById(R.id.relativeLayoutBack);
		relativeLayoutUsername = (RelativeLayout) findViewById(R.id.RelativeLayout1);
		relativeLayoutSuccess = (RelativeLayout) findViewById(R.id.relativeLayout3);
		relativeLayoutProgress = (RelativeLayout) findViewById(R.id.relativeLayout4);
		imageViewLoader = (ImageView) findViewById(R.id.imageViewLoader);

		imageView.setEnabled(false);
		editTextUsername.setEnabled(false);
		editTextPhone.setEnabled(false);
		editTextEmail.setEnabled(false);
		relativeLayoutSuccess.setVisibility(View.INVISIBLE);
		editTextPhone.setBackgroundColor(Color.WHITE);
		relativeLayoutUsername.setBackgroundColor(Color.WHITE);
		textViewMobile.setBackgroundColor(Color.WHITE);
		buttonChangePassword.setVisibility(View.VISIBLE);

		editTextEmail.setTypeface(Data.bariol_regular(getApplicationContext()));
		editTextPhone.setTypeface(Data.bariol_regular(getApplicationContext()));
		buttonNext.setTypeface(Data.bariol_regular(getApplicationContext()));
		textViewAccount.setTypeface(Data.bariol_bold(getApplicationContext()));
		textViewEmail.setTypeface(Data.bariol_regular(getApplicationContext()));
		textViewMobile
				.setTypeface(Data.bariol_regular(getApplicationContext()));
		textViewSUCCESS.setTypeface(Data.bariol_bold(getApplicationContext()));
		textViewAccountHeder.setTypeface(Data
				.bariol_bold(getApplicationContext()));
		editTextUsername.setTypeface(Data
				.bariol_regular(getApplicationContext()));
		buttonChangePassword.setTypeface(Data
				.bariol_regular(getApplicationContext()));

		editTextUsername.setText(""
				+ Data.getInfoOf(getApplicationContext(), Data.USER_NAME)
						.toUpperCase());
		editTextEmail.setText(""
				+ Data.getInfoOf(getApplicationContext(), Data.EMAIL));
		editTextPhone.setText(""
				+ Data.getInfoOf(getApplicationContext(), Data.PH_NO));

		editTextPhone.setText(""
				+ Data.getInfoOf(getApplicationContext(), Data.PH_NO));

		imageChooserManager = new ImageChooserManager(AccountUser.this,
				ChooserType.REQUEST_PICK_PICTURE);
		imageChooserManager.setImageChooserListener(this);

		imageChooserManager2 = new ImageChooserManager(AccountUser.this,
				ChooserType.REQUEST_CAPTURE_PICTURE);
		imageChooserManager2.setImageChooserListener(this);

		TraingleTransform t = new TraingleTransform(getApplicationContext());
		t.context = AccountUser.this;

		Picasso.with(AccountUser.this)
				.load(""
						+ Data.getInfoOf(getApplicationContext(),
								Data.USER_IMAGE_URL)).resize(400, 400)
				.centerCrop().skipMemoryCache().into(imageView);

		imageView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				try {
					imageChooserManager.choose();
				} catch (Exception e) {
					e.printStackTrace();
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

		buttonNext.setOnClickListener(new OnClickListener() {
// phone number limit increased.
			@Override
			public void onClick(View v) {
				if (buttonNext.getText().toString().equals("EDIT")) {
					buttonNext.setText("SAVE");
					editTextUsername.setEnabled(true);
					editTextPhone.setEnabled(true);
					imageView.setEnabled(true);
					editTextPhone.setBackgroundColor(Color
							.parseColor("#F9F9F9"));
					relativeLayoutUsername.setBackgroundColor(Color
							.parseColor("#F9F9F9"));
					textViewMobile.setBackgroundColor(Color
							.parseColor("#F9F9F9"));
					buttonChangePassword.setVisibility(View.GONE);
					relativeLayoutSuccess.setVisibility(View.INVISIBLE);
				} else {
					if (InternetCheck.getInstance(getApplicationContext())
							.isOnline(getApplicationContext())) {
						if (editTextUsername.getText().toString().trim()
								.isEmpty()) {
							new DialogPopup().alertPopup(AccountUser.this, "",
									"Enter Username");
						} else {
							if (editTextPhone.getText().toString().trim()
									.isEmpty()) {
								new DialogPopup().alertPopup(AccountUser.this,
										"", "Enter number");
							} else {
								if (editTextPhone.getText().toString().trim()
										.length() < 14) {
									new DialogPopup().alertPopup(
											AccountUser.this, "",
											"Phone number less than 10 digits");
								} else {
									servercallEditProfile();
								}

							}
						}
					} else {
						new DialogPopup().alertPopup(AccountUser.this, "",
								"Check your internet connection");
					}

				}
			}
		});

		relativeLayoutBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
				overridePendingTransition(R.anim.hold, R.anim.slide_out_right);

			}
		});

		buttonChangePassword.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				Intent intent = new Intent(AccountUser.this,
						ChangePasswordCustomer.class);
				startActivity(intent);
				overridePendingTransition(R.anim.slide_in_right,
						R.anim.slide_out_left);
				finish();

			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.account_user, menu);
		return true;
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		finish();
		overridePendingTransition(R.anim.hold, R.anim.slide_out_right);
	}

	void servercallEditProfile() {
		imageViewLoader.setVisibility(View.VISIBLE);
		RequestParams params = new RequestParams();

		params.put("access_token",
				Data.getInfoOf(getApplicationContext(), Data.ACCESS_TOKEN)); 
		params.put("username", ""
				+ editTextUsername.getText().toString().trim());//
		params.put("phone_no", "" + editTextPhone.getText().toString().trim());//

		if (user_pic == 1) {
			try {
				params.put("user_image", file);
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}
		}
		params.put("user_pic", "" + user_pic);//
		AsyncHttpClient client = new AsyncHttpClient();
		client.setTimeout(Httppost_Links.SERVER_TIME_OUT_TIME);
		client.post(Httppost_Links.Baselink + "edit_profile", params,
				new AsyncHttpResponseHandler() {

					@Override
					public void onSuccess(String response) {
						Log.i("request succesfull", "response = " + response);
						JSONObject res;
						imageViewLoader.setVisibility(View.INVISIBLE);
						try {
							res = new JSONObject(response.toString());
							if (res.getString("status").equals("2")) {
								Intent intent = new Intent(AccountUser.this,
										MainActivity.class);
								startActivity(intent);
								finish();
							} else {
								if (res.has("error")) {
									Log.d("Data.SERVER_ERROR_MSG",
											"Data.SERVER_ERROR_MSG"
													+ res.getString("error"));
									new DialogPopup().alertPopup(
											AccountUser.this, "",
											res.getString("error"));

								} else {
									if (user_pic == 1) {
										Data.saveInfoOf(
												getApplicationContext(),
												Data.USER_IMAGE_URL,
												res.getString("user_pic"));
									}
									user_pic = 0;

									Data.saveInfoOf(getApplicationContext(),
											Data.USER_NAME,
											res.getString("new_username"));
									Data.saveInfoOf(getApplicationContext(),
											Data.PH_NO,
											res.getString("phone_no"));
									relativeLayoutSuccess
											.setVisibility(View.VISIBLE);
									editTextPhone
											.setBackgroundColor(Color.WHITE);
									relativeLayoutUsername
											.setBackgroundColor(Color.WHITE);
									textViewMobile
											.setBackgroundColor(Color.WHITE);
									buttonChangePassword
											.setVisibility(View.VISIBLE);
									buttonNext.setText("EDIT");
									imageView.setEnabled(false);
									editTextUsername.setEnabled(false);
									editTextPhone.setEnabled(false);
									editTextEmail.setEnabled(false);
								}
							}
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}

					@Override
					public void onFailure(Throwable arg0) {
						Log.e("request fail", arg0.toString());
						new DialogPopup().alertPopup(AccountUser.this, "",
								"Server not responding\nTry again.");
						imageViewLoader.setVisibility(View.INVISIBLE);

					}
				});

	}

	private String getRealPathFromURI(Uri contentURI) {
		String result;
		Cursor cursor = getContentResolver().query(contentURI, null, null,
				null, null);
		if (cursor == null) { // Source is Dropbox or other similar local file
								// path
			result = contentURI.getPath();
		} else {
			cursor.moveToFirst();
			int idx = cursor
					.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
			result = cursor.getString(idx);
			cursor.close();
		}
		return result;
	}

	protected void onResume() {
		ListofMeals.pushUpdater = this;
		super.onResume();

	}

	protected void onPause() {
		super.onPause();
		Log.d("onPause", "onPause");
		ListofMeals.pushUpdater = null;
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
							Intent intent = new Intent(AccountUser.this,
									DriverLocationFinder.class);
							startActivity(intent);
							overridePendingTransition(R.anim.slide_in_right,
									R.anim.hold);
						}
					}
				});
			}
		}).start();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK
				&& (requestCode == ChooserType.REQUEST_PICK_PICTURE)) {
			imageChooserManager.submit(requestCode, data);
		} else if (resultCode == RESULT_OK
				&& requestCode == ChooserType.REQUEST_CAPTURE_PICTURE) {
			imageChooserManager2.submit(requestCode, data);
		}
	}

	@Override
	public void onImageChosen(final ChosenImage image) {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				if (image != null) {
					// Use the image
					picturePathString = image.getFilePathOriginal();
					Log.d("---------Image Path----------",
							"---------Image Path----------" + picturePathString);
					if (picturePathString.equalsIgnoreCase("null")) {

					} else {

						try {

							file = new File(picturePathString);
							Log.d("file", "file" + file);
							Picasso.with(AccountUser.this).load(file)
									.resize(300, 300).centerCrop()
									.skipMemoryCache().into(imageView);
							user_pic = 1;

						} catch (Exception e) {
							// .show();
							Log.e("Camera", e.toString());
						}
					}
				}
			}
		});
	}

	@Override
	public void onError(final String reason) {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				// Show error message
				Log.d("---------Image Path 2----------",
						"---------Image Path 2 ----------" + picturePathString);
			}
		});
	}

	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		Utils.buildAlertMessageNoGps(AccountUser.this);
		super.onWindowFocusChanged(hasFocus);
	}

	@Override
	protected void onStart() {
		super.onStart();
		EasyTracker.getInstance(AccountUser.this).activityStart(this);
	}

	@Override
	protected void onStop() {
		super.onStop();
		EasyTracker.getInstance(AccountUser.this).activityStop(this);
	}

}
