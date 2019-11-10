package com.example.spinit;

import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.preference.PreferenceManager;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Random;

public class Spinner extends AppCompatActivity implements Animation.AnimationListener {
    boolean blnButtonRotation = true;
    int intNumber = 1;
    long lngDegrees = 0;
    SharedPreferences sharedPreferences;
    ImageView selected,imageRoulette;

    Button b_start, b_increase, b_decrease;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().addFlags(1024);
        requestWindowFeature(1);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spinner);
/*        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);*/
        b_start = (Button)findViewById(R.id.spinItBTN);
        b_decrease = (Button)findViewById(R.id.subtractBTN);
        b_increase = (Button)findViewById(R.id.addStuffBTN);

        selected = (ImageView)findViewById(R.id.imageSelected);
        imageRoulette = (ImageView)findViewById(R.id.roulette);

        this.sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        //this.intNumber = this.sharedPreferences.getInt("INT_NUMBER", 6);
        setImageRoulette(this.intNumber);
    }

    @Override
    public void onAnimationStart(Animation animation)
    {
        this.blnButtonRotation = false;
        b_start.setVisibility(View.VISIBLE);

    }

    @Override
    public void onAnimationEnd(Animation animation)
    {
        this.blnButtonRotation = true;
        b_start.setVisibility(View.VISIBLE);


    }
    @Override
    public void onAnimationRepeat(Animation animation)
    {


    }
    //handles the spinning animation
    public void onClickButtonRotation(View v)
    {
        if(this.blnButtonRotation)
        {
            int ran = new Random().nextInt(360) + 3600;
            RotateAnimation rotateAnimation = new RotateAnimation((float)this.lngDegrees, (float)
                (this.lngDegrees + ((long)ran)),1,0.5f,1,0.5f);
            this.lngDegrees = (this.lngDegrees + ((long)ran)) % 360;
            rotateAnimation.setDuration((long)ran);
            rotateAnimation.setFillAfter(true);
            rotateAnimation.setInterpolator((new DecelerateInterpolator()));
            rotateAnimation.setAnimationListener(this);
            imageRoulette.setAnimation(rotateAnimation);
            imageRoulette.startAnimation(rotateAnimation);

        }

    }
    public void buttonPlus(View v)
    {
        if(this.intNumber != 3)
        {
            this.intNumber++;
            setImageRoulette(this.intNumber);
            b_decrease.setVisibility(View.VISIBLE);
          //  SharedPreferences.Editor editor = this.sharedPreferences.edit();
         //   editor.putInt("INT_NUMBER", this.intNumber);
          //  editor.commit();

        }
        if(this.intNumber == 3)
        {
            b_increase.setVisibility(View.INVISIBLE);
          //  b_decrease.setVisibility(View.VISIBLE);
        }
    }

    public void buttonMinus(View v)
    {
        if(this.intNumber != 1)
        {
            intNumber--;
            setImageRoulette(this.intNumber);
            b_increase.setVisibility(View.VISIBLE);
            //SharedPreferences.Editor editor = this.sharedPreferences.edit();
          //  editor.putInt("INT_NUMBER", this.intNumber);
           // editor.commit();
        }
        if(this.intNumber == 1)
        {
            b_decrease.setVisibility(View.INVISIBLE);
          //  b_increase.setVisibility(View.VISIBLE);
        }
    }
    private void setImageRoulette(int inputNum)
    {
        switch(inputNum)
        {
            case 1:
                imageRoulette.setImageDrawable(getResources().getDrawable(R.drawable.spin1));
                return;
            case 2:
                imageRoulette.setImageDrawable(getResources().getDrawable(R.drawable.spin2));
                return;
            case 3:
                imageRoulette.setImageDrawable(getResources().getDrawable(R.drawable.spin3));
                return;
        }
    }
}
