package com.example.ruteandoapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

public class SplashScreen extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 5000;
    //Hooks
    View first,second,third,fourth,fifth,sixth;
    TextView nombreapp;
    ImageView balloon;
    //Animations
    //Animation topAnimantion,bottomAnimation,middleAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseMessaging.getInstance().subscribeToTopic("RETOS");
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);
        //Hooks

        //balloon = findViewById(R.id.ballon);
        //nombreapp = findViewById(R.id.nombreapp);

        //Animation Calls
        //topAnimantion = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        //bottomAnimation = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);
        //middleAnimation = AnimationUtils.loadAnimation(this, R.anim.middle_animation);

        //-----------Setting Animations to the elements of Splash


        //balloon.setAnimation(topAnimantion);
        //nombreapp.setAnimation(bottomAnimation);

        //Splash Screen Code to call new Activity after some time
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //Calling new Activity
                Intent intent = new Intent(SplashScreen.this, Login.class);
                startActivity(intent);
                finish();
            }
        }, SPLASH_TIME_OUT);

    }

    private void sendNotificationData(String title, String messageBody, String clickaction){
        Intent intent;
        if(clickaction.equals("RETOS")){
            intent = new Intent(this, Principal.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        }
    }

    }