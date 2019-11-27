package com.example.SpinIt;


import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import android.content.DialogInterface;

import java.util.ArrayList;

public class Profile extends AppCompatActivity {

    Button foodBtn;
    TextView showSelectedFood;
    String[] listItems;
    boolean[] checkedItems;
    ArrayList<Integer> kUserItems = new ArrayList<>();
    ArrayList<String> foodListChosen = new ArrayList<>();


    Button dietaryBtn;
    TextView showSelectedDietary;
    String[] dietaryItems;
    boolean[] checkedDietaryItems;
    ArrayList<Integer> kUserItems2 = new ArrayList<>();
    ArrayList<String> dietaryListChosen = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });



        foodBtn = (Button) findViewById(R.id.foodTypeBtn);
        showSelectedFood = (TextView) findViewById((R.id.selectedFood));

        listItems = getResources().getStringArray(R.array.food_types);
        checkedItems = new boolean[listItems.length];

        //    UPDATE already added boxes
        ArrayList<String> tempList = new ArrayList<>();
        tempList.add("Distilleries");
        tempList.add("Coffee and Tea");

        for(String s: tempList)
        {
            for(int i=0; i<listItems.length; i++)
            {
                if(s.equals(listItems[i]))
                {
                    checkedItems[i] = true;
                    kUserItems.add(i);
//                    System.out.println(i);
                    break;
                }

            }
        }


////        the following commented code working
//
//        kUserItems.add(0);
//        checkedItems[0] = true;
//        kUserItems.add(7);
//        checkedItems[7] = true;


        String item = "";
        for(int i=0; i<kUserItems.size(); i++)
        {
            item = item + listItems[kUserItems.get(i)];
            if(i!=kUserItems.size()-1)  // not last item, add comma
            {
                item = item + ", ";
            }

        }
        showSelectedFood.setText(item);



        foodBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder mBuilder = new AlertDialog.Builder(Profile.this);
                mBuilder.setTitle("Available Food Types");
                mBuilder.setMultiChoiceItems(listItems, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        if(isChecked) {
                            kUserItems.add(which);
                            foodListChosen.add(listItems[which]);
                        }
                        else if(kUserItems.contains(which))
                        {
                            foodListChosen.remove(listItems[which]);
                            kUserItems.remove(Integer.valueOf(which));

                        }

                    }
                });
                mBuilder.setCancelable(false);
                mBuilder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String item = "";
                        for(int i=0; i<kUserItems.size(); i++)
                        {
                            item = item + listItems[kUserItems.get(i)];
                            if(i!=kUserItems.size()-1)  // not last item, add comma
                            {
                                item = item + ", ";
                            }

                        }
                        showSelectedFood.setText(item);
                    }
                });

                mBuilder.setNeutralButton("Clear", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        for(int i=0; i<checkedItems.length; i++)
                        {
                            checkedItems[i] = false;
                            kUserItems.clear();
                            showSelectedFood.setText("");
                            foodListChosen.clear();
                        }
                    }
                });

                mBuilder.setNegativeButton("Dimiss", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                AlertDialog foodDialog = mBuilder.create();
                foodDialog.show();

            }
        });





        dietaryBtn = (Button) findViewById(R.id.dietaryBtn);
        showSelectedDietary = (TextView) findViewById((R.id.selectedDietary));

        dietaryItems = getResources().getStringArray(R.array.dietary_types);
        checkedDietaryItems = new boolean[dietaryItems.length];


//        TO DO:
//        HERE WE WILL ALSO NEED TO GET THE LIST AND CHECK WHICH BOXES ARE ALREADY CHECKED
        dietaryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(Profile.this);
                mBuilder.setTitle("Available Dietary Types");
                mBuilder.setMultiChoiceItems(dietaryItems, checkedDietaryItems, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        if(isChecked) {
                            kUserItems2.add(which);
                            dietaryListChosen.add(dietaryItems[which]);
                        }
                        else if(kUserItems2.contains(which))
                        {
                            dietaryListChosen.remove(dietaryItems[which]);
                            kUserItems2.remove(Integer.valueOf(which));

                        }

                    }
                });
                mBuilder.setCancelable(false);
                mBuilder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String item = "";
                        for(int i=0; i<kUserItems2.size(); i++)
                        {
                            item = item + dietaryItems[kUserItems2.get(i)];
                            if(i!=kUserItems2.size()-1)  // not last item, add comma
                            {
                                item = item + ", ";
                            }

                        }
                        showSelectedDietary.setText(item);
                    }
                });

                mBuilder.setNeutralButton("Clear", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        for(int i=0; i<checkedDietaryItems.length; i++)
                        {
                            checkedDietaryItems[i] = false;
                            kUserItems2.clear();
                            showSelectedDietary.setText("");
                            dietaryListChosen.clear();
                        }
                    }
                });

                mBuilder.setNegativeButton("Dimiss", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                AlertDialog dietaryDialog = mBuilder.create();
                dietaryDialog.show();

            }
        });


    }

    public ArrayList<String> getFoodList()
    {
        return foodListChosen;
    }

    public ArrayList<String> getDietaryList()
    {
        return dietaryListChosen;
    }



}
