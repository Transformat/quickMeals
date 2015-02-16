package com.bitekite.driver;
import java.util.ArrayList;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import rmn.androidscreenlibrary.ASSL;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bitekite.DriverLocationFinder;
import com.bitekite.GCMIntentService;
import com.bitekite.Log;
import com.bitekite.MainActivity;
import com.bitekite.R;
import com.bitekite.adpters.OrderDetailsDriverListAdapter;
import com.bitekite.adpters.OrderListAdapterDriver;
import com.bitekite.adpters.RemainingOrdersAdapter;
import com.bitekite.driver.classes.DetailsBase;
import com.bitekite.driver.classes.OrderBase;
import com.bitekite.driver.classes.ToppingDetailsBase;
import com.bitekite.utils.Data;
import com.bitekite.utils.DialogPopup;
import com.bitekite.utils.InternetCheck;
import com.google.analytics.tracking.android.EasyTracker;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class OrderScreen extends Activity implements PushUpdater {

	public static PushUpdater pushUpdater;

	ProgressBar progressBar;
	Button checkIn;
	TextView textViewStatus, textViewName, textViewNoOrderToday;
	ImageView imageViewStatusBackground, imageViewStatusIcon, imageViewMenu,
			imageViewNoOrderTodayIcon;

	ListView listViewOrderDetails; //, listViewOrder
	TextView textViewUserName, textViewAddress, textViewMeals,
			textViewCommingOrdres;
	Button buttonDeliver;

	RelativeLayout relativeLayoutOrder, relativeLayoutDeliveredOrder,
			relativeLayoutPerformance, relativeLayoutAccount,
			relativeLayoutSupport, relativeLayoutEmergency,
			relativeLayoutCheckout, relativeLayoutLogout;
	
	ScrollView scrollView;
	

	RelativeLayout relativeOrderLayout;
	public static ArrayList<OrderBase> orderList = new ArrayList<OrderBase>();;

	// Boolean Data.startAnim=true;

	RelativeLayout relativeLayoutMenu, relativeLayoutHeader,
			relativeLayoutMain, relativeLayoutHidden, relativeLayoutStatus,
			relativeLayoutMainHome;
	LinearLayout linearLayoutMenuView;
	
	ExpandableListView expandableListViewRemaining;

	TextView textViewOrder, textViewDeliveredOrder, textViewPerformance,
			textViewAccount, textViewSupport, textViewEmergency,
			textViewCheckout, textViewLogout;

	Button buttonCall;
	public static String callnum;
	int TOTALHEIGHT=0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_take_order_driver);

		pushUpdater = this;
		Data.saveInfoOf(getApplicationContext(), Data.loginFrom, "driver");
		new ASSL(this, (ViewGroup) findViewById(R.id.root), 1134, 720, false);
		Data.activity = this;
		Data.saveInfoOf(getApplicationContext(), "BASE_URL",
				Httppost_Links.Baselink);
		
		
		expandableListViewRemaining = (ExpandableListView) findViewById(R.id.RemainingExpandableListView1);
		scrollView = (ScrollView) findViewById(R.id.scrollView1);
		imageViewMenu = (ImageView) findViewById(R.id.menuImg);
		progressBar = (ProgressBar) findViewById(R.id.progressBar1);
		textViewStatus = (TextView) findViewById(R.id.status);
		checkIn = (Button) findViewById(R.id.checkIn);
		imageViewStatusBackground = (ImageView) findViewById(R.id.statusImg);
		imageViewStatusIcon = (ImageView) findViewById(R.id.statusIconImg);
		textViewName = (TextView) findViewById(R.id.uswrNameTxt);
		relativeLayoutStatus = (RelativeLayout) findViewById(R.id.statusLayout);
		relativeLayoutMainHome = (RelativeLayout) findViewById(R.id.mainHomelayout);
		relativeOrderLayout = (RelativeLayout) findViewById(R.id.orderlayout);
		listViewOrderDetails = (ListView) findViewById(R.id.listViewOrderDetails);
