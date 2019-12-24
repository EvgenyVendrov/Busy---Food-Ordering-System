package com.example.busy.users;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.busy.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class Address_Users extends AppCompatActivity {
    private String City;
    private String Street;
    private String House_num;
    private String Phone_num;
    private String UID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address__users);


        UID = FirebaseAuth.getInstance().getCurrentUser().getUid();
    }
}
