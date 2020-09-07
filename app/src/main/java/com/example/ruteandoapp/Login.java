package com.example.ruteandoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ruteandoapp.model.Vars;
import com.example.ruteandoapp.io.APIRetrofitInterface;
import com.google.firebase.FirebaseApp;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Login extends AppCompatActivity{

    private APIRetrofitInterface jsonPlaceHolderApi;
    //private TextView TestApi;
    private TextView LoginText;
    private TextView PasswordText;
    private String Usuario;
    private String Pass;
    private long mLastClickTime = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        FirebaseApp.initializeApp(this);

        LoadingThing loadingThing = new LoadingThing(Login.this);

        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        //Toast.makeText(this, ""+refreshedToken, Toast.LENGTH_SHORT).show();
        String TAG = "Login.this";
        Log.i(TAG,"Mi token es: " + refreshedToken);

        LoginText = findViewById(R.id.editText_login_username);
        PasswordText = findViewById(R.id.editText_login_password);


        //TestApi = findViewById(R.id.TestApi);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://200.37.50.53/ApiRuteando/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        jsonPlaceHolderApi = retrofit.create(APIRetrofitInterface.class);

        this.Usuario = LoginText.getText().toString();
        this.Pass = PasswordText.getText().toString();
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(Login.this);
        int ID = preferences.getInt("id", 2);
        String nombre = preferences.getString("usuario", "nombreusuario");
        String apellido = preferences.getString("apellido", "apellidousuario");
        //Toast.makeText(getApplicationContext(), " "+ID, Toast.LENGTH_SHORT).show();
        if(nombre!="nombreusuario" && ID!=2 && apellido!="apellidousuario"){
            Intent i = new Intent(Login.this, Maintab.class);
            Toast.makeText(Login.this, "Bienvenido "+nombre+" "+apellido, Toast.LENGTH_SHORT).show();
            startActivity(i);
            finish();
        }
        else{

        }

    }

    public void onClick(View v) {
        LoadingThing loadingThing = new LoadingThing(Login.this);

        if (SystemClock.elapsedRealtime() - mLastClickTime < 2000) {
            return;
        }
        mLastClickTime = SystemClock.elapsedRealtime();
        switch (v.getId()) {
            case R.id.button_login_login:
                //loadingThing.startLoadingAnimation();
                createPost();
                //loadingThing.dismissDialog();
                break;
        }
    }
    private void createPost(){

        LoadingThing loadingThing = new LoadingThing(Login.this);
        loadingThing.startLoadingAnimation();
        String usuario = LoginText.getText().toString();
        String password = PasswordText.getText().toString();
        //Aqui enviar los datos
        //String resul = mTvResult.getText().toString();
        Vars vars = new Vars(usuario,password);
        Call<Vars> call = jsonPlaceHolderApi.login(vars);
        call.enqueue(new Callback<Vars>() {
            @Override
            public void onResponse(Call<Vars> call, Response<Vars> response) {

                if (!response.isSuccessful()) {
                    //mJsonTxtView.setText("Codigo:" + response.code());
                    Toast.makeText(getApplicationContext(), "Usuario/Contraseña incorrecta.", Toast.LENGTH_SHORT).show();
                    loadingThing.dismissDialog();

                    return;
                }

                Vars postsResponse = response.body();

                int idusu = postsResponse.UsuId();
                String nombre = postsResponse.Nombre();
                String apellido = postsResponse.Apep();
                Toast.makeText(Login.this, "Bienvenido "+nombre+" "+apellido, Toast.LENGTH_SHORT).show();
                //Guardar Login SharedPreferences
                SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(Login.this);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putInt("id", idusu);
                editor.putString("usuario", nombre);
                editor.putString("apellido", apellido);
                editor.commit();
                loadingThing.dismissDialog();
                Intent i = new Intent(Login.this, Maintab.class);
                startActivity(i);
                finish();

            }
            @Override
            public void onFailure(Call<Vars> call, Throwable t) {

                Toast.makeText(getApplicationContext(), "Fallo al ingresar los datos, compruebe su red.", Toast.LENGTH_SHORT).show();

                loadingThing.dismissDialog();
                //mJsonTxtView.setText(t.getMessage());
                return;
            }
        });
    }
}