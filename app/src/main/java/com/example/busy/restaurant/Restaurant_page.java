package com.example.busy.restaurant;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.busy.R;
import com.example.busy.restaurant.Rforms.Restaurant_Form;
import com.example.busy.users.Uform.filter_form;

public class Restaurant_page extends AppCompatActivity {
    private TextView amitai;
    private Restaurant_Form rset;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_page);

        amitai = findViewById(R.id.amitai_test);
        Intent i = getIntent();

    }
}
