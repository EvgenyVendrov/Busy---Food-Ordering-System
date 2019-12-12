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
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();//the current online user
    DatabaseReference ref_users; //the reference for Users realtimedatabase
    DatabaseReference ref_rests; //the reference for Restaurant realtimedatabase


    ListView listView;
    ArrayList<String> rest_list = new ArrayList<>(); //will contains the data of all the restourounts
    ArrayAdapter<String> rest_adapter; //the addapter that will get the rest_list and will be added to the list view


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
        findViewById(R.id.personal).setOnClickListener(this); //click listener 0f personal settings
        listView = (ListView) findViewById(R.id.rest_view);
        ref_users = FirebaseDatabase.getInstance().getReference("Users"); //get reference to Users
        ref_rests = FirebaseDatabase.getInstance().getReference("Restaurant"); //get reference to Restaurant

        // show the name of the user on top of the page
        ref_users.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                u = dataSnapshot.child(user.getUid()).getValue(Users_Form.class); // get user id of the current user
                TextView Hello_Name = findViewById(R.id.hello_name);
                Hello_Name.setText("Hello, " + u.getFirstName());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(Home_users.this, "" + databaseError.toString(), Toast.LENGTH_LONG).show();
            }
        });

        //show the testurouns in the database on the page
        ref_rests.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //iterate on the Restourunt nodes
               for(DataSnapshot ds : dataSnapshot.getChildren()) {
                   String str = ds.getValue().toString();
                   rest_list.add(str);


               }
               //add the array(rest_list) on array addapter and then add the arraydapter to the list view
                rest_adapter = new ArrayAdapter<String>(Home_users.this, android.R.layout.simple_list_item_1, rest_list);
                listView.setAdapter(rest_adapter);
            }
            //if there is an error pulling data from the data base show messege
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(Home_users.this, "cant show resturuonts", Toast.LENGTH_LONG).show();

            }
        });


    }
    //click functoin on personal settings button
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
