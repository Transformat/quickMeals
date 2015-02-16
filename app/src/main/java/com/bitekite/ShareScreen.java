package com.bitekite;

import java.io.File;
import java.io.InputStream;
import java.text.DateFormat;
import java.util.Date;

import oauth.signpost.OAuth;
import oauth.signpost.OAuthConsumer;
import oauth.signpost.OAuthProvider;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthProvider;

import org.apache.http.impl.client.DefaultHttpClient;

import rmn.androidscreenlibrary.ASSL;
import twitter4j.StatusUpdate;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.auth.AccessToken;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.test.suitebuilder.annotation.SmallTest;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bitekite.utils.Data;
import com.example.twitter_demo.Constants;
import com.example.twitter_demo.OAuthRequestTokenTask;
import com.example.twitter_demo.TwitterUtils;
import com.facebook.FacebookException;
import com.facebook.FacebookOperationCanceledException;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.widget.FacebookDialog;
import com.facebook.widget.WebDialog;
import com.facebook.widget.WebDialog.OnCompleteListener;
import com.google.analytics.tracking.android.EasyTracker;

public class ShareScreen extends Activity {

	ImageView fb_icon, message_icon, file_icon, twitter_icon;
	RelativeLayout relativeLayoutShareScreen, relativeLayoutBack;
	TextView textViewReferralCode, textViewCreateAccount;

	String fbmessage, emailtext, tweet;
	UiLifecycleHelper uiHelper;

	private String var = "";
	public DefaultHttpClient client;
	public String tok;
	boolean twitterNotAuth;
	twitter4j.Status status;
	User user;
	public String secret;
	public SharedPreferences prefs;

	private Session.StatusCallback callback = new Session.StatusCallback() {
		@Override
		public void call(Session session, SessionState state,
				Exception exception) {
			onSessionStateChange(session, state, exception);
		}
	};

