package com.bitekite;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import rmn.androidscreenlibrary.ASSL;
import android.app.Activity;
import android.content.Intent;
import android.location.Address;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bitekite.adpters.ListofMealAdapter;
import com.bitekite.adpters.OrderDetailCustomerAdapter;
import com.bitekite.classes.MealSelected;
import com.bitekite.driver.Httppost_Links;
import com.bitekite.driver.Utils;
import com.bitekite.utils.Data;
import com.bitekite.utils.DialogPopup;
import com.bitekite.utils.InternetCheck;
import com.bitekite.utils.LocationFetcherForeground;
import com.flurry.android.FlurryAgent;
import com.google.analytics.tracking.android.EasyTracker;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class CheckOutCustomer extends Activity {


	RelativeLayout relativeLayoutBack, relativeLayoutCheckOut,
			relativeLayoutCreditCard, relativeLayoutAddress,
			// relativeLayoutPrromoClick,
			relativeLayoutPromoDiscount;
	TextView textViewAddress, textViewCreditCard;
	ListView orderDetailList;
	public static ArrayList<MealSelected> selectedArrayList;
	Button buttonPlaceOrder;
	RelativeLayout relativeLayoutTopping;
	String mealids = "", mealqty = "", toppingids, toppingqty, price;
	EditText editTextPhone;
	// editTextPromoCode;
	TextView TextViewTopingName, TextViewToppingAmount, textViewTotal,
			textViewTotalAmount, TextViewDeliveryCharge,
			TextViewServiceTaxPercent, TextViewServiceTaxAdded,
			TextViewToppingQunt,
			// TextViewBill,TextViewBillAmount,
			TextViewDeliveryAmount, TextViewPromoDiscountAmount,
			TextViewPromoCodeDiscount;
	LinearLayout LinearLayoutFooter;
	TextView textViewAddressHeader, textViewPhoneHeader,
			textViewCreditCardHeader,
			// textViewPromotionCodeHeader,
			textViewOrderDetailHeader;
	Double total = 0.00;
	ArrayList<HashMap<String, String>> hashmapList = new ArrayList<HashMap<String, String>>();
	ArrayList<Integer> count = new ArrayList<Integer>();
	ArrayList<Integer> arrayList = new ArrayList<Integer>();
	String zipCode;
	String htmlString;
	String promo_id = "";
	Double promoDisc = 0.0;
	Double service = 0.0, delivery = 0.0;
	Double temp_price=0.0;
	Double remainingCredit=0.0;
	Double creditbalused=0.0;
	public static int ConfirmAddressFlag;
	
	ArrayList<Address>retList;	
	LocationFetcherForeground locationFetcherForeground;
	
	RelativeLayout relativeLayoutAddressClicked;
	Handler mHandler;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_check_out_customer);

		ConfirmAddressFlag=0;
		FlurryAgent.init(this, Data.flurryKey);

		relativeLayoutCheckOut = (RelativeLayout) findViewById(R.id.relativeLayoutCheckOut);
		new ASSL(this, relativeLayoutCheckOut, 1134, 720, false);

		Data.Flag = 0;
		// relativeLayoutPrromoClick=(RelativeLayout)findViewById(R.id.relativeLayoutPrromoClick);
		orderDetailList = (ListView) findViewById(R.id.listView1);
		relativeLayoutBack = (RelativeLayout) findViewById(R.id.relativeLayoutBack);
		relativeLayoutCreditCard = (RelativeLayout) findViewById(R.id.relativeLayoutCreditCard);
		relativeLayoutAddress = (RelativeLayout) findViewById(R.id.relativeLayoutAddress);
		buttonPlaceOrder = (Button) findViewById(R.id.button1);
		editTextPhone = (EditText) findViewById(R.id.editTextPhone);
		// editTextPromoCode=(EditText)findViewById(R.id.editTextPromoCode);
		textViewAddress = (TextView) findViewById(R.id.textViewAddress);
		textViewCreditCard = (TextView) findViewById(R.id.textViewCreditCard);

		textViewAddressHeader = (TextView) findViewById(R.id.textViewAddressHeader);

		textViewPhoneHeader = (TextView) findViewById(R.id.textViewPhoneHeader);
		textViewCreditCardHeader = (TextView) findViewById(R.id.textViewCreditCardHeader);
		// textViewPromotionCodeHeader=(TextView)findViewById(R.id.textViewPromotionCodeHeader);
		textViewOrderDetailHeader = (TextView) findViewById(R.id.textViewOrderDetailHeader);

		textViewAddressHeader.setTypeface(Data
				.bariol_bold(getApplicationContext()));

		textViewPhoneHeader.setTypeface(Data
				.bariol_bold(getApplicationContext()));
		textViewCreditCardHeader.setTypeface(Data
				.bariol_bold(getApplicationContext()));
		// textViewPromotionCodeHeader.setTypeface(Data.bariol_bold(getApplicationContext()));
		textViewOrderDetailHeader.setTypeface(Data
				.bariol_bold(getApplicationContext()));

		textViewTotal = (TextView) findViewById(R.id.textViewTotal);
		textViewTotalAmount = (TextView) findViewById(R.id.textViewTotalAmount);
		// TextViewTopingPrice=(TextView)footer.findViewById(R.id.TextViewTopingPrice);
		// TextViewToppingAmount=(TextView)
		// footer.findViewById(R.id.TextViewToppingAmount);
		TextViewServiceTaxPercent = (TextView) findViewById(R.id.TextViewServiceTaxPercent);
		TextViewServiceTaxAdded = (TextView) findViewById(R.id.TextViewServiceTaxAdded);
		// TextViewBillAmount=(TextView)findViewById(R.id.TextViewBillAmount);
		// TextViewBill=(TextView)findViewById(R.id.TextViewBill);
		TextViewDeliveryCharge = (TextView) findViewById(R.id.TextViewDeliveryCharge);
		TextViewDeliveryAmount = (TextView) findViewById(R.id.TextViewDeliveryAmount);
		TextViewPromoDiscountAmount = (TextView) findViewById(R.id.TextViewPromoDiscountAmount);
		TextViewPromoCodeDiscount = (TextView) findViewById(R.id.TextViewPromoCodeDiscount);
		relativeLayoutPromoDiscount = (RelativeLayout) findViewById(R.id.relativeLayoutPromoDiscount);
		
		relativeLayoutAddressClicked=(RelativeLayout)findViewById(R.id.relativeLayoutAddressClicked);

		buttonPlaceOrder.setTypeface(Data
				.bariol_regular(getApplicationContext()));
		// editTextPromoCode.setTypeface(Data.bariol_regular(getApplicationContext()));
		textViewAddress.setTypeface(Data
				.bariol_regular(getApplicationContext()));
		textViewCreditCard.setTypeface(Data
				.bariol_bold(getApplicationContext()));
		// textViewAddress.setTypeface(Data.Helvetica_NeueLTStd(getApplicationContext()));
		editTextPhone.setTypeface(Data.bariol_regular(getApplicationContext()));

		editTextPhone.setText(Data.getInfoOf(getApplicationContext(),
				Data.PH_NO));

		htmlString = "&#8226;&#8226;&#8226;&#8226; &#8226;&#8226;&#8226;&#8226; &#8226;&#8226;&#8226;&#8226; ";

		editTextPhone.setImeOptions(EditorInfo.IME_ACTION_DONE);

		buttonPlaceOrder.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if (textViewAddress.getText().toString().trim().isEmpty()) {
					new DialogPopup().alertPopup(CheckOutCustomer.this,
							"OOPS!", "Please add the delivery address");
				} else {
					if (editTextPhone.getText().toString().trim().isEmpty()) {
						new DialogPopup().alertPopup(CheckOutCustomer.this,
								"OOPS!", "Please add your phone number ");
					} else {
						
						if (editTextPhone.getText().toString().trim()
								.length() < 14) {
							new DialogPopup().alertPopup(
									CheckOutCustomer.this, "",
									"Phone number less than 10 digits");
						}
						
						else{
						if (textViewCreditCard.getText().toString().trim()
								.isEmpty()) {
							new DialogPopup().alertPopup(CheckOutCustomer.this,
									"OOPS!",
									"Please add the payment information");
						} else {
							if (Integer.parseInt(Data.getInfoOf(
									getApplicationContext(), Data.CHECK_CARD)) == 0) {
								new DialogPopup().alertPopup(
										CheckOutCustomer.this, "OOPS!",
										"Please add the payment information");
							} else {

								if (ConfirmAddressFlag == 1) {
									if (InternetCheck.getInstance(
											getApplicationContext()).isOnline(
											getApplicationContext())) {
										serverCallPlaceOrder();
									} else {
										new DialogPopup().alertPopup(CheckOutCustomer.this,"","Check your internet connection");
									}
								} else {
									new DialogPopup().alertPopup(CheckOutCustomer.this,"1", "Please confirm your address.");
								}
							}
						}
						}
					}
				}

			}
		});

		InputFilter filter = new InputFilter() {

			@Override
			public CharSequence filter(CharSequence source, int start, int end,
					Spanned dest, int dstart, int dend) {
				if (source.length() > 0) {

					if (!Character.isDigit(source.charAt(0))) {
						Log.e("start", "start");
						return "";
					}

					else {
						if (dstart == 3) {
							return source + ") ";
						} else if (dstart == 0) {
							return "(" + source;
						} else if ((dstart == 5) || (dstart == 9))
							return "-" + source;
						else if (dstart >= 14)
							return "";
					}

				} else {

				}

				return null;

			}
		};

		 editTextPhone.setFilters(new InputFilter[] { filter });
		 
		 
		 relativeLayoutAddressClicked.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				ConfirmAddressFlag=1;
				
				Intent intent = new Intent(CheckOutCustomer.this,
						AddressCheckout.class);
				startActivity(intent);
				overridePendingTransition(R.anim.slide_in_right,
						R.anim.slide_out_left);
			}
		});

		relativeLayoutCreditCard.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (Integer.parseInt(Data.getInfoOf(getApplicationContext(),
						Data.CHECK_CARD)) == 0) {
					Intent intent = new Intent(CheckOutCustomer.this,
							PaymentUserEdit.class);
					startActivity(intent);
					overridePendingTransition(R.anim.slide_in_right,
							R.anim.slide_out_left);
				} else {
					Intent intent = new Intent(CheckOutCustomer.this,
							AddPaymentUser.class);
					startActivity(intent);
					overridePendingTransition(R.anim.slide_in_right,
							R.anim.slide_out_left);
				}

			}
		});

		relativeLayoutBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (ListofMeals.Flag == 1) {
				} else {
					Intent intent = new Intent(CheckOutCustomer.this,
							Cust.class);
					startActivity(intent);
				}

				finish();
				overridePendingTransition(R.anim.slide_in_left,
						R.anim.slide_out_right);

			}
		});

		selectedArrayList = new ArrayList<MealSelected>();

		for (int i = 0; i < ListofMealAdapter.selectedMealArrayList.size(); i++) {

			if (ListofMealAdapter.selectedMealArrayList.get(i).quantity != 0) {
				MealSelected meal = new MealSelected();
				meal.setQuantity(ListofMealAdapter.selectedMealArrayList.get(i)
						.getQuantity());
				meal.setName(ListofMealAdapter.selectedMealArrayList.get(i)
						.getName());
				meal.setPrice(ListofMealAdapter.selectedMealArrayList.get(i)
						.getPrice());
				meal.setMeal_id(ListofMealAdapter.selectedMealArrayList.get(i)
						.getMeal_id());
				selectedArrayList.add(meal);
			}

		}
		for (int i = 0; i < selectedArrayList.size(); i++) {
			Log.d("name", "name = " + selectedArrayList.get(i).getName());
			Log.d("price", "price = " + selectedArrayList.get(i).getPrice());
			Log.d("quantity", "quantity = "
					+ selectedArrayList.get(i).getQuantity());
			Log.d("mealid", "mealid = " + selectedArrayList.get(i).getMeal_id());
			mealids = mealids + "," + selectedArrayList.get(i).getMeal_id();
			mealqty = mealqty + "," + selectedArrayList.get(i).getQuantity();
		}

		mealids = mealids.substring(1, mealids.length());
		mealqty = mealqty.substring(1, mealqty.length());
		Log.d("mee", "mee  " + mealids + ", " + mealqty);
		settoppingsids();

		OrderDetailCustomerAdapter adapter = new OrderDetailCustomerAdapter(
				CheckOutCustomer.this, selectedArrayList);
		LayoutInflater inflater = getLayoutInflater();
		View footer = inflater.inflate(
				R.layout.list_order_detail_customer_footer, orderDetailList,
				false);
		orderDetailList.addFooterView(footer, null, false);
		orderDetailList.setAdapter(adapter);
		// setListSize(orderDetailList,80);

		LinearLayout layout = (LinearLayout) footer
				.findViewById(R.id.addfooter);
		for (int i = 0; i < count.size(); i++) {
			View child = getLayoutInflater().inflate(
					R.layout.list_item_order_details, null);

			TextViewTopingName = (TextView) child.findViewById(R.id.textView2);
			TextViewToppingAmount = (TextView) child
					.findViewById(R.id.textView3);
			TextViewToppingQunt = (TextView) child.findViewById(R.id.textView1);
			TextViewToppingQunt.setTypeface(Data
					.bariol_bold(getApplicationContext()));
			TextViewToppingAmount.setTypeface(Data
					.bariol_regular(getApplicationContext()));
			TextViewTopingName.setTypeface(Data
					.bariol_regular(getApplicationContext()));
			TextViewToppingQunt.setText("" + count.get(i) + "x");
			TextViewTopingName.setText(""
					+ hashmapList.get(i).get("NAME").toString());
			Double prize = Double.parseDouble(hashmapList.get(i).get("PRICE")
					.toString())
					* count.get(i);
			DecimalFormat df = new DecimalFormat("#.##");
			
			prize = Double.valueOf(df.format(prize));
			String prizes = prize.toString();
			String str[] = prizes.split("\\.");
			if (str.length == 1) {
				TextViewToppingAmount.setText(Html.fromHtml("$" + prize
						+ ".<sup><small>" + "00" + "</small></sup>"));
			} else {
				TextViewToppingAmount.setText(Html.fromHtml("$" + str[0]
						+ ".<sup><small>" + str[1] + "</small></sup>"));
			}
			layout.addView(child);
		}
		LinearLayoutFooter = (LinearLayout) footer
				.findViewById(R.id.LinearLayoutFooter);
		LinearLayoutFooter.setLayoutParams(new ListView.LayoutParams(
				ListView.LayoutParams.MATCH_PARENT,
				ListView.LayoutParams.WRAP_CONTENT));// ListView.LayoutParams.WRAP_CONTENT));
		ASSL.DoMagic(LinearLayoutFooter);

		Double bill = Data.ToppingPrize + Data.MealPrize;

		Double tax = Double.parseDouble(Data.servicetax);
		service = (bill * tax) / 100;

		delivery = Double.parseDouble(Data.deliveryCharge);

		total = bill + delivery + service;
		
		// +8.00;
		DecimalFormat df = new DecimalFormat("#.##");
		temp_price=bill + delivery + service;
		temp_price = Double.valueOf(df.format(temp_price));
		
		total = Double.valueOf(df.format(total));
		bill = Double.valueOf(df.format(bill));
		service = Double.valueOf(df.format(service));

		TextViewServiceTaxPercent.setText("Tax");
		String prizes4 = service.toString();
		String str4[] = prizes4.split("\\.");
		if (str4.length == 1) {
			TextViewServiceTaxAdded.setText(Html.fromHtml("  $" + service
					+ ".<sup><small>" + "00" + "</small></sup>"));
		} else {
			TextViewServiceTaxAdded.setText(Html.fromHtml("  $" + str4[0]
					+ ".<sup><small>" + str4[1] + "</small></sup>"));
		}

		if(total<Double.parseDouble(Data.creditBalance))
		{
			creditbalused=total;
			remainingCredit=Double.parseDouble(Data.creditBalance)-total;	
			
			creditbalused = Double.valueOf(df.format(creditbalused));
			String prizes1 = creditbalused.toString();
			String str1[] = prizes1.split("\\.");

			if (str1.length == 1) {
				TextViewPromoDiscountAmount.setText(Html.fromHtml("-$" + creditbalused
						+ ".<sup><small>" + "00" + "</small></sup>"));
			} else {
				TextViewPromoDiscountAmount.setText(Html.fromHtml("-$" + str1[0]
						+ ".<sup><small>" + str1[1] + "</small></sup>"));
			}
			
			total=total-creditbalused;

			total = Double.valueOf(df.format(total));
			String prizes = total.toString();
			String str[] = prizes.split("\\.");

			if (str.length == 1) {
				textViewTotalAmount.setText(Html.fromHtml(" $" + total
						+ ".<sup><small>" + "00" + "</small></sup>"));
			} else {
				textViewTotalAmount.setText(Html.fromHtml(" $" + str[0]
						+ ".<sup><small>" + str[1] + "</small></sup>"));
			}

		}
		else
		{
			remainingCredit=0.0;
			
			if((total-Double.parseDouble(Data.creditBalance))>1.00)
			{
				Log.d("if","if");
				Double creditbal= Double.parseDouble(Data.creditBalance);
				
				creditbal = Double.valueOf(df.format(creditbal));
				String prizes1 = creditbal.toString();
				String str1[] = prizes1.split("\\.");
				if (str1.length == 1) {
					TextViewPromoDiscountAmount.setText(Html.fromHtml("-$" + creditbal
							+ ".<sup><small>" + "00" + "</small></sup>"));
				} else {
					TextViewPromoDiscountAmount.setText(Html.fromHtml("-$" + str1[0]
							+ ".<sup><small>" + str1[1] + "</small></sup>"));
				}
				creditbalused=Double.parseDouble(Data.creditBalance);
				total=total-creditbalused;
				
				total = Double.valueOf(df.format(total));
				String prizes = total.toString();
				String str[] = prizes.split("\\.");
				if (str.length == 1) {
					textViewTotalAmount.setText(Html.fromHtml(" $" + total
							+ ".<sup><small>" + "00" + "</small></sup>"));
				} else {
					textViewTotalAmount.setText(Html.fromHtml(" $" + str[0]
							+ ".<sup><small>" + str[1] + "</small></sup>"));
				}
			}
			else
			{
				Log.d("else","else");
				Double tminuscr = total-Double.parseDouble(Data.creditBalance);
				remainingCredit=1.00-tminuscr;
				Double creditbal= Double.parseDouble(Data.creditBalance);
				creditbal=creditbal-remainingCredit;
				remainingCredit = Double.valueOf(df.format(remainingCredit));
				creditbal = Double.valueOf(df.format(creditbal));
				String prizes1 = creditbal.toString();
				String str1[] = prizes1.split("\\.");
				if (str1.length == 1) {
					TextViewPromoDiscountAmount.setText(Html.fromHtml("-$" + creditbal
							+ ".<sup><small>" + "00" + "</small></sup>"));
				} else {
					TextViewPromoDiscountAmount.setText(Html.fromHtml("-$" + str1[0]
							+ ".<sup><small>" + str1[1] + "</small></sup>"));
				}
				creditbalused=creditbal;
				
				total=total-creditbalused;
				
				total = Double.valueOf(df.format(total));
				String prizes = total.toString();
				String str[] = prizes.split("\\.");
				if (str.length == 1) {
					textViewTotalAmount.setText(Html.fromHtml(" $" + total
							+ ".<sup><small>" + "00" + "</small></sup>"));
				} else {
					textViewTotalAmount.setText(Html.fromHtml(" $" + str[0]
							+ ".<sup><small>" + str[1] + "</small></sup>"));
				}
			}
			
		}
		
		Log.d("remainingCredit","remainingCredit"+remainingCredit);
		textViewTotal.setText("Total ");
