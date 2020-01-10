package com.example.busy.users;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.busy.R;
import com.example.busy.restaurant.OrderForm.OrderForm;
import com.example.busy.restaurant.Rforms.dish_form;
import com.example.busy.users.Uform.Address_form;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class Place_Order extends AppCompatActivity {
    private CheckBox pay_phone, pay_card;
    private TextView ordernum_view;
    private TextView totslprice_view;
    private Button orderbtn;
    private ListView listview;
    private ArrayList<String> dishes_str = new ArrayList<>();
    private ArrayList<dish_form> dishes = new ArrayList<>();
    private ArrayAdapter<dish_form> addapter;
    private double total_price;
    private OrderForm order;
    private final DatabaseReference ordersdata = FirebaseDatabase.getInstance().getReference("Orders");
    private String City;
    private String Street;
    private String House_num;
    private String Phone_num;
    private boolean bool = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place__order);

        pay_card = findViewById(R.id.pay_card_placeOrder);
        pay_phone = findViewById(R.id.pay_phone_placeOrder);
        pay_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bool = true;
                if (pay_phone.isChecked()) {
                    pay_phone.setChecked(false);
                }
            }
        });
        pay_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bool = false;
                if (pay_card.isChecked()) {
                    pay_card.setChecked(false);
                }
            }
        });


        ordernum_view = findViewById(R.id.orderNum_TV_rest);
        totslprice_view = findViewById(R.id.ordPrice_TV_rest);
        orderbtn = findViewById(R.id.Ord_btn_rest);
        listview = findViewById(R.id.ListView_place_order);
        Intent i = getIntent();
        Bundle extras = i.getBundleExtra("extras");
        final String order_num = extras.getString("order_id");
        String rest_id = extras.getString("rest_id");
        String client_id = extras.getString("client_id");
        String status = extras.getString("status");
        total_price = extras.getDouble("price");
        City = extras.getString("City");
        Street = extras.getString("Street");
        House_num = extras.getString("House_num");
        Phone_num = extras.getString("Phone_num");
        Address_form address = new Address_form(City, Street, House_num, Phone_num);
        dishes_str.addAll(extras.getStringArrayList("dishes"));
        order = new OrderForm(order_num, rest_id, client_id, status, address);
        string_to_dishes_array();
        addapter = new ArrayAdapter<>(Place_Order.this, R.layout.cutsumefont, dishes);
        totslprice_view.setText("price: " + Double.toString(total_price));
        ordernum_view.setText("order number" + order_num.replaceAll("[^0-9]", ""));
        listview.setAdapter(addapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                order.removeDish(dishes.get(i));
                dishes.remove(i);
                addapter = new ArrayAdapter<>(Place_Order.this, R.layout.cutsumefont, dishes);
                listview.setAdapter(addapter);
                total_price = order.getTotal_price();
                totslprice_view.setText("price: " + total_price);
            }
        });

        orderbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ordersdata.child(order_num).setValue(order);
                if (bool == false) {
                    Intent i = new Intent(Place_Order.this, OrderPage.class);
                    startActivity(i);
                    finish();
                } else {
                    Intent j = new Intent(Place_Order.this, pay_by_creditCard.class);
                    startActivity(j);
                }
            }
        });

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        finish();
    }

    private void string_to_dishes_array() {
        for (int j = 0; j < dishes_str.size(); j++) {
            String[] split = dishes_str.get(j).split(" ");
            String name = split[0];
            double price = Double.parseDouble(split[2].split(",")[0]);
            String desc = "";
            if (split.length >= 5) {
                desc = split[4];
            }
            dish_form dish = new dish_form(price, name, desc);
            dishes.add(dish);
        }
        for (int i = 0; i < dishes.size(); i++) {
            order.addDish(dishes.get(i));
        }
    }
}
