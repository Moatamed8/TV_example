package com.example.tvshow.activites;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;


import com.example.tvshow.R;

import static java.lang.Thread.sleep;

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getWindow().setStatusBarColor(ContextCompat.getColor(Splash.this,R.color.colorPrimaryDark));

        Thread thread=new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    sleep(5000);
                    Intent home =new Intent(Splash.this,MainActivity.class);
                    startActivity(home);


                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        thread.start();


    }
}