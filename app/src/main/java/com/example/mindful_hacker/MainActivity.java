package com.example.mindful_hacker;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Timer;

public class MainActivity extends Activity implements  View.OnClickListener{
//VARIABLES
    //COUNTDOWN TIMER VARS-----
    private static final long startTime = 3600000;//in millis
    private CountDownTimer countDownTimer;
    long currTime;

    private Boolean timerRunning;
    private long timeLeft;
    TextView txtMainCountDown;
    Button btnEdit;//create button variable

    int day, month, year;//bundle passing from startPage
    int hour, minute;

    Calendar submissionDate, now = null;
    long submissionDateInMillis;


    //instantiate pomodoroTimers
    PomodoroTimer pomodoroTimer;
    PomodoroTimer breakTimer;

    /*//POMODORO VARS

    TextView txtPomodoro;
    Button btnStartPause, btnReset;
    static final long startTimeInMillis = 1500000; //25mins
    CountDownTimer pomoCountDownTimer;
    Boolean pomoRunning;
    long pomoTimeLeftMillis = startTimeInMillis;
    int pomoHr, pomoMin, pomoSec;


    long pomodoroDuration;
    long breakDuration;*/



    //---------ONCREATE---------------------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        
        init();
        editTimer();
        startTimer();
        if(pomodoroTimer.getTimerRunning()){
            pomodoroTimer.checkPomoButtons();
        }
        else{
            breakTimer.checkPomoButtons();
        }


    }


//---------INIT------------------------------------------------------------------------

    private void init() {

        txtMainCountDown = findViewById(R.id.MainCountDown);
        btnEdit = findViewById(R.id.btnEdit);//instantiate button, provide id



        //vars from startPage
        Intent intent = getIntent();
        day = intent.getIntExtra("key_day", 0);
        month = intent.getIntExtra("key_month", 0);
        year = intent.getIntExtra("key_year", 0);
        hour = intent.getIntExtra("key_hour", 0);
        minute = intent.getIntExtra("key_minute", 0);


       //vars to calculate time
        Calendar submissionDate = Calendar.getInstance();
        submissionDate.set(year, month, day, hour, minute);
        submissionDateInMillis = submissionDate.getTimeInMillis();
        currTime = Calendar.getInstance().getTimeInMillis();
        //btnEdit.setText(""+day);

        //instantiate pomodorotimer objects
        pomodoroTimer = new PomodoroTimer(1500000);
        breakTimer = new PomodoroTimer(300000);
        pomodoroTimer.setTimerRunning(true);
        breakTimer.setTimerRunning(false);

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

        timeLeft = submissionDateInMillis - currTime;
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

        int mins = (int) (timeLeft / 3600000)/60;
        int secs = (int) (timeLeft / 1000)%60;
        int hrs = (int)(timeLeft/3600000);

        String timeLeftFormatted = String.format(Locale.getDefault(),"%02d:%02d:%02d", hrs, mins, secs);
        txtMainCountDown.setText(timeLeftFormatted);
    }

//-----POMODORO-----------------------------------------------------------------------------------
/*
    public void checkPomoButtons(){
        btnStartPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pomoRunning){
                    pausePomo();
                }
                else{
                    startPomo();
                }
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetPomo();
            }
        });

        updatePomoText();

    }


    public void startPomo(){
        pomoCountDownTimer = new CountDownTimer(pomoTimeLeftMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                pomoTimeLeftMillis = millisUntilFinished;
                updatePomoText();
            }

            @Override
            public void onFinish() {//IMPLEMENT BREAK TIMER HERE
                pomoRunning = false;
                btnStartPause.setText("START");
                btnStartPause.setVisibility(View.INVISIBLE);
                btnReset.setVisibility(View.VISIBLE);
            }
        }.start();

        pomoRunning = true;
        btnStartPause.setText("PAUSE");
        btnReset.setVisibility(View.INVISIBLE);
    }
    public void pausePomo(){
        pomoCountDownTimer.cancel();
        pomoRunning = false;
        btnStartPause.setText("START");
        btnReset.setVisibility(View.VISIBLE);
    }
    public void resetPomo(){
        pomoTimeLeftMillis = startTimeInMillis;
        updatePomoText();
        btnReset.setVisibility(View.INVISIBLE);
        btnStartPause.setVisibility(View.VISIBLE);
    }
    public void updatePomoText(){
        int pomoMins = (int) (pomoTimeLeftMillis / 1000)/60;
        int pomoSecs = (int) (pomoTimeLeftMillis / 1000)%60;

        String pomoTimeLeftFormatted = String.format(Locale.getDefault(),"%02d:%02d", pomoMins, pomoSecs);

        txtPomodoro.setText(pomoTimeLeftFormatted);
    }*/


//-----NOT USING-----------------------------------------------------------------------------
    @Override
    public void onClick(View v) {}
}
























class PomodoroTimer extends MainActivity implements View.OnClickListener {
    static long startTimeInMillis;
    public void setTimerRunning(boolean timerRunning){
        this.timerRunning = timerRunning;
    }
    public Boolean getTimerRunning(){
        return timerRunning;
    }

    //POMODORO VARS
    TextView txtPomodoro;
    Button btnStartPause, btnReset;
    CountDownTimer pomoCountDownTimer;
    Boolean timerRunning;
    long pomoTimeLeftMillis = startTimeInMillis;
//------CONSTRUCTOR-----------------------------------------------------------------------------
    public PomodoroTimer(long startTimeInMillis){
        this.startTimeInMillis = startTimeInMillis;
       // btnStartPause = findViewById(R.id.btnStartPause);
       // btnReset = findViewById(R.id.btnReset);
    }

    //-----POMODORO-----------------------------------------------------------------------------------

    public void checkPomoButtons(){
        Button btnStartPause = findViewById(R.id.btnStartPause);
        Button btnReset = findViewById(R.id.btnReset);
        btnStartPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(timerRunning){
                    pausePomo();
                } else{
                    startPomo();
                }
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetPomo();
            }
        });

        updatePomoText();

    }


    public void startPomo(){
        pomoCountDownTimer = new CountDownTimer(pomoTimeLeftMillis, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                pomoTimeLeftMillis = millisUntilFinished;
                updatePomoText();
            }

            @Override
            public void onFinish() {//IMPLEMENT BREAK TIMER HERE
                timerRunning = false;
                btnStartPause.setText("START");
                btnStartPause.setVisibility(View.INVISIBLE);
                btnReset.setVisibility(View.VISIBLE);
            }
        }.start();

        timerRunning = true;
        btnStartPause.setText("PAUSE");
        btnReset.setVisibility(View.INVISIBLE);
    }
    public void pausePomo(){
        pomoCountDownTimer.cancel();
        timerRunning = false;
        btnStartPause.setText("START");
        btnReset.setVisibility(View.VISIBLE);
    }
    public void resetPomo(){
        pomoTimeLeftMillis = startTimeInMillis;
        updatePomoText();
        btnReset.setVisibility(View.INVISIBLE);
        btnStartPause.setVisibility(View.VISIBLE);
    }
    public void updatePomoText(){
        int pomoMins = (int) (pomoTimeLeftMillis / 1000)/60;
        int pomoSecs = (int) (pomoTimeLeftMillis / 1000)%60;

        String pomoTimeLeftFormatted = String.format(Locale.getDefault(),"%02d:%02d", pomoMins, pomoSecs);

        txtPomodoro.setText(pomoTimeLeftFormatted);
    }


    //-----NOT USING-----------------------------------------------------------------------------
    @Override
    public void onClick(View v) {}

}







