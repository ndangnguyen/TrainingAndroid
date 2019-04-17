package com.nguyennguyendang.musics;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.leakcanary.RefWatcher;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;

public class SplashActivity extends AppCompatActivity {

    private static final String JSON_DIR = "songs.json";
    private ImageView ivLoading;
    private LoadSongs loadSongs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ivLoading = findViewById(R.id.ivLoading);
        new ListSong();
        loadSongs = new LoadSongs(this, ivLoading);
        loadSongs.execute();
    }

    private static class LoadSongs extends AsyncTask<Void, Integer, Void>{
        Context context;
        ImageView ivLoading;

        public LoadSongs(Context context, ImageView ivLoading) {
            this.context = context;
            this.ivLoading = ivLoading;
        }

        @Override
        protected Void doInBackground(Void... aVoid){
            JSONObject jsonRoot;
            int progress = 0;
            try {
                jsonRoot = new JSONObject(loadJsonData(context, JSON_DIR));
                JSONArray jsonSongs = jsonRoot.getJSONArray("songs");
                Song[] songs = new Song[jsonSongs.length()];
                for (int i = 0; i<= songs.length; i++) {
                    JSONObject songObject = jsonSongs.getJSONObject(i);
                    songs[i] = new Song(songObject.getString("title"), songObject.getString("link"));
                    ListSong.add(songs[i]);
                }
            }
            catch (Exception e) {e.printStackTrace();}

            //fake loading (because of the small data)
            try {
                for (int i = 0; i<= 50; i++) {
                    publishProgress(progress+= 5);
                    Thread.sleep(30);
                }
            }
            catch (Exception e) {e.printStackTrace();}
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            ivLoading.setRotation(values[0]);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Intent intent = new Intent(context, MainActivity.class);
            context.startActivity(intent);
            ((Activity)context).finish();
        }
    }

    private static String loadJsonData(Context context, String dir) {
        String content = "";
        try {
            InputStream is = context.getAssets().open(dir);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            content = new String(buffer, "utf-8");
        }
        catch (Exception e) {e.printStackTrace();}
        return content;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (loadSongs != null) loadSongs.cancel(true);
    }
}
