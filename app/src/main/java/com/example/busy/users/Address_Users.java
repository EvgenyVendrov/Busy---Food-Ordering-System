package com.example.busy.users;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.busy.R;
import com.example.busy.signUpActivity;
import com.example.busy.users.Uform.Address_form;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class Address_Users extends AppCompatActivity {
    private String City;
    private String Street;
    private String House_num;
    private String Phone_num;
    private String UID;
    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address__users);


        UID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        City =  findViewById(R.id.editText).toString();
        Street = findViewById(R.id.editText2).toString();
        House_num = findViewById(R.id.editText3).toString();
        Phone_num = findViewById(R.id.editText4).toString();

        final Address_form AF = new Address_form(City,Street,House_num,Phone_num);

        btn = findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase.getInstance().getReference("Addresses").child(UID).setValue(AF)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(Address_Users.this, "successfully", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(Address_Users.this, personal_settings.class);
                        startActivity(i);
                    }
                });
            }
        });
    }
}
