package com.bitekite.utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Gravity;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bitekite.CheckOutCustomer;
import com.bitekite.ListofMeals;
import com.bitekite.classes.MealToppings;
import com.bitekite.classes.SelectedItems;
import com.bitekite.driver.OrderScreen;
import com.bitekite.R;
/**
 * Stores common static data for access for all activities across the
 * application
 * 
 */

@SuppressLint("NewApi")
public class Data {

	public static String mealqunt="";
	public static String mealname="";
	public static String mealPrice="";
public static int ZipCodeFlag=0;

public static String ZIPCODE ="";
public static String flurryKey="MD363FWG3NPPFY5GVBP4";


	
	
	public static final String SERVER_ERROR_MSG = "Server error. Please try again later.";
	public static final String SERVER_NOT_RESOPNDING_MSG = "Oops!! Server not responding. Please try again later.";
	public static final String CHECK_INTERNET_MSG = "Check your internet connection.";
//	public static final String APP_SECRET_KEY = "sk_test_h7qE19ZyvARlne0OaEv0n7Bt";// Secret
																					// key
																					// for
																					// App
																					// by
																					// bhargava.ravish00@gmail.com
																					// (uber
																					// awe)
	
//	public static final String PUBLISHABLE_KEY = "pk_live_Y3UY9tRpJ5Iv3DJqbgI9Pi1l";// Chinmay				pk_test_uwFTQNMZNR97NNkYRR9lfnKU
////																					// sir
////																					// Credentials
////																					// ->//
////																					// publishable
////																					// key
////																					// pk_test_uwFTQNMZNR97NNkYRR9lfnKU
//	public static final String APP_SECRET_KEY = "sk_live_WbcbQQ1mZOQE35uHxA2If62I";	//			   		sk_test_bWdx57wf8rEAoOewfryX3JVs
	
	
	public static final String APP_SECRET_KEY = "sk_test_bWdx57wf8rEAoOewfryX3JVs";		//TEST
	public static final String PUBLISHABLE_KEY = "pk_test_uwFTQNMZNR97NNkYRR9lfnKU";	//TEST
	
	
//	public static final String APP_SECRET_KEY = "sk_live_WbcbQQ1mZOQE35uHxA2If62I";		//LIVE
//	public static final String PUBLISHABLE_KEY = "pk_live_Y3UY9tRpJ5Iv3DJqbgI9Pi1l";	//LIVE
	
	public static String APP_VERSION = "105";
	
	
	public static final String PLACEDORDERID = "placeOrderId";
	public static final String ORDERID = "orderId";
//	public static final String CURRENT_ORDER_LATITUDE = "Latitude";
//	public static final String CURRENT_ORDER_LONGITUDE = "Longitude";
//	public static final String CURRENT_ORDER_ADDRESS = "Address";
//	public static final String CURRENT_NUMBER_OF_MEALS = "Meals";
//	public static final String CURRENT_ORDER_CUSTOMER_NAME = "CustomerName";
//	public static final String START_ORDER_DETAILS = "StartOrderDetails";
	
	
	public static String orderIdCustomer;
	public static ArrayList<SelectedItems> selectedItems = new ArrayList<SelectedItems>();
	
	public static String googleSenderId ="484085852999";
	public static String regId="abcdef";
	public static final String SP_NAME = "bitekite";
	public static final String ACCESS_TOKEN = "accessToken";
	public static final String CURRENT_USER_STATUS = "current_user_status";
	public static final String CUSTOMER_ID = "customer_id";
	public static String loginFrom = "loginFrom";

	public static  final String USER_IMAGE_URL = "userImageURL";
	public static  final String USER_NAME = "userName";
	public static  final String PH_NO = "phoneNumber";
	public static  final String EMAIL = "emailId";
	public static  final String CHECK_CARD = "check_card";
	public static  final String REFERRAL_CODE = "referral_code";
//	public static  final String CREDIT_BALANCE = "credit_balance";
	
	public static final String ZIPI="zipi";
	
