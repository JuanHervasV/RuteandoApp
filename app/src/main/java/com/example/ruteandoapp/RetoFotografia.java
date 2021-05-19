package com.example.ruteandoapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ruteandoapp.Controlador.Retos;
import com.example.ruteandoapp.io.APIRetrofitInterface;
import com.example.ruteandoapp.model.DarPuntos;
import com.example.ruteandoapp.model.ValidarPuntos;
import com.example.ruteandoapp.model.Vars;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.net.URI;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetoFotografia extends AppCompatActivity {

    Button ch, up;
    ImageView img;
    StorageReference mStorageRef;
    public Uri imguri;
    private StorageTask uploadTask;
    private TextView tituloReto, descripReto;
    private APIRetrofitInterface jsonPlaceHolderApi;
    int contadorretos;
    int retocontadorvar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reto_fotografia);

        //TestApi = findViewById(R.id.TestApi);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://200.37.50.53/ApiRuteando/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        jsonPlaceHolderApi = retrofit.create(APIRetrofitInterface.class);

        mStorageRef = FirebaseStorage.getInstance().getReference("Images");

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(RetoFotografia.this);
        int ID = preferences.getInt("id", 2);
        String tituloreto = preferences.getString("nombrereto", "NombreReto");
        String descripreto = preferences.getString("descripreto", "DescripReto");
        String usuario = preferences.getString("usuario", "usuario");
        String apellido = preferences.getString("apellido", "apellido");

        ch = findViewById(R.id.escogerfoto);
        up = findViewById(R.id.subirfoto);
        img = findViewById(R.id.foto);
        tituloReto = findViewById(R.id.retoTitulo);
        descripReto = findViewById(R.id.retoDescrip);

        tituloReto.setText(""+tituloreto);
        descripReto.setText(""+descripreto);

        ch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Filechooser();
            }
        });

        up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(uploadTask != null && uploadTask.isInProgress()){
                    Toast.makeText(RetoFotografia.this, "Envío de imagen en progreso", Toast.LENGTH_SHORT).show();
                }
                Fileuploader();
            }
        });
        onTouch();
    }

    public void onTouch() {
        up = findViewById(R.id.subirfoto);
        up.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_MOVE) {
                    v.setBackgroundResource(R.drawable.rounded_cornermorado);
                }

                if (event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_CANCEL) {
                    v.setBackgroundResource(R.drawable.rounded_cornersscharff);
                    //v.setBackgroundColor(Color.parseColor("@drawable/rounded_corners"));
                }
                return false;
            }
        });
    }

    private String getExtension(Uri uri){
        ContentResolver cr = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(cr.getType(uri));
    }

    private void Fileuploader() {
        LoadingThing loadingThing = new LoadingThing(RetoFotografia.this);
        loadingThing.startLoadingAnimation();
        //StorageReference Ref=mStorageRef.child(System.currentTimeMillis()+"."+getExtension(img));

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(RetoFotografia.this);

        String tituloreto = preferences.getString("nombrereto", "NombreReto");

        String usuario = preferences.getString("usuario", "usuario");
        String apellido = preferences.getString("apellido", "apellido");
        int retocontadorvaroq = preferences.getInt("retocontador",0);
        int retocontadorvarol = retocontadorvaroq;

        // Create a storage reference from our app
        StorageReference storageRef = FirebaseStorage.getInstance().getReference(""+usuario+""+apellido+"Retos");

        // Create a reference to "mountains.jpg"
        StorageReference mountainsRef = storageRef.child(""+usuario+""+apellido+""+tituloreto+""+String.valueOf(retocontadorvarol));

        // Create a reference to 'images/mountains.jpg'
        StorageReference mountainImagesRef = storageRef.child("images/retos.jpg");

        // While the file names are the same, the references point to different files
        mountainsRef.getName().equals(mountainImagesRef.getName());    // true
        mountainsRef.getPath().equals(mountainImagesRef.getPath());    // false
        // Get the data from an ImageView as bytes
        img.setDrawingCacheEnabled(true);
        img.buildDrawingCache();

        if(null!=img.getDrawable()){

            Bitmap bitmap = ((BitmapDrawable) img.getDrawable()).getBitmap();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] data = baos.toByteArray();

            UploadTask uploadTaske = mountainsRef.putBytes(data);

            //uploadTask = Ref.putFile(imguri)
            uploadTaske.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    Bundle bundlereto = getIntent().getExtras();

                    //Extract the data…
                    int RetoId = bundlereto.getInt("retoid");

                    SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
                    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(RetoFotografia.this);
                    int ID = preferences.getInt("id", 2);

                    mountainsRef.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {

                            //Este es el link de la imagen
                            String FotoURL=task.getResult().toString();
                            //Log.i("URL",FotoURL);
                            ValidarPuntos validarPuntos = new ValidarPuntos(RetoId,ID,FotoURL);
                            Call<ValidarPuntos> call = jsonPlaceHolderApi.validarPuntos(validarPuntos);
                            call.enqueue(new Callback<ValidarPuntos>() {
                                @Override
                                public void onResponse(Call<ValidarPuntos> call, Response<ValidarPuntos> response) {

                                    if (!response.isSuccessful()) {
                                        //mJsonTxtView.setText("Codigo:" + response.code());

                                        loadingThing.dismissDialog();
                                        return;
                                    }

                                    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(RetoFotografia.this);
                                    int ID = preferences.getInt("id", 2);
                                    int retocontadorvaro = preferences.getInt("retocontador",0);
                                    if (retocontadorvaro ==0){
                                        retocontadorvaro++;
                                        SharedPreferences preferenceso = PreferenceManager.getDefaultSharedPreferences(RetoFotografia.this);
                                        SharedPreferences.Editor editor = preferenceso.edit();
                                        editor.putInt("retocontador", retocontadorvaro);
                                        editor.commit();
                                        ValidarPuntos postsResponse = response.body();
                                        Toast.makeText(RetoFotografia.this, "Imagen subida con éxito, espera resultados.",Toast.LENGTH_LONG).show();
                                        loadingThing.dismissDialog();
                                        finish();
                                    }
                                    else{

                                        retocontadorvaro++;
                                        SharedPreferences preferenceso = PreferenceManager.getDefaultSharedPreferences(RetoFotografia.this);
                                        SharedPreferences.Editor editor = preferenceso.edit();
                                        editor.putInt("retocontador", retocontadorvaro);
                                        editor.commit();
                                        ValidarPuntos postsResponse = response.body();
                                        Toast.makeText(RetoFotografia.this, "Gracias por participar. Tu reto ha sido enviado con éxito",Toast.LENGTH_LONG).show();
                                        loadingThing.dismissDialog();
                                        finish();

                                    }

                                }
                                @Override
                                public void onFailure(Call<ValidarPuntos> call, Throwable t) {
                                    Toast.makeText(getApplicationContext(), "Fallo al ingresar los datos, compruebe su red.", Toast.LENGTH_SHORT).show();
                                    loadingThing.dismissDialog();
                                    //mJsonTxtView.setText(t.getMessage());
                                    return;
                                }
                            });
                        }
                    })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception exception) {
                                    Toast.makeText(RetoFotografia.this, "Fallo al subir imagen.",Toast.LENGTH_LONG).show();
                                    loadingThing.dismissDialog();
                                    finish();
                                }
                            });
                }
            });

        }
        else{
            Toast.makeText(RetoFotografia.this, "selecciona una imagen", Toast.LENGTH_SHORT).show();
            loadingThing.dismissDialog();
            return;
        }

    }
    final int REQUEST_IMAGE_CAPTURE = 1;
    private void Filechooser() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            img.setImageBitmap(imageBitmap);
        }
    }
}