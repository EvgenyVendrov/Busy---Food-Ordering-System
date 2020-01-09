package com.example.busy.restaurant.OrderForm;

import com.example.busy.restaurant.Rforms.dish_form;

import java.util.ArrayList;

public class OrderForm {

    private String order_num;
    private String rest_id;
    private String client_id;
    private String status;
    private double total_price = 0;
    private ArrayList<dish_form> dishs_orderd = new ArrayList<dish_form>();

    public OrderForm(String order_num, String rest_id, String client_id, String status) {
        this.client_id = client_id;
        this.order_num = order_num;
        this.rest_id = rest_id;
        this.status = status;
    }

    public OrderForm(OrderForm ord) {
        this.client_id = ord.client_id;
        this.order_num = ord.order_num;
        this.rest_id = ord.rest_id;
        this.status = ord.status;
    }

    public void addDish(dish_form dish) {
        this.dishs_orderd.add(dish);
        total_price += dish.getPrice();
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


}
