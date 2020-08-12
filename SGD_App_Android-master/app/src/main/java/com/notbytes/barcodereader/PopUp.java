package com.notbytes.barcodereader;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.notbytes.barcodereader.Model.ContadorGuiasMfto;
import com.notbytes.barcodereader.Model.ManifiestoCerrar;
import com.notbytes.barcodereader.Model.ManifiestoContador;
import com.notbytes.barcodereader.io.APIRetrofitInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PopUp extends Activity {
    private Button ButtonSi;
    private Button ButtonNo;
    private TextView Texto;
    private APIRetrofitInterface jsonPlaceHolderApi;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.popup);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://200.37.50.53/ApiSGD/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        jsonPlaceHolderApi = retrofit.create(APIRetrofitInterface.class);

        DisplayMetrics dm= new DisplayMetrics();

        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout(950, 610);

        onTouch();

        RecuperarDatos();

        ContadorGuiasMftoGeneral();

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

        /*Valija = findViewById(R.id.txtValija);
        Valija.append(""+Valijas+"?");
        Guia = findViewById(R.id.txtGuia);*/
        //String Guias = Guia.getText().toString();

        //Enviar datos---------------------------------------------------
        Intent i = new Intent(PopUp.this, MenuPrincipal.class);
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

        //---------------------------------------------------------------

        /*Valija = findViewById(R.id.txtValija);
        Valija.append(""+Valijas+"?");
        Guia = findViewById(R.id.txtGuia);*/
        //String Guias = Guia.getText().toString();

        //Enviar datos---------------------------------------------------
        Intent i = new Intent(PopUp.this, ValijaAct.class);
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
        startActivity(i);
        //----------------------------------------------------------------
    }

    public void onTouch() {
        ButtonSi = findViewById(R.id.btnSi);
        ButtonNo = findViewById(R.id.btnNo);

        ButtonSi.setOnTouchListener(new View.OnTouchListener() {
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

        ButtonNo.setOnTouchListener(new View.OnTouchListener() {
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

    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.btnSi:
                //startActivity(new Intent(this, DespacharFinal.class));
                CerrarManifiesto();
                break;
            case R.id.btnNo:
                PasarDatos();
                break;
        }
    }

    private void ContadorGuiasMftoGeneral(){

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
        final String count = b.getString("count");
        //---------------------------------------------------------------

        //Aqui enviar los datos-------------------------------------------------------------------------------------------
        //String resul = mTvResult.getText().toString();
        ManifiestoContador manifiestoContador = new ManifiestoContador(""+MftoNro,""+Suc,""+MftoAnio);
        Call<ManifiestoContador> call = jsonPlaceHolderApi.createPost(manifiestoContador);
        call.enqueue(new Callback<ManifiestoContador>() {
            @Override
            public void onResponse(Call<ManifiestoContador> call, Response<ManifiestoContador> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Ha ocurrido un error al contar las guias.", Toast.LENGTH_SHORT).show();
                    return;
                }
                ManifiestoContador postsResponse = response.body();

                final String total = postsResponse.total();
                String total2 = postsResponse.tot2();

                //Aqui enviar los datos-------------------------------------------------------------------------------------------
                //String resul = mTvResult.getText().toString();
                ContadorGuiasMfto contadorGuiasMfto = new ContadorGuiasMfto(""+MftoAnio,""+Suc,""+MftoNro);
                Call<ContadorGuiasMfto> callo = jsonPlaceHolderApi.createPost(contadorGuiasMfto);
                callo.enqueue(new Callback<ContadorGuiasMfto>() {
                    @Override
                    public void onResponse(Call<ContadorGuiasMfto> call, Response<ContadorGuiasMfto> response) {
                        if (!response.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Ha ocurrido un error al contar las guias.", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        ContadorGuiasMfto postsResponse = response.body();

                        String Total = postsResponse.total();
                        String total2 = postsResponse.tot2();

                        Toast.makeText(getApplicationContext(), "Contando guias..", Toast.LENGTH_SHORT).show();

                        Texto = findViewById(R.id.txtTexto);
                        //Manifiesto.setText(Mfto);
                        if(Total.equals(total)){
                            Texto.setText("El manifiesto está completo. ¿Está seguro que desea cerrarlo?");

                        }
                        else{
                            Texto.setText("El manifiesto actualmente tiene "+Total+" guias de "+total+", ¿está seguro que desea cerrarlo?");
                        }

                        Intent i = new Intent(PopUp.this, DespacharFinal.class);
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
                        c.putString("count", count);
                        i.putExtras(c);
                        //startActivity(i);
                        //----------------------------------------------------------------

                        return;
                    }

                    @Override
                    public void onFailure(Call<ContadorGuiasMfto> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "Fallo al ingresar los datos, compruebe su red.", Toast.LENGTH_SHORT).show();
                        //Titulo.setText(t.getMessage());
                        return;
                    }
                });

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

    /*private void ContadorGuiasMftoActual(){

        //Aqui enviar los datos-------------------------------------------------------------------------------------------
        //String resul = mTvResult.getText().toString();
        ContadorGuiasMfto contadorGuiasMfto = new ContadorGuiasMfto(""+MftoNro,""+Suc,""+MftoAnio);
        Call<ContadorGuiasMfto> call = jsonPlaceHolderApi.createPost(contadorGuiasMfto);
        call.enqueue(new Callback<ContadorGuiasMfto>() {
            @Override
            public void onResponse(Call<ContadorGuiasMfto> call, Response<ContadorGuiasMfto> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Ha ocurrido un error al contar las guias.", Toast.LENGTH_SHORT).show();
                    return;
                }
                ContadorGuiasMfto postsResponse = response.body();

                String Total = postsResponse.total();
                String total2 = postsResponse.tot2();


                Toast.makeText(getApplicationContext(), "Contando guias..", Toast.LENGTH_SHORT).show();

                Texto = findViewById(R.id.txtTexto);
                //Manifiesto.setText(Mfto);
                Texto.setText("El manifiesto actualmente tiene  guias de "+Total+", ¿está seguro que desea cerrarla?");
                return;
            }

            @Override
            public void onFailure(Call<ContadorGuiasMfto> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Fallo al ingresar los datos, compruebe su red.", Toast.LENGTH_SHORT).show();
                //Titulo.setText(t.getMessage());
                return;
            }
        });
    }*/

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
        ManifiestoContador manifiestoContador = new ManifiestoContador(""+MftoNro,""+Suc,""+MftoAnio);
        Call<ManifiestoContador> call = jsonPlaceHolderApi.createPost(manifiestoContador);
        call.enqueue(new Callback<ManifiestoContador>() {
            @Override
            public void onResponse(Call<ManifiestoContador> call, Response<ManifiestoContador> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Ha ocurrido un error al contar las guias.", Toast.LENGTH_SHORT).show();
                    return;
                }
                ManifiestoContador postsResponse = response.body();

                final String total = postsResponse.total();
                String total2 = postsResponse.tot2();

                //Aqui enviar los datos-------------------------------------------------------------------------------------------
                //String resul = mTvResult.getText().toString();
                ContadorGuiasMfto contadorGuiasMfto = new ContadorGuiasMfto(""+MftoAnio,""+Suc,""+MftoNro);
                Call<ContadorGuiasMfto> callo = jsonPlaceHolderApi.createPost(contadorGuiasMfto);
                callo.enqueue(new Callback<ContadorGuiasMfto>() {
                    @Override
                    public void onResponse(Call<ContadorGuiasMfto> call, Response<ContadorGuiasMfto> response) {
                        if (!response.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Ha ocurrido un error al contar las guias.", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        ContadorGuiasMfto postsResponse = response.body();

                        String Total = postsResponse.total();
                        String total2 = postsResponse.tot2();

                        String codi;

                        //Aqui enviar los datos-------------------------------------------------------------------------------------------
                        //String resul = mTvResult.getText().toString();
                        if(Total.equals(total)){
                             codi = "3619";
                        }
                        else{
                             codi = "3620";
                        }

                        ManifiestoCerrar manifiestoCerrar = new ManifiestoCerrar(""+MftoNro,""+Suc,""+MftoAnio,""+usuario,""+codi);
                        Call<ManifiestoCerrar> calle = jsonPlaceHolderApi.createPost(manifiestoCerrar);
                        calle.enqueue(new Callback<ManifiestoCerrar>() {
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

                                Intent i = new Intent(PopUp.this, DespacharFinal.class);
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

                        //Toast.makeText(getApplicationContext(), "Manifiesto cerrado con éxito", Toast.LENGTH_SHORT).show();

                        return;
                    }

                    @Override
                    public void onFailure(Call<ContadorGuiasMfto> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "Fallo al ingresar los datos, compruebe su red.", Toast.LENGTH_SHORT).show();
                        //Titulo.setText(t.getMessage());
                        return;
                    }
                });

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

}
