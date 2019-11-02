package com.example.spinit;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;


public class MainActivity extends AppCompatActivity {

    private EditText Name;
    private EditText Password;
    //private TextView info;
    private Button Login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       //View nameView = inflater(R.layout.activity_main);

        //holds login name and pw
        Name = findViewById(R.id.editName);
        Password = findViewById(R.id.editPassword);
        Login = findViewById(R.id.loginButton);

        Login.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View view)
            {
                validate(Name.getText().toString(), Password.getText().toString());
            }
        });

    }

    private void validate(String userName, String userPW)//validate login
    {
        if(userName.equals("Erdi") && userPW.equals("badnews"))
        {
            Intent goToDash = new Intent(MainActivity.this, DashBoard.class);
            startActivity(goToDash);

        }

    }



}
