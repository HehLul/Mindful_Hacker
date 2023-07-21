package com.example.mindful_hacker;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
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
    private DatePicker datePicker;
    TimePicker timePicker;
    private Button btnNextPg;
//------------------------------------------------------------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        init();
    }


    //-----INITIALIZE--------------------------------------------------------------------------------------------------

    private void init(){
        datePicker = (DatePicker) findViewById(R.id.datePicker);


        timePicker = (TimePicker) findViewById(R.id.timePicker);
        //timePicker.setIs24HourView(false); // used to display AM/PM mode

        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                // display a toast with changed values of time picker
                Toast.makeText(getApplicationContext(), hourOfDay + "  " + minute, Toast.LENGTH_SHORT).show();

            }
        });

        btnNextPg = (Button) findViewById(R.id.btnNextPg);
        btnNextPg.setOnClickListener(this);
    }



    //----WHEN NEXT BUTN CLICKED-------------------------------------------------------------
    @Override
    public void onClick(View v) {


        if(v.getId() == R.id.btnNextPg){
            // get the values for day of month , month and year from a date picker
            String day = "Day = " + datePicker.getDayOfMonth();
            String month = "Month = " + (datePicker.getMonth() + 1);
            String year = "Year = " + datePicker.getYear();
            String hour = "Hour =" + timePicker.getHour();
            // display the values by using a toast
            btnNextPg.setText(day + hour);
         //   btnNextPg.setText(hour);
            //Toast.makeText(getApplicationContext(), day + "\n" + month + "\n" + year, Toast.LENGTH_LONG).show();
        }

    }


    @Override
    public void onDateSet(DatePicker view, int year, int month, int day) {

    }

    @Override
    public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {

    }
}
