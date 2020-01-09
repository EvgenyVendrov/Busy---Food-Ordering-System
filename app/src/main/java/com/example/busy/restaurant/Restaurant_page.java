package com.example.busy.restaurant;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.busy.R;
import com.example.busy.restaurant.Rforms.Restaurant_Form;
import com.example.busy.users.Make_Order;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Restaurant_page extends AppCompatActivity {
    private Restaurant_Form rest;
    private DatabaseReference rest_database;
    private FirebaseUser user;
    private TextView rest_info, rest_name, rest_phone, rest_location, rest_kosher, rest_type;
    private Button menubtn;
    private String rest_uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_page);

        Intent i = getIntent();
        final String uid = (String) i.getSerializableExtra("rest_uid");

        menubtn = findViewById(R.id.menu_rest_page);
        rest_info = findViewById(R.id.restname_Rest_page);
        rest_name = findViewById(R.id.name_TV_restPage);
        rest_phone = findViewById(R.id.phone_TV_restPage);
        rest_location = findViewById(R.id.location_TV_restPage);
        rest_kosher = findViewById(R.id.kosher_TV_restPage);
        rest_type = findViewById(R.id.type_TV_restPage);

        rest_database = FirebaseDatabase.getInstance().getReference("Restaurant");
        rest_database.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snep : dataSnapshot.getChildren()) {
                        if (snep.child("uid").getValue().equals(uid)) {
                            rest = snep.getValue(Restaurant_Form.class);
                            rest_uid = uid;
                        }
                    }

                    String show = rest.getName();
                    rest_name.setText(show);
                    show = "Location: " + rest.getLocation();
                    rest_location.setText(show);
                    show = "phone: " + rest.getPhone();
                    rest_phone.setText(show);
                    show = "kosher: " + rest.getKosher();
                    rest_kosher.setText(show);
                    show = "type: " + rest.getType();
                    rest_type.setText(show);
                    show ="Description: "+"\n"+ rest.getDescription();
                    rest_info.setText(show);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        menubtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Restaurant_page.this, Make_Order.class);
                i.putExtra("rest_uid", rest_uid);
                startActivity(i);
            }
        });

        rest_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri u = Uri.parse("tel:" + rest.getPhone());
                Intent _i = new Intent(Intent.ACTION_DIAL, u);

                try {
                    // Launch the Phone app's dialer with a phone
                    // number to dial a call.
                    startActivity(_i);
                } catch (SecurityException s) {
                    // show() method display the toast with
                    // exception message.
                    Toast.makeText(Restaurant_page.this, s.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}
