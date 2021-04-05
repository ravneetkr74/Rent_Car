package com.example.rentcar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class splash_screen_second extends AppCompatActivity {

    Button splash_screen_last;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
       // getSupportActionBar().hide(); //will hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen
        setContentView(R.layout.activity_splash_screen_second);

        splash_screen_last = findViewById(R.id.next_btn_sp3);

        splash_screen_last.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent last_splash = new Intent(getApplicationContext(),splash_screen_third.class);
                startActivity(last_splash);
            }
        });
    }
}