package com.example.mindful_hacker;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;
import java.util.Date;

public class StartPage extends Activity implements View.OnClickListener, DatePickerDialog.OnDateSetListener, TimePicker.OnTimeChangedListener {
    //VARIABLES
    DatePicker datePicker;
    public TimePicker timePicker;
    Calendar now = Calendar.getInstance();
    Button btnNextPg;
    int day, month, year;
    int hour, minute;

    int timeNow;



    //---------ONCREATE---------------------------------------------------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        init();

        nextPage();
        reset();
    }

    //-----INITIALIZE--------------------------------------------------------------------------------------------------

    private void init(){
        datePicker = (DatePicker) findViewById(R.id.datePicker);
        timePicker = (TimePicker) findViewById(R.id.timePicker);
        //TimePicker timePicker = new TimePicker(getApplicationContext());

        btnNextPg = (Button) findViewById(R.id.btnNextPg);
        btnNextPg.setOnClickListener(this);
    }
    //---------WHEN BTN CLICKED, NEXT PAGE-----------------------------------------------------------------------------

    private void nextPage(){


        Intent i = new Intent(getApplicationContext(), MainActivity.class);


        btnNextPg.setOnClickListener(new View.OnClickListener() {


            public void onClick(View v) {

                timePicker = new TimePicker(getApplicationContext());
                day = datePicker.getDayOfMonth();
                month = datePicker.getMonth() ;
                year = datePicker.getYear();
                minute = timePicker.getMinute();
                hour = timePicker.getHour();



                i.putExtra("key_day", day);
                i.putExtra("key_month", month);
                i.putExtra("key_year", year);
                i.putExtra("key_hour", hour);
                i.putExtra("key_minute", minute);


                startActivity(i);


            }
        });



    }

//--------RESET VARS WHEN INTENT CHANGE FROM MAIN-----------------------------------------------------------------------------------------------

    private void reset(){
    }

//---------NOT USED---------------------------------------------------------------------------------
    public void onClick(View v) {}
    @Override
    public void onDateSet(DatePicker view, int year, int month, int day) {}
    @Override
    public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {}
}
