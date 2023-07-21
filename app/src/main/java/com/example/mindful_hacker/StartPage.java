package com.example.mindful_hacker;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;
import java.util.Date;

public class StartPage extends Activity {
    //VARIABLES
    private DatePickerDialog datePickerDialog;
    private Button dateButton;
//------------------------------------------------------------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        initDatePicker();
        dateButton = findViewById(R.id.datePickerButton);
        dateButton.setText(getTodaysDate());
    }
    //-------------------------------------------------------------------------------------------------------

    private void initDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
                @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                month = month + 1;//set initial month to 1, january = 1
                String date = makeDateString(day, month, year);
                dateButton.setText(date);

            }
        };
        Calendar calender =  Calendar.getInstance();
        int year = calender.get(Calendar.YEAR);
        int month = calender.get(Calendar.MONTH);
        int day = calender.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_LIGHT;//CHOOSE WHATEVER STYLE

        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);
        datePickerDialog.getDatePicker().setMinDate(new Date().getTime() - 10000);

    }

    private String makeDateString(int day, int month, int year) {
        return getMonthFormat(month) + " " + day + " " +  year;
    }

    private String getMonthFormat(int month) {
        if(month == 1){return "JAN";}
        if(month == 2){return "FEB";}
        if(month == 3){return "MAR";}
        if(month == 4){return "APR";}
        if(month == 5){return "MAY";}
        if(month == 6){return "JUN";}
        if(month == 7){return "JUL";}
        if(month == 8){return "AUG";}
        if(month == 9){return "SEP";}
        if(month ==10){return "OCT";}
        if(month ==11){return "NOV";}
        if(month ==12){return "DEC";}
        return "JAN";
    }
    private String getTodaysDate() {
        Calendar calender =  Calendar.getInstance();
        int year = calender.get(Calendar.YEAR);
        int month = calender.get(Calendar.MONTH);
        month = month+1;
        int day = calender.get(Calendar.DAY_OF_MONTH);
        return makeDateString(day, month, year);
    }


    public void openDatePicker(){
        datePickerDialog.show();
    }
}
