package com.bitekite;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.bitekite.driver.Httppost_Links;
import com.bitekite.driver.OrderDelivered;
import com.bitekite.driver.OrderScreen;
import com.bitekite.utils.Data;
import com.bitekite.utils.DialogPopup;
import com.bitekite.utils.InternetCheck;
import com.bitekite.utils.LocationFetcherForeground;
import com.facebook.AppEventsLogger;
import com.flurry.android.FlurryAgent;
import com.google.analytics.tracking.android.EasyTracker;
import com.google.android.gcm.GCMRegistrar;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import rmn.androidscreenlibrary.ASSL;

public class SplashScreen extends Activity {

    ImageView imageViewLogo;
    Animation fadeOut;
    Data data;
    AlertDialog gpsDialogAlert;
    LocationFetcherForeground locationFetcherForeground;
    Handler mHandler;
    EasyTracker easyTracker;
    ArrayList<Address> retList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//		Crashlytics.start(this);
        setContentView(R.layout.activity_splash_screen);
        new ASSL(this, (ViewGroup) findViewById(R.id.root), 1134, 720, false);

        data = new Data();
        Data.totalValue = 0;
        imageViewLogo = (ImageView) findViewById(R.id.logo);
        fadeOut = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.fade_out);
        easyTracker = EasyTracker.getInstance(this);

        FlurryAgent.init(this, Data.flurryKey);

        fadeOut.setAnimationListener(new AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
                //            imageViewLogo.setVisibility(View.VISIBLE);
                if (InternetCheck.getInstance(getApplicationContext())
                        .isOnline(getApplicationContext())) {
                       getDeviceRegistered();
                     // checkInfo();
                } else {
                    new DialogPopup().alertPopup(SplashScreen.this, "",
                            "Check your internet connection");
                }

            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }
        });
        AppEventsLogger.activateApp(getApplicationContext());
    }

    @Override
    protected void onResume() {
        buildAlertMessageNoGps();
        if (locationFetcherForeground == null) {
            locationFetcherForeground = new LocationFetcherForeground(
                    SplashScreen.this, 0, 1);
        }
        super.onResume();
    }

    @Override
    protected void onPause() {
        if (locationFetcherForeground != null) {
            locationFetcherForeground.destroy();
            locationFetcherForeground = null;
        }
        super.onPause();
    }

    void servercallLoginWithAccessTokenDriver() {

        RequestParams params = new RequestParams();

        params.put("access_token", Data.getAccessToken(getApplicationContext()));

        params.put("device_token", "" + Data.regId);//
        params.put("latitude", "" + Data.currentLatitude);//
        params.put("longitude", "" + Data.currentLongitude);//
        params.put("app_version", "" + Data.APP_VERSION);// ?
        params.put("device_type", "0");

        Log.e("access_token",
                "access_token = "
                        + Data.getAccessToken(getApplicationContext()));
        Log.e("device_token", "device_token = " + Data.regId);//
        Log.e("latitude", "latitude = " + Data.currentLatitude);//
        Log.e("longitude", "longitude = " + Data.currentLongitude);//
        Log.e("app_version", "app_version = " + Data.APP_VERSION);// ?

        AsyncHttpClient client = new AsyncHttpClient();

        client.setTimeout(Httppost_Links.SERVER_TIME_OUT_TIME);
        client.post(Httppost_Links.Baselink + "access_token_login", params,
                new AsyncHttpResponseHandler() {

                    @Override
                    public void onSuccess(String response) {
                        Log.i("request succesfull", "response = " + response);
                        JSONObject res;
                        try {
                            res = new JSONObject(response.toString());

                            if (res.getString("status").equals("2")) {
                                Intent intent = new Intent(SplashScreen.this,
                                        MainActivity.class);
                                startActivity(intent);
                                finish();
                            } else {
                                if (res.has("error")) {
                                    Log.d("Data.SERVER_ERROR_MSG",
                                            "Data.SERVER_ERROR_MSG"
                                                    + res.getString("error"));

                                } else {

                                    JSONObject user_data = res
                                            .getJSONObject("user_data");
                                    Data.saveInfoOf(getApplicationContext(),
                                            Data.loginFrom, "driver");

                                    Data.saveInfoOf(getApplicationContext(),
                                            Data.FLAG_REQUEST,
                                            res.getString("flag"));

                                    Data.saveInfoOf(getApplicationContext(),
                                            Data.USER_NAME,
                                            user_data.getString("user_name"));

                                    Data.saveInfoOf(getApplicationContext(),
                                            Data.ACCESS_TOKEN,
                                            user_data.getString("access_token"));
                                    Data.saveInfoOf(getApplicationContext(),
                                            Data.EMAIL,
                                            user_data.getString("email"));
                                    Data.saveInfoOf(getApplicationContext(),
                                            Data.PH_NO,
                                            user_data.getString("phone_no"));
                                    Data.saveInfoOf(
                                            getApplicationContext(),
                                            Data.CURRENT_USER_STATUS,
                                            user_data
                                                    .getString("current_user_status"));
                                    Data.saveInfoOf(getApplicationContext(),
                                            Data.CHECKINVALUE,
                                            user_data.getString("checked_in"));
                                    Data.saveInfoOf(getApplicationContext(),
                                            Data.USER_IMAGE_URL,
                                            user_data.getString("user_image"));
                                    // Driver Login

                                    if (res.getString("popup").equals("0")) {
                                        if (Data.getInfoOf(
                                                getApplicationContext(),
                                                Data.ORDERID).toString()
                                                .equals("")) {
                                            Intent intent = new Intent(
                                                    SplashScreen.this,
                                                    OrderScreen.class);
                                            startActivity(intent);
                                            overridePendingTransition(
                                                    R.anim.slide_in_right,
                                                    R.anim.slide_out_left);
                                            finish();
                                        } else {
                                            Intent intent = new Intent(
                                                    SplashScreen.this,
                                                    OrderDelivered.class);
                                            startActivity(intent);
                                            overridePendingTransition(
                                                    R.anim.slide_in_right,
                                                    R.anim.slide_out_left);
                                            finish();
                                        }
                                    } else {
                                        JSONObject popup = res
                                                .getJSONObject("popup");
                                        Log.d("-- loginFrom --",
                                                "-- loginFrom -- "
                                                        + Data.getInfoOf(
                                                        getApplicationContext(),
                                                        Data.loginFrom));
                                        String force = popup
                                                .getString("is_force");

                                        if (force.equals("0")) {
                                            Data.alertMessageBox(
                                                    SplashScreen.this,
                                                    popup.getString("title"),
                                                    popup.getString("text"),
                                                    Data.getInfoOf(
                                                            getApplicationContext(),
                                                            Data.loginFrom));
                                        } else {
                                            Data.alertMessageBoxForce(
                                                    SplashScreen.this,
                                                    popup.getString("title"),
                                                    popup.getString("text"));
                                        }
                                    }

                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Throwable arg0) {
                        Log.e("request fail", arg0.toString());
                        new DialogPopup().alertPopup(SplashScreen.this, "",
                                "Server not responding\nTry again.");
                    }
                });

    }

    void servercallLoginWithAccessTokenCustomer() {

        RequestParams params = new RequestParams();

        params.put("access_token", Data.getAccessToken(getApplicationContext())); // +
        // //
        // Data.ACCESS_TOKEN);//
        // //
        // ?
        params.put("device_token", "" + Data.regId);// Data.regId);//
        params.put("latitude", "" + Data.currentLatitude);//
        params.put("longitude", "" + Data.currentLongitude);//
        params.put("app_version", "" + Data.APP_VERSION);// ?
        params.put("device_type", "0");

        Log.e("access_token",
                "access_token = "
                        + Data.getAccessToken(getApplicationContext()));
        Log.e("device_token", "device_token = " + Data.regId);//
        Log.e("latitude", "latitude = " + Data.currentLatitude);//
        Log.e("longitude", "longitude = " + Data.currentLongitude);//
        Log.e("app_version", "app_version = " + Data.APP_VERSION);// ?

        AsyncHttpClient client = new AsyncHttpClient();

        client.setTimeout(Httppost_Links.SERVER_TIME_OUT_TIME);
        client.post(Httppost_Links.Baselink + "access_token_login", params,
                new AsyncHttpResponseHandler() {

                    @Override
                    public void onSuccess(String response) {
                        Log.i("request succesfull", "response = " + response);
                        JSONObject res;
                        try {
                            res = new JSONObject(response.toString());

                            if (res.getString("status").equals("2")) {
                                Intent intent = new Intent(SplashScreen.this,
                                        MainActivity.class);
                                startActivity(intent);
                                finish();
                            } else {
                                if (res.has("error")) {
                                    Log.d("Data.SERVER_ERROR_MSG",
                                            "Data.SERVER_ERROR_MSG"
                                                    + res.getString("error"));

                                } else {

                                    Data.oldResponse = "";
                                    Data.saveInfoOf(getApplicationContext(),
                                            Data.loginFrom, "customer");
                                    JSONObject user_data = res
                                            .getJSONObject("user_data");

                                    Data.saveInfoOf(getApplicationContext(),
                                            Data.USER_IMAGE_URL,
                                            user_data.getString("user_image"));
                                    Data.saveInfoOf(getApplicationContext(),
                                            Data.USER_NAME,
                                            user_data.getString("user_name"));

                                    Data.saveInfoOf(getApplicationContext(),
                                            Data.LAST_4,
                                            user_data.getString("last_4"));

                                    Data.saveInfoOf(getApplicationContext(),
                                            Data.ADDRESS,
                                            user_data.getString("address"));

                                    Data.saveInfoOf(getApplicationContext(),
                                            Data.ACCESS_TOKEN,
                                            user_data.getString("access_token"));
                                    Data.saveInfoOf(getApplicationContext(),
                                            Data.EMAIL,
                                            user_data.getString("email"));
                                    Data.saveInfoOf(getApplicationContext(),
                                            Data.PH_NO,
                                            user_data.getString("phone_no"));
                                    Data.saveInfoOf(getApplicationContext(),
                                            Data.USER_IMAGE_URL,
                                            user_data.getString("user_image"));
                                    Data.saveInfoOf(getApplicationContext(),
                                            Data.CHECK_CARD,
                                            user_data.getString("card_check"));
                                    Data.saveInfoOf(getApplicationContext(),
                                            Data.REFERRAL_CODE, user_data
                                                    .getString("referral_code"));
                                    Log.d("DATAAA",
                                            "DATAAA  \n "
                                                    + Data.getInfoOf(
                                                    getApplicationContext(),
                                                    "last_4") + ",\n "
                                                    + Data.ADDRESS + ",\n "
                                                    + Data.PH_NO);

                                    if (res.getString("popup").equals("0")) {
                                        if (Data.getInfoOf(
                                                getApplicationContext(), "GCM")
                                                .equals("")) {
                                            Intent intent = new Intent(
                                                    SplashScreen.this,
                                                    ListofMeals.class);
                                            startActivity(intent);
                                            overridePendingTransition(
                                                    R.anim.slide_in_right,
                                                    R.anim.slide_out_left);
                                            finish();
                                        } else if (Data.getInfoOf(
                                                getApplicationContext(), "GCM")
                                                .equals("41")) {
                                            Intent intent = new Intent(
                                                    SplashScreen.this,
                                                    DriverLocationFinder.class);
                                            startActivity(intent);
                                            overridePendingTransition(
                                                    R.anim.slide_in_right,
                                                    R.anim.slide_out_left);
                                            finish();
                                        } else if (Data.getInfoOf(
                                                getApplicationContext(), "GCM")
                                                .equals("40")) {
                                            Intent intent = new Intent(
                                                    SplashScreen.this,
                                                    Receipt.class);
                                            startActivity(intent);
                                            overridePendingTransition(
                                                    R.anim.slide_in_right,
                                                    R.anim.slide_out_left);
                                            finish();
                                        }
                                    } else {
                                        JSONObject popup = res
                                                .getJSONObject("popup");

                                        String force = popup
                                                .getString("is_force");
                                        Log.d("-- loginFrom --",
                                                "-- loginFrom -- "
                                                        + Data.getInfoOf(
                                                        getApplicationContext(),
                                                        Data.loginFrom));
                                        if (force.equals("0")) {
                                            Data.alertMessageBox(
                                                    SplashScreen.this,
                                                    popup.getString("title"),
                                                    popup.getString("text"),
                                                    user_data
                                                            .getString("current_user_status"));
                                        } else {
                                            Data.alertMessageBoxForce(
                                                    SplashScreen.this,
                                                    popup.getString("title"),
                                                    popup.getString("text"));
                                        }

                                    }
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Throwable arg0) {
                        Log.e("request fail", arg0.toString());
                        new DialogPopup().alertPopup(SplashScreen.this, "",
                                "Server not responding\nTry again.");
                    }
                });

    }

    /**
     * This method is used to register the device on GCM
     */
    void getDeviceRegistered() {
        // Make sure the device has the proper dependencies.
        try {
            GCMRegistrar.checkDevice(SplashScreen.this);
            // Make sure the manifest permissions was properly set
            GCMRegistrar.checkManifest(SplashScreen.this);
            // Get GCM registration id
            String regId = "abcd";
            Log.i("reg id", "==" + regId);
            if ("".equals(regId)) {
                // Register with GCM
                GCMRegistrar.register(this, Data.googleSenderId);
                Log.d("***** if", "***** if");
                mHandler = new Handler();
                mHandler.postDelayed(new Runnable() {
                    public void run() {
                        if (!Data.regId.equals("")) {
                            Log.d("mHandler if", "mHandler if");
                            checkInfo();
                        } else {
                            if (Data.regId.equals("")) {
                                Log.d("mHandler else -> if",
                                        "mHandler else -> if");
                            }
                            Log.d("mHandler else", "mHandler else");
                            mHandler.postDelayed(this, 3000);
                        }
                    }
                }, 3000);

            } else {
                Data.regId = regId;
                checkInfo();
                Log.i("Already registered with GCM Server",
                        "Already registered with GCM Server");
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    void buildAlertMessageNoGps() {

        if (!((LocationManager) getSystemService(Context.LOCATION_SERVICE))
                .isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            if (gpsDialogAlert != null && gpsDialogAlert.isShowing()) {
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage(
                        "The app needs active GPS connection. Enable it from Settings.")
                        .setCancelable(false)
                        .setPositiveButton("Go to Settings",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(
                                            final DialogInterface dialog,
                                            final int id) {
                                        startActivity(new Intent(
                                                android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                                        finish();
                                    }
                                })

                ;
                gpsDialogAlert = null;
                gpsDialogAlert = builder.create();
                gpsDialogAlert.show();
            }
        } else {
            imageViewLogo.startAnimation(fadeOut);
            if (gpsDialogAlert != null && gpsDialogAlert.isShowing()) {
                gpsDialogAlert.dismiss();
            }
        }
    }

    /**
     * @return current Date from Calendar in dd/MM/yyyy format adding 1 into
     * month because Calendar month starts from zero
     */
    public static String getDate(Calendar cal) {
        return "" + cal.get(Calendar.DATE) + "/"
                + (cal.get(Calendar.MONTH) + 1) + "/" + cal.get(Calendar.YEAR);
    }

    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        EasyTracker.getInstance(SplashScreen.this).activityStart(this);
        FlurryAgent.onStartSession(SplashScreen.this, Data.flurryKey);
    }

    @Override
    protected void onStop() {
        // TODO Auto-generated method stub
        super.onStop();
        EasyTracker.getInstance(SplashScreen.this).activityStop(this);
        FlurryAgent.onEndSession(this);
        FlurryAgent.endTimedEvent("SplashScreen");
    }

    void checkInfo()

    {
        if (locationFetcherForeground != null) {
            Data.currentLatitude = locationFetcherForeground.getLatitude();
            Data.currentLongitude = locationFetcherForeground.getLongitude();
            Log.i("Current Location", "currentLatitude::"
                    + Data.currentLatitude + "  currentLongitude"
                    + Data.currentLongitude);
        }
        mHandler = new Handler();
        mHandler.postDelayed(new Runnable() {
            public void run() {
                if (Data.compareDouble(Data.currentLatitude, 0.0) != 0
                        && Data.compareDouble(Data.currentLongitude, 0.0) != 0) {
                    Log.d("currentLongitude", "currentLongitude  "
                            + Data.currentLatitude + ", "
                            + Data.currentLongitude);
                    Log.e("Access Token==",
                            Data.getAccessToken(getApplicationContext())
                                    + Data.getInfoOf(getApplicationContext(),
                                    Data.loginFrom).equalsIgnoreCase(
                                    "driver"));

                    if (Data.getAccessToken(getApplicationContext()).isEmpty()) {
                        Intent intent = new Intent(SplashScreen.this,
                                MainActivity.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.slide_in_right,
                                R.anim.slide_out_left);
                        finish();
                    } else {

                        if (Data.getInfoOf(getApplicationContext(),
                                Data.loginFrom).toString().equals("driver")) {

                            if (InternetCheck.getInstance(
                                    getApplicationContext()).isOnline(
                                    getApplicationContext())) {
                                servercallLoginWithAccessTokenDriver();
                            } else {
                                new DialogPopup().alertPopup(SplashScreen.this,
                                        "", "Check your internet connection");
                            }

                        } else if (InternetCheck.getInstance(
                                getApplicationContext()).isOnline(
                                getApplicationContext())) {
                            servercallLoginWithAccessTokenCustomer();
                        } else {
                            new DialogPopup().alertPopup(SplashScreen.this, "",
                                    "Check your internet connection");
                        }

                    }
                } else {
                    if (locationFetcherForeground != null) {
                        Data.currentLatitude = locationFetcherForeground
                                .getLatitude();
                        Data.currentLongitude = locationFetcherForeground
                                .getLongitude();
                        Log.i("Current Location", "currentLatitude::"
                                + Data.currentLatitude + "  currentLongitude"
                                + Data.currentLongitude);
                    }
                    mHandler.postDelayed(this, 3000);
                }
            }
        }, 3000);
    }

    void convertZip() {
        getAddressGivenLatLongFrom(Data.currentLatitude, Data.currentLongitude,
                SplashScreen.this);

    }

    public List<Address> getAddressGivenLatLongFrom(double latitude,
                                                    double longitude, final Activity activity) {
        Log.d("getAddressGivenLatLongFrom", "getAddressGivenLatLongFrom");
        RequestParams params = new RequestParams();
        String uri = "http://maps.google.com/maps/api/geocode/json?latlng="
                + latitude + "," + longitude + "&sensor=false?key="
                + Httppost_Links.BrowserKey;
        uri = uri.replaceAll(" ", "%20");
        Log.d("uri", "uri" + uri);
        AsyncHttpClient client = new AsyncHttpClient();
        client.setTimeout(Httppost_Links.SERVER_TIME_OUT_TIME);
        client.post(uri, params, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(String response) {
                try {
                    JSONObject job = new JSONObject(response);
                    if (response.contains("error")) {
                        String error = job.getString("error");
                        Log.i("Error ", "-----------" + error);
                        new DialogPopup().alertPopup(activity, "", error);
                    } else {
                        String indiStr = "";
                        List<String> ziparray = new ArrayList<String>();

                        JSONObject jsonObject = new JSONObject(response);
                        retList = new ArrayList<Address>();
                        JSONArray results = jsonObject.getJSONArray("results");
                        for (int i = 0; i < results.length(); i++) {
                            JSONObject result = results.getJSONObject(i);
                            indiStr = result.getString("formatted_address");

                            // Log.d("111","111");
                            JSONArray array = result
                                    .getJSONArray("address_components");
                            for (int j = 0; j < array.length(); j++) {
                                // Log.d("222","222");
                                JSONObject js = array.getJSONObject(j);
                                // Log.d("js","js"+js.toString());
                                JSONArray jsoo = js.getJSONArray("types");
                                // String comp= jsoo.getString(0);
                                // Log.d(" jsoo.getString(0)"," jsoo.getString(0)"+
                                // jsoo.getString(0));
                                if (jsoo.getString(0).equals("postal_code")) {
                                    // Log.d("333","333");
                                    // Log.d(" jsoo.getString(0)"," jsoo.getString(0)"+
                                    // jsoo.getString(0)+" = "+js.getString("long_name"));

                                    ziparray.add(js.getString("long_name"));
                                    break;
                                }

                            }

                            Address addr = new Address(Locale.getDefault());
                            addr.setAddressLine(0, indiStr);
                            retList.add(addr);
                        }
                        List<Address> addresses;
                        addresses = retList;
                        String address = addresses.get(0).getAddressLine(0);
                        String city = addresses.get(0).getAddressLine(1);
                        String country = addresses.get(0).getAddressLine(2);

                    }
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }

            @Override
            public void onFailure(Throwable arg0) {
//				new DialogPopup().alertPopup(activity, "",
//						"Google Server not responding\nTry again.");
            }

        });
        return retList;
    }
}
