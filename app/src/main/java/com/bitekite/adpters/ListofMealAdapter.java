package com.bitekite.adpters;

import java.util.ArrayList;

import rmn.androidscreenlibrary.ASSL;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.bitekite.R;
import com.bitekite.ListofMeals;
import com.bitekite.MealDetail;
import com.bitekite.classes.MealSelected;
import com.bitekite.classes.MealsData;
import com.bitekite.utils.Data;
import com.squareup.picasso.Picasso;

//Meal Adapter
public class ListofMealAdapter extends BaseAdapter {
	
	private AQuery aq;
	Activity activity;
	private static LayoutInflater inflater = null;
	ArrayList<MealsData> mealsArrayList;
	
	public static ArrayList<Integer> qtyList;
	public static ArrayList<MealSelected> selectedMealArrayList;

	ViewHolder holder;

	public ListofMealAdapter(Activity a, ArrayList<MealsData> mealArrayList) {

		activity = a;
		aq = new AQuery(activity);
		mealsArrayList=mealArrayList;
		selectedMealArrayList= new ArrayList<MealSelected>();
		qtyList = new ArrayList<Integer>();
//		mealselected = new MealSelected();
		for(int i =0;i<mealArrayList.size();i++)
		{
			MealSelected meal = new MealSelected();
			qtyList.add(0);
//			selectedMealArrayList.get(i).setMeal_id(mealsArrayList.get(i).meal_id);
			meal.setQuantity(0);
			selectedMealArrayList.add(meal);

		}
		
		inflater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		Log.d("In adapter","In adapter");
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mealsArrayList.size();
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
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View vi = convertView;
		if (convertView == null) {

			holder = new ViewHolder();
//			if(position==0)
//			{
//				vi = inflater.inflate(R.layout.list_header, null);
//			}
//			else
//			{
			vi = inflater.inflate(R.layout.list_item_listofmeal_adapter, null);
//			}
			holder.textViewQuanty = (TextView) vi.findViewById(R.id.textViewQuanty);
			holder.textViewDescription = (TextView) vi.findViewById(R.id.dish_name);
			holder.textViewPrice = (TextView) vi.findViewById(R.id.meal_price);
//			holder.textViewGC= (TextView) vi.findViewById(R.id.textViewGC);
			holder.img = (ImageView) vi.findViewById(R.id.imageView1);
//			holder.img.setX((int) (720 * ASSL.Xscale()));
//			holder.img.setX((int) (800 * ASSL.Yscale()));
			holder.buttonAdd=(Button)vi.findViewById(R.id.buttonAdd);
			holder.buttonMinus=(Button)vi.findViewById(R.id.buttonMinus);
			holder.buttonSoldOut=(Button)vi.findViewById(R.id.buttonSoldOut);
			holder.RelativeLayout1 = (RelativeLayout) vi.findViewById(R.id.RelativeLayout1);
			holder.relativeLayout2 = (RelativeLayout) vi.findViewById(R.id.relativeLayout2);
            holder.textViewChefName= (TextView) vi.findViewById(R.id.chef_name);
            holder.textViewChefDesc= (TextView) vi.findViewById(R.id.chef_description);
            holder.chefImage= (ImageView) vi.findViewById(R.id.chef_image);
            holder.RelativeLayout1.setLayoutParams(new ListView.LayoutParams(
					ListView.LayoutParams.MATCH_PARENT, ListView.LayoutParams.MATCH_PARENT));
			ASSL.DoMagic(holder.RelativeLayout1);
//			holder.buttonAdd.setTag(holder);
			
			vi.setTag(holder);
			// holder.tvname.setTag(holder);
			// holder.tvaddress.setTag(holder);
			// holder.ll.setTag(holder);
		}

		else {
		 holder= (ViewHolder)vi.getTag();
		}
		
		holder.pos = position;

//		holder.textViewGC.setTypeface(Data.bariol_regular(activity));
		
		if(Data.OPENSTATUS.equals("0"))
		{
			Log.e("closeeed","closeeed");
			holder.buttonAdd.setVisibility(View.GONE);
			holder.buttonMinus.setVisibility(View.GONE);
			holder.textViewQuanty.setVisibility(View.GONE);
		}
		else{
			Log.e("opeeeen","opeeeen");
			holder.buttonAdd.setVisibility(View.VISIBLE);
			holder.buttonMinus.setVisibility(View.VISIBLE);
			holder.textViewQuanty.setVisibility(View.VISIBLE);
		}
		
		
		holder.textViewQuanty.setTypeface(Data.bariol_bold(activity));
		holder.textViewDescription.setTypeface(Data.bariol_regular(activity));
		holder.textViewPrice.setTypeface(Data.bariol_regular(activity));
		holder.textViewChefName.setTypeface(Data.bariol_bold(activity));
        holder.textViewChefDesc.setTypeface(Data.bariol_regular(activity));
		holder.textViewQuanty.setTag(position);
		holder.buttonAdd.setTag(position);
		holder.buttonMinus.setTag(position);
		holder.textViewQuanty.setText(""+qtyList.get(position));
		
		
		holder.textViewDescription.setText("" + mealsArrayList.get(position).name);
        holder.textViewChefName.setText(mealsArrayList.get(position).chefName);
        holder.textViewChefDesc.setText(mealsArrayList.get(position).chefDescription);
		String prize= mealsArrayList.get(position).price.toString();

		String str[] = prize.split("\\.");
//		Double price = Double.parseDouble(mealsArrayList.get(position).price);
		if(str.length==1)
		{
			holder.textViewPrice.setText(Html.fromHtml("$"+ mealsArrayList.get(position).price+".<sup><small>"+"00"+"</small></sup>"));
		}
		else
		{
//			holder.tv3.setText(Html.fromHtml("$"+ mealsArrayList.get(position).price+".<sup>"+"00"+"</sup>"));
		holder.textViewPrice.setText(Html.fromHtml("$"+str[0]+".<sup><small>"+str[1]+"</small></sup>"));
		}
//		holder.tv3.setText("$" + mealsArrayList.get(position).price+Html.fromHtml("7<sup>2</sup>"));
		Log.d("meal image","meal image"+mealsArrayList.get(position).meal_image+",");
//		Picasso.with(activity)
//		.load(mealsArrayList.get(position).meal_image)
//		.placeholder(R.drawable.placeholder_item).resize((int) (720 * ASSL.Xscale()),
//				(int) (800 * ASSL.Yscale())).centerCrop()
//		.into(holder.img);
		
//		 aq.id(holder.img).image(""+mealsArrayList.get(position).meal_image,true,true);
		 
		 aq.id(holder.img).image(mealsArrayList.get(position).meal_image,true,true,0,0,null, 0,(800*ASSL.Xscale())/(720 * ASSL.Yscale()));
		 aq.id(holder.chefImage).image(mealsArrayList.get(position).chefImage,true,true,0,0,null,0,(150*ASSL.Xscale())/(150 * ASSL.Yscale()));
	//	 aq.id(holder.img).im
//		Log.d("mealsArrayList.get(position).stock_available", "mealsArrayList.get(position).stock_available"+mealsArrayList.get(position).stock_available.toString());
		if(mealsArrayList.get(position).stock_available.toString().equals("0"))
		{
			
			holder.buttonSoldOut.setVisibility(View.VISIBLE);
		}
		else
		{
			holder.buttonSoldOut.setVisibility(View.GONE);
		}
	     
		holder.buttonAdd.setTag(holder);
		
		holder.buttonAdd.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				holder = (ViewHolder) v.getTag();
				int pos = holder.pos;
				if(mealsArrayList.get(position).stock_available.toString().equals("0"))
				{
					
				}
				else
				{
				if(qtyList.get(pos)>=0)
				{
                    holder.buttonMinus.setVisibility(View.VISIBLE);
					int value = qtyList.get(pos);
					Log.e("holder.buttonAdd.getTag().toString()",""+holder.buttonAdd.getTag().toString());
					value= value+1;
					qtyList.set(pos, value);			
					selectedMealArrayList.get(pos).setQuantity(value);
					selectedMealArrayList.get(pos).setMeal_id(mealsArrayList.get(pos).meal_id);
					selectedMealArrayList.get(pos).setName(mealsArrayList.get(pos).name);
					selectedMealArrayList.get(pos).setPrice(mealsArrayList.get(pos).price);
					selectedMealArrayList.get(pos).setToppingSize(mealsArrayList.get(pos).toppingSize);
					checkButtons();
					Data.totalValue=Data.totalValue+1;
					ListofMeals.textViewTotal.setText(""+Data.totalValue);
//					mButton.setText(Html.fromHtml("7<sup>2</sup>"));
					ListofMeals.LinearLayout1.setVisibility(View.VISIBLE);
					notifyDataSetChanged();
				}

				}
//				value = Integer.parseInt(holder1.qunty.getText().toString());
//				Data.totalValue=Data.totalValue-value;
//				value=value+1;
//				
//				qtyList.add(holder.pos, ""+value);
//				Data.totalValue=Data.totalValue+value;
//				holder1.qunty.setText(""+qtyList.get(holder1.pos).toString());
//				Log.d("TOTAL","TOTAL"+Data.totalValue);
				
//				selectedMealArrayList.add( new MealSelected(mealsArrayList.get(holder1.pos).meal_description, mealsArrayList.get(holder1.pos).meal_id,
//						mealsArrayList.get(holder1.pos).price,mealsArrayList.get(holder1.pos).meal_image, 
//						mealsArrayList.get(holder1.pos).name, qtyList.get(holder1.pos).toString() , mealsArrayList.get(holder1.pos).toppings));
			}
		});
		
		
		holder.buttonMinus.setTag(holder);
		holder.buttonMinus.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				holder = (ViewHolder) v.getTag();
				int pos = holder.pos;
				
				if(mealsArrayList.get(position).stock_available.toString().equals("0"))
				{
					
				}
				else
				{
				if(qtyList.get(pos)>0)
				{
					int value = qtyList.get(pos);
					value= value-1;
					qtyList.set(pos, value);
					selectedMealArrayList.get(pos).setQuantity(value);
					selectedMealArrayList.get(pos).setMeal_id(mealsArrayList.get(pos).meal_id);
					selectedMealArrayList.get(pos).setName(mealsArrayList.get(pos).name);
					selectedMealArrayList.get(pos).setPrice(mealsArrayList.get(pos).price);
					selectedMealArrayList.get(pos).setToppingSize(mealsArrayList.get(pos).toppingSize);
					checkButtons();
					Data.totalValue=Data.totalValue-1;
					ListofMeals.textViewTotal.setText(""+Data.totalValue);
					
					if(Data.totalValue==0)
					{
						ListofMeals.LinearLayout1.setVisibility(View.GONE);
					}
					else
					{
						ListofMeals.LinearLayout1.setVisibility(View.VISIBLE);
					}
					notifyDataSetChanged();
				}else
                    holder.buttonMinus.setVisibility(View.INVISIBLE);

				}
				// TODO Auto-generated method stub
