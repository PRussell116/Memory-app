package com.example.memoryapp;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {
    @Rule
    public MainActivity activityRule = new MainActivity();

    @Test
    public void pressStartButton() {
        onView(withId(R.id.startBut)).perform(click());

        // ArrayList pattenr =  MainActivity.pattern;
    }
}