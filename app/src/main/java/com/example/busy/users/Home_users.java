package com.example.busy.users;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.busy.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Home_users extends AppCompatActivity {
    Users_Form u;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_users);

        //filter button
        Button filter = findViewById(R.id.Filter_Button);
        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Home_users.this, Filter_popup.class));
            }
        });

        final FirebaseDatabase database = FirebaseDatabase.getInstance();


        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                u = dataSnapshot.child(user.getUid()).getValue(Users_Form.class);
                TextView Hello_Name = findViewById(R.id.hello_name);
                Hello_Name.setText("Hello" + u.getFirstName());
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(Home_users.this,""+databaseError.toString(),Toast.LENGTH_LONG).show();
            }
        });




    }
}
