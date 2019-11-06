package com.example.spinit;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

//import com.example.spinit.ui.login.LoginActivity;

public class MainActivity extends AppCompatActivity {

    ImageView rotateImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void firstStart(View view){
        rotateImage = (ImageView)findViewById(R.id.rotate_img);
        Animation startRotateAnimation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.android_rotate_animation);
        rotateImage.startAnimation(startRotateAnimation);
        long delayMillis=1800;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent  intent=new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        }, delayMillis);

    }
    public void register(View view){
        rotateImage = (ImageView)findViewById(R.id.rotate_img);
        Animation startRotateAnimation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.android_rotate_animation);
        rotateImage.startAnimation(startRotateAnimation);
        long delayMillis=1800;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent  intent=new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        }, delayMillis);
    }


}
