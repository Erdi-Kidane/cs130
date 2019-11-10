package com.example.spinit;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Profile extends AppCompatActivity {
    private Button savePrefButton;
    ArrayList<String> addPrefList = new ArrayList<>();
    EditText pref;
    ListView showPref;
    Set<String> prefNoDuplication = new HashSet<>();

    private Button saveFoodButton;
    ArrayList<String> addFoodList = new ArrayList<>();
    EditText foodpref;
    ListView showFoodPref;

    Set<String> foodNoDuplication = new HashSet<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        showPref = (ListView) findViewById((R.id.prefListView));
        pref = (EditText) findViewById((R.id.preftxt));
        savePrefButton = (Button)findViewById(R.id.prefButton);
        savePrefButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String getInput = pref.getText().toString();

//                invalid empty input
                if(getInput == null || getInput.trim().equals(""))
                {
                    Toast.makeText(getBaseContext(), "Empty preference cannot be added.", Toast.LENGTH_LONG).show();
                }
//                ignore duplications
                else if(prefNoDuplication.contains(getInput))
                {
                    Toast.makeText(getBaseContext(), "This preference is already added.", Toast.LENGTH_LONG).show();
                }
//                add to the preference list
                else
                {
                    prefNoDuplication.add(getInput);
                    addPrefList.add(getInput);
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(Profile.this, android.R.layout.simple_list_item_1, addPrefList);
                    showPref.setAdapter(adapter);
                    ((EditText)findViewById(R.id.preftxt)).setText("");
                }
            }
        });



        showFoodPref = (ListView) findViewById((R.id.foodList));
        foodpref = (EditText) findViewById((R.id.foodType));
        saveFoodButton = (Button)findViewById(R.id.foodButton);
        saveFoodButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String getInput = foodpref.getText().toString();

//                invalid empty input
                if(getInput == null || getInput.trim().equals(""))
                {
                    Toast.makeText(getBaseContext(), "Empty food type cannot be added.", Toast.LENGTH_LONG).show();
                }
//                ignore duplications
                else if(foodNoDuplication.contains(getInput))
                {
                    Toast.makeText(getBaseContext(), "This food type is already added.", Toast.LENGTH_LONG).show();
                }
//                add to the preference list
                else
                {
                    foodNoDuplication.add(getInput);
                    addFoodList.add(getInput);
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(Profile.this, android.R.layout.simple_list_item_1, addFoodList);
                    showFoodPref.setAdapter(adapter);
                    ((EditText)findViewById(R.id.foodType)).setText("");
                }
            }
        });
        //
    }

}
