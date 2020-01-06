
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

public class Edit_Desc extends AppCompatActivity {
    private EditText desc_data;
    private String UID;
    private Button change_btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit__desc);

        desc_data = findViewById(R.id.EditDesc_text_rest);
        change_btn = findViewById(R.id.GO_button);

        UID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        //change BUTTON clicked
        change_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String desc = desc_data.getText().toString().trim();
                if (desc.isEmpty()) {
                    desc_data.setError("No Description is given");
                    desc_data.requestFocus();
                    return;
                }
                // Address_form AF = new Address_form(city, street, house, phone);
                FirebaseDatabase.getInstance().getReference("Restaurant").child(UID).child("description").setValue(desc)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(getApplicationContext(), "changed successfully", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(Edit_Desc.this, rest_update.class);
                                startActivity(i);
                            }
                        });
            }
        });

    }


}
