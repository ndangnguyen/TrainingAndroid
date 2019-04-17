package com.nguyennguyendang.contacts;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Telephony;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class Info extends AppCompatActivity {

    String name, phone;
    TextView txtName, txtPhone;
    ImageButton ibCall, ibMess;
    Person currentPerson;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        init();
        getViews();
        setViews();
        addListener();
    }

    private void init() {

    }

    private void getViews() {
        txtName = findViewById(R.id.txtName);
        txtPhone = findViewById(R.id.txtPhone);
        ibCall = findViewById(R.id.ibCall);
        ibMess = findViewById(R.id.ibMess);
    }

    private void setViews() {

    }

    private void addListener() {
        ibCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent dialIntent = new Intent(Intent.ACTION_DIAL);
                dialIntent.setData(Uri.parse("tel:" + currentPerson.getPhone()));
                startActivity(dialIntent);
            }
        });

        ibMess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) // At least KitKat
                {
                    String defaultSmsPackageName = Telephony.Sms.getDefaultSmsPackage(Info.this); // Need to change the build to API 19

                    Intent sendIntent = new Intent(Intent.ACTION_SEND);
                    sendIntent.setType("text/plain");
                    sendIntent.putExtra(Intent.EXTRA_TEXT, "text");

                    if (defaultSmsPackageName != null)// Can be null in case that there is no default, then the user would be able to choose
                    {
                        sendIntent.setPackage(defaultSmsPackageName);
                    }
                    startActivity(sendIntent);
                }
                else // For early versions, do what worked for you before.
                {
                    Intent smsIntent = new Intent(android.content.Intent.ACTION_VIEW);
                    smsIntent.setType("vnd.android-dir/mms-sms");
                    smsIntent.putExtra("address", currentPerson.getPhone());
                    smsIntent.putExtra("sms_body","message");
                    startActivity(smsIntent);
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        Intent myIntent = getIntent();
        name = myIntent.getStringExtra("name");
        for (Person p : Contact.getList()) {
            if (p.getName().equals(name)) {
                currentPerson = p;
                txtName.setText(p.getName());
                txtPhone.setText("Phone: "+p.getPhone());
            }
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }

}
