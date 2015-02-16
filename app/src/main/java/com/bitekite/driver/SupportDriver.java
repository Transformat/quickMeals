package com.bitekite.driver;

import rmn.androidscreenlibrary.ASSL;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bitekite.Log;
import com.bitekite.utils.Data;
import com.bitekite.utils.DialogPopup;
import com.google.analytics.tracking.android.EasyTracker;
import com.bitekite.R;
public class SupportDriver extends Activity implements PushUpdater {
	RelativeLayout relativeLayoutMenu, relativeLayoutHeader,
			relativeLayoutMain, relativeLayoutStatus;

	RelativeLayout relativeLayoutOrder, relativeLayoutDeliveredOrder,
			relativeLayoutPerformance, relativeLayoutAccount,
			relativeLayoutSupport, relativeLayoutEmergency,
			relativeLayoutCheckout, relativeLayoutLogout;

	LinearLayout linearLayoutMenuView;

	TextView textViewOrder, textViewDeliveredOrder, textViewPerformance,
			textViewAccount, textViewSupport, textViewEmergency,
			textViewCheckout, textViewLogout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_support_driver);
		new ASSL(this, (ViewGroup) findViewById(R.id.root), 1134, 720, false);

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

		textViewCheckout = (TextView) findViewById(R.id.CheckOutTxt);

		textViewOrder = (TextView) findViewById(R.id.TodayOrderTxt);
		textViewDeliveredOrder = (TextView) findViewById(R.id.DeliveredOrderTxt);
		textViewPerformance = (TextView) findViewById(R.id.PerformanceTxt);
		textViewAccount = (TextView) findViewById(R.id.AccountTxt);
		textViewSupport = (TextView) findViewById(R.id.SupportTxt);
		textViewEmergency = (TextView) findViewById(R.id.EmergencyTxt);
		textViewLogout = (TextView) findViewById(R.id.LogoutTxt);
		textViewLogout.setTypeface(Data.omnes_Medium(getApplicationContext()));
		textViewCheckout
				.setTypeface(Data.omnes_Medium(getApplicationContext()));
		textViewOrder.setTypeface(Data.omnes_Medium(getApplicationContext()));
		textViewDeliveredOrder.setTypeface(Data
				.omnes_Medium(getApplicationContext()));
		textViewPerformance.setTypeface(Data
				.omnes_Medium(getApplicationContext()));
		textViewAccount.setTypeface(Data.omnes_Medium(getApplicationContext()));
		textViewSupport.setTypeface(Data.omnes_Medium(getApplicationContext()));
		textViewEmergency.setTypeface(Data
				.omnes_Medium(getApplicationContext()));

		if (Data.getInfoOf(getApplicationContext(), Data.CHECKINVALUE)
				.equalsIgnoreCase("0")) {
			relativeLayoutLogout.setVisibility(View.VISIBLE);
			relativeLayoutCheckout.setVisibility(View.GONE);
		} else {
			relativeLayoutLogout.setVisibility(View.GONE);
			relativeLayoutCheckout.setVisibility(View.VISIBLE);
		}

		if (OrderScreen.orderList.size() > 0) {
			relativeLayoutLogout.setVisibility(View.GONE);
			relativeLayoutCheckout.setVisibility(View.GONE);
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
				Intent intent = new Intent(SupportDriver.this,
						OrderScreen.class);
				startActivity(intent);
				finish();
				overridePendingTransition(R.anim.slide_in_right, R.anim.hold);
			}

		});
		relativeLayoutDeliveredOrder.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(SupportDriver.this, History.class);
				startActivity(intent);
				finish();
				overridePendingTransition(R.anim.slide_in_right, R.anim.hold);
			}
		});

		relativeLayoutPerformance.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(SupportDriver.this,
						PerformanceDriver.class);
				startActivity(intent);
				finish();
				overridePendingTransition(R.anim.slide_in_right, R.anim.hold);
			}
		});

		relativeLayoutAccount.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(SupportDriver.this,
						AccountDriver.class);
				startActivity(intent);
				finish();
				overridePendingTransition(R.anim.slide_in_right, R.anim.hold);

			}
		});
		relativeLayoutEmergency.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(SupportDriver.this,
						EmergencyDriver.class);
				startActivity(intent);
				finish();
				overridePendingTransition(R.anim.slide_in_right, R.anim.hold);

			}
		});

		relativeLayoutSupport.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				closeAnim();

			}
		});
		relativeLayoutCheckout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(SupportDriver.this,
						OrderScreen.class);
				intent.putExtra("checkout", true);
				startActivity(intent);
				finish();
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
						new DialogPopup().alertPopup(SupportDriver.this, "",
								"You have received a new order");
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
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		EasyTracker.getInstance(SupportDriver.this).activityStart(this);
//		super.onStart();
	}
	
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		EasyTracker.getInstance(SupportDriver.this).activityStop(this);
//		super.onStart();
	}
}
