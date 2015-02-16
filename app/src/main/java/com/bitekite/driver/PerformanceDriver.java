package com.bitekite.driver;
import com.bitekite.R;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import rmn.androidscreenlibrary.ASSL;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bitekite.GCMIntentService;
import com.bitekite.Log;
import com.bitekite.MainActivity;
import com.bitekite.adpters.PerformanceAdapterDriver;
import com.bitekite.driver.classes.PerformanceBase;
import com.bitekite.utils.Data;
import com.bitekite.utils.DialogPopup;
import com.bitekite.utils.InternetCheck;
import com.google.analytics.tracking.android.EasyTracker;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class PerformanceDriver extends Activity  implements PushUpdater{

	TextView textViewHours, textViewHoursPrice, textViewNumberOfOrders,
			textViewOrderPrice, textViewTotalEarn, textViewHistory,textViewToday,textViewTotal;
	ExpandableListView expandableListViewAccountHistory;

	RelativeLayout relativeLayoutOrder, relativeLayoutDeliveredOrder,
			relativeLayoutPerformance, relativeLayoutAccount,
			relativeLayoutSupport, relativeLayoutEmergency,
			relativeLayoutCheckout,relativeLayoutLogout;
	RelativeLayout relativeLayoutMenu, relativeLayoutHeader,
			relativeLayoutMain, relativeLayoutHidden;
	LinearLayout linearLayoutMenuView;
	
	TextView textViewOrder, textViewDeliveredOrder,
	textViewPerformance, textViewAccount,
	textViewSupport, textViewEmergency,textViewCheckout,textViewLogout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_performance_driver);
		new ASSL(this, (ViewGroup) findViewById(R.id.root), 1134, 720, false);
		Data.activity = this;
		textViewHours = (TextView) findViewById(R.id.HoursTxt);
		textViewHoursPrice = (TextView) findViewById(R.id.HoursPriceTxt);
		textViewNumberOfOrders = (TextView) findViewById(R.id.NumberOfOrdersTxt);
		textViewOrderPrice = (TextView) findViewById(R.id.OrderWisePriceTxt);
		textViewTotalEarn = (TextView) findViewById(R.id.TotalEarn);
		textViewHistory = (TextView) findViewById(R.id.HistoryTxt);
		textViewToday = (TextView) findViewById(R.id.TodayTxt);
		textViewTotal = (TextView) findViewById(R.id.TotalTxt);
		relativeLayoutLogout = (RelativeLayout) findViewById(R.id.LogoutLayout);
		
		
		textViewHours.setTypeface(Data.bariol_regular(getApplicationContext()));
		textViewHoursPrice.setTypeface(Data.bariol_regular(getApplicationContext()));
		textViewNumberOfOrders.setTypeface(Data.bariol_regular(getApplicationContext()));
		textViewOrderPrice.setTypeface(Data.bariol_regular(getApplicationContext()));
		textViewTotalEarn.setTypeface(Data.bariol_bold(getApplicationContext()));
		textViewHistory.setTypeface(Data.bariol_bold(getApplicationContext()));
		textViewToday.setTypeface(Data.bariol_bold(getApplicationContext()));
		textViewTotal.setTypeface(Data.bariol_bold(getApplicationContext()));
		
		
		
		relativeLayoutHidden = (RelativeLayout) findViewById(R.id.hidden);
		expandableListViewAccountHistory = (ExpandableListView) findViewById(R.id.expandableListView1);

		relativeLayoutMenu = (RelativeLayout) findViewById(R.id.menuBtnLayout);

		relativeLayoutHeader = (RelativeLayout) findViewById(R.id.header);
		relativeLayoutMain = (RelativeLayout) findViewById(R.id.mainlayout);

		relativeLayoutOrder = (RelativeLayout) findViewById(R.id.TodayOrderLayout);
		relativeLayoutDeliveredOrder = (RelativeLayout) findViewById(R.id.DeliveredOrderLayout);
		relativeLayoutPerformance = (RelativeLayout) findViewById(R.id.PerformanceLayout);
		relativeLayoutAccount = (RelativeLayout) findViewById(R.id.AccountLayout);
		relativeLayoutSupport = (RelativeLayout) findViewById(R.id.SupportLayout);
		relativeLayoutEmergency = (RelativeLayout) findViewById(R.id.EmergencyLayout);
		linearLayoutMenuView = (LinearLayout) findViewById(R.id.menuView);
		relativeLayoutCheckout = (RelativeLayout) findViewById(R.id.CheckOutLayout);
		textViewLogout = (TextView) findViewById(R.id.LogoutTxt);
		
		textViewCheckout = (TextView) findViewById(R.id.CheckOutTxt);
		textViewOrder = (TextView) findViewById(R.id.TodayOrderTxt);
		textViewDeliveredOrder = (TextView) findViewById(R.id.DeliveredOrderTxt);
		textViewPerformance = (TextView) findViewById(R.id.PerformanceTxt);
		textViewAccount = (TextView) findViewById(R.id.AccountTxt);
		textViewSupport = (TextView) findViewById(R.id.SupportTxt);
		textViewEmergency = (TextView) findViewById(R.id.EmergencyTxt);
		
		textViewLogout.setTypeface(Data.bariol_bold(getApplicationContext()));
		textViewCheckout.setTypeface(Data.bariol_bold(getApplicationContext()));
		textViewOrder.setTypeface(Data.bariol_bold(getApplicationContext()));
		textViewDeliveredOrder.setTypeface(Data.bariol_bold(getApplicationContext()));
		textViewPerformance.setTypeface(Data.bariol_bold(getApplicationContext()));
		textViewAccount.setTypeface(Data.bariol_bold(getApplicationContext()));
		textViewSupport.setTypeface(Data.bariol_bold(getApplicationContext()));
		textViewEmergency.setTypeface(Data.bariol_bold(getApplicationContext()));
		
		if (OrderScreen.orderList.size() > 0) {
			relativeLayoutLogout.setVisibility(View.GONE);
			relativeLayoutCheckout.setVisibility(View.GONE);
		}
		else{
		if (Data.getInfoOf(getApplicationContext(), Data.CHECKINVALUE)
				.equalsIgnoreCase("0")) {
			relativeLayoutLogout.setVisibility(View.VISIBLE);
			relativeLayoutCheckout.setVisibility(View.GONE);
		} else {
			relativeLayoutLogout.setVisibility(View.GONE);
			relativeLayoutCheckout.setVisibility(View.VISIBLE);
		}
		}
		
		relativeLayoutMenu.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				openAnim();

			}
		});
		
		if(OrderScreen.orderList.size()>0)
		{
			relativeLayoutLogout.setVisibility(View.GONE);
			relativeLayoutCheckout.setVisibility(View.GONE);
		}

		linearLayoutMenuView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				closeAnim();

			}
		});

		relativeLayoutOrder.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(PerformanceDriver.this,
						OrderScreen.class);
				startActivity(intent); finish();
				overridePendingTransition(R.anim.slide_in_right,
						R.anim.hold);

			}
		});
		relativeLayoutDeliveredOrder.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(PerformanceDriver.this,
						History.class);
				startActivity(intent); finish();
				overridePendingTransition(R.anim.slide_in_right,
						R.anim.hold);
			}
		});

		relativeLayoutPerformance.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				closeAnim();

			}
		});

		relativeLayoutAccount.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(PerformanceDriver.this,
						AccountDriver.class);
				startActivity(intent); finish();
				overridePendingTransition(R.anim.slide_in_right,
						R.anim.hold);

			}
		});
		relativeLayoutEmergency.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(PerformanceDriver.this,
						EmergencyDriver.class);
				startActivity(intent); finish();
				overridePendingTransition(R.anim.slide_in_right,
						R.anim.hold);
			}
		});

		relativeLayoutSupport.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String htmlString = "&#8226;";
				Intent emailIntent = new Intent(Intent.ACTION_SEND);
				emailIntent.putExtra(Intent.EXTRA_EMAIL,
				new String[] { "support@bitekite.com" });
				emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Bite"+Html.fromHtml(htmlString)+"Kite Support");
				emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, "");
				emailIntent.setType("plain/text");
				try {
					startActivity(Intent.createChooser(emailIntent,"Send mail..."));
					Log.i("Finished sending email...", "");
				} catch (android.content.ActivityNotFoundException ex) {

//					Toast.makeText(getApplicationContext(),
//
//					"There is no email client installed.",
//
//					Toast.LENGTH_SHORT).show();

				}
