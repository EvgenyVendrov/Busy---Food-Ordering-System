package com.example.busy.users;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.busy.R;

public class signUpActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText editTextFirstName;
    private EditText editTextLastName;
    private EditText editTextEmail;
    private EditText editTextPass;
    private Button registerButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        editTextFirstName = findViewById(R.id.firstNameText);
        editTextLastName = findViewById(R.id.lastNameText);
        editTextEmail = findViewById(R.id.emailText);
        editTextPass = findViewById(R.id.passwordText);
        findViewById(R.id.signUpButton).setOnClickListener(this);
    }
    private void registerUser(){
        String FirstName = editTextFirstName.getText().toString().trim();
        String LastName =  editTextLastName.getText().toString().trim();
        String Email = editTextEmail.getText().toString().trim();
        String password = editTextPass.getText().toString().trim();
        String typeUser = "User";

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
