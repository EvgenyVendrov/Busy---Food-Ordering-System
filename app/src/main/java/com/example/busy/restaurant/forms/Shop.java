package com.example.busy.restaurant.forms;

import com.example.busy.restaurant.forms.Orders;

import java.util.ArrayList;

public class Shop {
    private int order_id;
    private ArrayList<Orders> _Orders;

    public Shop(){
        _Orders = new ArrayList<>();
        this.order_id=0;
    }

    public int getOrder_id() {
        return order_id;
    }

    public ArrayList<Orders> get_Orders() {
        return _Orders;
    }

    public Boolean add(Orders order){
        order_id++;
        return get_Orders().add(order);
    }

    public Boolean remove(Orders order){
        order_id--;
        return get_Orders().remove(order);
    }
}