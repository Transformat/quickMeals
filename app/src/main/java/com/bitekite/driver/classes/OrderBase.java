package com.bitekite.driver.classes;

import java.util.ArrayList;

public class OrderBase {

	String customer_name,price,drop_location_address,orderID,deliveryTime,dropLatitude,dropLongitude,distance,time_to_deliver,total_meals,phone_no;
	public String getPhone_no() {
		return phone_no;
	}

	public void setPhone_no(String phone_no) {
		this.phone_no = phone_no;
	}

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
		return orderID;
	}

	public void setOrderID(String orderID) {
		this.orderID = orderID;
	}

	public String getDeliveryTime() {
		return deliveryTime;
	}

	public void setDeliveryTime(String deliveryTime) {
		this.deliveryTime = deliveryTime;
	}

	public String getDropLatitude() {
		return dropLatitude;
	}

	public void setDropLatitude(String dropLatitude) {
		this.dropLatitude = dropLatitude;
	}

	public String getDropLongitude() {
		return dropLongitude;
	}

	public void setDropLongitude(String dropLongitude) {
		this.dropLongitude = dropLongitude;
	}
	
	public String getDistance() {
		return distance;
	}

	public void setDistance(String distance) {
		this.distance = distance;
	}
	
	public String getTimeToDeliver() {
		return time_to_deliver;
	}

	public void setTimeToDeliver(String time_to_deliver) {
		this.time_to_deliver = time_to_deliver;
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
