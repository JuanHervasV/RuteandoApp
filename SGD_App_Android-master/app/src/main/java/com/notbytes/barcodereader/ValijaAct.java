package com.notbytes.barcodereader;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.vision.barcode.Barcode;
import com.notbytes.barcode_reader.BarcodeReaderActivity;
import com.notbytes.barcodereader.Model.ContadorGuiasMfto;
import com.notbytes.barcodereader.Model.ManifiestoCerrar;
import com.notbytes.barcodereader.Model.ManifiestoContador;
import com.notbytes.barcodereader.Model.ValijaAdicionar;
import com.notbytes.barcodereader.Model.ValijaGDNValidar;
import com.notbytes.barcodereader.Model.ValijaValidar;
import com.notbytes.barcodereader.io.APIRetrofitInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ValijaAct extends AppCompatActivity {
    private APIRetrofitInterface jsonPlaceHolderApi;
    private TextView Manifiesto;
    private TextView Destino;
    private EditText Valija;
    private static final int BARCODE_READER_ACTIVITY_REQUEST = 1208;
    private Button Asignar;
    private Button Cerrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_valija);

        Valija = findViewById(R.id.txtValija);
        Valija.setFilters(new InputFilter[]{new InputFilter.AllCaps()});

        //Retrofit------------------------------------------------------

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
        Asignar = findViewById(R.id.btnAsignar);
        Cerrar = findViewById(R.id.btnCerrar);

        Asignar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_MOVE) {
                    v.setBackgroundResource(R.drawable.rounded_cornerneutral);
                    //v.setBackgroundColor(Color.parseColor("#9C9C9C"));
                }

                if (event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_CANCEL) {
                    v.setBackgroundResource(R.drawable.rounded_cornerverde);
                    //v.setBackgroundColor(Color.parseColor("#FF7177"));
                }
                return false;
            }
        });

        Cerrar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_MOVE) {
                    v.setBackgroundResource(R.drawable.rounded_cornerneutral);
                    //v.setBackgroundColor(Color.parseColor("#9C9C9C"));
                }

                if (event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_CANCEL) {
                    v.setBackgroundResource(R.drawable.rounded_cornerverde);
                    //v.setBackgroundColor(Color.parseColor("#FF7177"));
                }
                return false;
            }
        });
    }

    private void RecuperarDatos(){

        Manifiesto = findViewById(R.id.txtMfto);
        Destino = findViewById(R.id.txtDestino);

        //Llamar datos --------------------------------
        Bundle b = getIntent().getExtras();
        String Mfto = b.getString("Mfto");
        String MftoAnio = b.getString("MftoAnio");
        String MftoNro = b.getString("MftoNro");
        String Suc = b.getString("Suc");
        String PaisDes = b.getString("PaisDes");
        String CiuDes = b.getString("CiuDes");
        String Estado = b.getString("Estado");
        String usuario = b.getString("usuario");
        String password = b.getString("password");
        //----------------------------------------------

        Manifiesto.setText(Suc+" "+MftoAnio+"-"+MftoNro);
        Destino.setText(""+CiuDes);

        //

    }

    public void onClick(View v){
        switch (v.getId()) {
            case R.id.btnAsignar:
                ValijaValidar();
                break;
            case R.id.btnBr:
                FragmentManager supportFragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
                Fragment fragmentById = supportFragmentManager.findFragmentById(R.id.fm_container);
                if (fragmentById != null) {
                    fragmentTransaction.remove(fragmentById);
                }
                fragmentTransaction.commitAllowingStateLoss();
                launchBarCodeActivity();
                break;
            case R.id.btnCerrar:
                PasarDatos();
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
        Intent i = new Intent(ValijaAct.this, PopUp.class);
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

    private void launchBarCodeActivity() {
        Intent launchIntent = BarcodeReaderActivity.getLaunchIntent(this, true, false);
        startActivityForResult(launchIntent, BARCODE_READER_ACTIVITY_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Valija = findViewById(R.id.txtValija);

        if (requestCode == BARCODE_READER_ACTIVITY_REQUEST && data != null) {
            Barcode barcode = data.getParcelableExtra(BarcodeReaderActivity.KEY_CAPTURED_BARCODE);
            Toast.makeText(this, barcode.rawValue, Toast.LENGTH_SHORT).show();

            //Agrega datos al textview
            //mTvResult.setText("Último valor scaneado: "+barcode.rawValue);
            //Agregar datos a la list

            //arrayList.add(barcode.rawValue);
            //adapter.notifyDataSetChanged();

            //HashSet<String> hashSet = new HashSet<String>();
            //hashSet.addAll(arrayList);
            //arrayList.clear();
            //arrayList.addAll(hashSet);
            //Contar elementos del spinner ~
            //int count = listView.getAdapter().getCount();
            //int ctf= totalelementoslist+count;
            //contarelementos.setText(""+count);
            //---

            //Sonido beep
            final MediaPlayer mp = MediaPlayer.create(this, R.raw.beeps);
            mp.start();
            //--
            //mTvResultHeader.setText("Resultado");

            Valija.setText(barcode.rawValue);
        }
    }

    public void Añadirvalija(){

        Valija = findViewById(R.id.txtValija);
        final String Valijas = Valija.getText().toString();
        //Llamar datos ---------------------------------------
        Bundle b = getIntent().getExtras();
        final String Mfto = b.getString("Mfto");
        final String MftoAnio = b.getString("MftoAnio");
        final String MftoNro = b.getString("MftoNro");
        final String Suc = b.getString("Suc");
        final String PaisDes = b.getString("PaisDes");
        final String CiuDes = b.getString("CiuDes");
        final String Estado = b.getString("Estado");
        final String usuario = b.getString("usuario");
        //----------------------------------------------------

            ValijaAdicionar valijaAdicionar = new ValijaAdicionar(""+Valijas,""+MftoAnio,""+MftoNro,""+Suc,""+CiuDes,""+usuario);

            Call<ValijaAdicionar> call = jsonPlaceHolderApi.createPost(valijaAdicionar);
            call.enqueue(new Callback<ValijaAdicionar>() {
                @Override
                public void onResponse(Call<ValijaAdicionar> call, Response<ValijaAdicionar> response) {
                    if(!response.isSuccessful()){
                        //TestApi.setText("Codigo:" + response.code());
                        return;
                    }
                    ValijaAdicionar postsResponse = response.body();
                    String content = "";
                    content += "Estado:" + postsResponse.Estado() + "\n";
                    //content += "Mensaje:" + postsResponse.Mensaje() + "\n";
                    //TestApi.append(content);
                    if(postsResponse.Estado() == "true") {
                        Toast.makeText(ValijaAct.this, "Valija asignada correctamente", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(ValijaAct.this, GuiaAct.class);
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
                        i.putExtras(c);
                        //ValijaValidar();
                        startActivity(i);
                    }
                    else{
                        Toast.makeText(ValijaAct.this, "Fallo al asignar valjia", Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onFailure(Call<ValijaAdicionar> call, Throwable t) {
                    Toast.makeText(ValijaAct.this, "Fallo al asignar valjia", Toast.LENGTH_SHORT).show();
                }
            });

    }

    public void ValijaValidarGDN(){

        //------------------------------------------------------------------------------------------------------------------------
        Valija = findViewById(R.id.txtValija);
        final String Valijas = Valija.getText().toString();
        //Llamar datos ---------------------------------------
        Bundle b = getIntent().getExtras();
        final String Mfto = b.getString("Mfto");
        final String MftoAnio = b.getString("MftoAnio");
        final String MftoNro = b.getString("MftoNro");
        final String Suc = b.getString("Suc");
        final String PaisDes = b.getString("PaisDes");
        final String CiuDes = b.getString("CiuDes");
        final String Estado = b.getString("Estado");
        //----------------------------------------------------
        ValijaGDNValidar valijaGDNValidar = new ValijaGDNValidar(""+Valijas);

        Call<ValijaGDNValidar> call = jsonPlaceHolderApi.createPost(valijaGDNValidar);
        call.enqueue(new Callback<ValijaGDNValidar>() {
            @Override
            public void onResponse(Call<ValijaGDNValidar> call, Response<ValijaGDNValidar> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(ValijaAct.this, "No existe la valija", Toast.LENGTH_SHORT).show();
                    return;
                }
                ValijaGDNValidar postsResponse = response.body();
                //String content = "";
                //content += "Estado:" + postsResponse.Estado() + "\n";
                //content += "Mensaje:" + postsResponse.Mensaje() + "\n";
                //TestApi.append(content);
                String existe = postsResponse.Existe();
                String id = postsResponse.ID();
                String codigo = postsResponse.Codigo();
                String usuario = postsResponse.Usuario();

                Toast.makeText(ValijaAct.this, "Valija asignada correctamente", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(ValijaAct.this, GuiaAct.class);
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
                    c.putString("id", id);
                    i.putExtras(c);
                    startActivity(i);
            }
            @Override
            public void onFailure(Call<ValijaGDNValidar> call, Throwable t) {
                Toast.makeText(ValijaAct.this, "Fallo al asignar valjia", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void ValijaValidar(){

        Valija = findViewById(R.id.txtValija);
        Valija.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
        final String Valijas = Valija.getText().toString();

        //Llamar datos ---------------------------------------------------------------------------------------------------
        Bundle b = getIntent().getExtras();
        final String Mfto = b.getString("Mfto");
        final String MftoAnio = b.getString("MftoAnio");
        final String MftoNro = b.getString("MftoNro");
        final String Suc = b.getString("Suc");
        final String PaisDes = b.getString("PaisDes");
        final String CiuDes = b.getString("CiuDes");
        final String Estado = b.getString("Estado");
        final String usuario = b.getString("usuario");
        final String password = b.getString("password");
        final String CodigoUsuario = b.getString("codigousuario");
        //----------------------------------------------------------------------------------------------------------------

        //Aqui enviar los datos-------------------------------------------------------------------------------------------
        //String resul = mTvResult.getText().toString();
        ValijaValidar valijaValidar = new ValijaValidar(Valijas);
        Call<ValijaValidar> call = jsonPlaceHolderApi.createPost(valijaValidar);
        call.enqueue(new Callback<ValijaValidar>() {
            @Override
            public void onResponse(Call<ValijaValidar> call, Response<ValijaValidar> response) {
                if(!response.isSuccessful()){

                    //------------------------------------------------------------------------------------------------------------------------
                    //Valija = findViewById(R.id.txtValija);
                    //final String Valijas = Valija.getText().toString();
                    //Llamar datos ---------------------------------------
                    Bundle b = getIntent().getExtras();
                    final String Mfto = b.getString("Mfto");
                    final String MftoAnio = b.getString("MftoAnio");
                    final String MftoNro = b.getString("MftoNro");
                    final String Suc = b.getString("Suc");
                    final String PaisDes = b.getString("PaisDes");
                    final String CiuDes = b.getString("CiuDes");
                    final String Estado = b.getString("Estado");
                    //----------------------------------------------------

                    ValijaGDNValidar valijaGDNValidar = new ValijaGDNValidar(Valijas);

                    Call<ValijaGDNValidar> callu = jsonPlaceHolderApi.createPost(valijaGDNValidar);
                    callu.enqueue(new Callback<ValijaGDNValidar>() {
                        @Override
                        public void onResponse(Call<ValijaGDNValidar> call, Response<ValijaGDNValidar> response) {
                            if(!response.isSuccessful()){
                                Toast.makeText(ValijaAct.this, "No existe la valija", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            Valija = findViewById(R.id.txtValija);
                            final String Valijas = Valija.getText().toString();
                            //Llamar datos ---------------------------------------
                            Bundle b = getIntent().getExtras();
                            final String Mfto = b.getString("Mfto");
                            final String MftoAnio = b.getString("MftoAnio");
                            final String MftoNro = b.getString("MftoNro");
                            final String Suc = b.getString("Suc");
                            final String PaisDes = b.getString("PaisDes");
                            final String CiuDes = b.getString("CiuDes");
                            final String Estado = b.getString("Estado");

                            //----------------------------------------------------

                            ValijaAdicionar valijaAdicionar = new ValijaAdicionar(""+Valijas,""+MftoAnio,""+MftoNro,""+Suc,""+CiuDes,""+usuario);

                            Call<ValijaAdicionar> callo = jsonPlaceHolderApi.createPost(valijaAdicionar);
                            callo.enqueue(new Callback<ValijaAdicionar>() {
                                @Override
                                public void onResponse(Call<ValijaAdicionar> call, Response<ValijaAdicionar> response) {
                                    if(!response.isSuccessful()){
                                        Toast.makeText(ValijaAct.this, "La valija no se pudo asignar", Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                    ValijaAdicionar postsResponse = response.body();
                                    String content = "";
                                    content += "Estado:" + postsResponse.Estado() + "\n";

                                    if(postsResponse.Estado() == "true") {

                                        ValijaValidar valijaValidar = new ValijaValidar(""+Valijas);
                                        Call<ValijaValidar> calll = jsonPlaceHolderApi.createPost(valijaValidar);
                                        calll.enqueue(new Callback<ValijaValidar>() {
                                            @Override
                                            public void onResponse(Call<ValijaValidar> call, Response<ValijaValidar> response) {
                                                if (!response.isSuccessful()) {
                                                    Toast.makeText(getApplicationContext(), "Error.", Toast.LENGTH_SHORT).show();
                                                    return;
                                                }
                                                ValijaValidar postsResponse = response.body();

                                                int Estado = postsResponse.Estado();
                                                //int est = Estado.replace(" ", "");
                                                String Anio = postsResponse.Anio();
                                                String Existe = postsResponse.Existe();
                                                String Nro = postsResponse.Nro();
                                                String Suc = postsResponse.Suc();
                                                String ValijaID = postsResponse.ValijaID();

                                                if(Estado == 3615){
                                                    Toast.makeText(ValijaAct.this, "Ingreso correcto de la valija", Toast.LENGTH_SHORT).show();

                                                    Intent i = new Intent(ValijaAct.this, GuiaAct.class);
                                                    Bundle c = new Bundle();
                                                    c.putString("Valijas", Valijas);
                                                    c.putString("Mfto", Mfto);
                                                    c.putString("MftoAnio", MftoAnio);
                                                    c.putString("MftoNro", MftoNro);
                                                    c.putString("Suc", Suc);
                                                    c.putString("PaisDes", PaisDes);
                                                    c.putString("CiuDes", CiuDes);
                                                    c.putInt("Estado", Estado);
                                                    c.putString("ValijaID", ValijaID);
                                                    c.putString("usuario", usuario);
                                                    c.putString("password", password);
                                                    c.putString("codigousuario", CodigoUsuario);
                                                    i.putExtras(c);
                                                    startActivity(i);

                                                    return;
                                                }
                                                else if(Estado == 3616){
                                                    Toast.makeText(ValijaAct.this, "La valija se encuentra cerrada.", Toast.LENGTH_SHORT).show();
                                                    return;
                                                }
                                                else{
                                                    Toast.makeText(ValijaAct.this, "Ha ocurrido un error con su valija: "+Estado, Toast.LENGTH_SHORT).show();
                                                    return;
                                                }
                                            }
                                            @Override
                                            public void onFailure(Call<ValijaValidar> call, Throwable t) {
                                                Toast.makeText(getApplicationContext(), "Fallo al ingresar los datos, compruebe su red.", Toast.LENGTH_SHORT).show();
                                                //Titulo.setText(t.getMessage());
                                                return;
                                            }
                                        });
                                    }
                                    else{
                                        Toast.makeText(ValijaAct.this, "Fallo al asignar valjia", Toast.LENGTH_SHORT).show();
                                    }
                                }
                                @Override
                                public void onFailure(Call<ValijaAdicionar> call, Throwable t) {
                                    Toast.makeText(ValijaAct.this, "Fallo al asignar valjia", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                        @Override
                        public void onFailure(Call<ValijaGDNValidar> call, Throwable t) {
                            Toast.makeText(ValijaAct.this, "Fallo al asignar valjia", Toast.LENGTH_SHORT).show();
                        }
                    });
                    return;
                }
                ValijaValidar postsResponse = response.body();

                int Estado = postsResponse.Estado();
                //int est = Estado.replace(" ", "");
                String Anio = postsResponse.Anio();
                String Existe = postsResponse.Existe();
                String Nro = postsResponse.Nro();
                String Suc = postsResponse.Suc();
                String ValijaID = postsResponse.ValijaID();

                if(Estado == 3615){
                    Toast.makeText(ValijaAct.this, "Ingreso correcto de la valija", Toast.LENGTH_SHORT).show();

                    Intent i = new Intent(ValijaAct.this, GuiaAct.class);
                    Bundle c = new Bundle();
                    c.putString("Valijas", Valijas);
                    c.putString("Mfto", Mfto);
                    c.putString("MftoAnio", MftoAnio);
                    c.putString("MftoNro", MftoNro);
                    c.putString("Suc", Suc);
                    c.putString("PaisDes", PaisDes);
                    c.putString("CiuDes", CiuDes);
                    c.putInt("Estado", Estado);
                    c.putString("ValijaID", ValijaID);
                    c.putString("usuario", usuario);
                    c.putString("password", password);
                    c.putString("codigousuario", CodigoUsuario);
                    i.putExtras(c);
                    startActivity(i);


                }
                else if(Estado == 3616){
                    Toast.makeText(ValijaAct.this, "La valija se encuentra cerrada.", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(ValijaAct.this, "Ha ocurrido un error con su valija: "+Estado, Toast.LENGTH_SHORT).show();
                }

                return;

            }

            @Override
            public void onFailure(Call<ValijaValidar> call, Throwable t) {

                Toast.makeText(getApplicationContext(),"Fallo al ingresar los datos, compruebe su red.",Toast.LENGTH_SHORT).show();

                return;
            }
        });
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

                                Intent i = new Intent(ValijaAct.this, DespacharFinal.class);
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
