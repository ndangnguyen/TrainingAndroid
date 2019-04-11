package com.nguyennguyendang.register;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class RegisterList extends AppCompatActivity {

    ListView listView;
    ArrayList<Record> arrayList;
    CustomAdapter arrayAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_register_list);
//        listView = findViewById(R.id.listView);
//        arrayList = Record.getListRecord();
//        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arrayList);
//        listView.setAdapter(arrayAdapter);
//        setTitle("Register List");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_list);
        listView = findViewById(R.id.listView);
        arrayList = Record.getListRecord();
        arrayAdapter = new CustomAdapter(this, R.layout.custom_item_list_view, arrayList);
        listView.setAdapter(arrayAdapter);
        setTitle("Register List");
    }
}
