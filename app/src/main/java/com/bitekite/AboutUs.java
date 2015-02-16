package com.bitekite;

/*	
 * In this file we are showing the about us content.
 */
import org.json.JSONException;
import org.json.JSONObject;

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

import com.bitekite.driver.Httppost_Links;
import com.bitekite.driver.PushUpdater;
import com.bitekite.driver.Utils;
import com.bitekite.utils.Data;
import com.google.analytics.tracking.android.EasyTracker;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class AboutUs extends Activity implements PushUpdater {

	RelativeLayout relativeLayoutBack, relativeLayoutAboutUs,
			relativeLayoutCover;
	TextView textViewAbout, textViewContent;
	ImageView imageViewLoader;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about_us);

		relativeLayoutAboutUs = (RelativeLayout) findViewById(R.id.relativeLayoutAboutUs);
		new ASSL(this, relativeLayoutAboutUs, 1134, 720, false);

		relativeLayoutBack = (RelativeLayout) findViewById(R.id.relativeLayoutBack);
		textViewAbout = (TextView) findViewById(R.id.textViewCreateAccount);
		textViewContent = (TextView) findViewById(R.id.textView1);
		relativeLayoutCover = (RelativeLayout) findViewById(R.id.relativeLayoutCover);
		imageViewLoader = (ImageView) findViewById(R.id.imageViewLoader);

		textViewAbout.setTypeface(Data.bariol_bold(getApplicationContext()));
		textViewContent.setTypeface(Data
				.bariol_regular(getApplicationContext()));
		serverCallAboutUs();
		relativeLayoutBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
				overridePendingTransition(R.anim.hold, R.anim.slide_out_right);
			}
		});
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();

		finish();
		overridePendingTransition(R.anim.hold, R.anim.slide_out_right);
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
							Intent intent = new Intent(AboutUs.this,
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
		Utils.buildAlertMessageNoGps(AboutUs.this);
		super.onWindowFocusChanged(hasFocus);
	}

	/*
	 * server call for getting about us content.
	 */

	void serverCallAboutUs() {
		RequestParams params = new RequestParams();

		AsyncHttpClient client = new AsyncHttpClient();

		client.setTimeout(Httppost_Links.SERVER_TIME_OUT_TIME);
		client.get(Httppost_Links.Baselink + "support_page", params,
				new AsyncHttpResponseHandler() {

					@Override
					public void onSuccess(String response) {
						JSONObject res;
						try {

							res = new JSONObject(response);
							Log.i("Response", "Response" + res.toString(2));

							if (res.getString("status").equals("2")) {
								Intent intent = new Intent(AboutUs.this,
										MainActivity.class);
								startActivity(intent);
								finish();
							} else {
								JSONObject user_data = res
										.getJSONObject("data");

								String support_text = user_data
										.getString("support_text");
								String support_no = user_data
										.getString("support_no");

								Log.d("support_text", "support_text"
										+ support_text.replace("\\n", "\n"));
								textViewContent.setText(Html
										.fromHtml(support_text));
								relativeLayoutCover
										.setVisibility(View.INVISIBLE);
								imageViewLoader.setVisibility(View.INVISIBLE);
							}

						} catch (JSONException e) {
							e.printStackTrace();
						}
					}

					@Override
					public void onFailure(Throwable arg0) {
						Log.e("request fail", arg0.toString());
					}
				});
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		EasyTracker.getInstance(AboutUs.this).activityStart(this);
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		EasyTracker.getInstance(AboutUs.this).activityStop(this);
	}
}
