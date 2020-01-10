package com.example.busy.restaurant;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.busy.R;
import com.example.busy.restaurant.OrderForm.OrderForm;
import com.example.busy.restaurant.Rforms.Restaurant_Form;
import com.example.busy.restaurant.Rforms.dish_form;
import com.example.busy.restaurant.update.rest_update;
import com.example.busy.users.Uform.Address_form;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HOME_restaurant extends AppCompatActivity implements View.OnClickListener {

    private TextView welcome_txt;
    private ListView activeOrders_listView;
    private ArrayList<OrderForm> activeOrders_list = new ArrayList<>();
    private ArrayAdapter<OrderForm> activeOrders_adapter;
    private String UID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_restaurant);

        activeOrders_listView = findViewById(R.id.activeOrders_rest_listView);
        welcome_txt = findViewById(R.id.infoAboutUser_rest_text);
        FirebaseDatabase.getInstance().getReference("Restaurant").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Restaurant_Form curr_user = dataSnapshot.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).getValue(Restaurant_Form.class);
                String rest_name = curr_user.getName();
                UID = FirebaseAuth.getInstance().getCurrentUser().getUid();
                welcome_txt.setText("welcome, " + rest_name + " owner");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        FirebaseDatabase.getInstance().getReference("Orders")
                .addListenerForSingleValueEvent(new ValueEventListener() {
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
                                if (!rest_id.equals(UID) && status.equals("active"))
                                    continue;
                                String order_num = snapshot.child("order_num").getValue(String.class);
                                String client_id = snapshot.child("client_id").getValue(String.class);
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
                            activeOrders_adapter = new ArrayAdapter<OrderForm>(HOME_restaurant.this, android.R.layout.simple_list_item_1, activeOrders_list);
                            activeOrders_listView.setAdapter(activeOrders_adapter);
                            //activeOrders_adapter = new ArrayAdapter<OrderForm>(HOME_restaurant.this, R.layout.cutsumefont, activeOrders_list);
                            //activeOrders_listView.setAdapter(activeOrders_adapter);
                        } else {
                            Toast.makeText(HOME_restaurant.this, "no orders for this rest yet ", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });

        //listener to move to the settings activity when the image is clicked
        ImageView restSettings = findViewById(R.id.rest_settings);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.order_history_rest:
                Intent i = new Intent(HOME_restaurant.this, OrderHistoryRest.class);
                startActivity(i);
                break;
            case R.id.rest_settings:
                Intent j = new Intent(HOME_restaurant.this, rest_update.class);
                startActivity(j);
                break;
        }
    }
}
