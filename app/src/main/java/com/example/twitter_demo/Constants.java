package com.example.twitter_demo;

/**
 * This file is used for all constant value for twitter functioning. 
 * 
 * Project Name: - Fitmojis
 * Developed by ClickLabs. Developer: Ashish
 * Link: http://www.click-labs.com/
 */


import oauth.signpost.OAuthProvider;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;

public class Constants {

//	public static final String CONSUMER_KEY = "rHuznpNyW1EriHMnCtYYFThli";
//	public static final String CONSUMER_SECRET = "5FgemlOESvMLCJOR1CAAt2wDXhtgLoYNeFJOUGk5ExeG82BMLl";
	public static final String CONSUMER_KEY = "vDM3MT9FMzEGqFf0tvIsT7qGQ";
	public static final String CONSUMER_SECRET = "WrdsrB231hr7yfCvFw78kJI1MlOZggsDaqaMZN4BshFyhClXPQ";


	public static final String REQUEST_URL = "https://api.twitter.com/oauth/request_token";
	public static final String ACCESS_URL = "https://api.twitter.com/oauth/access_token";
	public static final String AUTHORIZE_URL = "https://api.twitter.com/oauth/authorize";

//	public static final String OAUTH_CALLBACK_SCHEME1 = "tumblrdemo-tapabl";
//	public static final String OAUTH_CALLBACK_HOST1 = "callback1";
//	public static final String OAUTH_CALLBACK_URL1 = OAUTH_CALLBACK_SCHEME1+ "://" + OAUTH_CALLBACK_HOST1;

	public static final String OAUTH_CALLBACK_SCHEME = "tumblrdemo-bitekite";
	public static final String OAUTH_CALLBACK_HOST = "callback";
	public static final String OAUTH_CALLBACK_URL = OAUTH_CALLBACK_SCHEME+ "://" + OAUTH_CALLBACK_HOST;

	public static CommonsHttpOAuthConsumer consumer = null;
	public static OAuthProvider provider = null;
}