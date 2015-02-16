package com.bitekite.adpters;

import java.text.DecimalFormat;
import java.util.ArrayList;

import rmn.androidscreenlibrary.ASSL;
import android.app.Activity;
import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.bitekite.R;
import com.bitekite.classes.MealSelected;
import com.bitekite.utils.Data;

public class OrderDetailCustomerAdapter extends BaseAdapter {

	
	Activity activity;
	private static LayoutInflater inflater = null;
	ArrayList<MealSelected>selectedList;
	
	ViewHolder holder;
	
	public OrderDetailCustomerAdapter(Activity activity,ArrayList<MealSelected> selectedlList) {
	
		this.activity = activity;
		this.selectedList = selectedlList;
		inflater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return selectedList.size();
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

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		Double prize = 0.00;
		View vi = convertView;
		if (convertView == null) {

			holder = new ViewHolder();
			vi = inflater.inflate(R.layout.list_item_order_details, null);
			holder.textViewMealQty = (TextView) vi.findViewById(R.id.textView1);
			holder.textViewMealName = (TextView) vi.findViewById(R.id.textView2);
			holder.textViewMealPrice = (TextView) vi.findViewById(R.id.textView3);
			holder.RelativeLayout1 = (RelativeLayout) vi.findViewById(R.id.RelativeLayout1);
			holder.RelativeLayout1.setLayoutParams(new ListView.LayoutParams(
					ListView.LayoutParams.MATCH_PARENT, 50));//ListView.LayoutParams.MATCH_PARENT));
			ASSL.DoMagic(holder.RelativeLayout1);
//			holder.buttonAdd.setTag(holder);
			
			vi.setTag(holder);
			

				
			
		}
		else{
			 holder= (ViewHolder)vi.getTag();
		}
		
		holder.textViewMealQty.setTypeface(Data.bariol_bold(activity));
		holder.textViewMealName.setTypeface(Data.bariol_regular(activity));
		holder.textViewMealPrice.setTypeface(Data.bariol_regular(activity));
		
		
		
		
		
		holder.textViewMealName.setText(selectedList.get(position).name);
		holder.textViewMealQty.setText(""+selectedList.get(position).quantity+"x");
		prize= Double.parseDouble(selectedList.get(position).price) *selectedList.get(position).quantity;
		DecimalFormat df = new DecimalFormat("#.##");
		
		prize = Double.valueOf(df.format(prize));
		String prizes= prize.toString();
		String str[] = prizes.split("\\.");
//		Double price = Double.parseDouble(mealsArrayList.get(position).price);
		if(str.length==1)
		{
			holder.textViewMealPrice.setText(Html.fromHtml("$"+ prize+".<sup><small>"+"00"+"</small></sup>"));
		}
		else
		{
//			holder.tv3.setText(Html.fromHtml("$"+ mealsArrayList.get(position).price+".<sup>"+"00"+"</sup>"));
		holder.textViewMealPrice.setText(Html.fromHtml("$"+str[0]+".<sup><small>"+str[1]+"</small></sup>"));
		}
		
//		holder.textViewMealPrice.setText("$ "+prize);
		
		
		if(!Data.mealname.contains(selectedList.get(position).name))
		{
			Data.mealqunt= Data.mealqunt+selectedList.get(position).quantity+"##";
			Data.mealname = Data.mealname+selectedList.get(position).name+"##";
			Data.mealPrice =Data.mealPrice +prize+"##";
		}
		
		return vi;
	}

	class ViewHolder {
//		RelativeLayout ll;
		int pos;
		TextView textViewMealQty, textViewMealName,textViewMealPrice;
		ImageView img;
		RelativeLayout RelativeLayout1;
		Button buttonAdd,buttonMinus;

	}
}
