package com.example.busy.restaurant;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.busy.R;
import com.example.busy.restaurant.Rforms.Restaurant_Form;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class Signup_Restaurant extends AppCompatActivity implements View.OnClickListener {
    private EditText rest_name_editext;
    private EditText location_editext;
    private EditText phone_editext;
    private String UID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup__restaurant);

        rest_name_editext = findViewById(R.id.rest_name);
        location_editext = findViewById(R.id.Location_rest);
        phone_editext = findViewById(R.id.rest_phone);
        findViewById(R.id.signup_rest).setOnClickListener(this);
        UID = FirebaseAuth.getInstance().getCurrentUser().getUid();


    }

    private void register_restaurant() {
        final String rest_name = rest_name_editext.getText().toString().trim();
        final String location = location_editext.getText().toString().trim();
        final String phone = phone_editext.getText().toString().trim();


        if (location.isEmpty()) {
            location_editext.setError("Location is required");
            location_editext.requestFocus();
            return;
        } else if (rest_name.isEmpty()) {
             rest_name_editext.setError("Restaurant Name is required");
             rest_name_editext.requestFocus();
             return;
        } else if (phone.isEmpty()) {
            phone_editext.setError("Phone is empty");
            phone_editext.requestFocus();
            return;
        }


        Restaurant_Form rest = new Restaurant_Form(rest_name, location, phone);
        FirebaseDatabase.getInstance().getReference("Restaurant").child(UID).setValue(rest)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(Signup_Restaurant.this, "Sign up restaurant is successfull", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(Signup_Restaurant.this, HOME_restaurant.class);
                            startActivity(i);

                        } else {
                            Toast.makeText(Signup_Restaurant.this, "Sign up failed", Toast.LENGTH_SHORT).show();

                        }
                    }
                });


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.signup_rest:
                register_restaurant();
                break;
        }
    }


}
