package com.example.busy.restaurant;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.busy.R;

public class logIn_rest extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in_rest);

        //Sign up text view
        TextView signUp = findViewById(R.id.signUp_text);
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(logIn_rest.this, signUp_rest.class);
                startActivity(i);
            }
        });
    }
}
