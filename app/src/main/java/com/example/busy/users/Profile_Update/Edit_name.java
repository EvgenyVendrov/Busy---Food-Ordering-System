package com.example.busy.users.Profile_Update;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.busy.R;
import com.example.busy.users.Uform.Users_Form;
import com.example.busy.users.personal_settings;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Edit_name extends AppCompatActivity implements View.OnClickListener {
    private EditText first_name, last_name;
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser(); //the current user in the database

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_name);

        findViewById(R.id.change_name).setOnClickListener(this);
        first_name = findViewById(R.id.new_first_name);
        last_name = findViewById(R.id.new_last_name);
    }

    private void changeName() {
        String F_name = first_name.getText().toString().trim();
        String L_name = last_name.getText().toString().trim();

        if (F_name.isEmpty()) {
            first_name.setError("Empty name input");
            first_name.requestFocus();
            return;
        }
        if (L_name.isEmpty()) {
            last_name.setError("Empty last name input");
            last_name.requestFocus();
            return;
        }

        FirebaseDatabase.getInstance().getReference("Users").child(user.getUid()).child("lastName").setValue(L_name);
        FirebaseDatabase.getInstance().getReference("Users").child(user.getUid()).child("firstName").setValue(F_name)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(Edit_name.this, "Edit successful", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(Edit_name.this, personal_settings.class);
                        startActivity(i);
                    }
                });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.change_name:
                changeName();
                break;
        }
    }
}
