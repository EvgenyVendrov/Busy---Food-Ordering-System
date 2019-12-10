package com.example.busy.restaurant;

import com.google.firebase.auth.FirebaseAuth;

public class Restaurant_Form {
    private String Rest_Name;
    private String Owner;
    private String location;
    private String Owner_Uid;
    private String Email;
    private int Current_Order_id;
    private String rest_phone;

    public Restaurant_Form(String rest_Name, String owner, String location, String email, String phone) {
        Rest_Name = rest_Name;
        Owner = owner;
        this.location = location;
        Owner_Uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        Email = email;
        Current_Order_id = 0;
        rest_phone = phone;
    }

    public String getRest_Name() {
        return Rest_Name;
    }

    public String getOwner() {
        return Owner;
    }

    public String getLocation() {
        return location;
    }

    public String getEmail() {
        return Email;
    }

    public int getCurrent_Order_id() {
        return Current_Order_id;
    }
}
