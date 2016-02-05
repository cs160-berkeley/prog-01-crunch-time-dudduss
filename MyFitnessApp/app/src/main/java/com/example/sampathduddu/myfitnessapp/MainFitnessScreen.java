package com.example.sampathduddu.myfitnessapp;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Spinner;
import android.widget.AdapterView;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.content.Context;
import android.view.MotionEvent;
import android.view.inputmethod.InputMethodManager;

import java.util.ArrayList;
import java.util.HashMap;


public class MainFitnessScreen extends AppCompatActivity {

    HashMap<String, Integer > repCalories = new HashMap<String, Integer>(){{
        put("Pushup", 350);
        put("Situp", 200);
        put("Squats", 225);
        put("Pullup", 100);
    }};

    HashMap<String, Integer > minuteCalories = new HashMap<String, Integer>(){{
        put("Leg-Lift", 25);
        put("Plank", 25);
        put("Jumping Jacks", 10);
        put("Cycling", 12);
        put("Walking", 20);
        put("Jogging", 12);
        put("Swimming", 13);
        put("Stair Climbing", 15);
    }};

    String selectedExercise = "";
    int allCaloriesBurned = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_fitness_screen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        //Do everything below this code

        final EditText numReps = (EditText) findViewById(R.id.numberReps);
        final EditText numMinutes = (EditText) findViewById(R.id.numberMinutes);
        final EditText numCalories = (EditText) findViewById(R.id.numCalories);
        final EditText amtWeight = (EditText) findViewById(R.id.weightAmt);


        numReps.setVisibility(View.INVISIBLE);
        numMinutes.setVisibility(View.INVISIBLE);



        Spinner exerciseSpn = (Spinner) findViewById(R.id.exerciseSpinner);

        exerciseSpn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                String[] some_array = getResources().getStringArray(R.array.exercises);
                selectedExercise = some_array[i];

                // Log.d("Selected", selectedExercise);

                if (repCalories.containsKey(selectedExercise)) {
                    numReps.setVisibility(View.VISIBLE);
                    numMinutes.setVisibility(View.INVISIBLE);
                } else if (minuteCalories.containsKey(selectedExercise)) {
                    numMinutes.setVisibility(View.VISIBLE);
                    numReps.setVisibility(View.INVISIBLE);
                }
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
                return;
            }
        });



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        final TextView totalCaloriesLabel = (TextView) findViewById(R.id.totalCalories);
        totalCaloriesLabel.setVisibility(View.INVISIBLE);

        Button calories = (Button) findViewById(R.id.btnCalculate);

        final Button checkOther = (Button) findViewById(R.id.btnCompare);
        checkOther.setVisibility(View.INVISIBLE);
        
        calories.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        //Calculate stuff here and display here...

//                        String type = exerciseType.getText().toString();
                        if (repCalories.containsKey(selectedExercise)) {

                            int repsFor100 = repCalories.get(selectedExercise);
                            int totalRepsDone = Integer.parseInt(numReps.getText().toString());

                            int caloriesBurned = (totalRepsDone * 100) / repsFor100;

                            allCaloriesBurned = caloriesBurned;
                        } else {

                            int minutesFor100 = minuteCalories.get(selectedExercise);
                            int totalMinutesDone = Integer.parseInt(numMinutes.getText().toString());

                            int caloriesBurned = (totalMinutesDone * 100) / minutesFor100;

                            allCaloriesBurned = caloriesBurned;
                        }

                        if (!amtWeight.getText().toString().equals("")) {
                            double weight = Double.parseDouble(amtWeight.getText().toString());
                            double ratio = weight/150.0;
                            double allCalories150 = allCaloriesBurned*1.0;
                            double weightAccountedCalories = allCalories150*ratio;
                            allCaloriesBurned = (int) Math.round(weightAccountedCalories);

                            totalCaloriesLabel.setVisibility(View.VISIBLE);
                            totalCaloriesLabel.setText("Congratulations, you have burned " +
                                    Integer.toString(allCaloriesBurned) + " calories.");

                        } else {
                            totalCaloriesLabel.setVisibility(View.VISIBLE);
                            totalCaloriesLabel.setText("Congratulations, you have burned " +
                                    Integer.toString(allCaloriesBurned) + " calories.");
                        }

                        checkOther.setVisibility(View.VISIBLE);

                    }
                }

        );




        checkOther.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {

                        Intent intent = new Intent("com.example.sampathduddu.myfitnessapp.OtherExercisesScreen");

                        String[] otherExercise = prepareArray(false);
                        intent.putExtra("calories", allCaloriesBurned);
                        intent.putExtra("listOtherExercises", otherExercise);
                        startActivity(intent);
                    }

                }
        );

        Button calculateRepsMinutes = (Button) findViewById(R.id.btnCalculateRepsMinutes);

        calculateRepsMinutes.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {

                        allCaloriesBurned = Integer.parseInt(numCalories.getText().toString());

                        if (!amtWeight.getText().toString().equals("")) {
                            double weight = Double.parseDouble(amtWeight.getText().toString());
                            double ratio = weight/150.0;
                            double allCalories150 = allCaloriesBurned*1.0;
                            double weightAccountedCalories = allCalories150*ratio;
                            allCaloriesBurned = (int) Math.round(weightAccountedCalories);
                        }

                        Intent intent = new Intent("com.example.sampathduddu.myfitnessapp.OtherExercisesScreen");

                        String[] otherExercise = prepareArray(true);
                        intent.putExtra("calories", allCaloriesBurned);
                        intent.putExtra("listOtherExercises", otherExercise);
                        startActivity(intent);

                    }
                }
        );





    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_fitness_screen, menu);
        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.
                INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public String[] prepareArray(Boolean all) {

        ArrayList<String> otherExerciseCalories = new ArrayList<String>();

        for (String exercise: repCalories.keySet()) {

            if (!all) {
                if(!exercise.equals(selectedExercise)) {
                    int totalRepsNeeded = (repCalories.get(exercise)*allCaloriesBurned)/100;
                    otherExerciseCalories.add(exercise + "      " + Integer.toString(totalRepsNeeded) + " REPS");
                }
            } else {
                int totalRepsNeeded = (repCalories.get(exercise)*allCaloriesBurned)/100;
                otherExerciseCalories.add(exercise + "      " + Integer.toString(totalRepsNeeded) + " REPS");
            }

        }

        for (String exercise: minuteCalories.keySet()) {

            if(!all) {
                if(!exercise.equals(selectedExercise)) {
                    int totalRepsNeeded = (minuteCalories.get(exercise)*allCaloriesBurned)/100;
                    otherExerciseCalories.add(exercise + "      " + Integer.toString(totalRepsNeeded) + " MINUTES");
                }
            } else {
                int totalRepsNeeded = (minuteCalories.get(exercise)*allCaloriesBurned)/100;
                otherExerciseCalories.add(exercise + "      " + Integer.toString(totalRepsNeeded) + " MINUTES");
            }

        }

        String[] result = new String[otherExerciseCalories.size()];
        result = otherExerciseCalories.toArray(result);

        return result;
    }

}
