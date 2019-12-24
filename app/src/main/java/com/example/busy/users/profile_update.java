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
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private DatabaseReference ref_users;
    private Button update_button;
    private Users_Form u;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_update);
        mAuth = FirebaseAuth.getInstance();

        ref_users = FirebaseDatabase.getInstance().getReference("Users");
        ref_users.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                u = dataSnapshot.child(user.getUid()).getValue(Users_Form.class); // get user id of the current user
                user_name = findViewById(R.id.your_name_textview);
                user_name.setText("your name: " + u.getFirstName());
                user_email = findViewById(R.id.CurrentEmail);
                user_email.setText("your email: " + u.getEmail());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        new_name = (EditText) findViewById(R.id.NewName_editext);
        new_email = (EditText) findViewById(R.id.NewEmail_editext);
        update_button = (Button) findViewById(R.id.update);
    }
    @Override
    public void onClick(View v){
        String name = new_name.getText().toString().trim();
        String email = new_email.getText().toString().trim();
        if (!name.isEmpty()){
            ref_users.child(user.getUid()).child("firstName").setValue(name);
            Toast.makeText(profile_update.this, "name updated", Toast.LENGTH_SHORT).show();
        }
        if (!email.isEmpty()){
            ref_users.child(user.getUid()).child("email").setValue(email);
            user.updateEmail(email);
            Toast.makeText(profile_update.this, "email is updated", Toast.LENGTH_SHORT).show();


        }
    }
}
