package com.bitekite.driver.classes;

import java.util.ArrayList;

public class DetailsBase {

	String mealName, mealImage, mealQuantity, mealId, mealCode;
	ArrayList<ToppingDetailsBase> tp = new ArrayList<ToppingDetailsBase>();

	public String getMealName() {
		return mealName;
	}

	public void setMealName(String meal_Name) {
		this.mealName = meal_Name;
	}

	public String getMealImage() {
		return mealImage;
	}

	public void setMealImage(String meal_Image) {
		this.mealImage = meal_Image;
	}
	
	public String getMealQuantity() {
		return mealQuantity;
	}

	public void setMealQuantity(String meal_Quantity) {
		this.mealQuantity = meal_Quantity;
	}
	
	public String getMealId() {
		return mealId;
	}

	public void setMealId(String meal_Id) {
		this.mealId = meal_Id;
	}
	
	public String getMealCode() {
		return mealCode;
	}

	public void setMealCode(String meal_Code) {
		this.mealCode = meal_Code;
	}
	
	public ArrayList<ToppingDetailsBase> getTp() {
		return tp;
	}

	public void setTp(ArrayList<ToppingDetailsBase> tp) {
		this.tp = tp;
	}

}
