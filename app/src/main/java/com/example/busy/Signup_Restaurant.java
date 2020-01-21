package com.example.busy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.busy.restaurant.Rforms.Restaurant_Form;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class Signup_Restaurant extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    private EditText rest_name_editext;
    private EditText phone_editext;
    private String UID;
    private String city="Ariel";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup__restaurant);

        rest_name_editext = findViewById(R.id.rest_name);
        phone_editext = findViewById(R.id.rest_phone);
        findViewById(R.id.next_btn).setOnClickListener(this);
        UID = FirebaseAuth.getInstance().getCurrentUser().getUid();


        //Cities Spinner
        Spinner Cities_spinner = findViewById(R.id.Cities_spinner);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.Cities, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Cities_spinner.setAdapter(adapter);
        Cities_spinner.setOnItemSelectedListener(this);

    }

    private void register_restaurant() {
        final String rest_name = rest_name_editext.getText().toString().trim();
        final String location = city;
        final String phone = phone_editext.getText().toString().trim();


         if (rest_name.isEmpty()) {
            rest_name_editext.setError("Restaurant Name is required");
            rest_name_editext.requestFocus();
            return;
        }
         if (phone.isEmpty()) {
            phone_editext.setError("Phone is empty");
            phone_editext.requestFocus();
            return;
        }


        Restaurant_Form rest = new Restaurant_Form(rest_name, location, phone,UID);
        FirebaseDatabase.getInstance().getReference("Restaurant").child(UID).setValue(rest)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Intent i = new Intent(Signup_Restaurant.this, Signup_Restaurant2.class);
                            startActivity(i);

                        } else {
                            Toast.makeText(Signup_Restaurant.this, "Sign up failed", Toast.LENGTH_SHORT).show();

                        }
                    }
                });


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.next_btn:
                register_restaurant();
                break;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        city = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(), city, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


}
