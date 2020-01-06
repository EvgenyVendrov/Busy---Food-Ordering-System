package com.example.busy.restaurant.update;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.busy.MainActivity;
import com.example.busy.R;
import com.example.busy.restaurant.Add_Dish;
import com.example.busy.restaurant.Display_Menu;
import com.example.busy.restaurant.HOME_restaurant;
import com.example.busy.restaurant.Rforms.Restaurant_Form;
import com.example.busy.users.Home_users;
import com.example.busy.users.Profile_Update.Edit_address;
import com.example.busy.users.Profile_Update.Edit_name;
import com.example.busy.users.Profile_Update.Edit_password;
import com.example.busy.users.personal_settings;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class rest_update extends AppCompatActivity {
    private TextView edit_description;
    private TextView edit_isKosher;
    private TextView edit_Type;
    private TextView edit_Location;
    private TextView edit_Name;
    private TextView edit_Phone;
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private DatabaseReference ref_users;
    private Button logout_btn;
    private Button add_dishbtn;
    private Button display_menubtn;
    private ImageView ret_btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rest_update);

        //init the vals
        ref_users = FirebaseDatabase.getInstance().getReference("Restaurant");
        edit_description = findViewById(R.id.Description_view);
        edit_isKosher = findViewById(R.id.isKosher_view);
        edit_Type = findViewById(R.id.Type_view);
        edit_Location = findViewById(R.id.Location_view);
        edit_Name = findViewById(R.id.Name_view);
        edit_Phone = findViewById(R.id.Phone_view);
        logout_btn = findViewById(R.id.Logout_btn);
        add_dishbtn = findViewById(R.id.add_tomenu);
        display_menubtn = findViewById(R.id.view_Menu);
        ret_btn = findViewById(R.id.return_btn);

        //this will make the relevant rest data to appear in the activity
        ref_users.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Restaurant_Form curr_user = dataSnapshot.child(user.getUid()).getValue(Restaurant_Form.class);
                edit_description.setText("Description: " + curr_user.getDescription());
                edit_isKosher.setText("Kosher: " + curr_user.getKosher());
                edit_Location.setText("Location: " + curr_user.getLocation());
                edit_Name.setText("Name: " + curr_user.getName());
                edit_Phone.setText("Phone: " + curr_user.getPhone());
                edit_Type.setText("Type: " + curr_user.getType());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        //return BUTTON clicked
        ret_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i_back = new Intent(rest_update.this, HOME_restaurant.class);
                startActivity(i_back);
            }
        });

        //EDIT DESCRIPTION BUTTON
        edit_description.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i_ED = new Intent(rest_update.this, Edit_Desc.class);
                startActivity(i_ED);
            }
        });

        //EDIT KOSHER BUTTON
        edit_isKosher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i_iK = new Intent(rest_update.this, Edit_Kosher.class);
                startActivity(i_iK);
            }
        });

        //EDIT TYPE BUTTON
        edit_Type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i_ET = new Intent(rest_update.this, Edit_Type.class);
                startActivity(i_ET);
            }
        });

        //EDIT LOCATION BUTTON
        edit_Location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "YOU CANNOT CHANGE RESTAURANT LOCATION", Toast.LENGTH_SHORT).show();
            }
        });

        //EDIT NAME BUTTON
        edit_Name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i_EN = new Intent(rest_update.this, Edit_Name.class);
                startActivity(i_EN);
            }
        });

        //EDIT PHONE BUTTON
        edit_Phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i_EP = new Intent(rest_update.this, Edit_Phone.class);
                startActivity(i_EP);
            }
        });

        //LOGOUT BUTTON
        logout_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent i_LO = new Intent(rest_update.this, MainActivity.class);
                startActivity(i_LO);
            }
        });
        add_dishbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i_add_dish = new Intent(rest_update.this, Add_Dish.class);
                startActivity(i_add_dish);
            }
        });
        display_menubtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i_display_menu = new Intent(rest_update.this, Display_Menu.class);
                startActivity(i_display_menu);
            }
        });

    }
}