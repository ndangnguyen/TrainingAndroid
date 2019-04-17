package com.nguyennguyendang.musics;

public class Song {
    private String mLink, mTitle;
    boolean isPlay;

    public Song(String mTitle, String mLink) {
        this.mLink = mLink;
        this.mTitle = mTitle;
        isPlay = false;
    }

    public String getLink() {
        return mLink;
    }

    public void setLink(String mLink) {
        this.mLink = mLink;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public boolean isPlay() {
        return isPlay;
    }

    public void setPlay(boolean isPlay) {
        this.isPlay = isPlay;
        for (Song song : ListSong.getListSongs()) {
            if (song != this){
                song.isPlay = false;
            }
        }
    }

    @Override
    public String toString() {
        return getTitle();
    }


}
