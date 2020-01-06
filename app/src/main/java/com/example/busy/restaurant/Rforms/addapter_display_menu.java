package com.example.busy.restaurant.Rforms;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.busy.R;
import com.example.busy.restaurant.Display_Menu;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import androidx.annotation.NonNull;

public class addapter_display_menu extends BaseAdapter implements ListAdapter {
    private ArrayList<String> list = new ArrayList<String>(); //the valus we will display
    private Context context;
    private final DatabaseReference menus = FirebaseDatabase.getInstance().getReference("menus");
    private String type; //the type of the menu
    private final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    menu_form data_menu; //the menu from the data base
    private EditText new_name;
    private EditText new_price;
    private EditText new_desc;

    public addapter_display_menu(ArrayList<String> list, Context context, String type, menu_form data_menu) {
        this.list = list;
        this.context = context;
        this.type = type;
        this.data_menu = data_menu;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup viewGroup) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.costume_listviewaddapter_item, null);
        }
        TextView listItemText = (TextView)view.findViewById(R.id.list_item_string);
        listItemText.setText(list.get(position));

        Button deleteBtn = (Button)view.findViewById(R.id.delete_btn);
        Button editBtn = (Button) view.findViewById(R.id.edit_btn);
        new_name = view.findViewById(R.id.Name_addapter_display);
        new_desc = view.findViewById(R.id.desc_addapter_display);
        new_price = view.findViewById(R.id.price_addapter_display);
        deleteBtn.setOnClickListener(new View.OnClickListener() { //delete function
            @Override
            public void onClick(View view) {
               final String name = list.get(position).split(",")[0]; //name of the chosen dish
                list.remove(position); //remove from the list of display
                notifyDataSetChanged();
                menus.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists() && dataSnapshot.child(user.getUid()).exists()){
                            DataSnapshot user_menu = dataSnapshot.child(user.getUid()); //get to the user menu
                            data_menu.remove_dish(name,type);
                            if (type == "Starters"){ //if the dish is from starters then set the updated starters
                                menus.child(user.getUid()).child("starters_list").setValue(data_menu.getStarters_list());
                            }
                            else if (type == "Mains"){ //same
                                menus.child(user.getUid()).child("main_list").setValue(data_menu.getMain_list());
                            }
                            else if (type == "Drink"){ //same
                                menus.child(user.getUid()).child("drink_list").setValue(data_menu.getDrink_list());
                            }
                            else if (type == "Deserts"){// same
                                menus.child(user.getUid()).child("deserts_list").setValue(data_menu.getDeserts_list());
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });

        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String namestr = new_name.getText().toString().trim();
                String pricestr = new_price.getText().toString().trim();
                String descstr = new_desc.getText().toString().trim();
                double price_num;
                if (namestr.isEmpty()){
                    new_name.setError("please enter name");
                    new_name.requestFocus();
                    return;
                }
                if (pricestr.isEmpty()){
                    new_price.setError("please enter price");
                    new_price.requestFocus();
                    return;
                }
                else{
                    price_num = Double.parseDouble(pricestr);
                }
                final dish_form dish = new dish_form(price_num,namestr,descstr);
                final String name = list.get(position).split(",")[0]; //name of the chosen dish

                list.remove(position);
                list.add(position, dish.to_string());
                /// i stop here!!!!!
                menus.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists() && dataSnapshot.child(user.getUid()).exists()){
                            DataSnapshot user_menu = dataSnapshot.child(user.getUid()); //get to the user menu
                            if (type == "Starters"){ //if the dish is from starters then set the updated starters
                              //  data_menu.add_start_dish(dish);
                                menus.child(user.getUid()).child("starters_list").setValue(data_menu.getStarters_list());
                            }
                            else if (type == "Mains"){ //same
                                //data_menu.add_main_dish(dish)
                                menus.child(user.getUid()).child("main_list").setValue(data_menu.getMain_list());
                            }
                            else if (type == "Drink"){ //same
                               // data_menu.add_drink(dish);
                                menus.child(user.getUid()).child("drink_list").setValue(data_menu.getDrink_list());
                            }
                            else if (type == "Deserts"){// same
                             //   data_menu.add_desert_dish(dish);
                                menus.child(user.getUid()).child("deserts_list").setValue(data_menu.getDeserts_list());
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
   return view;  }
}
