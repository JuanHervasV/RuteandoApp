package com.notbytes.barcodereader;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class splash_screen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        Thread timer = new Thread(){
            public void run(){
                try{
                    sleep(5990); //duración del gif
                }
                catch (InterruptedException e){
                    e.printStackTrace();
                }finally {
                    Intent intent = new Intent(splash_screen.this, Logeo.class);
                    startActivity(intent);
                }
            }
        };
    timer.start();
    }

    @Override
        protected void  onPause(){
        super.onPause();
        finish();
    }
}
