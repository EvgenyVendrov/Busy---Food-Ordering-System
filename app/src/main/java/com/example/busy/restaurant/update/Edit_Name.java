package com.example.busy.restaurant.update;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.busy.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class Edit_Name extends AppCompatActivity {
    private EditText name_data;
    private String UID;
    private Button change_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit__name);


        name_data = findViewById(R.id.EditName_text_rest);
        change_btn = findViewById(R.id.GO_button);

        UID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        //change BUTTON clicked
        change_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String name = name_data.getText().toString().trim();
                if (name.isEmpty()) {
                    name_data.setError("No Name is given");
                    name_data.requestFocus();
                    return;
                }
                // Address_form AF = new Address_form(city, street, house, phone);
                FirebaseDatabase.getInstance().getReference("Restaurant").child(UID).child("name").setValue(name)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(getApplicationContext(), "changed successfully", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(Edit_Name.this, rest_update.class);
                                startActivity(i);
                            }
                        });
            }
        });


    }
}
