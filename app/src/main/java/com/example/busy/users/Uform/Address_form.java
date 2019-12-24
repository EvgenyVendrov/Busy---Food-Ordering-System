package com.example.busy.users.Uform;

public class Address_form {
    private String City;
    private String Street;
    private String House_num;
    private String Phone_num;


    public Address_form(String City, String Street, String House_num, String Phone_num) {
        this.City = City;
        this.Street = Street;
        this.House_num = House_num;
        this.Phone_num = Phone_num;
    }


    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getStreet() {
        return Street;
    }

    public void setStreet(String street) {
        Street = street;
    }

    public String getHouse_num() {
        return House_num;
    }

    public void setHouse_num(String house_num) {
        House_num = house_num;
    }

    public String getPhone_num() {
        return Phone_num;
    }

    public void setPhone_num(String phone_num) {
        Phone_num = phone_num;
    }

}
