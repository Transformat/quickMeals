package com.bitekite.adpters;

import java.util.ArrayList;

import rmn.androidscreenlibrary.ASSL;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.bitekite.R;
import com.bitekite.Log;
import com.bitekite.driver.classes.OrderBase;
import com.bitekite.utils.CircleTransform;
import com.bitekite.utils.Data;
import com.squareup.picasso.Picasso;


public class OrderDetailsDriverListAdapter extends BaseAdapter {
	private LayoutInflater minflater;
	ArrayList<OrderBase> orderArrayList;
	
	Activity activity;
//	private AQuery aq;
	public OrderDetailsDriverListAdapter(Activity activity,ArrayList<OrderBase> orderList) {

		minflater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.activity = activity;
//		aq = new AQuery(this.activity);
		orderArrayList = new ArrayList<OrderBase>();
		orderArrayList.addAll(orderList);
		

	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return orderArrayList.get(0).getDetails().size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	public static class ViewHolder {

		public TextView textViewMealCode,textViewToopingCode;
		RelativeLayout relativeLayoutMain;
		ImageView imageViewFoodImage;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		final ViewHolder holder;

		if (convertView == null) {
			view = minflater.inflate(R.layout.listitem_orderlist_driver, null);
			holder = new ViewHolder();
			holder.textViewMealCode = (TextView) view.findViewById(R.id.MealCode);
			holder.textViewToopingCode = (TextView) view.findViewById(R.id.ToopingCode);
			holder.imageViewFoodImage = (ImageView) view.findViewById(R.id.foodItemImg);
			holder.relativeLayoutMain = (RelativeLayout) view.findViewById(R.id.RelativeLayout1);
			holder.relativeLayoutMain.setLayoutParams(new ListView.LayoutParams(ListView.LayoutParams.MATCH_PARENT, 140));
			ASSL.DoMagic(holder.relativeLayoutMain);
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}
		holder.textViewMealCode.setTag(holder);
		holder.textViewMealCode.setText(orderArrayList.get(0).getDetails().get(position).getMealName().toString());
		String temp="";
		int flag = 0;
		for(int i =0 ;i<orderArrayList.get(0).getDetails().get(position).getTp().size();i++)
		{
			flag=1;
			temp = temp+orderArrayList.get(0).getDetails().get(position).getTp().get(i).getTopping_name()+" + ";
			Log.e("temp"+i,temp);
		}
		if(flag==1)
		{
			temp=temp.substring(0,temp.length()-2);
		}
		holder.textViewToopingCode.setText(temp);
		
		holder.textViewMealCode.setTypeface(Data.bariol_bold(activity));
		holder.textViewToopingCode.setTypeface(Data.bariol_bold(activity));
		
//		 aq.id(holder.imageViewFoodImage).image(orderArrayList.get(0).getDetails().get(position).getMealImage(),true,true,0,0,null, 0,(800*ASSL.Xscale())/(720 * ASSL.Yscale())).transformer(new CircleTransform()).fit();
		Picasso.with(activity).load(orderArrayList.get(0).getDetails().get(position).getMealImage().toString()).placeholder(R.drawable.placeholder_driver_android).error(R.drawable.placeholder_driver_android).transform(new CircleTransform()).fit().into(holder.imageViewFoodImage);
		return view;
	}
}
