package com.bitekite.classes;

import java.util.ArrayList;

public class MealsData {

    public String meal_description;
    public String meal_id;
    public String price;
    public String stock_available;
    public String meal_image;
    public String name;
    public String meal_details;
    public String chefName;
    public String chefDescription;
    public String chefImage;
    public String chefPost;
    public String chefFullDetails;
    public String chefFullImage;
    public ArrayList<MealToppings> toppings;
    public int toppingSize;


    public MealsData(String meal_description, String meal_id, String price,
                     String stock_available, String meal_image, String name, String meal_details,
                     ArrayList<MealToppings> toppings, int toppingSize,String chefName,String chefDescription,String chefImage,String chefPost,
                     String chefFullDetails,String chefFullImage ) {

        this.meal_description = meal_description;
        this.meal_id = meal_id;
        this.price = price;
        this.stock_available = stock_available;
        this.meal_image = meal_image;
        this.name = name;
        this.meal_details = meal_details;
        this.toppings = toppings;
        this.toppingSize = toppingSize;
        this.chefName = chefName;
        this.chefDescription = chefDescription;
        this.chefImage = chefImage;
        this.chefPost=chefPost;
        this.chefFullDetails=chefFullDetails;
        this.chefFullImage=chefFullImage;
    }

}
