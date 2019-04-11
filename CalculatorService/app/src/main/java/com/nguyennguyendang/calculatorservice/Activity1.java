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

public class Activity1 extends AppCompatActivity {

    EditText etNumberA, etNumberB;
    Button btnSend;
    TextView txtResult;
    ListView lvResult;
    Double a,b;
    static ArrayList<String> listResults;
    static ArrayAdapter<String> adapterResults;
    String result;
    String selectResult;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_1);
        etNumberA = findViewById(R.id.etNumberA);
        etNumberB = findViewById(R.id.etNumberB);
        txtResult = findViewById(R.id.txtResult);
        lvResult = findViewById(R.id.lvResult);
        btnSend = findViewById(R.id.btnSend);

        initListView();
        registerForContextMenu(lvResult);

        lvResult.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                selectResult = lvResult.getItemAtPosition(position).toString();
                return false;
            }
        });

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateInput()) {
                    Intent intent = new Intent(Activity1.this, Activity2.class);
                    intent.putExtra("a", a);
                    intent.putExtra("b", b);
                    startActivity(intent);
                }
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
        if (listResults == null) listResults = new ArrayList<>();
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

    @Override
    protected void onResume() {
        super.onResume();
        Intent myIntent = getIntent();

        result = myIntent.getStringExtra("result");
        if (result != null) {
            showHistory(result);
        }
    }
}
