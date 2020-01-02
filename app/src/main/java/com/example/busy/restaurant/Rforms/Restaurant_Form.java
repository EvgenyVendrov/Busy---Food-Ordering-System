package com.example.busy.restaurant.Rforms;

public class Restaurant_Form {
    private String Name;
    private String Location;
    private String Phone;
    private String Type;
    private String Description;
    private String Kosher;


    public Restaurant_Form(String Name, String Location, String Phone) {
        this.Name = Name;
        this.Location = Location;
        this.Phone = Phone;
        this.Type = "";
        this.Description="";
        this.Kosher="";
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

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getKosher() {
        return Kosher;
    }

    public void setKosher(String kosher) {
        Kosher = kosher;
    }
}
