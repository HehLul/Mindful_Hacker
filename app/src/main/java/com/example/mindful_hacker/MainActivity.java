package com.example.mindful_hacker;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Locale;

public class MainActivity extends Activity implements  View.OnClickListener{
//VARIABLES
    private static final long startTime = 3600000;//in millis
    private CountDownTimer countDownTimer;
    private Boolean timerRunning;
    private long timeLeft = startTime;
    TextView txtMainCountDown;
    Button btnEdit;//create button variable

    long day, month, year;//bundle passing from startPage
    long hour, minute;


    //---------ONCREATE---------------------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        init();
        editTimer();
        startTimer();

    }


//---------INIT------------------------------------------------------------------------

    private void init() {

        txtMainCountDown = findViewById(R.id.MainCountDown);
        btnEdit = findViewById(R.id.btnEdit);//instantiate button, provide id

        //vars from startPage
        Intent intent = getIntent();
        day = intent.getLongExtra("key_day", 0);
        month = intent.getLongExtra("key_month", 0);
        year = intent.getLongExtra("key_year", 0);
        hour = intent.getLongExtra("key_hour", 0);
        minute = intent.getLongExtra("key_minute", 0);

        btnEdit.setText(""+minute);

    }
//-------COUNTDOWN TIMER METHODS--------------------------------------------------------------------------
    private void editTimer() {//switch page to start page to edit countdown start
        btnEdit.setOnClickListener(new View.OnClickListener() {//setOnClickListener
            @Override
            public void onClick(View v) {
                Log.d("MainActivity", "In if state");
                Intent i = new Intent(getApplicationContext(), StartPage.class);///switch page
                startActivity(i);
            }
        });
    }

    private void startTimer() {
        countDownTimer = new CountDownTimer(timeLeft, 1000) {//instantiate timer (timeleftinmillis, how often to change)
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeft = millisUntilFinished;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                timerRunning = false;
                txtMainCountDown.setText("00:00:00");
            }
        }.start();//Immediately start timer when finished creating in onTick()

        timerRunning = true;//now timer is running

    }


    private void updateCountDownText(){
        int mins = (int) (timeLeft / 1000)/60;
        int secs = (int) (timeLeft / 1000)%60;
        int hrs = (int)(timeLeft/3600000);

        String timeLeftFormatted = String.format(Locale.getDefault(),"%02d:%02d:%02d", hrs, mins, secs);
        txtMainCountDown.setText(timeLeftFormatted);
    }

//-----NOT USING-----------------------------------------------------------------------------
    @Override
    public void onClick(View v) {}
}








