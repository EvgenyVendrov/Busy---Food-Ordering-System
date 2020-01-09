package com.example.busy.restaurant;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.busy.R;

public class OrderHistoryRest extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history_rest);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.stats_btn_rest:
                Intent i = new Intent(OrderHistoryRest.this, RestStaticstics.class);
                startActivity(i);
        }
    }
}