//		listViewOrder = (ListView) findViewById(R.id.listViewOrder);
		textViewAddress = (TextView) findViewById(R.id.AddressTxt);
		textViewMeals = (TextView) findViewById(R.id.mealsTxt);
		textViewUserName = (TextView) findViewById(R.id.UserNameTxt);
		textViewCommingOrdres = (TextView) findViewById(R.id.ComingOrderTxt);
		buttonDeliver = (Button) findViewById(R.id.DeliverBtn);
		relativeLayoutHidden = (RelativeLayout) findViewById(R.id.hidden);
		relativeLayoutMenu = (RelativeLayout) findViewById(R.id.menuBtnLayout);
		relativeLayoutHeader = (RelativeLayout) findViewById(R.id.header);
		relativeLayoutMain = (RelativeLayout) findViewById(R.id.mainlayout);
		relativeLayoutLogout = (RelativeLayout) findViewById(R.id.LogoutLayout);
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
		textViewLogout = (TextView) findViewById(R.id.LogoutTxt);
		textViewLogout.setTypeface(Data.omnes_Medium(getApplicationContext()));
		textViewCheckout = (TextView) findViewById(R.id.CheckOutTxt);
		textViewOrder = (TextView) findViewById(R.id.TodayOrderTxt);
		textViewDeliveredOrder = (TextView) findViewById(R.id.DeliveredOrderTxt);
		textViewPerformance = (TextView) findViewById(R.id.PerformanceTxt);
		textViewAccount = (TextView) findViewById(R.id.AccountTxt);
		textViewSupport = (TextView) findViewById(R.id.SupportTxt);
		textViewEmergency = (TextView) findViewById(R.id.EmergencyTxt);
		textViewLogout = (TextView) findViewById(R.id.LogoutTxt);
		buttonCall=(Button)findViewById(R.id.buttonCall);
	

		imageViewNoOrderTodayIcon.setVisibility(View.INVISIBLE);
		textViewNoOrderToday.setVisibility(View.INVISIBLE);
		
		textViewStatus.setTypeface(Data.bariol_bold(getApplicationContext()));
		textViewName.setTypeface(Data.bariol_bold(getApplicationContext()));
		buttonDeliver.setTypeface(Data.bariol_regular(getApplicationContext()));
		textViewCommingOrdres.setTypeface(Data.bariol_bold(getApplicationContext()));
		textViewAddress.setTypeface(Data.bariol_bold(getApplicationContext()));
		textViewMeals.setTypeface(Data.bariol_regular(getApplicationContext()));
		textViewUserName.setTypeface(Data.bariol_bold(getApplicationContext()));
		checkIn.setTypeface(Data.bariol_regular(getApplicationContext()));
		textViewNoOrderToday.setTypeface(Data.bariol_bold(getApplicationContext()));
		
		textViewLogout.setTypeface(Data.bariol_bold(getApplicationContext()));
		textViewCheckout.setTypeface(Data.bariol_bold(getApplicationContext()));
		textViewOrder.setTypeface(Data.bariol_bold(getApplicationContext()));
		textViewDeliveredOrder.setTypeface(Data.bariol_bold(getApplicationContext()));
		textViewPerformance.setTypeface(Data.bariol_bold(getApplicationContext()));
		textViewAccount.setTypeface(Data.bariol_bold(getApplicationContext()));
		textViewSupport.setTypeface(Data.bariol_bold(getApplicationContext()));
		textViewEmergency.setTypeface(Data.bariol_bold(getApplicationContext()));
		buttonCall.setTypeface(Data.bariol_bold(getApplicationContext()));

		
		setData(0);
	
		buttonCall.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				
				Intent phoneCallIntent = new Intent(Intent.ACTION_CALL);
				phoneCallIntent.setData(Uri.parse("tel:"+callnum));
				startActivity(phoneCallIntent);
			}
		});
	
		buttonDeliver.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				// Data.saveInfoOf(getApplicationContext(),Data.ORDERID,orderList.get(0).getOrderID());
				// Data.saveInfoOf(getApplicationContext(),Data.CURRENT_ORDER_LATITUDE,orderList.get(0).getDropLatitude().toString());
				// Data.saveInfoOf(getApplicationContext(),Data.CURRENT_ORDER_LONGITUDE,orderList.get(0).getDropLongitude().toString());
				// Data.saveInfoOf(getApplicationContext(),Data.CURRENT_NUMBER_OF_MEALS,orderList.get(0).getTotalMeals().toString());
				// Data.saveInfoOf(getApplicationContext(),Data.CURRENT_ORDER_CUSTOMER_NAME,orderList.get(0).getCustomer_name().toString());
				// Data.saveInfoOf(getApplicationContext(),Data.CURRENT_ORDER_ADDRESS,orderList.get(0).getDrop_location_address().toString());
				// Data.saveInfoOf(getApplicationContext(),Data.ORDERID,orderList.get(0).getOrderID());
				// Intent intent = new Intent(OrderScreen.this,
				// OrderDelivered.class);
				// startActivity(intent);finish();
				// overridePendingTransition(R.anim.slide_in_right,
				// R.anim.hold);
				//
				
				startOrderServerCall();
				
				
				
				
//				String uri = String.format(Locale.ENGLISH, "http://maps.google.com/maps?daddr=%f,%f (%s)",  30.718828, 76.8101049, "Where the party is at");
//				Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
//				intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
//				startActivity(intent);

			}
		});
