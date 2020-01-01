package com.example.busy.restaurant.Rforms;

public class Restaurant_Form {
    private String Name;
    private String Location;
    private String Phone;
    private String type;


    public Restaurant_Form(String Name, String Location, String Phone) {
        this.Name = Name;
        this.Location = Location;
        this.Phone = Phone;
        this.type = "";

    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }
}
