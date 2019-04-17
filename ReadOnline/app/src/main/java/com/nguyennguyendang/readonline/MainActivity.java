package com.nguyennguyendang.readonline;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private final String FILE_URL = "https://www.dropbox.com/s/kv22szcpwsm0mca/Gameloft%20Privacy.txt?dl=1";

    private TextView tvContent;
    private ProgressBar pbWaiting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvContent = findViewById(R.id.tvContent);
        pbWaiting = findViewById(R.id.pbWaiting);

        tvContent.setVisibility(View.GONE);

        new ReadOnline().execute(FILE_URL);

        Handler mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case 0: {
                        
                    }
                }
            }
        };
    }

    private class ReadOnline extends AsyncTask<String, Void, String> {

        /**Explain more AsyncTask<String, Void, String>
         * String: need a url to read
         * Void: no need display in update
         * String: result
         */

        //start
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        //doing
        @Override
        protected String doInBackground(String... strs) {

            String content = "";
            HttpURLConnection httpURLConnection = null;
            try {

                URL url = new URL(strs[0]);

                httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("GET");
                httpURLConnection.connect();

                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader mReader = new BufferedReader(new InputStreamReader(inputStream));

                StringBuffer buffer = new StringBuffer();
                String line;

                while ((line = mReader.readLine()) != null) {
                    buffer.append(line + "\n");

                    //If you want to calculate % to display. You will do it here and call
                    //publishProgress();
                }

                content = buffer.toString();

                mReader.close();
                httpURLConnection.disconnect();
            } catch (Exception ex) {
                if (httpURLConnection != null) {
                    httpURLConnection.disconnect();
                }
                System.out.println("Download Lesson Error" + ex.toString());
            }
            return content;
        }




        //update
        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }


        //finish
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            tvContent.setText(s);

            tvContent.setVisibility(View.VISIBLE);
            pbWaiting.setVisibility(View.GONE);
        }
    }
}
