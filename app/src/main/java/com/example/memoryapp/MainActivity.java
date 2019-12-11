package com.example.memoryapp;

import androidx.appcompat.app.AppCompatActivity;


import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import java.util.ArrayList;

import java.util.Collections;

//TODO : ADD FUNCTION TO COLLECT THE BOXES



/**
 * Skeleton of an Android Things activity.
 * <p>
 * Android Things peripheral APIs are accessible through the class
 * PeripheralManagerService. For example, the snippet below will open a GPIO pin and
 * set it to HIGH:
 *
 * <pre>{@code
 * PeripheralManagerService service = new PeripheralManagerService();
 * mLedGpio = service.openGpio("BCM6");
 * mLedGpio.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW);
 * mLedGpio.setValue(true);
 * }</pre>
 * <p>
 * For more complex peripherals, look for an existing user-space driver, or implement one if none
 * is available.
 *
 * @see <a href="https://github.com/androidthings/contrib-drivers#readme">https://github.com/androidthings/contrib-drivers#readme</a>
 */



public class MainActivity extends AppCompatActivity implements View.OnClickListener {

   public ArrayList pattern = pickPattern(2,4);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Capture our button from layout
        Button button = findViewById(R.id.grid00);
        // Register the onClick listener with the implementation above
        button.setOnClickListener(this);
       // Log.i("pattern","pattern:" + pattern);





    }
    public void StartClick(View v) {
        // get new pattern (pattern length and gridSize will be based on difficulty)
        pattern = pickPattern(2,4);

        //call function to turn pattern boxes blue
        showPattern(pattern,false);

        // timer to delay the reset
        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // turn the blue boxes back to white
                        showPattern(pattern,true);
                    }
                }, 5000);





    }


    public void showPattern(ArrayList inputPattern,Boolean reset){
        /* todo disable the buttons during show pattern */

        for(int i = 0;i<inputPattern.size();i++){
            String currentButName =(String) pattern.get(i);
            // get the button widget
            Button currentBut;
            currentBut = findViewById(getResources().getIdentifier(currentButName, "id",this.getPackageName()));
            if(reset == false){
                currentBut.setBackgroundColor(Color.BLUE);
            }else{
                currentBut.setBackgroundColor(Color.parseColor("#A9A9A9"));
            }
        }
    }



    // Implement the OnClickListener callback
    public void onClick(View v) {
        // do something when the button is clicked
        Log.i("clicker","it worked");

        ColorDrawable viewColor =(ColorDrawable) v.getBackground();
        int colorId = viewColor.getColor();
        if(colorId == Color.BLUE){
            v.setBackgroundColor(Color.parseColor("#A9A9A9"));
        }else{
            v.setBackgroundColor(Color.BLUE);
        }
    }

    public void submitClick(View v) {
// TODO disable button presses during timeout phase

        Log.i("submit click","it worked");

        // collect all the selected grid boxes
        ArrayList<String> gridBoxes;
        gridBoxes = new ArrayList<String>();

        for(int i= 0; i<2;i++){
            for(int j=0;j<2;j++){
                // create the x and y coordinates of the panel
                String currentBoxId = "grid"+i+j;
                Button currentBut;
                currentBut = findViewById(getResources().getIdentifier(currentBoxId, "id",this.getPackageName()));
               // if its blue add it to the list of selected
                ColorDrawable viewColor =(ColorDrawable) currentBut.getBackground();
                int colorId = viewColor.getColor();
                if(colorId == Color.BLUE){
                    gridBoxes.add(currentBoxId);
                    // reset the box to white
                    currentBut.setBackgroundColor(Color.parseColor("#A9A9A9"));
                }
            }
        }
        Log.i("pattern","pattern:" + pattern);
        Log.i("submit results","results" + gridBoxes);

        // sort the lists
        Collections.sort(pattern);
        Collections.sort(gridBoxes);


        // check if submitted equals the pattern
        final TextView patternResultTextBox = findViewById(R.id.submitResults);
        patternResultTextBox.setVisibility(View.VISIBLE);
        if(gridBoxes.equals(pattern)){
            Log.i("Array match","true");
            patternResultTextBox.setText("Correct");
            patternResultTextBox.setTextColor(Color.GREEN);
        }
        else{
            Log.i("Array match","false");
            patternResultTextBox.setText("Incorrect");
            patternResultTextBox.setTextColor(Color.RED);
        }
        // hide the result box after some timer
        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // turn the blue boxes back to whit
                      patternResultTextBox.setVisibility(View.INVISIBLE);
                    }
                }, 1000);




    }
    public ArrayList<String>  pickPattern(int patternLength, int gridSize){
        // make array of all possible choices
        ArrayList<String> possibleBoxes;
        possibleBoxes = new ArrayList<String>();
        for(int i = 0;i<Math.sqrt(gridSize);i++){
            for(int j = 0;j<Math.sqrt(gridSize);j++){
                possibleBoxes.add("grid"+i+j);
            }
        }
        // initalise the array for storing the pattern
        ArrayList<String> patternList;
        patternList = new ArrayList<String>();
        for(int i = 0;i<patternLength;i++){
            // pick random option from possibleBoxes and remove from the list
            int choiceIndex = (int)(possibleBoxes.size() * Math.random());
            patternList.add(possibleBoxes.get(choiceIndex));
            possibleBoxes.remove(choiceIndex);
        }
        return patternList;
    }


}





