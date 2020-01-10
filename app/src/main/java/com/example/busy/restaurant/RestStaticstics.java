package com.example.busy.restaurant;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.TextView;
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

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RestStaticstics extends AppCompatActivity {
    private TextView num_of_orders_tot;
    private TextView num_of_uniq_users;
    private TextView tot_mon_earned;
    private TextView avg_ord_price;
    private TextView most_ord_dish;
    private String UID = FirebaseAuth.getInstance().getCurrentUser().getUid();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rest_staticstics);

        num_of_orders_tot = findViewById(R.id.howMuchOrd_rest_TV);
        num_of_uniq_users = findViewById(R.id.uniOrdUsers_rest_TV);
        tot_mon_earned = findViewById(R.id.totMon_rest_TV);
        avg_ord_price = findViewById(R.id.avgOrdPrice_rest_TV);
        most_ord_dish = findViewById(R.id.mostOrdDish_rest_TV);

        find_tot_ord_number();
        find_uniq_users_number();
        find_tot_mon_earned();
        find_avg_ord_price();
        find_most_ord_dish();
    }

    private void find_most_ord_dish() {
        FirebaseDatabase.getInstance().getReference("Orders")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Map<String, Integer> dish_amount_kv = new HashMap<>();
                        if (dataSnapshot.exists()) {
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                String rest_id = snapshot.child("rest_id").getValue(String.class);
                                String status = snapshot.child("status").getValue(String.class);
                                if (!rest_id.equals(UID) || !(status.equals("done")))
                                    continue;
                                for (DataSnapshot snapshot_dish : snapshot.child("dishs_orderd").getChildren()) {
                                    double price = snapshot_dish.child("price").getValue(double.class);
                                    String dish_name = snapshot_dish.child("dish_name").getValue(String.class);
                                    String dish_desc = snapshot_dish.child("dish_discription").getValue(String.class);
                                    dish_form curr_dish = new dish_form(price, dish_name, dish_desc);
                                    if (dish_amount_kv.containsKey(curr_dish.getDish_name())) {
                                        dish_amount_kv.replace(curr_dish.getDish_name(), dish_amount_kv.get(curr_dish.getDish_name()) + 1);
                                    } else {
                                        dish_amount_kv.put(curr_dish.getDish_name(), 1);
                                    }
                                }
                            }
                            Map.Entry<String, Integer> maxEntry = null;
                            for (Map.Entry<String, Integer> entry : dish_amount_kv.entrySet()) {
                                if (maxEntry == null || entry.getValue().compareTo(maxEntry.getValue()) > 0) {
                                    maxEntry = entry;
                                }
                            }
                            most_ord_dish.setText("most ordered dish: " + maxEntry.getKey() + "," + maxEntry.getValue() + " times");
                        } else {
                            avg_ord_price.setText("average coast of order: " + "NONE");
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
    }

    private void find_avg_ord_price() {
        FirebaseDatabase.getInstance().getReference("Orders")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        double tot_money_earned = 0;
                        int tot_ords = 0;
                        if (dataSnapshot.exists()) {
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                String rest_id = snapshot.child("rest_id").getValue(String.class);
                                String status = snapshot.child("status").getValue(String.class);
                                if (!rest_id.equals(UID) || !(status.equals("done")))
                                    continue;
                                tot_ords++;
                                double curr_tot = snapshot.child("total_price").getValue(double.class);
                                tot_money_earned += curr_tot;
                            }
                            avg_ord_price.setText("average coast of order: " + tot_money_earned / tot_ords);
                        } else {
                            avg_ord_price.setText("average coast of order: " + "" + 0);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
    }

    private void find_tot_mon_earned() {
        FirebaseDatabase.getInstance().getReference("Orders")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        double tot_money_earned = 0;
                        if (dataSnapshot.exists()) {
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                String rest_id = snapshot.child("rest_id").getValue(String.class);
                                String status = snapshot.child("status").getValue(String.class);
                                String client_id = snapshot.child("client_id").getValue(String.class);
                                if (!rest_id.equals(UID) || !(status.equals("done")))
                                    continue;

                                double curr_tot = snapshot.child("total_price").getValue(double.class);
                                tot_money_earned += curr_tot;
                            }
                            tot_mon_earned.setText("total money earned: " + tot_money_earned);
                        } else {
                            tot_mon_earned.setText("total money earned: " + "" + 0);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
    }

    private void find_uniq_users_number() {
        FirebaseDatabase.getInstance().getReference("Orders")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        ArrayList<String> uniq_users_uid = new ArrayList<>();
                        if (dataSnapshot.exists()) {
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                String rest_id = snapshot.child("rest_id").getValue(String.class);
                                String status = snapshot.child("status").getValue(String.class);
                                String client_id = snapshot.child("client_id").getValue(String.class);
                                if (!rest_id.equals(UID) || !(status.equals("done")))
                                    continue;
                                if (!uniq_users_uid.contains(client_id)) {
                                    uniq_users_uid.add(client_id);
                                }
                            }
                            num_of_uniq_users.setText("number of unique users ordered: " + uniq_users_uid.size());
                        } else {
                            num_of_uniq_users.setText("number of unique users ordered: " + "" + 0);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
    }

    private void find_tot_ord_number() {
        FirebaseDatabase.getInstance().getReference("Orders")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        int totOrdNum = 0;
                        if (dataSnapshot.exists()) {
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                String rest_id = snapshot.child("rest_id").getValue(String.class);
                                String status = snapshot.child("status").getValue(String.class);
                                if (!rest_id.equals(UID) || !(status.equals("done")))
                                    continue;
                                totOrdNum++;
                            }
                            num_of_orders_tot.setText("number of total orders: " + totOrdNum);
                        } else {
                            num_of_orders_tot.setText("number of total orders: " + "" + 0);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
    }
}
