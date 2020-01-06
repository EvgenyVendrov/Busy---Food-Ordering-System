package com.example.busy.restaurant.update;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
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

public class Edit_Type extends AppCompatActivity {

    private CheckBox reg, vege, vega;
    private String UID;
    private Button change_btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit__type);

        UID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        reg = findViewById(R.id.reg_CB_rest);
        vege = findViewById(R.id.vege_CB_rest);
        vega = findViewById(R.id.Vegan_CB_rest);
        change_btn = findViewById(R.id.GO_button);
        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (vega.isChecked()) {
                    vega.setChecked(false);
                }
                if (vege.isChecked()) {
                    vege.setChecked(false);
                }
            }
        });

        vega.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (reg.isChecked()) {
                    reg.setChecked(false);
                }
                if (vege.isChecked()) {
                    vege.setChecked(false);
                }
            }
        });

        vege.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (vega.isChecked()) {
                    vega.setChecked(false);
                }
                if (reg.isChecked()) {
                    reg.setChecked(false);
                }
            }
        });
        change_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newType = "";
                if (reg.isChecked() && !vege.isChecked() && !vega.isChecked()) {
                    newType = "Regular";
                } else if (!reg.isChecked() && vege.isChecked() && !vega.isChecked()) {
                    newType = "Vegetarian";
                } else if (!reg.isChecked() && !vege.isChecked() && vega.isChecked()) {
                    newType = "Vegan";
                } else {
                    Toast.makeText(getApplicationContext(), "NOTHING SELECTED!", Toast.LENGTH_SHORT).show();
                    return;
                }
                // Address_form AF = new Address_form(city, street, house, phone);
                FirebaseDatabase.getInstance().getReference("Restaurant").child(UID).child("type").setValue(newType)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(getApplicationContext(), "changed successfully", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(Edit_Type.this, rest_update.class);
                                startActivity(i);
                            }
                        });
            }
        });


        FirebaseDatabase.getInstance().getReference("Restaurant").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Restaurant_Form curr_user = dataSnapshot.child(UID).getValue(Restaurant_Form.class);
                String prevSet = curr_user.getType();
                if (prevSet.equals("Regular")) {
                    reg.setChecked(true);
                    vege.setChecked(false);
                    vega.setChecked(false);
                } else if (prevSet.equals("Vegetarian")) {
                    reg.setChecked(false);
                    vege.setChecked(true);
                    vega.setChecked(false);
                } else {
                    reg.setChecked(false);
                    vege.setChecked(false);
                    vega.setChecked(true);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}
