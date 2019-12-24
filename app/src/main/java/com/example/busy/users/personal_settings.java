package com.example.busy.users;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.busy.R;
import com.example.busy.restaurant.Signup_Restaurant;


public class personal_settings extends AppCompatActivity implements View.OnClickListener {
    private Button rest_sign;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_settings);

         findViewById(R.id.sign_rest).setOnClickListener(this);

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sign_rest:
                Intent i = new Intent(personal_settings.this, Signup_Restaurant.class);
                startActivity(i);
                break;
        }
    }

}