//				ViewHolder holder1 = (ViewHolder)v.getTag();
//				int value;
//				value = Integer.parseInt(holder1.qunty.getText().toString());
//				Data.totalValue=Data.totalValue-value;
//				if(value!=0)
//					value=value-1;
//				qtyList.add(holder.pos, ""+value);
//				Data.totalValue=Data.totalValue+value;
//				holder1.qunty.setText(""+qtyList.get(holder1.pos).toString());
//				Log.d("TOTAL","TOTAL"+Data.totalValue);
//				ListofMeals.textViewTotal.setText(""+Data.totalValue);
//				if(Data.totalValue==0)
//					ListofMeals.LinearLayout1.setVisibility(View.GONE);
//				
//				selectedMealArrayList.add( new MealSelected(mealsArrayList.get(holder1.pos).meal_description, mealsArrayList.get(holder1.pos).meal_id,
//						mealsArrayList.get(holder1.pos).price,mealsArrayList.get(holder1.pos).meal_image, 
//						mealsArrayList.get(holder1.pos).name, qtyList.get(holder1.pos).toString() , mealsArrayList.get(holder1.pos).toppings));
			}
		});
		
		holder.RelativeLayout1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				ListofMeals.flag=0;
				Intent intent = new Intent(activity,MealDetail.class);				 				
				intent.putExtra("meal_id",mealsArrayList.get(position).meal_id);
				intent.putExtra("meal_description",mealsArrayList.get(position).meal_description);
				intent.putExtra("price",mealsArrayList.get(position).price);
				intent.putExtra("stock_available",mealsArrayList.get(position).stock_available);
				intent.putExtra("meal_image",mealsArrayList.get(position).meal_image);
				intent.putExtra("name",mealsArrayList.get(position).name);
				intent.putExtra("meal_details",mealsArrayList.get(position).meal_details);
                intent.putExtra("chef_name",mealsArrayList.get(position).chefName);
                intent.putExtra("chef_description",mealsArrayList.get(position).chefDescription);
                intent.putExtra("chef_image",mealsArrayList.get(position).chefImage);
                intent.putExtra("chef_post",mealsArrayList.get(position).chefPost);
                intent.putExtra("chef_full_details",mealsArrayList.get(position).chefFullDetails);
                intent.putExtra("chef_full_image",mealsArrayList.get(position).chefFullImage);
				Data.TOPPING_ARRAYLIST= mealsArrayList.get(position).toppings;
				
