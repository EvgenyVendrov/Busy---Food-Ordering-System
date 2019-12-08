package com.example.busy.restaurant;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.busy.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


public class logIn_rest extends AppCompatActivity  {
    private EditText _email;
    private EditText _restname;
    private FirebaseAuth mAuth;
    private DatabaseReference databasereference;
    private FirebaseAuth.AuthStateListener mListener;
    private Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in_rest);

        //Sign up text view
        TextView signUp = findViewById(R.id.signUp_text);
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(logIn_rest.this, signUp_rest.class);
                startActivity(i);

            }
        });

        mAuth = FirebaseAuth.getInstance();
        _email = findViewById(R.id.EmailLog);
        _restname = findViewById(R.id.restname_text);
        login = findViewById(R.id.loginBotton);
        databasereference = FirebaseDatabase.getInstance().getReference("Restaurant");
        final String rname = _restname.getText().toString().trim();
        final String emil = _email.getText().toString().trim();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Restaurant_Form user = new Restaurant_Form(rname, emil);
                Query query = databasereference.child("Restaurant").orderByChild("email").equalTo(user.getEmail());
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.getValue() != null) {
                            // dataSnapshot is the "issue" node with all children with id 0

                            for (DataSnapshot user : dataSnapshot.getChildren()) {
                                // do something with the individual "issues"
                                Restaurant_Form usersBean = user.getValue(Restaurant_Form.class);

                                if (usersBean.getEmail().equals(_email.getText().toString().trim())) {
                                    Intent intent = new Intent(logIn_rest.this, HOME_restaurant.class);
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(logIn_rest.this, "Password is wrong", Toast.LENGTH_LONG).show();
                                }
                            }
                        } else {
                            Toast.makeText(logIn_rest.this, "User not found", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }
        });



    }



}
