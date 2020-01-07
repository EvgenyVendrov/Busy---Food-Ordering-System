package com.example.busy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.busy.restaurant.HOME_restaurant;
import com.example.busy.users.Home_users;
import com.example.busy.users.Uform.filter_form;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private FirebaseAuth mAuth;
    private EditText email_ID, password_ID;
    private DatabaseReference ref_users;
    private String UID;
    private String owner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Sign up text view
        TextView signUp = findViewById(R.id.signUp_text);
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, signUpActivity.class);
                startActivity(i);
            }
        });

        //Init class variables

        findViewById(R.id.loginBotton).setOnClickListener(this);
        mAuth = FirebaseAuth.getInstance();
        email_ID = findViewById(R.id.EmailLog);
        password_ID = findViewById(R.id.passwordLog);


    }

    public void LOGIN() {
        String email = email_ID.getText().toString();
        String password = password_ID.getText().toString();
        if (email.isEmpty()) {
            email_ID.setError("Missing Email");
            email_ID.requestFocus();

        } else if (password.isEmpty()) {
            password_ID.setError("Missing Password");
            password_ID.requestFocus();

        } else if (!(email.isEmpty() && password.isEmpty())) {
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (!task.isSuccessful()) {
                        Toast.makeText(LoginActivity.this, "Login Error", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(LoginActivity.this, "Log In Succesfull", Toast.LENGTH_LONG).show();

                        ref_users = FirebaseDatabase.getInstance().getReference("Users"); //get reference to Users
                        UID = FirebaseAuth.getInstance().getCurrentUser().getUid();
                        ref_users.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                owner = dataSnapshot.child(UID).child("owner").getValue().toString(); // get user id of the current user

                                if (owner.equals("no")) {
                                    filter_form FM = new filter_form();
                                    Intent i = new Intent(LoginActivity.this, Home_users.class);
                                    i.putExtra("filter", FM);
                                    startActivity(i);
                                } else {
                                    Intent j = new Intent(LoginActivity.this, HOME_restaurant.class);
                                    startActivity(j);
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

                    }
                }
            });
        } else {
            Toast.makeText(LoginActivity.this, "Error, Try again", Toast.LENGTH_LONG).show();
        }


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.loginBotton:
                LOGIN();
                break;
        }
    }

}
