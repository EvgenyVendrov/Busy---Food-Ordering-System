package com.example.busy.restaurant.Rforms;

public class Restaurant_Form {
    private String Name;
    private String Owner;
    private String Location;
    private String Email;
    private String Phone;
    private Shop shop;

    public Restaurant_Form(String Name, String Owner, String Location, String Email, String Phone) {
        this.Name = Name;
        this.Owner = Owner;
        this.Location = Location;
        this.Email = Email;
        this.Phone = Phone;
        shop = new Shop();
    }

    public String getRest_Name() {
        return Name;
    }

    public String getOwner() {
        return Owner;
    }

    public String getLocation() {
        return Location;
    }

    public String getEmail() {
        return Email;
    }

    public Shop getShop() {
        return shop;
    }

}