//		Double finalDeliveryCharges = Double.valueOf(df.format(Data.deliveryCharge));
		String prizes = Data.deliveryCharge.toString();
		String str[] = prizes.split("\\.");
		if (str.length == 1) {
			TextViewDeliveryAmount.setText(Html.fromHtml(" $" + Data.deliveryCharge
					+ ".<sup><small>" + "00" + "</small></sup>"));
		} else {
			TextViewDeliveryAmount.setText(Html.fromHtml(" $" + str[0]
					+ ".<sup><small>" + str[1] + "</small></sup>"));
		}
//		TextViewDeliveryAmount.setText(" $" + );

		textViewTotal.setTypeface(Data.bariol_bold(getApplicationContext()));
		textViewTotalAmount.setTypeface(Data
				.bariol_bold(getApplicationContext()));
		TextViewServiceTaxPercent.setTypeface(Data
				.bariol_regular(getApplicationContext()));
		TextViewServiceTaxAdded.setTypeface(Data
				.bariol_regular(getApplicationContext()));
		TextViewDeliveryCharge.setTypeface(Data
				.bariol_regular(getApplicationContext()));
		TextViewDeliveryAmount.setTypeface(Data
				.bariol_regular(getApplicationContext()));
		TextViewPromoDiscountAmount.setTypeface(Data
				.bariol_regular(getApplicationContext()));
		TextViewPromoCodeDiscount.setTypeface(Data
				.bariol_regular(getApplicationContext()));

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.check_out_customer, menu);
		return true;
	}

	@Override
	public void onBackPressed() {

		if (ListofMeals.Flag == 1) {
		} else {
			Intent intent = new Intent(CheckOutCustomer.this, Cust.class);
			startActivity(intent);
		}

		finish();
		overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);

	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		
		if (locationFetcherForeground != null) {
			locationFetcherForeground.destroy();
			locationFetcherForeground = null;
		}
		
		super.onPause();
		Log.d("onPause","onPause");
		Data.Flag=0;
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		if (Data.Flag == 1) {
			Log.d("1234567", "1234567");
			Data.Flag = 0;
			textViewAddress.setText(Data.selectedplace);
			Log.d("onResume if","onResume if");
		}
		else{
			if (locationFetcherForeground == null) {
				locationFetcherForeground = new LocationFetcherForeground(CheckOutCustomer.this, 0, 1);
			}
			locationFetcherForeground.getLatitude();
			locationFetcherForeground.getLongitude();
			try {
				Data.loading_box(this, "fetching...");
			} catch (Exception e) {
				// TODO: handle exception
			}
			
			mHandler = new Handler();
			mHandler.postDelayed(new Runnable() {
				public void run() {
					try {
					
					Data.currentLatitude = locationFetcherForeground.getLatitude();
					Data.currentLongitude = locationFetcherForeground.getLongitude();
					Log.i("Current Location", "currentLatitude::"
							+ Data.currentLatitude + "  currentLongitude"
							+ Data.currentLongitude);
					
					Data.seletedlat=Data.currentLatitude;
					Data.selectedlng=Data.currentLongitude;
					Data.LATITUDE = Data.seletedlat.toString();
					Data.LONGITUDE = Data.selectedlng.toString();
					getAddressGivenLatLongFrom(Data.currentLatitude,Data.currentLongitude,CheckOutCustomer.this);
					Log.d("onResume else","onResume else");
					} catch (Exception e) {
						// TODO: handle exception
					}
				}
			}, 3000);
		}
		textViewCreditCard.setText(Html.fromHtml(htmlString)
				+ Data.getInfoOf(getApplicationContext(), Data.LAST_4));
		textViewCreditCard.setTypeface(Data
				.bariol_regular(getApplicationContext()));
		
		
		
		super.onResume();
	}

	// getting address of selected lat/lng
