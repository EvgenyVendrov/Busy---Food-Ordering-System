package com.example.busy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.busy.users.LoginActivity;
import com.example.busy.users.signUpActivity;

public class MainActivity extends AppCompatActivity {
    private TextView signUp;
    private Button signIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //Sign up text view
        signUp = findViewById(R.id.signUpText);
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, signUpActivity.class);
                startActivity(i);
            }
        });
        

        //Sign in Button
         signIn = findViewById(R.id.signInEmail);
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(i);
            }
        });

    }
}
