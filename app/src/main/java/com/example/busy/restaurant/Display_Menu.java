package com.example.busy.restaurant;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import com.example.busy.R;
import com.example.busy.restaurant.Rforms.addapter_display_menu;
import com.example.busy.restaurant.Rforms.dish_form;
import com.example.busy.restaurant.Rforms.menu_form;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Display_Menu extends AppCompatActivity implements View.OnClickListener {
    private CheckBox start_box;
    private CheckBox main_box;
    private CheckBox desert_box;
    private CheckBox drink_box;
    private DatabaseReference ref_menus;
    private TextView menu_name;
    private menu_form data_menu;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    ArrayList<String> arr;
    addapter_display_menu addapter;
    ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display__menu);
        main_box = findViewById(R.id.Mains);
        desert_box = findViewById(R.id.Deserts);
        drink_box = findViewById(R.id.Drink);
        start_box = findViewById(R.id.Starters);
        menu_name = findViewById(R.id.menu_type);
        data_menu = new menu_form();
        ref_menus = FirebaseDatabase.getInstance().getReference("menus");
        list = findViewById(R.id.Menu_List);
        arr = new ArrayList<String>();
        findViewById(R.id.Add_Dish).setOnClickListener(this);



    }
    private void get_menu(String str){ //get the chosen menu and set menu_name
        menu_name.setText(str); //show the menu name
        final String type_dish = str;
        ref_menus.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists() && dataSnapshot.child(user.getUid()).exists()) { //if the user has menu
                    DataSnapshot user_menu = dataSnapshot.child(user.getUid());
                    if (type_dish == "Starters") { //if we chose starters then add it to menu and add it to the list we display (arr)
                        add_starterslist_menu_todata_menu(user_menu);
                        get_arrtype(arr,data_menu.getStarters_list() );
                        data_menu.clea_deserts();
                        data_menu.clear_drink();
                        data_menu.clear_mains();

                    } else if (type_dish == "Deserts") { //same
                        add_desertlist_menu_todata_menu(user_menu);
                        get_arrtype(arr,data_menu.getDeserts_list() );
                        data_menu.clear_mains();
                        data_menu.clear_drink();
                        data_menu.clear_starters();

                    } else if (type_dish == "Drink") { //same
                        add_drinklist_menu_todata_menu(user_menu);
                        get_arrtype(arr,data_menu.getDrink_list() );
                        data_menu.clea_deserts();
                        data_menu.clear_mains();
                        data_menu.clear_starters();

                    } else if (type_dish == "Mains") { //same
                        add_mainlist_menu_todata_menu(user_menu);
                        get_arrtype(arr,data_menu.getMain_list() );
                        data_menu.clea_deserts();
                        data_menu.clear_drink();
                        data_menu.clear_starters();

                    }
                    addapter = new addapter_display_menu(arr,Display_Menu.this,type_dish,data_menu); // intilize addapter
                    list.setAdapter(addapter); //show the addapter
                    data_menu.clear_mains();
                    data_menu.clear_starters();
                    data_menu.clear_drink();
                    data_menu.clea_deserts();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void add_starterslist_menu_todata_menu(DataSnapshot dt) { //this function add all the deserts in the user menu in database to arr
        DataSnapshot maindata = dt.child("starters_list"); //maindata = deserts menu of the current in database
        for (DataSnapshot childdata : maindata.getChildren()) { //loop on all the dishes in desert menu of the user
            String name = childdata.child("dish_name").getValue(String.class); //name of the dish
            String desc = childdata.child("dish_description").getValue(String.class); //discription of the dish
            double price = childdata.child("price").getValue(double.class); //price of the dish
            dish_form dish = new dish_form(price, name, desc);
            data_menu.add_start_dish(dish); //add dish to arr disertt menu
        }
    }
    private void add_desertlist_menu_todata_menu(DataSnapshot dt) { //this function add all the deserts in the user menu in database to arr
        DataSnapshot maindata = dt.child("deserts_list"); //maindata = deserts menu of the current in database
        for (DataSnapshot childdata : maindata.getChildren()) { //loop on all the dishes in desert menu of the user
            String name = childdata.child("dish_name").getValue(String.class); //name of the dish
            String desc = childdata.child("dish_description").getValue(String.class); //discription of the dish
            double price = childdata.child("price").getValue(double.class); //price of the dish
            dish_form dish = new dish_form(price, name, desc);
            data_menu.add_desert_dish(dish); //add dish to arr disertt menu
        }
    }
    private void add_drinklist_menu_todata_menu(DataSnapshot dt) { //same function as: same as add_desertlist_menu_toarr with drink dish
        DataSnapshot maindata = dt.child("drink_list");
        for (DataSnapshot childdata : maindata.getChildren()) {
            String name = childdata.child("dish_name").getValue(String.class);
            String desc = childdata.child("dish_description").getValue(String.class);
            double price = childdata.child("price").getValue(double.class);
            dish_form dish = new dish_form(price, name, desc);
            data_menu.add_drink(dish);
        }
    }

    private void add_mainlist_menu_todata_menu(DataSnapshot dt) { // same function as: add_desertlist_menu_toarr with main dish
        DataSnapshot maindata = dt.child("main_list");
        for (DataSnapshot childdata : maindata.getChildren()) {
            String name = childdata.child("dish_name").getValue(String.class);
            String desc = childdata.child("dish_description").getValue(String.class);
            double price = childdata.child("price").getValue(double.class);
            dish_form dish = new dish_form(price, name, desc);
            data_menu.add_main_dish(dish);
        }
    }

    @Override
    public void onClick(View v) { //make sure only one chekbox will be chek
        switch (v.getId()) {
            case R.id.Starters:
                if (main_box.isChecked()) {
                    main_box.setChecked(false);

                }
                if (desert_box.isChecked()) {
                    desert_box.setChecked(false);
                }
                if (drink_box.isChecked()) {
                    drink_box.setChecked(false);
                }
                get_menu("Starters");
                break;
            case R.id.Deserts:
                if (main_box.isChecked()) {
                    main_box.setChecked(false);
                }
                if (start_box.isChecked()) {
                    start_box.setChecked(false);
                }
                if (drink_box.isChecked()) {
                    drink_box.setChecked(false);
                }
                get_menu("Deserts");
                break;
            case R.id.Drink:
                if (main_box.isChecked()) {
                    main_box.setChecked(false);
                }
                if (desert_box.isChecked()) {
                    desert_box.setChecked(false);
                }
                if (start_box.isChecked()) {
                    start_box.setChecked(false);
                }
                get_menu("Drink");
                break;
            case R.id.Mains:
                if (start_box.isChecked()) {
                    start_box.setChecked(false);
                }
                if (desert_box.isChecked()) {
                    desert_box.setChecked(false);
                }
                if (drink_box.isChecked()) {
                    drink_box.setChecked(false);
                }
                get_menu("Mains");
                break;
            case R.id.Add_Dish:
                Intent i = new Intent(this, Add_Dish.class);
                startActivity(i);

        }
    }
    public void get_arrtype(ArrayList<String> arr_list, ArrayList<dish_form> dishes){ //add the dish info to arr, if arr is not empty clear it first
        if(!arr_list.isEmpty()){
            arr_list.clear();
        }
        for (int i = 0; i < dishes.size(); i++){
            arr_list.add(dishes.get(i).to_string());
        }
    }
}
