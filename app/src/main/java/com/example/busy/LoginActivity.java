package com.example.busy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.busy.users.Home_users;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private EditText email_ID, password_ID;
    private Button BTN_login;
    private FirebaseAuth.AuthStateListener mAuth_SL;


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
        mAuth = FirebaseAuth.getInstance();
        email_ID = findViewById(R.id.EmailLog);
        password_ID = findViewById(R.id.passwordLog);
        BTN_login = findViewById(R.id.loginBotton);

//        mAuth_SL = new FirebaseAuth.AuthStateListener() {
//            @Override
//            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
//                FirebaseUser mUser = mAuth.getCurrentUser();
//                if(mUser != null){
//                    Toast.makeText(LoginActivity.this,"Log In Succesfull",Toast.LENGTH_LONG).show();
//                    Intent i = new Intent(LoginActivity.this,Home_users.class);
//                    startActivity(i);
//                }
//            }
//        };
        BTN_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                                Intent i_home = new Intent(LoginActivity.this, Home_users.class);
                                startActivity(i_home);
                            }
                        }
                    });
                } else {
                    Toast.makeText(LoginActivity.this, "Error, Try again", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//        mAuth.addAuthStateListener(mAuth_SL);
//    }
}
