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
        ArrayList<Integer> scoreData = new ArrayList<>();
        while (data.moveToNext()) {
//            try {
                scoreData.add(data.getInt(1));
//            } catch (Exception e) {
                // add zero if exception, possible not enough data
//                scoreData.add(0);
//            }


        }

        // get most recent
        Integer latestScore;
        try {
            latestScore = scoreData.get(scoreData.size() - 1);
        } catch (Exception e) {
            latestScore = 0;
        }

        //insert into box
        TextView prevScoreBox = findViewById(R.id.previousScore);
        prevScoreBox.setText(String.valueOf(latestScore));


        // sort and order the score by descending
        Collections.sort(scoreData);
        Collections.reverse(scoreData);

        //insert into page
        for (int i = 0; i < 5; i++) {
            String boxId = "score" + (i + 1);
            TextView currentBox = findViewById(getResources().getIdentifier(boxId, "id", this.getPackageName()));
            try {
                currentBox.setText(String.valueOf(scoreData.get(i)));
            } catch (Exception e) {
                currentBox.setText("0");
            }


        }


    }


}
