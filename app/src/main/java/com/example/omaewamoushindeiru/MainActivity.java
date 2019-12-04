package com.example.omaewamoushindeiru;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    private MediaPlayer mediaPlayer;
    private TextView songName, songDuration;
    private SeekBar seekBar;
    private double timeStart=0,finalTime=0;
    private int forwardTime = 2000,backwardTime = 2000;
    private Handler durationHandler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        songName = (TextView)findViewById(R.id.songName);
        songDuration = (TextView)findViewById(R.id.songDuration);
        mediaPlayer = MediaPlayer.create(this,R.raw.onehourlilbom);
        seekBar = (SeekBar)findViewById(R.id.seekBar);
        songName.setText("[1 hour] Lil Boom - Already Dead (Omae Wa Mou) instrumental (prod. deadman 死人).mp3");
        seekBar.setMax((int)finalTime);
        seekBar.setClickable(false);
    }
    private Runnable updateSeekBarTime = new Runnable() {
        @Override
        public void run() {
            timeStart = mediaPlayer.getCurrentPosition();
            seekBar.setProgress((int)timeStart);
            double timeRemaining = finalTime-timeStart;
            songDuration.setText(String.format("%d min %d sec", TimeUnit.MILLISECONDS.toMinutes((long) timeRemaining),
                    TimeUnit.MILLISECONDS.toSeconds((long)timeRemaining)-TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long) timeRemaining))));
            durationHandler.postDelayed(this,100);
        }
    };
    public void play(View view){
        mediaPlayer.start();
        timeStart = mediaPlayer.getCurrentPosition();
        seekBar.setProgress((int)timeStart);
        durationHandler.postDelayed(updateSeekBarTime,100);
    }
    public void pause(View view){
        mediaPlayer.pause();
    }
    public void forward(View view){
        if((timeStart+forwardTime)>0){
            timeStart = timeStart+forwardTime;
            mediaPlayer.seekTo((int)timeStart);
        }
    }
    public void backforward(View view) {
//        check if we can go back at backwardTime seconds after song starts
        if ((timeStart-backwardTime)>0){
            timeStart = timeStart-backwardTime;
            mediaPlayer.seekTo((int)timeStart);
        }
    }
}

