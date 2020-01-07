package com.example.busy.users;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.busy.R;

import java.util.ArrayList;

public class user_orders_history extends AppCompatActivity {
    private ListView list;
    private ArrayList<String> arr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_orders_history);
        list=findViewById(R.id.list_view_user_orders);
        arr = new ArrayList<String>();
        arr.add("tal");
        arr.add("amit");
        arr.add("shmuel");
        arr.add("nissim");
        arr.add("bat yam");
        ArrayAdapter<String> addapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,arr);
        list.setAdapter(addapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(user_orders_history.this,arr.get(i),Toast.LENGTH_SHORT).show();
            }
        });
    }

}
