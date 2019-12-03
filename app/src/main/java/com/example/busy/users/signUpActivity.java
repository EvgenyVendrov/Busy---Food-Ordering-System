package com.example.busy.users;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.busy.R;
import com.example.busy.Users_Form;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class signUpActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText editTextFirstName;
    private EditText editTextLastName;
    private EditText editTextEmail;
    private EditText editTextPass;
    private FirebaseAuth mAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        editTextFirstName = findViewById(R.id.firstNameText);
        editTextLastName = findViewById(R.id.lastNameText);
        editTextEmail = findViewById(R.id.emailText);
        editTextPass = findViewById(R.id.passwordText);
        findViewById(R.id.signUpButton).setOnClickListener(this);
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mAuth.getCurrentUser() != null){
            //handle the alredy login user
        }
    }

    private void registerUser(){
        final String FirstName = editTextFirstName.getText().toString().trim();
        final String LastName =  editTextLastName.getText().toString().trim();
        final String Email = editTextEmail.getText().toString().trim();
        String password = editTextPass.getText().toString().trim();


        if(FirstName.isEmpty()){
            editTextFirstName.setError("First Name Requierd");
            editTextFirstName.requestFocus();
            return;
        }
        if(LastName.isEmpty()){
            editTextLastName.setError("Last Name Required");
            editTextLastName.requestFocus();
            return;
        }
        if (Email.isEmpty()){
            editTextEmail.setError("Email Required");
            editTextEmail.requestFocus();
            return;
        }
        if (password.isEmpty()){
            editTextPass.setError("password is required");
            editTextPass.requestFocus();
            return;
        }
        if (password.length() < 6){
            editTextPass.setError("password length shuld be higer than 6");
            editTextPass.requestFocus();
            return;
        }

        mAuth.createUserWithEmailAndPassword(Email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            //we will store the additional fileds in firebase database
                            Users_Form user = new Users_Form(FirstName,Email,LastName);
                            FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getUid()).setValue(user)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(signUpActivity.this, "logged in", Toast.LENGTH_SHORT).show();
                                    } else {
                                        //display a faliure messege
                                        Toast.makeText(signUpActivity.this, "culdnt logged in",Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }

                        else{
                            Toast.makeText(signUpActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.signUpButton:
                registerUser();
                break;
        }
    }
}
