package com.bitekite;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import rmn.androidscreenlibrary.ASSL;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bitekite.adpters.AddressCheckoutAdapter;
import com.bitekite.driver.Httppost_Links;
import com.bitekite.driver.Utils;
import com.bitekite.utils.Data;
import com.bitekite.utils.DialogPopup;
import com.bitekite.utils.InternetCheck;
import com.google.analytics.tracking.android.EasyTracker;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class AddressCheckout extends FragmentActivity {

	public static GoogleMap googleMap;
	public static double latitude = 0.0;
	public static double longitude = 0.0;
	TextView textViewAddressLocation;
	public static TextView searchbox;
	public static EditText editTextSearch;
	public static ListView placeListView;
	Button buttonCancel, buttonDone, buttonCurrentLocation;
	// ImageView imageViewSearch;
	RelativeLayout relativeLayoutAddressLocation;
	public static RelativeLayout relativeLayout1, relativeLayout2;
	String zipCode;
	int loadingFlag = 0;
	ProgressDialog dialog = null;

	ImageView imageView2;
	Button buttonAddressFound;
	private TouchableMapFragment mapFragment;
	ArrayList<Address> retList;
	public static int MapSettledFlag = 0;
	Context context;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_address_checkout);

		relativeLayoutAddressLocation = (RelativeLayout) findViewById(R.id.relativeLayoutAddressLocation);
		new ASSL(this, relativeLayoutAddressLocation, 1134, 720, false);

		context= AddressCheckout.this;
		relativeLayout1 = (RelativeLayout) findViewById(R.id.relativeLayout1);
		relativeLayout2 = (RelativeLayout) findViewById(R.id.relativeLayout2);
		textViewAddressLocation = (TextView) findViewById(R.id.textViewCreateAccount);
		searchbox = (TextView) findViewById(R.id.textViewSearchBox);
		placeListView = (ListView) findViewById(R.id.listView1);
		buttonCancel = (Button) findViewById(R.id.buttonCancel);
		editTextSearch = (EditText) findViewById(R.id.editTextSearch);
		// imageViewSearch = (ImageView) findViewById(R.id.imageViewSearch);
		buttonDone = (Button) findViewById(R.id.buttonDone);

		buttonAddressFound = (Button) findViewById(R.id.buttonAddressFound);

		imageView2 = (ImageView) findViewById(R.id.imageView2);

		textViewAddressLocation.setTypeface(Data
				.bariol_bold(getApplicationContext()));
		searchbox.setTypeface(Data.bariol_regular(getApplicationContext()));
		buttonCancel.setTypeface(Data.bariol_regular(getApplicationContext()));
		buttonDone.setTypeface(Data.bariol_regular(getApplicationContext()));
		editTextSearch
				.setTypeface(Data.bariol_regular(getApplicationContext()));

		if (!editTextSearch.getText().toString().equals("")) {
			imageView2.setVisibility(View.VISIBLE);
		} else {
			buttonAddressFound.setVisibility(View.GONE);
		}

		
		
		imageView2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				editTextSearch.setText("");
				imageView2.setVisibility(View.GONE);
				buttonAddressFound.setVisibility(View.GONE);
			}
		});

		editTextSearch.setImeOptions(EditorInfo.IME_ACTION_DONE);
		editTextSearch.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub
				new LongOperation().execute("");
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				if (editTextSearch.getText().toString().trim().length() == 0) {
					imageView2.setVisibility(View.GONE);
				} else {
					imageView2.setVisibility(View.VISIBLE);
				}
			}
		});

		buttonCurrentLocation = (Button) findViewById(R.id.buttonCurrentLocation);

		buttonCurrentLocation.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				getMyLocation();
			}
		});

		relativeLayout1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				relativeLayout2.setVisibility(View.VISIBLE);
				relativeLayout1.setVisibility(View.GONE);
				editTextSearch.setText(Data.selectedplace);
				InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.showSoftInput(editTextSearch,
						InputMethodManager.SHOW_IMPLICIT);
				buttonAddressFound.setVisibility(View.GONE);
			}
		});
		
		latitude = Data.seletedlat;
		longitude = Data.selectedlng;

		buttonDone.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				 Data.seletedlat = latitude;
				 Data.selectedlng = longitude ;
				 Data.selectedplace = Data.S_ADDRESS;
				
				Data.LATITUDE = Data.seletedlat.toString();
				Data.LONGITUDE = Data.selectedlng.toString();
				
				googleMap = null;
				Data.Flag = 1; // flag for coming back from map to create
								// profile directly
								// Data.selectedplace = Data.S_ADDRESS;
				try {
					InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
					imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY,
							0); // hide}
				} catch (Exception e) {
				}
				finish();
				overridePendingTransition(R.anim.slide_in_left,
						R.anim.slide_out_right);
			}
		});

		searchbox
				.setTypeface(Data.Helvetica_NeueLTStd(getApplicationContext()));

		searchbox.setText(Data.selectedplace);

		try {
			// Loading map
			initilizeMap();

		} catch (Exception e) {
			e.printStackTrace();
		}

		buttonCancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Data.Flag = 1;
				googleMap = null;
				finish();
				overridePendingTransition(R.anim.slide_in_left,
						R.anim.slide_out_right);
			}
		});
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		googleMap = null;
		Data.Flag = 1;
		finish();
		overridePendingTransition(R.anim.slide_in_left,
				R.anim.slide_out_right);
	}

	@Override
	protected void onResume() {
		super.onResume();
//		Data.loading_box(getApplicationContext(), "fetching...");
	}

	/**
	 * function to load map. If map is not created it will create it for you
	 * */
	@SuppressLint("NewApi")
	private void initilizeMap() {
		Log.d("123", "123");
		if (googleMap == null) {
			Log.d("12323", "12323");

			googleMap = ((SupportMapFragment) getSupportFragmentManager()
					.findFragmentById(R.id.map)).getMap();
			mapFragment = ((TouchableMapFragment) getSupportFragmentManager()
					.findFragmentById(R.id.map));
			googleMap.getUiSettings().setZoomControlsEnabled(false);
			googleMap.setMyLocationEnabled(false);
			googleMap.setMyLocationEnabled(true);
			googleMap.getUiSettings().setMyLocationButtonEnabled(false);
			googleMap.getUiSettings().setZoomGesturesEnabled(false);
			new MapStateListener(googleMap, mapFragment, this) {
				@Override
				public void onMapTouched() {
					// Map touched
				}

				@Override
				public void onMapReleased() {
					// Map released
				}

				@Override
				public void onMapUnsettled() {
					// Map unsettled
				}

				@Override
				public void onMapSettled() {
					// Map settled
					try {
						if (MapSettledFlag == 0) {
							LatLng centre = googleMap.getCameraPosition().target;
							latitude= centre.latitude;
							longitude = centre.longitude;
							Data.loading_box(context, "Checking...");
							getAddressGivenLatLongFrom(latitude,longitude,AddressCheckout.this);
						} else {
							MapSettledFlag = 0;
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			};

//			latitude = Data.seletedlat;
//			longitude = Data.selectedlng;
//			String lat = new Double(latitude).toString();
//			String longi = new Double(longitude).toString();
			googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
			LatLng selectedLocation = new LatLng(latitude, longitude);
			googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
					selectedLocation, 15));
			googleMap.animateCamera(CameraUpdateFactory.zoomTo(14), 2000, null);
			if (googleMap == null) {
			}
		}
	}

	public void google_places() {
		if (loadingFlag == 1) {
			dialog = ProgressDialog.show(AddressCheckout.this, "",
					"Loading... ", true);
		}

		RequestParams params = new RequestParams();

		String ignr2 = "";
		try {
			ignr2 = "https://maps.googleapis.com/maps/api/place/autocomplete/json?"
					+ "input="
					+ URLEncoder.encode(editTextSearch.getText().toString(),
							"utf8")
					+ "&type=address&location="
					+ Data.lati
					+ ","
					+ Data.longi
					+ "&radius=500"
					+ "&key=AIzaSyDA3lQ5nQetpndjXPmsgzXJ_W53uTbM5jk";
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Log.e("ignr2********************* ", "ignr2********************* "+ ignr2);
		AsyncHttpClient client = new AsyncHttpClient();
		client.post(ignr2, params, new AsyncHttpResponseHandler() {

			@Override
			public void onSuccess(String response) {

				Log.i("request result", "response ="+response.toString());
				try {
					JSONArray info = null;
					JSONObject jj = new JSONObject(response);
					info = jj.getJSONArray("predictions");
					if (info.length() == 0) {
						if (editTextSearch.getText().toString().length() == 0) {
						} else {
							buttonAddressFound.setVisibility(View.VISIBLE);
						}
					} else {
						buttonAddressFound.setVisibility(View.GONE);
					}

					for (int a = 0; a < info.length(); a++) {
						JSONObject first = info.getJSONObject(a);
						Log.i("first" + a, "" + first);
					}

					Data.resultsname = new String[info.length()];
					Data.resultsaddress = new String[info.length()];
					Data.latitudes = new Double[info.length()];
					Data.longitudes = new Double[info.length()];

					for (int i = 0; i < info.length(); i++) {
						// printing the values to the logcat
						try {
							Data.resultsname[i] = "";
							JSONArray jsonArray2 = info.getJSONObject(i)
									.getJSONArray("terms");

							if (jsonArray2.length() == 1) {
								for (int j = 0; j < jsonArray2.length(); j++) {

									Data.resultsname[i] = Data.resultsname[i]
											+ jsonArray2.getJSONObject(j)
													.getString("value") + ", ";
								}
							} else {
								for (int j = 0; j < jsonArray2.length() - 1; j++) {

									Data.resultsname[i] = Data.resultsname[i]
											+ jsonArray2.getJSONObject(j)
													.getString("value") + ", ";
								}
							}
							Data.resultsname[i] = Data.resultsname[i]
									.toString().substring(
											0,
											Data.resultsname[i].toString()
													.length() - 2);
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}

					for (int i = 0; i < Data.resultsname.length; i++) {
						Log.i("Results name : ....", "" + Data.resultsname[i]);
					}

					placeListView.setVisibility(View.VISIBLE);
					AddressCheckoutAdapter adapter = new AddressCheckoutAdapter(
							AddressCheckout.this, Data.resultsname,
							Data.resultsaddress);
					placeListView.setAdapter(adapter);
				} catch (Exception e) {
					e.printStackTrace();
					Log.e("NO DATAFETCH_POSTEXE", e.toString());
				}
				if (loadingFlag == 1) {
					dialog.cancel();
				}
			}

			@Override
			public void onFailure(Throwable arg0) {

				Log.e("request fail", arg0.toString());
				if (loadingFlag == 1) {
					dialog.cancel();
				}
				new DialogPopup().alertPopup(AddressCheckout.this, "",
						"Server not responding\nTry again.");
			}
		});

	}

	// getting address of selected lat/lng
//	public void getAddress() {
//		try {
//			Log.d("getAddress", "getAddress" + Data.seletedlat.toString()
//					+ ", " + Data.selectedlng.toString());
//			List<Address> addresses;
//
//			getAddressGivenLatLongFrom(
//					Double.parseDouble(Data.seletedlat.toString()),
//					Double.parseDouble(Data.selectedlng.toString()),
//					AddressCheckout.this);
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

	// getting address of selected lat/lng
//	public void getAddress2() {
//		try {
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

	public Bitmap createStartRideThumbDrawable(int drawable) {
		float scale = Math.min(ASSL.Xscale(), ASSL.Yscale());
		int width = (int) (97.0f * scale);
		int height = (int) (131.0f * scale);
		Bitmap mDotMarkerBitmap = Bitmap.createBitmap(width, height,
				Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(mDotMarkerBitmap);
		Drawable shape = getResources().getDrawable(drawable);
		shape.setBounds(0, 0, mDotMarkerBitmap.getWidth(),
				mDotMarkerBitmap.getHeight());
		shape.draw(canvas);
		return mDotMarkerBitmap;
	}

	void serverCallCheckZipCode(Double lat, Double longe) {
		
		RequestParams params = new RequestParams();

		params.put("access_token",
				"" + Data.getAccessToken(getApplicationContext()));// +Data.EMAIL);
		params.put("latitude", "" + lat);// +Data.EMAIL);
		params.put("longitude", "" + longe);// +Data.EMAIL);

		AsyncHttpClient client = new AsyncHttpClient();

		client.setTimeout(Httppost_Links.SERVER_TIME_OUT_TIME);
		client.post(Httppost_Links.Baselink + "check_delivery_location",
				params, new AsyncHttpResponseHandler() {

					@Override
					public void onSuccess(String response) {
						Data.loading_box_stop();
						JSONObject res;
						try {
							Data.ZIPCODE = zipCode;
							res = new JSONObject(response);
							if (res.getString("status").equals("2")) {
								Intent intent = new Intent(
										AddressCheckout.this,
										MainActivity.class);
								startActivity(intent);
								finish();
							} else {

								if (res.has("error")) {
									Data.ZipCodeFlag = 0;
									Log.d("Data.SERVER_ERROR_MSG",
											"Data.SERVER_ERROR_MSG"
													+ res.getString("error"));
									new DialogPopup().alertPopup(
											AddressCheckout.this, "OOPS!", ""
													+ res.getString("error"));

								} else {
									Data.ZipCodeFlag = 1;
									Data.ZIPCODE = zipCode;
								}
							}
						} catch (Exception e) {
							e.printStackTrace();
							Log.v("exception ", e.toString());
						}
					}

					@Override
					public void onFailure(Throwable arg0) {
						Log.e("request fail", arg0.toString());
						Data.loading_box_stop();
						new DialogPopup().alertPopup(AddressCheckout.this, "",
								"Server not responding\nTry again.");
					}
				});

	}

	private void getMyLocation() {
		latitude = Data.currentLatitude;
		longitude = Data.currentLongitude;
//		Data.LATITUDE = Data.seletedlat.toString();
//		Data.LONGITUDE = Data.selectedlng.toString();
		LatLng selectedLocation = new LatLng(latitude,longitude);
		googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
				selectedLocation, 15));
		googleMap.animateCamera(CameraUpdateFactory.zoomTo(14), 2000, null);
//		getAddress();
	}

	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		Utils.buildAlertMessageNoGps(AddressCheckout.this);
		super.onWindowFocusChanged(hasFocus);
	}

	@Override
	protected void onStart() {
		super.onStart();
		EasyTracker.getInstance(AddressCheckout.this).activityStart(this);
	}

	@Override
	protected void onStop() {
		super.onStop();
		EasyTracker.getInstance(AddressCheckout.this).activityStop(this);
	}

	private class LongOperation extends AsyncTask<String, Void, String> {
		@Override
		protected String doInBackground(String... params) {
			try {
				AddressCheckout.this.runOnUiThread(new Runnable() {
					@Override
					public void run() {
						// if(count % 3 == 0){
						google_places();
						// }
					}
				});

			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
		}

		@Override
		protected void onPreExecute() {
		}
	}

	public List<Address> getAddressGivenLatLongFrom(final double latitude,
			final double longitude, final Activity activity) {
		Log.d("getAddressGivenLatLongFrom", "getAddressGivenLatLongFrom");
		RequestParams params = new RequestParams();
		String uri = "http://maps.google.com/maps/api/geocode/json?latlng="
				+ latitude + "," + longitude + "&sensor=false?key="
				+ Httppost_Links.BrowserKey;
		uri = uri.replaceAll(" ", "%20");
		Log.d("uri", "uri" + uri);
		AsyncHttpClient client = new AsyncHttpClient();
		client.setTimeout(Httppost_Links.SERVER_TIME_OUT_TIME);
		client.post(uri, params, new AsyncHttpResponseHandler() {

			@Override
			public void onSuccess(String response) {
				try {
					JSONObject job = new JSONObject(response);
					if (response.contains("error")) {
						String error = job.getString("error");
						Log.i("Error ", "-----------" + error);
						new DialogPopup().alertPopup(activity, "", error);
					} else {
						String indiStr = "";
						List<String> ziparray = new ArrayList<String>();

						JSONObject jsonObject = new JSONObject(response);
						retList = new ArrayList<Address>();
						JSONArray results = jsonObject.getJSONArray("results");
						for (int i = 0; i < results.length(); i++) {
							JSONObject result = results.getJSONObject(i);
							indiStr = result.getString("formatted_address");

							JSONArray array = result
									.getJSONArray("address_components");
							for (int j = 0; j < array.length(); j++) {
								JSONObject js = array.getJSONObject(j);
								JSONArray jsoo = js.getJSONArray("types");
								if (jsoo.getString(0).equals("postal_code")) {
									ziparray.add(js.getString("long_name"));
									break;
								}

							}

							Address addr = new Address(Locale.getDefault());
							addr.setAddressLine(0, indiStr);
							retList.add(addr);
						}
						List<Address> addresses;
						addresses = retList;
						String address = addresses.get(0).getAddressLine(0);
						String city = addresses.get(0).getAddressLine(1);
						String country = addresses.get(0).getAddressLine(2);

						if (InternetCheck.getInstance(getApplicationContext())
								.isOnline(getApplicationContext())) {
							Log.d("*****serverCallCheckZipCode*****",
									"*****serverCallCheckZipCode*****");
							serverCallCheckZipCode(latitude, longitude);
						} else {
							new DialogPopup().alertPopup(AddressCheckout.this,
									"", "Check your internet connection");
						}

						Log.d("TAG", "address = " + address + ", city =" + city	+ ", country = " + country);
						Data.S_ADDRESS = "" + address;
						searchbox.setText("" + Data.S_ADDRESS);
					}
				} catch (Exception exception) {
					exception.printStackTrace();
				}
			}

			@Override
			public void onFailure(Throwable arg0) {
				Data.S_ADDRESS = "Deliver at pin";
				searchbox.setText("" + Data.S_ADDRESS);
				
//				new DialogPopup().alertPopup(activity, "",
//						"Google Server not responding\nTry again.");
			}

		});
		return retList;
	}

}
