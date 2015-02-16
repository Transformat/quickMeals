package com.bitekite;

import org.json.JSONException;
import org.json.JSONObject;

import rmn.androidscreenlibrary.ASSL;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
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

public class AddPaymentUser extends Activity implements PushUpdater {

	RelativeLayout relativeLayoutAddPayment, relativeLayoutBack,
			relativeLayoutCover;
	Button button1, button2, button3;
	TextView textViewHeader, textView2, textView3, textView1, textViewAdd;
	ListView listView1;
	ImageView imageView1, imageView2, imageView3;
	ImageView imageViewDelete1, imageViewDelete2, imageViewDelete3;
	RelativeLayout relativeLayout1, relativeLayout2, relativeLayout3,
			relativeLayoutAdd;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_payment_user);

		relativeLayoutAddPayment = (RelativeLayout) findViewById(R.id.relativeLayoutAddPayment);
		new ASSL(this, relativeLayoutAddPayment, 1134, 720, false);

		relativeLayoutCover = (RelativeLayout) findViewById(R.id.relativeLayoutCover);
		relativeLayoutBack = (RelativeLayout) findViewById(R.id.relativeLayoutBack);
		relativeLayout1 = (RelativeLayout) findViewById(R.id.relativeLayout1);
		relativeLayout2 = (RelativeLayout) findViewById(R.id.relativeLayout2);
		relativeLayout3 = (RelativeLayout) findViewById(R.id.relativeLayout3);
		relativeLayoutAdd = (RelativeLayout) findViewById(R.id.relativeLayoutAdd);
		listView1 = (ListView) findViewById(R.id.listView1);
		textViewHeader = (TextView) findViewById(R.id.textView);

		textView1 = (TextView) findViewById(R.id.textView1);
		textView2 = (TextView) findViewById(R.id.textView2);
		textView3 = (TextView) findViewById(R.id.textView3);
		textViewAdd = (TextView) findViewById(R.id.textViewAdd);

		imageView1 = (ImageView) findViewById(R.id.imageView2);
		imageView2 = (ImageView) findViewById(R.id.imageView3);
		imageView3 = (ImageView) findViewById(R.id.imageView4);

		imageViewDelete1 = (ImageView) findViewById(R.id.imageViewDelete1);
		imageViewDelete2 = (ImageView) findViewById(R.id.imageViewDelete2);
		imageViewDelete3 = (ImageView) findViewById(R.id.imageViewDelete3);

		button1 = (Button) findViewById(R.id.button2);
		button2 = (Button) findViewById(R.id.button3);
		button3 = (Button) findViewById(R.id.button4);

		textViewHeader.setTypeface(Data.bariol_bold(getApplicationContext()));
		textView1.setTypeface(Data.bariol_bold(getApplicationContext()));
		textView2.setTypeface(Data.bariol_bold(getApplicationContext()));
		textView3.setTypeface(Data.bariol_bold(getApplicationContext()));
		textViewAdd.setTypeface(Data.bariol_bold(getApplicationContext()));

		button1.setTypeface(Data.bariol_bold(getApplicationContext()));
		button2.setTypeface(Data.bariol_bold(getApplicationContext()));
		button3.setTypeface(Data.bariol_bold(getApplicationContext()));

		if (InternetCheck.getInstance(getApplicationContext()).isOnline(
				getApplicationContext())) {
			serverCallGetCard();
		} else {
			new DialogPopup().alertPopup(AddPaymentUser.this, "",
					"Check your internet connection");
		}

		relativeLayoutBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Data.Flag = 1;
				finish();
				overridePendingTransition(R.anim.slide_in_left,
						R.anim.slide_out_right);

			}
		});

		relativeLayoutAdd.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent intent = new Intent(AddPaymentUser.this,
						PaymentUserEdit.class);
				startActivity(intent);
				finish();
				overridePendingTransition(R.anim.slide_in_right,
						R.anim.slide_out_left);
			}
		});

		imageViewDelete1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if (InternetCheck.getInstance(getApplicationContext())
						.isOnline(getApplicationContext())) {
					serverCallDeleteCard("1");
				} else {
					new DialogPopup().alertPopup(AddPaymentUser.this, "",
							"Check your internet connection");
				}
			}
		});

		imageViewDelete2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (InternetCheck.getInstance(getApplicationContext())
						.isOnline(getApplicationContext())) {
					serverCallDeleteCard("2");
				} else {
					new DialogPopup().alertPopup(AddPaymentUser.this, "",
							"Check your internet connection");
				}
			}
		});
		imageViewDelete3.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (InternetCheck.getInstance(getApplicationContext())
						.isOnline(getApplicationContext())) {
					serverCallDeleteCard("3");
				} else {
					new DialogPopup().alertPopup(AddPaymentUser.this, "",
							"Check your internet connection");
				}
			}
		});

		imageView1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (InternetCheck.getInstance(getApplicationContext())
						.isOnline(getApplicationContext())) {
					serverCallMakeDefaultCard("1");
				} else {
					new DialogPopup().alertPopup(AddPaymentUser.this, "",
							"Check your internet connection");
				}

			}
		});

		imageView2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (InternetCheck.getInstance(getApplicationContext())
						.isOnline(getApplicationContext())) {
					serverCallMakeDefaultCard("2");
				} else {
					new DialogPopup().alertPopup(AddPaymentUser.this, "",
							"Check your internet connection");
				}

			}
		});
		imageView3.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (InternetCheck.getInstance(getApplicationContext())
						.isOnline(getApplicationContext())) {
					serverCallMakeDefaultCard("3");
				} else {
					new DialogPopup().alertPopup(AddPaymentUser.this, "",
							"Check your internet connection");
				}

			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.payment_user, menu);
		return true;
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		Data.Flag = 1;
		finish();
		overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
	}

	public void serverCallGetCard() {

		RequestParams params = new RequestParams();

		params.put("access_token",
				"" + Data.getAccessToken(getApplicationContext()));// +Data.ACCESS_TOKEN);
		Log.d("Data.getAccessToken(getApplicationContext())",
				"Data.getAccessToken(getApplicationContext())"
						+ Data.getAccessToken(getApplicationContext()));
		AsyncHttpClient client = new AsyncHttpClient();

		client.setTimeout(Httppost_Links.SERVER_TIME_OUT_TIME);
		client.post(Httppost_Links.Baselink + "get_credit_card_details",
				params, new AsyncHttpResponseHandler() {

					@Override
					public void onSuccess(String response) {
						JSONObject res;
						try {
							res = new JSONObject(response);
							Log.i("Response", "Response" + res.toString(2));

							if (res.getString("status").equals("2")) {
								Intent intent = new Intent(AddPaymentUser.this,
										MainActivity.class);
								startActivity(intent);
								finish();
							} else {

								JSONObject user_data = res
										.getJSONObject("user_data");

								String card_1 = user_data.getString("card_1");
								String card_2 = user_data.getString("card_2");
								String card_3 = user_data.getString("card_3");
								String defaulty = user_data
										.getString("default");

								setView(card_1, card_2, card_3, defaulty);
								relativeLayoutCover
										.setVisibility(View.INVISIBLE);
							}
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}

					@Override
					public void onFailure(Throwable arg0) {
						Log.e("request fail", arg0.toString());
						new DialogPopup().alertPopup(AddPaymentUser.this, "",
								"Server not responding\nTry again.");
					}
				});
	}

	String htmlString = "&#8226;&#8226;&#8226;&#8226; &#8226;&#8226;&#8226;&#8226; &#8226;&#8226;&#8226;&#8226; ";

	void setView(String card1, String card2, String card3, String defaulty) {

		if (card1.equals("0")) {
			relativeLayout1.setVisibility(View.GONE);
		} else {
			textView1.setText(Html.fromHtml(htmlString) + card1);
			relativeLayout1.setVisibility(View.VISIBLE);
		}
		if (card2.equals("0")) {
			relativeLayout2.setVisibility(View.GONE);
		} else {
			textView2.setText(Html.fromHtml(htmlString) + card2);
			relativeLayout2.setVisibility(View.VISIBLE);
		}
		if (card3.equals("0")) {
			relativeLayout3.setVisibility(View.GONE);
		} else {
			textView3.setText(Html.fromHtml(htmlString) + card3);
			relativeLayout3.setVisibility(View.VISIBLE);
		}

		if (!card1.equals("0") && !card2.equals("0") && !card3.equals("0")) {
			relativeLayoutAdd.setVisibility(View.GONE);
		} else {
			relativeLayoutAdd.setVisibility(View.VISIBLE);
		}

		if (defaulty.equals("1")) {

			Data.saveInfoOf(getApplicationContext(), Data.LAST_4, card1);

			button1.setText("Default");
			button2.setText("");
			button3.setText("");

			imageViewDelete1.setVisibility(View.GONE);
			imageViewDelete2.setVisibility(View.VISIBLE);
			imageViewDelete3.setVisibility(View.VISIBLE);

			button1.setVisibility(View.VISIBLE);
			button2.setVisibility(View.GONE);
			button3.setVisibility(View.GONE);

			imageView1.setBackgroundResource(R.drawable.check_card);
			imageView2.setBackgroundResource(R.drawable.uncheck_card);
			imageView3.setBackgroundResource(R.drawable.uncheck_card);
		} else if (defaulty.equals("2")) {
			Data.saveInfoOf(getApplicationContext(), Data.LAST_4, card2);

			button1.setText("");
			button2.setText("Default");
			button3.setText("");

			imageViewDelete1.setVisibility(View.VISIBLE);
			imageViewDelete2.setVisibility(View.GONE);
			imageViewDelete3.setVisibility(View.VISIBLE);

			button2.setVisibility(View.VISIBLE);
			button1.setVisibility(View.GONE);
			button3.setVisibility(View.GONE);

			imageView2.setBackgroundResource(R.drawable.check_card);
			imageView1.setBackgroundResource(R.drawable.uncheck_card);
			imageView3.setBackgroundResource(R.drawable.uncheck_card);
		} else if (defaulty.equals("3")) {

			Data.saveInfoOf(getApplicationContext(), Data.LAST_4, card3);
			button1.setText("");
			button2.setText("");
			button3.setText("Default");

			imageViewDelete1.setVisibility(View.VISIBLE);
			imageViewDelete2.setVisibility(View.VISIBLE);
			imageViewDelete3.setVisibility(View.GONE);

			button3.setVisibility(View.VISIBLE);
			button2.setVisibility(View.GONE);
			button1.setVisibility(View.GONE);

			imageView3.setBackgroundResource(R.drawable.check_card);
			imageView2.setBackgroundResource(R.drawable.uncheck_card);
			imageView1.setBackgroundResource(R.drawable.uncheck_card);
		}

	}

	void serverCallDeleteCard(String id) {
		RequestParams params = new RequestParams();

		params.put("id", "" + id);
		params.put("access_token",
				"" + Data.getAccessToken(getApplicationContext()));// +Data.ACCESS_TOKEN);
		Log.d("Data.getAccessToken(getApplicationContext())",
				"Data.getAccessToken(getApplicationContext())"
						+ Data.getAccessToken(getApplicationContext()));

		AsyncHttpClient client = new AsyncHttpClient();

		client.setTimeout(Httppost_Links.SERVER_TIME_OUT_TIME);
		client.post(Httppost_Links.Baselink + "delete_credit_card", params,
				new AsyncHttpResponseHandler() {

					@Override
					public void onSuccess(String response) {
						JSONObject res;
						try {
							res = new JSONObject(response);
							Log.i("Response", "Response" + res.toString(2));
							if (res.getString("status").equals("2")) {
								Intent intent = new Intent(AddPaymentUser.this,
										MainActivity.class);
								startActivity(intent);
								finish();
							} else {
								JSONObject user_data = res
										.getJSONObject("user_data");

								String card_1 = user_data.getString("card_1");
								String card_2 = user_data.getString("card_2");
								String card_3 = user_data.getString("card_3");
								String defaulty = user_data
										.getString("default");
								setView(card_1, card_2, card_3, defaulty);
							}

						} catch (JSONException e) {
							e.printStackTrace();
						}
					}

					@Override
					public void onFailure(Throwable arg0) {
						Log.e("request fail", arg0.toString());
						new DialogPopup().alertPopup(AddPaymentUser.this, "",
								"Server not responding\nTry again.");
					}
				});
	}

	void serverCallMakeDefaultCard(String id) {
		RequestParams params = new RequestParams();

		params.put("id", "" + id);
		params.put("access_token",
				"" + Data.getAccessToken(getApplicationContext()));// +Data.ACCESS_TOKEN);
		Log.d("Data.getAccessToken(getApplicationContext())",
				"Data.getAccessToken(getApplicationContext())"
						+ Data.getAccessToken(getApplicationContext()));
		AsyncHttpClient client = new AsyncHttpClient();

		client.setTimeout(Httppost_Links.SERVER_TIME_OUT_TIME);
		client.post(Httppost_Links.Baselink + "make_default_card", params,
				new AsyncHttpResponseHandler() {

					@Override
					public void onSuccess(String response) {
						JSONObject res;
						try {
							res = new JSONObject(response);
							Log.i("Response", "Response" + res.toString(2));

							if (res.getString("status").equals("2")) {
								Intent intent = new Intent(AddPaymentUser.this,
										MainActivity.class);
								startActivity(intent);
								finish();
							} else {
								JSONObject user_data = res
										.getJSONObject("user_data");

								String card_1 = user_data.getString("card_1");
								String card_2 = user_data.getString("card_2");
								String card_3 = user_data.getString("card_3");
								String defaulty = user_data
										.getString("default");
								setView(card_1, card_2, card_3, defaulty);
							}
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}

					@Override
					public void onFailure(Throwable arg0) {
						Log.e("request fail", arg0.toString());
						new DialogPopup().alertPopup(AddPaymentUser.this, "",
								"Server not responding\nTry again.");
					}
				});
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
							Intent intent = new Intent(AddPaymentUser.this,
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
		Utils.buildAlertMessageNoGps(AddPaymentUser.this);
		super.onWindowFocusChanged(hasFocus);
	}

	@Override
	protected void onStart() {
		super.onStart();
		EasyTracker.getInstance(AddPaymentUser.this).activityStart(this);
	}

	@Override
	protected void onStop() {
		super.onStop();
		EasyTracker.getInstance(AddPaymentUser.this).activityStop(this);
	}
}
