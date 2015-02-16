package com.bitekite.adpters;

import java.util.ArrayList;
import com.bitekite.R;
import rmn.androidscreenlibrary.ASSL;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bitekite.Log;
import com.bitekite.driver.classes.HistoryBase;
import com.bitekite.utils.CircleTransform;
import com.bitekite.utils.Data;
import com.squareup.picasso.Picasso;

public class HistoryAdapterDriver extends BaseExpandableListAdapter {
	
	private LayoutInflater minflater;
	ArrayList<HistoryBase> historyData;
	Activity activity;

	public HistoryAdapterDriver(Activity activity, ArrayList<HistoryBase> data) {
		minflater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.activity = activity;
		historyData = new ArrayList<HistoryBase>();
		historyData.addAll(data);
	}

	@Override
	public int getGroupCount() {
		return historyData.size();
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		
		return historyData.get(groupPosition).getDetails().size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		
		return groupPosition;
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		
		return null;
	}

	@Override
	public long getGroupId(int groupPosition) {
		
		return 0;
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		
		return 0;
	}

	@Override
	public boolean hasStableIds() {
		
		return false;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		View view = convertView;
		final ViewHolderParent holder;

		if (convertView == null) {
			view = minflater.inflate(R.layout.listitem_historyparent, null);
			holder = new ViewHolderParent();
			holder.textViewUserName = (TextView) view
					.findViewById(R.id.UserNameTxt);
			holder.textViewMeals = (TextView) view
					.findViewById(R.id.mealsTxt);
			holder.textViewTime = (TextView) view
					.findViewById(R.id.TimeTxt);
			holder.relativeLayoutMain = (RelativeLayout) view
					.findViewById(R.id.RelativeLayout1);
			holder.relativeLayoutMain
					.setLayoutParams(new ListView.LayoutParams(
							ListView.LayoutParams.MATCH_PARENT, 150));
			ASSL.DoMagic(holder.relativeLayoutMain);
			view.setTag(holder);
		} else {
			holder = (ViewHolderParent) view.getTag();
		}
		holder.textViewUserName.setTag(holder);
		holder.textViewUserName.setText(historyData.get(groupPosition).getCustomer_name());
		if(historyData.get(groupPosition).getTimeDifference().equals("0"))
		{
			holder.textViewTime.setText(historyData.get(groupPosition).getDeliveredTime()+"\nOn time");
		}
		else
		{
			holder.textViewTime.setText(historyData.get(groupPosition).getDeliveredTime()+"\n"+historyData.get(groupPosition).getTimeDifference()+"min delay");
		}
		
		if(groupPosition==0)
		{
			holder.relativeLayoutMain.setBackgroundResource(R.drawable.ic_detail_box);
		}
		else
		{
			holder.relativeLayoutMain.setBackgroundColor(Color.WHITE);
		}
		
		if(historyData.get(groupPosition).getTotalMeals().equalsIgnoreCase("1"))
		{
			holder.textViewMeals.setText(historyData.get(groupPosition).getTotalMeals()+" meal");
		}
		else
		{
			holder.textViewMeals.setText(historyData.get(groupPosition).getTotalMeals()+" meals");
		}
		
		holder.textViewMeals.setTypeface(Data.bariol_bold(activity));
		holder.textViewTime.setTypeface(Data.bariol_bold(activity));
		holder.textViewUserName.setTypeface(Data.bariol_bold(activity));
		
		return view;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		View view = convertView;
		final ViewHolderChild holder;

		if (convertView == null) {
			view = minflater.inflate(R.layout.listitem_orderlist_driver, null);
			holder = new ViewHolderChild();
			holder.textViewMealCode = (TextView) view.findViewById(R.id.MealCode);
			holder.textViewToopingCode = (TextView) view.findViewById(R.id.ToopingCode);
			holder.imageViewFoodImage = (ImageView) view.findViewById(R.id.foodItemImg);
			holder.relativeLayoutMain = (RelativeLayout) view.findViewById(R.id.RelativeLayout1);
			holder.relativeLayoutMain.setLayoutParams(new ListView.LayoutParams(ListView.LayoutParams.MATCH_PARENT, 140));
			ASSL.DoMagic(holder.relativeLayoutMain);
			view.setTag(holder);
		} else {
			holder = (ViewHolderChild) view.getTag();
		}
		holder.textViewMealCode.setTag(holder);
		holder.textViewMealCode.setText(historyData.get(groupPosition).getDetails().get(childPosition).getMealName().toString());
		String temp="";
		int flag = 0;
		for(int i =0 ;i<historyData.get(groupPosition).getDetails().get(childPosition).getTp().size();i++)
		{
			flag=1;
			temp = temp+historyData.get(groupPosition).getDetails().get(childPosition).getTp().get(i).getTopping_name()+" + ";
			Log.e("temp"+i,temp);
		}
		if(flag==1)
		{
			temp=temp.substring(0,temp.length()-2);
		}
		
		holder.textViewToopingCode.setText(temp);
		
		holder.textViewMealCode.setTypeface(Data.bariol_regular(activity));
		holder.textViewToopingCode.setTypeface(Data.bariol_regular(activity));
		
		Picasso.with(activity).load(historyData.get(groupPosition).getDetails().get(childPosition).getMealImage().toString()).placeholder(R.drawable.placeholder_driver_android).error(R.drawable.placeholder_driver_android).transform(new CircleTransform()).fit().into(holder.imageViewFoodImage);
		return view;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		
		return false;
	}
	
	public static class ViewHolderChild {

		public TextView textViewMealCode,textViewToopingCode;
		RelativeLayout relativeLayoutMain;
		ImageView imageViewFoodImage;
	}
	public static class ViewHolderParent {

		public TextView textViewUserName, textViewTime,textViewMeals;
		RelativeLayout relativeLayoutMain;
	}
	

}
