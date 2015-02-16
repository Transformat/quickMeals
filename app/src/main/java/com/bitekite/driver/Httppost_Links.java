package com.bitekite.driver;

public class Httppost_Links {

    //#define MD_BaseUrl @"http://app.bitekite.com:1000"    //AppStore version 1
//#define MD_BaseUrl @"http://uberformealdelivery.clicklabs.in:1002"  //Version 2 Development
//#define MD_BaseUrl @"http://uberformealdelivery.clicklabs.in:1001"  // Version 1 Development
//#define MD_BaseUrl @"http://uberformealdelivery.clicklabs.in:1000"  //Distribution
//	public static final String Baselink="http://app.bitekite.com:1001/";   //live version 2
	
//	public static final String Baselink ="http://uberformealdelivery.clicklabs.in:1002/";
//	public static final String Baselink="http://app.bitekite.com:1006/"; 		//testing V2
//	public static final String Baselink="http://app.bitekite.com:1007/"; 		//testing V3
//	public static final String Baselink="http://app.bitekite.com:1001/";		//Live
//	public static final String Baselink="http://app.bitekite.com:1003/";        //-- base url bitekite v2 
//	public static final String Baselink="http://app.bitekite.com:1004/";		//Live Version 103.
	
//	public static final String Baselink="http://app.bitekite.com:1015/";        //testing V3
    public static final String Baselink="http://54.67.92.77:1016/";
	
//	public static final String Baselink="http://app.bitekite.com:1008/"; 		//live V4
	
	public static final String BrowserKey="AIzaSyDA3lQ5nQetpndjXPmsgzXJ_W53uTbM5jk";			//AIzaSyDA3lQ5nQetpndjXPmsgzXJ_W53uTbM5jk
	
	
	public static final String CHEKIN = Baselink+"driver_check_in";
	public static final String ORDER =  Baselink+"list_orders";
	public static final String HISTORY = Baselink+"list_completed_orders";
	public static final String DRIVER_EARNINGS = Baselink + "driver_earnings";
	public static final String CHECKOUT = Baselink + "driver_check_out";
	public static final String ORDER_DELIVER = Baselink + "order_deliver_confirm";
	public static final String START_ORDER = Baselink + "start_order";
	public static final String PARTICULAR_ORDER_DETAILS = Baselink+ "particular_order_details";
	public static final int SERVER_TIME_OUT_TIME = 30000;
	public static final String CHANGE_PASSWORD = Baselink +"change_password";
	public static final String EDIT_PROFILE = Baselink +"edit_profile";
	public static final String FORGOT_PASSWORD = Baselink +"forgotPasswordFromEmail";
	
	
	public static final String CLIENT_ID="ca_4s8h0J1dj6kRP1gHSCb7ZbnxH9o6U2LM";				//test
//	public static final String CLIENT_ID="ca_52gVtVII8gBttp9j68fS0rQnUWxorK4l";		//LIVE				   // Real =ca_4s8hjU1jmjhvYn4AETHQR3BYDZUFgSjF
	
	
	public static final String STRIPE_URL_DRIVER = "https://connect.stripe.com/oauth/authorize?response_type=code&client_id="+CLIENT_ID+"&scope=read_write&state=1234";
//	public static final String GOOGLE_STRIPE_URL = "http://uberformeal.s3.amazonaws.com/meal_image/u8km1412577425426";//"www.google.com";//http://uberformeal.s3.amazonaws.com/user_image/whiteimage.jpg
	public static final String STRIPE_TOKEN_URL= "https://connect.stripe.com/oauth/token?";

	
}
