package com.example.busy.restaurant;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;

import com.example.busy.R;

public class Display_Menu extends AppCompatActivity implements View.OnClickListener {
    private CheckBox start_box;
    private CheckBox main_box;
    private CheckBox desert_box;
    private CheckBox drink_box;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display__menu);
        main_box = findViewById(R.id.Mains);
        desert_box = findViewById(R.id.Deserts);
        drink_box = findViewById(R.id.Drink);
        start_box = findViewById(R.id.Starters);




    }

    @Override
    public void onClick(View v) { //make sure only one chekbox will be chek
        switch (v.getId()) {
            case R.id.Starters:
                if (main_box.isChecked()) {
                    main_box.setChecked(false);
                }
                if (desert_box.isChecked()) {
                    desert_box.setChecked(false);
                }
                if (drink_box.isChecked()) {
                    drink_box.setChecked(false);
                }
                break;
            case R.id.Deserts:
                if (main_box.isChecked()) {
                    main_box.setChecked(false);
                }
                if (start_box.isChecked()) {
                    start_box.setChecked(false);
                }
                if (drink_box.isChecked()) {
                    drink_box.setChecked(false);
                }
                break;
            case R.id.Drink:
                if (main_box.isChecked()) {
                    main_box.setChecked(false);
                }
                if (desert_box.isChecked()) {
                    desert_box.setChecked(false);
                }
                if (start_box.isChecked()) {
                    start_box.setChecked(false);
                }
                break;
            case R.id.Mains:
                if (start_box.isChecked()) {
                    start_box.setChecked(false);
                }
                if (desert_box.isChecked()) {
                    desert_box.setChecked(false);
                }
                if (drink_box.isChecked()) {
                    drink_box.setChecked(false);
                }
                break;

        }
    }
}
