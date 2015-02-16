package com.bitekite.driver.classes;

public class ToppingDetailsBase {

	String topping_name,topping_image,topping_quantity,topping_id;
//	  "topping_name": "potato chips",
//    "topping_image": "http://uberformeal.s3.amazonaws.com/topping_image/images12.jpeg",
//    "topping_quantity": 1

	public String getTopping_name() {
		return topping_name;
	}

	public void setTopping_name(String topping_name) {
		this.topping_name = topping_name;
	}

	public String getTopping_image() {
		return topping_image;
	}

	public void setTopping_image(String topping_image) {
		this.topping_image = topping_image;
	}

	public String getTopping_quantity() {
		return topping_quantity;
	}

	public void setTopping_quantity(String topping_quantity) {
		this.topping_quantity = topping_quantity;
	}
	
	public String getTopping_id() {
		return topping_id;
	}

	public void setTopping_id(String topping_id) {
		this.topping_id = topping_id;
	}
	
}
