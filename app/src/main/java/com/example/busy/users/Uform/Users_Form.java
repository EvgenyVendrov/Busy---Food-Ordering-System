package com.example.busy.users.Uform;

public class Users_Form {
    private String FirstName;
    private String LastName;
    private String Email;
    private String Password;
    private String owner;
    private Address_form Address;

    public Users_Form(String FirstName, String Email, String LastName, String Password, String owner) {
        this.Email = Email;
        this.FirstName = FirstName;
        this.LastName = LastName;
        this.Password = Password;
        this.owner = owner;
        this.Address = new Address_form();

    }

    public Users_Form() {
        this.Address = new Address_form();
        this.owner = "";
        this.Email = "";
        this.FirstName = "";
        this.LastName = "";
        this.Password = "";
    }

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

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public Address_form getAddress() {
        return Address;
    }

    public void setAddress(Address_form address) {
        Address = new Address_form(address);
    }
}
