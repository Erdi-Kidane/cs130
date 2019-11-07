package com.example.spinit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class DashBoard extends AppCompatActivity {

    private Button spinButton;
    private Button roomButton;
    private Button profileButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);
        spinButton = findViewById(R.id.spinButton);
        spinButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
               goToSpin();

            }
        });
        roomButton = findViewById(R.id.roomButton);
        roomButton.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View view)
            {
                goToRoom();
            }
        });
        profileButton = findViewById(R.id.profileButton);
        profileButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                goToProfile();
            }
        });
    }

    private void goToRoom()
    {
        Intent room = new Intent(DashBoard.this, Rooms.class);
        startActivity(room);
    }

    private void goToProfile()
    {
        Intent profile = new Intent(DashBoard.this, Profile.class);
        startActivity(profile);
    }


    private void goToSpin()
    {



            Intent spin = new Intent(DashBoard.this, Spinner.class);
            startActivity(spin);



    }
}
