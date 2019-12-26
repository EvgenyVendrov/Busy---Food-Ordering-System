package com.example.busy.users;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.busy.R;
import com.example.busy.users.Uform.Users_Form;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Home_users extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    private Users_Form u;
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();//the current online user
    private DatabaseReference ref_users; //the reference for Users realtimedatabase
    private DatabaseReference ref_rests; //the reference for Restaurant realtimedatabase
    private ListView listView;
    private ArrayList<String> rest_list = new ArrayList<>(); //will contains the data of all the restourounts
    private ArrayAdapter<String> rest_adapter; //the addapter that will get the rest_list and will be added to the list view
    private String text;
    private Button searchbtn;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_users);

        //Cities Spinner
        Spinner Cities_spinner = findViewById(R.id.Cities_spinner);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.Cities, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Cities_spinner.setAdapter(adapter);
        Cities_spinner.setOnItemSelectedListener(this);


        //INIT
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



        //show the resturouns in the database on the page
       searchbtn = findViewById(R.id.search);


    }

    public void getData(View v) {

        Query query = ref_rests.orderByChild("location").equalTo(text);// order the database by location
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (!rest_list.isEmpty()) {
                    rest_list.clear();
                    rest_adapter.clear();
                    listView.clearAnimation();
                }

                if(dataSnapshot.exists()) { //if there is a restourants in this area
                    String str;
                    for(DataSnapshot db : dataSnapshot.getChildren()){
                        str = db.child("name").getValue(String.class); //get name of the restourants
                        rest_list.add(str);
                    }
                        rest_adapter = new ArrayAdapter<String>(Home_users.this, R.layout.cutsumefont, rest_list);
                        listView.setAdapter(rest_adapter);



                }
                else{
                    Toast.makeText(Home_users.this, "No Restaurant in this location! ", Toast.LENGTH_LONG).show();
                    rest_list.add("no restourants here");
                    rest_adapter = new ArrayAdapter<String>(Home_users.this, R.layout.cutsumefont, rest_list);
                    listView.setAdapter(rest_adapter);
                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(Home_users.this, "cant show resturuonts", Toast.LENGTH_LONG).show();

            }
        });
    }

    //click functoin on personal settings button
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.personal:
                Intent i = new Intent(Home_users.this, personal_settings.class);
                startActivity(i);
                break;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        text = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(), text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

}
