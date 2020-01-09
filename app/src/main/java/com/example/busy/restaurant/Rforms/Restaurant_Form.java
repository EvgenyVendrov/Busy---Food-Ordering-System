package com.example.busy.restaurant.Rforms;

public class Restaurant_Form {
    private String name;
    private String location;
    private String phone;
    private String type;
    private String description;
    private String kosher;
    private String UID;


    public Restaurant_Form(String Name, String Location, String Phone, String UID) {
        this.name = Name;
        this.location = Location;
        this.phone = Phone;
        this.UID = UID;
        this.type = "";
        this.description = "";
        this.kosher = "";
    }

    public Restaurant_Form(String Name, String Location, String Phone, String type, String desc, String kosher) {
        this.name = Name;
        this.location = Location;
        this.phone = Phone;
        this.type = type;
        this.description = desc;
        this.kosher = kosher;
    }

    public Restaurant_Form() {
        this.name = "";
        this.location = "";
        this.phone = "";
        this.type = "";
        this.description = "";
        this.kosher = "";
    }

    public Restaurant_Form(Restaurant_Form toCopy) {
        this.name = toCopy.getName();
        this.location = toCopy.getLocation();
        this.phone = toCopy.getPhone();
        this.type = toCopy.getType();
        this.description = toCopy.getDescription();
        this.kosher = toCopy.getKosher();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getKosher() {
        return kosher;
    }

    public void setKosher(String kosher) {
        this.kosher = kosher;
    }

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }
}