	public static String ESTIMATED_TIME = "estimated_time";
	public static String  customer_estimate_time="20";

	
	
	public static String S_PH_NO = "";
	public static String S_EMAIL = "";
	public static String S_PASSWORD = "";
	public static String S_ADDRESS="";
	public static String S_USER_NAME = "";
	public static String DEVICE_TYPE;
	public static String DEVICE_TOKEN;
	public static String LATITUDE="0.0";
	public static String LONGITUDE="0.0";
	public static String COUNTRY;
	public static String DEVICE_NAME;
	
	public static String OS_VERSION;

	public static String STRIPE_TOKEN_ID;
	public static  final String LAST_4="last_4";
	public static  final String ADDRESS="address";
	public static String S_LAST_4="";
	public static String STRIPE_SECRET_KEY;
	public static String CAR_MODEL="";
	public static String CAR_MAKER="";

	public static Typeface effraRegular;
	public static Typeface HelveticaNeueLTStd;
	public static Typeface omnesRegular;
	public static Typeface omnesMedium;
	public static Typeface omnesBold;
	
	
	

	public static String lati = "";
	public static String longi = "";
	public static String cityName = "";
	public static String[] resultsname;
	public static String[] resultsaddress;
	public static Double[] latitudes;
	public static Double[] longitudes;
	public static String selectedplace;
	public static Double seletedlat;
	public static Double selectedlng;
	public static int Flag = 0; // flag for coming back from map to create
								// profile

	public static double currentLatitude;
	public static double currentLongitude;
	public static double driverLatitude;
	public static double driverLongitude;
	public static String FLAG_REQUEST = "flag";
	public static int totalValue = 0;
	public static final String CHECKINVALUE = "checkInValue";
	public static final String ACTIVATEFLAG = "activateflag";
	
	public static String ToppingIds="1";
	public static String ToppingQty="0";
	public static double MealPrize;
	public static double ToppingPrize;
	
	public static  final String OrderPlacedFlag="orderPlaced";
	public static  final String DroppedLatitude="latitude";
	public static  final String DroppedLongitude="longitude";
//	public static String OrderDeliveredTime="";
	

	public static Activity activity;

	public static ArrayList<MealToppings> TOPPING_ARRAYLIST;

	public static Typeface bariolBold,bariolLight,bariolRegular,bariolThin;
	
	public static Typeface bariol_bold(Context appContext) { // accessing
		// fonts
		// functions
		if (bariolBold == null) {
			bariolBold = Typeface.createFromAsset(appContext.getAssets(),
					"bariol_bold.ttf");
		}
		return bariolBold;
	}
	public static Typeface bariol_light(Context appContext) { // accessing
		// fonts
		// functions
		if (bariolLight == null) {
			bariolLight = Typeface.createFromAsset(appContext.getAssets(),
					"bariol_light.ttf");
		}
		return bariolLight;
	}
	
	public static Typeface bariol_regular(Context appContext) { // accessing
		// fonts
		// functions
		if (bariolRegular == null) {
			bariolRegular = Typeface.createFromAsset(appContext.getAssets(),
					"bariol_regular.ttf");
		}
		return bariolRegular;
	}
	
	public static Typeface bariol_thin(Context appContext) { // accessing
		// fonts
		// functions
		if (bariolThin == null) {
			bariolThin = Typeface.createFromAsset(appContext.getAssets(),
					"bariol_thin.ttf");
		}
		return bariolThin;
	}
	
	
	public static Typeface effra_Std_Regular(Context appContext) { // accessing
		// fonts
		// functions
		if (effraRegular == null) {
			effraRegular = Typeface.createFromAsset(appContext.getAssets(),
					"Effra_Std_Rg 2.ttf");
		}
		return effraRegular;
	}

	public static Typeface Helvetica_NeueLTStd(Context appContext) { // accessing
		// fonts
		// functions
		if (HelveticaNeueLTStd == null) {
			HelveticaNeueLTStd = Typeface.createFromAsset(appContext.getAssets(),
					"bariol_regular.ttf");
		}
		return HelveticaNeueLTStd;
	}

