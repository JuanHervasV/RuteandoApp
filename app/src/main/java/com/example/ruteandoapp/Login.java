package com.example.ruteandoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ruteandoapp.model.Vars;
import com.example.ruteandoapp.io.APIRetrofitInterface;
import com.google.firebase.FirebaseApp;
import com.google.firebase.messaging.FirebaseMessaging;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Login extends AppCompatActivity {

    private APIRetrofitInterface jsonPlaceHolderApi;
    //private TextView TestApi;
    private TextView LoginText;
    private TextView PasswordText;
    private String Usuario;
    private String Pass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        FirebaseApp.initializeApp(this);


        LoginText = findViewById(R.id.editText_login_username);
        PasswordText = findViewById(R.id.editText_login_password);

        //TestApi = findViewById(R.id.TestApi);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://200.37.50.53/ApiCyT/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        jsonPlaceHolderApi = retrofit.create(APIRetrofitInterface.class);

        this.Usuario = LoginText.getText().toString();
        this.Pass = PasswordText.getText().toString();

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            Toast.makeText(getApplicationContext(), "Hay internet", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), "No hay internet", Toast.LENGTH_SHORT).show();
        }
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_login_login:
                createPost();
                break;
        }
    }
    private void createPost(){

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
                    return;
                }
                Vars postsResponse = response.body();

                String Estado = postsResponse.login();
                String Mensaje = postsResponse.password();
                String CodigoUsuario = postsResponse.codigoUsuario();

                Toast.makeText(Login.this, "Autentificación correcta", Toast.LENGTH_SHORT).show();
                String usuario = LoginText.getText().toString();
                String password = PasswordText.getText().toString();
                Intent i = new Intent(Login.this, Maintab.class);
                Bundle c = new Bundle();
                c.putString("usuario", usuario);
                c.putString("password", password);
                c.putString("codigousuario", CodigoUsuario);
                i.putExtras(c);
                startActivity(i);

            }

            @Override
            public void onFailure(Call<Vars> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Fallo al ingresar los datos, compruebe su red.", Toast.LENGTH_SHORT).show();
                //mJsonTxtView.setText(t.getMessage());
                return;
            }
        });
    }
}