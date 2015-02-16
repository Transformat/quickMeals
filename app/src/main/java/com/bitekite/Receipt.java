package com.bitekite;

import org.json.JSONObject;

import rmn.androidscreenlibrary.ASSL;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bitekite.driver.Httppost_Links;
import com.bitekite.driver.Utils;
import com.bitekite.utils.Data;
import com.bitekite.utils.DialogPopup;
import com.bitekite.utils.InternetCheck;
import com.google.analytics.tracking.android.EasyTracker;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class Receipt extends Activity {

	RelativeLayout relativeLayoutMainView, relativeLayoutRatingView,
			relativeLayoutBackBtn;
	LinearLayout linearLayoutQustionView;
	ScrollView scrollView;

	TextView textViewDate, textViewPrice, textViewQustion1, textViewQustion2,
			textViewQustion3, textViewQustion4, textViewQustion5,
			textViewQustion6, textViewTopbar, textViewMelas,
			textViewOrderSummery, textViewRate, textViewWhat, textViewDiv;
	RatingBar ratingBar;

	Boolean qustion1 = false, qustion2 = false, qustion3 = false,
			qustion4 = false, qustion5 = false, qustion6 = false,
			driver = false, food = false;
	Button buttonSubmit, buttonSub;
	String value;
	EditText editTextComment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_receipt);
		new ASSL(this, (ViewGroup) findViewById(R.id.root), 1134, 720, false);
		relativeLayoutBackBtn = (RelativeLayout) findViewById(R.id.relativeLayoutBack);
		relativeLayoutMainView = (RelativeLayout) findViewById(R.id.FirstView);
		relativeLayoutRatingView = (RelativeLayout) findViewById(R.id.RatingView);
		scrollView = (ScrollView) findViewById(R.id.scrollView1);

		textViewDate = (TextView) findViewById(R.id.DateAndTimeTxt);
		textViewPrice = (TextView) findViewById(R.id.AmountTxt);
		textViewTopbar = (TextView) findViewById(R.id.topbarTXT);
		textViewMelas = (TextView) findViewById(R.id.meals);
		textViewDiv = (TextView) findViewById(R.id.textView2);

		editTextComment = (EditText) findViewById(R.id.editText1);

		ratingBar = (RatingBar) findViewById(R.id.ratingBar1);
		textViewQustion1 = (TextView) findViewById(R.id.Qus1Txt);
		textViewQustion2 = (TextView) findViewById(R.id.Qus2Txt);
		textViewQustion3 = (TextView) findViewById(R.id.Qus3Txt);
		textViewQustion4 = (TextView) findViewById(R.id.Qus4Txt);
		textViewQustion5 = (TextView) findViewById(R.id.Qus5Txt);
		textViewQustion6 = (TextView) findViewById(R.id.Qus6Txt);
		textViewOrderSummery = (TextView) findViewById(R.id.OrderSimmaryTxt);
		textViewRate = (TextView) findViewById(R.id.RATE_YOUR_TXT);
		textViewWhat = (TextView) findViewById(R.id.What_DID);
		editTextComment = (EditText) findViewById(R.id.editText1);
		buttonSubmit = (Button) findViewById(R.id.button1);
		buttonSub = (Button) findViewById(R.id.Sub);
		relativeLayoutBackBtn.setVisibility(View.INVISIBLE);

		Data.saveInfoOf(getApplicationContext(), "placeOrderId", "");
		textViewMelas.setText(""
				+ Data.getInfoOf(getApplicationContext(), "Rating_meals"));
		textViewDate.setText(""
				+ Data.utcToLocal(Data.getInfoOf(getApplicationContext(),
						"Rating_dateTime")));
		Double rate = Double.parseDouble(Data.getInfoOf(
				getApplicationContext(), "Rating_price"));
		String prizes4 = rate.toString();
		String str4[] = prizes4.split("\\.");
		if (str4.length == 1) {
			textViewPrice.setText(Html.fromHtml("  $" + rate + ".<sup><small>"
					+ "00" + "</small></sup>"));
		} else {
			textViewPrice.setText(Html.fromHtml("  $" + str4[0]
					+ ".<sup><small>" + str4[1] + "</small></sup>"));
		}
		textViewDate.setTypeface(Data.bariol_bold(getApplicationContext()));
		textViewPrice.setTypeface(Data.bariol_bold(getApplicationContext()));
		textViewTopbar.setTypeface(Data.bariol_bold(getApplicationContext()));
		textViewMelas.setTypeface(Data.bariol_bold(getApplicationContext()));
		buttonSubmit.setTypeface(Data.bariol_regular(getApplicationContext()));
		textViewQustion1.setTypeface(Data.bariol_bold(getApplicationContext()));
		textViewQustion2.setTypeface(Data.bariol_bold(getApplicationContext()));
		textViewQustion3.setTypeface(Data.bariol_bold(getApplicationContext()));
		textViewQustion4.setTypeface(Data.bariol_bold(getApplicationContext()));
		textViewQustion5.setTypeface(Data.bariol_bold(getApplicationContext()));
		textViewQustion6.setTypeface(Data.bariol_bold(getApplicationContext()));
		textViewOrderSummery.setTypeface(Data
				.bariol_regular(getApplicationContext()));
		textViewRate.setTypeface(Data.bariol_regular(getApplicationContext()));
		textViewWhat.setTypeface(Data.bariol_bold(getApplicationContext()));

		buttonSub.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Log.e("Rating Bar Value", "===" + ratingBar.getProgress());
				if (ratingBar.getProgress() == 0) {
					new DialogPopup().alertPopup(Receipt.this, "",
							"Please rate the driver");
				} else if (ratingBar.getProgress() < 4) {

					ratingBar.setEnabled(false);

					textViewTopbar.setText("THANKS FOR ORDERING BITE KITE");
					relativeLayoutBackBtn.setVisibility(View.VISIBLE);
					textViewDiv.setVisibility(View.INVISIBLE);
					buttonSub.setVisibility(View.INVISIBLE);
					LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
							LayoutParams.MATCH_PARENT, (int) (215 * ASSL
									.Yscale()));
					relativeLayoutRatingView.setLayoutParams(lp);
					scrollView.smoothScrollBy(0, (int) (751 * ASSL.Yscale()));
				} else {

					value = "" + ratingBar.getProgress() + ","
							+ ratingBar.getProgress() + ","
							+ ratingBar.getProgress() + ","
							+ ratingBar.getProgress() + ","
							+ ratingBar.getProgress() + ","
							+ ratingBar.getProgress();
					if (InternetCheck.getInstance(getApplicationContext())
							.isOnline(getApplicationContext())) {
						driverRatingServerCall();
					} else {
						new DialogPopup().alertPopup(Receipt.this, "",
								"Check your internet connection");
					}
				}
			}
		});

		buttonSubmit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if (qustion1 || qustion6) {
					driver = true;
				}
				if (qustion2 || qustion3 || qustion4 || qustion5) {
					food = true;
				}
				if(!driver && !food)
				{
					new DialogPopup().alertPopup(Receipt.this, "",
							"Please select the option");
				}
				else{
					
				
				 if (driver && food) {
					value = "" + ratingBar.getProgress() + ","
							+ ratingBar.getProgress() + ","
							+ ratingBar.getProgress() + ","
							+ ratingBar.getProgress() + ","
							+ ratingBar.getProgress() + ","
							+ ratingBar.getProgress();
				} else if (driver) {
					value = "" + ratingBar.getProgress() + "," + 4 + "," + 4
							+ "," + 4 + "," + 4 + "," + ratingBar.getProgress();
				} else if (food) {
					value = "" + "," + 4 + "," + ratingBar.getProgress() + ","
							+ ratingBar.getProgress() + ","
							+ ratingBar.getProgress() + ","
							+ ratingBar.getProgress() + "," + 4;
				} else {
					value = "" + 4 + "," + 4 + "," + 4 + "," + 4 + "," + 4
							+ "," + 4;
				}
				
				
				
				if (InternetCheck.getInstance(getApplicationContext())
						.isOnline(getApplicationContext())) {
					driverRatingServerCall();
				} else {
					new DialogPopup().alertPopup(Receipt.this, "",
							"Check your internet connection");
				}
				}
			}
		});

		ratingBar.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {

			@Override
			public void onRatingChanged(RatingBar ratingBar, float rating,
					boolean fromUser) {

				Log.e("ratingBar.getProgress()", "==" + ratingBar.getProgress()
						+ "===" + ratingBar.getRating());
			}
		});

		textViewQustion1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (!qustion1) {
					qustion1 = true;
					textViewQustion1
							.setBackgroundResource(R.drawable.selector_andorid);
				} else {
					qustion1 = false;
					textViewQustion1.setBackgroundColor(Color.WHITE);
				}
			}
		});

		textViewQustion2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (!qustion2) {
					qustion2 = true;
					textViewQustion2
							.setBackgroundResource(R.drawable.selector_andorid);
				} else {
					qustion2 = false;
					textViewQustion2.setBackgroundColor(Color.WHITE);
				}
			}
		});

		textViewQustion3.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (!qustion3) {
					qustion3 = true;
					textViewQustion3
							.setBackgroundResource(R.drawable.selector_andorid);
				} else {
					qustion3 = false;
					textViewQustion3.setBackgroundColor(Color.WHITE);
				}
			}
		});

		textViewQustion4.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (!qustion4) {
					qustion4 = true;
					textViewQustion4
							.setBackgroundResource(R.drawable.selector_andorid);
				} else {
					qustion4 = false;
					textViewQustion4.setBackgroundColor(Color.WHITE);
				}
			}
		});

		textViewQustion5.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (!qustion5) {
					qustion5 = true;
					textViewQustion5
							.setBackgroundResource(R.drawable.selector_andorid);
				} else {
					qustion5 = false;
					textViewQustion5.setBackgroundColor(Color.WHITE);
				}
			}
		});

		textViewQustion6.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (!qustion6) {
					qustion6 = true;
					textViewQustion6
							.setBackgroundResource(R.drawable.selector_andorid);
				} else {
					qustion6 = false;
					textViewQustion6.setBackgroundColor(Color.WHITE);
				}
			}
		});

		relativeLayoutRatingView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

			}
		});

		relativeLayoutBackBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				ratingBar.setEnabled(true);
				scrollView.smoothScrollBy(0, (int) (-751 * ASSL.Yscale()));
				textViewTopbar.setText("RECEIPT");
				relativeLayoutBackBtn.setVisibility(View.INVISIBLE);
				textViewDiv.setVisibility(View.VISIBLE);
				buttonSub.setVisibility(View.VISIBLE);
				LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
						LayoutParams.MATCH_PARENT, (int) (287 * ASSL.Yscale()));
				relativeLayoutRatingView.setLayoutParams(lp);
			}
		});

		scrollView.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				return true;
			}
		});

		try {
			DriverLocationFinder.cancelNotification(getApplicationContext(), 0);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void alertMessageBoxForExit(Activity activity) {
		final AlertDialog.Builder builder = new AlertDialog.Builder(activity);
		builder.setMessage("" + ratingBar.getProgress() + " star")
				.setCancelable(false)
				.setPositiveButton("OK", new DialogInterface.OnClickListener() {

					public void onClick(final DialogInterface dialog,
							int intValue) {

						value = "" + ratingBar.getProgress() + ","
								+ ratingBar.getProgress() + ","
								+ ratingBar.getProgress() + ","
								+ ratingBar.getProgress() + ","
								+ ratingBar.getProgress() + ","
								+ ratingBar.getProgress();
						if (InternetCheck.getInstance(getApplicationContext())
								.isOnline(getApplicationContext())) {
							driverRatingServerCall();
						} else {
							new DialogPopup().alertPopup(Receipt.this, "",
									"Check your internet connection");
						}
					}
				})
				.setNegativeButton("Cancel",
						new DialogInterface.OnClickListener() {
							public void onClick(final DialogInterface dialog,
									int intValue) {
								dialog.cancel();
							}
						});

		final AlertDialog alert = builder.create();
		alert.show();
	}

	public void driverRatingServerCall() {
		Data.loading_box(this, "Loading...");
		RequestParams params = new RequestParams();
		params.put("access_token",
				Data.getInfoOf(getApplicationContext(), Data.ACCESS_TOKEN));
		params.put("order_id",
				Data.getInfoOf(getApplicationContext(), "Rating_orderId"));
		params.put("rating", value);
		params.put("comments", "" + editTextComment.getText().toString());

		AsyncHttpClient client = new AsyncHttpClient();
		client.setTimeout(Httppost_Links.SERVER_TIME_OUT_TIME);
		client.post(Httppost_Links.Baselink + "rate_driver", params,
				new AsyncHttpResponseHandler() {
					@Override
					public void onSuccess(String response) {
						Log.e("resp val", "=" + response);
						try {
							Data.loading_box_stop();
							JSONObject job = new JSONObject(response);

							if (job.getString("status").equals("2")) {
								Intent intent = new Intent(Receipt.this,
										MainActivity.class);
								startActivity(intent);
								finish();
							} else {
								if (response.contains("error")) {
									String error = job.getString("error");
									Log.i("Error ", "-----------" + error);

								} else {
									Data.saveInfoOf(getApplicationContext(),
											"GCM", "");
									Data.saveInfoOf(getApplicationContext(),
											"time", "0");
									Data.saveInfoOf(getApplicationContext(),
											"Rating_orderId", "");
									Data.saveInfoOf(getApplicationContext(),
											"Rating_dateTime", "");
									Data.saveInfoOf(getApplicationContext(),
											"Rating_price", "");
									Data.saveInfoOf(getApplicationContext(),
											"Rating_meals", "");
									Data.saveInfoOf(getApplicationContext(),
											Data.customer_estimate_time,
											"20");
                                    Data.saveInfoOf(getApplicationContext(), Data.OrderPlacedFlag,"0");
									Data.oldResponse = "";
									Intent intent = new Intent(Receipt.this,
											ListofMeals.class);
									startActivity(intent);
									overridePendingTransition(
											R.anim.slide_in_right,
											R.anim.slide_out_left);

									finish();
								}
							}
						} catch (Exception e) {
							Log.v("Login Data Error", "=" + e.toString());
						}
					}

					@Override
					public void onFailure(Throwable arg0) {
						Data.loading_box_stop();
						Log.v("Login Server Error", "fail: " + arg0);
						new DialogPopup().alertPopup(Receipt.this, "",
								"Server not responding\nTry again.");
					}
				});
	}

	@Override
	public void onBackPressed() {

	}

	@Override
	protected void onPause() {
		// finish();
		super.onPause();
	}

	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		Utils.buildAlertMessageNoGps(Receipt.this);
		super.onWindowFocusChanged(hasFocus);
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		EasyTracker.getInstance(Receipt.this).activityStart(this);
		// super.onStart();
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		EasyTracker.getInstance(Receipt.this).activityStop(this);
		// super.onStart();
	}
}
