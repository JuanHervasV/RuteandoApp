package com.notbytes.barcodereader;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class Pre_estado extends AppCompatActivity {
    private Button Btnsig;
    private Spinner spin;
    private TextView txtintro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Btnsig = findViewById(R.id.btnsig);
        spin = findViewById(R.id.estado);
        txtintro = findViewById(R.id.preestado);
        setContentView(R.layout.activity_pre_estado);
        FillSpinner();
        //RecuperarUsuario();
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnsig:
                RecuperarUsuario();
                break;
        }
    }
    private void FillSpinner(){
        spin = findViewById(R.id.estado);
        //String[] wee = list2.toArray(new String[list2.size()]);
        //final String[] str={"Report 1","Report 2","Report 3","Report 4","Report 5"};
        ArrayList<String> str = new ArrayList<>();
        str.add(new String("Recibido en MIAMI"));
        str.add(new String("En tránsito"));
        str.add(new String("En almacén Scharff(LIMA)"));
        str.add(new String("Entregado"));

        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(this,  android.R.layout.simple_spinner_dropdown_item, str);
        adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
        // Spinner spinYear = (Spinner)findViewById(R.id.spin);
        spin.setAdapter(adapter);
    }

    public void RecuperarUsuario(){
        //String passingdata = LoginText.getText().toString();
        //Intent i = new Intent(Logeo.this, MainActivity.class);
        Spinner mySpinner = findViewById(R.id.estado);
        final String Estado = mySpinner.getSelectedItem().toString();

        //Llamar datos usuario--------------------------
        Bundle c = getIntent().getExtras();
        String receivingdata = c.getString("Key");
        //String usu = receivingdata.toString();
        //TextView tv = findViewById(R.id.usuarios);
        //tv.append(receivingdata);
        //----------------------------------------------
        //String passingdata="Hola";
        //Intent i = new Intent(Logeo.this, MainActivity.class);
        Intent i = new Intent(Pre_estado.this, MainActivity.class);
        //Intent a = new Intent(Logeo.this, Pre_estado.class);
        Bundle b = new Bundle();
        b.putString("Key", receivingdata);
        b.putString("Key2", Estado);
        i.putExtras(b);
        startActivity(i);
    }

    public void RecuperarSpinner(){
        Spinner mySpinner = findViewById(R.id.estado);
        final String Estado = mySpinner.getSelectedItem().toString();

        //Recuperar datos-------------------------------
        Intent i = new Intent(Pre_estado.this, MainActivity.class);
        //Intent a = new Intent(Logeo.this, Pre_estado.class);
        Bundle b = new Bundle();
        b.putString("Key", Estado);
        i.putExtras(b);
        startActivity(i);
        //-----------------------------------------------

    }
}
