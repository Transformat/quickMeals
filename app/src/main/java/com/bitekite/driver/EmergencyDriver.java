package com.bitekite.driver;
import com.bitekite.R;
import org.json.JSONException;
import org.json.JSONObject;

import rmn.androidscreenlibrary.ASSL;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bitekite.GCMIntentService;
import com.bitekite.Log;
import com.bitekite.MainActivity;
import com.bitekite.utils.Data;
import com.bitekite.utils.DialogPopup;
import com.google.analytics.tracking.android.EasyTracker;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class EmergencyDriver extends Activity  implements PushUpdater{
	RelativeLayout relativeLayoutMenu, relativeLayoutHeader,
			relativeLayoutMain, relativeLayoutStatus,relativeLayoutCover;

	RelativeLayout relativeLayoutOrder, relativeLayoutDeliveredOrder,
			relativeLayoutPerformance, relativeLayoutAccount,
			relativeLayoutSupport, relativeLayoutEmergency,
			relativeLayoutCheckout,relativeLayoutLogout;
	String support_no;
	LinearLayout linearLayoutMenuView;
	TextView textViewOrder, textViewDeliveredOrder, textViewPerformance,
			textViewAccount, textViewSupport, textViewEmergency,textViewCheckout,textViewLogout,textViewNumber,textViewContent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_emergency_driver);
		new ASSL(this, (ViewGroup) findViewById(R.id.root), 1134, 720, false);

		relativeLayoutLogout = (RelativeLayout) findViewById(R.id.LogoutLayout);
		relativeLayoutMenu = (RelativeLayout) findViewById(R.id.menuBtnLayout);
		relativeLayoutCover = (RelativeLayout) findViewById(R.id.relativeLayoutCover);
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
		
		textViewContent = (TextView) findViewById(R.id.txt);
		textViewNumber =(TextView) findViewById(R.id.number);
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

		textViewNumber.setTypeface(Data.bariol_bold(getApplicationContext()));
		textViewContent.setTypeface(Data.bariol_regular(getApplicationContext()));
		
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
		if(OrderScreen.orderList.size()>0)
		{
			relativeLayoutLogout.setVisibility(View.GONE);
			relativeLayoutCheckout.setVisibility(View.GONE);
		}
		
		textViewNumber.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				  Intent phoneCallIntent = new Intent(Intent.ACTION_CALL);
				  phoneCallIntent.setData(Uri.parse("tel:"
				  +""+support_no));
				  startActivity(phoneCallIntent);
			}
		});

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
				Intent intent = new Intent(EmergencyDriver.this,
						OrderScreen.class);
				startActivity(intent); finish();
				overridePendingTransition(R.anim.slide_in_right, R.anim.hold);
			}

		});
		relativeLayoutDeliveredOrder.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(EmergencyDriver.this, History.class);
				startActivity(intent); finish();
				overridePendingTransition(R.anim.slide_in_right, R.anim.hold);
			}
		});

		relativeLayoutPerformance.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(EmergencyDriver.this,
						PerformanceDriver.class);
				startActivity(intent); finish();
				overridePendingTransition(R.anim.slide_in_right, R.anim.hold);
			}
		});

		relativeLayoutAccount.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent intent = new Intent(EmergencyDriver.this,
						AccountDriver.class);
				startActivity(intent); finish();
				overridePendingTransition(R.anim.slide_in_right, R.anim.hold);
			}
		});
		relativeLayoutEmergency.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				closeAnim();

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
//				Intent intent = new Intent(EmergencyDriver.this,
//						SupportDriver.class);
//				startActivity(intent); finish();
//				overridePendingTransition(R.anim.slide_in_right, R.anim.hold);

			}
		});
		relativeLayoutCheckout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(EmergencyDriver.this, OrderScreen.class);
				intent.putExtra("checkout", true);
				startActivity(intent); finish();
				overridePendingTransition(R.anim.slide_out_bottom_back,
						R.anim.hold);
				finish();
			}
		});
		relativeLayoutLogout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Utils.logout();
			}
		});
		serverCallEmergency();
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
	@Override
	public void onResume() {
		super.onResume();
		Data.activity = this;
		OrderScreen.pushUpdater = this;
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
							new DialogPopup().alertPopup(EmergencyDriver.this, "",
									GCMIntentService.stringMessage);
						}
						else if(GCMIntentService.flag.equals("19"))
						{
							Data.saveInfoOf(Data.activity,
									Data.CHECKINVALUE, "0");
							Intent intent = new Intent(EmergencyDriver.this,
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
							new DialogPopup().alertPopup(EmergencyDriver.this, "",
									GCMIntentService.stringMessage);
						}
						else if(GCMIntentService.flag.equals("16"))
						{
							new DialogPopup().alertPopup(EmergencyDriver.this, "",
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
		Utils.buildAlertMessageNoGps(EmergencyDriver.this);
		super.onWindowFocusChanged(hasFocus);
	}
	
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		EasyTracker.getInstance(EmergencyDriver.this).activityStart(this);
//		super.onStart();
	}
	
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		EasyTracker.getInstance(EmergencyDriver.this).activityStop(this);
//		super.onStart();
	}
	
	void serverCallEmergency() {
		RequestParams params = new RequestParams();

//		params.put("id", "" + id);
//		params.put("access_token",
//				"" + Data.getAccessToken(getApplicationContext()));// +Data.ACCESS_TOKEN);
//		Log.d("Data.getAccessToken(getApplicationContext())","Data.getAccessToken(getApplicationContext())"+Data.getAccessToken(getApplicationContext()));
		AsyncHttpClient client = new AsyncHttpClient();

		client.setTimeout(Httppost_Links.SERVER_TIME_OUT_TIME);
		client.get(Httppost_Links.Baselink + "support_page", params,
				new AsyncHttpResponseHandler() {

					@Override
					public void onSuccess(String response) {
						// Log.i("request succesfull", "response = " +
						// response);
						// fillServerData(response);
						JSONObject res;
						try {
							
							res = new JSONObject(response);
							Log.i("Response", "Response" + res.toString(2));

							if(res.getString("status").equals("2"))
							{
//								Data.alertMessageBox(SplashScreen.this,popup.getString("title"),popup.getString("text"),Data.getInfoOf(getApplicationContext(), Data.loginFrom));
							Intent intent = new Intent(EmergencyDriver.this,MainActivity.class);
							startActivity(intent);
							finish();
							}
							else
							{
							JSONObject user_data = res
									.getJSONObject("data");

							String support_text = user_data.getString("support_text");
							 support_no = user_data.getString("support_no");

							Log.d("support_text","support_text"+support_text.replace("\\n", "\n"));
							textViewContent.setText(Html.fromHtml(support_text));
							textViewNumber.setText(""+support_no);
							relativeLayoutCover.setVisibility(View.INVISIBLE);
							}

						} catch (JSONException e) {
							e.printStackTrace();
						}
					}

					@Override
					public void onFailure(Throwable arg0) {
						Log.e("request fail", arg0.toString());
//						new DialogPopup()
//						.alertPopup(AddPaymentUser.this, "",
//								"Server not responding\nTry again.");
					}
				});
	}
}
