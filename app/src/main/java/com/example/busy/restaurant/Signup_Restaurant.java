package com.example.busy.restaurant;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.busy.R;
import com.example.busy.restaurant.forms.Restaurant_Form;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class Signup_Restaurant extends AppCompatActivity {
    private EditText rest_name_editext;
    private EditText owner_editext;
    private EditText location_editext;
    private EditText phone_editext;
    private EditText email_edittext;
    private Button signup_btn;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup__restaurant);

        rest_name_editext = findViewById(R.id.rest_name);
        owner_editext = findViewById(R.id.owner);
        location_editext = findViewById(R.id.Location_rest);
        phone_editext = findViewById(R.id.rest_phone);
        signup_btn = findViewById(R.id.signup_rest);
        email_edittext = findViewById(R.id.rest_email);
        mAuth = FirebaseAuth.getInstance();


    }
    private void register_restaurant(){
        final FirebaseUser user = mAuth.getCurrentUser();
        final String rest_name = rest_name_editext.getText().toString().trim();
        final String rest_owner = owner_editext.getText().toString().trim();
        final String location = location_editext.getText().toString().trim();
        final String email = email_edittext.getText().toString().trim();
        final String phone = phone_editext.getText().toString().trim();


        if (rest_owner.isEmpty()){
            owner_editext.setError("First Name Required");
            owner_editext.requestFocus();
            return;
        }
        else if (location.isEmpty()){
            location_editext.setError("location is required");
            location_editext.requestFocus();
            return;
        }

        else if (phone.isEmpty()){
            phone_editext.setError("phone is empty");
            phone_editext.requestFocus();
            return;
        }


        mAuth.createUserWithEmailAndPassword(email, user.getUid())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //need to get user name from database
                        if (task.isSuccessful()){
                            Restaurant_Form rest = new Restaurant_Form(rest_name,rest_owner,location,email,phone);
                            FirebaseDatabase.getInstance().getReference("Restaurant").child(user.getUid()).setValue(rest)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(Signup_Restaurant.this, "Sign up restaurant is successfull", Toast.LENGTH_SHORT).show();
                                                Intent i = new Intent(Signup_Restaurant.this, HOME_restaurant.class);
                                                startActivity(i);

                                            }
                                            else{
                                                Toast.makeText(Signup_Restaurant.this, "Sign up failed", Toast.LENGTH_SHORT).show();

                                            }
                                        }
                                    });
                        }
                        else{
                            Toast.makeText(Signup_Restaurant.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();

                        }
                    }
                });

}

public void onClick(View v){
        register_restaurant();
}



}
