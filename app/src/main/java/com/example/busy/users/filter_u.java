package com.example.busy.users;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.example.busy.R;
import com.example.busy.users.Uform.filter_form;

public class filter_u extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {
    private CheckBox type_regCB, type_vegetarianCB, type_veganCB, kosherCB, notKosherCB;
    private String city = "Ariel", kosher, type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_u);


        //SET THE WINDOW MATRIX TO BE POP-UP SIZE
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout((int) (width * .8), (int) (height * .6));


        //Cities Spinner
        Spinner Cities_spinner = findViewById(R.id.city_spinner);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.Cities, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Cities_spinner.setAdapter(adapter);
        Cities_spinner.setOnItemSelectedListener(this);


        //INIT CHECKBOXES AND SET PATTERN
        type_regCB = findViewById(R.id.reg_CB_filter);
        type_vegetarianCB = findViewById(R.id.vegy_CB_filter);
        type_veganCB = findViewById(R.id.vegan_CB_filter);
        kosherCB = findViewById(R.id.kosher_CB_filter);
        notKosherCB = findViewById(R.id.noKosher_CB_filter);

        type_regCB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (type_veganCB.isChecked()) {
                    type_veganCB.setChecked(false);
                }
                if (type_vegetarianCB.isChecked()) {
                    type_vegetarianCB.setChecked(false);
                }
            }
        });
        type_vegetarianCB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (type_veganCB.isChecked()) {
                    type_veganCB.setChecked(false);
                }
                if (type_regCB.isChecked()) {
                    type_regCB.setChecked(false);
                }
            }
        });
        type_veganCB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (type_regCB.isChecked()) {
                    type_regCB.setChecked(false);
                }
                if (type_vegetarianCB.isChecked()) {
                    type_vegetarianCB.setChecked(false);
                }
            }
        });
        kosherCB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (notKosherCB.isChecked()) {
                    notKosherCB.setChecked(false);
                }
            }
        });
        notKosherCB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (kosherCB.isChecked()) {
                    kosherCB.setChecked(false);
                }
            }
        });
    }

    public void makeFilter() {
        if (type_regCB.isChecked()) {
            type = "Regular";
        } else if (type_veganCB.isChecked()) {
            type = "Vegan";
        } else if (type_vegetarianCB.isChecked()) {
            type = "Vegetarian";
        } else {
            type = "";
        }

        if (kosherCB.isChecked()) {
            kosher = "yes";
        } else if (notKosherCB.isChecked()) {
            kosher = "no";
        } else {
            kosher = "";
        }

        filter_form FM = new filter_form(city, kosher, type);

        Intent intent = new Intent("filter_Intent").putExtra("filter", FM);
        LocalBroadcastManager.getInstance(filter_u.this).sendBroadcast(intent);

//        Intent _i = new Intent(this, Home_users.class);
//        _i.putExtra("filter", FM);
//        startActivity(_i);
        finish();
    }

    // *** AdapterView.OnItemSelectedListener functions!! ***
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        city = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.go_btn_filter:
                makeFilter();
                break;
        }
    }
}
