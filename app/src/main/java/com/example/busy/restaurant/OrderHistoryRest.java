package com.example.busy.restaurant;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.busy.R;
import com.example.busy.restaurant.OrderForm.OrderForm;
import com.example.busy.restaurant.Rforms.dish_form;
import com.example.busy.users.Uform.Address_form;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class OrderHistoryRest extends AppCompatActivity implements View.OnClickListener {

    private ArrayList<OrderForm> activeOrders_list = new ArrayList<>();
    private ArrayAdapter<OrderForm> activeOrders_adapter;
    private String UID =  FirebaseAuth.getInstance().getCurrentUser().getUid();
    private ListView activeOrders_listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history_rest);

        activeOrders_listView = findViewById(R.id.OrderHis_LV_rest);

        FirebaseDatabase.getInstance().getReference("Orders")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (!activeOrders_list.isEmpty()) {
                            activeOrders_list.clear();
                            activeOrders_adapter.clear();
                            activeOrders_listView.clearAnimation();
                        }
                        if (dataSnapshot.exists()) {
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                String rest_id = snapshot.child("rest_id").getValue(String.class);
                                String status = snapshot.child("status").getValue(String.class);
                                String client_id = snapshot.child("client_id").getValue(String.class);
                                if (!rest_id.equals(UID) || !(status.equals("done")))
                                    continue;
                                String order_num = snapshot.child("order_num").getValue(String.class);
                                Address_form users_add = snapshot.child("user_address").getValue(Address_form.class);
                                OrderForm curr_order = new OrderForm(order_num, rest_id, client_id, status, users_add);
                                for (DataSnapshot snapshot_dish : snapshot.child("dishs_orderd").getChildren()) {
                                    double price = snapshot_dish.child("price").getValue(double.class);
                                    String dish_name = snapshot_dish.child("dish_name").getValue(String.class);
                                    String dish_desc = snapshot_dish.child("dish_discription").getValue(String.class);
                                    dish_form curr_dish = new dish_form(price, dish_name, dish_desc);
                                    curr_order.addDish(curr_dish);
                                }
                                activeOrders_list.add(curr_order);
                            }
                            activeOrders_adapter = new ArrayAdapter<OrderForm>(OrderHistoryRest.this, android.R.layout.simple_list_item_1, activeOrders_list);
                            activeOrders_listView.setAdapter(activeOrders_adapter);
                        } else {
                            Toast.makeText(OrderHistoryRest.this, "no orders for this rest yet ", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.stats_btn_rest:
                Intent i = new Intent(OrderHistoryRest.this, RestStaticstics.class);
                startActivity(i);
        }
    }
}
