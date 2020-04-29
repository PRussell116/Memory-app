package com.example.memoryapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class howToPlay extends AppCompatActivity {
    int currentImg = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_how_to_play);

        ImageView iv = findViewById(R.id.helpImg);
        iv.setImageResource(R.drawable.helpimg1);


    }


    public void goToMainMenu(View view) {
        Intent intent = new Intent(howToPlay.this, StartScreen.class);
        startActivity(intent);
    }

    public void rightClick(View view) {
        if (currentImg == 4) {
            currentImg = 1;
        } else currentImg += 1;
        changeImg();

    }

    public void leftClick(View view) {
        if (currentImg == 1) {
            currentImg = 4;
        } else currentImg -= 1;
        changeImg();
    }

    public void changeImg() {
        ImageView iv = findViewById(R.id.helpImg);
        switch (currentImg) {
            case 1:
                iv.setImageResource(R.drawable.helpimg1);
                break;
            case 2:
                iv.setImageResource(R.drawable.helpimg2);
                break;
            case 3:
                iv.setImageResource(R.drawable.help3);
                break;
            case 4:
                iv.setImageResource(R.drawable.help4);
                break;
        }

    }

}

