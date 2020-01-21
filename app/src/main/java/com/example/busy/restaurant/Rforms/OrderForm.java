package com.example.busy.restaurant.Rforms;

import com.example.busy.users.Uform.Address_form;

import java.util.ArrayList;

public class OrderForm {

    private String order_num;
    private String rest_id;
    private String client_id;
    private String status;
    private double total_price = 0;
    private Address_form user_address;
    private ArrayList<dish_form> dishs_orderd = new ArrayList<dish_form>();

    public OrderForm(String order_num, String rest_id, String client_id, String status, Address_form users_add) {
        this.client_id = client_id;
        this.order_num = order_num;
        this.rest_id = rest_id;
        this.status = status;
        user_address = new Address_form(users_add.getCity(), users_add.getStreet(), users_add.getHouse_num(), users_add.getPhone_num());
    }

    public OrderForm(OrderForm ord) {
        this.client_id = ord.client_id;
        this.order_num = ord.order_num;
        this.rest_id = ord.rest_id;
        this.status = ord.status;
        this.user_address = new Address_form(ord.user_address);
    }

    public void addDish(dish_form dish) {
        this.dishs_orderd.add(dish);
        total_price += dish.getPrice();
    }


    @Override
    public String toString() {
        String strToRet = "Status: " + status.toUpperCase() + ", " + "\n" +
                "Order Number: " + order_num.replaceAll("[^0-9]", "") + ", " + "\n" +
                "Client Phone: " + user_address.getPhone_num() + ", " + "\n" +
                "Client Address: " + user_address.getStreet() + ", " + user_address.getHouse_num() + ", " + user_address.getCity() + "\n";
        strToRet += "Dishes: ";
        for (dish_form dish : dishs_orderd) {
            strToRet += dish.getDish_name();
        }
        strToRet += "\n";
        strToRet += "Total Price: " + total_price + "\n";
        return strToRet;
    }

    public void removeDish(dish_form dish) {
        this.dishs_orderd.remove(dish);
        total_price -= dish.getPrice();
    }

    public String getClient_id() {
        return client_id;
    }

    public String getOrder_num() {
        return order_num;
    }

    public String getRest_id() {
        return rest_id;
    }

    public String getStatus() {
        return status;
    }

    public ArrayList<dish_form> getDishs_orderd() {
        return dishs_orderd;
    }

    public double getTotal_price() {
        return total_price;
    }

    public Address_form getUser_address() {
        return user_address;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
