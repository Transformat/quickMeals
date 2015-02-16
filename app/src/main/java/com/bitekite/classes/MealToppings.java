package com.bitekite.classes;

public class MealToppings {
	
	public String price;
	public String topping_id;
	public String topping_description;
	public String stock_available;
	public String topping_image;
	public String name;
	
	
	public MealToppings(String price, String topping_id,
			String topping_description, String stock_available,
			String topping_image, String name) {
		
		this.price = price;
		this.topping_id = topping_id;
		this.topping_description = topping_description;
		this.stock_available = stock_available;
		this.topping_image = topping_image;
		this.name = name;
	}

}
