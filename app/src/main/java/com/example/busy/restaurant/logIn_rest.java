package com.example.busy.restaurant;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.busy.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class logIn_rest extends AppCompatActivity {
    private EditText _email;
    private EditText _password;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mListener;

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

        mAuth = FirebaseAuth.getInstance();
        _email = findViewById(R.id.EmailLog);
        _password = findViewById(R.id.passwordLog);

    }

}