	public static Typeface omnes_Regular(Context appContext) { // accessing
		// fonts
		// functions
		if (omnesRegular == null) {
			omnesRegular = Typeface.createFromAsset(appContext.getAssets(),
					"bariol_regular.ttf");
		}
		return omnesRegular;
	}

	public static Typeface omnes_Medium(Context appContext) { // accessing
		// fonts
		// functions
		if (omnesMedium == null) {
			omnesMedium = Typeface.createFromAsset(appContext.getAssets(),
					"Omnes-Medium.otf");
		}
		return omnesMedium;
	}

	public static Typeface omnes_Bold(Context appContext) { // accessing
		// fonts
		// functions
		if (omnesBold == null) {
			omnesBold = Typeface.createFromAsset(appContext.getAssets(),
					"bariol_bold.ttf");
		}
		return omnesBold;
	}

	public static void saveInfoOf(Context context, String Action, String value) {
		SharedPreferences pref = context.getSharedPreferences(SP_NAME, 0);
		Editor editor = pref.edit();
		editor.putString(Action, value);
		editor.commit();
	}

	public static String getInfoOf(Context context, String Action) {
		SharedPreferences pref = context.getSharedPreferences(SP_NAME, 0);
		return pref.getString(Action, "");

	}

	public static int timeout = 30000;
	private static ProgressDialog pd_st;

	/**
	 * Custom Loading.
	 * 
	 * @param c
	 *            Application Context.
	 * @param msg
	 *            Message.
	 */
	public static void loading_box(Context c, String msg) {

		Log.v("loading start", "loading start");

		loading_box_stop();

		pd_st = new ProgressDialog(c,
				android.R.style.Theme_Translucent_NoTitleBar);

		pd_st.show();
		pd_st.setCancelable(false);
		pd_st.setContentView(R.layout.loading_box);
		TextView t1 = (TextView) pd_st.findViewById(R.id.textView1);
		t1.setText(msg);
		pd_st.findViewById(R.id.rlt).setAlpha(0.9f);

	}

	/**
	 * Close custom loading.
	 */
	public static void loading_box_stop() {
		if (pd_st != null)
			if (pd_st.isShowing()) {
				Log.v("loading stop", "loading stop");
				pd_st.dismiss();
			}

	}
	
	

	// ***************

	public static long last_press = 0;
	public static int button_press_delay = 500; // delay in milliseconds

	public static Boolean isTapable() {
		if (last_press + button_press_delay < System.currentTimeMillis()) {
			last_press = System.currentTimeMillis();
			return true;
		} else {
			return false;
		}
	}

	private static boolean net;

	public static boolean Internetcheck(Activity act) {

		try {
			ConnectivityManager ConMgr = (ConnectivityManager) act
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			if (ConMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED
					|| ConMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
							.getState() == NetworkInfo.State.CONNECTED)
				net = true;
			else {
				net = false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return net;
	}

	public static void getAlertDialogWithbothMessage(Activity act,
			String title, String msg) {
		new AlertDialog.Builder(act)
				.setTitle(title)
				.setMessage(msg)
				.setCancelable(false)
				.setPositiveButton(android.R.string.ok,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								dialog.cancel();
							}
						}).show();
	}

