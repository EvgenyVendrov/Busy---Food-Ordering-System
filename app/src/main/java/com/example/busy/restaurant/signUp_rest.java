package com.example.busy.restaurant;

import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.os.Bundle;
import android.widget.EditText;

import com.example.busy.R;
import com.google.firebase.auth.FirebaseAuth;

public class signUp_rest extends AppCompatActivity {
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
        findViewById(R.id.SignUp_button).setOnClickListener((View.OnClickListener) this);
        mAuth = FirebaseAuth.getInstance();

    }
    @Override
    protected void onStart() {
        super.onStart();
        if (mAuth.getCurrentUser() != null){
            //handle the alredy login user
        }
    }

    private void registerUser() {
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
    }
}
