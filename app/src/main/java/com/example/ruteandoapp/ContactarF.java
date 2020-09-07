package com.example.ruteandoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ContactarF extends AppCompatActivity {
    private Button enviar;
    private EditText edt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contactar_f);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnenviar:
                contactarwsp();
                break;
        }
    }
    public void contactarwsp(){
        enviar = findViewById(R.id.btnenviar);
        edt = findViewById(R.id.editTextMulti);
        String contenido = edt.getText().toString();
        //Uri uri = Uri.parse("https://wa.me/14155238886/?text=Mensaje: "+contenido); // missing 'http://' will cause crashed
        Uri uri = Uri.parse("https://wa.me/51922735915/?text="+contenido); // missing 'http://' will cause crashed
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);

    }
}