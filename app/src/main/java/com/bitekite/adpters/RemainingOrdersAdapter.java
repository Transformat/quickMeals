package com.bitekite.adpters;

import java.util.ArrayList;

import rmn.androidscreenlibrary.ASSL;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.MeasureSpec;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.AbsListView.LayoutParams;

import com.bitekite.Log;
import com.bitekite.R;
import com.bitekite.adpters.HistoryAdapterDriver.ViewHolderChild;
import com.bitekite.adpters.HistoryAdapterDriver.ViewHolderParent;
import com.bitekite.driver.classes.HistoryBase;
import com.bitekite.driver.classes.OrderBase;
import com.bitekite.utils.CircleTransform;
import com.bitekite.utils.Data;
import com.squareup.picasso.Picasso;

public class RemainingOrdersAdapter  extends BaseExpandableListAdapter {

	
	private LayoutInflater minflater;
	ArrayList<OrderBase> remainingData;
	Activity activity;

	public RemainingOrdersAdapter(Activity activity, ArrayList<OrderBase> orderList) {
		minflater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.activity = activity;
		remainingData = new ArrayList<OrderBase>();
		remainingData.addAll(orderList);
	}

	@Override
	public int getGroupCount() {
		return remainingData.size()-1;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		
		return remainingData.get(groupPosition+1).getDetails().size();
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
		holder.textViewUserName.setText(remainingData.get(groupPosition+1).getCustomer_name());
//		if(historyData.get(groupPosition).getTimeDifference().equals("0"))
//		{
			holder.textViewTime.setText(remainingData.get(groupPosition+1).getDistance().toString()+" miles, dues in "+remainingData.get(groupPosition+1).getTimeToDeliver().toString()+"mins");
//		}
//		else
//		{
//			holder.textViewTime.setText(historyData.get(groupPosition).getDeliveredTime()+"\n"+historyData.get(groupPosition).getTimeDifference()+"min delay");
//		}
		
		if(groupPosition==0)
		{
			holder.relativeLayoutMain.setBackgroundResource(R.drawable.ic_detail_box);
		}
		else
		{
			holder.relativeLayoutMain.setBackgroundColor(Color.WHITE);
		}
		
		if(remainingData.get(+1).getTotalMeals().equalsIgnoreCase("1"))
		{
			holder.textViewMeals.setText(remainingData.get(groupPosition+1).getTotalMeals()+" meal");
		}
		else
		{
			holder.textViewMeals.setText(remainingData.get(groupPosition+1).getTotalMeals()+" meals");
		}
		
		holder.textViewMeals.setTypeface(Data.bariol_bold(activity));
		holder.textViewTime.setTypeface(Data.bariol_bold(activity));
		holder.textViewUserName.setTypeface(Data.bariol_bold(activity));
		
		return view;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		
//		CustExpListview secondLevelexplv = new CustExpListview(activity,(remainingData.get(groupPosition).getDetails().size()));
////		secondLevelexplv.setAdapter(new SecondLevelAdapter(con, groupitems,
////				subcat));
//		secondLevelexplv.setGroupIndicator(null);
		
		
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
		holder.textViewMealCode.setText(remainingData.get(groupPosition+1).getDetails().get(childPosition).getMealName().toString());
		String temp="";
		int flag = 0;
		for(int i =0 ;i<remainingData.get(groupPosition+1).getDetails().get(childPosition).getTp().size();i++)
		{
			flag=1;
			temp = temp+remainingData.get(groupPosition+1).getDetails().get(childPosition).getTp().get(i).getTopping_name()+" + ";
			Log.e("temp"+i,temp);
		}
		if(flag==1)
		{
			temp=temp.substring(0,temp.length()-2);
		}
		
		holder.textViewToopingCode.setText(temp);
		
		holder.textViewMealCode.setTypeface(Data.bariol_regular(activity));
		holder.textViewToopingCode.setTypeface(Data.bariol_regular(activity));
		
		Picasso.with(activity).load(remainingData.get(groupPosition+1).getDetails().get(childPosition).getMealImage().toString()).placeholder(R.drawable.placeholder_driver_android).error(R.drawable.placeholder_driver_android).transform(new CircleTransform()).fit().into(holder.imageViewFoodImage);
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
	

//	public class CustExpListview extends ExpandableListView {
//
//		int intGroupPosition, intChildPosition, intGroupid;
//
//		public CustExpListview(Context context,int childPosition) {
//			super(context);
//			intChildPosition=childPosition;
//		}
//
//		protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//			
//			widthMeasureSpec = MeasureSpec.makeMeasureSpec(LayoutParams.MATCH_PARENT,MeasureSpec.AT_MOST);		
//			heightMeasureSpec = MeasureSpec.makeMeasureSpec(((int) (300.0f * ASSL.Yscale()))*(intChildPosition),MeasureSpec.AT_MOST);
//			Log.e("", "width="+widthMeasureSpec+"height="+heightMeasureSpec+"intChildPosition"+intChildPosition);
//			super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//		}
//	}


}
