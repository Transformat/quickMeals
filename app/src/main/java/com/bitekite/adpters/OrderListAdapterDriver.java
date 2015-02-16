package com.bitekite.adpters;

import java.util.ArrayList;

import rmn.androidscreenlibrary.ASSL;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.bitekite.R;
import com.bitekite.driver.classes.OrderBase;
import com.bitekite.utils.Data;

public class OrderListAdapterDriver extends BaseAdapter {
	private LayoutInflater minflater;
	ArrayList<OrderBase> orderArrayList;

	Activity activity;

	public OrderListAdapterDriver(Activity activity,
			ArrayList<OrderBase> orderList) {

		minflater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.activity = activity;
		orderArrayList = new ArrayList<OrderBase>();
		orderArrayList.addAll(orderList);

	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return orderArrayList.size()-1;
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

		public TextView textViewUserName, textViewDistance;
		RelativeLayout relativeLayoutMain;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		final ViewHolder holder;

		if (convertView == null) {
			view = minflater.inflate(R.layout.listitem_orderlist, null);
			holder = new ViewHolder();
			holder.textViewUserName = (TextView) view
					.findViewById(R.id.UserNameTxt);
			holder.textViewDistance = (TextView) view
					.findViewById(R.id.DistanceTxt);
			holder.relativeLayoutMain = (RelativeLayout) view
					.findViewById(R.id.RelativeLayout1);
			holder.relativeLayoutMain
					.setLayoutParams(new ListView.LayoutParams(
							ListView.LayoutParams.MATCH_PARENT, 80));
			ASSL.DoMagic(holder.relativeLayoutMain);
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}
		holder.textViewUserName.setTag(holder);
		holder.textViewUserName.setText(orderArrayList.get(position+1).getCustomer_name());
		holder.textViewDistance.setText(orderArrayList.get(position+1).getDistance().toString()+" miles, dues in "+orderArrayList.get(position+1).getTimeToDeliver().toString()+"mins");
		
		holder.textViewUserName.setTypeface(Data.bariol_regular(activity));
		holder.textViewDistance.setTypeface(Data.bariol_regular(activity));
		
		return view;
	}
}