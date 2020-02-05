package com.example.memoryapp;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;

//TODO make the ui pretty
//  TODO REMOVE THE OVERWRITES IN UI XML AND FIND OTU HOW TO USE @STRING

public class scoreScreen extends AppCompatActivity {
    DatabaseHelper mDatabaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_screen);
        mDatabaseHelper = new DatabaseHelper(this);

        fillData();
    }

    public void backToMain(View view) {

        Intent intent = new Intent(scoreScreen.this, StartScreen.class);
        startActivity(intent);
    }

    private void fillData() {
        Cursor data = mDatabaseHelper.getData();
        // Log.d("","sql 1st row data:" + data.getString(1));
        //get top 5
        ArrayList<String> scoreData = new ArrayList<>();
        while (data.moveToNext()) {
            scoreData.add(data.getString(1));

        }
        // get most recent
        String latestScore = scoreData.get(scoreData.size() - 1);
        //insert into box
        TextView prevScoreBox = findViewById(R.id.previousScore);
        prevScoreBox.setText(latestScore);


        Collections.sort(scoreData);
        //get top 5
        ArrayList<String> topFive = new ArrayList<>(scoreData.subList(scoreData.size() - 5, scoreData.size()));
        Collections.reverse(topFive);
        //insert into page
        for (int i = 0; i < 5; i++) {
            String boxId = "score" + (i + 1);
            TextView currentBox = findViewById(getResources().getIdentifier(boxId, "id", this.getPackageName()));
            currentBox.setText(topFive.get(i));

        }


    }


}
