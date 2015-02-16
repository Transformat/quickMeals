package com.bitekite;

import org.json.JSONException;
import org.json.JSONObject;

import rmn.androidscreenlibrary.ASSL;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bitekite.driver.Httppost_Links;
import com.bitekite.utils.Data;
import com.bitekite.utils.DialogPopup;
import com.google.analytics.tracking.android.EasyTracker;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class Promotion extends Activity {

	RelativeLayout relativeLayoutPromotions, relativeLayoutBack,
			relativeLayoutCover;
	EditText editTextPromoCode;
	TextView textViewPromo, textViewCreditAmount, textViewCredit;
	Button buttonApply;
	String creditbalance;
	ImageView imageViewLoader;
	ProgressBar progressBar1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_promotion);

		relativeLayoutPromotions = (RelativeLayout) findViewById(R.id.relativeLayoutPromotions);
		new ASSL(this, relativeLayoutPromotions, 1134, 720, false);

		editTextPromoCode = (EditText) findViewById(R.id.editTextPromoCode);
		relativeLayoutBack = (RelativeLayout) findViewById(R.id.relativeLayoutBack);
		buttonApply = (Button) findViewById(R.id.buttonApply);
		textViewPromo = (TextView) findViewById(R.id.textViewPromo);
		textViewCreditAmount = (TextView) findViewById(R.id.textViewCreditAmount);
		textViewCredit = (TextView) findViewById(R.id.textViewCredit);
		progressBar1=(ProgressBar)findViewById(R.id.progressBar1);

		relativeLayoutCover = (RelativeLayout) findViewById(R.id.relativeLayoutCover);
		imageViewLoader = (ImageView) findViewById(R.id.imageViewLoader);

		editTextPromoCode.setTypeface(Data
				.bariol_regular(getApplicationContext()));
		relativeLayoutBack = (RelativeLayout) findViewById(R.id.relativeLayoutBack);
		buttonApply.setTypeface(Data.bariol_regular(getApplicationContext()));
		textViewPromo.setTypeface(Data.bariol_bold(getApplicationContext()));
		textViewCreditAmount.setTypeface(Data
				.bariol_regular(getApplicationContext()));
		textViewCredit
				.setTypeface(Data.bariol_regular(getApplicationContext()));

		serverCallGetCreditBalance();

		relativeLayoutBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				InputMethodManager imm = (InputMethodManager) getApplicationContext()
						.getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(editTextPromoCode.getWindowToken(),
						0);
				finish();
				overridePendingTransition(R.anim.hold, R.anim.slide_out_right);
			}
		});

		buttonApply.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (editTextPromoCode.getText().toString().trim().equals("")) {
					new DialogPopup().alertPopup(Promotion.this, "",
							"Please enter promo code");
				} else {
					serverCallPromocode();
				}
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.promotion, menu);
		return true;
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		finish();
		overridePendingTransition(R.anim.hold, R.anim.slide_out_right);
	}

	void serverCallGetCreditBalance() {

		RequestParams params = new RequestParams();

		params.put("access_token",
				Data.getInfoOf(getApplicationContext(), Data.ACCESS_TOKEN)); // +
																				// Data.ACCESS_TOKEN);//
																				// ?

		AsyncHttpClient client = new AsyncHttpClient();

		client.setTimeout(Httppost_Links.SERVER_TIME_OUT_TIME);
		client.post(Httppost_Links.Baselink + "get_credit_balance", params,
				new AsyncHttpResponseHandler() {

					@Override
					public void onSuccess(String response) {
						Log.i("request succesfull", "response = " + response);
						// fillServerData(response);
						JSONObject res;
						try {
							res = new JSONObject(response.toString());
							if (res.getString("status").equals("2")) {
								Intent intent = new Intent(Promotion.this,
										MainActivity.class);
								startActivity(intent);
								finish();
							} else {
								if (res.has("error")) {
									Log.d("Data.SERVER_ERROR_MSG",
											"Data.SERVER_ERROR_MSG"
													+ res.getString("error"));
									new DialogPopup().alertPopup(
											Promotion.this, "",
											res.getString("error"));

								} else {

									Data.creditBalance = res
											.getString("credit_balance");
									Double credit = Double
											.parseDouble(Data.creditBalance);
									String prizes2 = credit.toString();
									String str2[] = prizes2.split("\\.");
									if (str2.length == 1) {
										textViewCreditAmount.setText(Html
												.fromHtml("$" + credit
														+ ".<sup><small>"
														+ "00"
														+ "</small></sup>"));
									} else {
										textViewCreditAmount.setText(Html
												.fromHtml("$" + str2[0]
														+ ".<sup><small>"
														+ str2[1]
														+ "</small></sup>"));
									}

									relativeLayoutCover
											.setVisibility(View.INVISIBLE);
									imageViewLoader
											.setVisibility(View.INVISIBLE);
								}
							}
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}

					@Override
					public void onFailure(Throwable arg0) {
						Log.e("request fail", arg0.toString());
						new DialogPopup().alertPopup(Promotion.this, "",
								"Server not responding\nTry again.");
					}
				});

	}

	void serverCallPromocode() {

		progressBar1.setVisibility(View.VISIBLE);
		RequestParams params = new RequestParams();

		params.put("access_token",
				Data.getInfoOf(getApplicationContext(), Data.ACCESS_TOKEN)); // +
																				// Data.ACCESS_TOKEN);//
																				// ?
		params.put("promo_code", "" + editTextPromoCode.getText().toString());//

		AsyncHttpClient client = new AsyncHttpClient();

		client.setTimeout(Httppost_Links.SERVER_TIME_OUT_TIME);
		client.post(Httppost_Links.Baselink + "apply_promo_code2", params,
				new AsyncHttpResponseHandler() {

					@Override
					public void onSuccess(String response) {
						Log.i("request succesfull", "response = " + response);
						JSONObject res;
						try {
							progressBar1.setVisibility(View.INVISIBLE);
							res = new JSONObject(response.toString());
							if (res.getString("status").equals("2")) {
								Intent intent = new Intent(Promotion.this,
										MainActivity.class);
								startActivity(intent);
								finish();
							} else {
								if (res.has("error")) {
									Log.d("Data.SERVER_ERROR_MSG",
											"Data.SERVER_ERROR_MSG"
													+ res.getString("error"));
									new DialogPopup().alertPopup(
											Promotion.this, "",
											res.getString("error"));

								} else {
									//
									Data.creditBalance = res
											.getString("credit_balance");
									Double credit = Double
											.parseDouble(Data.creditBalance);

									String prizes2 = credit.toString();
									String str2[] = prizes2.split("\\.");
									new DialogPopup().alertPopup(
											Promotion.this, "",
											"Promo code applied successfully");
									editTextPromoCode.setText("");
									if (str2.length == 1) {
										textViewCreditAmount.setText(Html
												.fromHtml("$" + credit
														+ ".<sup><small>"
														+ "00"
														+ "</small></sup>"));
									} else {
										textViewCreditAmount.setText(Html
												.fromHtml("$" + str2[0]
														+ ".<sup><small>"
														+ str2[1]
														+ "</small></sup>"));
									}
								}
							}
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}

					@Override
					public void onFailure(Throwable arg0) {
						Log.e("request fail", arg0.toString());
						new DialogPopup().alertPopup(Promotion.this, "",
								"Server not responding\nTry again.");
						progressBar1.setVisibility(View.INVISIBLE);
					}
				});

	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		EasyTracker.getInstance(Promotion.this).activityStart(this);
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		EasyTracker.getInstance(Promotion.this).activityStop(this);
	}

}
