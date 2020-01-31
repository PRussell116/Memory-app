package com.example.memoryapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


public class StartScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_screen);
        SeekBar diffBar;
        diffBar = findViewById(R.id.diffBar);
        diffBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener(){

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,boolean fromUser){
                TextView diffBox = findViewById(R.id.diffBox);
                /* set the value of the diff box to correct val */
                if(progress == 0) diffBox.setText("Easy");
                else if(progress == 1){
                    diffBox.setText("Medium");
                } else diffBox.setText("Hard");
            }
            public void onStartTrackingTouch(SeekBar seekBar) {
                /* TODO Auto-generated method stub */
            }
            public void onStopTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

        });

    }

    public void sendMessage(View view) {
        // find the difficulty
        TextView diffBox = findViewById(R.id.diffBox);
        CharSequence diffVal = diffBox.getText();



        Intent intent = new Intent(StartScreen.this, MainActivity.class);
        intent.putExtra("diffVal", diffVal);
        startActivity(intent);
    }
    // TODO ADD HOW TO PLAY PAGE

}
