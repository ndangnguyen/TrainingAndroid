package com.nguyennguyendang.musics;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.squareup.leakcanary.RefWatcher;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    public static final int PAUSE = 0;
    public static final int UPDATE_STATUS = 1;
    public static final int END = 2;
    public static final int NEXT_SONG = 3;

    private ListView lvSongs;
    private CustomAdapter customAdapter;
    private MediaPlayer mediaPlayer;
    private ImageButton ibPause, ibPlay, ibBack, ibNext, ibRepeat, ibNoRepeat;
    private TextView tvDuration, tvCurrentPos;
    private SeekBar sbPlay;

    private Handler mHandler;
    private Thread mThread;

    private Song currentSong;

    private boolean isClickedSeekbar = false;
    private boolean isRepeat = false;
    private boolean isFirstLauch = true;
    private int currentSongID = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        getViews();
        setViews();
        addListener();
    }

    private void init() {
        mediaPlayer = new MediaPlayer();
        customAdapter = new CustomAdapter(this, R.layout.custom_list_view, ListSong.getListSongs());
        initMusicController();
    }

    private void getViews() {
        lvSongs = findViewById(R.id.lvSongs);
        ibPause = findViewById(R.id.ibPause);
        ibPlay = findViewById(R.id.ibPlay);
        ibBack = findViewById(R.id.ibBack);
        ibNext = findViewById(R.id.ibNext);
        ibRepeat = findViewById(R.id.ibRepeat);
        ibNoRepeat = findViewById(R.id.ibNoRepeat);
        tvCurrentPos = findViewById(R.id.tvCurrentPos);
        tvDuration = findViewById(R.id.tvDuration);
        sbPlay = findViewById(R.id.sbPlay);
    }

    private void setViews() {
        lvSongs.setAdapter(customAdapter);
        sbPlay.setEnabled(false);
    }

    private void addListener() {
        lvSongs.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                currentSongID = position;
                currentSong = (Song) lvSongs.getItemAtPosition(currentSongID);
                playSong(currentSong);
            }
        });

        ibPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ibPause.setVisibility(View.GONE);
                ibPlay.setVisibility(View.VISIBLE);
                mediaPlayer.pause();
            }
        });

        ibPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isFirstLauch) {
                    playSong(ListSong.getListSongs().get(0));
                    ibPause.setVisibility(View.VISIBLE);
                    ibPlay.setVisibility(View.GONE);
                    return;
                }

                ibPause.setVisibility(View.VISIBLE);
                ibPlay.setVisibility(View.GONE);
                mediaPlayer.start();
                lvSongs.setSelection(currentSongID);
            }
        });

        ibBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentSongID--;
                if (currentSongID < 0) currentSongID = 0;
                currentSong = ListSong.getListSongs().get(currentSongID);
                playSong(currentSong);
                ibPlay.performClick();
            }
        });

        ibNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentSongID++;
                if (currentSongID >= ListSong.getSize()) currentSongID = 0;
                currentSong = ListSong.getListSongs().get(currentSongID);
                playSong(currentSong);
                ibPlay.performClick();
            }
        });

        ibRepeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ibRepeat.setVisibility(View.GONE);
                ibNoRepeat.setVisibility(View.VISIBLE);
                isRepeat = false;
            }
        });

        ibNoRepeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ibNoRepeat.setVisibility(View.GONE);
                ibRepeat.setVisibility(View.VISIBLE);
                isRepeat = true;
            }
        });

        sbPlay.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                isClickedSeekbar = true;
                return false;
            }
        });

        sbPlay.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progress;
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                this.progress = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                seekTo(progress);
                updateStatus();
                isClickedSeekbar = false;
            }
        });

        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case UPDATE_STATUS: {
                        updateStatus();
                        break;
                    }
                }
            }
        };
    }

    private void initMusicController(){
         mThread = new Thread(new Runnable() {
            @Override
            public void run() {
                Message message;
                while (true) {
                    if (Thread.interrupted()) return;
                    try {
                        if (mediaPlayer!= null && mediaPlayer.isPlaying() && !isClickedSeekbar) {
                            // update seek bar and status
                            message = new Message();
                            message.what = UPDATE_STATUS;
                            mHandler.sendMessage(message);
                        }
                        Thread.sleep(1000);
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
         mThread.start();
    }

    private boolean playSong(final Song song) {
        if (isFirstLauch) {
            ibPause.setVisibility(View.VISIBLE);
            ibPlay.setVisibility(View.GONE);
            isFirstLauch = false;
            sbPlay.setEnabled(true);
        }

        song.setPlay(true);
        customAdapter.notifyDataSetChanged();

        if (mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer.release();
        }
        try {
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            AssetFileDescriptor afd = getAssets().openFd(song.getLink());
            mediaPlayer.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
            afd.close();
            mediaPlayer.prepare();
            mediaPlayer.setVolume(1f, 1f);
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    setDuration();
                    updateStatus();
                    mediaPlayer.start();
                }
            });
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    if (!isRepeat) currentSongID++;
                    if (currentSongID >= ListSong.getSize()) currentSongID = 0;
                    currentSong = ListSong.getListSongs().get(currentSongID);
                    playSong(currentSong);
                }
            });
            return true;
        }
        catch (Exception e){
            e.printStackTrace();
            Log.d("Nguyen","Not play");
            return false;
        }
    }

    private void seekTo(int i) {
        mediaPlayer.seekTo(i);
    }

    private void setDuration() {
        int duration = mediaPlayer.getDuration();
        tvCurrentPos.setText("00:00");
        tvDuration.setText(convertToDate(duration));
        sbPlay.setMax(duration);
    }
    private String convertToDate(int time) {
        Date date = new Date(time);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");
        return simpleDateFormat.format(date);
    }

    private void updateStatus() {
        int currentPos = mediaPlayer.getCurrentPosition();
        tvCurrentPos.setText(convertToDate(currentPos));
        sbPlay.setProgress(currentPos);
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Do you want to quit?");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mediaPlayer.stop();
                mediaPlayer.release();
                finish();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mThread.interrupt();
        RefWatcher refWatcher = MyApplication.getRefWatcher(MainActivity.this);
        refWatcher.watch(this);
    }
}
