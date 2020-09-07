package com.example.ruteandoapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ruteandoapp.Controlador.Retos;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.net.URI;

public class RetoFotografia extends AppCompatActivity {

    Button ch, up;
    ImageView img;
    StorageReference mStorageRef;
    public Uri imguri;
    private StorageTask uploadTask;
    private TextView tituloReto, descripReto;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reto_fotografia);

        mStorageRef = FirebaseStorage.getInstance().getReference("Images");
        FirebaseMessaging.getInstance().subscribeToTopic("RETOS");

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(RetoFotografia.this);
        int ID = preferences.getInt("id", 2);
        String tituloreto = preferences.getString("nombrereto", "NombreReto");
        String descripreto = preferences.getString("descripreto", "DescripReto");
        String tiporeto = preferences.getString("tiporeto", "TipoReto");

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
    }
    private String getExtension(Uri uri){
        ContentResolver cr = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(cr.getType(uri));
    }

    private void Fileuploader() {
        StorageReference Ref=mStorageRef.child(System.currentTimeMillis()+"."+getExtension(imguri));
        uploadTask = Ref.putFile(imguri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        Toast.makeText(RetoFotografia.this, "Imagen subida con éxito, espera resultados.",Toast.LENGTH_LONG).show();

                        Ref.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                            @Override
                            public void onComplete(@NonNull Task<Uri> task) {
                                //Este es el link de la imagen
                                String profileImageUrl=task.getResult().toString();
                                Log.i("URL",profileImageUrl);
                            }
                        });

                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle unsuccessful uploads
                        // ...
                    }
                });
    }
    private void Filechooser() {
        Intent intent= new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1 && resultCode==RESULT_OK && data!=null && data.getData()!=null)
        {
            imguri=data.getData();
            img.setImageURI(imguri);
        }
    }
}