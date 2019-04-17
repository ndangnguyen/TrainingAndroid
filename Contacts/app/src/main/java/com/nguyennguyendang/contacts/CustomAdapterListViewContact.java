package com.nguyennguyendang.contacts;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class CustomAdapterListViewContact extends ArrayAdapter<Person> {

    Context context;
    ArrayList<Person> objects;
    public CustomAdapterListViewContact(Context context, int resource, ArrayList<Person> objects) {
        super(context, resource, objects);
        this.context = context;
        this.objects = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(R.layout.custom_item_list_view, null);

        Person currentPerson = objects.get(position);
        TextView tvName = convertView.findViewById(R.id.tvName);
        tvName.setText(currentPerson.getName());
        return convertView;
    }
}
