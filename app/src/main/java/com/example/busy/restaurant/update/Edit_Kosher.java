package com.example.busy.restaurant.update;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.busy.R;
import com.example.busy.restaurant.Rforms.Restaurant_Form;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Edit_Kosher extends AppCompatActivity {

    private CheckBox isKosher, isNOTkosher;
    private String UID;
    private Button change_btn;
    private String prevSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit__kosher);

        UID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        isKosher = findViewById(R.id.isKosher_CB_rest);
        isNOTkosher = findViewById(R.id.noKosher_CB_rest);
        change_btn = findViewById(R.id.GO_button);


        FirebaseDatabase.getInstance().getReference("Restaurant").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Restaurant_Form curr_user = dataSnapshot.child(UID).getValue(Restaurant_Form.class);
                prevSet = curr_user.getKosher();
                if (prevSet.equals("yes")) {
                    isKosher.setChecked(true);
                    isNOTkosher.setChecked(false);
                } else {
                    isKosher.setChecked(false);
                    isNOTkosher.setChecked(true);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        isKosher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isNOTkosher.isChecked()) {
                    isNOTkosher.setChecked(false);
                }
            }
        });

        isNOTkosher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isKosher.isChecked()) {
                    isKosher.setChecked(false);
                }
            }
        });

        change_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newKosher = "";
                if (isKosher.isChecked() && !isNOTkosher.isChecked()) {
                    newKosher = "yes";
                } else if (!isKosher.isChecked() && isNOTkosher.isChecked()) {
                    newKosher = "no";
                } else {
                    Toast.makeText(getApplicationContext(), "NOTHING SELECTED!", Toast.LENGTH_SHORT).show();
                    return;
                }
                // Address_form AF = new Address_form(city, street, house, phone);
                FirebaseDatabase.getInstance().

                        getReference("Restaurant").

                        child(UID).

                        child("kosher").

                        setValue(newKosher)
                        .

                                addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        Toast.makeText(getApplicationContext(), "changed successfully", Toast.LENGTH_SHORT).show();
                                        Intent i = new Intent(Edit_Kosher.this, rest_update.class);
                                        startActivity(i);
                                    }
                                });
            }
        });
    }
}
