package com.example.mindful_hacker;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

public class MainActivity extends Activity {
    TextView days;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        days = (TextView) findViewById(R.id.txtView1);

        Intent intent = getIntent();
        final String dayVal = intent.getStringExtra("key_day");

        days.setText(dayVal);

    }
}