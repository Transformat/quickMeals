package com.example.twitter_demo;

/**
 * This file is used twitter functiong. 
 * 
 * Project Name: - Fitmojis
 * Developed by ClickLabs. Developer: Ashish
 * Link: http://www.click-labs.com/
 */

import oauth.signpost.OAuth;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import android.content.SharedPreferences;
import android.util.Log;

public class TwitterUtils {
/**
 * 
 * @param prefs
 * @return
 */
	public static boolean isAuthenticated(SharedPreferences prefs) {
		String token = prefs.getString(OAuth.OAUTH_TOKEN, "");
		String secret = prefs.getString(OAuth.OAUTH_TOKEN_SECRET, "");
		AccessToken a = new AccessToken(token, secret);
		Twitter twitter = new TwitterFactory().getInstance();
		twitter.setOAuthConsumer(Constants.CONSUMER_KEY,Constants.CONSUMER_SECRET);
		twitter.setOAuthAccessToken(a);

		try {
			twitter.getAccountSettings();
			return true;
		} catch (TwitterException e) {
			return false;
		}
	}
/**
 * 
 * @param prefs
 * @param msg
 * @throws Exception
 */
	public static void sendTweet(SharedPreferences prefs, String msg)
			throws Exception {
		try {
			String token = prefs.getString(OAuth.OAUTH_TOKEN, "");
			String secret = prefs.getString(OAuth.OAUTH_TOKEN_SECRET, "");
			AccessToken a = new AccessToken(token, secret);
			Twitter twitter = new TwitterFactory().getInstance();
			twitter.setOAuthConsumer(Constants.CONSUMER_KEY,Constants.CONSUMER_SECRET);
			twitter.setOAuthAccessToken(a);
			twitter.updateStatus(msg);
		} catch (Exception e) {
			Log.e("error", e.toString());
		}
	}
}
