package com.example.busy.restaurant.Rforms;

public class dish_form {
    private String dish_name;
    private String dish_discription;
    private double price;

    public dish_form(double price,String dish_name, String dish_discription) {
        this.dish_name = dish_name;
        this.dish_discription = dish_discription;
        this.price = price;
    }

    public String getDish_name() {
        return dish_name;
    }

    public String getDish_discription() {
        return dish_discription;
    }

    public double getPrice() {
        return price;
    }
    public boolean check_equal(dish_form dish){
        if (this.getDish_name().equals(dish.getDish_name())){
            return true;
        }
        else {
            return false;
        }
    }
}
