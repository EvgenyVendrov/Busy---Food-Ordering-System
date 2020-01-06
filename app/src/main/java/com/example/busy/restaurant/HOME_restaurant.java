package com.example.busy.restaurant;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.busy.R;
import com.example.busy.restaurant.Rforms.Restaurant_Form;
import com.example.busy.restaurant.update.rest_update;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HOME_restaurant extends AppCompatActivity {

private TextView welcometxt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_restaurant);

        welcometxt = findViewById(R.id.infoAboutUser_rest_text);
        FirebaseDatabase.getInstance().getReference("Restaurant").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Restaurant_Form curr_user = dataSnapshot.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).getValue(Restaurant_Form.class);
                String rest_name = curr_user.getName();
                welcometxt.setText("welcome, "+rest_name+" owner");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        welcometxt.setText("");
        //listener to move to the settings activity when the image is clicked
        ImageView restSettings = findViewById(R.id.rest_settings);
        restSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HOME_restaurant.this, rest_update.class);
                startActivity(i);
            }
        });
    }
}
