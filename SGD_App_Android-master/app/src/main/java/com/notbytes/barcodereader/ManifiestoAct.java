package com.notbytes.barcodereader;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
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
import com.notbytes.barcodereader.Model.ValidarMfto;
import com.notbytes.barcodereader.io.APIRetrofitInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ManifiestoAct extends AppCompatActivity {
    private APIRetrofitInterface jsonPlaceHolderApi;
    private Button BtnEnviar;
    private EditText Mfto;
    private TextView Titulo;
    private static final int BARCODE_READER_ACTIVITY_REQUEST = 1208;

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manifiesto);

        //Retrofit------------------------------------------------------
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://200.37.50.53/ApiSGD/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        jsonPlaceHolderApi = retrofit.create(APIRetrofitInterface.class);
        onTouch();
    }

    public void onTouch() {
        BtnEnviar = findViewById(R.id.btnEnviar);
        BtnEnviar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_MOVE) {
                    v.setBackgroundResource(R.drawable.rounded_cornerneutral);
                    //v.setBackgroundColor(Color.parseColor("#9C9C9C"));
                }

                if (event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_CANCEL) {
                    v.setBackgroundResource(R.drawable.rounded_corneramarillo);
                    //v.setBackgroundColor(Color.parseColor("#FF7177"));
                }
                return false;
            }
        });
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnEnviar:
                createPost();
                break;
            case  R.id.btnBr:
                FragmentManager supportFragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
                Fragment fragmentById = supportFragmentManager.findFragmentById(R.id.fm_container);
                if (fragmentById != null) {
                    fragmentTransaction.remove(fragmentById);
                }
                fragmentTransaction.commitAllowingStateLoss();
                launchBarCodeActivity();
                break;
        }
    }

    private void launchBarCodeActivity() {
        Intent launchIntent = BarcodeReaderActivity.getLaunchIntent(this, true, false);
        startActivityForResult(launchIntent, BARCODE_READER_ACTIVITY_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Mfto = findViewById(R.id.mfto);

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

            Mfto.setText('0'+barcode.rawValue);
        }
    }

    private void createPost(){
        Mfto = findViewById(R.id.mfto);

        String Mft = Mfto.getText().toString();

        Titulo = findViewById(R.id.txtTitulo);
        //Aqui enviar los datos
        //String resul = mTvResult.getText().toString();
        ValidarMfto validarMfto = new ValidarMfto(Mft);
        Call<ValidarMfto> call = jsonPlaceHolderApi.createPost(validarMfto);
        call.enqueue(new Callback<ValidarMfto>() {
            @Override
            public void onResponse(Call<ValidarMfto> call, Response<ValidarMfto> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(getApplicationContext(),"Manifiesto no encontrado",Toast.LENGTH_LONG).show();
                    return;
                }
                ValidarMfto postsResponse = response.body();

                String MftoAnio = postsResponse.mftoAnio();
                String MftoNro = postsResponse.mftoNro();
                String Suc = postsResponse.suc();
                String PaisDes = postsResponse.paisDes();
                String CiuDes = postsResponse.ciuDes();
                String Estado = postsResponse.estado();
                //

                if (Estado != "") {
                    Toast.makeText(getApplicationContext(),"El manifiesto se encuentra cerrado.",Toast.LENGTH_LONG).show();
                    return;
                }

                else{
                    Toast.makeText(getApplicationContext(),"Manifiesto validado correctamente",Toast.LENGTH_LONG).show();

                    //Llamar datos --------------------------------
                    Bundle b = getIntent().getExtras();
                    String usuario = b.getString("usuario");
                    String password = b.getString("password");
                    String CodigoUsuario = b.getString("codigousuario");

                    //----------------------------------------------

                    Intent i = new Intent(ManifiestoAct.this, ValijaAct.class);
                    Bundle c = new Bundle();
                    String Mftf = Mfto.getText().toString();
                    c.putString("Mfto", Mftf);
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
                    return;
                }

            }

            @Override
            public void onFailure(Call<ValidarMfto> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Fallo al ingresar los datos, compruebe su red.",Toast.LENGTH_SHORT).show();
                //Titulo.setText(t.getMessage());
                return;
            }
        });
    }
}
