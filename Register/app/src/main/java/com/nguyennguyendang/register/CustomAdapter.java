package com.nguyennguyendang.register;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class CustomAdapter extends ArrayAdapter<Record> {

    Activity context;
    int resource;
    ArrayList<Record> objects;
    public CustomAdapter(Activity context, int resource, List<Record> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.objects = (ArrayList) objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View customView;
        LayoutInflater inflater= context.getLayoutInflater();
        Record currentRecord = objects.get(position);
        customView = inflater.inflate(resource, null, false);
        TextView txtName = customView.findViewById(R.id.txtName);
        TextView txtEmail = customView.findViewById(R.id.txtEmail);
        TextView txtPhone = customView.findViewById(R.id.txtPhone);
        TextView txtPosition = customView.findViewById(R.id.txtPosition);
        TextView txtLocation = customView.findViewById(R.id.txtLocation);
        RadioButton rbMale = customView.findViewById(R.id.rbMale);
        RadioButton rbFemale = customView.findViewById(R.id.rbFemale);
        CheckBox cbEn = customView.findViewById(R.id.cbEn);
        CheckBox cbJp = customView.findViewById(R.id.cbJp);
        CheckBox cbFr = customView.findViewById(R.id.cbFr);

        txtName.setText(currentRecord.getName());
        txtEmail.setText(currentRecord.getEmail());
        txtPhone.setText(currentRecord.getPhone());
        txtPosition.setText(currentRecord.getPosition());
        txtLocation.setText(currentRecord.getLocation());

        if (currentRecord.getGender().equals("Male")) {
            rbMale.setChecked(true);
        }
        else {
            rbFemale.setChecked(true);
        }

        if (currentRecord.getLanguage().equals("English "))
        {
            cbEn.setChecked(true);
            cbFr.setChecked(false);
            cbJp.setChecked(false);
        }
        else if (currentRecord.getLanguage().equals("English Japanese ")) {
            cbEn.setChecked(true);
            cbJp.setChecked(true);
            cbFr.setChecked(false);
        }
        else {
            cbEn.setChecked(true);
            cbFr.setChecked(true);
            cbJp.setChecked(true);
        }
        return customView;
    }
}
