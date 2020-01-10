package com.example.busy.restaurant;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
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
import com.example.busy.users.Make_Order;
import com.example.busy.users.Uform.Address_form;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HOME_restaurant extends AppCompatActivity implements View.OnClickListener {

    private TextView welcome_txt;
    private ListView activeOrders_listView;
    private ArrayList<OrderForm> activeOrders_list = new ArrayList<>();
    private ArrayAdapter<OrderForm> activeOrders_adapter;
    private String UID;
    private ArrayList<DataSnapshot> all_needed_data = new ArrayList<>();


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
                                if (!rest_id.equals(UID) && !(status.equals("active") || status.equals("unhandled")
                                        || status.equals("seen") || status.equals("preparation") || status.equals("on the way")
                                        || status.equals("received")))
                                    continue;
                                all_needed_data.add(snapshot);
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
                        } else {
                            Toast.makeText(HOME_restaurant.this, "no orders for this rest yet ", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });

        //****WORKING ON IT****//

        activeOrders_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                FirebaseDatabase.getInstance().getReference("Orders")
//                        .addListenerForSingleValueEvent(new ValueEventListener() {
//                            @Override
//                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//                            }
//
//                            @Override
//                            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                            }
//                        });
                switch (activeOrders_list.get(i).getStatus()) {
                    case "unhandled":
                        update_status_DB(activeOrders_list.get(i).getOrder_num(), "seen");
                        activeOrders_list.get(i).setStatus("seen");
                        break;
                    case "seen":
                        update_status_DB(activeOrders_list.get(i).getOrder_num(), "preparation");
                        activeOrders_list.get(i).setStatus("preparation");
                        break;
                    case "preparation":
                        update_status_DB(activeOrders_list.get(i).getOrder_num(), "on the way");
                        activeOrders_list.get(i).setStatus("on the way");
                        break;
                    case "on the way":
                        update_status_DB(activeOrders_list.get(i).getOrder_num(), "received");
                        activeOrders_list.get(i).setStatus("received");
                        break;
                    case "received":
                        update_status_DB(activeOrders_list.get(i).getOrder_num(), "done");
                        activeOrders_list.get(i).setStatus("done");
                        break;
                    case "done":
                        activeOrders_list.remove(i);
                        break;
                }
                activeOrders_adapter = new ArrayAdapter<OrderForm>(HOME_restaurant.this, android.R.layout.simple_list_item_1, activeOrders_list);
                activeOrders_listView.setAdapter(activeOrders_adapter);
            }
        });
        //listener to move to the settings activity when the image is clicked
        ImageView restSettings = findViewById(R.id.rest_settings);
    }

    private void update_status_DB(String order_num, String new_status) {
        FirebaseDatabase.getInstance().getReference("Orders").child(order_num).child("status").setValue(new_status);
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
