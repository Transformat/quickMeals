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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bitekite.DriverLocationFinder;
import com.bitekite.GCMIntentService;
import com.bitekite.Log;
import com.bitekite.MainActivity;
import com.bitekite.adpters.HistoryAdapterDriver;
import com.bitekite.driver.classes.DetailsBase;
import com.bitekite.driver.classes.HistoryBase;
import com.bitekite.driver.classes.ToppingDetailsBase;
import com.bitekite.utils.Data;
import com.bitekite.utils.DialogPopup;
import com.bitekite.utils.InternetCheck;
import com.google.analytics.tracking.android.EasyTracker;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class History extends Activity  implements PushUpdater{

	ProgressBar progressBar;
	ExpandableListView expandableListViewHistory;
	RelativeLayout relativeLayoutMenu, relativeLayoutHeader,
			relativeLayoutMain, relativeLayoutHidden;

	RelativeLayout relativeLayoutOrder, relativeLayoutDeliveredOrder,
			relativeLayoutPerformance, relativeLayoutAccount,
			relativeLayoutSupport, relativeLayoutEmergency,
			relativeLayoutCheckout,relativeLayoutLogout;

	
	ImageView imageViewNoOrderTodayIcon;
	LinearLayout linearLayoutMenuView;

	TextView textViewDayHistory,textViewNoOrderToday;

	TextView textViewOrder, textViewDeliveredOrder, textViewPerformance,
			textViewAccount, textViewSupport, textViewEmergency,
			textViewCheckout,textViewLogout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_history);
		new ASSL(this, (ViewGroup) findViewById(R.id.root), 1134, 720, false);
		Data.activity = this;

		textViewDayHistory = (TextView) findViewById(R.id.DayHistoryTxt);
		expandableListViewHistory = (ExpandableListView) findViewById(R.id.HistoryExpandableListView1);

		textViewDayHistory.setTypeface(Data.bariol_bold(getApplicationContext()));

		relativeLayoutMenu = (RelativeLayout) findViewById(R.id.menuBtnLayout);
		relativeLayoutHidden = (RelativeLayout) findViewById(R.id.hidden);
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
		imageViewNoOrderTodayIcon = (ImageView) findViewById(R.id.no_order_today_icon);
		textViewNoOrderToday = (TextView) findViewById(R.id.no_order_today_Txt);
		relativeLayoutLogout = (RelativeLayout) findViewById(R.id.LogoutLayout);
		textViewCheckout = (TextView) findViewById(R.id.CheckOutTxt);
		textViewOrder = (TextView) findViewById(R.id.TodayOrderTxt);
		textViewDeliveredOrder = (TextView) findViewById(R.id.DeliveredOrderTxt);
		textViewPerformance = (TextView) findViewById(R.id.PerformanceTxt);
		textViewAccount = (TextView) findViewById(R.id.AccountTxt);
		textViewSupport = (TextView) findViewById(R.id.SupportTxt);
		textViewEmergency = (TextView) findViewById(R.id.EmergencyTxt);
		textViewLogout = (TextView) findViewById(R.id.LogoutTxt);
	
		textViewLogout.setTypeface(Data.bariol_bold(getApplicationContext()));
		textViewCheckout.setTypeface(Data.bariol_bold(getApplicationContext()));
		textViewOrder.setTypeface(Data.bariol_bold(getApplicationContext()));
		textViewDeliveredOrder.setTypeface(Data.bariol_bold(getApplicationContext()));
		textViewPerformance.setTypeface(Data.bariol_bold(getApplicationContext()));
		textViewAccount.setTypeface(Data.bariol_bold(getApplicationContext()));
		textViewSupport.setTypeface(Data.bariol_bold(getApplicationContext()));
		textViewEmergency.setTypeface(Data.bariol_bold(getApplicationContext()));

		imageViewNoOrderTodayIcon.setVisibility(View.INVISIBLE);
		textViewNoOrderToday.setVisibility(View.INVISIBLE);
		progressBar= (ProgressBar) findViewById(R.id.progressBar1);
		textViewNoOrderToday.setTypeface(Data.bariol_regular(getApplicationContext()));
		
		
		if(OrderScreen.orderList.size()>0)
		{
			relativeLayoutLogout.setVisibility(View.GONE);
			relativeLayoutCheckout.setVisibility(View.GONE);
		}
		else{
			
	
		if (Data.getInfoOf(getApplicationContext(), Data.CHECKINVALUE)
				.equalsIgnoreCase("0")) {
			relativeLayoutLogout.setVisibility(View.VISIBLE);
			relativeLayoutCheckout.setVisibility(View.GONE);
		}
		else
		{
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

		linearLayoutMenuView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				closeAnim();

			}
		});

		relativeLayoutOrder.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(History.this, OrderScreen.class);
				startActivity(intent); finish();
				overridePendingTransition(R.anim.slide_in_right, R.anim.hold);

			}
		});
		relativeLayoutDeliveredOrder.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				closeAnim();

			}
		});

		relativeLayoutPerformance.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(History.this,
						PerformanceDriver.class);
				startActivity(intent); finish();
				overridePendingTransition(R.anim.slide_in_right, R.anim.hold);
			}
		});

		relativeLayoutAccount.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(History.this, AccountDriver.class);
				startActivity(intent); finish();
				overridePendingTransition(R.anim.slide_in_right, R.anim.hold);

			}
		});
		relativeLayoutEmergency.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(History.this, EmergencyDriver.class);
				startActivity(intent); finish();
				overridePendingTransition(R.anim.slide_in_right, R.anim.hold);
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
//				Intent intent = new Intent(History.this, SupportDriver.class);
//				startActivity(intent); finish();
//				overridePendingTransition(R.anim.slide_in_right, R.anim.hold);

			}
		});
		
		relativeLayoutCheckout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				Intent intent = new Intent(History.this, OrderScreen.class);
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
			historyServerCall();
		} else {
			new DialogPopup().alertPopup(History.this,
					"", "Check your internet connection");
		}
		
	}

	public void historyServerCall() {

		// Data.loading_box(Data.activity, "Loading...");

		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();

		params.put("access_token",
				Data.getInfoOf(Data.activity, Data.ACCESS_TOKEN));
		client.setTimeout(Httppost_Links.SERVER_TIME_OUT_TIME);
		client.post(Httppost_Links.HISTORY, params,
				new AsyncHttpResponseHandler() {

					@Override
					public void onSuccess(String response_str) {
						// Data.loading_box_stop();
						Log.v("Order Api", "=" + response_str);
						// {"uid":0,"hostname":"10.151.0.245","roles":{"1":"anonymous user"},"cache":0}

						try {
							JSONObject jb = new JSONObject(response_str);
							Log.v("Order Api", "=" + jb.toString(2));
							if(jb.getString("status").equals("2"))
							{
//								Data.alertMessageBox(SplashScreen.this,popup.getString("title"),popup.getString("text"),Data.getInfoOf(getApplicationContext(), Data.loginFrom));
							Intent intent = new Intent(History.this,MainActivity.class);
							startActivity(intent);
							finish();
							}
							else
							{
							if (jb.getJSONArray("data").length() == 0) {
//								Data.getAlertDialogWithbothMessage(
//										Data.activity, "OOPS!",
//										"No order found!");
								
								imageViewNoOrderTodayIcon.setVisibility(View.VISIBLE);
								textViewNoOrderToday.setVisibility(View.VISIBLE);
								progressBar.setVisibility(View.INVISIBLE);
							} else {
								relativeLayoutHidden.setVisibility(View.GONE);
								ArrayList<HistoryBase> historyList = new ArrayList<HistoryBase>();
								ArrayList<DetailsBase> detailsList = null;
								ArrayList<ToppingDetailsBase> toppingList;

								try {
									for (int i = 0; i < jb.getJSONArray("data")
											.length(); i++) {
										HistoryBase historyBase = new HistoryBase();
										JSONArray jsonArray = jb
												.getJSONArray("data");
										historyBase.setCustomer_name(jsonArray
												.getJSONObject(i).getString(
														"customer_name"));
										historyBase.setPrice(jsonArray
												.getJSONObject(i).getString(
														"price"));
										historyBase
												.setDrop_location_address(jsonArray
														.getJSONObject(i)
														.getString(
																"drop_location_address"));
										historyBase.setOrderID(jsonArray
												.getJSONObject(i).getString(
														"order_id"));
										historyBase.setTimeDifference(jsonArray
												.getJSONObject(i).getString(
														"time_difference"));
										historyBase.setDeliveredTime(Data
												.utcToLocal(jsonArray
														.getJSONObject(i)
														.getString(
																"delivered_time")));
										Log.e("Data.utcToLocal",
												Data.utcToLocal(jsonArray
														.getJSONObject(i)
														.getString(
																"delivered_time")));
										historyBase.setTotalMeals(jsonArray
												.getJSONObject(i).getString(
														"total_meals"));
										detailsList = new ArrayList<DetailsBase>();
										JSONArray jsonArray2 = jsonArray
												.getJSONObject(i).getJSONArray(
														"details");
										for (int j = 0; j < jsonArray2.length(); j++) {

											DetailsBase detailsBase = new DetailsBase();
											detailsBase.setMealCode(jsonArray2
													.getJSONObject(j)
													.getString("meal_code"));
											detailsBase.setMealId(jsonArray2
													.getJSONObject(j)
													.getString("meal_id"));
											detailsBase.setMealImage(jsonArray2
													.getJSONObject(j)
													.getString("meal_image"));
											detailsBase.setMealName(jsonArray2
													.getJSONObject(j)
													.getString("meal_name"));
											detailsBase
													.setMealQuantity(jsonArray2
															.getJSONObject(j)
															.getString(
																	"meal_quantity"));
											JSONArray jsonArray3 = jsonArray2
													.getJSONObject(j)
													.getJSONArray(
															"topping_details");
											toppingList = new ArrayList<ToppingDetailsBase>();
											for (int k = 0; k < jsonArray3
													.length(); k++) {

												ToppingDetailsBase toppingDetailsBase = new ToppingDetailsBase();
												toppingDetailsBase
														.setTopping_id(jsonArray3
																.getJSONObject(
																		k)
																.getString(
																		"topping_id"));
												toppingDetailsBase
														.setTopping_image(jsonArray3
																.getJSONObject(
																		k)
																.getString(
																		"topping_image"));
												toppingDetailsBase
														.setTopping_name(jsonArray3
																.getJSONObject(
																		k)
																.getString(
																		"topping_name"));
												toppingDetailsBase
														.setTopping_quantity(jsonArray3
																.getJSONObject(
																		k)
																.getString(
																		"topping_quantity"));
												toppingList
														.add(toppingDetailsBase);
											}
											detailsBase.setTp(toppingList);

											detailsList.add(detailsBase);

										}
										historyBase.setDetails(detailsList);
										historyList.add(historyBase);
									}
								} catch (JSONException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								HistoryAdapterDriver historyAdapterDriver = new HistoryAdapterDriver(
										History.this, historyList);
								expandableListViewHistory
										.setAdapter(historyAdapterDriver);
							}
							}
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}

					@Override
					public void onFailure(Throwable arg0, String json) {
						// Data.loading_box_stop();
						Log.v("request fail", "response=" + json);
						new DialogPopup()
						.alertPopup(History.this, "",
								"Server not responding\nTry again.");
					}
				});

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
	public void onBackPressed() {
		if (linearLayoutMenuView.isShown()) {
			closeAnim();
		} else {
			super.onBackPressed();
			overridePendingTransition(R.anim.hold, R.anim.slide_out_right);
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
							new DialogPopup().alertPopup(History.this, "",
									GCMIntentService.stringMessage);
						}
						else if(Data.getInfoOf(getApplicationContext(), "OD").equals("19"))
						{
							Data.saveInfoOf(Data.activity,
									Data.CHECKINVALUE, "0");
							Intent intent = new Intent(History.this,
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
							new DialogPopup().alertPopup(History.this, "",
									GCMIntentService.stringMessage);
						}
						else if(GCMIntentService.flag.equals("16"))
						{
							new DialogPopup().alertPopup(History.this, "",
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
		OrderScreen.pushUpdater=null;
	}
	@Override
	protected void onDestroy() {
		super.onDestroy();
		Log.d("onDestroy", "onDestroy");
	}
	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		Utils.buildAlertMessageNoGps(History.this);
		super.onWindowFocusChanged(hasFocus);
	}
	
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		EasyTracker.getInstance(History.this).activityStart(this);
//		super.onStart();
	}
	
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		EasyTracker.getInstance(History.this).activityStop(this);
//		super.onStart();
	}
}
