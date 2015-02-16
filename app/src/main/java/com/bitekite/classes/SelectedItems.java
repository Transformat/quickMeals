package com.bitekite.classes;

import java.util.ArrayList;

public class SelectedItems {
	
	String mealName,mealId,mealIndex,mealPrice;
	ArrayList<SelectedTopping> topping = new ArrayList<SelectedTopping>();

	public String getMealPrice() {
		return mealPrice;
	}

	public void setMealPrice(String mealPrice) {
		this.mealPrice = mealPrice;
	}

	public ArrayList<SelectedTopping> getTopping() {
		return topping;
	}

	public void setTopping(ArrayList<SelectedTopping> topping) {
		this.topping = topping;
	}
	
	public void addTopping(SelectedTopping topping) {
		if(this.topping == null){
			this.topping = new ArrayList<SelectedTopping>();
		}
		if(!this.topping.contains(topping)){
			this.topping.add(topping);
		}
	}

	public String getMealIndex() {
		return mealIndex;
	}

	public void setMealIndex(String mealIndex) {
		this.mealIndex = mealIndex;
	}

	public String getMealName() {
		return mealName;
	}

	public void setMealName(String mealName) {
		this.mealName = mealName;
	}

	public String getMealId() {
		return mealId;
	}

	public void setMealId(String mealId) {
		this.mealId = mealId;
	}

}
