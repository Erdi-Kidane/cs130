package com.example.SpinIt;

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
import android.widget.Toast

import java.util.Random;

public class Spinner extends AppCompatActivity implements Animation.AnimationListener {
    boolean blnButtonRotation = true;
    int intNumber = 1;
    long lngDegrees = 0;
    ImageView selected,imageRoulette;
    private double savedDegree;

    Button b_start, b_increase, b_decrease;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().addFlags(1024);
        requestWindowFeature(1);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spinner);
        b_start = (Button)findViewById(R.id.spinItBTN);
        b_decrease = (Button)findViewById(R.id.subtractBTN);
        b_increase = (Button)findViewById(R.id.addStuffBTN);
        selected = (ImageView)findViewById(R.id.imageSelected);
        imageRoulette = (ImageView)findViewById(R.id.roulette);
        setImageRoulette(this.intNumber);




    }
    public void onClickButtonRotation(View v)
    {
        if(this.blnButtonRotation)
        {
            int ran = new Random().nextInt(360) + 3600;
            RotateAnimation rotateAnimation = new RotateAnimation((float)this.lngDegrees, (float)
                    (this.lngDegrees + ((long)ran)),1,0.5f,1,0.5f);
            this.lngDegrees = (this.lngDegrees + ((long)ran)) % 360;
            savedDegree = lngDegrees;
            rotateAnimation.setDuration((long)ran);
            rotateAnimation.setFillAfter(true);
            rotateAnimation.setInterpolator((new DecelerateInterpolator()));
            rotateAnimation.setAnimationListener(this);
            imageRoulette.setAnimation(rotateAnimation);
            imageRoulette.startAnimation(rotateAnimation);



        }

    }
    public int outputSelection(int degree, int wheelType)
    {
        if(wheelType == 1)
        {
            if((degree >= 0 && degree <=90) || (degree >= 270 && degree <= 360))
            {
                return 1;
            }
            else
                return 2;
        }
        else if(wheelType == 2)
        {
            if((degree >= 0 && degree <= 90))
                return 2;
            if(degree >= 91 && degree <= 180)
                return 4;
            if(degree >= 181 && degree <= 270)
                return 1;
            if(degree >= 271 && degree <= 360)
                return 3;

        }
        else if(wheelType == 3)
        {
            if(degree >= 0 && degree <= 45)
                return 6;
            if(degree >= 46 && degree <= 90)
                return 5;
            if(degree >= 91 && degree <= 135)
                return 1;
            if(degree >= 136 && degree <= 180)
                return 7;
            if(degree >= 181 && degree <= 255)
                return 2;
            if(degree >=256 && degree <= 270)
                return 8;
            if(degree >= 271 && degree <= 315)
                return 4;
            if(degree >= 316 && degree <= 360)
                return 3;
        }
        return 0;
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
        int castDegreeToInt = (int)savedDegree;
        String valOfSelection =  String.valueOf(outputSelection(castDegreeToInt, intNumber));
        Toast toast = Toast.makeText(this, " " + valOfSelection,0);
        //toast.setGravity(49, 0, 50);
        toast.show();

        //  outputSelection(savedDegree, intNumber);

    }
    @Override
    public void onAnimationRepeat(Animation animation)
    {


    }
    public void buttonPlus(View v)
    {
        if(this.intNumber != 3)
        {
            this.intNumber++;
            setImageRoulette(this.intNumber);
            b_decrease.setVisibility(View.VISIBLE);
/*            SharedPreferences.Editor editor = this.sharedPreferences.edit();
           editor.putInt("INT_NUMBER", this.intNumber);
            editor.commit();*/

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
/*            SharedPreferences.Editor editor = this.sharedPreferences.edit();
            editor.putInt("INT_NUMBER", this.intNumber);
            editor.commit();*/
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
