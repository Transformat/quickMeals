package com.bitekite.classes;

public class SelectedTopping {

	String toppingName,toppingId,toppingPrice;

	public String getToppingName() {
		return toppingName;
	}

	public void setToppingName(String toppingName) {
		this.toppingName = toppingName;
	}

	public String getToppingId() {
		return toppingId;
	}

	public void setToppingId(String toppingId) {
		this.toppingId = toppingId;
	}

	public String getToppingPrice() {
		return toppingPrice;
	}

	public void setToppingPrice(String toppingPrice) {
		this.toppingPrice = toppingPrice;
	}
	
	@Override
	public boolean equals(Object o) {
		try{
			if(((SelectedTopping)o).toppingId.equalsIgnoreCase(this.toppingId)){
				return true;
			}
			else{
				return false;
			}
		} catch(Exception e){
			return false;
		}
	}
	
}
