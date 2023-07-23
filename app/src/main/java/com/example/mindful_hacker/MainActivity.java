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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
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
   // PomodoroTimer pomodoroTimer;
   // PomodoroTimer breakTimer;



    //POMODORO VARS

    TextView txtPomodoro;
    Button btnStartPause, btnReset;
    static final long startTimeInMillis = 1500000; //25mins
    CountDownTimer pomoCountDownTimer;
    Boolean pomoRunning;
    long pomoTimeLeftMillis = startTimeInMillis;
    int pomoHr, pomoMin, pomoSec;


    //RANDOM ACTIVITY GENERATOR VARS
    RandomActivityGenerator randomActivityGenerator;



    //---------ONCREATE---------------------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        init();
        editTimer();
        startTimer();
        checkPomoButtons();

        randomActivityGenerator.generateRandomActivity();

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
        pomoRunning = false;
        btnStartPause = findViewById(R.id.btnStartPause);
        btnReset = findViewById(R.id.btnReset);
        txtPomodoro = findViewById(R.id.txtPomodoro);


        //random activity generator init
        ArrayList<String> randomActivitiesArray = new ArrayList<>();
        randomActivitiesArray.add("Stretching: Do some light stretching exercises to ease tension in your muscles and improve blood circulation.");
        randomActivitiesArray.add("Deep breathing: Practice deep breathing techniques to relax your mind and reduce stress.");
        randomActivitiesArray.add( "Walk or stroll: Take a short walk around your workspace or outside to get some fresh air and clear your mind.");
        randomActivitiesArray.add("Hydrate: Drink a glass of water to stay hydrated and refresh yourself.");
        randomActivitiesArray.add("Snack: Have a healthy snack to refuel your energy levels.");
        randomActivitiesArray.add("Listen to music: Listen to calming or uplifting music to boost your mood.");
        randomActivitiesArray.add("Meditate: Spend a few minutes meditating or practicing mindfulness to center your thoughts.");
        randomActivitiesArray.add("Daydream: Let your mind wander and daydream for a moment to allow your brain to rest.");
        randomActivitiesArray.add("Light exercise: Do a quick set of jumping jacks or push-ups to get your blood flowing.");
        randomActivitiesArray.add("Read: Read a few pages of a book or an article on a topic of interest.");
        randomActivitiesArray.add("Puzzle or brain teaser: Engage in a quick brain teaser or puzzle game to stimulate your mind.");
        randomActivitiesArray.add("Doodle or sketch: Let your creative side flow and doodle or sketch something for fun.");
        randomActivitiesArray.add("Organize your workspace: Use the break to tidy up your desk and create a more organized environment.");
        randomActivitiesArray.add("Socialize: If you're working with others, use the break to chat briefly with colleagues or friends.");
        randomActivitiesArray.add("Play a short game: Play a quick video game or a mobile game to relax and unwind.");
        randomActivitiesArray.add("Look outside: Spend a moment looking out of the window and enjoying the view.");
        randomActivitiesArray.add("Practice a hobby: If you have a hobby like playing a musical instrument, writing, or drawing, use the break to indulge in it.");
        randomActivitiesArray.add("Mindful breathing: Focus solely on your breathing for a minute or two to calm your mind.");
        randomActivitiesArray.add("Do a mini workout: Perform a short exercise routine to get your body moving.");
        randomActivitiesArray.add("Laugh: Watch a funny video or read a joke to lighten your mood and reduce stress.");
        randomActivityGenerator = new RandomActivityGenerator(randomActivitiesArray);
        randomActivityGenerator.setBtnRandomActivityGenerator(findViewById(R.id.btnRandomActivityGenerator));


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
        int hrs = (int)(timeLeft/3600000);
        int mins = (int) (timeLeft/1000/60)%60;
        int secs = (int) (timeLeft / 1000)%60;


        String timeLeftFormatted = String.format(Locale.getDefault(),"%02d:%02d:%02d", hrs, mins, secs);
        txtMainCountDown.setText(timeLeftFormatted);
    }

//-----POMODORO-----------------------------------------------------------------------------------

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
    }


//-----NOT USING-----------------------------------------------------------------------------
    @Override
    public void onClick(View v) {}
}











class RandomActivityGenerator extends MainActivity implements View.OnClickListener{
//FIELDS
        private Button btnRandomActivityGenerator;
        private String randomActivitiesArray[];
        private ArrayList<String>randomActivitiesList;
        private String currActivity="", prevActivity = "";
//CONSTRUCTOR
        public RandomActivityGenerator(ArrayList<String>randomActivitiesList){
            this.randomActivitiesList = randomActivitiesList;
        }

//GETTER SETTERS

    public Button getBtnRandomActivityGenerator() {
        return btnRandomActivityGenerator;
    }

    public void setBtnRandomActivityGenerator(Button btnRandomActivityGenerator) {
        this.btnRandomActivityGenerator = btnRandomActivityGenerator;
    }
    public String[] getRandomActivitiesArray() {
        return randomActivitiesArray;
    }

    public void setRandomActivitiesArray(String[] randomActivitiesArray) {
        this.randomActivitiesArray = randomActivitiesArray;
    }
    public ArrayList<String> getRandomActivitiesList() {
        return randomActivitiesList;
    }
    public void setRandomActivitiesList(ArrayList<String> randomActivitiesList) {
        this.randomActivitiesList = randomActivitiesList;
    }



//METHODS
    public void generateRandomActivity(){
            btnRandomActivityGenerator.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    pickRandomActivity();
                    updateBtnRandomActivityGenereatorText();
                }
            });

    }

    private void updateBtnRandomActivityGenereatorText() {
            if(prevActivity.equals(currActivity)){pickRandomActivity();}
            btnRandomActivityGenerator.setText(currActivity);
    }

    private void pickRandomActivity() {
          prevActivity = currActivity;
        Collections.shuffle(randomActivitiesList);
        currActivity = randomActivitiesList.get(0);

    }


}












/*class PomodoroTimer extends MainActivity implements View.OnClickListener {
    static long startTimeInMillis;
    Boolean timerRunning;
    public void setTimerRunning(boolean timerRunning){
        this.timerRunning = timerRunning;
    }
    public Boolean getTimerRunning(){
        return timerRunning;
    }

    public void init(){
        //POMODORO VARS
        TextView txtPomodoro;
        Button btnStartPause, btnReset;
        CountDownTimer pomoCountDownTimer;
        Boolean timerRunning;
        long pomoTimeLeftMillis = startTimeInMillis;
    }

   /* //POMODORO VARS
    TextView txtPomodoro;
    Button btnStartPause, btnReset;
    CountDownTimer pomoCountDownTimer;
    Boolean timerRunning;
    long pomoTimeLeftMillis = startTimeInMillis;*/
/*//------CONSTRUCTOR-----------------------------------------------------------------------------
    public PomodoroTimer(long startTimeInMillis, Button btnStartPause, Button btnReset, TextView txtPomodoro){
        this.startTimeInMillis = startTimeInMillis;
        this.btnReset = btnReset;
        this.btnStartPause = btnStartPause;
        this.txtPomodoro = txtPomodoro;
       // btnStartPause = findViewById(R.id.btnStartPause);
       // btnReset = findViewById(R.id.btnReset);
    }

    //-----POMODORO-----------------------------------------------------------------------------------

    public void checkPomoButtons(){
     //   Button btnStartPause = findViewById(R.id.btnStartPause);
     //   Button btnReset = findViewById(R.id.btnReset);
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

}*/







