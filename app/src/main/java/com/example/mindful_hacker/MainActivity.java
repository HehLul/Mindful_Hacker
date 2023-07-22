package com.example.mindful_hacker;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {

    TextView txtMainCountDown;
    Button btnEdit;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();

        init();

       // countdown();
       // edit();

    }

    private void init() {
        txtMainCountDown = findViewById(R.id.MainCountDown);
        btnEdit = findViewById(R.id.btnEdit);

        context = this;
        button editButton = new button(btnEdit, context);

        if(editButton.getClicked() == true){
            Log.d("MainActivity", "In if state");
            Intent i = new Intent(this, StartPage.class);
            startActivity(i);
        }

    }
}


class button extends MainActivity implements  View.OnClickListener {
    Button btn;
    Context context;
   Boolean clicked = false;
    Intent i = getIntent();

    public button(Button btn, Context context) {
        this.btn = btn;
        this.context = context;
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //btn.setText("HELLLOOOOO");
                //Log.d("MainActivity", "This is a debug message");
                if(v.getId()==(R.id.btnEdit)){
                    clicked = true;
                }


            }

        });



    }

    public Boolean getClicked(){
        return clicked;
    }



    @Override
    public void onClick(View v) {
        clicked = true;
    }
}