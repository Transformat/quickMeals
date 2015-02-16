package com.bitekite.adpters;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;
import com.bitekite.R;
import rmn.androidscreenlibrary.ASSL;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bitekite.Log;
import com.bitekite.driver.classes.PerformanceBase;
import com.bitekite.utils.Data;

public class PerformanceAdapterDriver extends BaseExpandableListAdapter {

	private LayoutInflater minflater;
	ArrayList<PerformanceBase> accountData;
	Activity activity;

	public PerformanceAdapterDriver(Activity activity, ArrayList<PerformanceBase> data) {
		minflater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.activity = activity;
		accountData = new ArrayList<PerformanceBase>();
		accountData.addAll(data);
	}

	@Override
	public int getGroupCount() {
		// TODO Auto-generated method stub
		return accountData.size()-1;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		// TODO Auto-generated method stub
		return 1;
	}

	@Override
	public Object getGroup(int groupPosition) {
		// TODO Auto-generated method stub
		return groupPosition;
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getGroupId(int groupPosition) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		View view = convertView;
		final ViewHolderParent holder;

		if (convertView == null) {
			view = minflater.inflate(R.layout.listitem_performance_parent, null);
			holder = new ViewHolderParent();
			
			holder.textViewDay = (TextView) view.findViewById(R.id.DayTxt);
			holder.textViewTotalPrice = (TextView) view.findViewById(R.id.TotalPriceTxt);
			holder.relativeLayoutMain = (RelativeLayout) view
					.findViewById(R.id.RelativeLayout1);
			holder.relativeLayoutMain
					.setLayoutParams(new ListView.LayoutParams(
							ListView.LayoutParams.MATCH_PARENT, 80));
			
			
			ASSL.DoMagic(holder.relativeLayoutMain);
			view.setTag(holder);
		} else {
			holder = (ViewHolderParent) view.getTag();
		}
		if(groupPosition==0)
		{
			holder.relativeLayoutMain.setBackgroundResource(R.drawable.ic_detail_box);
		}
		else
		{
			holder.relativeLayoutMain.setBackgroundColor(Color.WHITE);
		}
		
		holder.textViewDay.setText(utcToLocal(accountData.get(groupPosition+1).getDate()));
		holder.textViewTotalPrice.setText("$"+accountData.get(groupPosition+1).getFinalTotal());
		
		
		holder.textViewDay.setTypeface(Data.bariol_bold(activity));
		holder.textViewTotalPrice.setTypeface(Data.bariol_bold(activity));
		
		return view;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		View view = convertView;
		final ViewHolderChild holder;

		if (convertView == null) {
			view = minflater.inflate(R.layout.listitem_performance_child, null);
			holder = new ViewHolderChild();
			
			holder.textViewHours = (TextView) view.findViewById(R.id.HoursTxt);
			holder.textViewHoursPrice = (TextView) view.findViewById(R.id.HoursPriceTxt);
			holder.textViewOrder = (TextView) view.findViewById(R.id.NumberOfOrdersTxt);
			holder.textViewOrderPrice = (TextView) view.findViewById(R.id.OrderWisePriceTxt);

			holder.relativeLayoutMain = (RelativeLayout) view
					.findViewById(R.id.RelativeLayout1);
			holder.relativeLayoutMain
					.setLayoutParams(new ListView.LayoutParams(
							ListView.LayoutParams.MATCH_PARENT, 200));
			ASSL.DoMagic(holder.relativeLayoutMain);
			view.setTag(holder);
		} else {
			holder = (ViewHolderChild) view.getTag();
		}
		
		String temp="";
		if(accountData.get(groupPosition+1).getTotalHours().toString().equals("0"))
		{
			temp="";
		}
		else if(accountData.get(groupPosition+1).getTotalHours().toString().equals("1"))
		{
			temp=temp+(accountData.get(groupPosition+1).getTotalHours().toString())+ " hour ";
		}
		else
		{
			temp=temp+(accountData.get(groupPosition+1).getTotalHours().toString())+ " hours ";
		}
		
		
		if(accountData.get(groupPosition+1).getMinutes().toString().equals("0"))
		{
			temp = temp+"";
		}
		else if(accountData.get(groupPosition+1).getMinutes().toString().equals("1"))
		{
			temp = temp+" "+accountData.get(groupPosition+1).getMinutes().toString()+ " minute";
		}
		else
		{
			temp = temp+" "+accountData.get(groupPosition+1).getMinutes().toString()+ " minutes";
		}
		
		if(accountData.get(groupPosition+1).getTotalHours().toString().equals("0") && accountData.get(groupPosition+1).getMinutes().toString().equals("0"))
		{
			temp = temp+"0 hour ";
		}
		
		temp = temp +"  X $"+ accountData.get(groupPosition+1).getHourPrice().toString();
		holder.textViewHours.setText(temp);
		
//		holder.textViewHours.setText(accountData.get(groupPosition+1).getTotalHours().toString()+" hours X $"+accountData.get(groupPosition+1).getHourPrice().toString());
		holder.textViewHoursPrice.setText("$"+accountData.get(groupPosition+1).getHourSum());
		holder.textViewOrder.setText(accountData.get(groupPosition+1).getTotalOrder().toString()+" order X $"+accountData.get(groupPosition+1).getOrderPrice().toString());
		holder.textViewOrderPrice.setText("$"+accountData.get(groupPosition+1).getOrderSum());
		
		holder.textViewHours.setTypeface(Data.bariol_regular(activity));
		holder.textViewHoursPrice.setTypeface(Data.bariol_regular(activity));
		holder.textViewOrder.setTypeface(Data.bariol_regular(activity));
		holder.textViewOrderPrice.setTypeface(Data.bariol_regular(activity));
		
		return view;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return false;
	}

	public static class ViewHolderParent {

		public TextView textViewDay, textViewTotalPrice;
		RelativeLayout relativeLayoutMain;
	}

	public static class ViewHolderChild {

		public TextView textViewHours, textViewHoursPrice, textViewOrder,
				textViewOrderPrice;
		RelativeLayout relativeLayoutMain;
	}
	
	public String utcToLocal(String utcTime) {
		SimpleDateFormat sdf = new SimpleDateFormat("MMMM d");
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
		try {
			Date myDate = simpleDateFormat.parse(utcTime);
			String localDate = sdf.format(myDate);
			
			return localDate;
		} catch (Exception e1) {
			Log.e("e1", "=" + e1);
			return utcTime;

		}

	}

}
