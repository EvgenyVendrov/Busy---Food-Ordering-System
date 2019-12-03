package com.example.busy.restaurant;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.view.View;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.example.busy.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class signUp_rest extends AppCompatActivity implements View.OnClickListener  {
    private EditText name;
    private EditText email;
    private EditText password;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_rest);

        name = findViewById(R.id.name_text);
        email = findViewById(R.id.email_text);
        password = findViewById(R.id.pass_text);
        findViewById(R.id.SignUp_button).setOnClickListener(this);
        mAuth = FirebaseAuth.getInstance();

    }
    @Override
    protected void onStart() {
        super.onStart();
        if (mAuth.getCurrentUser() != null){
            //handle the alredy login user
        }
    }

    private void registerRest() {
        final String Name = name.getText().toString().trim();
        final String Email = email.getText().toString().trim();
        String Password = password.getText().toString().trim();

        if(Name.isEmpty()){
            name.setError("Name Required");
            name.requestFocus();
            return;
        }

        if(Email.isEmpty()){
            email.setError("Email Required");
            email.requestFocus();
            return;
        }

        if(Password.isEmpty()){
            password.setError("Password Required");
            password.requestFocus();
            return;
        }

        if (Password.length()<6){
            password.setError("password length shuld be higer than 6");
            password.requestFocus();
            return;
        }

        mAuth.createUserWithEmailAndPassword(Email,Password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Restaurant_Form rest = new Restaurant_Form(Name,Email);
                            FirebaseDatabase.getInstance().getReference("Restaurant").child(FirebaseAuth.getInstance().getUid()).setValue(rest)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(signUp_rest.this,"Sign Up successfully",Toast.LENGTH_SHORT).show();
                                        Intent i = new Intent (signUp_rest.this,HOME_restaurant.class);
                                        startActivity(i);
                                    }

                                    else {
                                        Toast.makeText(signUp_rest.this,"Error, couldn't sign up",Toast.LENGTH_SHORT).show();

                                    }
                                }
                            });
                        }

                        else {
                            Toast.makeText(signUp_rest.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.SignUp_button:
                registerRest();
                break;
        }
    }
}
