package com.nguyennguyendang.musics;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomAdapter extends ArrayAdapter<Song> {
    Activity context;
    int resource;
    ArrayList<Song> objects;
    Song currentSong;
    public CustomAdapter(Activity context, int resource, ArrayList<Song> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.objects = objects;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = context.getLayoutInflater().inflate(resource,null);
        TextView tvName = convertView.findViewById(R.id.tvName);
        ImageView ivPlaying = convertView.findViewById(R.id.ivPlaying);
        currentSong = objects.get(position);
        tvName.setText(currentSong.getTitle());
        if (currentSong.isPlay) {
            ivPlaying.setVisibility(View.VISIBLE);
        }
        else {
            ivPlaying.setVisibility(View.GONE);
        }
        return convertView;
    }
}
