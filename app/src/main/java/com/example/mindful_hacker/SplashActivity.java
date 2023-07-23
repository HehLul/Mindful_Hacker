package com.example.mindful_hacker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

public class SplashActivity extends AppCompatActivity {
    //FIELDS
    private TextView txtSplash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        //TXTVIEW INIT
        txtSplash = findViewById(R.id.txtSplash);//text id
        //LOAD ANIMATIONS
        Animation alpha = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.alpha);
        Animation scale = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.scale);

        txtSplash.startAnimation(alpha);//execute animation
        txtSplash.startAnimation(scale);//execute animation


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent iHome;
                iHome = new Intent(SplashActivity.this, StartPage.class);
                startActivity(iHome);//Execute main activity intent

                finish(); //POP FROM STACK, CANT GO BACK
            }
        }, 5000);//5 SECOND DELAY


    }
}