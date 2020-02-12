package com.example.memoryapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Objects;
import java.util.Random;

import static java.lang.String.format;



public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    public ArrayList pattern;
    protected int winStreak = 0;
    protected int patternLength = 2;
    public int gridSize = 4;//default grid size 4 for easy
    DatabaseHelper mDatabaseHelper;
    String diffValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDatabaseHelper = new DatabaseHelper(this);
        setContentView(R.layout.activity_main);

        // Capture our button from layout
        Button button = findViewById(R.id.grid00);
        // Register the onClick listener with the implementation above
        button.setOnClickListener(this);
        // Log.i("pattern","pattern:" + pattern);
        // find the value of the difficulty from the activity

        diffValue = Objects.requireNonNull(getIntent().getCharSequenceExtra("diffVal")).toString();
        if (diffValue.equals("Difficulty")) {
            diffValue = "Easy";
        }
        // turn the right boxes visible
        changeDiff();

        pattern = pickPattern(2, gridSize, diffValue);


    }

    public void StartClick(View v) {
        // get new pattern (pattern length and gridSize will be based on difficulty)
        int gridSize;
        if (diffValue.equals("Medium")) {
            gridSize = 9;
        } else if (diffValue.equals("Hard")) {
            gridSize = 36;
        } else {
            gridSize = 4;
        }
        pattern = pickPattern(patternLength, gridSize, diffValue);

        //call function to turn pattern boxes blue
        showPattern(pattern, false);
        //lock the buttons on screen so they cannot be pressed
        buttonLock(false, true, true);

        // timer to delay the reset
        new android.os.Handler().postDelayed(
                () -> {
                    // turn the blue boxes back to white
                    showPattern(pattern, true);


                    //unlock the buttons but lock the start button
                    buttonLock(true, false, true);
                }, 5000);


    }


    public void showPattern(ArrayList inputPattern, Boolean reset) {


        for (int i = 0; i < inputPattern.size(); i++) {
            String currentButName = (String) pattern.get(i);
            // get the button widget
            Button currentBut;
            currentBut = findViewById(getResources().getIdentifier(currentButName, "id", this.getPackageName()));
            if (!reset) currentBut.setBackgroundColor(Color.BLUE);
            else {
                currentBut.setBackgroundColor(Color.parseColor("#A9A9A9"));
            }
        }

    }



    // Implement the OnClickListener callback
    public void onClick(View v) {

        ColorDrawable viewColor = (ColorDrawable) v.getBackground();
        int colorId = viewColor.getColor();
        if (colorId == Color.BLUE) {
            v.setBackgroundColor(Color.parseColor("#A9A9A9"));
        } else v.setBackgroundColor(Color.BLUE);
    }

    @SuppressLint("SetTextI18n")
    public void submitClick(View v) {

        // collect all the selected grid boxes
        ArrayList<String> gridBoxes;
        gridBoxes = new ArrayList<>();


        for (int i = 0; i < Math.pow(gridSize, 0.5); i++) {
            for (int j = 0; j < Math.pow(gridSize, 0.5); j++) { // root of grid size to find the x and y
                // create the x and y coordinates of the panel
                String currentBoxId;
                if (diffValue.equals("Medium")) {
                    currentBoxId = "gridMED" + i + j;
                } else if (diffValue.equals("Hard")) {
                    currentBoxId = "gridHard" + i + j;
                } else {
                    currentBoxId = "grid" + i + j;
                }
                Button currentBut;
                currentBut = findViewById(getResources().getIdentifier(currentBoxId, "id", this.getPackageName()));
                // if its blue add it to the list of selected
                ColorDrawable viewColor = (ColorDrawable) currentBut.getBackground();
                int colorId = viewColor.getColor();
                if (colorId == Color.BLUE) {
                    gridBoxes.add(currentBoxId);
                    // reset the box to white
                    currentBut.setBackgroundColor(Color.parseColor("#A9A9A9"));
                }
            }
        }
        Log.i("pattern", "pattern:" + pattern);
        Log.i("submit results", "results" + gridBoxes);

        // sort the lists
        Collections.sort(pattern);
        Collections.sort(gridBoxes);


        // check if submitted equals the pattern
        final TextView patternResultTextBox = findViewById(R.id.submitResults);
        patternResultTextBox.setVisibility(View.VISIBLE);
        if (gridBoxes.equals(pattern)) {
            Log.i("Array match", "true");
            patternResultTextBox.setText("Correct");
            patternResultTextBox.setTextColor(Color.GREEN);
            patternResultTextBox.bringToFront();
            // increase the win streak and pattern len if too long increase the difficulty (more boxes)
            winStreak++;
            patternLength++;


            if (patternLength > 3 && diffValue.equals("Easy")) {
                // set diff to medium
                diffValue = "Medium";
                // turn boxes invisible and medium to visible
                changeDiff();


            } else if (patternLength > 7 && diffValue.equals("Medium")) {
                // set diff to hard
                diffValue = "Hard";
                // turn boxes invisible and hard to visible
                changeDiff();
            } else if (patternLength > 20) {
                Random r = new Random();
                patternLength = r.nextInt(30); //change so its only long pattern
            }

            // when max pattern len on hardest diff is reached prevent going too high, choose random long pattern


        } else {
            Log.i("Array match", "false");
            patternResultTextBox.setTextColor(Color.RED);

            patternResultTextBox.setText(format("You scored: %d", winStreak));
            patternResultTextBox.bringToFront();
            // store the score and date

            DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
            Date dateObj = new Date();
            String formattedDate = df.format(dateObj);

            addData(winStreak, formattedDate);

            winStreak = 0;



            /* after losing go to main menu after a delay */

            Handler handler = new Handler();
            handler.postDelayed(this::goToMainMenu, 1000);

        }
        // hide the result box after some timer
        new android.os.Handler().postDelayed(
                () -> {
                    // turn the blue boxes back to white
                    patternResultTextBox.setVisibility(View.INVISIBLE);
                    // set pattern to null so you cannot repeat the last input
                    buttonLock(true, false, false);
                }, 1000);


    }

    public ArrayList<String> pickPattern(int patternLength, int gridSize, String diff) {
        // make array of all possible choices
        ArrayList<String> possibleBoxes;
        possibleBoxes = new ArrayList<>();
        for (int i = 0; i < Math.sqrt(gridSize); i++) {
            for (int j = 0; j < Math.sqrt(gridSize); j++) {
                if (diff.equals("Medium")) {
                    possibleBoxes.add("gridMED" + i + j);
                } else if (diff.equals(("Hard"))) {
                    possibleBoxes.add("gridHard" + i + j);
                } else {
                    possibleBoxes.add("grid" + i + j);
                }
            }
        }
        // initialize the array for storing the pattern
        ArrayList<String> patternList;
        patternList = new ArrayList<>();
        for (int i = 0; i < patternLength; i++) {
            // pick random option from possibleBoxes and remove from the list
            int choiceIndex = (int) (possibleBoxes.size() * Math.random());
            patternList.add(possibleBoxes.get(choiceIndex));
            possibleBoxes.remove(choiceIndex);
        }
        return patternList;
    }

    public void goToMainMenu() {
        Intent intent = new Intent(MainActivity.this, StartScreen.class);
        startActivity(intent);
    }


    public void buttonLock(Boolean lockState, boolean lockSub, Boolean lockStart) {
        // collect all buttons

        ViewGroup screenContainer = findViewById(R.id.screenContainer);
        // loop children of container
        for (int i = 0; i < screenContainer.getChildCount(); i++) {
            View currentBut = screenContainer.getChildAt(i);
            currentBut.setEnabled(lockState);


        }//only lock the grid buttons and start button
        if (lockSub) {
            View submit = findViewById(R.id.submitBut);
            submit.setEnabled(false);
        }
        if (lockStart) {
            View start = findViewById(R.id.StartBut);
            start.setEnabled(false);
        }
        // lock the start button so you cannot get a new sequence

    }


    public void addData(int newEntry, String date) {
        boolean insertData = mDatabaseHelper.addData(newEntry, date);
        if (insertData) {
            Log.d("", "addData: data inserted");
        } else {
            Log.d("", "addData: data failed to insert");
        }
        /*
        database stored in emulator

        /data/data/com.example.memoryapp/databases/scoreTable

        */

    }

    public void changeDiff() {
        //TODO change ui to use tables
        //collect all the easy diff boxes
        int[] easyBoxes = {R.id.grid00, R.id.grid01, R.id.grid10, R.id.grid11};
        // collect all the medium diff boxes
        int[] medBoxes = {R.id.gridMED00, R.id.gridMED01, R.id.gridMED02, R.id.gridMED10, R.id.gridMED11, R.id.gridMED12, R.id.gridMED20, R.id.gridMED21, R.id.gridMED22};


        ViewGroup hardBoxContainter = findViewById(R.id.hardBoxContainer);
        hardBoxContainter.setVisibility(View.GONE);


        //turn boxes invisible
        for (int easyBox : easyBoxes) {
            Button currentButton = findViewById(easyBox);
            currentButton.setVisibility(View.GONE);
        }
        for (int medBox : medBoxes) {
            Button currentButton = findViewById(medBox);
            currentButton.setVisibility(View.GONE);
        }

        // default is easy


        if (diffValue.equals("Medium")) {
            gridSize = 9;
            winStreak = 3; // start at 3 when you choose medium
            patternLength = 3;

            //set medium boxes to visible
            for (int medBox : medBoxes) {
                Button currentButton = findViewById(medBox);
                currentButton.setVisibility(View.VISIBLE);
            }

        } else if (diffValue.equals("Hard")) {
            gridSize = 36;
            winStreak = 7; // start at 3 when you choose medium
            patternLength = 7;

            hardBoxContainter.setVisibility(View.VISIBLE);
        } else {
            // default easy
            gridSize = 4;
            //set easy boxes to visible
            for (int easyBox : easyBoxes) {
                Button currentButton = findViewById(easyBox);
                currentButton.setVisibility(View.VISIBLE);
                Log.i("grid", "grid:" + gridSize);


            }
        }

    }


}





