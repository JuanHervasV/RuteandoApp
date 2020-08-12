package com.notbytes.barcodereader;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.notbytes.barcodereader.Model.ValijaCerrar;
import com.notbytes.barcodereader.Model.ValijaContador;
import com.notbytes.barcodereader.Model.ValijaStatus;
import com.notbytes.barcodereader.io.APIRetrofitInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CerrarValijaAct extends AppCompatActivity {
    private TextView Titulo;
    private TextView Manifiesto;
    private TextView Guia;
    private TextView Valija;
    private Button CerrarValija;
    private Button Volver;
    private APIRetrofitInterface jsonPlaceHolderApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cerrar_valija);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://200.37.50.53/ApiSGD/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        jsonPlaceHolderApi = retrofit.create(APIRetrofitInterface.class);
        RecuperarDatos();
        ValijasContador();
        onTouch();
    }

    public void onTouch() {

        CerrarValija = findViewById(R.id.btnCerrar);
        Volver = findViewById(R.id.btnVolver);

        CerrarValija.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_MOVE) {
                    v.setBackgroundResource(R.drawable.rounded_cornerneutral);
                    //v.setBackgroundColor(Color.parseColor("#9C9C9C"));
                }

                if (event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_CANCEL) {
                    v.setBackgroundResource(R.drawable.rounded_cornersscharff);
                    //v.setBackgroundColor(Color.parseColor("#FF7177"));
                }
                return false;
            }
        });

    }

    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnCerrar:
                cerrarValija();
                break;
        }
    }

    public void PasarDatos(){
        Manifiesto = findViewById(R.id.txtMfto);
        Valija = findViewById(R.id.txtValija);
        //Llamar datos ----------------------------------------------------------
        Bundle b = getIntent().getExtras();
        String Valijas = b.getString("Valijas");
        String Mfto = b.getString("Mfto");
        String MftoAnio = b.getString("MftoAnio");
        String MftoNro = b.getString("MftoNro");
        String Suc = b.getString("Suc");
        String PaisDes = b.getString("PaisDes");
        String CiuDes = b.getString("CiuDes");
        String Estado = b.getString("Estado");
        String usuario = b.getString("usuario");
        String password = b.getString("password");
        String count = b.getString("count");
        //----------------------------------------------------------------------
        Manifiesto.setText(Mfto);
        Valija.setText(Valijas);

        //Enviar datos----------------------------------------------------------
        Intent i = new Intent(CerrarValijaAct.this, CerrarManifiestoAct.class);
        //String passingdata = LoginText.getText().toString();
        Bundle c = new Bundle();
        c.putString("Valijas", Valijas);
        c.putString("Mfto", Mfto);
        c.putString("MftoAnio", MftoAnio);
        c.putString("MftoNro", MftoNro);
        c.putString("Suc", Suc);
        c.putString("PaisDes", PaisDes);
        c.putString("CiuDes", CiuDes);
        c.putString("Estado", Estado);
        c.putString("usuario", usuario);
        c.putString("password", password);
        c.putString("count", count);
        i.putExtras(c);
        startActivity(i);
        //----------------------------------------------------------------------

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
        //---------------------------------------------------------------

        Manifiesto = findViewById(R.id.txtMfto);
        //Manifiesto.setText(Mfto);
        Manifiesto.setText("Manifiesto: "+Suc+" "+MftoAnio+"-"+MftoNro);
        Valija = findViewById(R.id.txtValija);
        Valija.append(""+Valijas+"?");
        Guia = findViewById(R.id.txtGuia);
        //String Guias = Guia.getText().toString();

        //Enviar datos---------------------------------------------------
        Intent i = new Intent(CerrarValijaAct.this, CerrarManifiestoAct.class);
        //String passingdata = LoginText.getText().toString();
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
        i.putExtras(c);
        //startActivity(i);
        //----------------------------------------------------------------
    }

    private void cerrarValija(){
        //Aqui enviar los datos-------------------------------------------------------------------------------------------
        //String resul = mTvResult.getText().toString();
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
        final String usuario = b.getString("usuario");
        final String password = b.getString("password");
        final String count = b.getString("count");
        //---------------------------------------------------------------
        ValijaCerrar valijaCerrar = new ValijaCerrar(ValijaID);
        Call<ValijaCerrar> call = jsonPlaceHolderApi.createPost(valijaCerrar);
        call.enqueue(new Callback<ValijaCerrar>() {
            @Override
            public void onResponse(Call<ValijaCerrar> call, Response<ValijaCerrar> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(getApplicationContext(),"Valija no correcta.",Toast.LENGTH_SHORT).show();
                    return;
                }
                ValijaCerrar postsResponse = response.body();
                Toast.makeText(getApplicationContext(),"Valija cerrada correctamente",Toast.LENGTH_SHORT).show();

                Intent i = new Intent(CerrarValijaAct.this, CerrarManifiestoAct.class);
                Bundle c = new Bundle();
                //Enviar datos---------------------------------------------------
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
                c.putString("count", count);
                i.putExtras(c);
                //startActivity(i);
                //----------------------------------------------------------------
                i.putExtras(c);
                startActivity(i);

                return;
            }
            @Override
            public void onFailure(Call<ValijaCerrar> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Fallo al ingresar los datos, compruebe su red.",Toast.LENGTH_SHORT).show();
                //Titulo.setText(t.getMessage());
                return;
            }
        });
    }

    private void cerrarStatus(){
        //Aqui enviar los datos-------------------------------------------------------------------------------------------

        ValijaStatus valijaStatus = new ValijaStatus("145", "123");
        Call<ValijaStatus> call = jsonPlaceHolderApi.createPost(valijaStatus);
        call.enqueue(new Callback<ValijaStatus>() {
            @Override
            public void onResponse(Call<ValijaStatus> call, Response<ValijaStatus> response) {
                if(!response.isSuccessful()){

                    Toast.makeText(getApplicationContext(),"No se pudo cambiar el status",Toast.LENGTH_SHORT).show();

                    PasarDatos();

                    return;
                }
                ValijaStatus postsResponse = response.body();

                Toast.makeText(getApplicationContext(),"Status cambiado exitosamente",Toast.LENGTH_SHORT).show();

                Intent i = new Intent(CerrarValijaAct.this, CerrarManifiestoAct.class);
                Bundle c = new Bundle();

                i.putExtras(c);

                startActivity(i);

                return;
            }
                @Override
            public void onFailure(Call<ValijaStatus> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Fallo al ingresar los datos, compruebe su red.",Toast.LENGTH_SHORT).show();
                return;
            }
        });
    }

    private void ValijasContador() {
        //Aqui enviar los datos-------------------------------------------------------------------------------------------

        Guia = findViewById(R.id.txtGuia);

        //Llamar datos -------------------------------------------------
        Bundle c = getIntent().getExtras();
        String ValijaID = c.getString("ValijaID");
        String Valijas = c.getString("Valijas");
        String Mfto = c.getString("Mfto");
        String MftoAnio = c.getString("MftoAnio");
        String MftoNro = c.getString("MftoNro");
        String Suc = c.getString("Suc");
        String PaisDes = c.getString("PaisDes");
        String CiuDes = c.getString("CiuDes");
        String Estado = c.getString("Estado");
        //---------------------------------------------------------------

        ValijaContador valijaContador = new ValijaContador(""+ValijaID);
        Call<ValijaContador> call = jsonPlaceHolderApi.createPost(valijaContador);
        call.enqueue(new Callback<ValijaContador>() {
            @Override
            public void onResponse(Call<ValijaContador> call, Response<ValijaContador> response) {
                if (!response.isSuccessful()) {

                    Toast.makeText(getApplicationContext(), "No existe la valija", Toast.LENGTH_SHORT).show();
                    return;
                }
                ValijaContador postsResponse = response.body();
                String Valija = postsResponse.valija();
                String Total = postsResponse.total();

                Toast.makeText(getApplicationContext(), "API Contador Iniciado", Toast.LENGTH_SHORT).show();

                Intent i = new Intent(CerrarValijaAct.this, CerrarManifiestoAct.class);
                Bundle c = new Bundle();
                i.putExtras(c);
                Guia.append(" "+Total);
                return;

            }

            @Override
            public void onFailure(Call<ValijaContador> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Fallo al ingresar los datos, compruebe su red.", Toast.LENGTH_SHORT).show();
                return;
            }
        });
    }
}
