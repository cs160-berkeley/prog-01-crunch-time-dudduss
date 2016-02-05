package com.example.sampathduddu.myfitnessapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.Button;
import android.widget.AdapterView.OnItemClickListener;

import android.view.View;


/**
 * Created by sampathduddu on 2/4/16.
 */
public class OtherExercisesScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.other_exercises_screen);

        Button back = (Button) findViewById(R.id.backButton);

        final ListView listView = (ListView) findViewById(R.id.exerciseList);


       // populateArray();
        // Defined Array values to show in ListView

        String[] values = getIntent().getExtras().getStringArray("listOtherExercises");
//        String[] values = new String[] { "Android List View",
//                "Adapter implementation",
//                "Simple List View In Android",
//                "Create List View Android",
//                "Android Example",
//                "List View Source Code",
//                "List View Array Adapter",
//                "Android Example List View"
//        };

        // Define a new Adapter
        // First parameter - Context
        // Second parameter - Layout for the row
        // Third parameter - ID of the TextView to which the data is written
        // Forth - the Array of data

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, values);


        // Assign adapter to ListView
        listView.setAdapter(adapter);

        // ListView Item Click Listener
        listView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                // ListView Clicked item index
                int itemPosition = position;

                // ListView Clicked item value
                String  itemValue  = (String) listView.getItemAtPosition(position);

                int calories = getIntent().getExtras().getInt("calories");
                // Show Alert
                Toast.makeText(getApplicationContext(),
                        "For " + itemValue + " is required to burn " + Integer.toString(calories)
                                + " calories.", Toast.LENGTH_LONG).show();
            }

        });

        back.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        finish();
                    }
                }
        );
    }

    public void populateArray () {
        int calories = getIntent().getExtras().getInt("calories");
        Log.d("Total calories burned",Integer.toString(calories));
    }

}


