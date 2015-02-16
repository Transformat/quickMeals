package com.bitekite.driver;
//package com.ufmd.driver;
//
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import rmn.androidscreenlibrary.ASSL;
//import android.app.Activity;
//import android.content.Intent;
//import android.graphics.Color;
//import android.os.Bundle;
//import android.os.Handler;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.view.ViewGroup;
//import android.view.animation.Animation;
//import android.view.animation.AnimationUtils;
//import android.widget.Button;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//
//import com.loopj.android.http.AsyncHttpClient;
//import com.loopj.android.http.AsyncHttpResponseHandler;
//import com.loopj.android.http.RequestParams;
//import com.uber.utils.Data;
//import com.ufmd.Log;
//import com.ufmd.R;
//
//public class StatusDriver extends Activity {
//
//	public static Button checkIn;
//	private boolean checkinFlag;
//	TextView textViewStatus, textViewName;
//	ImageView imageViewStatusBackground, imageViewStatusIcon;
//	RelativeLayout relativeLayoutMenu, relativeLayoutHeader,
//			relativeLayoutMain, relativeLayoutStatus;
//
//	RelativeLayout relativeLayoutOrder, relativeLayoutDeliveredOrder,
//			relativeLayoutPerformance, relativeLayoutAccount,
//			relativeLayoutSupport, relativeLayoutEmergency,relativeLayoutCheckout;
//
//	LinearLayout linearLayoutMenuView;
//	Animation slideOutUp, slideOutBottom, slideOutUpBack, slideOutBottomBack;
//	
//	TextView textViewOrder, textViewDeliveredOrder,
//	textViewPerformance, textViewAccount,
//	textViewSupport, textViewEmergency,textViewCheckout;
//
//	@Override
//	public void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_status_driver);
//		new ASSL(this, (ViewGroup) findViewById(R.id.root), 1134, 720, false);
//		Data.activity = this;
//		textViewStatus = (TextView) findViewById(R.id.status);
//		checkIn = (Button) findViewById(R.id.checkIn);
//		imageViewStatusBackground = (ImageView) findViewById(R.id.statusImg);
//		imageViewStatusIcon = (ImageView) findViewById(R.id.statusIconImg);
//		textViewName = (TextView) findViewById(R.id.uswrNameTxt);
//		relativeLayoutStatus = (RelativeLayout) findViewById(R.id.statusLayout);
//		
//		
//		textViewStatus.setTypeface(Data.omnes_Regular(getApplicationContext()));
//		textViewName.setTypeface(Data.omnes_Medium(getApplicationContext()));
//		checkIn.setTypeface(Data.omnes_Medium(getApplicationContext()));
//
//		slideOutUp = AnimationUtils.loadAnimation(getApplicationContext(),
//				R.anim.slide_out_up);
//		slideOutBottom = AnimationUtils.loadAnimation(getApplicationContext(),
//				R.anim.slide_out_bottom);
//		slideOutUpBack = AnimationUtils.loadAnimation(getApplicationContext(),
//				R.anim.slide_out_up_back);
//		slideOutBottomBack = AnimationUtils.loadAnimation(
//				getApplicationContext(), R.anim.slide_out_bottom_back);
//
//		relativeLayoutMenu = (RelativeLayout) findViewById(R.id.menuBtnLayout);
//
//		relativeLayoutHeader = (RelativeLayout) findViewById(R.id.header);
//		relativeLayoutMain = (RelativeLayout) findViewById(R.id.mainlayout);
//
//		relativeLayoutOrder = (RelativeLayout) findViewById(R.id.TodayOrderLayout);
//		relativeLayoutDeliveredOrder = (RelativeLayout) findViewById(R.id.DeliveredOrderLayout);
//		relativeLayoutPerformance = (RelativeLayout) findViewById(R.id.PerformanceLayout);
//		relativeLayoutAccount = (RelativeLayout) findViewById(R.id.AccountLayout);
//		relativeLayoutSupport = (RelativeLayout) findViewById(R.id.SupportLayout);
//		relativeLayoutEmergency = (RelativeLayout) findViewById(R.id.EmergencyLayout);
//		linearLayoutMenuView = (LinearLayout) findViewById(R.id.menuView);
//		relativeLayoutCheckout = (RelativeLayout) findViewById(R.id.CheckOutLayout);
//		
//		textViewCheckout = (TextView) findViewById(R.id.CheckOutTxt);
//		textViewOrder = (TextView) findViewById(R.id.TodayOrderTxt);
//		textViewDeliveredOrder = (TextView) findViewById(R.id.DeliveredOrderTxt);
//		textViewPerformance = (TextView) findViewById(R.id.PerformanceTxt);
//		textViewAccount = (TextView) findViewById(R.id.AccountTxt);
//		textViewSupport = (TextView) findViewById(R.id.SupportTxt);
//		textViewEmergency = (TextView) findViewById(R.id.EmergencyTxt);
//		
//		textViewCheckout.setTypeface(Data.omnes_Medium(getApplicationContext()));
//		textViewOrder.setTypeface(Data.omnes_Medium(getApplicationContext()));
//		textViewDeliveredOrder.setTypeface(Data.omnes_Medium(getApplicationContext()));
//		textViewPerformance.setTypeface(Data.omnes_Medium(getApplicationContext()));
//		textViewAccount.setTypeface(Data.omnes_Medium(getApplicationContext()));
//		textViewSupport.setTypeface(Data.omnes_Medium(getApplicationContext()));
//		textViewEmergency.setTypeface(Data.omnes_Medium(getApplicationContext()));
//		
//
//		linearLayoutMenuView.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				relativeLayoutHeader.clearAnimation();
//				relativeLayoutMain.clearAnimation();
//				relativeLayoutHeader.startAnimation(slideOutUpBack);
//				relativeLayoutMain.startAnimation(slideOutBottomBack);
//				relativeLayoutHeader.setVisibility(View.VISIBLE);
//				relativeLayoutMain.setVisibility(View.VISIBLE);
//				Handler mHandler = new Handler();
//				mHandler.postDelayed(new Runnable() {
//					public void run() {
//						linearLayoutMenuView.setVisibility(View.INVISIBLE);
//					}
//				}, 500);
//
//			}
//		});
//
//		relativeLayoutOrder.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				Intent intent = new Intent(StatusDriver.this, OrderScreen.class);
//				startActivity(intent);
//				overridePendingTransition(R.anim.slide_in_right,
//						R.anim.hold);
//			}
//
//		});
//		relativeLayoutDeliveredOrder.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				Intent intent = new Intent(StatusDriver.this, History.class);
//				startActivity(intent);
//				overridePendingTransition(R.anim.slide_in_right,
//						R.anim.hold);
//			}
//		});
//
//		relativeLayoutPerformance.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				Intent intent = new Intent(StatusDriver.this,
//						PerformanceDriver.class);
//				startActivity(intent);
//				overridePendingTransition(R.anim.slide_in_right,
//						R.anim.hold);
//			}
//		});
//		
//		relativeLayoutAccount.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				Intent intent = new Intent(StatusDriver.this,
//						AccountDriver.class);
//				startActivity(intent);
//				overridePendingTransition(R.anim.slide_in_right,
//						R.anim.hold);
//				
//			}
//		});
//		relativeLayoutEmergency.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				Intent intent = new Intent(StatusDriver.this,
//						EmergencyDriver.class);
//				startActivity(intent);
//				overridePendingTransition(R.anim.slide_in_right,
//						R.anim.hold);
//			}
//		});
//		
//		relativeLayoutSupport.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				Intent intent = new Intent(StatusDriver.this,
//						SupportDriver.class);
//				startActivity(intent);
//				overridePendingTransition(R.anim.slide_in_right,
//						R.anim.hold);
//				
//			}
//		});
//		relativeLayoutCheckout.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				Utils.chekOutServerCall();
//			}
//		});
//
//		// flag:20 --- driver not yet verified from admin
//		// flag:21--- driver verified by admin
//		// flag:22-- admin rejected the driver verification
//
//		textViewName.setText(Data.getInfoOf(getApplicationContext(),
//				Data.USER_NAME));
//		if (Data.getInfoOf(getApplicationContext(), Data.ACTIVATEFLAG)
//				.equalsIgnoreCase("")) {
//			if (Data.getInfoOf(getApplicationContext(), Data.FLAG_REQUEST)
//					.equalsIgnoreCase("20")) {
//				checkinFlag = false;
//				textViewStatus.setText("Request Pending");
//				checkIn.setTextColor(Color.GRAY);
//				imageViewStatusIcon
//						.setBackgroundResource(R.drawable.ic_pending_icon);
//				imageViewStatusBackground
//						.setBackgroundResource(R.drawable.ic_pending_status_bg);
//
//			} else if (Data.getInfoOf(getApplicationContext(),
//					Data.FLAG_REQUEST).equalsIgnoreCase("21")) {
//				checkinFlag = true;
//				textViewStatus.setText("Registered as driver.");
//				checkIn.setTextColor(Color.WHITE);
//				imageViewStatusIcon
//						.setBackgroundResource(R.drawable.ic_registered_icon);
//				imageViewStatusBackground
//						.setBackgroundResource(R.drawable.ic_registered_status);
//				Data.saveInfoOf(getApplicationContext(), Data.ACTIVATEFLAG, "1");
//			} else if (Data.getInfoOf(getApplicationContext(),
//					Data.FLAG_REQUEST).equalsIgnoreCase("22")) {
//				checkinFlag = false;
//				textViewStatus.setText("Request Rejected");
//				checkIn.setTextColor(Color.GRAY);
//				imageViewStatusIcon
//						.setBackgroundResource(R.drawable.ic_disapproved_icon);
//				imageViewStatusBackground
//						.setBackgroundResource(R.drawable.ic_disapproved_status);
//			}
//		} else {
//			relativeLayoutStatus.setVisibility(View.INVISIBLE);
//		}
//		
//		if (Data.getInfoOf(getApplicationContext(), Data.CHECKINVALUE)
//				.equalsIgnoreCase("1")) {
//			checkIn.setText("Check out");
//
//		} else {
//			checkIn.setText("Check in");
//		}
//
//		if (Data.getInfoOf(getApplicationContext(), Data.FLAG_REQUEST)
//				.equalsIgnoreCase("21")) {
//
//			relativeLayoutMenu.setOnClickListener(new OnClickListener() {
//
//				@Override
//				public void onClick(View v) {
//					linearLayoutMenuView.setVisibility(View.VISIBLE);
//					relativeLayoutHeader.startAnimation(slideOutUp);
//					relativeLayoutMain.startAnimation(slideOutBottom);
//					relativeLayoutHeader.setVisibility(View.INVISIBLE);
//					relativeLayoutMain.setVisibility(View.INVISIBLE);
//
//				}
//			});
//
//			checkIn.setOnClickListener(new OnClickListener() {
//
//				@Override
//				public void onClick(View v) {
//					if (Data.getInfoOf(getApplicationContext(),
//							Data.CHECKINVALUE).equals("1")) {
//						Utils.chekOutServerCall();
//					} else {
//						chekInServerCall();
//					}
//				}
//			});
//		}
//
//	}
//
//	private void chekInServerCall() {
//
//		Data.loading_box(this, "Loading...");
//		AsyncHttpClient client = new AsyncHttpClient();
//		RequestParams params = new RequestParams();
//
//		Log.e("Access token", "," + Data.getInfoOf(this, Data.ACCESS_TOKEN));
//
//		params.put("access_token", Data.getInfoOf(this, Data.ACCESS_TOKEN));
//		client.post(Httppost_Links.CHEKIN, params,
//				new AsyncHttpResponseHandler() {
//					@Override
//					public void onSuccess(String response) {
//						Data.loading_box_stop();
//						Log.v("checkin Api", "=" + response);
//
//						JSONObject res;
//						try {
//							res = new JSONObject(response);
//							Log.d("response", "response" + res.toString(2));
//							if (res.has("error")) {
//								Log.d("Data.SERVER_ERROR_MSG",
//										"Data.SERVER_ERROR_MSG"
//												+ res.getString("error"));
//								Data.getAlertDialogWithbothMessage(
//										StatusDriver.this, "OOPS!",
//										res.getString("error"));
//							} else {
//								checkIn.setText("Check out");
//								Data.saveInfoOf(getApplicationContext(),
//										Data.CHECKINVALUE, "1");
//								Intent intent = new Intent(StatusDriver.this, OrderScreen.class);
//								startActivity(intent);
//								overridePendingTransition(R.anim.slide_in_right,
//										R.anim.slide_out_left);
//								finish();
//							}
//						} catch (JSONException e) {
//							e.printStackTrace();
//						}
//					}
//
//					@Override
//					public void onFailure(Throwable arg0, String json) {
//						Data.loading_box_stop();
//						Log.v("request fail", "response=" + json);
//					}
//				});
//
//	}
//
////	private void chekOutServerCall() {
////
////		Data.loading_box(this, "Loading...");
////		AsyncHttpClient client = new AsyncHttpClient();
////		RequestParams params = new RequestParams();
////
////		Log.e("Access token", "," + Data.getInfoOf(this, Data.ACCESS_TOKEN));
////
////		params.put("access_token", Data.getInfoOf(this, Data.ACCESS_TOKEN));
////		client.post(Httppost_Links.CHECKOUT, params,
////				new AsyncHttpResponseHandler() {
////					@Override
////					public void onSuccess(String response) {
////						Data.loading_box_stop();
////						Log.v("checkin Api", "=" + response);
////
////						JSONObject res;
////						try {
////							res = new JSONObject(response);
////							Log.d("response", "response" + res.toString(2));
////							if (res.has("error")) {
////								Log.d("Data.SERVER_ERROR_MSG",
////										"Data.SERVER_ERROR_MSG"
////												+ res.getString("error"));
////								Data.getAlertDialogWithbothMessage(
////										StatusDriver.this, "OOPS!",
////										res.getString("error"));
////							} else {
////								checkIn.setText("Check in");
////								Data.saveInfoOf(getApplicationContext(),
////										Data.CHECKINVALUE, "0");
////							}
////						} catch (JSONException e) {
////							e.printStackTrace();
////						}
////					}
////
////					@Override
////					public void onFailure(Throwable arg0, String json) {
////						Data.loading_box_stop();
////						Log.v("request fail", "response=" + json);
////					}
////				});
////
////	}
//
//	@Override
//	public void onBackPressed() {
//		if (linearLayoutMenuView.isShown()) {
//			relativeLayoutHeader.clearAnimation();
//			relativeLayoutMain.clearAnimation();
//			relativeLayoutHeader.startAnimation(slideOutUpBack);
//			relativeLayoutMain.startAnimation(slideOutBottomBack);
//			relativeLayoutHeader.setVisibility(View.VISIBLE);
//			relativeLayoutMain.setVisibility(View.VISIBLE);
//			Handler mHandler = new Handler();
//			mHandler.postDelayed(new Runnable() {
//				public void run() {
//					linearLayoutMenuView.setVisibility(View.INVISIBLE);
//				}
//			}, 500);
//		} else {
//			finish();
//		}
//
//	}
//
//	@Override
//	public void onResume() {
//		super.onResume();
//		Data.activity = this;
//	}
//
//}
