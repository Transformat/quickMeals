package com.bitekite.driver.classes;

import java.util.ArrayList;

public class HistoryBase {

	String customer_name, price, drop_location_address, order_id,
			time_difference, delivered_time,total_meals;

	ArrayList<DetailsBase> details = new ArrayList<DetailsBase>();

	public String getCustomer_name() {
		return customer_name;
	}

	public void setCustomer_name(String customer_name) {
		this.customer_name = customer_name;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getDrop_location_address() {
		return drop_location_address;
	}

	public void setDrop_location_address(String drop_location_address) {
		this.drop_location_address = drop_location_address;
	}

	public String getOrderID() {
		return order_id;
	}

	public void setOrderID(String order_id) {
		this.order_id = order_id;
	}

	public String getTimeDifference() {
		return time_difference;
	}

	public void setTimeDifference(String time_difference) {
		this.time_difference = time_difference;
	}

	public String getDeliveredTime() {
		return delivered_time;
	}

	public void setDeliveredTime(String delivered_time) {
		this.delivered_time = delivered_time;
	}

	public String getTotalMeals() {
		return total_meals;
	}

	public void setTotalMeals(String total_meals) {
		this.total_meals = total_meals;
	}
	
	public ArrayList<DetailsBase> getDetails() {
		return details;
	}

	public void setDetails(ArrayList<DetailsBase> details) {
		this.details = details;
	}
}
