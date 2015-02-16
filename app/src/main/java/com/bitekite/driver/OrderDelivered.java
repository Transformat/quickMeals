package com.bitekite.driver;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import rmn.androidscreenlibrary.ASSL;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.SystemClock;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bitekite.DriverLocationFinder;
import com.bitekite.GCMIntentService;
import com.bitekite.MainActivity;
import com.bitekite.R;
import com.bitekite.adpters.OrderDetailsDriverListAdapter;
import com.bitekite.driver.classes.DetailsBase;
import com.bitekite.driver.classes.OrderBase;
import com.bitekite.driver.classes.ToppingDetailsBase;
import com.bitekite.utils.Data;
import com.bitekite.utils.DialogPopup;
import com.bitekite.utils.InternetCheck;
import com.google.analytics.tracking.android.EasyTracker;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class OrderDelivered extends FragmentActivity implements PushUpdater {

	GoogleMap map;
	Location myLocation;
	Marker destMarker;
	String timeString, distanceString;
	TextView textViewTime, textViewName, textViewAddress, textViewDueTimer,
			textViewDeliver, textViewIn;
	Button buttonOrderDelivered;
	ListView listViewOrderDetails;
	ImageView imageViewDetailsBtn;
	ArrayList<OrderBase> orderList = new ArrayList<OrderBase>();
	Animation animationSlideInRight, animationSlideInLeft,
			animationSlideOutRight, animationSlideOutLeft;
	RelativeLayout relativeLayoutOrderDetails, relativeLayoutMap,
			relativeLayoutHidden;
	CountDownTimer sec;
	Button buttonCall;
	String callnum;
	private long startTime = 0L;
	private Handler myHandler = new Handler();
	
	ImageView imageViewDirection,imageViewMyLocation;
	RelativeLayout relativeLayoutNavigation;
	LinearLayout linearLayoutAddress;
	ImageView directionsImage;
	String Instruction="";
//	TextToSpeech ttobj;
	TextView textViewDirections,textViewDistance;
	int callFlag=0,Flag=0;
	ImageView imageViewResume,imageViewSwitch;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_order_delivered);
		new ASSL(this, (ViewGroup) findViewById(R.id.root), 1134, 720, false);
		textViewTime = (TextView) findViewById(R.id.timeTxt);
		textViewName = (TextView) findViewById(R.id.NameTxt);
		textViewAddress = (TextView) findViewById(R.id.AddressTxt);
		textViewDeliver = (TextView) findViewById(R.id.DeliverTxt);
		textViewIn = (TextView) findViewById(R.id.inTxt);
		buttonOrderDelivered = (Button) findViewById(R.id.OrderDeliveredBtn);
		listViewOrderDetails = (ListView) findViewById(R.id.listViewDetails);
		imageViewDetailsBtn = (ImageView) findViewById(R.id.toggal);
		relativeLayoutOrderDetails = (RelativeLayout) findViewById(R.id.DetailsLayout);
		relativeLayoutMap = (RelativeLayout) findViewById(R.id.mapLayout);
		textViewDueTimer = (TextView) findViewById(R.id.DueTimerTxt);
		relativeLayoutHidden = (RelativeLayout) findViewById(R.id.hiddenLayout);
		
		imageViewDirection=(ImageView)findViewById(R.id.imageViewDirection);
//		directionsImage=(ImageView)findViewById(R.id.imageViewDirections);
		textViewDirections=(TextView)findViewById(R.id.textViewDirections);
		textViewDistance=(TextView)findViewById(R.id.textViewDistance);
		relativeLayoutNavigation=(RelativeLayout)findViewById(R.id.RelativeLayoutNavigation);
		linearLayoutAddress=(LinearLayout)findViewById(R.id.AddressLayout);
		imageViewResume = (ImageView) findViewById(R.id.imageViewResume);
		imageViewSwitch = (ImageView) findViewById(R.id.imageViewSwitch);
		
		buttonCall=(Button)findViewById(R.id.buttonCall);