//	public void getAddress() {
//		try {
//				getAddressGivenLatLongFrom(Double.parseDouble(Data.seletedlat.toString()),
//						Double.parseDouble(Data.selectedlng.toString()),CheckOutCustomer.this);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}


	void serverCallPlaceOrder() {

		Data.saveInfoOf(getApplicationContext(), "phoneNumber", editTextPhone.getText().toString());
		RequestParams params = new RequestParams();
		Data.loading_box(this, "Loading...");
		params.put("access_token","" + Data.getAccessToken(getApplicationContext()));// +Data.ACCESS_TOKEN);
		params.put("price", "" + temp_price);
		params.put("drop_location_address", ""	+ textViewAddress.getText().toString());// +Data.ADDRESS);
		params.put("meal_id", "" + mealids);
		params.put("meal_quantity", "" + mealqty);
		params.put("topping_id", "");
		params.put("topping_quantity", "" );
		params.put("drop_latitude", "" + Data.LATITUDE);
		params.put("drop_longitude", "" + Data.LONGITUDE);
		params.put("phone_no","" + Data.getInfoOf(getApplicationContext(), "phoneNumber"));
		params.put("final_price", "" + total);
		params.put("remaining_credits", "" + remainingCredit);
		params.put("used_credits", "" + creditbalused);
        params.put("delivery_charges","" +Data.deliveryCharge );
		params.put("tax_charges","" +service );

		Log.d("access_token","access_token = " + Data.getAccessToken(getApplicationContext()));// +Data.ACCESS_TOKEN);
		Log.d("price", "price = " + temp_price);
		Log.d("drop_location_address", "drop_location_address = "+ textViewAddress.getText().toString());// +Data.ADDRESS);
		Log.d("meal_id", "meal_id = " + mealids);
		Log.d("meal_quantity", "meal_quantity = " + mealqty);
		Log.d("topping_id", "topping_id = " + Data.ToppingIds);
		Log.d("topping_quantity", "topping_quantity = " + Data.ToppingQty);
		Log.d("drop_latitude", "drop_latitude = " + Data.LATITUDE);
		Log.d("drop_longitude", "drop_longitude = " + Data.LONGITUDE);
		Log.d("phone_no","phone_no =" + Data.getInfoOf(getApplicationContext(), "phoneNumber"));		// params.put("promo_id", ""+promo_id);
		Log.d("final_price", "final_price = " + total);
		Log.d("remaining_credits", "remaining_credits = " + remainingCredit);
		Log.d("used_credits", "used_credits =" + creditbalused);
		

		Data.saveInfoOf(getApplicationContext(), "drop_location_address",
				textViewAddress.getText().toString());
		Data.saveInfoOf(getApplicationContext(), "phone_no",
				Data.getInfoOf(getApplicationContext(), "phoneNumber"));

		AsyncHttpClient client = new AsyncHttpClient();

		client.setTimeout(Httppost_Links.SERVER_TIME_OUT_TIME);
		client.post(Httppost_Links.Baselink + "place_order", params,
				new AsyncHttpResponseHandler() {

					@Override
					public void onSuccess(String response) {
						Data.loading_box_stop();
						JSONObject res;
						try {
							res = new JSONObject(response);
							if (res.getString("status").equals("2")) {
								Intent intent = new Intent(
										CheckOutCustomer.this,
										MainActivity.class);
								startActivity(intent);
								finish();
							} else {
								for (int i = 0; i < count.size(); i++) {
									Data.mealqunt = Data.mealqunt
											+ count.get(i) + "##" ;
									Data.mealname = Data.mealname
											+ hashmapList.get(i).get("NAME")
													.toString() + "##";
									Data.mealPrice = Data.mealPrice
											+ Double.parseDouble(hashmapList
													.get(i).get("PRICE")
													.toString()) * count.get(i)
											+ "##";
								}
								Data.mealqunt = Data.mealqunt + "--" + "##"+"--" + "##"
										+ "--" + "##" + "--";
								Data.mealname = Data.mealname + "Delivery & Tips"
										+ "##" + "Tax" + "##" + "Credit Used" + "##" + "Total";
								Data.mealPrice = Data.mealPrice + delivery
										+ "##" + service + "##" + creditbalused +"##" + total;

								Log.e("Data====", Data.mealname + "\n"
										+ Data.mealPrice + "\n" + Data.mealqunt +"\n creditbalused ="+creditbalused);
								Data.saveInfoOf(getApplicationContext(),
										"Details", Data.mealname + "@@"
												+ Data.mealPrice + "@@"
												+ Data.mealqunt);
								Data.mealname = "";
								Data.mealPrice = "";
								Data.mealqunt = "";

								Log.i("Response", "Response" + res.toString(2));
								if (res.has("error")) {
									Log.d("Data.SERVER_ERROR_MSG",
											"Data.SERVER_ERROR_MSG"
													+ res.getString("error"));
									String msg = res.getString("error");
									new DialogPopup().alertPopup(
											CheckOutCustomer.this, "OOPS!", ""
													+ msg);

								} else {

									Data.saveInfoOf(getApplicationContext(),
											"order_id",
											res.getString("order_id"));
									Data.saveInfoOf(getApplicationContext(),
											"GCM", "");

									Data.saveInfoOf(getApplicationContext(),
											"placeOrderId",
											res.getString("order_id"));
									
									
									Data.totalValue = 0;
									Data.oldResponse = "";
									Data.saveInfoOf(getApplicationContext(),
											Data.OrderPlacedFlag, "1");
									Data.saveInfoOf(getApplicationContext(),
											Data.DroppedLatitude, ""
													+ Data.LATITUDE);
									Data.saveInfoOf(getApplicationContext(),
											Data.DroppedLongitude, ""
													+ Data.LONGITUDE);

									Data.saveInfoOf(getApplicationContext(), Data.customer_estimate_time,res.getString("time"));
									Data.saveInfoOf(getApplicationContext(),"time", res.getString("time"));
									
									Log.e("Time", Data.customer_estimate_time);

									Intent intent = new Intent(
											CheckOutCustomer.this,
											ListofMeals.class);
									startActivity(intent);
									overridePendingTransition(
											R.anim.slide_in_left,
											R.anim.slide_out_right);
									finish();
								}
							}
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}

					@Override
					public void onFailure(Throwable arg0) {
						Log.e("request fail", arg0.toString());
						// finish();
						Data.loading_box_stop();
						new DialogPopup().alertPopup(CheckOutCustomer.this, "",
								"Server not responding\nTry again.");
					}
				});
	}

	void settoppingsids() {

		// ******************************* #
		String value1 = "", finalvalue = "", value2 = "", toppingqty = "", qty = "1";

		for (int i = 0; i < Data.selectedItems.size(); i++) {
			if (value2.equals(Data.selectedItems.get(i).getMealId())) {

			} else {
				value2 = Data.selectedItems.get(i).getMealId();
				if (i == 0) {
					finalvalue = finalvalue + "";
					toppingqty = toppingqty + "";
				} else {
					finalvalue = finalvalue + "@";
					toppingqty = toppingqty + "@";
				}
			}

			Log.d("meal id", "meal id" + Data.selectedItems.get(i).getMealId());
			if (Data.selectedItems.get(i).getTopping().size() == 0) {
				finalvalue = finalvalue + "#";
				toppingqty = toppingqty + "#";
			} else {
				for (int j = 0; j < Data.selectedItems.get(i).getTopping()
						.size(); j++) {
					Log.d("topping id", "topping id"
							+ Data.selectedItems.get(i).getTopping().get(j)
									.getToppingId());

					if (j == Data.selectedItems.get(i).getTopping().size() - 1) {

						if (Data.selectedItems.get(i).getMealId()
								.equals(value1)) {
							Log.d("12345", "12345");

							if (Data.selectedItems.size() < i
									&& Data.selectedItems.get(i + 1)
											.getMealId().equals(value1)) {
								toppingqty = toppingqty + "" + qty;
								finalvalue = finalvalue
										+ ""
										+ Data.selectedItems.get(i)
												.getTopping().get(j)
												.getToppingId();
							} else {
								if (!(Data.selectedItems.size() == i)) {
									toppingqty = toppingqty + qty + "#";
									finalvalue = finalvalue
											+ ""
											+ Data.selectedItems.get(i)
													.getTopping().get(j)
													.getToppingId() + "#";
								}

							}

						} else {
							Log.d("123", "123");
							value1 = Data.selectedItems.get(i).getMealId();
							finalvalue = finalvalue
									+ ""
									+ Data.selectedItems.get(i).getTopping()
											.get(j).getToppingId() + "#";
							toppingqty = toppingqty + "" + qty + "#";
						}
					} else {

						finalvalue = finalvalue
								+ ""
								+ Data.selectedItems.get(i).getTopping().get(j)
										.getToppingId() + ",";
						toppingqty = toppingqty + "" + qty + ",";

					}
					Log.d("value1fg", "value1dfg  =  " + finalvalue);
				}
			}
		}
		Log.d("value1fg", "value1dfg  =  " + finalvalue);
		finalvalue = finalvalue.substring(0, finalvalue.length() - 1);
		toppingqty = toppingqty.substring(0, toppingqty.length() - 1);
		finalvalue = finalvalue.replace("#@", "@");
		toppingqty = toppingqty.replace("#@", "@");
		Log.d("value1", "value  =  " + finalvalue);
		Log.d("dfdfg", "top qty  =  " + toppingqty);
		Data.ToppingIds = finalvalue;
		Data.ToppingQty = toppingqty;
		toppingprice();
		for (int i = 0; i < hashmapList.size(); i++) {
			Log.d("hashmaplist", "hashmapList" + hashmapList.get(i).toString());
		}

		for (int i = 0; i < count.size(); i++) {
			Log.d("count", "count" + count.get(i).toString());
		}
	}

	void toppingprice() {
		int flag = 0;
		Data.ToppingPrize = 0;
		for (int i = 0; i < Data.selectedItems.size(); i++) {
			for (int k = 0; k < Data.selectedItems.get(i).getTopping().size(); k++) {
				Data.ToppingPrize = Data.ToppingPrize
						+ Double.parseDouble(Data.selectedItems.get(i)
								.getTopping().get(k).getToppingPrice()
								.toString());
				arrayList.add(Integer.parseInt(Data.selectedItems.get(i)
						.getTopping().get(k).getToppingId().toString()));

				HashMap<String, String> hashMap = new HashMap<String, String>();
				hashMap.put("ID", Data.selectedItems.get(i).getTopping().get(k)
						.getToppingId().toString());
				hashMap.put("NAME",
						Data.selectedItems.get(i).getTopping().get(k)
								.getToppingName().toString());
				hashMap.put("PRICE", Data.selectedItems.get(i).getTopping()
						.get(k).getToppingPrice().toString());
				hashmapList.add(hashMap);
				flag = 1;
			}

		}

		if (flag == 1) {
			Collections.sort(arrayList);
			Collections.sort(hashmapList, new MapComparator("ID"));
			int j = 1;
			count.add(1);
			while (j < arrayList.size()) {
				if (arrayList.get(j - 1).equals(arrayList.get(j))) {
					hashmapList.remove(j);
					count.set(count.size() - 1, count.get(count.size() - 1) + 1);

					arrayList.remove(j);
				} else {
					count.add(1);
					j++;
				}
			}
			flag = 0;
		}

		Log.d("Final Prize  ---  ", "Final Prize  ---  " + Data.ToppingPrize
				+ " + " + Data.MealPrize);
	}

//	void serverCallPromoCode() {
//		RequestParams params = new RequestParams();
//
//		params.put("access_token",
//				Data.getInfoOf(getApplicationContext(), Data.ACCESS_TOKEN)); // +
//																				// Data.ACCESS_TOKEN);//
//																				// ?
//		params.put("meal_id", "" + mealids);
//		params.put("meal_quantity", "" + mealqty);
//		params.put("topping_id", "" + Data.ToppingIds);
//		params.put("topping_quantity", "" + Data.ToppingQty);
//		params.put("total_amount", "" + total);
//
//		AsyncHttpClient client = new AsyncHttpClient();
//
//		client.setTimeout(Httppost_Links.SERVER_TIME_OUT_TIME);
//		client.post(Httppost_Links.Baselink + "apply_promo_code", params,
//				new AsyncHttpResponseHandler() {
//
//					@Override
//					public void onSuccess(String response) {
//						Log.i("request succesfull", "response = " + response);
//						// fillServerData(response);
//						JSONObject res;
//						try {
//							res = new JSONObject(response.toString());
//							if (res.getString("status").equals("2")) {
//								Intent intent = new Intent(
//										CheckOutCustomer.this,
//										MainActivity.class);
//								startActivity(intent);
//								finish();
//							} else {
//								if (res.has("error")) {
//									Log.d("Data.SERVER_ERROR_MSG",
//											"Data.SERVER_ERROR_MSG"
//													+ res.getString("error"));
//									new DialogPopup().alertPopup(
//											CheckOutCustomer.this, "",
//											res.getString("error"));
//
//								} else {
//									promoDisc = Double.parseDouble(res
//											.getString("discount"));
//									promo_id = res.getString("promo_id");
//									relativeLayoutPromoDiscount
//											.setVisibility(View.VISIBLE);
//									String prizes = promoDisc.toString();
//									String str[] = prizes.split("\\.");
//									if (str.length == 1) {
//										TextViewPromoDiscountAmount
//												.setText(Html.fromHtml("-$"
//														+ promoDisc
//														+ ".<sup><small>"
//														+ "00"
//														+ "</small></sup>"));
//									} else {
//										TextViewPromoDiscountAmount
//												.setText(Html.fromHtml("-$"
//														+ str[0]
//														+ ".<sup><small>"
//														+ str[1]
//														+ "</small></sup>"));
//									}
//
//									total = total - promoDisc;
//									DecimalFormat df = new DecimalFormat("#.##");
//									total = Double.valueOf(df.format(total));
//
//									String prizes2 = total.toString();
//									String str2[] = prizes2.split("\\.");
//									if (str2.length == 1) {
//										textViewTotalAmount.setText(Html
//												.fromHtml("$" + total
//														+ ".<sup><small>"
//														+ "00"
//														+ "</small></sup>"));
//									} else {
//										textViewTotalAmount.setText(Html
//												.fromHtml("$" + str2[0]
//														+ ".<sup><small>"
//														+ str2[1]
//														+ "</small></sup>"));
//									}
//								}
//							}
//						} catch (JSONException e) {
//							e.printStackTrace();
//						}
//					}
//
//					@Override
//					public void onFailure(Throwable arg0) {
//						Log.e("request fail", arg0.toString());
//						new DialogPopup().alertPopup(CheckOutCustomer.this, "",
//								"Server not responding\nTry again.");
//					}
//				});
//
//	}

	void serverCallCheckZipCode(Double lat,Double longe) {
		try {
			Data.loading_box(this, "Checking...");
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		RequestParams params = new RequestParams();

		params.put("access_token",
				"" + Data.getAccessToken(getApplicationContext()));// +Data.EMAIL);
		params.put("latitude", "" + lat);// +Data.EMAIL);
		params.put("longitude", "" + longe);// +Data.EMAIL);
		Data.ZIPCODE = zipCode;
		AsyncHttpClient client = new AsyncHttpClient();

		client.setTimeout(Httppost_Links.SERVER_TIME_OUT_TIME);
		client.post(Httppost_Links.Baselink + "check_delivery_location", params,
				new AsyncHttpResponseHandler() {

					@Override
					public void onSuccess(String response) {
						Data.loading_box_stop();
						JSONObject res;
						try {
							res = new JSONObject(response);

							if (res.getString("status").equals("2")) {
								Intent intent = new Intent(
										CheckOutCustomer.this,
										MainActivity.class);
								startActivity(intent);
								finish();
							} else {
								Log.d("response", "response" + res.toString(2));
								if (res.has("error")) {
									Data.ZipCodeFlag = 0;
									Log.d("Data.SERVER_ERROR_MSG",
											"Data.SERVER_ERROR_MSG"
													+ res.getString("error"));
									new DialogPopup().alertPopup(
											CheckOutCustomer.this, "OOPS!", ""
													+ res.getString("error"));

								} else {
									Data.ZipCodeFlag = 1;
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
						new DialogPopup().alertPopup(CheckOutCustomer.this, "",
								"Server not responding\nTry again.");
					}
				});

	}

	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		Utils.buildAlertMessageNoGps(CheckOutCustomer.this);
		super.onWindowFocusChanged(hasFocus);
	}

	class MapComparator implements Comparator<Map<String, String>> {
		private final String key;

		public MapComparator(String key) {
			this.key = key;
		}

		public int compare(Map<String, String> first, Map<String, String> second) {
			// TODO: Null checking, both for maps and values
			String firstValue = first.get(key);
			String secondValue = second.get(key);
			return firstValue.compareTo(secondValue);
		}
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		EasyTracker.getInstance(CheckOutCustomer.this).activityStart(this);
		FlurryAgent.onStartSession(CheckOutCustomer.this, Data.flurryKey); 
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		EasyTracker.getInstance(CheckOutCustomer.this).activityStop(this);
		FlurryAgent.onEndSession(this);
	    FlurryAgent.endTimedEvent("checkout screen");
	}

	void setListSize(ListView listView, int height) {
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

			@Override
			public void onSuccess(String response) {
				try {
					JSONObject job = new JSONObject(response);
					Log.d("response","response"+response);
					if (response.contains("error")) {
						String error = job.getString("error");
						Log.i("Error ", "-----------" + error);
						new DialogPopup().alertPopup(activity, "", error);
					} else {
						String indiStr = "";
						 List <String> ziparray = new ArrayList<String>();
						
						JSONObject jsonObject = new JSONObject(response);
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
//									 Log.d("333","333");
//									 Log.d(" jsoo.getString(0)"," jsoo.getString(0)"+ jsoo.getString(0)+" = "+js.getString("long_name"));		
									
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

							if (InternetCheck.getInstance(getApplicationContext())
									.isOnline(getApplicationContext())) {
								Data.loading_box_stop();
								serverCallCheckZipCode(latitude,longitude);
							} else {
								new DialogPopup().alertPopup(CheckOutCustomer.this, "",
										"Check your internet connection");
							}
						Log.d("TAG", "address = " + address + ", city =" + city
								+ ", country = " + country + ", Zip Code:" + zipCode);
						Data.S_ADDRESS = "" + address ;//+ ", " + city + ", " + country;
						Data.selectedplace = Data.S_ADDRESS;
						textViewAddress.setText("" + Data.S_ADDRESS);
					}
				} catch (Exception exception) {
					exception.printStackTrace();
				}
				Data.loading_box_stop();
			}
			
			@Override
			public void onFailure(Throwable arg0) {
				Data.loading_box_stop();
				Data.S_ADDRESS = "Deliver at pin"; 		//+ ", " + city + ", " + country;
				Data.selectedplace = Data.S_ADDRESS;
				textViewAddress.setText("" + Data.S_ADDRESS);
//				new DialogPopup().alertPopup(activity, "",
//						"Google Server not responding\nTry again.");
				
			}
			
		});
		return retList;
	}
	
	
}
