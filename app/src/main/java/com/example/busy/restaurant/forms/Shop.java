package com.example.busy.restaurant.forms;

import java.util.ArrayList;

public class Shop {
    private int order_id;
    private ArrayList<Orders> orders;

    public Shop(){
        orders = new ArrayList<>();
        this.order_id=0;
    }

    public int getOrder_id() {
        return order_id;
    }

    public ArrayList<Orders> getorders() {
        return orders;
    }

    public Boolean add(Orders order){
        order_id++;
        return getorders().add(order);
    }

    public Boolean remove(Orders order){
        order_id--;
        return getorders().remove(order);
    }
}
