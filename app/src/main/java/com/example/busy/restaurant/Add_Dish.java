package com.example.busy.restaurant;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.example.busy.R;
import com.example.busy.restaurant.Rforms.dish_form;
import com.example.busy.restaurant.Rforms.menu_form;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Add_Dish extends AppCompatActivity implements View.OnClickListener {
    private TextView name;
    private TextView price;
    private TextView description;
    private Button add;
    private CheckBox starter, main, drink, desert;
    private DatabaseReference menus;
    private final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private boolean menu_exist;
    private boolean menu_empty;
    private menu_form arr;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__dish);
        name = (TextView) findViewById(R.id.dish_name);
        price = (TextView) findViewById(R.id.dish_price);
        description = (TextView) findViewById(R.id.dish_desc);
        add = (Button) findViewById(R.id.add_button);
        starter = (CheckBox) findViewById(R.id.starters_box);
        main = (CheckBox) findViewById(R.id.mains_box);
        drink = (CheckBox) findViewById(R.id.drink_box);
        desert = (CheckBox) findViewById(R.id.deserts_box);
        menu_empty = false;
        menu_exist = false;
        menus = FirebaseDatabase.getInstance().getReference("menus");
        arr = new menu_form();
        get_menu();

        add.setOnClickListener(new View.OnClickListener() { //add button function
            @Override
            public void onClick(View view) {
                String namestr = name.getText().toString().trim();
                String desc = description.getText().toString().trim();
                String pricestr = price.getText().toString().trim();
                double price_doub;
                if (pricestr.isEmpty()) { //chek if there is a price
                    price.setError("price filed is empty");
                    price.requestFocus();
                    return;
                } else {
                    price_doub = Double.parseDouble(pricestr);
                }
                if (namestr.isEmpty()) { //chek if there is a name to the dish
                    name.setError("dish name is empty");
                    name.requestFocus();
                    return;
                }
                dish_form dish = new dish_form(price_doub, namestr, desc);
                if (menu_exist == true) {
                    add_dish_toexist_menu(dish);

                } else if (menu_empty == true) {
                    write_menu_indata(dish);
                }
            }
        });

    }

    private void write_menu_indata(dish_form dish) { //write the first dish and create a menu in data base
        boolean flag = true; //chek if one of the box is cheked
        if (starter.isChecked()) {
            arr.add_start_dish(dish);

        } else if (main.isChecked()) {
            arr.add_main_dish(dish);
        } else if (drink.isChecked()) {
            arr.add_drink(dish);

        } else if (desert.isChecked()) {
            arr.add_desert_dish(dish);

        } else { //if the client dident chek any box
            desert.setError("please choose dish type");
            desert.requestFocus();
            drink.requestFocus();
            main.requestFocus();
            starter.requestFocus();
            flag = false;

        }
        if (flag == true) { //if one of the boxs is check add to arr
            menus.child(user.getUid()).setValue(arr);
            menu_empty = false; //now there is a menu in database
            menu_exist = true;
        }
    }

    private void add_dish_toexist_menu(dish_form dish) { //add a dish to exist menu in database with the chosen chekbox
        if (arr.exist(dish)) { //if the dish is alredy exist dont add it
            Toast.makeText(Add_Dish.this, "this dish is alredy on the menu", Toast.LENGTH_SHORT).show();
        } else {
            if (starter.isChecked()) { //add to starter menu
                arr.add_start_dish(dish);
                menus.child(user.getUid()).child("starters_list").push().setValue(dish);
                Toast.makeText(Add_Dish.this, "dish added", Toast.LENGTH_SHORT).show();


            } else if (main.isChecked()) { //add to main menu
                arr.add_main_dish(dish);
                menus.child(user.getUid()).child("main_list").push().setValue(dish);
                Toast.makeText(Add_Dish.this, "dish added", Toast.LENGTH_SHORT).show();

            } else if (drink.isChecked()) { //add to drink menu
                arr.add_drink(dish);
                menus.child(user.getUid()).child("drink_list").push().setValue(dish);
                Toast.makeText(Add_Dish.this, "dish added", Toast.LENGTH_SHORT).show();


            } else if (desert.isChecked()) { //add to desert menu
                arr.add_desert_dish(dish);
                menus.child(user.getUid()).child("deserts_list").push().setValue(dish);
                Toast.makeText(Add_Dish.this, "dish added", Toast.LENGTH_SHORT).show();

            } else { //if the client dident chek any box
                Toast.makeText(Add_Dish.this, "please select dish type", Toast.LENGTH_SHORT).show();

                desert.requestFocus();
                drink.requestFocus();
                main.requestFocus();
                starter.requestFocus();
            }
        }
    }


    public void get_menu() { //get menu from database
        menus.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists() && dataSnapshot.child(user.getUid()).exists()) { //if there is a data in menu and the user have a menu
                    DataSnapshot user_menu = dataSnapshot.child(user.getUid()); //user_menu get the menu data from database
                    if (user_menu.child("main_list").exists()) { //if there is a main_list menu in database add get it to arr
                        add_mainlist_menu_toarr(user_menu);
                    }
                    if (user_menu.child("starters_list").exists()) { //if there is a starters_list menu in database then add it to arr
                        add_starterslist_menu_toarr(user_menu);
                    }
                    if (user_menu.child("drink_list").exists()) { //if there is a frink_list menu in database then add it to arr
                        add_drinklist_menu_toarr(user_menu);
                    }

                    if (user_menu.child("deserts_list").exists()) { //if there is a deserts_list menu in database then add it to arr
                        add_desertlist_menu_toarr(user_menu);
                    }

                    menu_exist = true;

                } else { //if there is no menu then create an emty one
                    arr = new menu_form();
                    menu_empty = true;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                databaseError.getMessage();

            }
        });
    }

    private void add_desertlist_menu_toarr(DataSnapshot dt) { //this function add all the deserts in the user menu in database to arr
        DataSnapshot maindata = dt.child("deserts_list"); //maindata = deserts menu of the current in database
        for (DataSnapshot childdata : maindata.getChildren()) { //loop on all the dishes in desert menu of the user
            String name = childdata.child("dish_name").getValue(String.class); //name of the dish
            String desc = childdata.child("dish_discription").getValue(String.class); //discription of the dish
            double price = childdata.child("price").getValue(double.class); //price of the dish
            dish_form dish = new dish_form(price, name, desc);
            arr.add_desert_dish(dish); //add dish to arr disertt menu
        }
    }

    private void add_drinklist_menu_toarr(DataSnapshot dt) { //same function as: same as add_desertlist_menu_toarr with drink dish
        DataSnapshot maindata = dt.child("drink_list");
        for (DataSnapshot childdata : maindata.getChildren()) {
            String name = childdata.child("dish_name").getValue(String.class);
            String desc = childdata.child("dish_discription").getValue(String.class);
            double price = childdata.child("price").getValue(double.class);
            dish_form dish = new dish_form(price, name, desc);
            arr.add_drink(dish);
        }
    }

    private void add_starterslist_menu_toarr(DataSnapshot dt) { //same function as: add_desertlist_menu_toarr with starter dish
        DataSnapshot maindata = dt.child("starters_list");
        for (DataSnapshot childdata : maindata.getChildren()) {
            String name = childdata.child("dish_name").getValue(String.class);
            String desc = childdata.child("dish_discription").getValue(String.class);
            double price = childdata.child("price").getValue(double.class);
            dish_form dish = new dish_form(price, name, desc);
            arr.add_start_dish(dish);
        }
    }

    private void add_mainlist_menu_toarr(DataSnapshot dt) { // same function as: add_desertlist_menu_toarr with main dish
        DataSnapshot maindata = dt.child("main_list");
        for (DataSnapshot childdata : maindata.getChildren()) {
            String name = childdata.child("dish_name").getValue(String.class);
            String desc = childdata.child("dish_discription").getValue(String.class);
            double price = childdata.child("price").getValue(double.class);
            dish_form dish = new dish_form(price, name, desc);
            arr.add_main_dish(dish);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }


    @Override
    public void onClick(View v) { //make sure only one chekbox will be chek
        switch (v.getId()) {
            case R.id.starters_box:
                if (main.isChecked()) {
                    main.setChecked(false);
                }
                if (desert.isChecked()) {
                    desert.setChecked(false);
                }
                if (drink.isChecked()) {
                    drink.setChecked(false);
                }
                break;
            case R.id.deserts_box:
                if (main.isChecked()) {
                    main.setChecked(false);
                }
                if (starter.isChecked()) {
                    starter.setChecked(false);
                }
                if (drink.isChecked()) {
                    drink.setChecked(false);
                }
                break;
            case R.id.drink_box:
                if (main.isChecked()) {
                    main.setChecked(false);
                }
                if (desert.isChecked()) {
                    desert.setChecked(false);
                }
                if (starter.isChecked()) {
                    starter.setChecked(false);
                }
                break;
            case R.id.mains_box:
                if (starter.isChecked()) {
                    starter.setChecked(false);
                }
                if (desert.isChecked()) {
                    desert.setChecked(false);
                }
                if (drink.isChecked()) {
                    drink.setChecked(false);
                }
                break;

        }
    }


}
