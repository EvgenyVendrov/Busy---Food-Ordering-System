package com.example.busy.users;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.busy.R;

public class OrderPage extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_page);
    }

    //click function on personal settings button
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.Orders_history_btn:
                Intent i = new Intent(OrderPage.this, OrderHistoryUser.class);
                startActivity(i);
                break;
        }
    }
}
