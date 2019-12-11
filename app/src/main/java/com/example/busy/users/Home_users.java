package com.example.busy.users;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.busy.R;
import com.example.busy.restaurant.forms.Restaurant_Form;
import com.example.busy.users.Uform.Users_Form;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Home_users extends AppCompatActivity implements View.OnClickListener {
    Users_Form u;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    DatabaseReference ref_users;
    DatabaseReference ref_rests;


    ListView listView;
    ArrayList<String> rest_list = new ArrayList<>();
    ArrayAdapter<String> rest_adapter;


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
        findViewById(R.id.personal).setOnClickListener(this);
        listView = (ListView) findViewById(R.id.rest_view);
        ref_users = FirebaseDatabase.getInstance().getReference("Users");
        ref_rests = FirebaseDatabase.getInstance().getReference("Restaurant");

        ref_users.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                u = dataSnapshot.child(user.getUid()).getValue(Users_Form.class);
                TextView Hello_Name = findViewById(R.id.hello_name);
                Hello_Name.setText("Hello, " + u.getFirstName());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(Home_users.this, "" + databaseError.toString(), Toast.LENGTH_LONG).show();
            }
        });

        ref_rests.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //   Restaurant_Form value = dataSnapshot.child("7f0r9dQy3yPYE0fxmPh4eJzb20A2").getValue(Restaurant_Form.class);
                String name = dataSnapshot.child("7f0r9dQy3yPYE0fxmPh4eJzb20A2").getValue().toString();
                String str = name;
                rest_list.add(str);
                rest_adapter = new ArrayAdapter<String>(Home_users.this, android.R.layout.simple_list_item_1, rest_list);
                listView.setAdapter(rest_adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.personal:
                Intent i = new Intent(Home_users.this, personal_settings.class);
                startActivity(i);
                break;
        }
    }
}
