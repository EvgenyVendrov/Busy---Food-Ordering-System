package com.example.busy.restaurant.update;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.busy.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class rest_update extends AppCompatActivity {
    private TextView edit_description;
    private TextView edit_isKosher;
    private TextView edit_Type;
    private TextView edit_Location;
    private TextView edit_Name;
    private TextView edit_Phone;
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private DatabaseReference ref_users;
    private Button logout_btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rest_update);

        //init the vals
        ref_users = FirebaseDatabase.getInstance().getReference("Users");
        edit_description = findViewById(R.id.Description_view);
        edit_isKosher = findViewById(R.id.isKosher_view);
        edit_Type = findViewById(R.id.Type_view);
        edit_Location = findViewById(R.id.Location_view);
        edit_Name = findViewById(R.id.Name_view);
        edit_Phone = findViewById(R.id.Phone_view);
        logout_btn = (Button) findViewById(R.id.Logout_btn);

        //this will make the relevant rest data to appear in the activity
        ref_users.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Restaurant_Form curr_user = dataSnapshot.child(user.getUid()).getValue(Restaurant_Form.class);
                edit_description.setText("");

                //                Address_form curr_add = curr_user.getAddress();
//                edit_fn.setText("Name: " + curr_user.getFirstName() + " " + curr_user.getLastName());
//                edit_email.setText("Email: " + curr_user.getEmail());
//                edit_pass.setText("password: " + curr_user.getPassword());
//                edit_address.setText("address: city: " + curr_add.getCity() + " street: " + curr_add.getStreet() + " house number: " + curr_add.getHouse_num()
//                        + " phone number: " + curr_add.getPhone_num());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}