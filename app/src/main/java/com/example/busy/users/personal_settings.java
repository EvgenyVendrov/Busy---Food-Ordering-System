package com.example.busy.users;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.busy.R;
import com.example.busy.users.Profile_Update.Edit_address;
import com.example.busy.users.Profile_Update.Edit_email;
import com.example.busy.users.Profile_Update.Edit_lastname;
import com.example.busy.users.Profile_Update.Edit_name;
import com.example.busy.users.Profile_Update.Edit_password;


public class personal_settings extends AppCompatActivity {
    TextView edit_fn;
    TextView edit_ln;
    TextView edit_email;
    TextView edit_pass;
    TextView edit_address;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_settings);

        //EDIT ADDRESS BUTTON
        edit_address = findViewById(R.id.Address_view);
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

        //EDIT EMAIL BUTTON
        edit_email = findViewById(R.id.Email_view);
        edit_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i_EE = new Intent(personal_settings.this, Edit_email.class);
                startActivity(i_EE);
            }
        });

        //EDIT FIRST NAME BUTTON
        edit_fn = findViewById(R.id.Name_view);
        edit_fn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i_EF = new Intent(personal_settings.this, Edit_name.class);
                startActivity(i_EF);
            }
        });

        //EDIT LAST NAME BUTTON
        edit_ln = findViewById(R.id.Last_view);
        edit_ln.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i_EL = new Intent(personal_settings.this, Edit_lastname.class);
                startActivity(i_EL);
            }
        });

        //EDIT PASSWORD BUTTON
        edit_pass = findViewById(R.id.Pass_view);
        edit_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i_EP = new Intent(personal_settings.this, Edit_password.class);
                startActivity(i_EP);
            }
        });

    }

}
