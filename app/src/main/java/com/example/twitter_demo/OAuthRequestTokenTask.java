package com.example.twitter_demo;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.OAuthProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

/**
 * An asynchronous task that communicates with Twitter to retrieve a request
 * token. (OAuthGetRequestToken)
 * 
 * After receiving the request token from Twitter, pop a browser to the user to
 * authorize the Request Token. (OAuthAuthorizeToken)
 * 
 */
public class OAuthRequestTokenTask extends AsyncTask<Void, Void, Void> {

	final String TAG = getClass().getName();
	private Context context;
	private OAuthProvider provider;
	private OAuthConsumer consumer;
	int page;

	/**
	 * 
	 * We pass the OAuth consumer and provider.
	 * 
	 * @param context
	 *            Required to be able to start the intent to launch the browser.
	 * @param provider
	 *            The OAuthProvider object
	 * @param consumer
	 *            The OAuthConsumer object
	 * @param sendtweet
	 * @param t_tweet
	 */
	public OAuthRequestTokenTask(Context context, OAuthConsumer consumer,
			OAuthProvider provider, int page) {
		this.context = context;
		this.consumer = consumer;
		this.provider = provider;
		this.page = page;
		// Log.i(TAG, "4");
	}

	/**
	 * 
	 * Retrieve the OAuth Request Token and present a browser to the user to
	 * authorize the token.
	 * 
	 */
	@Override
	protected Void doInBackground(Void... params) {

		try {
			Log.i(TAG, "Retrieving request token from Google servers");
			final String url;
			if (page == 1) {
				Log.e("pageee", "paaaaggge....111111");
				url = provider.retrieveRequestToken(consumer,Constants.OAUTH_CALLBACK_URL);
				Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url))
						.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP
								| Intent.FLAG_ACTIVITY_NO_HISTORY
								| Intent.FLAG_FROM_BACKGROUND);
				context.startActivity(intent);
			}
//			} else if (page == 2) {
//				Log.e("pageee", "paaaaggge....2222");
//				url = provider.retrieveRequestToken(consumer,Constants.OAUTH_CALLBACK_URL);
//				Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url))
//						.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP
//								| Intent.FLAG_ACTIVITY_NO_HISTORY
//								| Intent.FLAG_FROM_BACKGROUND);
//				context.startActivity(intent);
//			}
			// Log.i(TAG, "Popping a browser with the authorize URL : " + url);
			// Intent intent = new Intent(Intent.ACTION_VIEW,
			// Uri.parse(url)).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP
			// | Intent.FLAG_ACTIVITY_NO_HISTORY| Intent.FLAG_FROM_BACKGROUND);
			// context.startActivity(intent);
		} catch (Exception e) {
			Log.e(TAG, "Error during OAUth retrieve request token", e);
			// Toast.makeText(context,
			// "Problem Logging In to Twitter.Try Later ",
			// Toast.LENGTH_SHORT).show();

		}

		return null;
	}
}