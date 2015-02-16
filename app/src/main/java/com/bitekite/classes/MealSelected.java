package com.bitekite.classes;


public class MealSelected {

//	public String meal_description;
	public String meal_id;
	public String price;	
//	public String meal_image;
	public String name;
	public int quantity;
	public int toppingSize;
//	public ArrayList<MealToppings> toppings;
	
	public int getToppingSize() {
		return toppingSize;
	}
	public void setToppingSize(int toppingSize) {
		this.toppingSize = toppingSize;
	}
	public String getMeal_id() {
		return meal_id;
	}
	public void setMeal_id(String meal_id) {
		this.meal_id = meal_id;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}


}
