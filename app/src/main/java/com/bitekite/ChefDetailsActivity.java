package com.bitekite;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidquery.AQuery;

import rmn.androidscreenlibrary.ASSL;


public class ChefDetailsActivity extends Activity {
    ImageView chefImage;
    TextView chefName;
    TextView chefPost;
    TextView chefDetails;
    private AQuery aq;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chef_details);
        Bundle bundle = getIntent().getExtras();
        chefImage = (ImageView) findViewById(R.id.chef_full_image);
        chefName = (TextView) findViewById(R.id.chef_name_chef_page);
        chefPost = (TextView) findViewById(R.id.chef_post_chef_page);
        chefDetails = (TextView) findViewById(R.id.chef_detail_chef_page);
        aq = new AQuery(getApplicationContext());
        aq.id(chefImage).image(bundle.getString("chef_full_image"), true, true, 0, 0, null, 0,
                (800 * ASSL.Xscale()) / (800 * ASSL.Yscale()));
        chefName.setText(bundle.getString("chef_name"));
        chefPost.setText(bundle.getString("chef_post"));
        chefDetails.setText(bundle.getString("chef_details"));


    }


}
