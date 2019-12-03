package com.example.busy;

public class Users_Form {
    public String FirstName;
    public String LastName;
    public String Email;
    public final String Type = "User";

    public Users_Form(String FirstName, String Email, String LastName){
        this.Email=Email;
        this.FirstName=FirstName;
        this.LastName = LastName;

    }
}
