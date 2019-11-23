package com.example.SpinIt;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.TextView;

public class Popup extends Activity {
    private String[] selection;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        setContentView(R.layout.popupwindow);
        TextView choice;
        Intent displayPopUp = getIntent();
       // String[] info = displayPopUp.getStringArrayExtra("info");
        String info = displayPopUp.getStringExtra("info");
        choice = (TextView)findViewById(R.id.shownPopUp);

        super.onCreate(savedInstanceState);



        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*.7), (int)(height*.5));
       choice.setText(info);

    }
}
