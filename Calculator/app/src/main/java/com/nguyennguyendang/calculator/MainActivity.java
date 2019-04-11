package com.nguyennguyendang.calculator;

import android.content.ClipData;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
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

public class MainActivity extends AppCompatActivity {

    EditText etNumberA, etNumberB;
    Button btnPlus, btnSub, btnMul, btnDiv;
    TextView txtResult;
    ListView lvResult;
    Double a,b;
    ArrayList<String> listResults;
    ArrayAdapter<String> adapterResults;
    String result;
    String selectResult;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.simple_calculator);
        etNumberA = findViewById(R.id.etNumberA);
        etNumberB = findViewById(R.id.etNumberB);
        btnPlus = findViewById(R.id.btnPlus);
        btnSub = findViewById(R.id.btnSub);
        btnMul = findViewById(R.id.btnMul);
        btnDiv = findViewById(R.id.btnDiv);
        txtResult = findViewById(R.id.txtResult);
        lvResult = findViewById(R.id.lvResult);

        initListView();
        registerForContextMenu(lvResult);

        btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateInput()) {
                    result = String.valueOf(a) + " + " + String.valueOf(b) + " = " ;
                    result += String.valueOf(a+b);
                    txtResult.setText(result);
                    showHistory(result);
                }
            }
        });
        btnSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateInput()) {
                    result = String.valueOf(a) + " - " + String.valueOf(b) + " = " ;
                    result += String.valueOf(a-b);
                    txtResult.setText(result);
                    showHistory(result);
                }
            }
        });
        btnMul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateInput()) {
                    result = String.valueOf(a) + " x " + String.valueOf(b) + " = " ;
                    result += String.valueOf(a*b);
                    txtResult.setText(result);
                    showHistory(result);
                }
            }
        });
        btnDiv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateInput()) {
                    try {
                        result = String.valueOf(a) + " / " + String.valueOf(b) + " = " ;
                        double s = a / b;
                        s = Math.round(s * 10) / 10.0;
                        result += String.valueOf(s);
                        txtResult.setText(result);
                        showHistory(result);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        lvResult.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                selectResult = lvResult.getItemAtPosition(position).toString();
                return false;
            }
        });

    }

    public boolean validateInput() {
        try {
            a = Double.valueOf(etNumberA.getText().toString());
            b = Double.valueOf(etNumberB.getText().toString());
            return true;
        }
        catch (Exception e){
            e.printStackTrace();
            Toast.makeText(this, "Invalid input", Toast.LENGTH_LONG).show();
        }
        return false;
    }

    public void initListView(){
        listResults = new ArrayList<>();
        adapterResults = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listResults);
        lvResult.setAdapter(adapterResults);
    }

    public void showHistory(String newResult){
        listResults.add(0, newResult);
        adapterResults.notifyDataSetChanged();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        switch (v.getId()) {
            case R.id.lvResult:
            {
                getMenuInflater().inflate(R.menu.menu_context, menu);
                menu.setHeaderTitle(selectResult);
                //head = menu.findItem(R.id.itHead);
                //head.setTitle(selectResult);
                //menu.add(Menu.NONE, menuDeleteId,Menu.NONE, menuDeleteTitle);
                break;
            }
        }
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()){
            case R.id.itDelete:
                adapterResults.remove(adapterResults.getItem(info.position));
                break;
        }
        return super.onContextItemSelected(item);
    }
}
