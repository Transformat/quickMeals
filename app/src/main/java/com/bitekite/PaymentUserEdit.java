package com.bitekite;

import java.util.Calendar;

import org.json.JSONException;
import org.json.JSONObject;

import rmn.androidscreenlibrary.ASSL;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bitekite.driver.Httppost_Links;
import com.bitekite.driver.PushUpdater;
import com.bitekite.driver.Utils;
import com.bitekite.utils.Data;
import com.bitekite.utils.DialogPopup;
import com.bitekite.utils.InternetCheck;
import com.creditcard.CreditCardEditText;
import com.google.analytics.tracking.android.EasyTracker;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.stripe.android.Stripe;
import com.stripe.android.TokenCallback;
import com.stripe.android.model.Card;
import com.stripe.android.model.Token;
import com.stripe.exception.AuthenticationException;

@SuppressLint("ResourceAsColor")
public class PaymentUserEdit extends Activity implements PushUpdater {

	Stripe stripe;
	EditText editTextMonth, editTextCVC, editTextZip, editTextPersonal;
	CreditCardEditText editTextLast4;
	int year, day, month;
	String new_month;
	Button buttonSave;
	RelativeLayout relativeLayoutPaymentEdit, relativeLayoutBack;
	TextView textView1, textView2, textViewAccount;

	int FirstTimeComesFlag = 0;

