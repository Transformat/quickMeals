package com.bitekite.adpters;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import rmn.androidscreenlibrary.ASSL;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bitekite.AddressCheckout;
import com.bitekite.MainActivity;
import com.bitekite.R;
import com.bitekite.driver.Httppost_Links;
import com.bitekite.utils.Data;
import com.bitekite.utils.DialogPopup;
import com.bitekite.utils.InternetCheck;
import com.flurry.android.FlurryAgent;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class AddressCheckoutAdapter extends BaseAdapter{

	Activity activity;
	Geocoder geocoder;
	String zipCode;
	String address;
	 ArrayList<Address>retList;	

	private String[] place_name, place_address;

	// private TextView name, address;

	private static LayoutInflater inflater = null;

	ViewHolder holder;

	public AddressCheckoutAdapter(Activity a, String[] name, String[] address) {

		activity = a;
		place_name = name;
		place_address = address;

		inflater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

	}

	public int getCount() {
		return place_name.length;
	}

	public Object getItem(int position) {
		return position;
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(final int position, View convertView, ViewGroup parent) {
		View vi = convertView;
		if (convertView == null) {

			holder = new ViewHolder();
			vi = inflater.inflate(R.layout.list_item_location_adapter, null);
			holder.tvname = (TextView) vi.findViewById(R.id.name);
			holder.tvaddress = (TextView) vi.findViewById(R.id.address);
			holder.ll = (RelativeLayout) vi.findViewById(R.id.LinearLayoutitem);
			holder.ll.setLayoutParams(new ListView.LayoutParams(
					ListView.LayoutParams.MATCH_PARENT, 104));
			ASSL.DoMagic(holder.ll);
			vi.setTag(holder);
			// holder.tvname.setTag(holder);
			// holder.tvaddress.setTag(holder);
			// holder.ll.setTag(holder);
		}

		else {
			holder = new ViewHolder();
			vi = inflater.inflate(R.layout.list_item_location_adapter, null);
			holder.tvname = (TextView) vi.findViewById(R.id.name);
			holder.tvaddress = (TextView) vi.findViewById(R.id.address);
			holder.ll = (RelativeLayout) vi.findViewById(R.id.LinearLayoutitem);
			holder.ll.setLayoutParams(new ListView.LayoutParams(
					ListView.LayoutParams.MATCH_PARENT, 104));
			ASSL.DoMagic(holder.ll);
			// Log.e("panga is just happen aggain in else",""+position);
		}

		holder.tvname.setTypeface(Data.bariol_bold(activity));
		holder.tvaddress.setTypeface(Data.bariol_regular(activity));

		holder.pos = position;
		holder.tvname.setText("" + place_name[position]);
		holder.tvaddress.setText("");// + place_address[position]);

		holder.ll.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.i("item position : ", position + "........"
						+ place_name[position]);

				AddressCheckout.MapSettledFlag=1;
//				Data.selectedplace = place_name[position];
				
				address= place_name[position];
				Data.S_ADDRESS=address;
				AddressCheckout.searchbox.setText(address);
//				AddressCheckout.editTextSearch.setText(address);
				InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
	            imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0); // hide
//				convertAddress();
	            
	            findlatlong();
	            
//			
			}
		});

		return vi;

	}
	
	
	
	void serverCallCheckZipCode(Double lat ,Double longe) {
		Data.loading_box(activity, "Checking...");
		RequestParams params = new RequestParams();

		params.put("access_token",
				"" + Data.getAccessToken(activity));// +Data.EMAIL);
//		params.put("zipcode", "" + zipCode);// +Data.EMAIL);
		params.put("latitude", "" +lat );// +Data.EMAIL);
		params.put("longitude", "" + longe);// +Data.EMAIL);

		AsyncHttpClient client = new AsyncHttpClient();

		client.setTimeout(Httppost_Links.SERVER_TIME_OUT_TIME);
		client.post(Httppost_Links.Baselink + "check_delivery_location", params,
				new AsyncHttpResponseHandler() {

					@Override
					public void onSuccess(String response) {
						// Log.i("request succesfull", "response = " +
						// response);
						// fillServerData(response);
						Data.loading_box_stop();
						JSONObject res;
						try {
							Data.ZIPCODE=zipCode;
							res = new JSONObject(response);
							Log.d("response", "response" + res.toString(2));
							if (res.getString("status").equals("2")) {
								// Data.alertMessageBox(SplashScreen.this,popup.getString("title"),popup.getString("text"),Data.getInfoOf(getApplicationContext(),
								// Data.loginFrom));
								Intent intent = new Intent(
										activity,
										MainActivity.class);
								activity.startActivity(intent);
								activity.finish();
							} else {

								if (res.has("error")) {
									Data.ZipCodeFlag = 0;
									Log.d("Data.SERVER_ERROR_MSG",
											"Data.SERVER_ERROR_MSG"
													+ res.getString("error"));
									new DialogPopup().alertPopup(
											activity, "OOPS!", ""
													+ res.getString("error"));

								} else {
									Data.ZipCodeFlag = 1;
									Data.ZIPCODE=zipCode;
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
						new DialogPopup().alertPopup(activity, "",
								"Server not responding\nTry again.");
					}
				});

	}
	
	
	public void convertAddress(Double lat,Double lng) {
	    if (address != null && !address.isEmpty()) {
	        try {
	                
					AddressCheckout.latitude = lat;
					AddressCheckout.longitude = lng;
					LatLng selectedLocation = new LatLng(AddressCheckout.latitude, AddressCheckout.longitude);
					
					AddressCheckout.placeListView.setVisibility(View.GONE);
					AddressCheckout.relativeLayout1.setVisibility(View.VISIBLE);
					AddressCheckout.relativeLayout2.setVisibility(View.GONE);
					
					
					AddressCheckout.googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
					AddressCheckout.googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(selectedLocation, 14));
					
					AddressCheckout.googleMap.animateCamera(CameraUpdateFactory.zoomTo(14), 2000,null);
	                
							getAddressGivenLatLongFrom(lat,lng, activity);
							
	        } catch (Exception e) {
	            e.printStackTrace();
	        } // end catch
	    } // end if
	} 
	
	public void findlatlong()

	{
		final ProgressDialog dialog = ProgressDialog.show(activity,
				"", "Loading... ", true);
		RequestParams params = new RequestParams();
		// params.put("user_access_token",Data.access_token);
		// params.put("latitude", Data.lati);
		// params.put("longitude", Data.longi);
		Log.d(address, address);
		String ignr2 = null;
		try {
			ignr2 = "https://maps.googleapis.com/maps/api/geocode/json?address=" 
					+ URLEncoder.encode(address,"utf8")+
					"&key=AIzaSyDA3lQ5nQetpndjXPmsgzXJ_W53uTbM5jk";
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
//		ignr2 = ignr2.replaceAll(" ", "%20");

		AsyncHttpClient client = new AsyncHttpClient();
		client.post(ignr2, params, new AsyncHttpResponseHandler() {

			@Override
			public void onSuccess(String response) {

				Log.i("request result", response);
				try {
					JSONArray info = null;
					JSONObject jj = new JSONObject(response);
					
					info = jj.getJSONArray("results");
					if (info.length() == 0) {
						Toast.makeText(activity,
								"No location found.", 500).show();
					}

					for (int a = 0; a < info.length(); a++) {
						JSONObject first = info.getJSONObject(a);
						Log.i("first" + a, "" + first);
					}

//					Data.resultsname = new String[info.length()];
//					Data.resultsaddress = new String[info.length()];
					Data.latitudes = new Double[info.length()];
					Data.longitudes = new Double[info.length()];

					for (int i = 0; i < info.length(); i++) {
						// printing the values to the logcat
						try {
//							Data.resultsname[i] = info.getJSONObject(i)
//									.getString("description");
//							Data.resultsaddress[i] = info.getJSONObject(i)
//									.getString("formatted_address");
							Data.latitudes[i] = info.getJSONObject(i)
									.getJSONObject("geometry")
									.getJSONObject("location").getDouble("lat");
							Data.longitudes[i] = info.getJSONObject(i)
									.getJSONObject("geometry")
									.getJSONObject("location").getDouble("lng");
							Log.v("name..........", "" +info.getJSONObject(i).getString("formatted_address"));
							Log.v(" latitude", "" + Data.latitudes[i]);
							Log.v(" longitude", "" + Data.longitudes[i]);
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							// FlurryAgent.logEvent(TAG + "1" + e.toString());
						}
					}
					convertAddress(Data.latitudes[0], Data.longitudes[0]);
//
//					for (int i = 0; i < Data.resultsname.length; i++) {
//						Log.i("Results name : ....", "" + Data.latitudes[i]);
//					}
//
//					placeListView.setVisibility(View.VISIBLE);
//					AddressCheckoutAdapter adapter = new AddressCheckoutAdapter(
//							AddressCheckout.this, Data.resultsname,
//							Data.resultsaddress);
//					placeListView.setAdapter(adapter);
				} catch (Exception e) {
					e.printStackTrace();
					// FlurryAgent.logEvent(TAG + "3" + e.toString());
					Log.e("NO DATAFETCH_POSTEXE", e.toString());
				}
				dialog.cancel();
			}

			@Override
			public void onFailure(Throwable arg0) {

				Log.e("request fail", arg0.toString());
				dialog.cancel();
//				new DialogPopup().alertPopup(AddressCheckout.this, "",
//						"Server not responding\nTry again.");
				// new AlertDialog.Builder(KikitLocation.this)
				// .setTitle("Error")
				// .setMessage("Oops!! some error Occurs. Please try again later.")
				// .setPositiveButton(android.R.string.ok, null)
				// .show();
				// error_view.setText(Data.failure_error_msg);
				// error_view.setVisibility(0);
				// error_gone();
			}
		});

	}
	public Bitmap createStartRideThumbDrawable(int drawable) {
		float scale = Math.min(ASSL.Xscale(), ASSL.Yscale());
		int width = (int) (97.0f * scale);
		int height = (int) (131.0f * scale);
		Bitmap mDotMarkerBitmap = Bitmap.createBitmap(width, height,
				Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(mDotMarkerBitmap);
		Drawable shape = activity.getResources().getDrawable(drawable);
		shape.setBounds(0, 0, mDotMarkerBitmap.getWidth(),
				mDotMarkerBitmap.getHeight());
		shape.draw(canvas);
		return mDotMarkerBitmap;
	}
	
	public List<Address> getAddressGivenLatLongFrom(final double latitude,
			final double longitude, final Activity activity) {
		Log.d("getAddressGivenLatLongFrom", "getAddressGivenLatLongFrom");
		RequestParams params = new RequestParams();
		String uri = "http://maps.google.com/maps/api/geocode/json?latlng="
				+ latitude + "," + longitude + "&sensor=false?key="+Httppost_Links.BrowserKey;
		uri = uri.replaceAll(" ", "%20");
		Log.d("uri","uri"+uri);
		AsyncHttpClient client = new AsyncHttpClient();
		client.setTimeout(Httppost_Links.SERVER_TIME_OUT_TIME);
		client.post(uri, params, new AsyncHttpResponseHandler() {
//			

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
						 List <String> ziparray = new ArrayList<String>();
						
						JSONObject jsonObject = new JSONObject(response);
//						Log.i("responseeee ", "-----------" + response);
						retList = new ArrayList<Address>();
						JSONArray results = jsonObject.getJSONArray("results");
						for (int i = 0; i < results.length(); i++) {
							JSONObject result = results.getJSONObject(i);
							 indiStr = result.getString("formatted_address");
							 
//							 Log.d("111","111");
							 JSONArray array = result.getJSONArray("address_components");
							 for(int j=0;j<array.length();j++)
							 {
//								 Log.d("222","222");
								 JSONObject js = array.getJSONObject(j);
//								 Log.d("js","js"+js.toString());
								 JSONArray jsoo = js.getJSONArray("types");
//								 String comp=  jsoo.getString(0);
//								 Log.d(" jsoo.getString(0)"," jsoo.getString(0)"+ jsoo.getString(0));
								 if(jsoo.getString(0).equals("postal_code"))					
								{
									 ziparray.add(js.getString("long_name"));
									 break;
								 }
								
							 }
							 
							Address addr = new Address(Locale.getDefault());
							addr.setAddressLine(0, indiStr);
							retList.add(addr);
						}
						List<Address> addresses;
						addresses=retList;
					
						String address = addresses.get(0).getAddressLine(0);
						String city = addresses.get(0).getAddressLine(1);
						String country = addresses.get(0).getAddressLine(2);
						
					if (InternetCheck.getInstance(activity)
							.isOnline(activity)) {
								serverCallCheckZipCode(latitude,longitude);
							} else {
								new DialogPopup().alertPopup(activity, "",
										"Check your internet connection");
							}
						
					}
				} catch (Exception exception) {
					exception.printStackTrace();
				}
			}
			
			@Override
			public void onFailure(Throwable arg0) {
				
//				new DialogPopup().alertPopup(activity, "",
//						"Google Server not responding\nTry again.");
			}
			
		});
		return retList;
	}
	
	class ViewHolder {

		int pos;
		RelativeLayout ll;
		TextView tvname, tvaddress;

	}
}