//		ttobj=new TextToSpeech(getApplicationContext(), 
//			      new TextToSpeech.OnInitListener() {
//			      @Override
//			      public void onInit(int status) {
//			         if(status != TextToSpeech.ERROR){
//			             ttobj.setLanguage(Locale.UK);
//			            }				
//			         }
//			      });

		startOrderServerCall();

		
		textViewTime.setTypeface(Data.bariol_bold(getApplicationContext()));
		textViewName.setTypeface(Data.bariol_bold(getApplicationContext()));
		textViewAddress.setTypeface(Data.bariol_bold(getApplicationContext()));
		textViewDueTimer.setTypeface(Data.bariol_regular(getApplicationContext()));
		textViewDeliver.setTypeface(Data.bariol_bold(getApplicationContext()));
		textViewIn.setTypeface(Data.bariol_bold(getApplicationContext()));
		buttonOrderDelivered.setTypeface(Data.bariol_regular(getApplicationContext()));
		buttonCall.setTypeface(Data.bariol_regular(getApplicationContext()));

		map = ((SupportMapFragment) getSupportFragmentManager()
				.findFragmentById(R.id.map)).getMap();
		map.setMyLocationEnabled(false);
		map.getUiSettings().setZoomControlsEnabled(false);
		
		
		imageViewMyLocation = (ImageView) findViewById(R.id.imageViewMyLocation);

		imageViewMyLocation.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			
				getMyLocation();
			}
		});
		
		
		imageViewDirection.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				String uri = String.format(Locale.ENGLISH, "http://maps.google.com/maps?saddr=%f,%f&daddr=%s,%s",
						Data.currentLatitude, Data.currentLongitude,orderList.get(0).getDropLatitude().toString(),
						orderList.get(0).getDropLongitude().toString());
				Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
				intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
				startActivity(intent);
			}
		});
		
		buttonCall.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				Intent phoneCallIntent = new Intent(Intent.ACTION_CALL);
				phoneCallIntent.setData(Uri.parse("tel:"+callnum));
				startActivity(phoneCallIntent);
			}
		});
		
		buttonOrderDelivered.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if (InternetCheck.getInstance(getApplicationContext())
						.isOnline(getApplicationContext())) {
					getOrderDeliveredServerCall();
				} else {
					new DialogPopup().alertPopup(OrderDelivered.this, "",
							"Check your internet connection");
				}

			}
		});

