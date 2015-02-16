package com.bitekite;

import rmn.androidscreenlibrary.ASSL;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bitekite.utils.Data;

public class Policy extends Activity {

	RelativeLayout relativeLayoutBack, relativeLayoutPolicy;
	TextView textViewPolicyHeader;// ,textViewContent;
	WebView webView;
	String tnc, policy;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_policy);

		relativeLayoutPolicy = (RelativeLayout) findViewById(R.id.relativeLayoutPolicy);
		new ASSL(this, relativeLayoutPolicy, 1134, 720, false);

		relativeLayoutBack = (RelativeLayout) findViewById(R.id.relativeLayoutBack);
		textViewPolicyHeader = (TextView) findViewById(R.id.textViewCreateAccount);

		textViewPolicyHeader.setTypeface(Data
				.bariol_bold(getApplicationContext()));
		webView = (WebView) findViewById(R.id.webView1);

		if (CreateAccount.Flag == 0) {
			policy = "file:///android_asset/Privacy.html";
			webView.loadUrl(policy);
			textViewPolicyHeader.setText("PRIVACY POLICY");
		} else {
			tnc = "file:///android_asset/Tnc.html";
			webView.loadUrl(tnc);
			textViewPolicyHeader.setText("TERMS OF SERVICE");
		}

		relativeLayoutBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
				overridePendingTransition(R.anim.hold, R.anim.slide_out_right);
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.policy, menu);
		return true;
	}
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		finish();
		overridePendingTransition(R.anim.hold, R.anim.slide_out_right);
	}

}
