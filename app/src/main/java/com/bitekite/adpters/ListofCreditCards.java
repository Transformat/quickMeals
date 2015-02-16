package com.bitekite.adpters;

import rmn.androidscreenlibrary.ASSL;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import com.bitekite.R;
import com.bitekite.classes.ListofCardDetails;

public class ListofCreditCards extends BaseAdapter{

	Activity activity;
	private static LayoutInflater inflater = null;
	ViewHolder holder;

	public ListofCreditCards(Activity a) {
	
		activity = a;	
		inflater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

	}

	public int getCount() {
		return 3;
	}

	public Object getItem(int position) {
		return position;
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(final int position, View convertView, ViewGroup parent) {
		View vi = convertView;
		if (convertView == null) {

			holder = new ViewHolder();
			vi = inflater.inflate(R.layout.list_item_add_payment, null);
			holder.card = (TextView) vi.findViewById(R.id.textView1);
//			holder.tv2 = (TextView) vi.findViewById(R.id.textView2);
//			holder.tv3 = (TextView) vi.findViewById(R.id.textView3);
//			holder.img = (ImageView) vi.findViewById(R.id.imageView1);
//			holder.buttonAdd=(Button)vi.findViewById(R.id.buttonAdd);
//			holder.buttonMinus=(Button)vi.findViewById(R.id.buttonMinus);
			holder.ll = (LinearLayout) vi.findViewById(R.id.LinearLayout1);
			holder.ll.setLayoutParams(new ListView.LayoutParams(
					ListView.LayoutParams.MATCH_PARENT, ListView.LayoutParams.MATCH_PARENT));
			ASSL.DoMagic(holder.ll);
//			holder.buttonAdd.setTag(holder);
			
			vi.setTag(holder);
			// holder.tvname.setTag(holder);
			// holder.tvaddress.setTag(holder);
			// holder.ll.setTag(holder);
		}

		else {
		 holder= (ViewHolder)vi.getTag();
		}
		ListofCardDetails card = new ListofCardDetails();
//
//		holder.tvname.setTypeface(Data.omnes_Regular(activity));
//		holder.tvaddress.setTypeface(Data.omnes_Regular(activity));
//		holder.card.setText(card.getCard_1())
		holder.pos = position;
//		holder.tvname.setText("" + place_name[position]);
//		holder.tvaddress.setText("" + place_address[position]);


		// holder.ll.setLayoutParams(new ListView.LayoutParams(720,
		// 104));
		//
		// ASSL.DoMagic(holder.ll);
		return vi;

	}
	class ViewHolder {

		int pos;
		LinearLayout ll;
		TextView card;

	}
}