	public static void getAlertDialogWithMessage(Activity act, String msg) {
		new AlertDialog.Builder(act)
				.setTitle("ERROR!")
				.setMessage(msg)
				.setCancelable(false)
				.setPositiveButton(android.R.string.ok,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								dialog.cancel();
							}
						}).show();
	}

	public static void getAlertDialog(Activity act) {
		new AlertDialog.Builder(act)
				.setTitle("CONNECTION FAILED!")
				.setMessage(
						"Your Internet Connection is not available at the moment. Please try again later.")
				.setCancelable(false)
				.setPositiveButton(android.R.string.ok,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								dialog.cancel();
							}
						}).show();
	}

	public static void getAlertDialog_withfinish(final Activity act,
			String string) {
		new AlertDialog.Builder(act)
				.setTitle("CONNECTION FAILED!")
				.setMessage(string)
				.setCancelable(false)
				.setPositiveButton(android.R.string.ok,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								dialog.cancel();
								act.finish();

							}
						}).show();
	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@SuppressLint("NewApi")
	public static void updateAvailable(final Context act) {
		AlertDialog.Builder builder = new AlertDialog.Builder(act);
		TextView title = new TextView(act);
		title.setText("UPDATE AVAILABLE");
		title.setBackgroundColor(Color.DKGRAY);
		title.setPadding(10, 10, 10, 10);
		title.setGravity(Gravity.CENTER);
		title.setTextColor(Color.WHITE);
		title.setTextSize(20);

		TextView text = new TextView(act);
		text.setText("New update has been released. Go to STORE.");
		text.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT));
		text.setTextSize(18);
		text.setGravity(Gravity.CENTER);

		// Creates a linearlayout layout and sets it with initial params
		LinearLayout ll = new LinearLayout(act);
		ll.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT));
		ll.setGravity(Gravity.CENTER);
		ll.addView(text);
		builder.setCustomTitle(title);
		builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				try {
					act.startActivity(new Intent(Intent.ACTION_VIEW, Uri
							.parse("market://details?id=com.youareaceo.yaac")));
				} catch (Exception e) {
					Log.e("Link not available", "Link not available");
//					Toast.makeText(act, "Link not available", 300).show();
				}

				dialog.dismiss();
				SharedPreferences prefs = PreferenceManager
						.getDefaultSharedPreferences(act);
				Editor editor = prefs.edit();

				if (editor != null) {
					int count = prefs.getInt("updatecount", 0) + 1;
					editor.putInt("updatecount", count);
					editor.commit();
				}
			}
		});
		builder.setNegativeButton("Not Now",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {

						SharedPreferences prefs = PreferenceManager
								.getDefaultSharedPreferences(act);
						Editor editor = prefs.edit();
						if (editor != null) {
							int count = prefs.getInt("updatecount", 0) + 1;
							editor.putInt("updatecount", count);
							editor.commit();
						}
						dialog.dismiss();
					}
				});
		Dialog d = builder.setView(ll).create();

		d.show();
	}

	public static boolean startAnim = true;

	public static String utcToLocal(String utcTime) {
		SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy, hh:mm a");
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
				"yyyy-MM-dd hh:mm:ss");
		try {
			Date myDate = simpleDateFormat.parse(utcTime);

			myDate.setTime(myDate.getTime()+ TimeZone.getDefault().getOffset(
					Calendar.getInstance().getTime().getTime()));

			String localDate = sdf.format(myDate);
			return localDate;
		} catch (Exception e1) {
			Log.e("e1", "=" + e1);
			return utcTime;
		}

	}

	private static Animation slideOutUp;

	public static Animation getSlideOutUp(Context c) {
		if (slideOutUp == null) {
			slideOutUp = AnimationUtils.loadAnimation(c, R.anim.slide_out_up);

			slideOutUp.setAnimationListener(new AnimationListener() {

				@Override
				public void onAnimationStart(Animation animation) {
					// TODO Auto-generated method stub
					startAnim = false;
				}

				@Override
				public void onAnimationRepeat(Animation animation) {
					// TODO Auto-generated method stub

				}

				@Override
				public void onAnimationEnd(Animation animation) {
					// TODO Auto-generated method stub
					startAnim = true;
				}
			});
		}
		return slideOutUp;
	}

	private static Animation slideOutBottom;

	public static Animation getslideOutBottom(Context c) {
		if (slideOutBottom == null) {
			slideOutBottom = AnimationUtils.loadAnimation(c,
					R.anim.slide_out_bottom);

			slideOutBottom.setAnimationListener(new AnimationListener() {

				@Override
				public void onAnimationStart(Animation animation) {
					// TODO Auto-generated method stub
					startAnim = false;
				}

				@Override
				public void onAnimationRepeat(Animation animation) {
					// TODO Auto-generated method stub

				}

				@Override
				public void onAnimationEnd(Animation animation) {
					// TODO Auto-generated method stub
					startAnim = true;
				}
			});
		}
		return slideOutBottom;
	}

	private static Animation slideOutUpBack;

	public static Animation getslideOutUpBack(Context c) {
		if (slideOutUpBack == null) {
			slideOutUpBack = AnimationUtils.loadAnimation(c,
					R.anim.slide_out_up_back);

			slideOutUpBack.setAnimationListener(new AnimationListener() {

				@Override
				public void onAnimationStart(Animation animation) {
					// TODO Auto-generated method stub
					Data.startAnim = false;
				}

				@Override
				public void onAnimationRepeat(Animation animation) {
					// TODO Auto-generated method stub

				}

				@Override
				public void onAnimationEnd(Animation animation) {
					// TODO Auto-generated method stub
					Data.startAnim = true;
				}
			});
		}
		return slideOutUpBack;
	}

	private static Animation slideOutBottomBack;
	public static String startTime;
	public static String endTime;
	public static String newResponse="";
	public static String oldResponse="";
	
	

	public static Animation getslideOutBottomBack(Context c) {
		if (slideOutBottomBack == null) {
			slideOutBottomBack = AnimationUtils.loadAnimation(c,
					R.anim.slide_out_bottom_back);

			slideOutBottomBack.setAnimationListener(new AnimationListener() {

				@Override
				public void onAnimationStart(Animation animation) {
					// TODO Auto-generated method stub
					Data.startAnim = false;
				}

				@Override
				public void onAnimationRepeat(Animation animation) {
					// TODO Auto-generated method stub

				}

				@Override
				public void onAnimationEnd(Animation animation) {
					// TODO Auto-generated method stub
					Data.startAnim = true;
				}
			});
		}
		return slideOutBottomBack;
	}

	
	public static void saveAccessToken(String token, Context context) {
		SharedPreferences pref = PreferenceManager
				.getDefaultSharedPreferences(context);
		Editor editor = pref.edit();
		editor.putString("accessToken", token);
		editor.commit();
	}

	public static String getAccessToken(Context context) {
		
			SharedPreferences pref = PreferenceManager
					.getDefaultSharedPreferences(context);
			return pref.getString("accessToken", "");
		
	}
	public static void alertMessageBox(final Activity activity,String title,String msg, final String loginFrom2) {
		final AlertDialog.Builder builder = new AlertDialog.Builder(activity);
		builder.setMessage(msg)
				.setCancelable(false)
				.setTitle(title)
				.setPositiveButton("Ok",
						new DialogInterface.OnClickListener() {

							public void onClick(final DialogInterface dialog,
									int intValue) {
							
								 	Uri uriUrl = Uri.parse("https://play.google.com/store/apps/details?id=com.bitekite&hl=en");
							        Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
							        activity.startActivity(launchBrowser);
							        activity.finish();
							}
						})
				.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
					public void onClick(final DialogInterface dialog,
							int intValue) {
						if(loginFrom2.equalsIgnoreCase("2"))
						{
						Intent intent = new Intent(activity,ListofMeals.class);
						activity.startActivity(intent);
						}
						else if(loginFrom2.equalsIgnoreCase("1"))
						{
							Intent intent = new Intent(activity,OrderScreen.class);
							activity.startActivity(intent);
						}
						 activity.finish();
//						dialog.cancel();
					}
				});

		final AlertDialog alert = builder.create();
		alert.show();
	}
	public static void alertMessageBoxForce(final Activity activity,String title,String msg) {
		final AlertDialog.Builder builder = new AlertDialog.Builder(activity);
		builder.setMessage(msg)
				.setCancelable(false)
				.setTitle(title)
				.setPositiveButton("Ok",
						new DialogInterface.OnClickListener() {

							public void onClick(final DialogInterface dialog,
									int intValue) {
							
								 	Uri uriUrl = Uri.parse("https://play.google.com/store/apps/details?id=com.bitekite&hl=en");
							        Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
							        activity.startActivity(launchBrowser);
							        activity.finish();
							}
						});

		final AlertDialog alert = builder.create();
		alert.show();
	}
	
	
	public static void alertMessageBoxForConfirmation(final Activity activity,String title,String msg) {
		final AlertDialog.Builder builder = new AlertDialog.Builder(activity);
		builder.setMessage(msg)
				.setCancelable(false)
				.setTitle(title)
				.setPositiveButton("Ok",
						new DialogInterface.OnClickListener() {

							public void onClick(final DialogInterface dialog,
									int intValue) {
							CheckOutCustomer.ConfirmAddressFlag=1;
//								 	Uri uriUrl = Uri.parse("http://www.click-labs.com");
//							        Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
//							        activity.startActivity(launchBrowser);
//							        activity.finish();
							}
						});

		final AlertDialog alert = builder.create();
		alert.show();
	}
	
