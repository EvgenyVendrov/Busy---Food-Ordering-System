package com.example.busy.users;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.busy.R;
import com.example.busy.users.Uform.Address_form;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class Address_Users extends AppCompatActivity implements View.OnClickListener {
    private EditText City;
    private EditText Street;
    private EditText House_num;
    private EditText Phone_num;
    private String UID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address__users);

        findViewById(R.id.GO_button).setOnClickListener(this);
        UID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        City = findViewById(R.id.CityET);
        Street = findViewById(R.id.Address_ET);
        House_num = findViewById(R.id.HN_ET);
        Phone_num = findViewById(R.id.Phone_ET);

    }

    public void Address_sign() {
        final String city = City.getText().toString().trim();
        final String street = Street.getText().toString().trim();
        final String house = House_num.getText().toString().trim();
        final String phone = Phone_num.getText().toString().trim();

        if (city.isEmpty()) {
            City.setError("No City given");
            City.requestFocus();
            return;
        }
        if (street.isEmpty()) {
            Street.setError("No Street given");
            Street.requestFocus();
            return;
        }
        if (house.isEmpty()) {
            House_num.setError("No House number given");
            House_num.requestFocus();
            return;
        }
        if (phone.isEmpty()) {
            Phone_num.setError("No Phone number given");
            Phone_num.requestFocus();
            return;
        }

        Address_form AF = new Address_form(city, street, house, phone);
        FirebaseDatabase.getInstance().getReference("Users").child(UID).child("Address").setValue(AF)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(Address_Users.this, "successfully", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(Address_Users.this, personal_settings.class);
                        startActivity(i);
                    }
                });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.GO_button:
                Address_sign();
                break;
        }
    }
}
