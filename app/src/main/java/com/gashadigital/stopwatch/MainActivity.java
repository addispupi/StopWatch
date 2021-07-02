package com.gashadigital.stopwatch;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    Button btnStart, btnStop, btnReset;
    private int seconds = 0;

    private boolean running;
    private boolean wasRunning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnStart = findViewById(R.id.start);
        btnStop = findViewById(R.id.stop);
        btnReset = findViewById(R.id.reset);

        if(savedInstanceState != null){
            seconds = savedInstanceState.getInt("seconds");
            running = savedInstanceState.getBoolean("running");
            wasRunning = savedInstanceState.getBoolean("wasRunning");
        }
        runTimer();

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(btnStart.getText().toString() == getString(R.string.start)){
                    seconds = 0;
                    running = true;
                    btnStart.setText(R.string.pause);
                }
                else if(btnStart.getText().toString() == getString(R.string.pause)){
                    running = false;
                    btnStart.setText(R.string.resume);
                }
                else if(btnStart.getText().toString() == getString(R.string.resume)){
                    running = true;
                    btnStart.setText(R.string.pause);
                }

            }
        });
        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                running = false;
                btnStart.setText(R.string.start);
            }
        });
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                running = false;
                seconds = 0;
                btnStart.setText(R.string.start);
            }
        });
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState, @NonNull PersistableBundle outPersistentState) {
        outState.putInt("seconds", seconds);
        outState.putBoolean("running", running);
        outState.putBoolean("wasRunning", wasRunning);
        super.onSaveInstanceState(outState, outPersistentState);
    }

    protected void onPause(){
        super.onPause();
        wasRunning = running;
        running = false;
    }

    protected void onResume(){
        super.onResume();
        if(wasRunning){
            running = true;
        }
    }

    private void runTimer(){
        final TextView timeField = findViewById(R.id.timeField);
        final Handler handler = new Handler();

        handler.post(new Runnable() {
            @Override
            public void run() {
                int hours = seconds/36000;
                int minutes = (seconds % 3600)/60;
                int sec = seconds % 60;

                String time = String.format(Locale.getDefault(), "%d:%02d:%2d", hours, minutes, sec);
                timeField.setText(time);

                if (running){
                    seconds++;
                }
                handler.postDelayed(this,1000);
            }
        });
    }

}