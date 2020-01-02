package com.example.busy.users;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.busy.MainActivity;
import com.example.busy.R;
import com.example.busy.users.Profile_Update.Edit_address;
import com.example.busy.users.Profile_Update.Edit_name;
import com.example.busy.users.Profile_Update.Edit_password;
import com.example.busy.users.Uform.Address_form;
import com.example.busy.users.Uform.Users_Form;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class personal_settings extends AppCompatActivity {
    private TextView edit_fn;
    private TextView edit_email;
    private TextView edit_pass;
    private TextView edit_address;
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private DatabaseReference ref_users;
    private Button logout_btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_settings);


        ref_users = FirebaseDatabase.getInstance().getReference("Users");
        edit_address = findViewById(R.id.Address_view);
        edit_email = findViewById(R.id.Email_view);
        edit_fn = findViewById(R.id.Name_view);
        edit_pass = findViewById(R.id.Pass_view);
        logout_btn = (Button) findViewById(R.id.Logout);
        ref_users.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Users_Form curr_user = dataSnapshot.child(user.getUid()).getValue(Users_Form.class);
                Address_form curr_add = curr_user.getAddress();
                edit_fn.setText("Name: " + curr_user.getFirstName() + " " + curr_user.getLastName());
                edit_email.setText("Email: " + curr_user.getEmail());
                edit_pass.setText("password: " + curr_user.getPassword());
                edit_address.setText("address: city: " + curr_add.getCity() + " street: " + curr_add.getStreet() + " house number: " + curr_add.getHouse_num()
                        + " phone number: " + curr_add.getPhone_num());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //EDIT ADDRESS BUTTON
        edit_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i_EA = new Intent(personal_settings.this, Edit_address.class);
                startActivity(i_EA);
            }
        });

        //RETURN BUTTON
        ImageView rtn = findViewById(R.id.return_btn);
        rtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(personal_settings.this, Home_users.class);
                startActivity(i);
            }
        });

        //EDIT FIRST NAME BUTTON
        edit_fn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i_EF = new Intent(personal_settings.this, Edit_name.class);
                startActivity(i_EF);
            }
        });

        //EDIT PASSWORD BUTTON
        edit_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i_EP = new Intent(personal_settings.this, Edit_password.class);
                startActivity(i_EP);
            }
        });

        //LOGOUT BUTTON
        logout_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent i_out = new Intent(personal_settings.this, MainActivity.class);
                startActivity(i_out);
            }
        });
    }

}