//		setData(0);
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
				
				closeAnim();
			}
		});
		relativeLayoutDeliveredOrder.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (Data.getInfoOf(getApplicationContext(), Data.FLAG_REQUEST)
						.equalsIgnoreCase("21")) {
					Intent intent = new Intent(OrderScreen.this, History.class);
					startActivity(intent);
					finish();
					overridePendingTransition(R.anim.slide_in_right, R.anim.hold);
				} else {
					
					closeAnim();
				}
				
				
			}
		});

		relativeLayoutPerformance.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				
				if (Data.getInfoOf(getApplicationContext(), Data.FLAG_REQUEST)
						.equalsIgnoreCase("21")) {
					Intent intent = new Intent(OrderScreen.this,
							PerformanceDriver.class);
					startActivity(intent);
					finish();
					overridePendingTransition(R.anim.slide_in_right, R.anim.hold);
				} else {
					
					closeAnim();
				}
				
				
			}
		});

		relativeLayoutAccount.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				
				if (Data.getInfoOf(getApplicationContext(), Data.FLAG_REQUEST)
						.equalsIgnoreCase("21")) {
					Intent intent = new Intent(OrderScreen.this,
							AccountDriver.class);
					startActivity(intent);
					finish();
					overridePendingTransition(R.anim.slide_in_right, R.anim.hold);
				} else {
					
					closeAnim();
				}
				
				

			}
		});
		relativeLayoutEmergency.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				
				if (Data.getInfoOf(getApplicationContext(), Data.FLAG_REQUEST)
						.equalsIgnoreCase("21")) {
					Intent intent = new Intent(OrderScreen.this,
							EmergencyDriver.class);
					startActivity(intent);
					finish();
					overridePendingTransition(R.anim.slide_in_right, R.anim.hold);
				} else {
					
					closeAnim();
				}
				
				
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

