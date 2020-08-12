package com.notbytes.barcodereader;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class DespacharFinal extends AppCompatActivity {
    private Button Volver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_despachar_final);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnVolver:
                Intent i = new Intent(DespacharFinal.this, MenuPrincipal.class);
                //Llamar datos -------------------------------------------------
                Bundle b = getIntent().getExtras();
                final String Mfto = b.getString("Mfto");
                String Valijas = b.getString("Valijas");
                final String MftoAnio = b.getString("MftoAnio");
                final String MftoNro = b.getString("MftoNro");
                final String Suc = b.getString("Suc");
                final String PaisDes = b.getString("PaisDes");
                final String CiuDes = b.getString("CiuDes");
                final String Estado = b.getString("Estado");
                final String ValijaID = b.getString("ValijaID");
                //---------------------------------------------------------------

                //Enviar datos---------------------------------------------------

                Bundle c = new Bundle();
                c.putString("Valijas", Estado);
                c.putString("Mfto", Mfto);
                c.putString("MftoAnio", MftoAnio);
                c.putString("MftoNro", MftoNro);
                c.putString("Suc", Suc);
                c.putString("PaisDes", PaisDes);
                c.putString("CiuDes", CiuDes);
                c.putString("Estado", Estado);
                c.putString("ValijaID", ValijaID);
                i.putExtras(c);
                //----------------------------------------------------------------
                startActivity(i);
                break;
        }
    }

    public void onTouch() {

        Volver = findViewById(R.id.btnVolver);
        Volver.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_MOVE) {
                    v.setBackgroundResource(R.drawable.rounded_cornersscharff);
                }

                if (event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_CANCEL) {
                    v.setBackgroundResource(R.drawable.rounded_corners);
                    //v.setBackgroundColor(Color.parseColor("@drawable/rounded_corners"));
                }
                return false;
            }
        });
    }


}