package com.bitekite;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.bitekite.adpters.ListofMealAdapter;
import com.bitekite.classes.MealToppings;
import com.bitekite.classes.MealsData;
import com.bitekite.classes.SelectedItems;
import com.bitekite.driver.Httppost_Links;
import com.bitekite.driver.PushUpdater;
import com.bitekite.driver.Utils;
import com.bitekite.utils.Data;
import com.bitekite.utils.DialogPopup;
import com.bitekite.utils.InternetCheck;
import com.google.analytics.tracking.android.EasyTracker;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import rmn.androidscreenlibrary.ASSL;

public class ListofMeals extends Activity implements PushUpdater {

    ListofMealAdapter adapter;

    PullToRefreshListView pullmealsListView;
    public static PushUpdater pushUpdater;
    static ArrayList<MealsData> dataMealArrayList;
    ArrayList<MealToppings> mealToppingsArrayList;
    RelativeLayout relativeLayoutListofmeal;
    ListView mealsListView;
    public static LinearLayout LinearLayout1;
    public static TextView textViewTotal;
    Button buttonMenu;
    TextView textViewBannerContent, textViewTimings, textViewOrderMinutes,
            textViewTodayMenu, textViewCustomize;
    ImageView imageViewBanner, imageloader;
    LinearLayout linearLayout;
    ScrollView scrollView1;
    RelativeLayout relativeLayoutMainView, relativeLayoutTopBar,
            relativeLayoutHidden;
    public static RelativeLayout relativeLayoutOrderPlaced;
    public static TextView textViewOrderPlaced;

    int onResumeFlag = 0;

    LinearLayout linearLayoutMenu;
    TextView textViewDaily, textViewPayment, textViewAccount, textViewSupport,
            textViewAboutUs, textViewPromotion, textViewShare, textViewLogout;
    // public static int mapflag=0;
    public static int Flag = 0;
    public static int flag = 1; // List of meal pause
    int pull = 0;
    ArrayList<String> list = new ArrayList<String>();

    public static RelativeLayout relativeLayoutCheckOut,
            relativeLayoutCustomize;

