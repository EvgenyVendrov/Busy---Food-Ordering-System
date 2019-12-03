package com.example.busy.restaurant;

public class Restaurant_Form {
    private String restaurant_Name;
    private String email;
    private final String type = "Restaurant";

    public Restaurant_Form(String restaurant_Name, String email){
        this.restaurant_Name=restaurant_Name;
        this.email=email;
    }

    public void setRestaurant_Name(String restaurant_Name) {
        this.restaurant_Name = restaurant_Name;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getRestaurant_Name(){
        return this.restaurant_Name;
    }
    public String getEmail(){
        return this.email;
    }
    public String getType(){
        return this.type;
    }

}
