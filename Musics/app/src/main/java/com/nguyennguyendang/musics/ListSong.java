package com.nguyennguyendang.musics;

import java.util.ArrayList;

public class ListSong {

    public ListSong() {
        if (listSongs == null) {
            listSongs = new ArrayList<>();
        }
    }

    public static ArrayList<Song> getListSongs() {
        return listSongs;
    }

    public static void setListSongs(ArrayList<Song> listSongs) {
        ListSong.listSongs = listSongs;
    }

    private static ArrayList<Song> listSongs;

    public static void add(Song song) {
        listSongs.add(song);
    }

    public static int getSize() {
        return listSongs.size();
    }

}
