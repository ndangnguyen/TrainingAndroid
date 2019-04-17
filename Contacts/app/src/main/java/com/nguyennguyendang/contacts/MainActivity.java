package com.nguyennguyendang.contacts;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ImageButton ibAdd, ibClear;
    ListView lvContact;
    EditText etSearchName;
    Person selectedPerson;
    CustomAdapterListViewContact adapterListViewContact;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        getViews();
        setViews();
        addViewsListener();
    }

    public void init() {
        new Contact();
        Contact.add(new Person("Nguyên", "0372500510"));
        Contact.add(new Person("Thành", "0987287502"));
        Contact.add(new Person("Chung", "0398341876"));
        Contact.add(new Person("Pháp", "0376782128"));
        Contact.add(new Person("Nghĩa", "035254365"));
        Contact.add(new Person("Tâm", "093645243"));
        Contact.add(new Person("Lâm", "0956633242"));
        Contact.add(new Person("Quyến", "023654236"));
        Contact.add(new Person("Huynh", "093654236"));
    }

    public void getViews() {
        lvContact = findViewById(R.id.lvContact);
        ibAdd = findViewById(R.id.ibAdd);
        ibClear = findViewById(R.id.ibClear);
        etSearchName = findViewById(R.id.etSearchName);
    }

    public void setViews() {
        setListView();
    }

    public void addViewsListener() {
        lvContact.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, Info.class);
                String name = lvContact.getItemAtPosition(position).toString();
                intent.putExtra("name", name);
                startActivity(intent);
            }
        });
        lvContact.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                selectedPerson = ((Person)lvContact.getItemAtPosition(position));
                return false;
            }
        });

        ibAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddContacPopup();
            }
        });

        etSearchName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                findPerson(etSearchName.getText().toString());
                if(s.length() > 0){
                    ibClear.setVisibility(View.VISIBLE);
                }else{
                    ibClear.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        ibClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etSearchName.setText(null);
            }
        });

        registerForContextMenu(lvContact);
    }

    public void showAddContacPopup() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        AlertDialog alertDialog;
        builder.setView(R.layout.add_contact_popup2);
        builder.setTitle("Add Contact");
        builder.setIcon(R.drawable.add_contact);
        builder.setPositiveButton("ADD", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                final EditText etName = ((AlertDialog) dialog).findViewById(R.id.etName);
                final EditText etPhone = ((AlertDialog) dialog).findViewById(R.id.etPhone);
                addNewPerson(etName.getText().toString().trim(), etPhone.getText().toString().trim());
                resetInputSearch();
            }
        });
        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        alertDialog = builder.create();
        alertDialog.show();
    }

    public void showEditContacPopup(String name, String phone){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        View customDialog = getLayoutInflater().inflate(R.layout.add_contact_popup2, null);
        final EditText etName = customDialog.findViewById(R.id.etName);
        final EditText etPhone = customDialog.findViewById(R.id.etPhone);

        builder.setView(customDialog);
        builder.setTitle("Edit Contact");
        builder.setIcon(R.drawable.add_contact);
        builder.setPositiveButton("SAVE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                selectedPerson.setName(etName.getText().toString().trim());
                selectedPerson.setPhone(etPhone.getText().toString().trim());
                setListView();
            }
        });
        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });


        AlertDialog alertDialog = builder.create();

        etName.setText(name);
        etPhone.setText(phone);

        alertDialog.show();
    }

    public void addNewPerson(String name, String phone) {
        Person person = new Person();
        person.setName(name);
        person.setPhone(phone);
        Contact.add(person);
        adapterListViewContact.notifyDataSetChanged();
    }

    public void setListView() {
        setListView(Contact.getList());
    }

    public void setListView(ArrayList<Person> listPerson) {
        adapterListViewContact = new CustomAdapterListViewContact(
                this,
                R.layout.custom_item_list_view,
                listPerson
        );
        lvContact.setAdapter(adapterListViewContact);
        adapterListViewContact.notifyDataSetChanged();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.context_menu, menu);
        menu.setHeaderTitle(selectedPerson.getName());
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.itDelete: {
                Contact.del(selectedPerson);
                setListView();
                break;
            }
            case R.id.itEdit: {
                showEditContacPopup(selectedPerson.getName(), selectedPerson.getPhone());
                break;
            }
        }
        return super.onContextItemSelected(item);
    }

    public void findPerson(String name) {
        if (name.trim().equals("")) {
            setListView(Contact.getList());
            adapterListViewContact.notifyDataSetChanged();
            return;
        }
        ArrayList<Person> foundList = new ArrayList<>();
        for (Person p : Contact.getList()) {
            if (p.getName().contains(name.trim())) foundList.add(p);
        }
        setListView(foundList);
    }

    public void resetInputSearch() {
        etSearchName.setText(null);
    }

    @Override
    public void onBackPressed() {
    }
}
