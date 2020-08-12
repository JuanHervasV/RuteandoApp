package com.notbytes.barcodereader;

import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.InputFilter;
import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.vision.barcode.Barcode;
import com.notbytes.barcode_reader.BarcodeReaderActivity;
import com.notbytes.barcode_reader.BarcodeReaderFragment;
import com.notbytes.barcodereader.Model.AdicionarGuias;
import com.notbytes.barcodereader.Model.AgregarIncidencia;
import com.notbytes.barcodereader.Model.GuiaSuc;
import com.notbytes.barcodereader.Model.ValidarGuia;
import com.notbytes.barcodereader.io.APIRetrofitInterface;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GuiaAct extends AppCompatActivity implements BarcodeReaderFragment.BarcodeReaderListener {
    private TextView Manifiesto;
    private TextView Valija;
    private EditText Guia;
    private EditText Guio;
    private APIRetrofitInterface jsonPlaceHolderApi;
    private TextView Mensaje;
    private static final int BARCODE_READER_ACTIVITY_REQUEST = 1208;
    private Button Agregar;
    private Button Cerrar;
    private FrameLayout Contenedor;
    private ListView listView;
    private TextView contarelementos;
    int totalelementoslist = 1;
    ArrayAdapter<String> adapter;
    ArrayList<String> arrayList;
    MediaPlayer mp2;
    MediaPlayer mp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guia);
        mp2 = MediaPlayer.create(this, R.raw.wrong);
        mp = MediaPlayer.create(this, R.raw.beeps);

        Guia = findViewById(R.id.txtGuia);
        Guia.setFilters(new InputFilter[]{new InputFilter.AllCaps()});

        //Contador elementos
        contarelementos = findViewById(R.id.contarelementos);

        //Lista------------------------------------------------------------------------------------------
        listView= findViewById(R.id.listview);
        String[]android_flavours={};
        arrayList= new ArrayList<>(Arrays.asList(android_flavours));
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,arrayList);
        listView.setAdapter(adapter);
        // Result value = barcode.receiveScan();
        //arrayList.add(barcode.rawValue);
        for (int i = 0; i < android_flavours.length; i++) {
            arrayList.add(android_flavours[i]);

            HashSet<String> hashSet = new HashSet<String>();
            hashSet.addAll(arrayList);
            arrayList.clear();
            arrayList.addAll(hashSet);
        }
        //listarpro = findViewById(R.id.lista);
        //String resul = mTvResult.getText().toString();-------------------------------------------------
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://200.37.50.53/ApiSGD/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        jsonPlaceHolderApi = retrofit.create(APIRetrofitInterface.class);

        RecuperarDatos();
        onTouch();
        //createPost();
    }

    public void onTouch() {
        Agregar = findViewById(R.id.btnAgregar);
        Cerrar = findViewById(R.id.btnCerrar);
        Agregar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_MOVE) {
                    v.setBackgroundResource(R.drawable.rounded_cornerneutral);
                }

                if (event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_CANCEL) {
                    v.setBackgroundResource(R.drawable.rounded_cornerceleste);
                }
                return false;
            }
        });

        Cerrar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_MOVE) {
                    v.setBackgroundResource(R.drawable.rounded_cornerneutral);
                }

                if (event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_CANCEL) {
                    v.setBackgroundResource(R.drawable.rounded_cornerceleste);
                }
                return false;
            }
        });
    }

    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnAgregar:
                AdicionarGuia();
                break;
            case R.id.btnBr:
                Guia = findViewById(R.id.txtGuia);
                Guia.setVisibility(View.INVISIBLE);
                Contenedor = findViewById(R.id.fm_container);
                Contenedor.setVisibility(View.VISIBLE);
                Agregar = findViewById(R.id.btnAgregar);
                Agregar.setVisibility(View.INVISIBLE);
                addBarcodeReaderFragment();
                break;
            case R.id.btnCerrar:
                PasarDatos();
                break;
            case R.id.btnTipear:
                Guia = findViewById(R.id.txtGuia);
                Guia.setVisibility(View.VISIBLE);
                Contenedor = findViewById(R.id.fm_container);
                Contenedor.setVisibility(View.INVISIBLE);
                Agregar = findViewById(R.id.btnAgregar);
                Agregar.setVisibility(View.VISIBLE);
                cerrarBarCode();
                break;
        }
    }

    private void addBarcodeReaderFragment() {
        BarcodeReaderFragment readerFragment = BarcodeReaderFragment.newInstance(true, false, View.VISIBLE);
        readerFragment.setListener(this);
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fm_container, readerFragment);
        fragmentTransaction.commitAllowingStateLoss();
    }

    private void cerrarBarCode() {
        BarcodeReaderFragment readerFragment = BarcodeReaderFragment.newInstance(false, false, View.GONE);
        readerFragment.setListener(null);
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fm_container, readerFragment);
        fragmentTransaction.commitAllowingStateLoss();
    }

    private void AdicionarGuia(){

        //Llamar datos ---------------------------------------------------------
        Bundle b = getIntent().getExtras();
        final String Valijas = b.getString("Valijas");
        String Mfto = b.getString("Mfto");
        final String MftoAnio = b.getString("MftoAnio");
        final String MftoNro = b.getString("MftoNro");
        final String Suc = b.getString("Suc");
        final String CiuDesp = b.getString("CiuDes");
        final String ValijaID = b.getString("ValijaID");
        final String usuario = b.getString("usuario");
        final String password = b.getString("password");
        final String CodigoUsuario = b.getString("codigousuario");
        //----------------------------------------------------------------------

        Guia = findViewById(R.id.txtGuia);
        Mensaje = findViewById(R.id.txtMensaje);
        //Manifiesto = findViewById(R.id.txtMfto);
        Valija = findViewById(R.id.txtValija);
        final String Codi = Guia.getText().toString();
        final GuiaSuc valijaValidar = new GuiaSuc(Codi);
        Call<GuiaSuc> cal = jsonPlaceHolderApi.createPost(valijaValidar);
        cal.enqueue(new Callback<GuiaSuc>() {
            @Override
            public void onResponse(Call<GuiaSuc> call, Response<GuiaSuc> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Guia no correct", Toast.LENGTH_SHORT).show();
                    Mensaje.setText("No existe la guia "+Codi+" ");
                    Mensaje.setBackgroundColor(Color.parseColor("#ff0000"));
                    Mensaje.setTextSize(16);
                    mp2.start();
                    return;
                }
                GuiaSuc postsResponse = response.body();

                final String Mft_Ano = postsResponse.mft_ano();
                final String Mft_Nro = postsResponse.mft_nro();
                final String Suc_Code = postsResponse.suc_code();
                final String Guia_det_nro_ref = postsResponse.guia_nro_ref();
                final String Guia_det_id = postsResponse.guia_det_id();
                final String Guia_det_con_ciu = postsResponse.guia_det_con_ciu();

                String CiuDespF = CiuDesp.toString();
                String CiuDespFi = CiuDespF.replace(" ","");
                String Guia_det_con_ciuF = Guia_det_con_ciu.toString();
                String Guia_det_con_ciuFi = Guia_det_con_ciuF.replace(" ","");

                if(Mft_Nro.length()<2) {

                    //Aqui se registra la incidencia ~
                    AgregarIncidencia agregarIncidencia = new AgregarIncidencia("" + Guia_det_nro_ref, "" + Valijas, "" + usuario, "3602", ""+Suc+" - "+MftoAnio+ " - "+ MftoNro);
                    Call<AgregarIncidencia> callm = jsonPlaceHolderApi.createPost(agregarIncidencia);
                    callm.enqueue(new Callback<AgregarIncidencia>() {
                        @Override
                        public void onResponse(Call<AgregarIncidencia> call, Response<AgregarIncidencia> response) {
                            if (!response.isSuccessful()) {
                                Toast.makeText(getApplicationContext(), "Incidencia no registrada", Toast.LENGTH_LONG).show();
                                return;
                            }
                            AgregarIncidencia postsResponse = response.body();

                            String estado = postsResponse.Estado();
                            String mensaje = postsResponse.Mensaje();
                            Toast.makeText(getApplicationContext(), "Incidencia registrada.", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(Call<AgregarIncidencia> call, Throwable t) {
                            Toast.makeText(getApplicationContext(), "Fallo al ingresar los datos, compruebe su red.", Toast.LENGTH_SHORT).show();
                            //Titulo.setText(t.getMessage());
                            return;
                        }
                    });

                    Mensaje.setText("La guia " + Codi + " no tiene manifiesto.");
                    Mensaje.setBackgroundColor(Color.parseColor("#FF7177"));
                    Mensaje.setTextSize(15);
                    mp2.start();
                    return;

                }

                    else {

                    if (MftoNro.equals(Mft_Nro)) {

                        if (CiuDespFi.equalsIgnoreCase(Guia_det_con_ciuFi)) {
                            ValidarGuia validarGuia = new ValidarGuia("" + Mft_Ano, "" + Mft_Nro, "" + Suc_Code, "" + Codi);
                            Call<ValidarGuia> cal = jsonPlaceHolderApi.createPost(validarGuia);
                            cal.enqueue(new Callback<ValidarGuia>() {
                                @Override
                                public void onResponse(Call<ValidarGuia> call, Response<ValidarGuia> response) {
                                    if (!response.isSuccessful()) {

                                        //Aqui se registra la incidencia ~
                                        AgregarIncidencia agregarIncidencia = new AgregarIncidencia("" + Guia_det_nro_ref, "" + Valijas, "" + usuario, "1", ""+Suc_Code+" - "+Mft_Ano+ " - "+ Mft_Nro);
                                        Call<AgregarIncidencia> callm = jsonPlaceHolderApi.createPost(agregarIncidencia);
                                        callm.enqueue(new Callback<AgregarIncidencia>() {
                                            @Override
                                            public void onResponse(Call<AgregarIncidencia> call, Response<AgregarIncidencia> response) {
                                                if (!response.isSuccessful()) {
                                                    Toast.makeText(getApplicationContext(), "Incidencia no registrada", Toast.LENGTH_LONG).show();
                                                    return;
                                                }
                                                AgregarIncidencia postsResponse = response.body();

                                                String estado = postsResponse.Estado();
                                                String mensaje = postsResponse.Mensaje();
                                                Toast.makeText(getApplicationContext(), "Incidencia registrada.", Toast.LENGTH_SHORT).show();
                                            }

                                            @Override
                                            public void onFailure(Call<AgregarIncidencia> call, Throwable t) {
                                                Toast.makeText(getApplicationContext(), "Fallo al ingresar los datos, compruebe su red.", Toast.LENGTH_SHORT).show();
                                                //Titulo.setText(t.getMessage());
                                                return;
                                            }
                                        });

                                        //Toast.makeText(getApplicationContext(), "Guia no correcta", Toast.LENGTH_SHORT).show();
                                        Mensaje.setText("La guia " + Codi + " no existe.");
                                        Mensaje.setBackgroundColor(Color.parseColor("#FF7177"));

                                        mp2.start();
                                        return;
                                    }
                                    ValidarGuia postsResponse = response.body();

                                    String Estado = postsResponse.estado();
                                    String Guia = postsResponse.Guias();
                                    String Codigo = postsResponse.codigo();

                                    int Est = Integer.parseInt(Estado);
                                    //Toast.makeText(getApplicationContext(), "Guia validada correctamente", Toast.LENGTH_SHORT).show();
                                    if (Est != 0) {

                                        if (Codigo.equals(Valijas)) {

                                            //Toast.makeText(getApplicationContext(), "La guia ya está asignada a la valija.", Toast.LENGTH_SHORT);
                                            Mensaje.setText("La guia ya está asignada en esta misma valija.");
                                            Mensaje.setBackgroundColor(Color.parseColor("#FF7177"));
                                            Mensaje.setTextSize(16);
                                            mp2.start();

                                        } else {
                                            //Toast.makeText(getApplicationContext(), "La guia ya está asignada a la valija.", Toast.LENGTH_SHORT);
                                            Mensaje.setText("La guia está asignada a la valija " + Codigo + ".");
                                            Mensaje.setBackgroundColor(Color.parseColor("#FF7177"));
                                            Mensaje.setTextSize(16);
                                            mp2.start();

                                        }

                                    } else {

                                        Guio = findViewById(R.id.txtGuia);
                                        Mensaje = findViewById(R.id.txtMensaje);
                                        //Manifiesto = findViewById(R.id.txtMfto);
                                        Valija = findViewById(R.id.txtValija);
                                        //Llamar datos ---------------------------------------------------------
                                        Bundle b = getIntent().getExtras();
                                        final String Valijas = b.getString("Valijas");
                                        String Mfto = b.getString("Mfto");
                                        String MftoAnio = b.getString("MftoAnio");
                                        String MftoNro = b.getString("MftoNro");
                                        String Suc = b.getString("Suc");
                                        final String ValijaID = b.getString("ValijaID");
                                        //----------------------------------------------------------------------

                                        final String Gui = Guio.getText().toString();
                                        Integer ValijID = Integer.parseInt(ValijaID);


                                        //Manifiesto.setText(Mfto+" "+MftoAnio+" "+MftoNro+" "+Suc+" "+Gui);
                                        //Valija.setText(Valijas);

                                        AdicionarGuias adicionarGuias = new AdicionarGuias(1234, ValijID, "" + Guia_det_id, "" + CodigoUsuario, "" + Gui);
                                        Call<AdicionarGuias> calli = jsonPlaceHolderApi.createPost(adicionarGuias);
                                        calli.enqueue(new Callback<AdicionarGuias>() {
                                            @Override
                                            public void onResponse(Call<AdicionarGuias> call, Response<AdicionarGuias> response) {
                                                if (!response.isSuccessful()) {
                                                    Mensaje.setText("La guia " + Gui + " no es correcta ");
                                                    Mensaje.setBackgroundColor(Color.parseColor("#FF7177"));
                                                    Toast.makeText(getApplicationContext(), "Guia no correcta", Toast.LENGTH_SHORT).show();
                                                    return;
                                                }
                                                AdicionarGuias postsResponse = response.body();

                                                String estado = postsResponse.Estado();
                                                String mensaje = postsResponse.Mensaje();

                                                if (estado.equals("false")) {

                                                    Mensaje.setText("La guia " + Gui + " no es correcta, pertenece a la valija " + estado);
                                                    Mensaje.setBackgroundColor(Color.parseColor("#FF7177"));
                                                    Toast.makeText(getApplicationContext(), "Guia no correcta", Toast.LENGTH_SHORT).show();
                                                    mp2.start();
                                                } else {

                                                    //Aqui se registra la incidencia ~
                                                    AgregarIncidencia agregarIncidencia = new AgregarIncidencia("" + Guia_det_nro_ref, "" + Valijas, "" + usuario, "3603", "" + Mft_Nro);
                                                    Call<AgregarIncidencia> callm = jsonPlaceHolderApi.createPost(agregarIncidencia);
                                                    callm.enqueue(new Callback<AgregarIncidencia>() {
                                                        @Override
                                                        public void onResponse(Call<AgregarIncidencia> call, Response<AgregarIncidencia> response) {
                                                            if (!response.isSuccessful()) {
                                                                Toast.makeText(getApplicationContext(), "Incidencia no registrada", Toast.LENGTH_LONG).show();
                                                                return;
                                                            }
                                                            AgregarIncidencia postsResponse = response.body();

                                                            String estado = postsResponse.Estado();
                                                            String mensaje = postsResponse.Mensaje();
                                                            Toast.makeText(getApplicationContext(), "Incidencia registrada.", Toast.LENGTH_SHORT).show();
                                                        }

                                                        @Override
                                                        public void onFailure(Call<AgregarIncidencia> call, Throwable t) {
                                                            Toast.makeText(getApplicationContext(), "Fallo al ingresar los datos, compruebe su red.", Toast.LENGTH_SHORT).show();
                                                            //Titulo.setText(t.getMessage());
                                                            return;
                                                        }
                                                    });

                                                    arrayList.add("" + Gui);
                                                    HashSet<String> hashSet = new HashSet<String>();
                                                    hashSet.addAll(arrayList);
                                                    arrayList.clear();
                                                    arrayList.addAll(hashSet);
                                                    adapter.notifyDataSetChanged();
                                                    Toast.makeText(getApplicationContext(), "Guia añadida correctamente", Toast.LENGTH_SHORT).show();
                                                    Mensaje.setTextSize(15);
                                                    Mensaje.setText("La guia " + Gui + " fue añadida correctamente ");
                                                    Mensaje.setBackgroundColor(Color.parseColor("#008f39"));
                                                    //Contar elementos del spinner ~
                                                    int count = listView.getAdapter().getCount();
                                                    int ctf= totalelementoslist+count;
                                                    contarelementos.setText(""+count);
                                                    //---
                                                    Guio.setText("");
                                                    mp.start();

                                                }

                                                //Mensaje.append("" + estado);
                                                return;
                                            }

                                            @Override
                                            public void onFailure(Call<AdicionarGuias> call, Throwable t) {
                                                Toast.makeText(getApplicationContext(), "Fallo al ingresar los datos, compruebe su red.", Toast.LENGTH_SHORT).show();
                                                return;
                                            }
                                        });
                                        //--
                                    }

                                    return;
                                }

                                @Override
                                public void onFailure(Call<ValidarGuia> call, Throwable t) {
                                    Toast.makeText(getApplicationContext(), "Fallo al ingresar los datos, compruebe su red.", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                            });
                        }
                        else {
                            //Aqui se registra la incidencia ~
                            AgregarIncidencia agregarIncidencia = new AgregarIncidencia("" + Guia_det_nro_ref, "" + Valijas, "" + usuario, "1", ""+Suc_Code+" - "+Mft_Ano+ " - "+ Mft_Nro);
                            Call<AgregarIncidencia> callm = jsonPlaceHolderApi.createPost(agregarIncidencia);
                            callm.enqueue(new Callback<AgregarIncidencia>() {
                                @Override
                                public void onResponse(Call<AgregarIncidencia> call, Response<AgregarIncidencia> response) {
                                    if (!response.isSuccessful()) {
                                        Toast.makeText(getApplicationContext(), "Incidencia no registrada", Toast.LENGTH_LONG).show();
                                        return;
                                    }
                                    AgregarIncidencia postsResponse = response.body();

                                    String estado = postsResponse.Estado();
                                    String mensaje = postsResponse.Mensaje();
                                    Toast.makeText(getApplicationContext(), "Incidencia registrada.", Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onFailure(Call<AgregarIncidencia> call, Throwable t) {
                                    Toast.makeText(getApplicationContext(), "Fallo al ingresar los datos, compruebe su red.", Toast.LENGTH_SHORT).show();
                                    //Titulo.setText(t.getMessage());
                                    return;
                                }
                            });
                            //Toast.makeText(getApplicationContext(), "Guia no correct", Toast.LENGTH_SHORT).show();
                            Mensaje.setText("La guia " + Codi + " pertenece al manifiesto " + Mft_Ano + "-" + Mft_Nro + ".");
                            Mensaje.setBackgroundColor(Color.parseColor("#FF7177"));
                            Mensaje.setTextSize(13);
                            mp2.start();
                            return;
                        }
                    }
                    else{
                        //Toast.makeText(getApplicationContext(), "La guia pertenece al manifiesto "+Mft_Nro, Toast.LENGTH_LONG).show();
                        //Aqui se registra la incidencia ~
                        AgregarIncidencia agregarIncidencia = new AgregarIncidencia("" + Guia_det_nro_ref, "" + Valijas, "" + usuario, "3602", ""+Suc_Code+" - "+Mft_Ano+ " - "+ Mft_Nro);
                        Call<AgregarIncidencia> callm = jsonPlaceHolderApi.createPost(agregarIncidencia);
                        callm.enqueue(new Callback<AgregarIncidencia>() {
                            @Override
                            public void onResponse(Call<AgregarIncidencia> call, Response<AgregarIncidencia> response) {
                                if (!response.isSuccessful()) {
                                    Toast.makeText(getApplicationContext(), "Incidencia no registrada", Toast.LENGTH_LONG).show();
                                    return;
                                }
                                AgregarIncidencia postsResponse = response.body();

                                String estado = postsResponse.Estado();
                                String mensaje = postsResponse.Mensaje();
                                Toast.makeText(getApplicationContext(), "Incidencia registrada.", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onFailure(Call<AgregarIncidencia> call, Throwable t) {
                                Toast.makeText(getApplicationContext(), "Fallo al ingresar los datos, compruebe su red.", Toast.LENGTH_SHORT).show();
                                //Titulo.setText(t.getMessage());
                                return;
                            }
                        });

                        Mensaje.setText("La guia "+Codi+" pertenece al manifiesto " + Mft_Ano + "-" + Mft_Nro + ".");
                        Mensaje.setBackgroundColor(Color.parseColor("#FF7177"));
                        Mensaje.setTextSize(13);
                        mp2.start();

                        return;
                    }

                }

                return;
            }

            @Override
            public void onFailure(Call<GuiaSuc> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Fallo al ingresar los datos, compruebe su red.", Toast.LENGTH_SHORT).show();
                return;
            }
        });
    }

    public void onScanned(final Barcode barcode) {

        //Llamar datos ---------------------------------------------------------
        Bundle b = getIntent().getExtras();
        final String Valijas = b.getString("Valijas");
        String Mfto = b.getString("Mfto");
        final String MftoAnio = b.getString("MftoAnio");
        final String MftoNro = b.getString("MftoNro");
        final String Suc = b.getString("Suc");
        final String CiuDesp = b.getString("CiuDes");
        final String ValijaID = b.getString("ValijaID");
        final String usuario = b.getString("usuario");
        final String password = b.getString("password");
        final String CodigoUsuario = b.getString("codigousuario");
        //----------------------------------------------------------------------

        Guia = findViewById(R.id.txtGuia);
        Mensaje = findViewById(R.id.txtMensaje);
        //Manifiesto = findViewById(R.id.txtMfto);
        Valija = findViewById(R.id.txtValija);
        final String Bardcodigo = barcode.rawValue;
        final GuiaSuc valijaValidar = new GuiaSuc(Bardcodigo);
        Call<GuiaSuc> cal = jsonPlaceHolderApi.createPost(valijaValidar);
        cal.enqueue(new Callback<GuiaSuc>() {
            @Override
            public void onResponse(Call<GuiaSuc> call, Response<GuiaSuc> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Guia no correcta", Toast.LENGTH_SHORT).show();
                    Mensaje.setText("No existe la guia "+Bardcodigo+" ");
                    Mensaje.setBackgroundColor(Color.parseColor("#ff0000"));
                    Mensaje.setTextSize(16);
                    mp2.start();
                    return;
                }
                GuiaSuc postsResponse = response.body();

                final String Mft_Ano = postsResponse.mft_ano();
                final String Mft_Nro = postsResponse.mft_nro();
                final String Suc_Code = postsResponse.suc_code();
                final String Guia_det_nro_ref = postsResponse.guia_nro_ref();
                final String Guia_det_id = postsResponse.guia_det_id();
                final String Guia_det_con_ciu = postsResponse.guia_det_con_ciu();

                String CiuDespF = CiuDesp.toString();
                String CiuDespFi = CiuDespF.replace(" ","");
                String Guia_det_con_ciuF = Guia_det_con_ciu.toString();
                String Guia_det_con_ciuFi = Guia_det_con_ciuF.replace(" ","");

                if(Mft_Nro.length()<2) {

                    //Aqui se registra la incidencia ~
                    AgregarIncidencia agregarIncidencia = new AgregarIncidencia("" + Guia_det_nro_ref, "" + Valijas, "" + usuario, "3602", ""+Suc+" - "+MftoAnio+ " - "+ MftoNro);
                    Call<AgregarIncidencia> callm = jsonPlaceHolderApi.createPost(agregarIncidencia);
                    callm.enqueue(new Callback<AgregarIncidencia>() {
                        @Override
                        public void onResponse(Call<AgregarIncidencia> call, Response<AgregarIncidencia> response) {
                            if (!response.isSuccessful()) {
                                Toast.makeText(getApplicationContext(), "Incidencia no registrada", Toast.LENGTH_LONG).show();
                                return;
                            }
                            AgregarIncidencia postsResponse = response.body();

                            String estado = postsResponse.Estado();
                            String mensaje = postsResponse.Mensaje();
                            Toast.makeText(getApplicationContext(), "Incidencia registrada.", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(Call<AgregarIncidencia> call, Throwable t) {
                            Toast.makeText(getApplicationContext(), "Fallo al ingresar los datos, compruebe su red.", Toast.LENGTH_SHORT).show();
                            //Titulo.setText(t.getMessage());
                            return;
                        }
                    });

                    Mensaje.setText("La guia " + Bardcodigo + " no tiene manifiesto.");
                    Mensaje.setBackgroundColor(Color.parseColor("#FF7177"));
                    Mensaje.setTextSize(15);
                    mp2.start();
                    return;

                }

                else {

                    if (MftoNro.equals(Mft_Nro)) {

                        if (CiuDespFi.equalsIgnoreCase(Guia_det_con_ciuFi)){
                            ValidarGuia validarGuia = new ValidarGuia(""+Mft_Ano, ""+Mft_Nro, ""+Suc_Code, ""+Bardcodigo);
                            Call<ValidarGuia> cal = jsonPlaceHolderApi.createPost(validarGuia);
                            cal.enqueue(new Callback<ValidarGuia>() {
                                @Override
                                public void onResponse(Call<ValidarGuia> call, Response<ValidarGuia> response) {
                                    if (!response.isSuccessful()) {

                                        //Aqui se registra la incidencia ~
                                        AgregarIncidencia agregarIncidencia = new AgregarIncidencia(""+Guia_det_nro_ref,""+Valijas,""+usuario,"1",""+Suc_Code+" - "+Mft_Ano+ " - "+ Mft_Nro);
                                        Call<AgregarIncidencia> callm = jsonPlaceHolderApi.createPost(agregarIncidencia);
                                        callm.enqueue(new Callback<AgregarIncidencia>() {
                                            @Override
                                            public void onResponse(Call<AgregarIncidencia> call, Response<AgregarIncidencia> response) {
                                                if (!response.isSuccessful()) {
                                                    Toast.makeText(getApplicationContext(), "Incidencia no registrada", Toast.LENGTH_LONG).show();
                                                    return;
                                                }
                                                AgregarIncidencia postsResponse = response.body();

                                                String estado = postsResponse.Estado();
                                                String mensaje = postsResponse.Mensaje();
                                                Toast.makeText(getApplicationContext(), "Incidencia registrada.", Toast.LENGTH_SHORT).show();
                                            }

                                            @Override
                                            public void onFailure(Call<AgregarIncidencia> call, Throwable t) {
                                                Toast.makeText(getApplicationContext(),"Fallo al ingresar los datos, compruebe su red.",Toast.LENGTH_SHORT).show();
                                                //Titulo.setText(t.getMessage());
                                                return;
                                            }
                                        });

                                        //Toast.makeText(getApplicationContext(), "Guia no correcta", Toast.LENGTH_SHORT).show();
                                        Mensaje.setText("La guia "+Bardcodigo+" no existe.");
                                        Mensaje.setBackgroundColor(Color.parseColor("#FF7177"));

                                        mp2.start();
                                        return;
                                    }
                                    ValidarGuia postsResponse = response.body();

                                    String Estado = postsResponse.estado();
                                    String Guia = postsResponse.Guias();
                                    String Codigo = postsResponse.codigo();

                                    int Est = Integer.parseInt(Estado);
                                    //Toast.makeText(getApplicationContext(), "Guia validada correctamente", Toast.LENGTH_SHORT).show();
                                    if (Est != 0){

                                        if(Codigo.equals(Valijas)){

                                            //Toast.makeText(getApplicationContext(), "La guia ya está asignada a la valija.", Toast.LENGTH_SHORT);
                                            Mensaje.setText("La guia ya está asignada en esta misma valija.");
                                            Mensaje.setBackgroundColor(Color.parseColor("#FF7177"));
                                            Mensaje.setTextSize(16);
                                            mp2.start();

                                        }
                                        else{
                                            //Toast.makeText(getApplicationContext(), "La guia ya está asignada a la valija.", Toast.LENGTH_SHORT);
                                            Mensaje.setText("La guia está asignada a la valija "+Codigo+".");
                                            Mensaje.setBackgroundColor(Color.parseColor("#FF7177"));
                                            Mensaje.setTextSize(16);
                                            mp2.start();

                                        }

                                    }
                                    else{

                                        Guio = findViewById(R.id.txtGuia);
                                        Mensaje = findViewById(R.id.txtMensaje);
                                        //Manifiesto = findViewById(R.id.txtMfto);
                                        Valija = findViewById(R.id.txtValija);
                                        //Llamar datos ---------------------------------------------------------
                                        Bundle b = getIntent().getExtras();
                                        final String Valijas = b.getString("Valijas");
                                        String Mfto = b.getString("Mfto");
                                        final String MftoAnio = b.getString("MftoAnio");
                                        final String MftoNro = b.getString("MftoNro");
                                        final String Suc = b.getString("Suc");
                                        final String ValijaID = b.getString("ValijaID");
                                        //----------------------------------------------------------------------

                                        Integer ValijID = Integer.parseInt(ValijaID);


                                        //Manifiesto.setText(Mfto+" "+MftoAnio+" "+MftoNro+" "+Suc+" "+Gui);
                                        //Valija.setText(Valijas);

                                        AdicionarGuias adicionarGuias = new AdicionarGuias(1234,ValijID,""+Guia_det_id,""+CodigoUsuario,""+Bardcodigo);
                                        Call<AdicionarGuias> calli = jsonPlaceHolderApi.createPost(adicionarGuias);
                                        calli.enqueue(new Callback<AdicionarGuias>() {
                                            @Override
                                            public void onResponse(Call<AdicionarGuias> call, Response<AdicionarGuias> response) {
                                                if(!response.isSuccessful()){
                                                    Mensaje.setText("La guia "+Bardcodigo+" no es correcta ");
                                                    Mensaje.setBackgroundColor(Color.parseColor("#FF7177"));
                                                    Toast.makeText(getApplicationContext(),"Guia no correcta",Toast.LENGTH_SHORT).show();
                                                    return;
                                                }
                                                AdicionarGuias postsResponse = response.body();

                                                String estado = postsResponse.Estado();
                                                String mensaje = postsResponse.Mensaje();

                                                if (estado.equals("false")){

                                                    Mensaje.setText("La guia "+Bardcodigo+" no es correcta, pertenece a la valija "+estado);
                                                    Mensaje.setBackgroundColor(Color.parseColor("#FF7177"));
                                                    Mensaje .setTextSize(13);
                                                    Toast.makeText(getApplicationContext(),"Guia no correcta",Toast.LENGTH_SHORT).show();
                                                    mp2.start();
                                                }
                                                else{

                                                    //Aqui se registra la incidencia ~
                                                    AgregarIncidencia agregarIncidencia = new AgregarIncidencia(""+Guia_det_nro_ref,""+Valijas,""+usuario,"3603",""+Suc+" - "+MftoAnio+ " - "+ MftoNro);
                                                    Call<AgregarIncidencia> callm = jsonPlaceHolderApi.createPost(agregarIncidencia);
                                                    callm.enqueue(new Callback<AgregarIncidencia>() {
                                                        @Override
                                                        public void onResponse(Call<AgregarIncidencia> call, Response<AgregarIncidencia> response) {
                                                            if (!response.isSuccessful()) {
                                                                Toast.makeText(getApplicationContext(), "Incidencia no registrada", Toast.LENGTH_LONG).show();
                                                                return;
                                                            }
                                                            AgregarIncidencia postsResponse = response.body();

                                                            String estado = postsResponse.Estado();
                                                            String mensaje = postsResponse.Mensaje();
                                                            Toast.makeText(getApplicationContext(), "Incidencia registrada.", Toast.LENGTH_SHORT).show();
                                                        }

                                                        @Override
                                                        public void onFailure(Call<AgregarIncidencia> call, Throwable t) {
                                                            Toast.makeText(getApplicationContext(),"Fallo al ingresar los datos, compruebe su red.",Toast.LENGTH_SHORT).show();
                                                            //Titulo.setText(t.getMessage());
                                                            return;
                                                        }
                                                    });

                                                    arrayList.add(""+Bardcodigo);
                                                    HashSet<String> hashSet = new HashSet<String>();
                                                    hashSet.addAll(arrayList);
                                                    arrayList.clear();
                                                    arrayList.addAll(hashSet);
                                                    adapter.notifyDataSetChanged();
                                                    Toast.makeText(getApplicationContext(),"Guia añadida correctamente",Toast.LENGTH_SHORT).show();
                                                    Mensaje.setTextSize(15);
                                                    Mensaje.setText("La guia "+Bardcodigo+" fue añadida correctamente ");
                                                    Mensaje.setBackgroundColor(Color.parseColor("#008f39"));
                                                    //Contar elementos del spinner ~
                                                    int count = listView.getAdapter().getCount();
                                                    int ctf= totalelementoslist+count;
                                                    contarelementos.setText(""+count);
                                                    //---
                                                    Guio.setText("");
                                                    mp.start();

                                                }

                                                //Mensaje.append("" + estado);
                                                return;
                                            }

                                            @Override
                                            public void onFailure(Call<AdicionarGuias> call, Throwable t) {
                                                Toast.makeText(getApplicationContext(), "Fallo al ingresar los datos, compruebe su red.", Toast.LENGTH_SHORT).show();
                                                return;
                                            }
                                        });
                                        //--
                                    }

                                    return;
                                }

                                @Override
                                public void onFailure(Call<ValidarGuia> call, Throwable t) {
                                    Toast.makeText(getApplicationContext(), "Fallo al ingresar los datos, compruebe su red.", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                            });
                        }
                        else{
                            //Aqui se registra la incidencia ~
                            AgregarIncidencia agregarIncidencia = new AgregarIncidencia(""+Guia_det_nro_ref,""+Valijas,""+usuario,"1",""+Suc+" - "+MftoAnio+ " - "+ MftoNro);
                            Call<AgregarIncidencia> callm = jsonPlaceHolderApi.createPost(agregarIncidencia);
                            callm.enqueue(new Callback<AgregarIncidencia>() {
                                @Override
                                public void onResponse(Call<AgregarIncidencia> call, Response<AgregarIncidencia> response) {
                                    if (!response.isSuccessful()) {
                                        Toast.makeText(getApplicationContext(), "Incidencia no registrada", Toast.LENGTH_LONG).show();
                                        return;
                                    }
                                    AgregarIncidencia postsResponse = response.body();

                                    String estado = postsResponse.Estado();
                                    String mensaje = postsResponse.Mensaje();
                                    Toast.makeText(getApplicationContext(), "Incidencia registrada.", Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onFailure(Call<AgregarIncidencia> call, Throwable t) {
                                    Toast.makeText(getApplicationContext(),"Fallo al ingresar los datos, compruebe su red.",Toast.LENGTH_SHORT).show();
                                    //Titulo.setText(t.getMessage());
                                    return;
                                }
                            });
                            //Toast.makeText(getApplicationContext(), "Guia no correcta", Toast.LENGTH_SHORT).show();
                            Mensaje.setText("La guia " + Bardcodigo + " pertenece al manifiesto " + Mft_Ano + "-" + Mft_Nro + ".");
                            Mensaje.setBackgroundColor(Color.parseColor("#FF7177"));
                            Mensaje.setTextSize(13);
                            mp2.start();
                            return;
                        }
                    }
                    else{

                        //Aqui se registra la incidencia ~
                        AgregarIncidencia agregarIncidencia = new AgregarIncidencia("" + Guia_det_nro_ref, "" + Valijas, "" + usuario, "3602", ""+Suc_Code+" - "+Mft_Ano+ " - "+ Mft_Nro);
                        Call<AgregarIncidencia> callm = jsonPlaceHolderApi.createPost(agregarIncidencia);
                        callm.enqueue(new Callback<AgregarIncidencia>() {
                            @Override
                            public void onResponse(Call<AgregarIncidencia> call, Response<AgregarIncidencia> response) {
                                if (!response.isSuccessful()) {
                                    Toast.makeText(getApplicationContext(), "Incidencia no registrada", Toast.LENGTH_LONG).show();
                                    return;
                                }
                                AgregarIncidencia postsResponse = response.body();

                                String estado = postsResponse.Estado();
                                String mensaje = postsResponse.Mensaje();
                                Toast.makeText(getApplicationContext(), "Incidencia registrada.", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onFailure(Call<AgregarIncidencia> call, Throwable t) {
                                Toast.makeText(getApplicationContext(), "Fallo al ingresar los datos, compruebe su red.", Toast.LENGTH_SHORT).show();
                                //Titulo.setText(t.getMessage());
                                return;
                            }
                        });
                        //Toast.makeText(getApplicationContext(), "La guia pertenece al manifiesto "+Mft_Nro, Toast.LENGTH_LONG).show();
                        Mensaje.setText("La guia "+Bardcodigo+" pertenece al manifiesto " + Mft_Ano + "-" + Mft_Nro + ".");
                        Mensaje.setBackgroundColor(Color.parseColor("#FF7177"));
                        Mensaje.setTextSize(13);
                        mp2.start();

                        return;
                    }

                }

                return;
            }

            @Override
            public void onFailure(Call<GuiaSuc> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Fallo al ingresar los datos, compruebe su red.", Toast.LENGTH_SHORT).show();
                return;
            }
        });
    }


    public void onScannedMultiple(List<Barcode> barcodes) {

    }


    public void onBitmapScanned(SparseArray<Barcode> sparseArray) {

    }


    public void onScanError(String errorMessage) {

    }

    public void onCameraPermissionDenied() {
        Toast.makeText(this, "Permiso de cámara denegado", Toast.LENGTH_LONG).show();
    }

    private void launchBarCodeActivity() {
        Intent launchIntent = BarcodeReaderActivity.getLaunchIntent(this, true, false);
        startActivityForResult(launchIntent, BARCODE_READER_ACTIVITY_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Guia = findViewById(R.id.txtGuia);

        if (requestCode == BARCODE_READER_ACTIVITY_REQUEST && data != null) {
            Barcode barcode = data.getParcelableExtra(BarcodeReaderActivity.KEY_CAPTURED_BARCODE);
            Toast.makeText(this, barcode.rawValue, Toast.LENGTH_SHORT).show();
            //mTvResultHeader.setText("Resultado");

            Guia.setText(barcode.rawValue);
        }
    }

    public void RecuperarDatos(){
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
        String ValijaID = b.getString("ValijaID");
        //-----------------------------------------------------------------------
        Manifiesto.setText(Suc+" "+MftoAnio+"-"+MftoNro);
        Valija.setText(Valijas);
    }

    public void PasarDatos(){
        Manifiesto = findViewById(R.id.txtMfto);
        Valija = findViewById(R.id.txtValija);
        //Llamar datos ----------------------------------------------------------
        Bundle b = getIntent().getExtras();
        String ValijaID = b.getString("ValijaID");
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
        String CodigoUsuario = b.getString("codigousuario");
        //----------------------------------------------------------------------
        String count = contarelementos.getText().toString();
        //----------------------------------------------------------------------
        Manifiesto.setText(Suc+" "+MftoAnio+"-"+MftoNro);
        Valija.setText(Valijas);
        //Enviar datos----------------------------------------------------------
        Intent i = new Intent(GuiaAct.this, CerrarValijaAct.class);
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
        c.putString("ValijaID", ValijaID);
        c.putString("usuario", usuario);
        c.putString("password", password);
        c.putString("codigousuario", CodigoUsuario);
        c.putString("count", count);
        i.putExtras(c);
        startActivity(i);
        //----------------------------------------------------------------------
    }

    private void SucGuia(){
        Guia = findViewById(R.id.txtGuia);
        String Gui = Guia.getText().toString();
        GuiaSuc valijaValidar = new GuiaSuc(""+Gui);
        Call<GuiaSuc> call = jsonPlaceHolderApi.createPost(valijaValidar);
        call.enqueue(new Callback<GuiaSuc>() {
            @Override
            public void onResponse(Call<GuiaSuc> call, Response<GuiaSuc> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(getApplicationContext(),"Guia no correcta",Toast.LENGTH_SHORT).show();
                    return;
                }
                GuiaSuc postsResponse = response.body();

                String Mft_Ano = postsResponse.mft_ano();
                String Mft_Nro = postsResponse.mft_nro();
                String Suc_Code = postsResponse.suc_code();

                Toast.makeText(getApplicationContext(),"Guia agregada correctamente "+Suc_Code,Toast.LENGTH_SHORT).show();
                return;
            }
            @Override
            public void onFailure(Call<GuiaSuc> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Fallo al ingresar los datos, compruebe su red.",Toast.LENGTH_SHORT).show();
                return;
            }
        });
    }

}
