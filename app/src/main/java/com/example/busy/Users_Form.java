package com.example.busy;

public class Users_Form {
    String FirstName;
    String LastName;
    String Email;
    String Type;

    public Users_Form(String FirstName, String Email, String Type, String LastName){
        this.Email=Email;
        this.FirstName=FirstName;
        this.LastName = LastName;
        this.Type=Type;
    }
}
