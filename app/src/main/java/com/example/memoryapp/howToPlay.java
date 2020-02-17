package com.example.memoryapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class howToPlay extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_how_to_play);
    }


    public void goToMainMenu(View view) {
        Intent intent = new Intent(howToPlay.this, StartScreen.class);
        startActivity(intent);
    }

}
