package com.example.a41passtask;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    EditText workouttype;
    Chronometer chronometer;
    TextView result;



    private boolean running;
    private long pause;
    private String lastactivity = "";
    private String activity = "";
    private String time = "";


    public static final String PREFS_NAME = "MyPrefsFile";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        chronometer = findViewById(R.id.chronometer);
        chronometer.setFormat("Time: %s");
        workouttype = findViewById(R.id.workouttype);
        result = findViewById(R.id.result);
        if(savedInstanceState != null)
        {
            pause = savedInstanceState.getLong("timeOnPause", 0);
            running = savedInstanceState.getBoolean("running", true);
            long getBaseTime = savedInstanceState.getLong("getBaseTime", 0);
            lastactivity = savedInstanceState.getString("lastactivity");
            activity = savedInstanceState.getString("activity");
            time = savedInstanceState.getString("time");

            result.setText(String.format("You spent %s on %s last time", time, lastactivity));
            if (running == true) {
                chronometer.start();
                chronometer.setBase(SystemClock.elapsedRealtime() - (SystemClock.elapsedRealtime() - getBaseTime));
            }
            else {
                chronometer.setBase(SystemClock.elapsedRealtime() - pause);
                chronometer.stop();
            }
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong("timeOnPause", pause);
        outState.putBoolean("running", running);
        outState.putLong("getBaseTime", chronometer.getBase());
        outState.putString("lastactivity", lastactivity);
        outState.putString("activity", activity);
        outState.putString("time", time);
    }


    public void startworkout(View v)
    {
        if (!running)
        {
            activity = workouttype.getText().toString();
            chronometer.setBase(SystemClock.elapsedRealtime() - pause);
            chronometer.start();
            running = true;
        }
    }

    public void pauseworkout(View v)
    {
        if (running)
        {
            chronometer.stop();
            pause = SystemClock.elapsedRealtime() - chronometer.getBase();
            running = false;
        }
    }

    public void stopworkout(View v) {
        if (running) {
            chronometer.stop();
            pause = 0;
            running = false;
            lastactivity = activity;
            activity = "";
            time = chronometer.getText().toString();
            result.setText(String.format("You spent %s on %s last time", time, lastactivity));
        }
    }
}