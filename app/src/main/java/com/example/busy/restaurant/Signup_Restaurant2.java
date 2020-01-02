package com.example.busy.restaurant;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.busy.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class Signup_Restaurant2 extends AppCompatActivity implements View.OnClickListener {
    private CheckBox type_regCB, type_vegeCB, type_veganCB, kosherCB, notKosherCB;
    private EditText descriptionET;
    private String UID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup__restaurant2);

        UID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        type_regCB = findViewById(R.id.regular_CB);
        type_vegeCB = findViewById(R.id.vege_CB);
        type_veganCB = findViewById(R.id.vegan_CB);
        kosherCB = findViewById(R.id.kosher_CB);
        notKosherCB = findViewById(R.id.NoKosher_CB);
        descriptionET = findViewById(R.id.description_text);
        findViewById(R.id.finish_btn).setOnClickListener(this);

        type_regCB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (type_veganCB.isChecked()) {
                    type_veganCB.setChecked(false);
                }
                if (type_vegeCB.isChecked()) {
                    type_vegeCB.setChecked(false);
                }
            }
        });
        type_vegeCB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (type_veganCB.isChecked()) {
                    type_veganCB.setChecked(false);
                }
                if (type_regCB.isChecked()) {
                    type_regCB.setChecked(false);
                }
            }
        });
        type_veganCB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (type_regCB.isChecked()) {
                    type_regCB.setChecked(false);
                }
                if (type_vegeCB.isChecked()) {
                    type_vegeCB.setChecked(false);
                }
            }
        });
        kosherCB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (notKosherCB.isChecked()) {
                    notKosherCB.setChecked(false);
                }
            }
        });
        notKosherCB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (kosherCB.isChecked()) {
                    kosherCB.setChecked(false);
                }
            }
        });
    }

    private void register_restaurant2() {
        String type;
        String kosher;
        String description = descriptionET.getText().toString().trim();
        if (type_regCB.isChecked()) {
            type = "Regular";
        } else if (type_veganCB.isChecked()) {
            type = "Vegan";
        } else {
            type = "Vegetarian";
        }

        if (notKosherCB.isChecked()) {
            kosher = "no";
        } else {
            kosher = "yes";
        }

        FirebaseDatabase.getInstance().getReference("Restaurant").child(UID).child("kosher").setValue(kosher);
        FirebaseDatabase.getInstance().getReference("Restaurant").child(UID).child("type").setValue(type);
        FirebaseDatabase.getInstance().getReference("Restaurant").child(UID).child("description").setValue(description)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(Signup_Restaurant2.this, "Sign up restaurant is successfull", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(Signup_Restaurant2.this, HOME_restaurant.class);
                        startActivity(i);
                    }
                });

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.finish_btn:
                register_restaurant2();
                break;
        }
    }
}