	@SuppressLint("ResourceAsColor")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_payment_user_edit);

		relativeLayoutPaymentEdit = (RelativeLayout) findViewById(R.id.relativeLayoutPaymentEdit);
		new ASSL(this, relativeLayoutPaymentEdit, 1134, 720, false);

		relativeLayoutBack = (RelativeLayout) findViewById(R.id.relativeLayoutBack);

		editTextLast4 = (CreditCardEditText) findViewById(R.id.editText1);
		editTextMonth = (EditText) findViewById(R.id.editTextMonth);
		// editTextYear=(EditText)findViewById(R.id.editTextYear);
		editTextZip = (EditText) findViewById(R.id.editText2);
		editTextCVC = (EditText) findViewById(R.id.editText3);
		editTextPersonal = (EditText) findViewById(R.id.editText4);
		buttonSave = (Button) findViewById(R.id.buttonNext);
		textView1 = (TextView) findViewById(R.id.textView1);
		textView2 = (TextView) findViewById(R.id.textView2);
		textViewAccount = (TextView) findViewById(R.id.textViewCreateAccount);

		buttonSave.setTypeface(Data.bariol_regular(getApplicationContext()));
		editTextLast4.setTypeface(Data.bariol_regular(getApplicationContext()));
		editTextMonth.setTypeface(Data.bariol_regular(getApplicationContext()));
		editTextCVC.setTypeface(Data.bariol_regular(getApplicationContext()));
		editTextZip.setTypeface(Data.bariol_regular(getApplicationContext()));
		editTextPersonal.setTypeface(Data
				.bariol_regular(getApplicationContext()));
		textView1.setTypeface(Data.bariol_regular(getApplicationContext()));
		textView2.setTypeface(Data.bariol_regular(getApplicationContext()));
		buttonSave.setTypeface(Data.bariol_regular(getApplicationContext()));
		textViewAccount.setTypeface(Data.bariol_bold(getApplicationContext()));

		String htmlString = "&#8226;&#8226;&#8226;&#8226; &#8226;&#8226;&#8226;&#8226; &#8226;&#8226;&#8226;&#8226; ";
		editTextLast4.setHint(Html.fromHtml(htmlString) + "1234");
		editTextZip.setEnabled(false);
		editTextCVC.setEnabled(false);
		editTextMonth.setEnabled(false);
		editTextLast4.setEnabled(false);
		editTextPersonal.setEnabled(false);
		editTextLast4.setBackgroundColor(getResources().getColor(
				R.color.transparent));
		editTextMonth.setBackgroundColor(getResources().getColor(
				R.color.transparent));
		editTextCVC.setBackgroundColor(getResources().getColor(
				R.color.transparent));
		editTextZip.setBackgroundColor(getResources().getColor(
				R.color.transparent));
		editTextPersonal.setBackgroundColor(getResources().getColor(
				R.color.transparent));

		// ******************** Edit clicked code ********************
		buttonSave.setText("SAVE");
		editTextZip.setEnabled(true);
		editTextCVC.setEnabled(true);
		editTextMonth.setEnabled(true);
		editTextLast4.setEnabled(true);
		editTextPersonal.setEnabled(true);

		editTextZip.setHintTextColor(R.color.black_pure);
		editTextCVC.setHintTextColor(R.color.grey);
		editTextMonth.setHintTextColor(R.color.black_pure);
		editTextLast4.setHintTextColor(R.color.black_pure);
		editTextPersonal.setHintTextColor(R.color.grey);

		editTextLast4
				.setBackgroundColor(getResources().getColor(R.color.white));
		editTextMonth
				.setBackgroundColor(getResources().getColor(R.color.white));
		editTextCVC.setBackgroundColor(getResources().getColor(R.color.white));
		editTextZip.setBackgroundColor(getResources().getColor(R.color.white));
		editTextPersonal.setBackgroundColor(getResources().getColor(
				R.color.white));

		// ************************ End ************************

		if (Integer.parseInt(Data.getInfoOf(getApplicationContext(),
				Data.CHECK_CARD)) == 0) {
			FirstTimeComesFlag = 1;
		}

		relativeLayoutBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Data.Flag = 1;
				if (Integer.parseInt(Data.getInfoOf(getApplicationContext(),
						Data.CHECK_CARD)) == 0) {

				} else {
					Intent intent = new Intent(PaymentUserEdit.this,
							AddPaymentUser.class);
					startActivity(intent);
				}
				finish();
				overridePendingTransition(R.anim.slide_in_left,
						R.anim.slide_out_right);
				finish();

			}
		});

		editTextMonth.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				showDialog(1);
				return true;
			}
		});

		buttonSave.setOnClickListener(new OnClickListener() {

			@SuppressLint("ResourceAsColor")
			@Override
			public void onClick(View v) {
				
				if (editTextLast4.getText().toString().trim().isEmpty()
						|| editTextCVC.getText().toString().trim().isEmpty()
						|| editTextMonth.getText().toString().trim().isEmpty())// ||
																				// editTextYear.getText().toString().equals(""))
				{
					new DialogPopup().alertPopup(PaymentUserEdit.this, "",
							"Please fill in all fields");
				} else {
					try {
					
							InputMethodManager imm = (InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
				            imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0); // hide
						
						Data.loading_box(PaymentUserEdit.this, "loading...");
					} catch (Exception e) {
						// TODO: handle exception
					}
				
					Card card = new Card(editTextLast4.getText().toString()
							.trim(), Integer.parseInt(new_month.toString()
							.trim()), year, editTextCVC.getText().toString()
							.trim()); // 4242424242424242

					boolean validation = card.validateCard();
					if (!card.validateNumber()) {
						new DialogPopup().alertPopup(PaymentUserEdit.this, "",
								"The card number that you entered is invalid");
						Data.loading_box_stop();
					} else if (!card.validateExpiryDate()) {
						Data.loading_box_stop();
						Log.d("errors",
								"The expiration date that you entered is invalid");
						new DialogPopup()
								.alertPopup(PaymentUserEdit.this, "",
										"The expiration date that you entered is invalid");
					} else if (!card.validateCVC()) {
						Data.loading_box_stop();
						Log.d("errors",
								"The CVC code that you entered is invalid");
						new DialogPopup().alertPopup(PaymentUserEdit.this, "",
								"The CVC code that you entered is invalid");
					} else if (validation) {
						try {
							stripe = new Stripe(Data.PUBLISHABLE_KEY); // default//
																		// ->//
																		// pk_test_6pRNASCoBOKtIshFeQd4XMUh
						} catch (AuthenticationException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						// *********** This method is used to create token which
						// we have to pass to server *********//

						stripe.createToken(card, new TokenCallback() {
							public void onSuccess(Token token) {
								Log.i("Token",
										"" + token.getCard() + ", "
												+ token.getId() + ", "
												+ token.getCreated());
								Data.STRIPE_TOKEN_ID = token.getId();
								Data.saveInfoOf(getApplicationContext(),
										Data.STRIPE_TOKEN_ID, token.getId());

								int currentapiVersion = android.os.Build.VERSION.SDK_INT;
								String device_name = android.os.Build.DEVICE;

								Data.OS_VERSION = "" + currentapiVersion;
								Data.DEVICE_NAME = device_name;

								Log.d("deviceeeee", "deviceeeee----    "
										+ currentapiVersion + ",  "
										+ device_name);

								String sub = editTextLast4.getText().toString()
										.trim();
								sub = sub.substring(sub.length() - 4);
								Log.d("sub", "sub" + sub);
								Data.S_LAST_4 = sub;// editTextLast4.getText().toString().trim();
								if (InternetCheck.getInstance(
										getApplicationContext()).isOnline(
										getApplicationContext())) {
									buttonSave.setEnabled(false);
									
									servercallAddCreditCard();
								} else {
									Data.loading_box_stop();
									new DialogPopup().alertPopup(
											PaymentUserEdit.this, "",
											"Check your internet connection");
								}
//								Data.loading_box_stop();

							}

							public void onError(Exception error) {
								// Show localized error message
								Log.i("error", "" + error.getMessage());
								new DialogPopup().alertPopup(
										PaymentUserEdit.this, "",
										"" + error.getMessage());
								Data.loading_box_stop();
							}
						});

					} else {
						Log.d("errors",
								"The card details that you entered are invalid");
						new DialogPopup()
								.alertPopup(PaymentUserEdit.this, "",
										"The card details that you entered are invalid");
						
					}
				}
			}
		});

	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		Data.Flag = 1;
		if (Integer.parseInt(Data.getInfoOf(getApplicationContext(),
				Data.CHECK_CARD)) == 0) {

		} else {
			Intent intent = new Intent(PaymentUserEdit.this,
					AddPaymentUser.class);
			startActivity(intent);
		}
//		finish();
		overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
		finish();
	}

	void servercallAddCreditCard() {
//		Data.loading_box(this, "loading...");

		RequestParams params = new RequestParams();

		params.put("access_token",
				Data.getInfoOf(getApplicationContext(), Data.ACCESS_TOKEN)); 
																				
		params.put("stripe_token",""
						+ Data.getInfoOf(getApplicationContext(),
						Data.STRIPE_TOKEN_ID));//
		params.put("last_4", Data.S_LAST_4);//
		params.put("email",
				"" + Data.getInfoOf(getApplicationContext(), Data.EMAIL));//
		Log.d("stripe_token",""	+ Data.getInfoOf(getApplicationContext(),
								Data.STRIPE_TOKEN_ID));//
		Log.d("access_token",Data.getInfoOf(getApplicationContext(), Data.ACCESS_TOKEN)); 
		Log.d("last_4", Data.S_LAST_4);//
		Log.d("email", "" + Data.getInfoOf(getApplicationContext(), Data.EMAIL));//
		AsyncHttpClient client = new AsyncHttpClient();

		client.setTimeout(Httppost_Links.SERVER_TIME_OUT_TIME);
		client.post(Httppost_Links.Baselink + "add_credit_card", params,
				new AsyncHttpResponseHandler() {

					@Override
					public void onSuccess(String response) {
						Data.loading_box_stop();
						Log.i("request succesfull", "response = " + response);
						JSONObject res;
						try {
							res = new JSONObject(response.toString());
							if (res.getString("status").equals("2")) {
								Intent intent = new Intent(
										PaymentUserEdit.this,
										MainActivity.class);
								startActivity(intent);
								finish();
							} else {
								if (res.has("error")) {
									Log.d("Data.SERVER_ERROR_MSG",
											"Data.SERVER_ERROR_MSG"
													+ res.getString("error"));
									new DialogPopup().alertPopup(
											PaymentUserEdit.this, "",
											res.getString("error"));

								} else {
									Data.Flag = 1;

									Data.saveInfoOf(getApplicationContext(),
											Data.CHECK_CARD, "1");
									Data.saveInfoOf(getApplicationContext(),
											Data.LAST_4,
											Data.S_LAST_4);

									if (FirstTimeComesFlag == 1) {

									} else {
										Intent intent = new Intent(
												PaymentUserEdit.this,
												AddPaymentUser.class);
										startActivity(intent);

									}
									finish();
									overridePendingTransition(
											R.anim.slide_in_right,
											R.anim.slide_out_left);

								}
								buttonSave.setEnabled(true);
							}
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}

					@Override
					public void onFailure(Throwable arg0) {
						Data.loading_box_stop();
						Log.e("request fail", arg0.toString());
						buttonSave.setEnabled(true);
						new DialogPopup().alertPopup(PaymentUserEdit.this, "",
								"Server not responding\nTry again.");
					}
				});

	}

	private DatePickerDialog.OnDateSetListener dateListener = new DatePickerDialog.OnDateSetListener() {

		private String new_day;

		@Override
		public void onDateSet(DatePicker view, int years, int monthOfYear,
				int dayOfMonth) {
			year = years;
			month = monthOfYear + 1;

			day = dayOfMonth;
			if (month < 10) {
				new_month = "0" + month;
			} else {
				new_month = "" + month;
			}
			if (day < 10) {
				new_day = "0" + day;
			} else {
				new_day = "" + day;
			}
			String final_date = new_month + "/" + new_day + "/" + year;
			Log.i("date", "" + final_date);
			String yr = "" + year;
			year = Integer.parseInt(yr.substring(2));
			editTextMonth.setText(new_month.toString() + "/" + year);
		}
	};

	@SuppressLint("NewApi")
	protected Dialog onCreateDialog(int dialogId) {

		Calendar cal = Calendar.getInstance();

		year = cal.get(Calendar.YEAR);
		month = cal.get(Calendar.MONTH);
		day = cal.get(Calendar.DAY_OF_MONTH);
		final DatePickerDialog datepiker = new DatePickerDialog(this,
				dateListener, year, month, day);

		Log.e("dateDialog new", "==");
		datepiker.setTitle("Select Date:");
		datepiker.setCanceledOnTouchOutside(false);
		datepiker.setCancelable(false);
		datepiker.getDatePicker().setCalendarViewShown(false);
		int day = getApplicationContext().getResources().getIdentifier(
				"android:id/day", null, null);
		if (day != 0) {
			View dayPicker = datepiker.getDatePicker().findViewById(day);
			if (dayPicker != null) {
				dayPicker.setVisibility(View.GONE);
			}
		}

		datepiker.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						if (which == DialogInterface.BUTTON_NEGATIVE) {
							dialog.dismiss();
							dialog.cancel();
						}
					}
				});

		datepiker.setButton(DialogInterface.BUTTON_POSITIVE, "OK",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						int flag = 0;
						if (which == DialogInterface.BUTTON_POSITIVE) {
							DatePicker datePicker = datepiker.getDatePicker();
							dateListener.onDateSet(datePicker,
									datePicker.getYear(),
									datePicker.getMonth(),
									datePicker.getDayOfMonth());
							dialog.dismiss();
							dialog.cancel();
						}
					}
				});
		return datepiker;

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
							Intent intent = new Intent(PaymentUserEdit.this,
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
	public void onWindowFocusChanged(boolean hasFocus) {
		Utils.buildAlertMessageNoGps(PaymentUserEdit.this);
		super.onWindowFocusChanged(hasFocus);
	}

	@Override
	protected void onStart() {
		super.onStart();
		EasyTracker.getInstance(PaymentUserEdit.this).activityStart(this);
	}

	@Override
	protected void onStop() {
		super.onStop();
		EasyTracker.getInstance(PaymentUserEdit.this).activityStop(this);
	}
}
