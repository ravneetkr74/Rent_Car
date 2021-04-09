package com.example.rentcar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.example.rentcar.ui.SharedPrefUtil;

public class splash_screen_first extends AppCompatActivity {

    Button splash_screen2;
    SharedPrefUtil sharedPrefUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
       // getSupportActionBar().hide(); //will hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen
        setContentView(R.layout.activity_splash_screen_first);
        sharedPrefUtil=SharedPrefUtil.getInstance();

        splash_screen2 = findViewById(R.id.next_btn_sp2);

        splash_screen2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sharedPrefUtil.getBoolean(SharedPrefUtil.LOGIN)){
                    Intent i=new Intent(getApplicationContext(),HomeActivity.class);

                    startActivity(i);

                }else {
                    Intent splash_screen_second = new Intent(getApplicationContext(),
                            com.example.rentcar.splash_screen_second.class);
                    startActivity(splash_screen_second);
                }

            }
        });
    }

}