//				Intent intent = new Intent(PerformanceDriver.this,
//						SupportDriver.class);
//				startActivity(intent); finish();
//				overridePendingTransition(R.anim.slide_in_right,
//						R.anim.hold);

			}
		});
		
		relativeLayoutCheckout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(PerformanceDriver.this, OrderScreen.class);
				intent.putExtra("checkout", true);
				startActivity(intent); finish();
				overridePendingTransition(R.anim.slide_out_bottom_back,
						R.anim.hold);
			}
		});
		relativeLayoutLogout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Utils.logout();
			}
		});
		if (InternetCheck.getInstance(getApplicationContext()).isOnline(
				getApplicationContext())) {
			driverEarningsCall();
		} else {
			new DialogPopup().alertPopup(PerformanceDriver.this,
					"", "Check your internet connection");
		}
		
	}

	public void driverEarningsCall() {

//		Data.loading_box(Data.activity, "Loading...");
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();

		Log.e("Access token",
				"," + Data.getInfoOf(Data.activity, Data.ACCESS_TOKEN));

		params.put("access_token",
				Data.getInfoOf(Data.activity, Data.ACCESS_TOKEN));
		client.setTimeout(Httppost_Links.SERVER_TIME_OUT_TIME);
		client.post(Httppost_Links.DRIVER_EARNINGS, params,
				new AsyncHttpResponseHandler() {
					@Override
					public void onSuccess(String response) {
//						Data.loading_box_stop();
						Log.v("checkin Api", "=" + response);

						JSONObject res;
						try {
							res = new JSONObject(response);
							Log.d("response", "response" + res.toString(2));
							
							if(res.getString("status").equals("2"))
							{
//								Data.alertMessageBox(SplashScreen.this,popup.getString("title"),popup.getString("text"),Data.getInfoOf(getApplicationContext(), Data.loginFrom));
							Intent intent = new Intent(PerformanceDriver.this,MainActivity.class);
							startActivity(intent);
							finish();
							}
							else
							{
							if (res.has("error")) {

							} else {
								relativeLayoutHidden.setVisibility(View.GONE);
								ArrayList<PerformanceBase> accountList = new ArrayList<PerformanceBase>();
								try {
									for (int i = 0; i < res
											.getJSONArray("data").length(); i++) {
										PerformanceBase accountBase = new PerformanceBase();
										JSONArray jsonArray = res
												.getJSONArray("data");
										accountBase.setDate(jsonArray
												.getJSONObject(i).getString(
														"date"));
										accountBase.setFinalTotal(jsonArray
												.getJSONObject(i).getString(
														"final_total"));
										accountBase.setHourPrice(jsonArray
												.getJSONObject(i).getString(
														"hour_price"));
										accountBase.setHourSum(jsonArray
												.getJSONObject(i).getString(
														"hour_sum"));
										accountBase.setOrderPrice(jsonArray
												.getJSONObject(i).getString(
														"order_price"));
										accountBase.setOrderSum(jsonArray
												.getJSONObject(i).getString(
														"order_sum"));
										accountBase.setTotalHours(jsonArray
												.getJSONObject(i).getString(
														"total_hours"));
										accountBase.setTotalOrder(jsonArray
												.getJSONObject(i).getString(
														"total_orders"));
										accountBase.setMinutes(jsonArray
												.getJSONObject(i).getString(
														"minutes"));
										
										accountList.add(accountBase);
									}
								} catch (JSONException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								
								String temp="";
								if(accountList.get(0).getTotalHours().toString().equals("0"))
								{
									temp="";
								}
								else if(accountList.get(0).getTotalHours().toString().equals("1"))
								{
									temp=temp+(accountList.get(0).getTotalHours().toString())+ " hour ";
								}
								else
								{
									temp=temp+(accountList.get(0).getTotalHours().toString())+ " hours ";
								}
								
								
								if(accountList.get(0).getMinutes().toString().equals("0"))
								{
									temp = temp+"";
								}
								else if(accountList.get(0).getMinutes().toString().equals("1"))
								{
									temp = temp+" "+accountList.get(0).getMinutes().toString()+ " minute ";
								}
								else
								{
									temp = temp+" "+accountList.get(0).getMinutes().toString()+ " minutes ";
								}
								
								if(accountList.get(0).getTotalHours().toString().equals("0") && accountList.get(0).getMinutes().toString().equals("0"))
								{
									temp = temp+"0 hour ";
								}
								
								temp = temp +"X $"+ accountList.get(0).getHourPrice().toString();
								textViewHours.setText(temp);
//								textViewHours.setText(accountList.get(0).getTotalHours().toString()+ " hours"+ accountList.get(0).getHourPrice().toString());
								textViewHoursPrice.setText("$"+ accountList.get(0).getHourSum());
								textViewNumberOfOrders.setText(accountList.get(0).getTotalOrder().toString()+ " order X $"+ accountList.get(0).getOrderPrice().toString());
								textViewOrderPrice.setText("$"
										+ accountList.get(0).getOrderSum());
								textViewTotalEarn.setText("$"
										+ accountList.get(0).getFinalTotal());

								PerformanceAdapterDriver accountAdapterDriver = new PerformanceAdapterDriver(
										PerformanceDriver.this, accountList);
								expandableListViewAccountHistory
										.setAdapter(accountAdapterDriver);
								if (accountList.size() == 1) {
									textViewHistory
											.setVisibility(View.INVISIBLE);
								} else {
									textViewHistory.setVisibility(View.VISIBLE);
								}
							}
							}
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}

					@Override
					public void onFailure(Throwable arg0, String json) {
//						Data.loading_box_stop();
						Log.v("request fail", "response=" + json);
						new DialogPopup()
						.alertPopup(PerformanceDriver.this, "",
								"Server not responding\nTry again.");
					}
				});

	}

	@Override
	public void onResume() {
		super.onResume();
		Data.activity = this;
		OrderScreen.pushUpdater = this;
	}

	@Override
	public void onBackPressed() {
		if (linearLayoutMenuView.isShown()) {
			closeAnim();
		} else {
			super.onBackPressed();
			overridePendingTransition(R.anim.hold,
					R.anim.slide_out_right);
			finish();
		}

	}
	
	void openAnim() {
		if (Data.startAnim) {
			Data.startAnim = false;
			relativeLayoutHeader.clearAnimation();
			relativeLayoutMain.clearAnimation();
			linearLayoutMenuView.setVisibility(View.VISIBLE);
			relativeLayoutHeader.startAnimation(Data
					.getSlideOutUp(getApplicationContext()));
			relativeLayoutMain.startAnimation(Data
					.getslideOutBottom(getApplicationContext()));
			relativeLayoutHeader.setVisibility(View.INVISIBLE);
			relativeLayoutMain.setVisibility(View.INVISIBLE);
		}
	}

	void closeAnim() {
		if (Data.startAnim) {
			relativeLayoutHeader.clearAnimation();
			relativeLayoutMain.clearAnimation();
			relativeLayoutHeader.startAnimation(Data
					.getslideOutUpBack(getApplicationContext()));
			relativeLayoutMain.startAnimation(Data
					.getslideOutBottomBack(getApplicationContext()));
			relativeLayoutHeader.setVisibility(View.VISIBLE);
			relativeLayoutMain.setVisibility(View.VISIBLE);
			Handler mHandler = new Handler();
			mHandler.postDelayed(new Runnable() {
				public void run() {
					linearLayoutMenuView.setVisibility(View.INVISIBLE);
				}
			}, 700);
		}
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
							new DialogPopup().alertPopup(PerformanceDriver.this, "",
									GCMIntentService.stringMessage);
						}
						else if(GCMIntentService.flag.equals("19"))
						{
							Data.saveInfoOf(Data.activity,
									Data.CHECKINVALUE, "0");
							Intent intent = new Intent(PerformanceDriver.this,
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
							new DialogPopup().alertPopup(PerformanceDriver.this, "",
									GCMIntentService.stringMessage);
						}
						else if(GCMIntentService.flag.equals("16"))
						{
							new DialogPopup().alertPopup(PerformanceDriver.this, "",
									GCMIntentService.stringMessage);
						}
					}
				});
			}
		}).start();
	}
	@Override
	protected void onPause() {
		super.onPause();
		Log.d("onPause", "onPause");
		OrderScreen.pushUpdater=null;
	}
	@Override
	protected void onDestroy() {
		super.onDestroy();
		Log.d("onDestroy", "onDestroy");
	}
	
	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		Utils.buildAlertMessageNoGps(PerformanceDriver.this);
		super.onWindowFocusChanged(hasFocus);
	}
	
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		EasyTracker.getInstance(PerformanceDriver.this).activityStart(this);
//		super.onStart();
	}
	
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		EasyTracker.getInstance(PerformanceDriver.this).activityStop(this);
//		super.onStart();
	}
}
