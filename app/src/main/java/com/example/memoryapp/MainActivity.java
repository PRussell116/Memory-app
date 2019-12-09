package com.example.memoryapp;

import androidx.appcompat.app.AppCompatActivity;


import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;


import java.util.ArrayList;
//TODO : ADD FUNCTION TO COLLECT THE BOXES
//TODO : RANDOMLY CHOOSE BOXES
//TODO : SHOW PATTERN BEFORE HAND


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



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Capture our button from layout
        Button button = findViewById(R.id.grid00);
        // Register the onClick listener with the implementation above
        button.setOnClickListener(this);




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
                }
            }
        }
        Log.i("submit results","results" + gridBoxes);


    }


}





