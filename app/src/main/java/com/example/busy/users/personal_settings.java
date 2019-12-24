package com.example.busy.users;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.busy.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class personal_settings extends AppCompatActivity implements View.OnClickListener {
    private Button update_profile;
    private FirebaseAuth mAuth;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_settings);
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        findViewById(R.id.update_profile).setOnClickListener(this); //set click listener on update button
        findViewById(R.id.addres).setOnClickListener(this);
        findViewById(R.id.Logout).setOnClickListener(this);

    }
    @Override
    public void onClick(View v) { //the click function on view
        switch (v.getId()) {
            case R.id.update_profile: //if the view was the update button than go to profile_update activity
                Intent update_switch = new Intent(personal_settings.this, profile_update.class);
                startActivity(update_switch);
                break;
            case R.id.addres:
                Intent i = new Intent(personal_settings.this, Address_Users.class);
                startActivity(i);
                break;
            case R.id.Logout:
                mAuth.signOut();


        }
    }

}
