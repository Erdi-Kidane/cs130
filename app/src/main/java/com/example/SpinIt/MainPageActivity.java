package com.example.SpinIt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainPageActivity extends AppCompatActivity {
    private Button mSpinButton, mGroupButton, mPrefenceButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        mSpinButton = (Button) findViewById(R.id.spinButton);

        mGroupButton = (Button) findViewById(R.id.groupButton);
        mPrefenceButton = (Button) findViewById(R.id.pbutton);
        mSpinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent registerIntent = new Intent(MainPageActivity.this, Spinner.class);
                startActivity(registerIntent);
            }
        });
        mGroupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent registerIntent = new Intent(MainPageActivity.this, MainActivity.class);
                startActivity(registerIntent);
            }
        });
        mGroupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent registerIntent = new Intent(MainPageActivity.this, MainActivity.class);
                startActivity(registerIntent);
            }
        });
        mPrefenceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent registerIntent = new Intent(MainPageActivity.this, Profile.class);
                startActivity(registerIntent);
            }
        });
    }


}
