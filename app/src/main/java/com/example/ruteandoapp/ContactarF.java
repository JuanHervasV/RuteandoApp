package com.example.ruteandoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ContactarF extends AppCompatActivity {
    private Button enviar;
    private EditText edt;
    private long mLastClickTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contactar_f);
        onTouch();
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnenviar:
                contactarwsp();
                break;
        }
    }

    public void onTouch() {
        enviar = findViewById(R.id.btnenviar);
        enviar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_MOVE) {
                    v.setBackgroundResource(R.drawable.rounded_cornermorado);
                }

                if (event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_CANCEL) {
                    v.setBackgroundResource(R.drawable.rounded_cornersscharff);
                    //v.setBackgroundColor(Color.parseColor("@drawable/rounded_corners"));
                }
                return false;
            }
        });
    }

    public void contactarwsp(){
        if (SystemClock.elapsedRealtime() - mLastClickTime < 2000) {
            return;
        }
        /*mLastClickTime = SystemClock.elapsedRealtime();
        enviar = findViewById(R.id.btnenviar);
        edt = findViewById(R.id.editTextMulti);
        String contenido = edt.getText().toString();
        //Uri uri = Uri.parse("https://wa.me/14155238886/?text=Mensaje: "+contenido); // missing 'http://' will cause crashed
        Uri uri = Uri.parse("https://wa.me/51922735915/?text="+contenido); // missing 'http://' will cause crashed
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);*/
        finish();
    }
}