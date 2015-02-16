package com.bitekite;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import rmn.androidscreenlibrary.ASSL;
import android.R.integer;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bitekite.driver.PushUpdater;
import com.bitekite.driver.SimpleJSONParser;
import com.bitekite.driver.Utils;
import com.bitekite.utils.Data;
import com.google.analytics.tracking.android.EasyTracker;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

public class DriverLocationFinder extends FragmentActivity implements
		PushUpdater {
	static GoogleMap map;
	static Marker destMarker, custMarker;
	static String timeString;
	static String distanceString;
	static Activity activity;
	static TextView textViewTime;
	static Context context;
	TextView textViewCancel, textViewDetails, textViewDriverwill,
			textViewHeader, textViewAddressStatic, textViewAddress,
			textViewPhoneStatic, textViewPhone, textViewOrder;
	Animation animationSlideInRight, animationSlideInLeft,
			animationSlideOutRight, animationSlideOutLeft;
	LinearLayout linearLayoutDetails;
	RelativeLayout relativeLayoutMap;
	ScrollView scrollView;
	static int eshant = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_driver_location_finder);
		new ASSL(this, (ViewGroup) findViewById(R.id.root), 1134, 720, false);
		activity = this;
		eshant = 0;
		context = getApplicationContext();
		textViewTime = (TextView) findViewById(R.id.timeTxt);
		textViewCancel = (TextView) findViewById(R.id.CANCEL_TXT);
		scrollView = (ScrollView) findViewById(R.id.scrollView1);
		map = ((SupportMapFragment) getSupportFragmentManager()
				.findFragmentById(R.id.map)).getMap();
		map.setMyLocationEnabled(true);

		Data.saveInfoOf(getApplicationContext(), "placeOrderId", "");
		if (Data.getInfoOf(getApplicationContext(), "GCM").equals("")) {

			CameraUpdate cameraUpdate = CameraUpdateFactory
					.newLatLngZoom(new LatLng(Data.currentLatitude,
							Data.currentLongitude), 15);
			map.animateCamera(cameraUpdate);
			textViewCancel.setVisibility(View.VISIBLE);
		} else {

			try {
				cancelNotification(getApplicationContext(), 0);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(
					new LatLng(Double.parseDouble(Data.getInfoOf(
							getApplicationContext(), Data.DroppedLatitude)),
							Double.parseDouble(Data.getInfoOf(
									getApplicationContext(),
									Data.DroppedLongitude))), 15);
			map.animateCamera(cameraUpdate);
			textViewCancel.setVisibility(View.VISIBLE);
		}

		map.getUiSettings().setZoomControlsEnabled(false);
		textViewDetails = (TextView) findViewById(R.id.DETAILS_TXT);
		textViewDriverwill = (TextView) findViewById(R.id.driverwill);
		textViewHeader = (TextView) findViewById(R.id.CURRENT_DELIVERY_TXT);

		textViewAddressStatic = (TextView) findViewById(R.id.ADDRESS_TXT);
		textViewAddress = (TextView) findViewById(R.id.ADDRESS);
		textViewPhoneStatic = (TextView) findViewById(R.id.PHONE_TXT);
		textViewPhone = (TextView) findViewById(R.id.PHONE);
		textViewOrder = (TextView) findViewById(R.id.ODRED_TXT);

		linearLayoutDetails = (LinearLayout) findViewById(R.id.detailsLayout);
		relativeLayoutMap = (RelativeLayout) findViewById(R.id.mapLayout);

		textViewCancel
				.setTypeface(Data.bariol_regular(getApplicationContext()));
		textViewDetails.setTypeface(Data
				.bariol_regular(getApplicationContext()));
		textViewTime.setTypeface(Data.bariol_bold(getApplicationContext()));
		textViewDriverwill.setTypeface(Data
				.bariol_regular(getApplicationContext()));
		textViewHeader.setTypeface(Data.bariol_bold(getApplicationContext()));

		textViewAddressStatic.setTypeface(Data
				.bariol_regular(getApplicationContext()));
		textViewAddress.setTypeface(Data.bariol_bold(getApplicationContext()));
		textViewPhoneStatic.setTypeface(Data
				.bariol_regular(getApplicationContext()));
		textViewPhone.setTypeface(Data.bariol_bold(getApplicationContext()));
		textViewOrder.setTypeface(Data.bariol_regular(getApplicationContext()));

		textViewAddress.setText(""
				+ Data.getInfoOf(getApplicationContext(),
						"drop_location_address"));
		textViewPhone.setText(""
				+ Data.getInfoOf(getApplicationContext(), "phone_no"));

		String temp[] = Data.getInfoOf(getApplicationContext(), "Details")
				.split("\\@@");
		String nameList[] = temp[0].split("\\##");
		String priceList[] = temp[1].split("\\##");
		String qunt[] = temp[2].split("\\##");
		Log.e("Data====", temp[0]);
		for (int i = 0; i < nameList.length; i++) {
			View child = getLayoutInflater().inflate(
					R.layout.list_item_order_details, null);
			TextView textViewQunt = (TextView) child
					.findViewById(R.id.textView1);
			TextView textViewName = (TextView) child
					.findViewById(R.id.textView2);
			TextView textViewAmount = (TextView) child
					.findViewById(R.id.textView3);
			RelativeLayout relativeLayoutTopping = (RelativeLayout) child
					.findViewById(R.id.RelativeLayout1);
			relativeLayoutTopping.setLayoutParams(new ListView.LayoutParams(
					ListView.LayoutParams.MATCH_PARENT, 80));// ListView.LayoutParams.WRAP_CONTENT));
			ASSL.DoMagic(relativeLayoutTopping);

			if (qunt[i].equals("--")) {
				textViewQunt.setTypeface(Data
						.bariol_bold(getApplicationContext()));
				textViewName.setTypeface(Data
						.bariol_bold(getApplicationContext()));
				textViewAmount.setTypeface(Data
						.bariol_bold(getApplicationContext()));
			} else {
				textViewQunt.setText("" + qunt[i] + "x");
				textViewQunt.setTypeface(Data
						.bariol_regular(getApplicationContext()));
				textViewName.setTypeface(Data
						.bariol_regular(getApplicationContext()));
				textViewAmount.setTypeface(Data
						.bariol_regular(getApplicationContext()));
			}

			if (nameList[i].equalsIgnoreCase("Credit Used")) {
				String prizes = priceList[i].toString();
				String str[] = prizes.split("\\.");
				if (str.length == 1) {
					textViewAmount.setText(Html.fromHtml("-$" + priceList[i]
							+ ".<sup><small>" + "00" + "</small></sup>"));
				} else {
					textViewAmount.setText(Html.fromHtml("-$" + str[0]
							+ ".<sup><small>" + str[1] + "</small></sup>"));
				}

			} else {

				String prizes = priceList[i].toString();
				String str[] = prizes.split("\\.");
				if (str.length == 1) {
					textViewAmount.setText(Html.fromHtml("$" + priceList[i]
							+ ".<sup><small>" + "00" + "</small></sup>"));
				} else {
					textViewAmount.setText(Html.fromHtml("$" + str[0]
							+ ".<sup><small>" + str[1] + "</small></sup>"));
				}

			}
			textViewName.setText("" + nameList[i]);

			linearLayoutDetails.addView(child);
		}

		textViewCancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				eshant = 1;
				Data.oldResponse = "";
				Intent intent = new Intent(DriverLocationFinder.this,
						ListofMeals.class);
				startActivity(intent);
				finish();
				overridePendingTransition(R.anim.slide_in_left,
						R.anim.slide_out_right);
				finish();
				// animationSlideInRight = AnimationUtils.loadAnimation(
				// getApplicationContext(), R.anim.slide_in_right_slow);
				// animationSlideInLeft = AnimationUtils.loadAnimation(
				// getApplicationContext(), R.anim.slide_in_left_slow);
				// animationSlideOutRight = AnimationUtils.loadAnimation(
				// getApplicationContext(), R.anim.slide_out_right_slow);
				// animationSlideOutLeft = AnimationUtils.loadAnimation(
				// getApplicationContext(), R.anim.slide_out_left_slow);
				// linearLayoutDetails.clearAnimation();
				// linearLayoutDetails
				// .setAnimation(animationSlideOutRight);
				// relativeLayoutMap.setAnimation(animationSlideInLeft);
				// linearLayoutDetails.setVisibility(View.INVISIBLE);
				// textViewCancel.setVisibility(View.INVISIBLE);
				// textViewDetails.setVisibility(View.VISIBLE);
			}
		});
		textViewDetails.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				animationSlideInRight = AnimationUtils.loadAnimation(
						getApplicationContext(), R.anim.slide_in_right_slow);
				animationSlideInLeft = AnimationUtils.loadAnimation(
						getApplicationContext(), R.anim.slide_in_left_slow);
				animationSlideOutRight = AnimationUtils.loadAnimation(
						getApplicationContext(), R.anim.slide_out_right_slow);
				animationSlideOutLeft = AnimationUtils.loadAnimation(
						getApplicationContext(), R.anim.slide_out_left_slow);
				if (!scrollView.isShown()) {
					scrollView.clearAnimation();
					scrollView.setAnimation(animationSlideInRight);
					relativeLayoutMap.setAnimation(animationSlideOutLeft);
					scrollView.setVisibility(View.VISIBLE);
					textViewDetails.setText("MAP");

				} else {
					scrollView.clearAnimation();
					scrollView.setAnimation(animationSlideOutRight);
					relativeLayoutMap.setAnimation(animationSlideInLeft);
					scrollView.setVisibility(View.INVISIBLE);

					textViewDetails.setText("DETAILS");
				}
			}
		});

	}

	public static Bitmap createStartRideThumbDrawable(int drawable) {
		float scale = Math.min(ASSL.Xscale(), ASSL.Yscale());
		int width = (int) (97.0f * scale);
		int height = (int) (131.0f * scale);
		Bitmap mDotMarkerBitmap = Bitmap.createBitmap(width, height,
				Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(mDotMarkerBitmap);
		Drawable shape = context.getResources().getDrawable(drawable);
		shape.setBounds(0, 0, mDotMarkerBitmap.getWidth(),
				mDotMarkerBitmap.getHeight());
		shape.draw(canvas);
		return mDotMarkerBitmap;
	}

	public static void addMarker(LatLng latLng) {

		map.clear();
		map.addMarker(new MarkerOptions()
				.position(
						new LatLng(Double.parseDouble(Data.getInfoOf(context,
								Data.DroppedLatitude)), Double.parseDouble(Data
								.getInfoOf(context, Data.DroppedLongitude))))
				.title("Drop Location")
				.icon(BitmapDescriptorFactory
						.fromBitmap(createStartRideThumbDrawable(R.drawable.cust_location)))
				.snippet(Data.getInfoOf(context, "drop_location_address")));
		new CreatePathAsyncTask(new LatLng(Double.parseDouble(Data.getInfoOf(
				context, Data.DroppedLatitude)), Double.parseDouble(Data
				.getInfoOf(context, Data.DroppedLongitude))), latLng).execute();

		MarkerOptions markerOptions = new MarkerOptions();
		markerOptions.title("Distance from your location");
		markerOptions
				.snippet(String.format("%.2f", Float.parseFloat(distance(
						(new LatLng(Double.parseDouble(Data.getInfoOf(context,
								Data.DroppedLatitude)), Double.parseDouble(Data
								.getInfoOf(context, Data.DroppedLongitude)))),
						latLng)))
						+ " km");
		markerOptions
				.position(latLng)
				.icon(BitmapDescriptorFactory
						.fromBitmap(createStartRideThumbDrawable(R.drawable.driver_locatio)));
		destMarker = map.addMarker(markerOptions);
	}

	static String distance(LatLng start, LatLng end) {
		try {
			Location location1 = new Location("locationA");
			location1.setLatitude(start.latitude);
			location1.setLongitude(start.longitude);
			Location location2 = new Location("locationA");
			location2.setLatitude(end.latitude);
			location2.setLongitude(end.longitude);

			double distance = location1.distanceTo(location2);
			distance = distance / 1000.0;
			Log.e("", "");

			return "" + distance;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";

	}

	static class CreatePathAsyncTask extends AsyncTask<Void, Void, String> {
		String url;

		CreatePathAsyncTask(LatLng source, LatLng destination) {
			this.url = makeURLPath(source, destination);
		}

		// http://maps.googleapis.com/maps/api/directions/json?origin=30.7342187,76.78088307&destination=30.74571777,76.78635478&sensor=false&mode=driving&alternatives=false
		public String makeURLPath(LatLng source, LatLng destination) {
			StringBuilder urlString = new StringBuilder();
			urlString
					.append("http://maps.googleapis.com/maps/api/directions/json");
			urlString.append("?origin=");// from
			urlString.append(Double.toString(source.latitude));
			urlString.append(",");
			urlString.append(Double.toString(source.longitude));
			urlString.append("&destination=");// to
			urlString.append(Double.toString(destination.latitude));
			urlString.append(",");
			urlString.append(Double.toString(destination.longitude));
			urlString.append("&sensor=false&mode=driving&alternatives=false");

			// Log.e("urlString", "" + urlString.toString());

			return urlString.toString();
		}

		public void drawPath(String result) {
			try {

				final JSONObject json = new JSONObject(result);

				// Log.e("result", "==" + result);

				JSONObject leg0 = json.getJSONArray("routes").getJSONObject(0)
						.getJSONArray("legs").getJSONObject(0);
				double distanceOfPath = leg0.getJSONObject("distance")
						.getDouble("value");
				distanceString = leg0.getJSONObject("distance").getString(
						"text");
				timeString = leg0.getJSONObject("duration").getString("text");

				int min = (int) Math.ceil(Integer.parseInt(Data.getInfoOf(
						context, Data.customer_estimate_time)) / 60.0);

				if (Data.getInfoOf(context, Data.customer_estimate_time)
						.equals("customer_estimate_time")) {
					textViewTime.setText("Waiting...");
				} else if (Integer.parseInt(Data.getInfoOf(context,
						Data.customer_estimate_time)) < 60) {
					textViewTime.setText("less than a min");
				} else {
					// int est =
					// Integer.parseInt(Data.customer_estimate_time)/60;
					textViewTime.setText(min + " mins");
				}

				//
				// JSONArray routeArray = json.getJSONArray("routes");
				// JSONObject routes = routeArray.getJSONObject(0);
				// JSONObject overviewPolylines = routes
				// .getJSONObject("overview_polyline");
				// String encodedString = overviewPolylines.getString("points");
				// List<LatLng> list = decodePoly(encodedString);
				// PolylineOptions options = new PolylineOptions().width(8)
				// .color(Color.parseColor("#4890e8")).geodesic(true);
				// for (int z = 0; z < list.size(); z++) {
				// LatLng point = list.get(z);
				// options.add(point);
				// }
				// map.addPolyline(options);

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		private List<LatLng> decodePoly(String encoded) {

			List<LatLng> poly = new ArrayList<LatLng>();
			int index = 0, len = encoded.length();
			int lat = 0, lng = 0;

			while (index < len) {
				int bInt, shift = 0, result = 0;
				do {
					bInt = encoded.charAt(index++) - 63;
					result |= (bInt & 0x1f) << shift;
					shift += 5;
				} while (bInt >= 0x20);
				int dlat = ((result & 1) == 0 ? (result >> 1) : ~(result >> 1));
				lat += dlat;

				shift = 0;
				result = 0;
				do {
					bInt = encoded.charAt(index++) - 63;
					result |= (bInt & 0x1f) << shift;
					shift += 5;
				} while (bInt >= 0x20);
				int dlng = ((result & 1) == 0 ? (result >> 1) : ~(result >> 1));
				lng += dlng;

				LatLng pLatLng = new LatLng((((double) lat / 1E5)),
						(((double) lng / 1E5)));
				poly.add(pLatLng);
			}
			return poly;
		}

		@Override
		protected void onPreExecute() {
			// progress.setVisibility(View.VISIBLE);
			super.onPreExecute();
		}

		@Override
		protected String doInBackground(Void... params) {
			return new SimpleJSONParser().getJSONFromUrl(url);
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			if (result != null) {
				drawPath(result);
			}
			// progress.setVisibility(View.GONE);
		}
	}

	@Override
	protected void onResume() {
		if (Data.getInfoOf(getApplicationContext(), "GCM").equals("40")) {
			Data.saveInfoOf(getApplicationContext(), "GCM", "40");
			stopService(new Intent(DriverLocationFinder.this,
					MyServiceCust.class));
			Intent intent = new Intent(DriverLocationFinder.this, Receipt.class);
			startActivity(intent);
			finish();
			overridePendingTransition(R.anim.slide_in_right, R.anim.hold);
		} else {
			startService(new Intent(this, MyServiceCust.class));
			ListofMeals.pushUpdater = this;
			// startService(i);
		}
		super.onResume();

	}

	protected void onPause() {
		super.onPause();
		Log.d("onPause", "onPause");
		if (isMyServiceRunning()) {
			stopService(new Intent(DriverLocationFinder.this,
					MyServiceCust.class));
		} else {
			Log.e("service running not", "-===");
		}

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

						if (GCMIntentService.flag.equals("40")) {
							Data.saveInfoOf(getApplicationContext(), "GCM",
									"40");
							stopService(new Intent(DriverLocationFinder.this,
									MyServiceCust.class));
							Intent intent = new Intent(
									DriverLocationFinder.this, Receipt.class);
							startActivity(intent);
							finish();
							overridePendingTransition(R.anim.slide_in_right,
									R.anim.hold);
						}
						if (GCMIntentService.flag.equals("41")) {
							startService(new Intent(DriverLocationFinder.this,
									MyServiceCust.class));
							Intent intent = new Intent(
									DriverLocationFinder.this,
									DriverLocationFinder.class);
							startActivity(intent);
							finish();
							overridePendingTransition(R.anim.fade_out_d,
									R.anim.hold);
						}

					}
				});
			}
		}).start();
	}

	@Override
	public void onBackPressed() {
		if (Data.getInfoOf(getApplicationContext(), "GCM").equals("")) {
			overridePendingTransition(R.anim.hold, R.anim.slide_out_right);
			finish();
		}

	}

	private boolean isMyServiceRunning() {
		ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
		for (RunningServiceInfo service : manager
				.getRunningServices(Integer.MAX_VALUE)) {
			if (MyServiceCust.class.getName().equals(
					service.service.getClassName())) {
				return true;
			}
		}
		return false;
	}

	public static void cancelNotification(Context ctx, int notifyId) {
		String ns = Context.NOTIFICATION_SERVICE;
		NotificationManager nMgr = (NotificationManager) ctx
				.getSystemService(ns);
		nMgr.cancel(notifyId);
	}

	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		Utils.buildAlertMessageNoGps(DriverLocationFinder.this);
		super.onWindowFocusChanged(hasFocus);
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		EasyTracker.getInstance(DriverLocationFinder.this).activityStart(this);
		// super.onStart();
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		EasyTracker.getInstance(DriverLocationFinder.this).activityStop(this);
		// super.onStart();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		// stopService(i);
	}
}
