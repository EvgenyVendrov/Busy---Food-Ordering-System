package com.example.busy.users;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.busy.R;
import com.example.busy.restaurant.OrderForm.OrderForm;
import com.example.busy.restaurant.Rforms.dish_form;
import com.example.busy.restaurant.Rforms.menu_form;
import com.example.busy.users.Uform.Address_form;
import com.example.busy.users.Uform.Users_Form;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Make_Order extends AppCompatActivity implements View.OnClickListener {
    private CheckBox mainbox;
    private CheckBox startbox;
    private CheckBox desertbox;
    private CheckBox drinkbox;
    private CheckBox editbox;
    private ListView listview;
    private String rest_uid;
    private ArrayList<dish_form> dish_menu = new ArrayList<>();
    private ArrayAdapter<dish_form> dish_addapter;
    private DatabaseReference menu_db = FirebaseDatabase.getInstance().getReference("menus");
    private String user_uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
    private OrderForm order;
    private TextView totalptv;
    private Button placeorderbtn;
    private DatabaseReference users = FirebaseDatabase.getInstance().getReference("Users").child(user_uid);
    private Address_form user_addres;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make__order);
        mainbox = findViewById(R.id.mainDish_CB_rest);
        startbox = findViewById(R.id.entrees_CB_rest);
        drinkbox = findViewById(R.id.drinks_CB_rest);
        desertbox = findViewById(R.id.desserts_CB_rest);
        editbox = findViewById(R.id.editOrd_CB_rest);
        placeorderbtn = findViewById(R.id.placeOrd_btn_rest);
        listview = findViewById(R.id.Listview_Make_Order);
        totalptv = findViewById(R.id.totalOrd_TV_rest);
        Intent i = getIntent();
        rest_uid = (String) i.getSerializableExtra("rest_uid");
        final String ordernum = rest_uid + user_uid + (((int) ((Math.random()) * 10000)));
        users.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    user_addres = dataSnapshot.child("Address").getValue(Address_form.class);
                    order = new OrderForm(ordernum, rest_uid, user_uid, "unhandled", user_addres);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (!editbox.isChecked()) {
                    order.addDish(dish_menu.get(i));
                    totalptv.setText("Total Order: " + order.getTotal_price());
                } else {
                    order.removeDish(dish_menu.get(i));
                    dish_menu.remove(i);
                    dish_addapter = new ArrayAdapter<dish_form>(Make_Order.this, R.layout.cutsumefont, dish_menu);
                    listview.setAdapter(dish_addapter);
                    totalptv.setText("Total price: " + order.getTotal_price());
                }
            }
        });
        placeorderbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ordernum = order.getOrder_num();
                String restid = order.getRest_id();
                String clientid = order.getClient_id();
                String status = order.getStatus();
                String City = order.getUser_address().getCity();
                String Street = order.getUser_address().getStreet();
                String House_num = order.getUser_address().getHouse_num();
                String Phone_num = order.getUser_address().getPhone_num();
                Intent i = new Intent(Make_Order.this, Place_Order.class);
                Bundle extras = new Bundle();
                extras.putString("order_id", ordernum);
                extras.putString("rest_id", restid);
                extras.putString("client_id", clientid);
                extras.putString("status", status);
                extras.putDouble("price", order.getTotal_price());
                extras.putString("City", City);
                extras.putString("Street", Street);
                extras.putString("House_num", House_num);
                extras.putString("Phone_num", Phone_num);
                ArrayList<String> dishes = new ArrayList<>();

                for (int j = 0; j < order.getDishs_orderd().size(); j++) {
                    dishes.add(order.getDishs_orderd().get(j).to_string());
                }

                extras.putStringArrayList("dishes", dishes);
                i.putExtra("extras", extras);
                startActivity(i);
            }
        });
    }

    private void getdata() {
        menu_db.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (!dish_menu.isEmpty()) {
                    dish_menu.clear();
                    dish_addapter.clear();
                }
                if (dataSnapshot.exists() && dataSnapshot.child(rest_uid).exists()) {
                    double price;
                    String name_dish;
                    String desc_dish;
                    DataSnapshot snep = dataSnapshot.child(rest_uid);
                    if (mainbox.isChecked()) {
                        if (snep.child("main_list").exists()) {
                            for (DataSnapshot db : snep.child("main_list").getChildren()) {
                                price = db.child("price").getValue(double.class);
                                name_dish = db.child("dish_name").getValue(String.class);
                                desc_dish = db.child("dish_discription").getValue(String.class);
                                dish_form temp = new dish_form(price, name_dish, desc_dish);
                                dish_menu.add(temp);

                            }
                            dish_addapter = new ArrayAdapter<dish_form>(Make_Order.this, R.layout.cutsumefont, dish_menu);

                        } else {
                            Toast.makeText(Make_Order.this, "there is no dishes in this section", Toast.LENGTH_SHORT).show();
                        }
                    }
                    if (startbox.isChecked()) {

                        if (snep.child("starters_list").exists()) {
                            for (DataSnapshot db : snep.child("starters_list").getChildren()) {
                                price = db.child("price").getValue(double.class);
                                name_dish = db.child("dish_name").getValue(String.class);
                                desc_dish = db.child("dish_discription").getValue(String.class);
                                dish_form temp = new dish_form(price, name_dish, desc_dish);
                                dish_menu.add(temp);

                            }
                            dish_addapter = new ArrayAdapter<dish_form>(Make_Order.this, R.layout.cutsumefont, dish_menu);

                        } else {
                            Toast.makeText(Make_Order.this, "there is no dishes in this section", Toast.LENGTH_SHORT).show();
                        }
                    }
                    if (drinkbox.isChecked()) {

                        if (snep.child("drink_list").exists()) {
                            for (DataSnapshot db : snep.child("drink_list").getChildren()) {
                                price = db.child("price").getValue(double.class);
                                name_dish = db.child("dish_name").getValue(String.class);
                                desc_dish = db.child("dish_discription").getValue(String.class);
                                dish_form temp = new dish_form(price, name_dish, desc_dish);
                                dish_menu.add(temp);

                            }
                            dish_addapter = new ArrayAdapter<dish_form>(Make_Order.this, R.layout.cutsumefont, dish_menu);

                        } else {
                            Toast.makeText(Make_Order.this, "there is no dishes in this section", Toast.LENGTH_SHORT).show();
                        }
                    }
                    if (desertbox.isChecked()) {

                        if (snep.child("deserts_list").exists()) {
                            for (DataSnapshot db : snep.child("deserts_list").getChildren()) {
                                price = db.child("price").getValue(double.class);
                                name_dish = db.child("dish_name").getValue(String.class);
                                desc_dish = db.child("dish_discription").getValue(String.class);
                                dish_form temp = new dish_form(price, name_dish, desc_dish);
                                dish_menu.add(temp);

                            }
                            dish_addapter = new ArrayAdapter<dish_form>(Make_Order.this, R.layout.cutsumefont, dish_menu);

                        } else {
                            Toast.makeText(Make_Order.this, "there is no dishes in this section", Toast.LENGTH_SHORT).show();
                        }
                    }
                    if (editbox.isChecked()) {
                        if (!order.getDishs_orderd().isEmpty()) {
                            dish_menu.addAll(order.getDishs_orderd());
                            dish_addapter = new ArrayAdapter<dish_form>(Make_Order.this, R.layout.cutsumefont, dish_menu);
                        } else {
                            Toast.makeText(Make_Order.this, "you havnt chose dishes to order", Toast.LENGTH_SHORT).show();

                        }
                    }

                    listview.setAdapter(dish_addapter);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onClick(View v) { //make sure only one chekbox will be chek
        switch (v.getId()) {
            case R.id.entrees_CB_rest:
                if (mainbox.isChecked()) {
                    mainbox.setChecked(false);
                }
                if (desertbox.isChecked()) {
                    desertbox.setChecked(false);
                }
                if (drinkbox.isChecked()) {
                    drinkbox.setChecked(false);
                }
                if (editbox.isChecked()) {
                    editbox.setChecked(false);
                }
                break;
            case R.id.desserts_CB_rest:
                if (mainbox.isChecked()) {
                    mainbox.setChecked(false);
                }
                if (startbox.isChecked()) {
                    startbox.setChecked(false);
                }
                if (drinkbox.isChecked()) {
                    drinkbox.setChecked(false);
                }
                if (editbox.isChecked()) {
                    editbox.setChecked(false);
                }
                break;
            case R.id.drinks_CB_rest:
                if (mainbox.isChecked()) {
                    mainbox.setChecked(false);
                }
                if (desertbox.isChecked()) {
                    desertbox.setChecked(false);
                }
                if (startbox.isChecked()) {
                    startbox.setChecked(false);
                }
                if (editbox.isChecked()) {
                    editbox.setChecked(false);
                }
                break;
            case R.id.mainDish_CB_rest:
                if (startbox.isChecked()) {
                    startbox.setChecked(false);
                }
                if (desertbox.isChecked()) {
                    desertbox.setChecked(false);
                }
                if (drinkbox.isChecked()) {
                    drinkbox.setChecked(false);
                }
                if (editbox.isChecked()) {
                    editbox.setChecked(false);
                }
                break;
            case R.id.editOrd_CB_rest:
                if (startbox.isChecked()) {
                    startbox.setChecked(false);
                }
                if (desertbox.isChecked()) {
                    desertbox.setChecked(false);
                }
                if (drinkbox.isChecked()) {
                    drinkbox.setChecked(false);
                }
                if (mainbox.isChecked()) {
                    mainbox.setChecked(false);
                }
        }
        getdata();
    }
}