//	public static void loading_box2(Context c, String msg) {

//		Log.v("loading start", "loading start");
//
//		loading_box_stop();
//
//		pd_st = new ProgressDialog(c,
//				android.R.style.Theme_Translucent_NoTitleBar);
//
//		pd_st.show();
//		pd_st.setCancelable(false);
//		pd_st.setContentView(R.layout.loading_box);
//		TextView t1 = (TextView) pd_st.findViewById(R.id.textView1);
//		t1.setText(msg);
//		pd_st.findViewById(R.id.rlt).setAlpha(0.9f);
		static ImageView view;

		private static AnimationDrawable frameAnimation;
		private	static ProgressDialog progressDial;
		public static String deliveryCharge;
		public static String servicetax;
		public static String creditBalance;
		public static String ORDERSTATUS="";
		public static String OPENSTATUS="";
//		public static String PLACEDORDERID="";
		
		public static String OUTOFZONE="";

		
		
//		public static int ConfirmAddressFlag=0;
		
		
		public static void showLoadingDialog(Context contxt, String msg) {
			
		progressDial = new ProgressDialog(contxt,
		android.R.style.Theme_Translucent_NoTitleBar);
		// pd_st.getWindow().getAttributes().windowAnimations =
		// R.style.Animations_LoadingDialogFade;
		progressDial.getWindow().getAttributes().windowAnimations =
		R.style.Animations_progressFadeInOut;
		progressDial.show();
//			progressDial.getWindow().addFlags(
//			WindowManager.LayoutParams.FLAG_DIM_BEHIND);
		progressDial.setCancelable(false);
		progressDial.setContentView(R.layout.loading_box2);
		WindowManager.LayoutParams layoutParams = progressDial.getWindow()
		.getAttributes();
		layoutParams.dimAmount = 0.3f;
		progressDial.getWindow().setAttributes(layoutParams);
		progressDial.getWindow().addFlags(
		WindowManager.LayoutParams.FLAG_DIM_BEHIND);		 
		view = (ImageView) progressDial.findViewById(R.id.progressBar1);

		// Setting animation_list.xml as the background of the image view
		view.setBackgroundResource(R.drawable.gif_loader);
		// Typecasting the Animation Drawable
		frameAnimation = (AnimationDrawable) view.getBackground();
		frameAnimation.start();
		}

		public static void loading_box_stop2() {
			if (progressDial != null)
				if (progressDial.isShowing()) {
					Log.v("loading stop", "loading stop");
					progressDial.dismiss();
				}

		}
//	}
	
	/**
	 * 
	 * Compare double values returns 1 if d1 > d2 returns -1 if d1 < d2 returns
	 * 
	 * 0 if d1 == d2
	 * 
	 * 
	 * 
	 * @param d1
	 * 
	 * @param d2
	 */

	public static int compareDouble(double d1, double d2) {

		if (d1 == d2) {
			return 0;
		} else {
			double EPSILON = 0.00000001;
			if ((d1 - d2) > EPSILON) {
				return 1;
			} else if ((d1 - d2) < EPSILON && (d1 - d2) > 0.0) {
				return 0;
			} else if ((d1 - d2) < 0.0) {
				return -1;
			} else {
				return 0;
			}
		}
	}
	
}
