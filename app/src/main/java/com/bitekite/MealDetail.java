package com.bitekite;

import rmn.androidscreenlibrary.ASSL;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.bitekite.driver.PushUpdater;
import com.bitekite.driver.Utils;
import com.bitekite.utils.Data;
import com.google.analytics.tracking.android.EasyTracker;
import com.squareup.picasso.Picasso;

public class MealDetail extends Activity implements PushUpdater {

	String meal_id, meal_description, price, stock_available, meal_image, name,
			meal_details,chefImagethumb;
	RelativeLayout relativeLayoutMealDetail, relativeLayoutBack,relativeLayoutChefClickable;
	TextView textViewDetails, textViewDetailDescription, textViewDescription,
			textViewPrice,chefName,chefDescription;
	ImageView imageView2,chefImage;
	private AQuery aq;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_meal_detail);

		relativeLayoutMealDetail = (RelativeLayout) findViewById(R.id.relativeLayoutMealDetail);
		new ASSL(this, relativeLayoutMealDetail, 1134, 720, false);

		imageView2 = (ImageView) findViewById(R.id.imageView2);
		textViewDetails = (TextView) findViewById(R.id.textViewDetails);
		textViewDetailDescription = (TextView) findViewById(R.id.textViewDetailDescription);
		textViewDescription = (TextView) findViewById(R.id.textViewDescription);
		textViewPrice = (TextView) findViewById(R.id.textViewPrice);
		relativeLayoutBack = (RelativeLayout) findViewById(R.id.relativeLayoutBack);
        relativeLayoutChefClickable= (RelativeLayout) findViewById(R.id.chef_page_click_layout);
        chefName= (TextView) findViewById(R.id.chef_name_detail_page);
        chefDescription= (TextView) findViewById(R.id.chef_description_detail_page);
        chefImage= (ImageView) findViewById(R.id.chef_image_detail_page);
        textViewDetails.setTypeface(Data.bariol_bold(getApplicationContext()));
		textViewDetailDescription.setTypeface(Data
				.bariol_regular(getApplicationContext()));
		textViewDescription.setTypeface(Data
				.bariol_regular(getApplicationContext()));
		textViewPrice.setTypeface(Data.bariol_regular(getApplicationContext()));

		final Bundle bundle = getIntent().getExtras();
		meal_id = bundle.getString("meal_id");
		meal_description = bundle.getString("meal_description");
		price = bundle.getString("price");
		stock_available = bundle.getString("stock_available");
		meal_image = bundle.getString("meal_image");
		name = bundle.getString("name");
		meal_details = bundle.getString("meal_details");
        chefDescription.setText(bundle.getString("chef_description"));
        chefName.setText(bundle.getString("chef_name"));
        chefImagethumb=bundle.getString("chef_image");
		aq = new AQuery(getApplicationContext());

		aq.id(imageView2).image(meal_image, true, true, 0, 0, null, 0,
				(360 * ASSL.Xscale()) / (720 * ASSL.Yscale()));
        aq.id(chefImage).image(chefImagethumb, true, true, 0, 0, null, 0,
                (150 * ASSL.Xscale()) / (150 * ASSL.Yscale()));

		textViewDetailDescription.setText(meal_details);
		textViewDescription.setText(meal_description);

		String prize = price.toString();
		String str[] = prize.split("\\.");
		if (str.length == 1) {
			textViewPrice.setText(Html.fromHtml("$" + price + ".<sup><small>"
					+ "00" + "</small></sup>"));
		} else {
			textViewPrice.setText(Html.fromHtml("$" + str[0] + ".<sup><small>"
					+ str[1] + "</small></sup>"));
		}

		relativeLayoutBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
				overridePendingTransition(R.anim.slide_in_left,
						R.anim.slide_out_right);
			}
		});
        relativeLayoutChefClickable.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent chefDetails=new Intent(MealDetail.this,ChefDetailsActivity.class);
                chefDetails.putExtra("chef_name",bundle.getString("chef_name"));
                chefDetails.putExtra("chef_post",bundle.getString("chef_post"));
                chefDetails.putExtra("chef_details",bundle.getString("chef_full_details"));
                chefDetails.putExtra("chef_full_image",bundle.getString("chef_full_image"));
                startActivity(chefDetails);
            }
        });

	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
		finish();
	}

	protected void onResume() {
		ListofMeals.pushUpdater = this;
		super.onResume();

	}

	protected void onPause() {
		super.onPause();
		Log.d("onPause", "onPause");
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
						if (GCMIntentService.flag.equals("41")) {
							Intent intent = new Intent(MealDetail.this,
									DriverLocationFinder.class);
							startActivity(intent);
							overridePendingTransition(R.anim.slide_in_right,
									R.anim.hold);
						}
					}
				});
			}
		}).start();
	}

	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		Utils.buildAlertMessageNoGps(MealDetail.this);
		super.onWindowFocusChanged(hasFocus);
	}

	@Override
	protected void onStart() {
		super.onStart();
		EasyTracker.getInstance(MealDetail.this).activityStart(this);
	}

	@Override
	protected void onStop() {
		super.onStop();
		EasyTracker.getInstance(MealDetail.this).activityStop(this);
	}
}
