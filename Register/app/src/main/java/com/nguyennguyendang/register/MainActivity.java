package com.nguyennguyendang.register;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText etFullname, etEmail, etPhone;
    RadioButton rbMale, rbFemale;
    Spinner spPosition, spLocation;
    CheckBox cbEn, cbJp, cbFr;
    Button btnApply, btnViewList;

    ArrayList<String> listPosition = null, listLocation = null;
    ArrayAdapter<String> adapterPosition = null, adapterLocation = null;
    Record newRecord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        newRecord = new Record();

        etFullname = findViewById(R.id.etFullname);
        etEmail = findViewById(R.id.etEmail);
        etPhone = findViewById(R.id.etPhone);
        rbMale = findViewById(R.id.rbMale);
        rbFemale = findViewById(R.id.rbFemale);
        spPosition = findViewById(R.id.spPosition);
        spLocation = findViewById(R.id.spLocation);
        cbEn = findViewById(R.id.cbEn);
        cbJp = findViewById(R.id.cbJp);
        cbFr = findViewById(R.id.cbFr);
        btnApply = findViewById(R.id.btnApply);
        btnViewList = findViewById(R.id.btnViewList);

        setLocationSpinner();
        setPositionSpinner();

        btnApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateInput()) {
                    Toast.makeText(MainActivity.this, "Register successful!", Toast.LENGTH_LONG).show();
                    getData();
                    Record.addRecord(newRecord);
                    resetInput();
                } else {
                    Toast.makeText(MainActivity.this, "Check input!", Toast.LENGTH_LONG).show();
                }
            }
        });
        btnViewList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RegisterList.class);
                startActivity(intent);
            }
        });

        rbFemale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rbMale.setChecked(false);
            }
        });

        rbMale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rbFemale.setChecked(false);
            }
        });
    }

    public void setLocationSpinner() {
        listLocation = new ArrayList<>();
        listLocation.add("Ha Noi");
        listLocation.add("Da Nang");
        listLocation.add("Ho Chi Minh");

        adapterPosition = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listLocation);
        adapterPosition.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spLocation.setAdapter(adapterPosition);
        spLocation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                newRecord.setLocation(spLocation.getItemAtPosition(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void setPositionSpinner() {
        listPosition = new ArrayList<>();
        listPosition.add("Android Devloper");
        listPosition.add("IOS Developer");
        listPosition.add("Java Developer");
        listPosition.add("Game Developer");

        adapterPosition = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listPosition);
        adapterPosition.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spPosition.setAdapter(adapterPosition);
        spPosition.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                newRecord.setPosition(spPosition.getItemAtPosition(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public boolean validateInput() {
        if (etFullname.getText().toString().trim().equals("")) return false;
        if (etEmail.getText().toString().trim().equals("")) return false;
        if (etPhone.getText().toString().trim().equals("")) return false;
        if (!rbFemale.isChecked() && !rbMale.isChecked() ) return false;
        if (!cbEn.isChecked() && !cbFr.isChecked() && !cbJp.isChecked()) return  false;
        return true;
    }

    public void getData() {
        newRecord.setName(etFullname.getText().toString().trim());
        newRecord.setEmail(etEmail.getText().toString().trim());
        newRecord.setPhone(etPhone.getText().toString().trim());
        if (rbMale.isChecked()) {
            newRecord.setGender("Male");
        }
        else {
            newRecord.setGender("Female");
        }
        String tempLang = "";
        if (cbEn.isChecked()) tempLang += "English ";
        if (cbJp.isChecked()) tempLang += "Japanese ";
        if (cbFr.isChecked()) tempLang += "French";
        newRecord.setLanguage(tempLang);
    }

    public void resetInput(){
        newRecord = new Record();
        etFullname.setText(null);
        etPhone.setText(null);
        etEmail.setText(null);
        rbMale.setChecked(false);
        rbFemale.setChecked(false);
        spLocation.setSelection(0);
        spPosition.setSelection(0);
        cbFr.setChecked(false);
        cbJp.setChecked(false);
        cbEn.setChecked(false);
    }
}
