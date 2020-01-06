package com.example.busy.users.Uform;

import java.io.Serializable;


@SuppressWarnings("serial")
public class filter_form implements Serializable {
    private String city;
    private String kosher;
    private String type;

    public filter_form() {
        this.city = "Ariel";
        this.kosher = "";
        this.type = "";
    }

    public filter_form(String city, String kosher, String type) {
        this.city = city;
        this.kosher = kosher;
        this.type = type;
    }

    public filter_form(filter_form copy) {
        this.city=copy.city;
        this.kosher=copy.kosher;
        this.type=copy.type;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getKosher() {
        return kosher;
    }

    public void setKosher(String kosher) {
        this.kosher = kosher;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