//		buttonOrderDelivered.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				animationSlideInRight = AnimationUtils.loadAnimation(
//						getApplicationContext(), R.anim.slide_in_right_slow);
//				animationSlideInLeft = AnimationUtils.loadAnimation(
//						getApplicationContext(), R.anim.slide_in_left_slow);
//				animationSlideOutRight = AnimationUtils.loadAnimation(
//						getApplicationContext(), R.anim.slide_out_right_slow);
//				animationSlideOutLeft = AnimationUtils.loadAnimation(
//						getApplicationContext(), R.anim.slide_out_left_slow);
//				if (!relativeLayoutNavigation.isShown()) {
//					relativeLayoutNavigation.clearAnimation();
//					relativeLayoutNavigation
//							.setAnimation(animationSlideInRight);
//					linearLayoutAddress.setAnimation(animationSlideOutLeft);
//					relativeLayoutNavigation.setVisibility(View.VISIBLE);
////					imageViewDetailsBtn
////							.setBackgroundResource(R.drawable.location_toggle_btn);
//
//				} else {
//					relativeLayoutNavigation.clearAnimation();
//					relativeLayoutNavigation
//							.setAnimation(animationSlideOutRight);
//					linearLayoutAddress.setAnimation(animationSlideInLeft);
//					relativeLayoutNavigation.setVisibility(View.INVISIBLE);
////					imageViewDetailsBtn
////							.setBackgroundResource(R.drawable.food_toggle_btn);
//				}
//
//			}
//		});
		
		imageViewDetailsBtn.setOnClickListener(new OnClickListener() {

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
				if (!relativeLayoutOrderDetails.isShown()) {
					relativeLayoutOrderDetails.clearAnimation();
					relativeLayoutOrderDetails
							.setAnimation(animationSlideInRight);
					relativeLayoutMap.setAnimation(animationSlideOutLeft);
					relativeLayoutOrderDetails.setVisibility(View.VISIBLE);
					imageViewDetailsBtn
							.setBackgroundResource(R.drawable.location_toggle_btn);

				} else {
					relativeLayoutOrderDetails.clearAnimation();
					relativeLayoutOrderDetails
							.setAnimation(animationSlideOutRight);
					relativeLayoutMap.setAnimation(animationSlideInLeft);
					relativeLayoutOrderDetails.setVisibility(View.INVISIBLE);
					imageViewDetailsBtn
							.setBackgroundResource(R.drawable.food_toggle_btn);
				}

			}
		});
		
		imageViewSwitch.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				animationSlideInRight = AnimationUtils.loadAnimation(
						getApplicationContext(), R.anim.slide_in_right_slow);
				animationSlideInLeft = AnimationUtils.loadAnimation(
						getApplicationContext(), R.anim.slide_in_left_slow);
				animationSlideOutRight = AnimationUtils.loadAnimation(
						getApplicationContext(), R.anim.slide_out_right_slow);
				animationSlideOutLeft = AnimationUtils.loadAnimation(
						getApplicationContext(), R.anim.slide_out_left_slow);
				if (!relativeLayoutNavigation.isShown()) {
					relativeLayoutNavigation.clearAnimation();
					relativeLayoutNavigation
							.setAnimation(animationSlideInRight);
					linearLayoutAddress.setAnimation(animationSlideOutLeft);
					relativeLayoutNavigation.setVisibility(View.VISIBLE);
//					imageViewDetailsBtn
//							.setBackgroundResource(R.drawable.location_toggle_btn);

				} else {

					relativeLayoutNavigation.clearAnimation();
					relativeLayoutNavigation
							.setAnimation(animationSlideOutRight);
					linearLayoutAddress.setAnimation(animationSlideInLeft);
					relativeLayoutNavigation.setVisibility(View.INVISIBLE);
//					imageViewDetailsBtn
//							.setBackgroundResource(R.drawable.food_toggle_btn);
				
				}
				
			}
		});

		
	}

	LocationListener myListener = new LocationListener() {

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
		}

		@Override
		public void onProviderEnabled(String provider) {
		}

		@Override
		public void onProviderDisabled(String provider) {
		}

		@Override
		public void onLocationChanged(Location location) {
			myLocation = location;
			Data.currentLatitude=myLocation.getLatitude();
			Data.currentLongitude=myLocation.getLongitude();
			map.addMarker(
					new MarkerOptions().position(
							new LatLng(Double.parseDouble(orderList.get(0)
									.getDropLatitude().toString()), Double
									.parseDouble(orderList.get(0)
									.getDropLongitude().toString())))
					.title(orderList.get(0).getDrop_location_address())
					.icon(BitmapDescriptorFactory
					.fromBitmap(createStartRideThumbDrawable(R.drawable.cust_location))));

			addMarker(new LatLng(myLocation.getLatitude(),
					myLocation.getLongitude()));
			new CreatePathAsyncTask(new LatLng(myLocation.getLatitude(),
					myLocation.getLongitude()), new LatLng(
					Double.parseDouble(orderList.get(0).getDropLatitude()
							.toString()), Double.parseDouble(orderList.get(0)
							.getDropLongitude().toString()))).execute();
			CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(
					new LatLng(myLocation.getLatitude(),
							myLocation.getLongitude()), 15);
			map.animateCamera(cameraUpdate);

		}
	};

	public void addMarker(LatLng latLng) {
		map.clear();
		map.addMarker(
				new MarkerOptions().position(
						new LatLng(Double.parseDouble(orderList.get(0)
								.getDropLatitude().toString()), Double
								.parseDouble(orderList.get(0)
										.getDropLongitude().toString())))
				.title(orderList.get(0).getDrop_location_address())
				.icon(BitmapDescriptorFactory
						.fromBitmap(createStartRideThumbDrawable(R.drawable.cust_location))));
		MarkerOptions markerOptions = new MarkerOptions();
		markerOptions.title("Distance from your location");
//		Log.e("D======","======"+myLocation.getLatitude()+"=="+ myLocation.getLongitude()+ latLng+" km");
		
		markerOptions.snippet(String.format("%.2f", Float.parseFloat(distance(
				new LatLng(Double.parseDouble(orderList.get(0)
						.getDropLatitude().toString()), Double
						.parseDouble(orderList.get(0)
								.getDropLongitude().toString())), latLng)))+" km");
		markerOptions.position(latLng).icon(BitmapDescriptorFactory
				.fromBitmap(createStartRideThumbDrawable(R.drawable.driver_locatio)));
		destMarker = map.addMarker(markerOptions);
	}

	String distance(LatLng start, LatLng end) {
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

	class CreatePathAsyncTask extends AsyncTask<Void, Void, String> {
		String url;

		CreatePathAsyncTask(LatLng source, LatLng destination) {
			this.url = makeURLPath(source, destination);
		}

		// http://maps.googleapis.com/maps/api/directions/json?origin=30.7342187,76.78088307&destination=30.74571777,76.78635478&sensor=false&mode=driving&alternatives=false
//		public String makeURLPath(LatLng source, LatLng destination) {
//			StringBuilder urlString = new StringBuilder();
//			urlString
//					.append("http://maps.googleapis.com/maps/api/directions/json");
//			urlString.append("?origin=");// from
//			urlString.append(Double.toString(source.latitude));
//			urlString.append(",");
//			urlString.append(Double.toString(source.longitude));
//			urlString.append("&destination=");// to
//			urlString.append(Double.toString(destination.latitude));
//			urlString.append(",");
//			urlString.append(Double.toString(destination.longitude));
//			urlString.append("&sensor=false&mode=driving&alternatives=false");
//
//			Log.e("urlString", "" + urlString.toString());
//
//			return urlString.toString();
//		}
		
//		// http://maps.googleapis.com/maps/api/directions/json?origin=30.7342187,76.78088307&destination=30.74571777,76.78635478&sensor=false&mode=driving&alternatives=false
				public String makeURLPath(LatLng source, LatLng destination) {
					StringBuilder urlString = new StringBuilder();
					urlString
							.append("http://api.tiles.mapbox.com/v4/directions/mapbox.driving/");
					urlString.append("");// from
					urlString.append(Double.toString(source.longitude));
					urlString.append(",");
					urlString.append(Double.toString(source.latitude));
					urlString.append(";");// to
					urlString.append(Double.toString(destination.longitude));
					urlString.append(",");
					urlString.append(Double.toString(destination.latitude));
					urlString.append(".json?access_token=pk.eyJ1Ijoia2F1c2hhbDEyMzQiLCJhIjoibFJQNHROSSJ9.SjcPrpRJHNyrfkk-AJEZ1g");

					Log.e("urlString", "" + urlString.toString());

					return urlString.toString();
				}

		public void drawPath(String result) {
//			try {
//				Log.e("result", "==" + result);
//				final JSONObject json = new JSONObject(result);
//				Log.e("result", "==" + result);
//				JSONObject leg0 = json.getJSONArray("routes").getJSONObject(0).getJSONArray("legs").getJSONObject(0);
//				double distanceOfPath = leg0.getJSONObject("distance").getDouble("value");
//				distanceString = leg0.getJSONObject("distance").getString("text");
//				timeString = leg0.getJSONObject("duration").getString("text");
//				textViewTime.setText(timeString);
//				JSONArray routeArray = json.getJSONArray("routes");
//				JSONObject routes = routeArray.getJSONObject(0);
//				JSONObject overviewPolylines = routes.getJSONObject("overview_polyline");
//				String encodedString = overviewPolylines.getString("points");
//				List<LatLng> list = decodePoly(encodedString);
//				PolylineOptions options = new PolylineOptions().width(8).color(Color.parseColor("#4890e8")).geodesic(true);
//				for (int z = 0; z < list.size(); z++) {
//					LatLng point = list.get(z);
//					options.add(point);
//				}
//				map.addPolyline(options);
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
			

			try {
				Log.e("result", "==" + result.toString());
				final JSONObject json = new JSONObject(result);

				Log.e("result", "==" + result);

				JSONObject leg0 = json.getJSONArray("routes").getJSONObject(0).getJSONArray("steps").getJSONObject(0);
				JSONObject leg1 = json.getJSONArray("routes").getJSONObject(0).getJSONArray("steps").getJSONObject(1);
				
		
//				for(int i=0;i<json.getJSONArray("routes").getJSONObject(0).getJSONArray("steps").length()-1;i++)
//				{
//					seconds = seconds + Integer.parseInt(json.getJSONArray("routes").getJSONObject(0).getJSONArray("steps").getJSONObject(i).getString("duration"));
//				}
//				Float.parseFloat(seconds+"");
				int seconds=Integer.parseInt(json.getJSONArray("routes").getJSONObject(0).getString("duration"));
				Data.saveInfoOf(getApplicationContext(), Data.ESTIMATED_TIME, ""+seconds);
//				Log.d("sec","sec"+seconds);
				int min = (int) Math.ceil(seconds/60.0);
				
				Log.d("seconds","seconds"+seconds + "minutes = "+min);
				if (seconds < 60) 
				{
					textViewTime.setText("less than a min");
				} else
				{
					textViewTime.setText(min + " min");
				}
				String distanceOfPath;
//				if(json.getJSONArray("routes").getJSONObject(0).getJSONArray("steps").length()==2)
//				{
//				
//				 distanceOfPath = leg1.getJSONObject("maneuver").getString("instruction");
//				 
//				 Log.d("instruction","instruction : "+ distanceOfPath+", distanceOfPath : "+distanceString);
////					timeString = leg0.getJSONObject("duration").getString("text");
//					textView2.setText("instructions : "+distanceOfPath);
//					textView3.setText("distance : "+0.0+" metres");
//					textView4.setText("duration : "+0.0+" seconds");
//				}
//				else{
					
					 distanceOfPath = leg0.getJSONObject("maneuver").getString("instruction");
					 String nextPath = leg1.getJSONObject("maneuver").getString("instruction");
					 String type = leg1.getJSONObject("maneuver").getString("type");
					 
					 directionsImage= (ImageView)findViewById(R.id.imageViewDirections);
					 if(type.equals("bear right") || type.equals("sharp right") || type.equals("turn right")){
						 directionsImage.setImageResource(R.drawable.right);
						 directionsImage.setVisibility(View.VISIBLE);
					 }
					 else if(type.equals("bear left") || type.equals("sharp left") || type.equals("turn left")){
						 directionsImage.setImageResource(R.drawable.left);
						 directionsImage.setVisibility(View.VISIBLE);
					 }
					else if(type.equals("enter roundabout")){
						directionsImage.setImageResource(R.drawable.round_about);
						directionsImage.setVisibility(View.VISIBLE);
										 }
					else if(type.equals("continue") || type.equals("waypoint")){
						directionsImage.setImageResource(R.drawable.straight);
						directionsImage.setVisibility(View.VISIBLE);
					}
					else {
						directionsImage.setVisibility(View.GONE);
						directionsImage.setImageResource(R.drawable.ic_launcher);
					}
					 
//						Log.d("instruction","instruction : "+ distanceOfPath+", distanceOfPath : "+distanceString);
//						timeString = leg0.getJSONObject("duration").getString("text");
//					 textViewDirections.setText("instructions : "+distanceOfPath);
//					 textViewDirections.setText("next : "+nextPath);
						String temp = distanceOfPath +" and then "+nextPath;
						 textViewDirections.setText(temp);
						
						if(!temp.equals(Instruction))
						{
							Instruction=temp;
//							speakText(Instruction);
						}
						
						
						
						
						String dist = leg0.getString("distance");
						String time = leg0.getString("duration");
						String bearing = leg0.getString("heading");
						float bearVal = Float.parseFloat(bearing);
						double mileConvert = 0.00062137;
						double mile = Integer.parseInt(dist)*mileConvert;
						
						DecimalFormat df = new DecimalFormat("#.##");
						mile = Double.valueOf(df.format(mile));
						
						
						textViewDistance.setText(""+mile+" miles");
//						textView4.setText("duration : "+time+" seconds");
//				}
//				distanceString = leg0.getJSONObject("distance").getString("text");
				
				JSONArray routeArray = json.getJSONArray("routes");
				JSONObject routes = routeArray.getJSONObject(0);
				
				
				
				JSONObject overviewPolylines = routes.getJSONObject("geometry");
				JSONArray jsonArray = overviewPolylines.getJSONArray("coordinates");
				List<Double> lat = new ArrayList<Double>();
				List<Double> lng = new ArrayList<Double>();
				for(int i = 0; i<jsonArray.length();i++)
				{
					JSONArray js = jsonArray.getJSONArray(i);
					lat.add(Double.parseDouble(js.get(1).toString()));
					lng.add(Double.parseDouble(js.get(0).toString()));
//					Log.e("js.get(0).toString();", "js.get(0).toString();"+js.get(0).toString());
//					poly.add(jsonArray.get(i).toString());
//					Log.e("jsonArray.get(i).toString()", "jsonArray.get(i).toString()"+jsonArray.get(i).toString());
				}
////				String encodedString = overviewPolylines.getString("points");
////				List<LatLng> list = decodePoly(encodedString);
				PolylineOptions options = new PolylineOptions().width(8).color(Color.parseColor("#4890e8")).geodesic(true);
				for (int z = 0; z < lat.size(); z++) {
					LatLng point = new LatLng(lat.get(z), lng.get(z));
					options.add(point);
				}
				map.addPolyline(options);
				CameraPosition anotherPosition = new CameraPosition(new LatLng(Double.parseDouble(lat.get(0).toString()), Double.parseDouble(lng.get(0).toString())), 18, 0, bearVal); 

				if (callFlag == 0) {
					map.moveCamera(CameraUpdateFactory.newCameraPosition(anotherPosition));
//					callFlag=1;
				} else {

					if (Flag == 0) {
						map.moveCamera(CameraUpdateFactory.newCameraPosition(anotherPosition));
					}
					else{
						map.moveCamera(CameraUpdateFactory.newCameraPosition(anotherPosition));
					}
				}
				
//				CameraPosition.builder()
//				CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(myLocation.getLatitude(), myLocation.getLongitude()), 18);
				
////				PolylineOptions options = new PolylineOptions().width(8)
////						.color(Color.parseColor("#4890e8")).geodesic(true);
//				Polyline line;
//				for(int j=0;j<lat.size();j++)
//				{
//					Log.d("aaa","aaa"+j);
//					 map.addPolyline(new PolylineOptions().add(new LatLng(lat.get(j),lng.get(j))).width(10).color(Color.RED));
//				}
//				CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(
//						new LatLng(myLocation.getLatitude(),
//								myLocation.getLongitude()), 15);
//				map.animateCamera(cameraUpdate);
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
			return new SimpleJSONParserGet().getJSONFromUrl(url);
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

	public void getOrderDeliveredServerCall() {
		RequestParams params = new RequestParams();
		Data.loading_box(OrderDelivered.this, "Loading...");

		params.put("access_token",
				Data.getInfoOf(Data.activity, Data.ACCESS_TOKEN));
		params.put("order_id",
				Data.getInfoOf(getApplicationContext(), Data.ORDERID));

		AsyncHttpClient client = new AsyncHttpClient();
		client.setTimeout(Httppost_Links.SERVER_TIME_OUT_TIME);
		client.post(Httppost_Links.ORDER_DELIVER, params,
				new AsyncHttpResponseHandler() {
					@Override
					public void onSuccess(String response) {
						Log.e("resp val", response);
						try {
							JSONObject job = new JSONObject(response);
							Data.loading_box_stop();
							if(job.getString("status").equals("2"))
							{
//								Data.alertMessageBox(SplashScreen.this,popup.getString("title"),popup.getString("text"),Data.getInfoOf(getApplicationContext(), Data.loginFrom));
							Intent intent = new Intent(OrderDelivered.this,MainActivity.class);
							startActivity(intent);
							finish();
							}
							else
							{
							if (response.contains("error")) {
								String error = job.getString("error");
								Log.i("Error ", "-----------" + error);
								new DialogPopup().alertPopup(
										OrderDelivered.this, "", error);
							} else {
								// Data.saveInfoOf(getApplicationContext(),
								// Data.START_ORDER_DETAILS, "");
								Data.saveInfoOf(getApplicationContext(),
										Data.ORDERID, "");
								// Data.saveInfoOf(getApplicationContext(),
								// Data.CURRENT_ORDER_LATITUDE, "");
								// Data.saveInfoOf(getApplicationContext(),
								// Data.CURRENT_ORDER_LONGITUDE, "");
								// Data.saveInfoOf(getApplicationContext(),
								// Data.CURRENT_NUMBER_OF_MEALS, "");
								// Data.saveInfoOf(getApplicationContext(),
								// Data.CURRENT_ORDER_CUSTOMER_NAME, "");
								// Data.saveInfoOf(getApplicationContext(),
								// Data.CURRENT_ORDER_ADDRESS, "");
								myHandler.removeCallbacks(updateTimerMethod);
								Intent intent = new Intent(OrderDelivered.this,
										OrderScreen.class);
								startActivity(intent);
								overridePendingTransition(R.anim.slide_in_left,
										R.anim.slide_out_right);
								finish();

							}
							}
						} catch (Exception e) {
							Log.v("Login Data Error", e.toString());
						}
					}

					@Override
					public void onFailure(Throwable arg0) {
						// TODO Auto-generated method stub
						Data.loading_box_stop();
						Log.v("Login Server Error", "fail: " + arg0);
						new DialogPopup().alertPopup(OrderDelivered.this, "",
								"Server not responding\nTry again.");

					}
				});
	}

	public void startOrderServerCall() {
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();

		Log.e("Access token",
				","
						+ Data.getInfoOf(getApplicationContext(),
								Data.ACCESS_TOKEN));

		params.put("access_token",
				Data.getInfoOf(getApplicationContext(), Data.ACCESS_TOKEN));
		params.put("order_id",
				Data.getInfoOf(getApplicationContext(), Data.ORDERID));
		client.setTimeout(Httppost_Links.SERVER_TIME_OUT_TIME);
		client.post(Httppost_Links.PARTICULAR_ORDER_DETAILS, params,
				new AsyncHttpResponseHandler() {
					@Override
					public void onSuccess(String response) {
						Log.v("checkin Api", "=" + response);

						JSONObject res;
						try {

							res = new JSONObject(response);
							Log.d("response", "response" + res.toString(2));
							
							if(res.getString("status").equals("2"))
							{
//								Data.alertMessageBox(SplashScreen.this,popup.getString("title"),popup.getString("text"),Data.getInfoOf(getApplicationContext(), Data.loginFrom));
							Intent intent = new Intent(OrderDelivered.this,MainActivity.class);
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
								try {

									Log.v("Order Api", "=" + res.toString(2));

									orderList.clear();
									ArrayList<DetailsBase> detailsList = null;
									ArrayList<ToppingDetailsBase> toppingList;

									for (int i = 0; i < res
											.getJSONArray("data").length(); i++) {

										OrderBase orderBaseObject = new OrderBase();
										JSONArray jsonArray = res
												.getJSONArray("data");
										
										callnum = jsonArray.getJSONObject(i).getString("phone_no");
										orderBaseObject
												.setCustomer_name(jsonArray
														.getJSONObject(i)
														.getString(
																"customer_name"));
										orderBaseObject.setPrice(jsonArray
												.getJSONObject(i).getString(
														"price"));
										orderBaseObject
												.setDrop_location_address(jsonArray
														.getJSONObject(i)
														.getString(
																"drop_location_address"));
										orderBaseObject.setOrderID(jsonArray
												.getJSONObject(i).getString(
														"order_id"));
										orderBaseObject
												.setDeliveryTime(jsonArray
														.getJSONObject(i)
														.getString(
																"delivery_time"));
										orderBaseObject
												.setDropLatitude(jsonArray
														.getJSONObject(i)
														.getString(
																"drop_latitude"));
										orderBaseObject
												.setDropLongitude(jsonArray
														.getJSONObject(i)
														.getString(
																"drop_longitude"));
										orderBaseObject.setDistance(jsonArray
												.getJSONObject(i).getString(
														"distance"));
										orderBaseObject
												.setTimeToDeliver(jsonArray
														.getJSONObject(i)
														.getString(
																"time_to_deliver"));
										orderBaseObject.setTotalMeals(jsonArray
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
										orderBaseObject.setDetails(detailsList);
										orderList.add(orderBaseObject);
									}
								} catch (Exception e) {
									e.printStackTrace();
								}

								textViewName.setText(orderList.get(0)
										.getCustomer_name().toString());
								textViewAddress.setText(orderList.get(0)
										.getDrop_location_address().toString());
//								buttonCall.setText(""+callnum);
								
								
								relativeLayoutHidden
										.setVisibility(View.INVISIBLE);

								OrderDetailsDriverListAdapter orderDetailsDriverListAdapter = new OrderDetailsDriverListAdapter(
										OrderDelivered.this, orderList);
								listViewOrderDetails
										.setAdapter(orderDetailsDriverListAdapter);

								LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
								locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000,10, myListener);

								sec = new CountDownTimer((Long
										.parseLong(orderList.get(0)
												.getTimeToDeliver())) * 60000,60000) {

									public void onTick(long millisUntilFinished) {
										textViewDueTimer
												.setText("Due in "
														+ formatTime(millisUntilFinished)
														+ " min, "
														+ orderList
																.get(0)
																.getTotalMeals()
																.toString()
														+ " meal");
										// textViewDueTimer.setText(""+ new
										// SimpleDateFormat("ss").format(new
										// Date(millisUntilFinished)));
									}

									public void onFinish() {
										// textViewDueTimer.setText("--0");
										startTime = SystemClock.uptimeMillis();
										myHandler.postDelayed(
												updateTimerMethod, 0);
									}
								};
								sec.start();
		
							}
							}
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}

					@Override
					public void onFailure(Throwable arg0, String json) {
						Log.v("request fail", "response=" + arg0);
						new DialogPopup()
						.alertPopup(OrderDelivered.this, "",
								"Server not responding\nTry again.");
					}
				});

	}

	private Runnable updateTimerMethod = new Runnable() {

		public void run() {
			long timeInMillies = SystemClock.uptimeMillis() - startTime;
			// long finalTime = timeSwap + timeInMillies;
			
			if(orderList.get(0).getTimeToDeliver().contains("-"))
			{
				textViewDueTimer.setText("Delayed by " + (Integer.parseInt(formatTime(timeInMillies))-Integer.parseInt(orderList.get(0).getTimeToDeliver()))
						+ " min, " + orderList.get(0).getTotalMeals().toString()
						+ " meal");
			}
			else
			{
				textViewDueTimer.setText("Delayed by " + formatTime(timeInMillies)
						+ " min, " + orderList.get(0).getTotalMeals().toString()
						+ " meal");
			}
			
			// int seconds = (int) (finalTime / 1000);
			// int minutes = seconds / 60;
			// seconds = seconds % 60;
			// int milliseconds = (int) (finalTime % 1000);
			// textTimer.setText("" + minutes + ":"
			// + String.format("%02d", seconds) + ":"
			// + String.format("%03d", milliseconds));
			myHandler.postDelayed(this, 0);
		}

	};

	public String formatTime(long millis) {
		String output = "00:00";
		long seconds = millis / 1000;
		long minutes = seconds / 60;

		seconds = seconds % 60;
		minutes = minutes % 60;

		String sec = String.valueOf(seconds);
		String min = String.valueOf(minutes);

		if (seconds < 10)
			sec = "0" + seconds;
		if (minutes < 10)
			min = "0" + minutes;

		// output = min + " : " + sec;
		output = min;
		return output;
	}// formatTime

	@Override
	public void onBackPressed() {
		finish();
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
		
//		 ttobj=new TextToSpeech(getApplicationContext(), 
//			      new TextToSpeech.OnInitListener() {
//			      @Override
//			      public void onInit(int status) {
//			         if(status != TextToSpeech.ERROR){
//			             ttobj.setLanguage(Locale.UK);
//			            }				
//			         }
//			      });
	}

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

	@Override
	public void pushUpdater() {
		Log.e("pushUpdater", "=");
		new Thread(new Runnable() {

			@Override
			public void run() {

				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						if (GCMIntentService.flag.equals("15")) {
							new DialogPopup().alertPopup(OrderDelivered.this,
									"", GCMIntentService.stringMessage);
						} else if (Data.getInfoOf(getApplicationContext(), "OD").equals("19")) {
							Data.saveInfoOf(Data.activity,
									Data.CHECKINVALUE, "0");
							Intent intent = new Intent(OrderDelivered.this,
									OrderScreen.class);
							intent.putExtra("checkout_admin", true);
							startActivity(intent);
							finish();
							overridePendingTransition(R.anim.slide_in_left,
									R.anim.hold);
							stopService(new Intent(Data.activity,
									MyService.class));
						} else if (GCMIntentService.flag.equals("25")) {
							Data.saveInfoOf(Data.activity,
									Data.CHECKINVALUE, "1");
							new DialogPopup().alertPopup(OrderDelivered.this,
									"", GCMIntentService.stringMessage);
						} else if (GCMIntentService.flag.equals("16")) {
							new DialogPopup().alertPopup(OrderDelivered.this,
									"",GCMIntentService.stringMessage);
						}
						 Data.saveInfoOf(getApplicationContext(), "OD", "");
					}
				});
			}
		}).start();
	}

	private void getMyLocation() {
//		if (destMarker != null) {
//			destMarker.remove();
//		}
		Data.seletedlat = Data.currentLatitude;
		Data.selectedlng = Data.currentLongitude;
		Data.LATITUDE = Data.seletedlat.toString();
		Data.LONGITUDE = Data.selectedlng.toString();
//		destMarker = map
//				.addMarker(new MarkerOptions()
//						.position(new LatLng(Data.seletedlat, Data.selectedlng))
//						.title("You are here")
//						.icon(BitmapDescriptorFactory
//								.fromBitmap(createStartRideThumbDrawable(R.drawable.cust_location))));
//		// googleMap.setMyLocationEnabled(true);
//		LatLng selectedLocation = new LatLng(Data.seletedlat, Data.selectedlng);
//		map.moveCamera(CameraUpdateFactory.newLatLngZoom(
//				selectedLocation, 15));
//		map.animateCamera(CameraUpdateFactory.zoomTo(14), 2000, null);
//		getAddress();
//		addMarker(new LatLng(Data.seletedlat,
//				Data.selectedlng));
		
		
		
		
		
//		myLocation = location;
		
		if(myLocation!=null)
		{
		map.addMarker(new MarkerOptions().position(new LatLng(Double.parseDouble(orderList.get(0)
								.getDropLatitude().toString()), Double.parseDouble(orderList.get(0)
										.getDropLongitude().toString())))
				.title(orderList.get(0).getDrop_location_address())
				.icon(BitmapDescriptorFactory
						.fromBitmap(createStartRideThumbDrawable(R.drawable.cust_location))));

		addMarker(new LatLng(Data.seletedlat,Data.selectedlng));
		new CreatePathAsyncTask(new LatLng(Data.seletedlat,Data.selectedlng), new LatLng(
				Double.parseDouble(orderList.get(0).getDropLatitude()
						.toString()), Double.parseDouble(orderList.get(0)
						.getDropLongitude().toString()))).execute();
		CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(
				new LatLng(Data.seletedlat,Data.selectedlng), 15);
		map.animateCamera(cameraUpdate);
		}
	
	}
	
//	public void speakText(String s){
//	      String toSpeak =s;
//	      Toast.makeText(getApplicationContext(), toSpeak, 
//	      Toast.LENGTH_SHORT).show();
//	      ttobj.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
//
//	   }
	
	@Override
	protected void onPause() {
//			 if(ttobj !=null){
//		         ttobj.stop();
//		         ttobj.shutdown();
//		      }
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
		Utils.buildAlertMessageNoGps(OrderDelivered.this);
		super.onWindowFocusChanged(hasFocus);
	}
	
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		EasyTracker.getInstance(OrderDelivered.this).activityStart(this);
//		super.onStart();
	}
	
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		EasyTracker.getInstance(OrderDelivered.this).activityStop(this);
//		super.onStart();
	}
}