//				Intent intent = new Intent(OrderScreen.this,
//						SupportDriver.class);
//				startActivity(intent);
//				finish();
//				overridePendingTransition(R.anim.slide_in_right, R.anim.hold);

			}
		});

		relativeLayoutCheckout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				
				if (Data.getInfoOf(getApplicationContext(), Data.FLAG_REQUEST)
						.equalsIgnoreCase("21")) {
					if (InternetCheck.getInstance(getApplicationContext()).isOnline(
							getApplicationContext())) {
						chekOutServerCall();
					} else {
						new DialogPopup().alertPopup(OrderScreen.this,
								"", "Check your internet connection");
					}
					closeAnim();
				} else {
					
					closeAnim();
				}
				
			}
		});

		relativeLayoutLogout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Utils.logout();
			}
		});
		setData(3);

		if (!getIntent().hasExtra("checkout")) {
			if (Data.getInfoOf(getApplicationContext(), Data.CHECKINVALUE)
					.equalsIgnoreCase("0")) {
				setData(0);

			} else {

				if (InternetCheck.getInstance(getApplicationContext()).isOnline(
						getApplicationContext())) {
					orderServerCall();
				} else {
					new DialogPopup().alertPopup(OrderScreen.this,
							"", "Check your internet connection");
				}
			}
		} else {
			if (InternetCheck.getInstance(getApplicationContext()).isOnline(
					getApplicationContext())) {
				chekOutServerCall();
			} else {
				new DialogPopup().alertPopup(OrderScreen.this,
						"", "Check your internet connection");
			}
		}
		
		if (getIntent().hasExtra("checkout_admin")) {
			setData(0);
			new DialogPopup().alertPopup(OrderScreen.this,
					"", "Your shift is complete. Thank you, have a good day!");
		}
		
		
		expandableListViewRemaining.setOnGroupExpandListener(new OnGroupExpandListener() {
			
			@Override
			public void onGroupExpand(int groupPosition) {
				// TODO Auto-generated method stub
						
				setheight(expandableListViewRemaining,groupPosition,140);
				
			}
		});
		
		expandableListViewRemaining.setOnGroupCollapseListener(new OnGroupCollapseListener() {
			
			@Override
			public void onGroupCollapse(int groupPosition) {
				// TODO Auto-generated method stub
				TOTALHEIGHT=TOTALHEIGHT - (orderList.get(groupPosition).getDetails().size() * 150);
				Log.d("TOTALHEIGHT","TOTALHEIGHT"+TOTALHEIGHT);
				setheight(expandableListViewRemaining, groupPosition,0);
				
			}
		});
		
		

	}

	private void orderServerCall() {

		// Data.loading_box(this, "Loading...");

		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();

		params.put("access_token", Data.getInfoOf(this, Data.ACCESS_TOKEN));
		client.setTimeout(Httppost_Links.SERVER_TIME_OUT_TIME);
		client.post(Httppost_Links.ORDER, params,
				new AsyncHttpResponseHandler() {

					@Override
					public void onSuccess(String response_str) {
						// Data.loading_box_stop();
						Log.v("Order Api", "=" + response_str);
						//

						try {
							JSONObject jb = new JSONObject(response_str);
							Log.v("Order Api", "=" + jb.toString(2));

							if(jb.getString("status").equals("2"))
							{
//								Data.alertMessageBox(SplashScreen.this,popup.getString("title"),popup.getString("text"),Data.getInfoOf(getApplicationContext(), Data.loginFrom));
							Intent intent = new Intent(OrderScreen.this,MainActivity.class);
							startActivity(intent);
							finish();
							}
							else
							{
								Log.d("No order found  1  ","No order found  1   " + jb.getJSONArray("data").length());
							if (jb.getJSONArray("data").length() == 0) {
								// Data.getAlertDialogWithbothMessage(
								// OrderScreen.this, "OOPS!",
								// "No order found!");
								orderList.clear();
								
								Log.d("No order found  2   ","No order found   2  ");
								
								relativeLayoutHidden.setVisibility(View.VISIBLE);
								imageViewNoOrderTodayIcon
										.setVisibility(View.VISIBLE);
								textViewNoOrderToday
										.setVisibility(View.VISIBLE);
								progressBar.setVisibility(View.INVISIBLE);
							} else {

								orderList.clear();
								ArrayList<DetailsBase> detailsList = null;
								ArrayList<ToppingDetailsBase> toppingList;

								for (int i = 0; i < jb.getJSONArray("data")
										.length(); i++) {

									OrderBase orderBaseObject = new OrderBase();
									JSONArray jsonArray = jb
											.getJSONArray("data");
									orderBaseObject.setCustomer_name(jsonArray
											.getJSONObject(i).getString(
													"customer_name"));
									orderBaseObject.setPrice(jsonArray
											.getJSONObject(i)
											.getString("price"));
									
									orderBaseObject
											.setDrop_location_address(jsonArray
													.getJSONObject(i)
													.getString(
															"drop_location_address"));
									orderBaseObject.setOrderID(jsonArray
											.getJSONObject(i).getString(
													"order_id"));
									orderBaseObject.setDeliveryTime(jsonArray
											.getJSONObject(i).getString(
													"delivery_time"));
									orderBaseObject.setDropLatitude(jsonArray
											.getJSONObject(i).getString(
													"drop_latitude"));
									orderBaseObject.setDropLongitude(jsonArray
											.getJSONObject(i).getString(
													"drop_longitude"));
									orderBaseObject.setDistance(jsonArray
											.getJSONObject(i).getString(
													"distance"));
									orderBaseObject.setTimeToDeliver(jsonArray
											.getJSONObject(i).getString(
													"time_to_deliver"));
									orderBaseObject.setTotalMeals(jsonArray
											.getJSONObject(i).getString(
													"total_meals"));

									orderBaseObject.setPhone_no(jsonArray
											.getJSONObject(i).getString(
													"phone_no"));

									
									detailsList = new ArrayList<DetailsBase>();
									JSONArray jsonArray2 = jsonArray
											.getJSONObject(i).getJSONArray(
													"details");
									for (int j = 0; j < jsonArray2.length(); j++) {

										DetailsBase detailsBase = new DetailsBase();
										detailsBase.setMealCode(jsonArray2
												.getJSONObject(j).getString(
														"meal_code"));
										detailsBase.setMealId(jsonArray2
												.getJSONObject(j).getString(
														"meal_id"));
										detailsBase.setMealImage(jsonArray2
												.getJSONObject(j).getString(
														"meal_image"));
										detailsBase.setMealName(jsonArray2
												.getJSONObject(j).getString(
														"meal_name"));
										detailsBase.setMealQuantity(jsonArray2
												.getJSONObject(j).getString(
														"meal_quantity"));
										JSONArray jsonArray3 = jsonArray2
												.getJSONObject(j).getJSONArray(
														"topping_details");
										toppingList = new ArrayList<ToppingDetailsBase>();
										for (int k = 0; k < jsonArray3.length(); k++) {

											ToppingDetailsBase toppingDetailsBase = new ToppingDetailsBase();
											toppingDetailsBase
													.setTopping_id(jsonArray3
															.getJSONObject(k)
															.getString(
																	"topping_id"));
											toppingDetailsBase
													.setTopping_image(jsonArray3
															.getJSONObject(k)
															.getString(
																	"topping_image"));
											toppingDetailsBase
													.setTopping_name(jsonArray3
															.getJSONObject(k)
															.getString(
																	"topping_name"));
											toppingDetailsBase
													.setTopping_quantity(jsonArray3
															.getJSONObject(k)
															.getString(
																	"topping_quantity"));
											toppingList.add(toppingDetailsBase);
										}
										detailsBase.setTp(toppingList);

										detailsList.add(detailsBase);

									}
									orderBaseObject.setDetails(detailsList);
									orderList.add(orderBaseObject);
								}

//								Log.i("orderList", "=="
//										+ orderList.get(0).getDetails().get(0)
//												.getTp().get(0)
//												.getTopping_name().toString());
								
								
								
								callnum = orderList.get(0).getPhone_no();
								textViewAddress.setText(orderList.get(0)
										.getDrop_location_address());
								if (orderList.get(0).getTotalMeals()
										.equalsIgnoreCase("1")) {
									textViewMeals.setText(orderList.get(0)
											.getTotalMeals() + " meal");
								} else {
									textViewMeals.setText(orderList.get(0)
											.getTotalMeals() + " meals");
								}

								textViewUserName.setText(orderList.get(0)
										.getCustomer_name());
								
//								buttonCall.setText(""+callnum);

								
								
								OrderDetailsDriverListAdapter orderDetailsDriverListAdapter = new OrderDetailsDriverListAdapter(
										OrderScreen.this, orderList);
								listViewOrderDetails
										.setAdapter(orderDetailsDriverListAdapter);

								setListSize(listViewOrderDetails,140);
								
								OrderListAdapterDriver orderListAdapter = new OrderListAdapterDriver(
										OrderScreen.this, orderList);
//								listViewOrder.setAdapter(orderListAdapter);
//								setListSize(listViewOrder,80);
								
								RemainingOrdersAdapter remainingOrdersAdapter = new RemainingOrdersAdapter(
										OrderScreen.this, orderList);
								expandableListViewRemaining.setAdapter(remainingOrdersAdapter);
								setListSize(expandableListViewRemaining,150);
								
								setData(1);
								if (orderList.size() > 0) {
									relativeLayoutLogout
											.setVisibility(View.GONE);
									relativeLayoutCheckout
											.setVisibility(View.GONE);

								}
								
								scrollView.fullScroll(ScrollView.FOCUS_UP);
							}
							}
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						scrollView.fullScroll(ScrollView.FOCUS_UP);
					}

					@Override
					public void onFailure(Throwable arg0, String json) {
						// Data.loading_box_stop();
						Log.v("request fail", "response=" + json);

						new DialogPopup().alertPopup(OrderScreen.this, "",
								"Server not responding\nTry again.");

					}
				});

	}

	private void chekInServerCall() {
		setData(3);

		// Data.loading_box(this, "Loading...");
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();

		Log.e("Access token", "," + Data.getInfoOf(this, Data.ACCESS_TOKEN));

		params.put("access_token", Data.getInfoOf(this, Data.ACCESS_TOKEN));
		client.setTimeout(Httppost_Links.SERVER_TIME_OUT_TIME);
		client.post(Httppost_Links.CHEKIN, params,
				new AsyncHttpResponseHandler() {
					@Override
					public void onSuccess(String response) {
						// Data.loading_box_stop();
						Log.v("checkin Api", "=" + response);

						JSONObject res;
						try {
							res = new JSONObject(response);
							Log.d("response", "response" + res.toString(2));
							
							if(res.getString("status").equals("2"))
							{
//								Data.alertMessageBox(SplashScreen.this,popup.getString("title"),popup.getString("text"),Data.getInfoOf(getApplicationContext(), Data.loginFrom));
							Intent intent = new Intent(OrderScreen.this,MainActivity.class);
							startActivity(intent);
							finish();
							}
							else
							{
							if (res.has("error")) {
								Log.d("Data.SERVER_ERROR_MSG",
										"Data.SERVER_ERROR_MSG"
												+ res.getString("error"));
								Data.getAlertDialogWithbothMessage(
										OrderScreen.this, "OOPS!",
										res.getString("error"));
							} else {
								// checkIn.setText("Check out");
								startService(new Intent(Data.activity,
										MyService.class));
								Data.saveInfoOf(getApplicationContext(),
										Data.CHECKINVALUE, "1");
								// relativeLayoutCheckout.setVisibility(View.VISIBLE);
								orderServerCall();
							}
							}
						} catch (JSONException e) {
							e.printStackTrace();
						}

					}

					@Override
					public void onFailure(Throwable arg0, String json) {
						// Data.loading_box_stop();
						Log.v("request fail", "response=" + json);
					}
				});

	}

	@Override
	public void onResume() {
		super.onResume();
		Data.activity = this;
		pushUpdater = this;
		Log.e("onResume", "onResume"+(Data.getInfoOf(getApplicationContext(), "OD")));
		if(Data.getInfoOf(getApplicationContext(), "OD").equals("19"))
		{
			pushUpdater();
				
		}
		
		if (InternetCheck.getInstance(getApplicationContext()).isOnline(
				getApplicationContext())) {
			try {
				DriverLocationFinder.cancelNotification(getApplicationContext(), 0);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			orderServerCall();
		} else {
			new DialogPopup().alertPopup(OrderScreen.this,
					"", "Check your internet connection");
		}
		

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

	public void chekOutServerCall() {
		setData(3);
		// / relativeLayoutHidden.setVisibility(View.VISIBLE);
		// Data.loading_box(Data.activity, "Loading...");
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();

		Log.e("Access token",
				"," + Data.getInfoOf(Data.activity, Data.ACCESS_TOKEN));

		params.put("access_token",
				Data.getInfoOf(Data.activity, Data.ACCESS_TOKEN));
		client.setTimeout(Httppost_Links.SERVER_TIME_OUT_TIME);
		client.post(Httppost_Links.CHECKOUT, params,
				new AsyncHttpResponseHandler() {
					@Override
					public void onSuccess(String response) {
						// Data.loading_box_stop();
						Log.v("checkin Api", "=" + response);

						JSONObject res;
						try {

							res = new JSONObject(response);
							if(res.getString("status").equals("2"))
							{
//								Data.alertMessageBox(SplashScreen.this,popup.getString("title"),popup.getString("text"),Data.getInfoOf(getApplicationContext(), Data.loginFrom));
							Intent intent = new Intent(OrderScreen.this,MainActivity.class);
							startActivity(intent);
							finish();
							}
							else
							{
							Log.d("response", "response" + res.toString(2));
							if (res.has("error")) {
								Log.d("Data.SERVER_ERROR_MSG",
										"Data.SERVER_ERROR_MSG"
												+ res.getString("error"));
								Data.getAlertDialogWithbothMessage(
										Data.activity, "OOPS!",
										res.getString("error"));
							} else {

								Data.saveInfoOf(Data.activity,
										Data.CHECKINVALUE, "0");
								setData(0);
								stopService(new Intent(Data.activity,
										MyService.class));

							}
							}
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}

					@Override
					public void onFailure(Throwable arg0, String json) {
						// Data.loading_box_stop();
						Log.v("request fail", "response=" + json);
					}
				});

	}

	void setData(int state) {
		relativeLayoutHidden.setVisibility(View.VISIBLE);
		relativeOrderLayout.setVisibility(View.INVISIBLE);
		relativeLayoutMainHome.setVisibility(View.INVISIBLE);
		relativeLayoutCheckout.setVisibility(View.VISIBLE);
		relativeLayoutLogout.setVisibility(View.INVISIBLE);
		progressBar.setVisibility(View.VISIBLE);
		imageViewNoOrderTodayIcon.setVisibility(View.INVISIBLE);
		textViewNoOrderToday.setVisibility(View.INVISIBLE);

		switch (state) {
		case 0:
			relativeLayoutHidden.setVisibility(View.INVISIBLE);
			relativeLayoutMainHome.setVisibility(View.VISIBLE);
			relativeLayoutCheckout.setVisibility(View.GONE);
			relativeLayoutLogout.setVisibility(View.VISIBLE);
			// flag:20 --- driver not yet verified from admin
			// flag:21--- driver verified by admin
			// flag:22-- admin rejected the driver verification

			textViewName.setText(Data.getInfoOf(getApplicationContext(),
					Data.USER_NAME));
			if (Data.getInfoOf(getApplicationContext(), Data.ACTIVATEFLAG)
					.equalsIgnoreCase("")) {
				if (Data.getInfoOf(getApplicationContext(), Data.FLAG_REQUEST)
						.equalsIgnoreCase("20")) {
					textViewStatus.setText("Request Pending");
					checkIn.setTextColor(Color.GRAY);
					imageViewStatusIcon
							.setBackgroundResource(R.drawable.ic_pending_icon);
					imageViewStatusBackground
							.setBackgroundResource(R.drawable.ic_pending_status_bg);

				} else if (Data.getInfoOf(getApplicationContext(),
						Data.FLAG_REQUEST).equalsIgnoreCase("21")) {
					textViewStatus.setText("Registered as driver.");
					checkIn.setTextColor(Color.WHITE);
					imageViewStatusIcon
							.setBackgroundResource(R.drawable.ic_registered_icon);
					imageViewStatusBackground
							.setBackgroundResource(R.drawable.ic_registered_status);
					Data.saveInfoOf(getApplicationContext(), Data.ACTIVATEFLAG,
							"1");
				} else if (Data.getInfoOf(getApplicationContext(),
						Data.FLAG_REQUEST).equalsIgnoreCase("22")) {
					textViewStatus.setText("Request Rejected");
					checkIn.setTextColor(Color.GRAY);
					imageViewStatusIcon
							.setBackgroundResource(R.drawable.ic_disapproved_icon);
					imageViewStatusBackground
							.setBackgroundResource(R.drawable.ic_disapproved_status);
				}
			} else {
				relativeLayoutStatus.setVisibility(View.INVISIBLE);
			}

			if (Data.getInfoOf(getApplicationContext(), Data.FLAG_REQUEST)
					.equalsIgnoreCase("21")) {

				relativeLayoutMenu.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						if (Data.startAnim) {
							openAnim();
						}

					}
				});

				checkIn.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						if (InternetCheck.getInstance(getApplicationContext()).isOnline(
								getApplicationContext())) {
							chekInServerCall();
						} else {
							new DialogPopup().alertPopup(OrderScreen.this,
									"", "Check your internet connection");
						}
						

					}
				});
			}

			break;

		case 1:
			relativeLayoutHidden.setVisibility(View.INVISIBLE);
			relativeOrderLayout.setVisibility(View.VISIBLE);

			break;

		default:
			break;
		}

	}

	public void startOrderServerCall() {
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();

		Log.e("Access token",
				"," + Data.getInfoOf(Data.activity, Data.ACCESS_TOKEN));

		params.put("access_token",
				Data.getInfoOf(Data.activity, Data.ACCESS_TOKEN));
		params.put("order_id", orderList.get(0).getOrderID());
		client.setTimeout(Httppost_Links.SERVER_TIME_OUT_TIME);
		client.post(Httppost_Links.START_ORDER, params,
				new AsyncHttpResponseHandler() {
					@Override
					public void onSuccess(String response) {
						// Data.loading_box_stop();
						Log.v("checkin Api", "=" + response);

						JSONObject res;
						try {

							res = new JSONObject(response);
							Log.d("response", "response" + res.toString(2));
							
							if(res.getString("status").equals("2"))
							{
//								Data.alertMessageBox(SplashScreen.this,popup.getString("title"),popup.getString("text"),Data.getInfoOf(getApplicationContext(), Data.loginFrom));
							Intent intent = new Intent(OrderScreen.this,MainActivity.class);
							startActivity(intent);
							finish();
							}
							else
							{
							if (res.has("error")) {
								Log.d("Data.SERVER_ERROR_MSG",
										"Data.SERVER_ERROR_MSG"
												+ res.getString("error"));
								Data.getAlertDialogWithbothMessage(
										Data.activity, "OOPS!",
										res.getString("error"));
							} else {
								// Data.saveInfoOf(getApplicationContext(),Data.START_ORDER_DETAILS,response);
								Data.saveInfoOf(getApplicationContext(),
										Data.ORDERID, orderList.get(0)
												.getOrderID());
								// Data.saveInfoOf(getApplicationContext(),Data.CURRENT_ORDER_LATITUDE,orderList.get(0).getDropLatitude().toString());
								// Data.saveInfoOf(getApplicationContext(),Data.CURRENT_ORDER_LONGITUDE,orderList.get(0).getDropLongitude().toString());
								// Data.saveInfoOf(getApplicationContext(),Data.CURRENT_NUMBER_OF_MEALS,orderList.get(0).getTotalMeals().toString());
								// Data.saveInfoOf(getApplicationContext(),Data.CURRENT_ORDER_CUSTOMER_NAME,orderList.get(0).getCustomer_name().toString());
								// Data.saveInfoOf(getApplicationContext(),Data.CURRENT_ORDER_ADDRESS,orderList.get(0).getDrop_location_address().toString());
								Intent intent = new Intent(OrderScreen.this,
										OrderDelivered.class);
								startActivity(intent);
								overridePendingTransition(
										R.anim.slide_in_right, R.anim.hold);
								finish();

							}
							}
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}

					@Override
					public void onFailure(Throwable arg0, String json) {
						// Data.loading_box_stop();
						Log.v("request fail", "response=" + json);
					}
				});

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
							if (InternetCheck.getInstance(getApplicationContext()).isOnline(
									getApplicationContext())) {
								orderServerCall();
							} else {
								new DialogPopup().alertPopup(OrderScreen.this,
										"", "Check your internet connection");
							}
							new DialogPopup().alertPopup(OrderScreen.this, "",
									GCMIntentService.stringMessage);
						}
						else if(Data.getInfoOf(getApplicationContext(), "OD").equals("19"))
						{
							Data.saveInfoOf(Data.activity,
									Data.CHECKINVALUE, "0");
							setData(0);
							stopService(new Intent(Data.activity,
									MyService.class));
							new DialogPopup().alertPopup(OrderScreen.this,
									"", GCMIntentService.stringMessage);
						}
						else if(GCMIntentService.flag.equals("25"))
						{
							new DialogPopup().alertPopup(OrderScreen.this, "",
									GCMIntentService.stringMessage);
							Data.saveInfoOf(Data.activity,
									Data.CHECKINVALUE, "1");
						}
						else if(GCMIntentService.flag.equals("43"))
						{
							Data.saveInfoOf(getApplicationContext(),Data.FLAG_REQUEST,"22");
							textViewStatus.setText("Request Rejected");
							checkIn.setTextColor(Color.GRAY);
							imageViewStatusIcon
									.setBackgroundResource(R.drawable.ic_disapproved_icon);
							imageViewStatusBackground
									.setBackgroundResource(R.drawable.ic_disapproved_status);
							setData(0);
							new DialogPopup().alertPopup(OrderScreen.this, "",
									GCMIntentService.stringMessage);
						}
						else if(GCMIntentService.flag.equals("16"))
						{
							if (InternetCheck.getInstance(getApplicationContext()).isOnline(
									getApplicationContext())) {
								orderServerCall();
							} else {
								new DialogPopup().alertPopup(OrderScreen.this,
										"", "Check your internet connection");
							}
							new DialogPopup().alertPopup(OrderScreen.this, "",
									GCMIntentService.stringMessage);
						}
						else if(GCMIntentService.flag.equals("42"))
						{
							textViewStatus.setText("Registered as driver.");
							imageViewStatusIcon.setBackgroundResource(R.drawable.ic_registered_icon);
							imageViewStatusBackground.setBackgroundResource(R.drawable.ic_registered_status);
							checkIn.setTextColor(Color.WHITE);
							Data.saveInfoOf(getApplicationContext(), Data.ACTIVATEFLAG,
									"1");
							Data.saveInfoOf(getApplicationContext(),Data.FLAG_REQUEST,"21");
							setData(0);
							new DialogPopup().alertPopup(OrderScreen.this, "",
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

	@Override
	protected void onDestroy() {
		super.onDestroy();
		Log.d("onDestroy", "onDestroy");
	}
	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		Utils.buildAlertMessageNoGps(OrderScreen.this);
		super.onWindowFocusChanged(hasFocus);
	}
	
	
	void setListSize(ListView listView, int height)
	{
		ListAdapter listadap = listView.getAdapter();
		int totalHeight1 = 0;

		for (int j = 0; j < listadap.getCount(); j++) {
			totalHeight1 += (int) (height * ASSL.Yscale());
		}

		ViewGroup.LayoutParams params1 = listView.getLayoutParams();
		params1.height = totalHeight1
				+ (listView.getDividerHeight() * (listadap.getCount() - 1));
		listView.setLayoutParams(params1);
		listView.requestLayout();
	
	}
//	public void setheight(ExpandableListView list, int groupPosition,int height){
//	try {
//		if (list.getCount() > 0) {
//		ExpandableListAdapter  listAdap = list.getExpandableListAdapter();
//		int totalHeight = 0;
//
////		View listItem = listAdap.getView(1, null, list);
////		listItem.measure(0, 0);
////		int singleHeight = listItem.getMeasuredHeight();
////		totalHeight = singleHeight * list.getCount();
//
//			for (int i = 0; i < listAdap.getChildrenCount(groupPosition)Count(); i++) {
////			View listItem = listAdap.getChildView(groupPosition, childPosition, false, convertView, list);
////			listItem.measure(0, 0);
//			totalHeight += listItem.getMeasuredHeight();
//			}
//		ViewGroup.LayoutParams params = list.getLayoutParams();
//		params.height = totalHeight + (list.getDividerHeight() * (list.getCount() - 1));
//		list.setLayoutParams(params);
//		list.requestLayout();
//		}
//		} catch (Exception e) {
//		Log.e("Exception in expanding list ", "," + e.toString());
//		}
//	}
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		EasyTracker.getInstance(OrderScreen.this).activityStart(this);
//		super.onStart();
	}
	
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		EasyTracker.getInstance(OrderScreen.this).activityStop(this);
//		super.onStart();
	}
	public void setheight(ExpandableListView list, int groupPosition,int height)
 {
		try {
			Log.d("setheight", "setheight");
			if (list.getCount() > 0) {
				ExpandableListAdapter listAdap = list
						.getExpandableListAdapter();
				int totalHeight = 0;

				// View listItem = listAdap.getChildView(groupPosition,
				// childPosition, false, convertView, parent);
				// listItem.measure(0, 0);
				// int singleHeight = listItem.getMeasuredHeight();
				// totalHeight = singleHeight * list.getCount();

				for (int i = 0; i < listAdap.getChildrenCount(groupPosition); i++) {
					// View listItem = listAdap.getGroupView(i, true, null,
					// list);
					// listItem.measure(0, 0);
					totalHeight += (int) (height * ASSL.Yscale());
					Log.d("height", "height" + totalHeight);
				}
				TOTALHEIGHT = TOTALHEIGHT + totalHeight;
				ViewGroup.LayoutParams params = list.getLayoutParams();
				params.height = ((orderList.size() - 1) * 160) + TOTALHEIGHT
						+ (list.getDividerHeight() * (list.getCount()));
//				Log.d("getCount","getCount" + (orderList.size() - 1)+ list.getDividerHeight()* (list.getCount() - 1));
				Log.d("params.height","params.height" +params.height);
				list.setLayoutParams(params);
				list.requestLayout();
			}
		} catch (Exception e) {
			Log.e("Exception in expanding list ", "," + e.toString());
		}
	}
}
