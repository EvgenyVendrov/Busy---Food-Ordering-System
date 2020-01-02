package com.example.busy.restaurant;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.busy.R;
import com.example.busy.restaurant.update.rest_update;

public class HOME_restaurant extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_restaurant);

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