//				for(int i=0;i<mealsArrayList.size();i++)
//				{
//					
//				}
//			
				activity.startActivity(intent);		
				activity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

			}
		});
		
		

		return vi;
	}
	
	class ViewHolder {
//		RelativeLayout ll;
		int pos;
		TextView textViewQuanty, textViewDescription,textViewPrice,textViewChefName,textViewChefDesc;
		ImageView img,chefImage;
		RelativeLayout RelativeLayout1,relativeLayout2;
		Button buttonAdd,buttonMinus,buttonSoldOut;

	}

	void checkButtons()
	{
		for(int i =0 ;i<selectedMealArrayList.size();i++)
		{
			if(selectedMealArrayList.get(i).getQuantity()>0)
			{
				if(selectedMealArrayList.get(i).getToppingSize()>0)
				{
					Log.e("relativeLayoutCheckOut"+i, "VISIBLE");

					ListofMeals.relativeLayoutCheckOut.setVisibility(View.VISIBLE);
					ListofMeals.relativeLayoutCustomize.setVisibility(View.VISIBLE);
					break;
				}
				else
				{
					Log.e("relativeLayoutCustomize"+i, "GONE");
					ListofMeals.relativeLayoutCheckOut.setVisibility(View.VISIBLE);
					ListofMeals.relativeLayoutCustomize.setVisibility(View.GONE);
				}
			}
			
		}
	}
}
