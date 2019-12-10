package com.example.memoryapp;

import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;

import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.SeekBar.OnSeekBarChangeListener;


public class StartScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_screen);
        SeekBar diffBar;
        diffBar = (SeekBar) findViewById(R.id.diffBar);
        diffBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener(){

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,boolean fromUser){
                TextView diffBox = findViewById(R.id.diffBox);
                /* set the value of the diff box to correct val */
                if(progress == 0) diffBox.setText("Easy");
                else if(progress == 1){
                    diffBox.setText("medium");
                }
                else{
                    diffBox.setText("Hard");
                }
            }
            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }
            public void onStopTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

        });

    }
}
