package com.example.busy.restaurant;

public class Restaurant_Form {
    private String restaurant_Name;
    private String email;
    private String password;
    private String address;
    private final String type = "Restaurant";

    public Restaurant_Form(String restaurant_Name, String Email, String password, String address){
        this.restaurant_Name=restaurant_Name;
        this.email=Email;
        this.password=password;
        this.address=address;
    }

    public void setRestaurant_Name(String restaurant_Name) {
        this.restaurant_Name = restaurant_Name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRestaurant_Name(){
        return this.restaurant_Name;
    }
    public String getEmail(){
        return this.email;
    }
    public String getPassword(){
        return this.password;
    }
    public String getType(){
        return this.type;
    }
    public String getAddress(){
        return this.address;
    }

}
