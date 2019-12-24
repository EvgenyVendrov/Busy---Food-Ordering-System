package com.example.busy.users;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.busy.R;


public class personal_settings extends AppCompatActivity implements View.OnClickListener {
    private Button update_profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_settings);


         findViewById(R.id.update_profile).setOnClickListener(this);

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.update_profile:
                Intent update_switch = new Intent(personal_settings.this, profile_update.class);
                startActivity(update_switch);
                break;
        }
    }

}
