package com.example.ruteandoapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.messaging.FirebaseMessaging;


public class Principal extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        //
        //FirebaseMessaging.getInstance().subscribeToTopic("RETOS");
        //        API de Firebase - Redireccionar a clase.
        FirebaseMessaging.getInstance().subscribeToTopic("RETOS")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        String msg = "Iniciando link inteligente";

                        //Log.d(TAG, msg);
                        Toast.makeText(Principal.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });
        //Fin API de Firebase


    }
}