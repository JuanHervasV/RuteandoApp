package com.notbytes.barcodereader;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

public class MenuPrincipal extends AppCompatActivity {
    private Button Despachar;
    private Button Consultar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);
        onTouch();
    }

    public void onTouch() {

        Despachar = findViewById(R.id.btnDespachar);
        Consultar = findViewById(R.id.btnConsultar);
        Despachar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_MOVE) {
                    v.setBackgroundResource(R.drawable.rounded_cornersscharff);
                }

                if (event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_CANCEL) {
                    v.setBackgroundResource(R.drawable.rounded_corners);
                }
                return false;
            }
        });

        Consultar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_MOVE) {
                    v.setBackgroundResource(R.drawable.rounded_cornersscharff);
                }

                if (event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_CANCEL) {
                    v.setBackgroundResource(R.drawable.rounded_corners);
                }
                return false;
            }
        });

    }

    public void RecuperarDatos(){
        //Llamar datos -------------------------------------------------
        Bundle b = getIntent().getExtras();
        String Mfto = b.getString("Mfto");
        String Valijas = b.getString("Valijas");
        String MftoAnio = b.getString("MftoAnio");
        String MftoNro = b.getString("MftoNro");
        String Suc = b.getString("Suc");
        String PaisDes = b.getString("PaisDes");
        String CiuDes = b.getString("CiuDes");
        String Estado = b.getString("Estado");
        String ValijaID = b.getString("ValijaID");
        String usuario = b.getString("usuario");
        String password = b.getString("password");
        String CodigoUsuario = b.getString("codigousuario");
        //---------------------------------------------------------------
        if (MftoAnio == "null"){
            Intent i = new Intent(MenuPrincipal.this, ManifiestoAct.class);
            startActivity(i);
            return;
        }
        else{
            Intent i = new Intent(MenuPrincipal.this, ManifiestoAct.class);
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
            c.putString("usuario", usuario);
            c.putString("password", password);
            c.putString("codigousuario", CodigoUsuario);
            i.putExtras(c);
            startActivity(i);
            return;
        }

    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnDespachar:
                RecuperarDatos();
                break;
            case R.id.btnConsultar:
                RecuperarDatos();
                break;
        }

    }

    public void RecuperarUsuarioDespachar(){
        //String passingdata = LoginText.getText().toString();
        //Intent i = new Intent(Logeo.this, MainActivity.class);
        Spinner mySpinner = findViewById(R.id.estado);
        //final String Estado = mySpinner.getSelectedItem().toString();

        //Llamar datos usuario--------------------------
        Bundle c = getIntent().getExtras();
        String receivingdata = c.getString("Key");
        //String usu = receivingdata.toString();
        //TextView tv = findViewById(R.id.usuarios);
        //tv.append(receivingdata);
        //----------------------------------------------
        String passingdata="Hola";
        //Intent i = new Intent(Logeo.this, MainActivity.class);
        Intent i = new Intent(MenuPrincipal.this, ManifiestoAct.class);
        //Intent a = new Intent(Logeo.this, Pre_estado.class);
        Bundle b = new Bundle();
        b.putString("Key", receivingdata);
        //b.putString("Key2", Estado);
        i.putExtras(b);
        startActivity(i);
    }

    public void RecuperarUsuarioConsultar(){
        //String passingdata = LoginText.getText().toString();
        //Intent i = new Intent(Logeo.this, MainActivity.class);
        Spinner mySpinner = findViewById(R.id.estado);
        //final String Estado = mySpinner.getSelectedItem().toString();

        //Llamar datos usuario--------------------------
        Bundle c = getIntent().getExtras();
        String receivingdata = c.getString("Key");
        //String usu = receivingdata.toString();
        //TextView tv = findViewById(R.id.usuarios);
        //tv.append(receivingdata);
        //----------------------------------------------
        String passingdata="Hola";
        //Intent i = new Intent(Logeo.this, MainActivity.class);
        Intent i = new Intent(MenuPrincipal.this, MainActivity.class);
        //Intent a = new Intent(Logeo.this, Pre_estado.class);
        Bundle b = new Bundle();
        b.putString("Key", receivingdata);
        //b.putString("Key2", Estado);
        i.putExtras(b);
        startActivity(i);
    }

}
