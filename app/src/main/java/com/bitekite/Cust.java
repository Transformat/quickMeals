package com.bitekite;

import rmn.androidscreenlibrary.ASSL;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bitekite.classes.SelectedTopping;
import com.bitekite.driver.Utils;
import com.bitekite.utils.Data;
import com.google.analytics.tracking.android.EasyTracker;
import com.squareup.picasso.Picasso;

public class Cust extends Activity {

	TextView textViewMealDetails, textViewNumberOfMeals, textViewCheckout,
			textViewChoose, textViewNumberOfCartItems;

	LinearLayout linearLayoutScroll, linearLayoutTouch,
			linearLayoutScrollTopping;
	HorizontalScrollView horizontalScrollViewMeal, horizontalScrollViewTopping;
	RelativeLayout relativeLayoutLeftBtn, relativeLayoutRightBtn,
			relativeLayoutBack, relativeLayoutCart;
	ImageView imageViewPointer1, imageViewPointer2, imageViewPointer3,
			imageViewPointer4, imageViewPointer5;
	int k = 0, counter = 0, value;
	TextView textViewNoToppings;
	LinearLayout linearLayoutSelectedToppings;
	ImageView imageViewMealLeft, imageViewMealRight;
	TextView textViewCheckOut;
	int p;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cust);
		new ASSL(this, (ViewGroup) findViewById(R.id.root), 1134, 720, false);

		imageViewMealLeft = (ImageView) findViewById(R.id.mealLeft);
		imageViewMealRight = (ImageView) findViewById(R.id.mealRight);
		linearLayoutScroll = (LinearLayout) findViewById(R.id.horizontalScrollLayout);
		linearLayoutScrollTopping = (LinearLayout) findViewById(R.id.horizontalScrollLayoutTopping);
		horizontalScrollViewMeal = (HorizontalScrollView) findViewById(R.id.horizontalScrollView1);
		horizontalScrollViewTopping = (HorizontalScrollView) findViewById(R.id.horizontalScrollViewTopping);
		relativeLayoutRightBtn = (RelativeLayout) findViewById(R.id.rightBtnLayout);
		relativeLayoutLeftBtn = (RelativeLayout) findViewById(R.id.leftBtnLayout);
		linearLayoutTouch = (LinearLayout) findViewById(R.id.ClickLayout);
		relativeLayoutBack = (RelativeLayout) findViewById(R.id.relativeLayoutBack);
		textViewNumberOfCartItems = (TextView) findViewById(R.id.TotalItems);
		relativeLayoutCart = (RelativeLayout) findViewById(R.id.Cart);
		textViewCheckout = (TextView) findViewById(R.id.textViewCheckout);
		textViewNoToppings = (TextView) findViewById(R.id.textViewNoToppings);
		linearLayoutSelectedToppings = (LinearLayout) findViewById(R.id.selectedTopping);

		textViewCheckOut = (TextView) findViewById(R.id.textViewCheckOut);

		imageViewPointer1 = (ImageView) findViewById(R.id.pointer1);
		imageViewPointer2 = (ImageView) findViewById(R.id.pointer2);
		imageViewPointer3 = (ImageView) findViewById(R.id.pointer3);
		imageViewPointer4 = (ImageView) findViewById(R.id.pointer4);
		imageViewPointer5 = (ImageView) findViewById(R.id.pointer5);

		textViewMealDetails = (TextView) findViewById(R.id.MealDetails);
		textViewNumberOfMeals = (TextView) findViewById(R.id.NumberOfMeals);
		textViewChoose = (TextView) findViewById(R.id.textViewChoose);

		textViewCheckOut.setTypeface(Data
				.bariol_regular(getApplicationContext()));
		textViewMealDetails.setTypeface(Data
				.bariol_bold(getApplicationContext()));
		textViewChoose.setTypeface(Data.bariol_bold(getApplicationContext()));
		textViewNumberOfMeals.setTypeface(Data
				.bariol_regular(getApplicationContext()));
		textViewNoToppings.setTypeface(Data
				.bariol_bold(getApplicationContext()));

		textViewMealDetails.setText(""
				+ ListofMeals.dataMealArrayList.get(Integer
						.parseInt(Data.selectedItems.get(counter)
								.getMealIndex().toString())).meal_description);

		setTopping();
		if (Data.selectedItems.size() == 1) {
			textViewNumberOfMeals.setText("" + Data.selectedItems.size()
					+ " MEAL SELECTED");
		} else {
			textViewNumberOfMeals.setText("" + Data.selectedItems.size()
					+ " MEALS SELECTED");
		}

		if (Data.selectedItems.size() < 6) {
			relativeLayoutRightBtn.setVisibility(View.INVISIBLE);
			relativeLayoutLeftBtn.setVisibility(View.INVISIBLE);
		} else {
			relativeLayoutRightBtn.setVisibility(View.VISIBLE);
			relativeLayoutLeftBtn.setVisibility(View.VISIBLE);
		}

		for (int i = 0; i < Data.selectedItems.size(); i++) {

			View child = getLayoutInflater()
					.inflate(R.layout.layout_meal, null);
			final LinearLayout linearLayoutMain = (LinearLayout) child
					.findViewById(R.id.meal_item);
			linearLayoutMain
					.setLayoutParams(new ListView.LayoutParams(104, 140));
			ASSL.DoMagic(linearLayoutMain);
			linearLayoutScroll.addView(child);

			ImageView imageViewMeal = (ImageView) child
					.findViewById(R.id.imageViewMeal);
			TextView textViewMealNumber = (TextView) child
					.findViewById(R.id.MealNumber);
			textViewMealNumber.setText("MEAL " + (i + 1));
			textViewMealNumber.setTypeface(Data
					.bariol_regular(getApplicationContext()));
			linearLayoutMain.setTag(i);

			linearLayoutMain.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

					value = (Integer.parseInt(linearLayoutMain.getTag()
							.toString()));

					Log.e("Value", "==" + value + "====" + k);

					if (value == k + 0) {
						imageViewPointer1.setVisibility(View.VISIBLE);
						imageViewPointer2.setVisibility(View.INVISIBLE);
						imageViewPointer3.setVisibility(View.INVISIBLE);
						imageViewPointer4.setVisibility(View.INVISIBLE);
						imageViewPointer5.setVisibility(View.INVISIBLE);
					} else if (value == k + 1) {
						imageViewPointer1.setVisibility(View.INVISIBLE);
						imageViewPointer2.setVisibility(View.VISIBLE);
						imageViewPointer3.setVisibility(View.INVISIBLE);
						imageViewPointer4.setVisibility(View.INVISIBLE);
						imageViewPointer5.setVisibility(View.INVISIBLE);
					} else if (value == k + 2) {
						imageViewPointer1.setVisibility(View.INVISIBLE);
						imageViewPointer2.setVisibility(View.INVISIBLE);
						imageViewPointer3.setVisibility(View.VISIBLE);
						imageViewPointer4.setVisibility(View.INVISIBLE);
						imageViewPointer5.setVisibility(View.INVISIBLE);
					} else if (value == k + 3) {
						imageViewPointer1.setVisibility(View.INVISIBLE);
						imageViewPointer2.setVisibility(View.INVISIBLE);
						imageViewPointer3.setVisibility(View.INVISIBLE);
						imageViewPointer4.setVisibility(View.VISIBLE);
						imageViewPointer5.setVisibility(View.INVISIBLE);
					} else if (value == k + 4) {
						imageViewPointer1.setVisibility(View.INVISIBLE);
						imageViewPointer2.setVisibility(View.INVISIBLE);
						imageViewPointer3.setVisibility(View.INVISIBLE);
						imageViewPointer4.setVisibility(View.INVISIBLE);
						imageViewPointer5.setVisibility(View.VISIBLE);
					}
					counter = value;
					textViewMealDetails.setText(""
							+ ListofMeals.dataMealArrayList.get(Integer
									.parseInt(Data.selectedItems.get(counter)
											.getMealIndex().toString())).meal_description);
					setTopping();

				}
			});

		}

		textViewNumberOfCartItems.setText("" + Data.selectedItems.size());

		relativeLayoutCart.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// ListofMeals.Flag=1;
				Intent intent = new Intent(Cust.this, CheckOutCustomer.class);
				startActivity(intent);
				overridePendingTransition(R.anim.slide_in_right,
						R.anim.slide_out_left);
				finish();
			}
		});

		relativeLayoutRightBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (k < Data.selectedItems.size() - 5) {
					k++;
					counter++;

					textViewMealDetails.setText(""
							+ ListofMeals.dataMealArrayList.get(Integer
									.parseInt(Data.selectedItems.get(counter)
											.getMealIndex().toString())).meal_description);

					setTopping();
				}

				Log.e("counter", "==" + counter);
				horizontalScrollViewMeal.smoothScrollBy(
						(int) (104 * ASSL.Xscale()), 0);
			}
		});

		relativeLayoutLeftBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (k != 0) {
					k--;
					counter--;
					textViewMealDetails.setText(""
							+ ListofMeals.dataMealArrayList.get(Integer
									.parseInt(Data.selectedItems.get(counter)
											.getMealIndex().toString())).meal_description);

					setTopping();
				}

				Log.e("counter", "==" + counter);
				horizontalScrollViewMeal.smoothScrollBy(
						-(int) (104 * ASSL.Xscale()), 0);
			}
		});
		relativeLayoutBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				finish();
				overridePendingTransition(R.anim.slide_in_left,
						R.anim.slide_out_right);
			}
		});

		horizontalScrollViewMeal.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				return true;
			}
		});

		horizontalScrollViewTopping.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {

				return true;
			}
		});

	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub

		finish();
		overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);

	}

	void setTopping() {
		String url;
		linearLayoutSelectedToppings.removeAllViews();
		linearLayoutScrollTopping.removeAllViews();

		textViewNoToppings.setVisibility(View.VISIBLE);
		int size = ListofMeals.dataMealArrayList.get(Integer
				.parseInt(Data.selectedItems.get(counter).getMealIndex()
						.toString())).toppings.size();
		p = 2;
		if (size > 2) {

			imageViewMealRight.setVisibility(View.VISIBLE);
			imageViewMealLeft.setVisibility(View.INVISIBLE);
			imageViewMealRight.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					horizontalScrollViewTopping.smoothScrollBy(
							(int) (360 * ASSL.Xscale()), 0);
					p++;
					if (p * (int) (360 * ASSL.Xscale()) < linearLayoutScrollTopping
							.getWidth()) {
						Log.e("horizontalScrollViewTopping", "==" + p
								* (int) (360 * ASSL.Xscale()) + "==="
								+ linearLayoutScrollTopping.getWidth());
						imageViewMealLeft.setVisibility(View.VISIBLE);
					} else {
						imageViewMealLeft.setVisibility(View.VISIBLE);
						imageViewMealRight.setVisibility(View.INVISIBLE);
					}
				}
			});

			imageViewMealLeft.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					horizontalScrollViewTopping.smoothScrollBy(
							-(int) (360 * ASSL.Xscale()), 0);
					p--;
					if (p * (int) (360 * ASSL.Xscale()) > (int) (720 * ASSL
							.Xscale())) {
						Log.e("horizontalScrollViewTopping", "==" + p
								* (int) (360 * ASSL.Xscale()) + "==="
								+ linearLayoutScrollTopping.getWidth());
						imageViewMealRight.setVisibility(View.VISIBLE);

						Log.e("VALUE",
								"====" + linearLayoutScrollTopping.getWidth());
					} else {
						imageViewMealRight.setVisibility(View.VISIBLE);
						imageViewMealLeft.setVisibility(View.INVISIBLE);
					}
				}
			});
		} else {
			imageViewMealRight.setVisibility(View.INVISIBLE);
			imageViewMealLeft.setVisibility(View.INVISIBLE);
		}

		for (int i = 0; i < size; i++) {
			textViewNoToppings.setVisibility(View.GONE);
			View child = getLayoutInflater().inflate(R.layout.topping_item,
					null);
			final ImageView imageView = (ImageView) child
					.findViewById(R.id.toopingImg);
			TextView textViewToppingName = (TextView) child
					.findViewById(R.id.ToppingName);
			TextView textViewToppingDetails = (TextView) child
					.findViewById(R.id.ToppingDetails);
			TextView textViewToppintItemPrice = (TextView) child
					.findViewById(R.id.PriceTxt);

			textViewToppingName.setTypeface(Data
					.bariol_regular(getApplicationContext()));
			textViewToppingDetails.setTypeface(Data
					.bariol_regular(getApplicationContext()));
			textViewToppintItemPrice.setTypeface(Data
					.bariol_bold(getApplicationContext()));

			final RelativeLayout relativeLayoutShadow = (RelativeLayout) child
					.findViewById(R.id.relativeLayoutShadow);
			final ImageView imageViewSelected = (ImageView) child
					.findViewById(R.id.selecetItemImg);

			final LinearLayout relativeLayoutMain = (LinearLayout) child
					.findViewById(R.id.topping_item);
			relativeLayoutMain.setLayoutParams(new ListView.LayoutParams(360,
					ListView.LayoutParams.WRAP_CONTENT));
			ASSL.DoMagic(relativeLayoutMain);
			linearLayoutScrollTopping.addView(child);
			url = ListofMeals.dataMealArrayList.get(Integer
					.parseInt(Data.selectedItems.get(counter).getMealIndex()
							.toString())).toppings.get(i).topping_image
					.toString();

			textViewToppingName.setText(ListofMeals.dataMealArrayList
					.get(Integer.parseInt(Data.selectedItems.get(counter)
							.getMealIndex().toString())).toppings.get(i).name);
			textViewToppingDetails
					.setText(ListofMeals.dataMealArrayList.get(Integer
							.parseInt(Data.selectedItems.get(counter)
									.getMealIndex().toString())).toppings
							.get(i).topping_description.toString());

			String temp[] = ListofMeals.dataMealArrayList.get(Integer
					.parseInt(Data.selectedItems.get(counter).getMealIndex()
							.toString())).toppings.get(i).price.toString()
					.split("\\.");

			if (temp.length == 1) {
				textViewToppintItemPrice.setText(Html.fromHtml("$" + temp[0]
						+ ".<sup><small>00</small></sup>"));
			} else {
				if (temp[1].length() == 1) {
					textViewToppintItemPrice.setText(Html.fromHtml("$"
							+ temp[0] + ".<sup><small>" + temp[1]
							+ "0</small></sup>"));
				} else {
					textViewToppintItemPrice.setText(Html.fromHtml("$"
							+ temp[0] + ".<sup><small>" + temp[1]
							+ "</small></sup>"));
				}

			}

			Picasso.with(this)
					.load(url)
					.placeholder(R.drawable.placeholder_topping)
					.resize((int) (360 * ASSL.Xscale()),
							(int) (380 * ASSL.Yscale())).centerCrop()
					.into(imageView);

			imageView.setTag(i);

			for (int k = 0; k < Data.selectedItems.get(counter).getTopping()
					.size(); k++) {
				Log.e("Set Topping", "Set Topping");
				if (ListofMeals.dataMealArrayList.get(Integer
						.parseInt(Data.selectedItems.get(counter)
								.getMealIndex().toString())).toppings.get(i).topping_id
						.toString().equals(
								Data.selectedItems.get(counter).getTopping()
										.get(k).getToppingId().toString())) {
					imageViewSelected.setVisibility(View.VISIBLE);
					relativeLayoutShadow.setVisibility(View.VISIBLE);

					break;
				} else {
					imageViewSelected.setVisibility(View.INVISIBLE);
					relativeLayoutShadow.setVisibility(View.INVISIBLE);
				}
			}

			imageView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

					if (imageViewSelected.isShown()) {
						imageViewSelected.setVisibility(View.INVISIBLE);
						relativeLayoutShadow.setVisibility(View.INVISIBLE);
						for (int j = 0; j < Data.selectedItems.get(counter)
								.getTopping().size(); j++) {
							if (ListofMeals.dataMealArrayList.get(Integer
									.parseInt(Data.selectedItems.get(counter)
											.getMealIndex().toString())).toppings
									.get(Integer.parseInt(imageView.getTag()
											.toString())).topping_id
									.equals(Data.selectedItems.get(counter)
											.getTopping().get(j).getToppingId()
											.toString())) {
								Data.selectedItems.get(counter).getTopping()
										.remove(j);
								break;
							}
						}

					} else {
						imageViewSelected.setVisibility(View.VISIBLE);
						relativeLayoutShadow.setVisibility(View.VISIBLE);
						SelectedTopping selectedToppingObject = new SelectedTopping();
						selectedToppingObject.setToppingId(ListofMeals.dataMealArrayList
								.get(Integer
										.parseInt(Data.selectedItems
												.get(counter).getMealIndex()
												.toString())).toppings
								.get(Integer.parseInt(imageView.getTag()
										.toString())).topping_id);
						selectedToppingObject
								.setToppingName(ListofMeals.dataMealArrayList
										.get(Integer
												.parseInt(Data.selectedItems
														.get(counter)
														.getMealIndex()
														.toString())).toppings
										.get(Integer.parseInt(imageView
												.getTag().toString())).name);
						selectedToppingObject
								.setToppingPrice(ListofMeals.dataMealArrayList
										.get(Integer
												.parseInt(Data.selectedItems
														.get(counter)
														.getMealIndex()
														.toString())).toppings
										.get(Integer.parseInt(imageView
												.getTag().toString())).price);

						Data.selectedItems.get(counter).addTopping(
								selectedToppingObject);
					}
					setlist();
				}
			});
			setlist();
		}
	}

	void setlist() {
		linearLayoutSelectedToppings.removeAllViews();
		for (int k = 0; k < Data.selectedItems.get(counter).getTopping().size(); k++) {
			View child = getLayoutInflater().inflate(
					R.layout.selectedtopping_listitem, null);

			TextView textViewToppingName = (TextView) child
					.findViewById(R.id.ToppingName);
			TextView textViewToppingPrice = (TextView) child
					.findViewById(R.id.ToppingPrice);

			textViewToppingName.setText(Data.selectedItems.get(counter)
					.getTopping().get(k).getToppingName());
			String temp[] = Data.selectedItems.get(counter).getTopping().get(k)
					.getToppingPrice().split("\\.");

			if (temp.length == 1) {
				textViewToppingPrice.setText(Html.fromHtml("      +     $ "
						+ temp[0] + ".<sup><small>00</small></sup>"));

			} else {
				if (temp[1].length() == 1) {
					textViewToppingPrice.setText(Html.fromHtml("      +     $ "
							+ temp[0] + ".<sup><small>" + temp[1]
							+ "0</small></sup>"));
				} else {
					textViewToppingPrice.setText(Html.fromHtml("      +     $ "
							+ temp[0] + ".<sup><small>" + temp[1]
							+ "</small></sup>"));
				}

			}
			final LinearLayout relativeLayoutMain = (LinearLayout) child
					.findViewById(R.id.linearSelected);
			relativeLayoutMain.setLayoutParams(new ListView.LayoutParams(
					ListView.LayoutParams.MATCH_PARENT, 70));
			ASSL.DoMagic(relativeLayoutMain);
			linearLayoutSelectedToppings.addView(child);
		}
	}

	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		Utils.buildAlertMessageNoGps(Cust.this);
		super.onWindowFocusChanged(hasFocus);
	}

	@Override
	protected void onStart() {
		super.onStart();
		EasyTracker.getInstance(Cust.this).activityStart(this);
	}

	@Override
	protected void onStop() {
		super.onStop();
		EasyTracker.getInstance(Cust.this).activityStop(this);
	}
}
