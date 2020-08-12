package com.notbytes.barcodereader;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.notbytes.barcodereader.Model.ManifiestoCerrar;
import com.notbytes.barcodereader.Model.ManifiestoContador;
import com.notbytes.barcodereader.Model.MftoValijas;
import com.notbytes.barcodereader.io.APIRetrofitInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CerrarManifiestoAct extends AppCompatActivity {

    private ArrayList<String> data = new ArrayList<String>();
    private ArrayList<Integer> data1 = new ArrayList<Integer>();
    private ArrayList<String> data2 = new ArrayList<String>();
    private TableLayout table;
    private TextView Manifiesto;
    private APIRetrofitInterface jsonPlaceHolderApi;
    private TextView Valija;
    private TextView Guia;
    private Button CerrarMfto;
    private Button Volver;
    private Button AgregarValija;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cerrar_manifiesto);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://200.37.50.53/ApiSGD/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        jsonPlaceHolderApi = retrofit.create(APIRetrofitInterface.class);
        RecuperarDatos();
        //ManifiestoValija();
        onTouch();
    }

    public void onTouch() {
        CerrarMfto = findViewById(R.id.btnCerrar);
        Volver = findViewById(R.id.btnVolver);

        CerrarMfto.setOnTouchListener(new View.OnTouchListener() {
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

    public void RecuperarDatos(){
        //Llamar datos --------------------------------------------------
        Bundle b = getIntent().getExtras();
        String Mfto = b.getString("Mfto");
        String Valijas = b.getString("Valijas");
        String MftoAnio = b.getString("MftoAnio");
        String MftoNro = b.getString("MftoNro");
        String Suc = b.getString("Suc");
        String PaisDes = b.getString("PaisDes");
        String CiuDes = b.getString("CiuDes");
        String Estado = b.getString("Estado");
        String usuario = b.getString("usuario");
        String password = b.getString("password");
        String CodigoUsuario = b.getString("codigousuario");
        //---------------------------------------------------------------

        Manifiesto = findViewById(R.id.txtMfto);
        //Manifiesto.setText(Mfto);
        Manifiesto.setText("¿Desea cerrar el manifiesto "+MftoAnio+" - "+MftoNro+"?");
        /*Valija = findViewById(R.id.txtValija);
        Valija.append(""+Valijas+"?");
        Guia = findViewById(R.id.txtGuia);*/
        //String Guias = Guia.getText().toString();

        //Enviar datos---------------------------------------------------
        Intent i = new Intent(CerrarManifiestoAct.this, MenuPrincipal.class);
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
        c.putString("usuario", usuario);
        c.putString("password", password);
        c.putString("codigousuario", CodigoUsuario);
        i.putExtras(c);
        //startActivity(i);
        //----------------------------------------------------------------
    }

    public void RelletarTabla(){
        data.add("Valija");
        data2.add("Cerrado");
        table = findViewById(R.id.tabla);

    }

    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.btnCerrar:

                PasarDatos();
                //startActivity(new Intent(this, PopUp.class));
                //CerrarManifiesto();
                break;
            case R.id.btnAgregarValija:
                PasarDatosB();
                break;
        }

    }

    public void PasarDatos(){

        //Llamar datos --------------------------------------------------
        Bundle b = getIntent().getExtras();
        String Mfto = b.getString("Mfto");
        String Valijas = b.getString("Valijas");
        String MftoAnio = b.getString("MftoAnio");
        String MftoNro = b.getString("MftoNro");
        String Suc = b.getString("Suc");
        String PaisDes = b.getString("PaisDes");
        String CiuDes = b.getString("CiuDes");
        String Estado = b.getString("Estado");
        String usuario = b.getString("usuario");
        String password = b.getString("password");
        String CodigoUsuario = b.getString("codigousuario");
        String count = b.getString("count");
        //---------------------------------------------------------------

        //Enviar datos-----------------------------------------------------------------
        Intent i = new Intent(CerrarManifiestoAct.this, PopUp.class);
        //String passingdata = LoginText.getText().toString();
        Bundle c = new Bundle();
        //c.putString("Valijas", Estado);
        c.putString("Mfto", Mfto);
        c.putString("MftoAnio", MftoAnio);
        c.putString("MftoNro", MftoNro);
        c.putString("Suc", Suc);
        c.putString("PaisDes", PaisDes);
        c.putString("CiuDes", CiuDes);
        c.putString("Estado", Estado);
        c.putString("usuario", usuario);
        c.putString("password", password);
        c.putString("codigousuario", CodigoUsuario);
        c.putString("count", count);
        i.putExtras(c);
        startActivity(i);
        //------------------------------------------------------------------------------

    }

    public void PasarDatosA(){

        //Llamar datos --------------------------------------------------
        Bundle b = getIntent().getExtras();
        String Mfto = b.getString("Mfto");
        String Valijas = b.getString("Valijas");
        String MftoAnio = b.getString("MftoAnio");
        String MftoNro = b.getString("MftoNro");
        String Suc = b.getString("Suc");
        String PaisDes = b.getString("PaisDes");
        String CiuDes = b.getString("CiuDes");
        String Estado = b.getString("Estado");
        String usuario = b.getString("usuario");
        String password = b.getString("password");
        String CodigoUsuario = b.getString("codigousuario");
        String count = b.getString("count");
        //---------------------------------------------------------------

        //Enviar datos-----------------------------------------------------------------
        Intent i = new Intent(CerrarManifiestoAct.this, PopUp.class);
        //String passingdata = LoginText.getText().toString();
        Bundle c = new Bundle();
        //c.putString("Valijas", Estado);
        c.putString("Mfto", Mfto);
        c.putString("MftoAnio", MftoAnio);
        c.putString("MftoNro", MftoNro);
        c.putString("Suc", Suc);
        c.putString("PaisDes", PaisDes);
        c.putString("CiuDes", CiuDes);
        c.putString("Estado", Estado);
        c.putString("usuario", usuario);
        c.putString("password", password);
        c.putString("codigousuario", CodigoUsuario);
        c.putString("count", count);
        i.putExtras(c);
        startActivity(i);
        //------------------------------------------------------------------------------

    }

    public void PasarDatosB(){

        //Llamar datos --------------------------------------------------
        Bundle b = getIntent().getExtras();
        String Mfto = b.getString("Mfto");
        String Valijas = b.getString("Valijas");
        String MftoAnio = b.getString("MftoAnio");
        String MftoNro = b.getString("MftoNro");
        String Suc = b.getString("Suc");
        String PaisDes = b.getString("PaisDes");
        String CiuDes = b.getString("CiuDes");
        String Estado = b.getString("Estado");
        String usuario = b.getString("usuario");
        String password = b.getString("password");
        String CodigoUsuario = b.getString("codigousuario");
        //---------------------------------------------------------------

        //Enviar datos-----------------------------------------------------------------
        Intent i = new Intent(CerrarManifiestoAct.this, ValijaAct.class);
        //String passingdata = LoginText.getText().toString();
        Bundle c = new Bundle();
        //c.putString("Valijas", Estado);
        c.putString("Mfto", Mfto);
        c.putString("MftoAnio", MftoAnio);
        c.putString("MftoNro", MftoNro);
        c.putString("Suc", Suc);
        c.putString("PaisDes", PaisDes);
        c.putString("CiuDes", CiuDes);
        c.putString("Estado", Estado);
        c.putString("usuario", usuario);
        c.putString("password", password);
        c.putString("codigousuario", CodigoUsuario);
        i.putExtras(c);
        startActivity(i);
        //------------------------------------------------------------------------------

    }


    private void CerrarManifiesto(){

        //Llamar datos --------------------------------------------------
            Bundle b = getIntent().getExtras();
            final String Mfto = b.getString("Mfto");
            String Valijas = b.getString("Valijas");
            final String MftoAnio = b.getString("MftoAnio");
            final String MftoNro = b.getString("MftoNro");
            final String Suc = b.getString("Suc");
            final String PaisDes = b.getString("PaisDes");
            final String CiuDes = b.getString("CiuDes");
            final String Estado = b.getString("Estado");
            final String usuario = b.getString("usuario");
            final String password = b.getString("password");
            final String CodigoUsuario= b.getString("codigousuario");
        //---------------------------------------------------------------

        //Aqui enviar los datos-------------------------------------------------------------------------------------------
            //String resul = mTvResult.getText().toString();
                    ManifiestoCerrar manifiestoCerrar = new ManifiestoCerrar(""+MftoNro,""+Suc,""+MftoAnio,""+usuario,"3619");
                    Call<ManifiestoCerrar> call = jsonPlaceHolderApi.createPost(manifiestoCerrar);
                    call.enqueue(new Callback<ManifiestoCerrar>() {
                @Override
                public void onResponse(Call<ManifiestoCerrar> call, Response<ManifiestoCerrar> response) {
                    if (!response.isSuccessful()) {
                        Toast.makeText(getApplicationContext(), "Ha ocurrido un error con el manifiesto.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    ManifiestoCerrar postsResponse = response.body();

                    String estado = postsResponse.Estado();
                    String mensaje = postsResponse.Mensaje();
                    Toast.makeText(getApplicationContext(), "Manifiesto cerrado correctamente.", Toast.LENGTH_SHORT).show();

                    Intent i = new Intent(CerrarManifiestoAct.this, DespacharFinal.class);
                    Bundle c = new Bundle();
                    //c.putString("Estado", Estado);
                    //Enviar datos---------------------------------------------------
                    c.putString("Valijas", Estado);
                    c.putString("Mfto", Mfto);
                    c.putString("MftoAnio", MftoAnio);
                    c.putString("MftoNro", MftoNro);
                    c.putString("Suc", Suc);
                    c.putString("PaisDes", PaisDes);
                    c.putString("CiuDes", CiuDes);
                    c.putString("Estado", Estado);
                    c.putString("usuario", usuario);
                    c.putString("password", password);
                    c.putString("codigousuario", CodigoUsuario);
                    i.putExtras(c);
                    startActivity(i);
                    //----------------------------------------------------------------
                    return;
                }

                @Override
                public void onFailure(Call<ManifiestoCerrar> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "Fallo al ingresar los datos, compruebe su red.", Toast.LENGTH_SHORT).show();
                    //Titulo.setText(t.getMessage());
                    return;
                }
            });
        }

    private void ContadorManifiestos(){

        ManifiestoContador manifiestoContador = new ManifiestoContador("1","","");
        Call<ManifiestoContador> call = jsonPlaceHolderApi.createPost(manifiestoContador);
        call.enqueue(new Callback<ManifiestoContador>() {
            @Override
            public void onResponse(Call<ManifiestoContador> call, Response<ManifiestoContador> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Error.", Toast.LENGTH_SHORT).show();
                    return;
                }
                ManifiestoContador postsResponse = response.body();

                //String Estado = postsResponse.estado();
                //String Guia = postsResponse.Guias();
                Toast.makeText(getApplicationContext(), "API Contador Manifiesto Iniciado", Toast.LENGTH_SHORT).show();

                Intent i = new Intent(CerrarManifiestoAct.this, DespacharFinal.class);
                Bundle c = new Bundle();
                //c.putString("Estado", Estado);
                //c.putString("Guia", Guia);
                //i.putExtras(c);
                //Mensaje.append(""+postsResponse.estado());
                //Mensaje.append(""+postsResponse.Guias());
                //startActivity(i);
                //Titulo.append(""+postsResponse.estado());
                return;
            }
            @Override
            public void onFailure(Call<ManifiestoContador> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Fallo al ingresar los datos, compruebe su red.", Toast.LENGTH_SHORT).show();
                //Titulo.setText(t.getMessage());
                return;
            }
        });
    }

    private void ValijasMftos(){

        MftoValijas mftoValijas = new MftoValijas("00025111","PELIM01","2016");//
        Call<List<MftoValijas>> call = jsonPlaceHolderApi.createPost(mftoValijas);
        call.enqueue(new Callback<List<MftoValijas>>() {
        //<MftoValijas> call = jsonPlaceHolderApi.createPost(mftoValijas);
        //call.enqueue(new Callback<MftoValijas>() {
            @Override
            public void onResponse(Call<List<MftoValijas>> call, Response<List<MftoValijas>> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(getApplicationContext(),"No correcto",Toast.LENGTH_SHORT).show();
                    return;
                }
                List<MftoValijas> rptas = response.body();
                //SocialData data = response.body();
                List<MftoValijas> datalist = new ArrayList<>();
                //datalist = rptas.mftoValijas();
                /*String Mft_Ano = postsResponse.mftoValijas();
                int Mft_Nro = postsResponse.Total();
                String Suc_Code = postsResponse.Cerrado();*/
                Toast.makeText(getApplicationContext(),"Pasó correctamente"+rptas,Toast.LENGTH_SHORT).show();
                return;
            }
            @Override
            public void onFailure(Call<List<MftoValijas>> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Fallo al ingresar los datos, compruebe su red.",Toast.LENGTH_SHORT).show();
                return;
            }
        });
    }
    }