	private void onSessionStateChange(Session session, SessionState state,
			Exception exception) {
		if (state.isOpened()) {
			// shareFBButton.setVisibility(View.VISIBLE);

		} else if (state.isClosed()) {
			// shareFBButton.setVisibility(View.GONE);
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_share_screen);

		uiHelper = new UiLifecycleHelper(ShareScreen.this, callback);
		uiHelper.onCreate(savedInstanceState);

		relativeLayoutShareScreen = (RelativeLayout) findViewById(R.id.relativeLayoutShareScreen);
		new ASSL(this, relativeLayoutShareScreen, 1134, 720, false);

		prefs = PreferenceManager.getDefaultSharedPreferences(this);

		relativeLayoutBack = (RelativeLayout) findViewById(R.id.relativeLayoutBack);
		fb_icon = (ImageView) findViewById(R.id.fb_icon);
		message_icon = (ImageView) findViewById(R.id.message_icon);
		file_icon = (ImageView) findViewById(R.id.file_icon);
		twitter_icon = (ImageView) findViewById(R.id.twitter_icon);

		textViewCreateAccount = (TextView) findViewById(R.id.textViewCreateAccount);
		textViewReferralCode = (TextView) findViewById(R.id.textViewReferralCode);
		textViewCreateAccount.setTypeface(Data
				.bariol_bold(getApplicationContext()));
		textViewReferralCode.setTypeface(Data
				.bariol_regular(getApplicationContext()));
		textViewReferralCode.setText("Share a Meal with a friend!");

		fbmessage = "I've just tried Quick Meals and think you'd enjoy it too! Download the app to	your iPhone or Android for more info! bitekite.com";
		emailtext = "I've just tried Quick Meals and think you'd enjoy it too! Download the app to	your iPhone or Android for more info! bitekite.com";
		tweet = "I've just tried Quick Meals and think you'd enjoy it too! Download from bitekite.com";

		relativeLayoutBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
				overridePendingTransition(R.anim.hold, R.anim.slide_out_right);
			}
		});

		fb_icon.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				shareFB();
			}
		});

		message_icon.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				Intent emailIntent = new Intent(Intent.ACTION_SEND);
				emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[] { "" });
				emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,
						"You've been invited to try Quick Meals for lunch!");
				emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, ""
						+ emailtext);
				emailIntent.setType("plain/text");
				try {
					startActivity(Intent.createChooser(emailIntent,
							"Send mail..."));
					finish();
					Log.i("Finished sending email...", "");
				} catch (android.content.ActivityNotFoundException ex) {
				}
			}
		});

		file_icon.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Uri smsUri = Uri.parse("");
				Intent intent = new Intent(Intent.ACTION_SENDTO);
				intent.setData(Uri.parse("smsto:" + smsUri));
				intent.putExtra("sms_body", "" + fbmessage);
				startActivity(intent);
			}
		});

		twitter_icon.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				var = "login";
				if (!Data.Internetcheck(ShareScreen.this)) {
					Data.getAlertDialog(ShareScreen.this);
				} else {
					new logout().execute();
				}
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.share_screen, menu);
		return true;
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		finish();
		overridePendingTransition(R.anim.hold, R.anim.slide_out_right);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		Session.getActiveSession().onActivityResult(ShareScreen.this,
				requestCode, resultCode, data);
		uiHelper.onActivityResult(requestCode, resultCode, data,
				new FacebookDialog.Callback() {
					@Override
					public void onError(FacebookDialog.PendingCall pendingCall,
							Exception error, Bundle data) {
						error.printStackTrace();
						Log.e("Activity Bundle ", "" + data);
						Log.e("Activity error ", "" + error);
						Log.e("Activity pendingCall ", "" + pendingCall);
					}

					@Override
					public void onComplete(
							FacebookDialog.PendingCall pendingCall, Bundle data) {
						Log.i("Activity", "Success!");
					}
				});
	}

	@Override
	public void onResume() {
		super.onResume();
		// For scenarios where the main activity is launched and user
		// session is not null, the session state change notification
		// may not be triggered. Trigger it if it's open/closed.
		Session session = Session.getActiveSession();
		if (session != null && (session.isOpened() || session.isClosed())) {

		}

		uiHelper.onResume();
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		uiHelper.onSaveInstanceState(outState);
	}

	@Override
	public void onPause() {
		super.onPause();
		uiHelper.onPause();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		uiHelper.onDestroy();
	}

	public void shareFB() {

		if (FacebookDialog.canPresentShareDialog(ShareScreen.this,
				FacebookDialog.ShareDialogFeature.SHARE_DIALOG)) {
			FacebookDialog shareDialog = new FacebookDialog.ShareDialogBuilder(
					ShareScreen.this).setName("QuickMeals")
					.setDescription(fbmessage + "")
					.setLink(getString(R.string.sharemsglink))
					.setPicture(getString(R.string.sharepicturelink)).build();
			uiHelper.trackPendingDialogCall(shareDialog.present());
		} else {
			// Fallback. For example, publish the post using the Feed Dialog
			publishFeedDialog();
		}
	}

	private void publishFeedDialog() {
		final Bundle params = new Bundle();
		params.putString("name", "QuickMeals");
		params.putString("description", fbmessage);
		params.putString("link", getString(R.string.sharemsglink));
		params.putString("picture", getString(R.string.sharepicturelink));
		Log.i("Session.getActiveSession()", "=" + Session.getActiveSession());
		if (Session.getActiveSession() == null) {
			openActiveSession(params);
			return;
		} else {
			Log.i("Session.getActiveSession() state ", "="
					+ Session.getActiveSession().getState());
			if (Session.getActiveSession().getState() == SessionState.OPENED
					|| Session.getActiveSession().getState() == SessionState.OPENED_TOKEN_UPDATED) {
				openWebDialog(params);
			} else {
				openActiveSession(params);
				return;
			}
		}

	}

	public void openWebDialog(final Bundle params) {
		WebDialog feedDialog = (new WebDialog.FeedDialogBuilder(
				ShareScreen.this, Session.getActiveSession(), params))
				.setOnCompleteListener(new OnCompleteListener() {

					@Override
					public void onComplete(Bundle values,
							FacebookException error) {

						if (error == null) {
							// When the story is posted, echo the success
							// and the post Id.
							final String postId = values.getString("post_id");
							Log.d("post_id",
									"post_id" + values.getString("post_id"));
							if (postId != null) {
							} else {
								// User clicked the Cancel button

							}
						} else if (error instanceof FacebookOperationCanceledException) {
							// User clicked the "x" button
							Log.d("error", "error" + error.getMessage());
						} else {
							// Generic, ex: network error
							Log.d("error2", "error2" + error.getMessage());
						}
					}

				}).build();
		feedDialog.show();
	}

	public void openActiveSession(final Bundle params) {
		Session.openActiveSession(ShareScreen.this, true,
				new Session.StatusCallback() {
					@Override
					public void call(Session session, SessionState state,
							Exception exception) {
						Session.setActiveSession(session);
						Log.i("openActiveSession Session.getActiveSession() state ",
								"=" + Session.getActiveSession().getState());
						if (Session.getActiveSession().getState() == SessionState.OPENED
								|| Session.getActiveSession().getState() == SessionState.OPENED_TOKEN_UPDATED) {
							openWebDialog(params);
						}
					}
				});
	}

	/********* twitter share *************/
	/**
	 * Async for twitter share
	 * 
	 * @author ashish
	 * 
	 */
	public class ReadData extends AsyncTask<String, String, String> {
		@Override
		protected String doInBackground(String... params) {
			try {
				if ("login".equals(var)) {
					Log.e("called", "called");
					loginTweet();
				}
			} catch (Exception e) {
				Log.v("Error", e.toString());
			}
			return "";
		}

		@Override
		protected void onPostExecute(String result) {
			if ("not".equals(var)) {
				var = "";
				if (!Data.Internetcheck(ShareScreen.this)) {
					Data.getAlertDialog(ShareScreen.this);
				} else {
					new RetrieveData().execute("data");
				}
			} else if ("login".equals(var)) {
				var = "";
			}
		}
	}

	/**
	 * Async class to retreive data from twitter
	 * 
	 * @author ashish
	 * 
	 */

	class RetrieveData extends AsyncTask<String, Void, String> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			Data.loading_box(ShareScreen.this, "Loading...");
		}

		protected String doInBackground(String... urls) {
			try {
				String url = urls[0];
				Constants constants = new Constants();
				String consumerKey = Constants.CONSUMER_KEY;
				String consumerSecret = Constants.CONSUMER_SECRET;
				String accessSecret = secret;
				String acessTOken = tok;
				Twitter twitter = new TwitterFactory().getInstance();
				twitter.setOAuthConsumer(consumerKey, consumerSecret);
				// Access Token
				AccessToken accessToken = null;
				accessToken = new AccessToken(acessTOken, accessSecret);
				twitter.setOAuthAccessToken(accessToken);
				String currentDateTimeString = DateFormat.getDateTimeInstance()
						.format(new Date());
				SharedPreferences pref = getSharedPreferences("MyPref1", 0);
				Editor editor = pref.edit();
				editor.putString("twitter_name", twitter.getScreenName());
				editor.commit();
				try {
					StatusUpdate status = new StatusUpdate(" " + tweet);
					InputStream is = getResources().openRawResource(
							R.drawable.app_icon);
					status.setMedia("bitekite.jpg", is);
					twitter.updateStatus(status);
				} catch (TwitterException e) {
					Log.d("TAG 12", "Pic Upload error" + e.getExceptionCode());
					throw e;
				}
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(String text) {
			Toast.makeText(ShareScreen.this, "Posted Successfully!", 1000)
					.show();
			Data.loading_box_stop();
		}
	};

	@Override
	public void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		Log.i("TAG", "ONNEWINTENT");
		Log.i("TAG", "after share");
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(ShareScreen.this);
		final Uri uri = intent.getData();
		if (uri != null
				&& uri.getScheme().equals(Constants.OAUTH_CALLBACK_SCHEME)) {
			Log.i("TAG", "Callback received : " + uri);
			Log.i("TAG", "Retrieving Access Token");
			new RetrieveAccessTokenTask(ShareScreen.this, Constants.consumer,
					Constants.provider, prefs).execute(uri);
		}
	}

	/**
	 * Async for retrieve acess token from twitter
	 * 
	 * @author ashish
	 * 
	 */
	public class RetrieveAccessTokenTask extends AsyncTask<Uri, Void, Void> {
		private Context context;
		private OAuthProvider provider;
		private OAuthConsumer consumer;
		private SharedPreferences prefs;

		public RetrieveAccessTokenTask(Context context, OAuthConsumer consumer,
				OAuthProvider provider, SharedPreferences prefs) {
			this.context = context;
			this.consumer = consumer;
			this.provider = provider;
			this.prefs = prefs;
			Log.e("running....", "dsfsd");

		}

		@Override
		protected Void doInBackground(Uri... params) {
			final Uri uri = params[0];
			final String oauth_verifier = uri
					.getQueryParameter(OAuth.OAUTH_VERIFIER);
			Log.e("running....", "34234234");
			try {
				provider.retrieveAccessToken(consumer, oauth_verifier);

				final Editor edit = prefs.edit();
				edit.putString(OAuth.OAUTH_TOKEN, consumer.getToken());
				edit.putString(OAuth.OAUTH_TOKEN_SECRET,
						consumer.getTokenSecret());
				edit.commit();
				Log.e("running....", "asdfwd.....");
				String token1 = prefs.getString(OAuth.OAUTH_TOKEN, "");
				String secret1 = prefs.getString(OAuth.OAUTH_TOKEN_SECRET, "");
				consumer.setTokenWithSecret(token1, secret1);
				AccessToken accessToken = new AccessToken(token1, secret1);
				final Twitter twitter = new TwitterFactory().getInstance();
				twitter.setOAuthConsumer(Constants.CONSUMER_KEY,
						Constants.CONSUMER_SECRET);
				twitter.setOAuthAccessToken(accessToken);
				try {
					SharedPreferences pref = getSharedPreferences("MyPref", 0);
					Editor editor = pref.edit();
					editor.putString("twitter_name", twitter.getScreenName());
					editor.commit();
				} catch (IllegalStateException e) {
					e.printStackTrace();
				} catch (TwitterException e) {
					e.printStackTrace();
				}

				long userId = accessToken.getUserId();
				user = twitter.showUser(userId);
				String username = user.getName();
				Log.e("USER NAME", "user name: " + username);
				tok = accessToken.getToken();
				secret = accessToken.getTokenSecret();
				Log.e("ACCESS TOKEN", "access token: " + tok);
				executeAfterAccessTokenRetrieval();
				Log.i("TAG", "OAuth - Access Token Retrieved");
			} catch (Exception e) {
				Log.e("TAG", "OAuth - Access Token Retrieval Error", e);
			}
			return null;
		}

		public void executeAfterAccessTokenRetrieval() {
			// TODO Auto-generated method stub
			var = "not";
			client = new DefaultHttpClient();
			if (!Data.Internetcheck(ShareScreen.this)) {
				Data.getAlertDialog(ShareScreen.this);
			} else {
				new ReadData().execute(" ");
			}
		}
	}

	/**
	 * function for login to twitter if user not authorize
	 */
	public void loginTweet() {
		try {
			Log.i("TAG", "3");
			Constants.consumer = new CommonsHttpOAuthConsumer(
					Constants.CONSUMER_KEY, Constants.CONSUMER_SECRET);
			Constants.provider = new CommonsHttpOAuthProvider(
					Constants.REQUEST_URL, Constants.ACCESS_URL,
					Constants.AUTHORIZE_URL);
		} catch (Exception e) {
			Log.e("TAG", "Error creating consumer / provider", e);
		}

		Log.i("TAG", "Starting task to retrieve request token.");
		new OAuthRequestTokenTask(ShareScreen.this, Constants.consumer,
				Constants.provider, 1).execute();
	}

	/**
	 * Async class for twitter share
	 * 
	 * @author ashish
	 * 
	 */
	class logout extends AsyncTask<Void, Void, String> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			Data.loading_box(ShareScreen.this, "Loading...");
		}

		protected String doInBackground(Void... urls) {
			if (TwitterUtils.isAuthenticated(prefs)) {
				Log.i("authenticated", "authenticated");
				try {
					String token = prefs.getString(OAuth.OAUTH_TOKEN, "");
					String secret = prefs.getString(OAuth.OAUTH_TOKEN_SECRET,
							"");
					twitter4j.auth.AccessToken accessToken = new twitter4j.auth.AccessToken(
							token, secret);
					Twitter twitter = new TwitterFactory().getInstance();
					twitter.setOAuthConsumer(Constants.CONSUMER_KEY,
							Constants.CONSUMER_SECRET);
					twitter.setOAuthAccessToken(accessToken);
					String currentDateTimeString = DateFormat
							.getDateTimeInstance().format(new Date());
					String temp = "hello";
					SharedPreferences pref = getSharedPreferences("MyPref1", 0);
					Editor editor = pref.edit();
					editor.putString("twitter_name", twitter.getScreenName());
					editor.commit();
					try {
						StatusUpdate status = new StatusUpdate(" " + tweet);
						InputStream is = getResources().openRawResource(
								R.drawable.app_icon);
						status.setMedia("bitekite.jpg", is);
						twitter.updateStatus(status);
					} catch (TwitterException e) {
						Log.d("TAG 13", "Pic Upload  " + e.getExceptionCode());
						throw e;
					}
				} catch (Exception e) {
					Log.i("in catch", e.toString());
				}
				twitterNotAuth = false;
			} else {
				if (!Data.Internetcheck(ShareScreen.this)) {
					Data.getAlertDialog(ShareScreen.this);
				} else {
					twitterNotAuth = true;
					new ReadData().execute(" ");
				}
				Log.i("authenticated", "not authenticated");
			}
			return null;
		}

		protected void onPostExecute(String text) {
			Data.loading_box_stop();
			if (!twitterNotAuth) {
				Toast.makeText(ShareScreen.this, "Posted Successfully!", 1000)
						.show();
			}
		}
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		EasyTracker.getInstance(ShareScreen.this).activityStart(this);
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		EasyTracker.getInstance(ShareScreen.this).activityStop(this);
	}

}