    EasyTracker mTracker;
    LayoutInflater inflater;
    View header;
    Intent i;
    TextView textViewCheckout;
    private AQuery aq;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listof_meals);

        relativeLayoutListofmeal = (RelativeLayout) findViewById(R.id.relativeLayoutListofmeal);
        new ASSL(this, relativeLayoutListofmeal, 1134, 720, false);

        Data.activity = ListofMeals.this;

        EasyTracker.getInstance(this);
        imageloader = (ImageView) findViewById(R.id.imageView2);
        textViewCustomize = (TextView) findViewById(R.id.textViewCustomize);
        textViewDaily = (TextView) findViewById(R.id.DailyMenuTxt);
        textViewPayment = (TextView) findViewById(R.id.PaymentTxt);
        textViewAccount = (TextView) findViewById(R.id.AccountTxt);
        textViewSupport = (TextView) findViewById(R.id.SupportTxt);
        textViewAboutUs = (TextView) findViewById(R.id.AboutTxt);
        textViewPromotion = (TextView) findViewById(R.id.PromotionTxt);
        textViewShare = (TextView) findViewById(R.id.ShareTxt);
        textViewCheckout = (TextView) findViewById(R.id.textViewCheckout);

        textViewLogout = (TextView) findViewById(R.id.LogoutTxt);
        textViewOrderPlaced = (TextView) findViewById(R.id.textViewOrderPlaced);
        // textViewOrderMinutes = (TextView)
        // findViewById(R.id.textViewOrderMinutes);

        textViewCustomize
                .setTypeface(Data.bariol_bold(getApplicationContext()));
        textViewDaily.setTypeface(Data.bariol_bold(getApplicationContext()));
        textViewPayment.setTypeface(Data.bariol_bold(getApplicationContext()));
        textViewAccount.setTypeface(Data.bariol_bold(getApplicationContext()));
        textViewSupport.setTypeface(Data.bariol_bold(getApplicationContext()));
        textViewAboutUs.setTypeface(Data.bariol_bold(getApplicationContext()));
        textViewPromotion
                .setTypeface(Data.bariol_bold(getApplicationContext()));
        textViewShare.setTypeface(Data.bariol_bold(getApplicationContext()));

        textViewLogout.setTypeface(Data.bariol_bold(getApplicationContext()));

        textViewCheckout.setTypeface(Data.bariol_bold(getApplicationContext()));

        textViewOrderPlaced.setTypeface(Data
                .bariol_regular(getApplicationContext()));
        // textViewOrderMinutes.setTypeface(Data
        // .bariol_bold(getApplicationContext()));

        relativeLayoutHidden = (RelativeLayout) findViewById(R.id.hidden);
        LinearLayout1 = (LinearLayout) findViewById(R.id.LinearLayout1);
        textViewTotal = (TextView) findViewById(R.id.textViewTotal);
        buttonMenu = (Button) findViewById(R.id.buttonMenu);

        relativeLayoutCustomize = (RelativeLayout) findViewById(R.id.relativeLayoutCustomize);
        relativeLayoutTopBar = (RelativeLayout) findViewById(R.id.topBarHeader);
        linearLayoutMenu = (LinearLayout) findViewById(R.id.linearLayout);
        relativeLayoutMainView = (RelativeLayout) findViewById(R.id.mainView);
        scrollView1 = (ScrollView) findViewById(R.id.scrollView1);
        relativeLayoutCheckOut = (RelativeLayout) findViewById(R.id.relativeLayoutCheckOut);
        relativeLayoutOrderPlaced = (RelativeLayout) findViewById(R.id.relativeLayoutOrderPlaced);

        textViewTotal.setTypeface(Data.bariol_bold(getApplicationContext()));
        // relativeLayoutMenuView = (RelativeLayout)
        // findViewById(R.id.rootMenuLayout);
        pullmealsListView = (PullToRefreshListView) findViewById(R.id.listView1);

        // mealsListView = (ListView) findViewById(R.id.listView1);

        mealsListView = pullmealsListView.getRefreshableView();

        mealsListView.setDivider(null);
        mealsListView.setDividerHeight(0);
        mealsListView.setSmoothScrollbarEnabled(true);

        inflater = getLayoutInflater();
        header = inflater.inflate(R.layout.list_header, mealsListView, false);
        textViewBannerContent = (TextView) header
                .findViewById(R.id.textViewBannerContent);
        textViewTimings = (TextView) header.findViewById(R.id.textViewTimings);
        textViewTodayMenu = (TextView) header
                .findViewById(R.id.textViewTodayMenu);
        imageViewBanner = (ImageView) header.findViewById(R.id.imageViewBanner);
        linearLayout = (LinearLayout) header
                .findViewById(R.id.LinearLayoutMain);
        linearLayout.setLayoutParams(new ListView.LayoutParams(
                ListView.LayoutParams.MATCH_PARENT,
                ListView.LayoutParams.WRAP_CONTENT));
        ASSL.DoMagic(linearLayout);
        mealsListView.addHeaderView(header, null, false);

        aq = new AQuery(getApplicationContext());

        if (InternetCheck.getInstance(getApplicationContext()).isOnline(
                getApplicationContext())) {
            serverCallListofMeals();
        } else {
            new DialogPopup().alertPopup(ListofMeals.this, "",
                    "Check your internet connection");
        }

        pullmealsListView
                .setOnRefreshListener(new OnRefreshListener<ListView>() {

                    @Override
                    public void onRefresh(
                            PullToRefreshBase<ListView> refreshView) {
                        // TODO Auto-generated method stub
                        stopService(i);

                        pull = 1;
                        serverCallListofMeals();
                        startService(i);
                    }
                });

        buttonMenu.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                openAnim();

            }
        });

        relativeLayoutCheckOut.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (Data.getInfoOf(getApplicationContext(),
                        Data.OrderPlacedFlag).equals("1")) {
                    new DialogPopup().alertPopup(ListofMeals.this, "",
                            "Please wait till we deliver your current order");
                } else {
                    Flag = 1;
                    flag = 0;
                    setlist();
                    Intent intent = new Intent(ListofMeals.this,
                            CheckOutCustomer.class);
                    startActivity(intent);
                    // finish();
                    overridePendingTransition(R.anim.slide_in_right,
                            R.anim.slide_out_left);
                }
            }
        });

        relativeLayoutCustomize.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (Data.getInfoOf(getApplicationContext(),
                        Data.OrderPlacedFlag).equals("1")) {
                    new DialogPopup().alertPopup(ListofMeals.this, "",
                            "Please wait till we deliver your current order");
                } else {
                    Flag = 0;
                    flag = 0;
                    setlist();
                    Intent intent = new Intent(ListofMeals.this, Cust.class);
                    startActivity(intent);
                    // finish();
                    overridePendingTransition(R.anim.slide_in_right,
                            R.anim.slide_out_left);
                }
            }
        });
        relativeLayoutOrderPlaced.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (Data.getInfoOf(getApplicationContext(), "GCM").toString()
                        .equals("41")) {
                    Intent intent = new Intent(ListofMeals.this,
                            DriverLocationFinder.class);
                    startActivity(intent);
                    finish();
                    overridePendingTransition(R.anim.slide_in_right,
                            R.anim.slide_out_left);
                }

            }
        });
        linearLayoutMenu.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                closeAnim();
            }
        });

        textViewDaily.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                closeAnim();

            }
        });

        textViewPayment.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                flag = 0;

                if (Integer.parseInt(Data.getInfoOf(getApplicationContext(),
                        Data.CHECK_CARD)) == 0) {
                    Intent intent = new Intent(ListofMeals.this,
                            PaymentUserEdit.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_right,
                            R.anim.hold);
                } else {
                    Intent intent = new Intent(ListofMeals.this,
                            AddPaymentUser.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_right,
                            R.anim.hold);
                }

            }
        });

        textViewAccount.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                flag = 0;
                Intent intent = new Intent(ListofMeals.this, AccountUser.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.hold);

            }
        });

        textViewSupport.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                flag = 0;
                Intent emailIntent = new Intent(Intent.ACTION_SEND);
                emailIntent.putExtra(Intent.EXTRA_EMAIL,
                        new String[]{"support@quickmeals.com"});
                emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,
                        "Quick" + Html.fromHtml("&#8226;") + "Meals Support");
                emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, "");
                emailIntent.setType("plain/text");
                try {
                    startActivity(Intent.createChooser(emailIntent,
                            "Send mail..."));

                    Log.i("Finished sending email...", "");
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(getApplicationContext(),
                            "There is no email client installed.",
                            Toast.LENGTH_SHORT).show();
                }

            }
        });

        textViewAboutUs.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                flag = 0;
                Intent intent = new Intent(ListofMeals.this, AboutUs.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.hold);

            }
        });

        textViewPromotion.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                flag = 0;
                Intent intent = new Intent(ListofMeals.this, Promotion.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.hold);

            }
        });

        textViewShare.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                flag = 0;
                Intent intent = new Intent(ListofMeals.this, ShareScreen.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.hold);

            }
        });

        textViewLogout.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (InternetCheck.getInstance(getApplicationContext())
                        .isOnline(getApplicationContext())) {
                    logout();
                } else {
                    new DialogPopup().alertPopup(ListofMeals.this, "",
                            "Check your internet connection");
                }

            }
        });

    }

    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        EasyTracker.getInstance(ListofMeals.this).activityStart(this);
        // super.onStart();
    }

    @Override
    protected void onStop() {
        // TODO Auto-generated method stub
        super.onStop();
        EasyTracker.getInstance(ListofMeals.this).activityStop(this);
        // super.onStart();
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub

        super.onDestroy();

    }

    void serverCallListofMeals() {

        if (pull == 1
                || Data.getInfoOf(getApplicationContext(), Data.OrderPlacedFlag)
                .equals("1") || onResumeFlag == 1) {
            pull = 0;
            onResumeFlag = 0;

        } else {
            Data.showLoadingDialog(ListofMeals.this, "Loading...");

        }

        RequestParams params = new RequestParams();
        DateFormat df = DateFormat.getTimeInstance();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        format.setTimeZone(TimeZone.getTimeZone("utc"));
        String utcTime = format.format(new Date());
        Data.startTime = utcTime;
        Log.d("--------gmtTime------", "-------gmtTime--------" + utcTime);

        Date date = new Date();
        date.setHours(date.getHours() + 24);
        System.out.println(date);
        SimpleDateFormat simpDate;
        simpDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        simpDate.setTimeZone(TimeZone.getTimeZone("utc"));
        System.out.println(simpDate.format(date));
        Data.endTime = simpDate.format(date);

        params.put("access_token",
                "" + Data.getAccessToken(getApplicationContext()));
        params.put("from_time", Data.startTime);
        params.put("to_time", Data.endTime);

        AsyncHttpClient client = new AsyncHttpClient();
        client.setTimeout(Httppost_Links.SERVER_TIME_OUT_TIME);
        client.post(Httppost_Links.Baselink + "get_meals", params,
                new AsyncHttpResponseHandler() {

                    @Override
                    public void onSuccess(String response) {
                        Log.i("request succesfull", "response = " + response);
                        // fillServerData(response);

                        Data.newResponse = response;
                        if (Data.newResponse.equalsIgnoreCase(Data.oldResponse)) {
                            Data.loading_box_stop2();
                            Log.i("ONRESUME", "ONRESUME = " + response);
                        } else {
                            Data.totalValue = 0;
                            Data.oldResponse = Data.newResponse;
                            JSONObject res;
                            try {
                                res = new JSONObject(response);

                                if (res.getString("status").equals("2")) {
                                    Data.loading_box_stop2();
                                    Intent intent = new Intent(
                                            ListofMeals.this,
                                            MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Log.i("Response",
                                            "Response" + res.toString(2));
                                    JSONArray data = res.getJSONArray("data");
                                    dataMealArrayList = new ArrayList<MealsData>();
                                    for (int i = 0; i < data.length(); i++) {
                                        JSONObject count = data
                                                .getJSONObject(i);
                                        mealToppingsArrayList = new ArrayList<MealToppings>();
                                        JSONArray topping = count
                                                .getJSONArray("toppings");

                                        for (int j = 0; j < topping.length(); j++) {
                                            JSONObject ucount = topping
                                                    .getJSONObject(j);

                                            mealToppingsArrayList.add(new MealToppings(
                                                    ucount.getString("price"),
                                                    ucount.getString("topping_id"),
                                                    ucount.getString("topping_description"),
                                                    ucount.getString("stock_available"),
                                                    ucount.getString("topping_image"),
                                                    ucount.getString("name")));
                                        }

                                        dataMealArrayList.add(new MealsData(
                                                count.getString("meal_description"),
                                                count.getString("meal_id"),
                                                count.getString("price"),
                                                count.getString("stock_available"),
                                                count.getString("meal_image"),
                                                count.getString("name"),
                                                count.getString("meal_details"),
                                                mealToppingsArrayList,
                                                mealToppingsArrayList.size(),
                                                count.getString("chef_name"),
                                                count.getString("chef_description"),
                                                count.getString("chef_image"),
                                                count.getString("chef_post"),
                                                count.getString("chef_full_details"),
                                                count.getString("chef_full_image")));

                                        Log.d("toppings size", "toppings size"
                                                + mealToppingsArrayList.size());

                                    }

                                    Data.deliveryCharge = res
                                            .getString("delivery_charge");
                                    Data.servicetax = res
                                            .getString("service_tax");
                                    Data.creditBalance = res
                                            .getString("credit_balance");

                                    Data.OPENSTATUS = res
                                            .getString("open_status");

                                    String start_time = res
                                            .getString("start_time");
                                    String end_time = res.getString("end_time");
                                    String banner_image = res
                                            .getString("banner_image");
                                    String banner_content = res
                                            .getString("banner_content");

                                    Log.d("others", "others " + start_time
                                            + ", " + end_time + ", "
                                            + banner_content + ", "
                                            + banner_image + Data.creditBalance);

                                    Log.d("datameal size", "datameal size"
                                            + dataMealArrayList.size());

                                    adapter = new ListofMealAdapter(
                                            ListofMeals.this, dataMealArrayList);

                                    if (pull == 1
                                            || Data.getInfoOf(
                                            getApplicationContext(),
                                            Data.OrderPlacedFlag)
                                            .equals("1")) {

                                        pull = 0;
                                    } else {
                                    }
                                    mealsListView.setAdapter(adapter);
                                    textViewBannerContent.setText(""
                                            + banner_content.replace("\\n",
                                            "\n"));

                                    textViewBannerContent.setTypeface(Data
                                            .bariol_regular(getApplicationContext()));
                                    textViewTimings.setTypeface(Data
                                            .bariol_regular(getApplicationContext()));
                                    textViewTodayMenu.setTypeface(Data
                                            .bariol_bold(getApplicationContext()));

                                    String startTime = utcToLocal(start_time)
                                            .toUpperCase();
                                    String endTime = utcToLocal(end_time)
                                            .toUpperCase();
                                    String menu_content = res
                                            .getString("menu_content");
                                    textViewTodayMenu.setText(""
                                            + menu_content.replace("\\n", "\n"));
                                    textViewTimings.setText("" + startTime
                                            + " - " + endTime);

//									Picasso.with(ListofMeals.this)
//											.load(banner_image)
//											.placeholder(
//													R.drawable.placeholder_item)
//											.resize((int) (720 * ASSL.Xscale()),
//													(int) (720 * ASSL.Yscale()))
//											.centerCrop().into(imageViewBanner);

                                    aq.id(imageViewBanner).image(banner_image, true, true, 720, 0, null, 0, (720 * ASSL.Xscale()) / (720 * ASSL.Yscale()));

                                    Data.loading_box_stop2();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        relativeLayoutHidden.setVisibility(View.INVISIBLE);
                        pullmealsListView.onRefreshComplete();
                        if (Data.totalValue == 0) {
                            ListofMeals.LinearLayout1.setVisibility(View.GONE);
                        } else {
                            ListofMeals.LinearLayout1
                                    .setVisibility(View.VISIBLE);
                        }

                    }

                    @Override
                    public void onFailure(Throwable arg0) {
                        pullmealsListView.onRefreshComplete();
                        Log.e("request fail", arg0.toString());
                        Data.loading_box_stop2();
                        new DialogPopup().alertPopup(ListofMeals.this, "",
                                "Server not responding\nTry again.");
                    }
                });
    }

    @Override
    public void onBackPressed() {
        if (linearLayoutMenu.isShown()) {
            closeAnim();
        } else {
            super.onBackPressed();
            Data.totalValue = 0;
            overridePendingTransition(R.anim.hold, R.anim.slide_out_right);
            finish();
        }

    }

    void openAnim() {
        if (Data.startAnim) {
            Data.startAnim = false;
            relativeLayoutTopBar.clearAnimation();
            relativeLayoutMainView.clearAnimation();
            linearLayoutMenu.setVisibility(View.VISIBLE);
            relativeLayoutTopBar.startAnimation(Data
                    .getSlideOutUp(getApplicationContext()));
            relativeLayoutMainView.startAnimation(Data
                    .getslideOutBottom(getApplicationContext()));
            relativeLayoutTopBar.setVisibility(View.INVISIBLE);
            relativeLayoutMainView.setVisibility(View.INVISIBLE);
        }
    }

    void closeAnim() {
        if (Data.startAnim) {
            relativeLayoutTopBar.clearAnimation();
            relativeLayoutMainView.clearAnimation();
            relativeLayoutTopBar.startAnimation(Data
                    .getslideOutUpBack(getApplicationContext()));
            relativeLayoutMainView.startAnimation(Data
                    .getslideOutBottomBack(getApplicationContext()));
            relativeLayoutTopBar.setVisibility(View.VISIBLE);
            relativeLayoutMainView.setVisibility(View.VISIBLE);
            Handler mHandler = new Handler();
            mHandler.postDelayed(new Runnable() {
                public void run() {
                    linearLayoutMenu.setVisibility(View.INVISIBLE);
                }
            }, 700);
        }
    }

    void logout() {
        Data.loading_box(ListofMeals.this, "Loading...");
        RequestParams params = new RequestParams();

        params.put("access_token",
                "" + Data.getAccessToken(getApplicationContext()));// +Data.ACCESS_TOKEN);
        AsyncHttpClient client = new AsyncHttpClient();

        client.setTimeout(Httppost_Links.SERVER_TIME_OUT_TIME);
        client.post(Httppost_Links.Baselink + "logout", params,
                new AsyncHttpResponseHandler() {

                    @Override
                    public void onSuccess(String response) {
                        Data.loading_box_stop();
                        JSONObject res;
                        try {
                            Data.oldResponse = "";
                            res = new JSONObject(response);
                            if (res.getString("status").equals("2")) {
                                Intent intent = new Intent(ListofMeals.this,
                                        MainActivity.class);
                                startActivity(intent);
                                finish();
                            } else {
                                Log.i("Response", "Response" + res.toString(2));

                                Data.saveInfoOf(getApplicationContext(),
                                        Data.ACCESS_TOKEN, "");
                                Data.saveAccessToken("",
                                        getApplicationContext());
                                Data.saveInfoOf(getApplicationContext(),
                                        Data.loginFrom, "");
                                Intent intent = new Intent(ListofMeals.this,
                                        MainActivity.class);
                                startActivity(intent);
                                overridePendingTransition(R.anim.slide_in_left,
                                        R.anim.slide_out_right);
                                finish();
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
                    }
                });
    }

    void setlist() {
        Data.MealPrize = 0;
        Data.selectedItems.clear();
        for (int i = 0; i < ListofMealAdapter.selectedMealArrayList.size(); i++) {

            Log.d("--selsected meal--", "-- selsected meal id -- "
                    + ListofMealAdapter.selectedMealArrayList.get(i).meal_id);
            Log.d("--selsected meal--", "-- selsected quantity -- "
                    + ListofMealAdapter.selectedMealArrayList.get(i).quantity);
            Log.d("Qtylist", "qtylist "
                    + ListofMealAdapter.qtyList.get(i).toString());
            int k = ListofMealAdapter.selectedMealArrayList.get(i).quantity;
            for (int j = 0; j < k; j++) {
                list.add(ListofMealAdapter.selectedMealArrayList.get(i).meal_id);

                SelectedItems selectedItemsObject = new SelectedItems();
                selectedItemsObject
                        .setMealId(ListofMealAdapter.selectedMealArrayList
                                .get(i).meal_id);

                Data.MealPrize = Data.MealPrize
                        + Double.parseDouble(ListofMealAdapter.selectedMealArrayList
                        .get(i).price);

                for (int p = 0; p < ListofMeals.dataMealArrayList.size(); p++) {
                    if (ListofMeals.dataMealArrayList.get(p).meal_id.toString()
                            .equals(ListofMealAdapter.selectedMealArrayList
                                    .get(i).meal_id.toString())) {

                        selectedItemsObject
                                .setMealName(ListofMeals.dataMealArrayList
                                        .get(p).name.toString());
                        selectedItemsObject.setMealIndex("" + p);
                        selectedItemsObject
                                .setMealPrice(ListofMeals.dataMealArrayList
                                        .get(p).price.toString());
                        break;
                    }
                }

                Data.selectedItems.add(selectedItemsObject);

            }

        }
        Log.d("prizeeeeeee", "prizeeeeeee" + Data.MealPrize);
    }

    @Override
    protected void onResume() {
        pushUpdater = this;
        flag = 1;
        String gcm = Data.getInfoOf(getApplicationContext(), "GCM").toString();
        if (gcm.equals("41")) {
            if (DriverLocationFinder.eshant == 0) {
                DriverLocationFinder.eshant = 1;
                Intent intent = new Intent(ListofMeals.this,
                        DriverLocationFinder.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.slide_in_right,
                        R.anim.slide_out_left);
            }
        } else if (gcm.equals("40")) {
            Intent intent = new Intent(ListofMeals.this, Receipt.class);
            startActivity(intent);
            finish();
            overridePendingTransition(R.anim.slide_in_right, R.anim.hold);
        }
        if (Data.getInfoOf(getApplicationContext(), Data.OrderPlacedFlag)
                .equals("1")) {
            if (gcm.equals("41")) {
                if (Data.getInfoOf(getApplicationContext(),
                        Data.customer_estimate_time).equals("0")
                        || Data.getInfoOf(getApplicationContext(),
                        Data.customer_estimate_time).equals("")) {
                    textViewOrderPlaced
                            .setText("Your server will arrive in few minutes, \nclick here to check your server's location");
                } else if (Integer.parseInt(Data.getInfoOf(
                        getApplicationContext(), Data.customer_estimate_time)) <= 60) {
                    textViewOrderPlaced
                            .setText("Your server will arrive in less than a min, \nclick here to check your server's location");
                } else {
                    Log.e("estimate time",""+Data.customer_estimate_time);
                    int est = (int) Math.ceil(Integer.parseInt(Data.getInfoOf(
                            getApplicationContext(),
                            Data.customer_estimate_time))/60.0);
                    textViewOrderPlaced
                            .setText("Your server will arrive in "
                                    + est
                                    + " minutes, \nclick here to check your server's location");
                }
            } else {
                Log.e("estimate time",""+Data.customer_estimate_time);
                int est = (int) Math
                        .ceil(Integer.parseInt(Data.customer_estimate_time)/60.0);
                textViewOrderPlaced
                        .setText(" Your server is "
                                + est
                                + " minutes away, you should be able to track your server's location in few minutes");
            }
            serverCallListofMeals();
            relativeLayoutOrderPlaced.setVisibility(View.VISIBLE);
            ListofMeals.textViewOrderPlaced.setTextColor(Color.BLACK);
            ListofMeals.relativeLayoutOrderPlaced
                    .setBackgroundResource(R.color.white);
            LinearLayout1.setVisibility(View.GONE);
            textViewLogout.setVisibility(View.INVISIBLE);
        } else {
            Log.d("12345", "12345");
            onResumeFlag = 1;
            serverCallListofMeals();
            relativeLayoutCustomize.setEnabled(true);
            relativeLayoutCheckOut.setEnabled(true);
            relativeLayoutOrderPlaced.setVisibility(View.INVISIBLE);
            textViewLogout.setVisibility(View.VISIBLE);
        }
        i = new Intent(ListofMeals.this, MyService.class);
        startService(i);

        super.onResume();
    }

    protected void onPause() {

        Log.d("onPause",
                "onPause" + "==="
                        + Data.getInfoOf(getApplicationContext(), "GCM"));
        ListofMeals.pushUpdater = null;

        if (flag != 0) {
            //
            // flag=0;
            // finish();
        }
        stopService(i);
        super.onPause();

    }

    @Override
    public void pushUpdater() {
        new Thread(new Runnable() {

            @Override
            public void run() {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        flag = 1;
                        if (GCMIntentService.flag.equals("41")) {
                            if (Data.getInfoOf(getApplicationContext(),
                                    Data.customer_estimate_time).equals("0")
                                    || Data.getInfoOf(getApplicationContext(),
                                    Data.customer_estimate_time)
                                    .equals("")) {
                                textViewOrderPlaced
                                        .setText("Your server will arrive in few minutes, \nclick here to check your server's location");
                            } else if (Integer.parseInt(Data.getInfoOf(
                                    getApplicationContext(),
                                    Data.customer_estimate_time)) < 60) {
                                textViewOrderPlaced
                                        .setText("Your server will arrive in less than a min, \nclick here to check your server's location");
                            } else {
                                int est = (int) Math.ceil(Integer.parseInt(Data
                                        .getInfoOf(getApplicationContext(),
                                                Data.customer_estimate_time)) / 60.0);
                                textViewOrderPlaced
                                        .setText("Your server will arrive in "
                                                + est
                                                + " minutes, \nclick here to check your server's location");
                            }
                            Intent intent = new Intent(ListofMeals.this,
                                    DriverLocationFinder.class);
                            startActivity(intent);
                            finish();
                            overridePendingTransition(R.anim.slide_in_right,
                                    R.anim.hold);
                        } else if (Data
                                .getInfoOf(getApplicationContext(), "GCM")
                                .toString().equals("40")) {
                            Intent intent = new Intent(ListofMeals.this,
                                    Receipt.class);
                            startActivity(intent);
                            finish();
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
        Utils.buildAlertMessageNoGps(ListofMeals.this);
        super.onWindowFocusChanged(hasFocus);
    }

    @SuppressLint("SimpleDateFormat")
    public String utcToLocal(String utcTime) {

        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");

        try {
            Date myDate = simpleDateFormat.parse(utcTime);
            myDate.setTime(myDate.getTime()
                    + TimeZone.getDefault().getOffset(
                    Calendar.getInstance().getTime().getTime()));
            String localDate = sdf.format(myDate);
            return localDate;
        } catch (Exception e1) {
            Log.e("e1", "=" + e1);
            return utcTime;
        }
    }
}
