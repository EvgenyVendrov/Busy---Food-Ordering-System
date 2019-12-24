package com.example.busy.users;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.busy.R;
import com.example.busy.users.Uform.Users_Form;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class profile_update extends AppCompatActivity implements View.OnClickListener {
    private TextView user_name;
    private TextView user_email;
    private EditText new_name;
    private EditText new_email;
    private EditText current_password;
    private EditText new_password;
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser(); //the current user in the database
    private DatabaseReference ref_users;
    private Button update_button;
    private Button change_passwordbtn;
    private Users_Form u;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_update);
        mAuth = FirebaseAuth.getInstance();

        ref_users = FirebaseDatabase.getInstance().getReference("Users"); //get database Users reference
        ref_users.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                u = dataSnapshot.child(user.getUid()).getValue(Users_Form.class); // get user id of the current user
                user_name = findViewById(R.id.your_name_textview);
                user_name.setText("your name: " + u.getFirstName()); //set string value to user_name text view
                user_email = findViewById(R.id.CurrentEmail);
                user_email.setText("your email: " + u.getEmail()); //set string value to user_email textview

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(profile_update.this, "Error ocured", Toast.LENGTH_SHORT).show();


            }
        });

        new_name = (EditText) findViewById(R.id.NewName_editext);
        new_email = (EditText) findViewById(R.id.NewEmail_editext);
        update_button = (Button) findViewById(R.id.update);
        current_password = (EditText) findViewById(R.id.current_password);
        new_password = (EditText) findViewById(R.id.new_password);
        change_passwordbtn = (Button) findViewById(R.id.change_password);
    }
    @Override
    public void onClick(View v) { //click function on update button
        switch (v.getId()) {
            case R.id.update:
                String name = new_name.getText().toString().trim(); //the name the user entered in the edittext name
                String email = new_email.getText().toString().trim(); //the email the user enter in the editext email
                if (!name.isEmpty()) { //if nsme is not empty change the name in the real time database
                    ref_users.child(user.getUid()).child("firstName").setValue(name);
                    Toast.makeText(profile_update.this, "name updated", Toast.LENGTH_SHORT).show();
                }
                if (!email.isEmpty()) { //if email is not empty change email in the real time database and in the auth
                    ref_users.child(user.getUid()).child("email").setValue(email);
                    user.updateEmail(email);
                    Toast.makeText(profile_update.this, "email is updated", Toast.LENGTH_SHORT).show();
            }
                break;
            case R.id.change_password:
                changepassword();

        }
    }

    private void changepassword() {
        String password = current_password.getText().toString().trim();
        if(password.isEmpty()){
            current_password.setError("current password is emtpy");
            current_password.requestFocus();
            return;
        }
        if(!password.equals(u.getPassword())){
           current_password.setError("wrong password");
           current_password.requestFocus();
           return;
       }
        String new_userpass = new_password.getText().toString().trim();
        if(new_userpass.isEmpty()){
            new_password.setError("new password is empty");
            new_password.requestFocus();
            return;
        }
        if (new_userpass.equals(password)){
            new_password.setError("the new password is the same as the old one");
            new_password.requestFocus();
            return;
        }
        user.updatePassword(new_userpass);
        ref_users.child(user.getUid()).child("password").setValue(new_userpass);
        Toast.makeText(profile_update.this, "passwored changed succefuly", Toast.LENGTH_SHORT).show();
    }
}
