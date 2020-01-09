package com.example.busy.Admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.busy.R;

public class home_admin extends AppCompatActivity {

    Button stats_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_admin);

        stats_btn = findViewById(R.id.stats_btn_rest);
        stats_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(home_admin.this, appStats.class);
                startActivity(i);

            }
        });
    }

    public void onClickShowBroadcast(View view) {
        EditText st = (EditText) findViewById(R.id.broadcast_ET_admin);
        Intent intent = new Intent();
        intent.putExtra("msg", (CharSequence) st.getText().toString());
        intent.setAction("com.com.example.busy.CUSTOM_INTENT");
        sendBroadcast(intent);
    }
}
