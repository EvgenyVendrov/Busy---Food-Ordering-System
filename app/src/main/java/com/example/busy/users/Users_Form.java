package com.example.busy.users;

public class Users_Form {
    private String FirstName;
    private String LastName;
    private String Email;
    private  String Password;

    public Users_Form(String FirstName, String Email, String LastName, String Password){
        this.Email=Email;
        this.FirstName = FirstName;
        this.LastName = LastName;
        this.Password = Password;

    }
    public Users_Form(){}

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }
}
