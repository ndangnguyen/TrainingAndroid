package com.nguyennguyendang.calculatorservice;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Activity2 extends AppCompatActivity {

    TextView txtNumberA, txtNumberB;
    Button btnPlus, btnSub, btnMul, btnDiv, btnSendResult;
    TextView txtResult;
    ListView lvResult;
    Double a,b;
    String result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);
        txtNumberA = findViewById(R.id.txtNumberA);
        txtNumberB = findViewById(R.id.txtNumberB);
        btnPlus = findViewById(R.id.btnPlus);
        btnSub = findViewById(R.id.btnSub);
        btnMul = findViewById(R.id.btnMul);
        btnDiv = findViewById(R.id.btnDiv);
        btnSendResult = findViewById(R.id.btnSendResult);
        txtResult = findViewById(R.id.txtResult);

        Intent myIntent = getIntent();
        a = myIntent.getDoubleExtra("a", 0);
        b = myIntent.getDoubleExtra("b", 0);

        txtNumberA.setText(String.valueOf(a));
        txtNumberB.setText(String.valueOf(b));

        btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                result = String.valueOf(a) + " + " + String.valueOf(b) + " = " ;
                result += String.valueOf(a+b);
                txtResult.setText(result);
            }
        });

        btnSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                result = String.valueOf(a) + " - " + String.valueOf(b) + " = " ;
                result += String.valueOf(a-b);
                txtResult.setText(result);
            }
        });

        btnMul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                result = String.valueOf(a) + " x " + String.valueOf(b) + " = " ;
                result += String.valueOf(a*b);
                txtResult.setText(result);
            }
        });

        btnDiv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    result = String.valueOf(a) + " / " + String.valueOf(b) + " = " ;
                    double s = a / b;
                    s = Math.round(s * 10) / 10.0;
                    result += String.valueOf(s);
                    txtResult.setText(result);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        btnSendResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateInput()) {
                    Intent intent = new Intent(Activity2.this, Activity1.class);
                    intent.putExtra("result", result);
                    startActivity(intent);
                }
                else Toast.makeText(Activity2.this, "Empty result!!!", Toast.LENGTH_LONG).show();
            }
        });

    }

    public boolean validateInput() {
        return result != null;
    }
}
