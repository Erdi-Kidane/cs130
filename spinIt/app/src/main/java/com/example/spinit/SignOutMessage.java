package com.example.spinit;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SignOutMessage extends AppCompatActivity {

    private TextView mTextViewBarney;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);

        mTextViewBarney.findViewById(R.id.TextViewBarney);
        mTextViewBarney.setText("Thank you!");

    }
}
