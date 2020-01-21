package com.example.busy.restaurant.Rforms;

public class dish_form {
    private String dish_name;
    private String dish_description;

    private double price;

    public dish_form(double price,String dish_name, String dish_description) {
        this.dish_name = dish_name;
        this.dish_description = dish_description;
        this.price = price;
    }

    public String getDish_name() {
        return dish_name;
    }

    public String getDish_description() {
        return dish_description;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        String str =   dish_name +" , price: " + price +" " + " , description: " + dish_description;
       return  str;
    }

    public boolean check_equal(dish_form dish){
        if (this.getDish_name().equals(dish.getDish_name())){
            return true;
        }
        else {
            return false;
        }
    }
    public String to_string(){
        String str =   dish_name +", price: " + price + ", description: " + dish_description;
        return  str;
    }

    public void setDish_( dish_form temp_dish) {
        this.dish_name = temp_dish.getDish_name();
        this.price = temp_dish.getPrice();
        this.dish_description = temp_dish.dish_description;
    }


}
