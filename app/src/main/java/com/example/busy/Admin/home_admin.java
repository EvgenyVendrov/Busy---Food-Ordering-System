package com.example.busy.Admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.busy.R;

public class home_admin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_admin);
    }

    public void onClickShowBroadcast(View view) {
        EditText st = (EditText) findViewById(R.id.broadcast_ET_admin);
        Intent intent = new Intent();
        intent.putExtra("msg", (CharSequence) st.getText().toString());
        intent.setAction("com.com.example.busy.CUSTOM_INTENT");
        sendBroadcast(intent);
    }
}
