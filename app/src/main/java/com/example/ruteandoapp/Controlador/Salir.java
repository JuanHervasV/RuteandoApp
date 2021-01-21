package com.example.ruteandoapp.Controlador;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ruteandoapp.ContactarF;
import com.example.ruteandoapp.LoadingThing;
import com.example.ruteandoapp.Login;
import com.example.ruteandoapp.R;
import com.example.ruteandoapp.RetoFotografia;
import com.example.ruteandoapp.io.APIRetrofitInterface;
import com.example.ruteandoapp.model.Avatar;
import com.example.ruteandoapp.model.AvatarSet;
import com.example.ruteandoapp.model.RankingUsuario;
import com.example.ruteandoapp.model.UsuarioPts;
import com.example.ruteandoapp.model.ValidarPuntos;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Salir#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Salir extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private APIRetrofitInterface jsonPlaceHolderApi;
    private TextView nombreUsu;
    private TextView apellidoUsu;
    private TextView puntosUsu;
    private TextView rankingUsu;
    public Button salir;
    public Button contactar;
    ImageView avatarimg;
    private Dialog mDialog;
    private long mLastClickTime = 0;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Salir() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Salir.
     */
    // TODO: Rename and change types and number of parameters
    public static Salir newInstance(String param1, String param2) {
        Salir fragment = new Salir();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_salir, container, false);
        nombreUsu = view.findViewById(R.id.NombreUsuarioS);
        apellidoUsu = view.findViewById(R.id.ApellidoUsuarioS);
        puntosUsu = view.findViewById(R.id.PuntosUsuarioS);
        rankingUsu = view.findViewById(R.id.RankingUsuarioS);
        avatarimg = getActivity().findViewById(R.id.avatarimg);
        salir = getActivity().findViewById(R.id.salir);
        contactar = getActivity().findViewById(R.id.contactos);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://200.37.50.53/ApiRuteando/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        jsonPlaceHolderApi = retrofit.create(APIRetrofitInterface.class);

        ImageView imz = (ImageView) view.findViewById(R.id.avatarimg);
        Button sal = (Button) view.findViewById(R.id.salir);
        Button conta = (Button) view.findViewById(R.id.contactos);

        sal.setOnTouchListener(new View.OnTouchListener() {
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

        conta.setOnTouchListener(new View.OnTouchListener() {
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
        //Imagen
        imz.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (SystemClock.elapsedRealtime() - mLastClickTime < 2000) {
                    return;
                }
                Filechooser();

            }
        });
        //



        sal.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (SystemClock.elapsedRealtime() - mLastClickTime < 2000) {
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
                preferences.edit().clear().commit();
                Intent i = new Intent(getActivity(), Login.class);
                startActivity(i);
                getActivity().finish();
            }
        });

        conta.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (SystemClock.elapsedRealtime() - mLastClickTime < 2000) {
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();
                Intent i = new Intent(getActivity(), ContactarF.class);
                startActivity(i);
            }
        });

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        //int ID = preferences.getInt("id", 4);
        String nombreusu = preferences.getString("usuario", "None").toLowerCase();
        String apellidousu = preferences.getString("apellido","None").toLowerCase();
        int pts = preferences.getInt("points",-1);
        nombreUsu.setText(""+nombreusu);
        apellidoUsu.setText(""+apellidousu);
        if (pts!=-1) {
            checkpoints();
        }

        PuntosUsu();
        RankingUsu();
        LoadAvatar();
        return view;

    }

    private void LoadAvatar(){



        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        int ID = preferences.getInt("id", 2);

        AvatarSet avatarSet = new AvatarSet(ID);
        Call<AvatarSet> call = jsonPlaceHolderApi.avatarSet(avatarSet);
        call.enqueue(new Callback<AvatarSet>() {
            @Override
            public void onResponse(Call<AvatarSet> call, Response<AvatarSet> response) {

                if (!response.isSuccessful()) {
                    //mJsonTxtView.setText("Codigo:" + response.code());
                    Toast.makeText(getActivity().getApplicationContext(), "Error.", Toast.LENGTH_SHORT).show();

                    return;
                }
                ImageView miniavatar = getActivity().findViewById(R.id.miniavatar);
                avatarimg = getActivity().findViewById(R.id.avatarimg);
                    AvatarSet postsResponse = response.body();
                    String urlavatar = postsResponse.Usu_Avatar();
                //Lib Picasso
                miniavatar.setBackgroundColor(Color.BLACK);
                Picasso.get().load(urlavatar).placeholder(R.drawable.avatarfina).into(avatarimg);


                    //Toast.makeText(getActivity(), "Imagen subida con éxito",Toast.LENGTH_LONG).show();

                    //getActivity().finish();


            }
            @Override
            public void onFailure(Call<AvatarSet> call, Throwable t) {
                Toast.makeText(getActivity().getApplicationContext(), "Fallo al ingresar los datos, compruebe su red.", Toast.LENGTH_SHORT).show();

                //mJsonTxtView.setText(t.getMessage());
                return;
            }
        });

    }

    final int REQUEST_IMAGE_CAPTURE = 1;
    private void Filechooser() {
        Salir salir = this;
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            salir.startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        avatarimg = getActivity().findViewById(R.id.avatarimg);
        ImageView miniavatar = getActivity().findViewById(R.id.miniavatar);
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            avatarimg.setImageBitmap(imageBitmap);
            miniavatar.setBackgroundColor(Color.BLACK);
            FileUpload();

        }
    }

    private void FileUpload(){
        LoadingThing loadingThing = new LoadingThing(getActivity());
        loadingThing.startLoadingAnimation();
        avatarimg = getActivity().findViewById(R.id.avatarimg);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());

        String usuario = preferences.getString("usuario", "usuario");
        String apellido = preferences.getString("apellido", "apellido");
        int retocontadorvaroq = preferences.getInt("retocontador",0);
        int retocontadorvarol = retocontadorvaroq;

        // Create a storage reference from our app
        StorageReference storageRef = FirebaseStorage.getInstance().getReference(""+usuario+""+apellido+"Avatar");

        // Create a reference to "mountains.jpg"
        StorageReference mountainsRef = storageRef.child(""+usuario+""+apellido+""+""+String.valueOf(retocontadorvarol));

        // Create a reference to 'images/mountains.jpg'
        StorageReference mountainImagesRef = storageRef.child("images/avatar.jpg");

        // While the file names are the same, the references point to different files
        mountainsRef.getName().equals(mountainImagesRef.getName());    // true
        mountainsRef.getPath().equals(mountainImagesRef.getPath());    // false
        // Get the data from an ImageView as bytes
        avatarimg.setDrawingCacheEnabled(true);
        avatarimg.buildDrawingCache();

        if(null!=avatarimg.getDrawable()){

            Bitmap bitmap = ((BitmapDrawable) avatarimg.getDrawable()).getBitmap();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] data = baos.toByteArray();

            UploadTask uploadTaske = mountainsRef.putBytes(data);

            //uploadTask = Ref.putFile(imguri)
            uploadTaske.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    Bundle bundlereto = getActivity().getIntent().getExtras();

                    //Extract the data…
                    //int RetoId = bundlereto.getInt("retoid");

                    SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
                    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
                    int ID = preferences.getInt("id", 2);

                    mountainsRef.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {

                            //Este es el link de la imagen
                            String FotoURL=task.getResult().toString();
                            //Log.i("URL",FotoURL);
                            Avatar avatar = new Avatar(FotoURL,ID);
                            Call<Avatar> call = jsonPlaceHolderApi.avatar(avatar);
                            call.enqueue(new Callback<Avatar>() {
                                @Override
                                public void onResponse(Call<Avatar> call, Response<Avatar> response) {

                                    if (!response.isSuccessful()) {
                                        //mJsonTxtView.setText("Codigo:" + response.code());
                                        Toast.makeText(getActivity().getApplicationContext(), "Usuario/Contraseña incorrecta.", Toast.LENGTH_SHORT).show();
                                        loadingThing.dismissDialog();
                                        return;
                                    }

                                        Avatar postsResponse = response.body();
                                        Toast.makeText(getActivity(), "Imagen subida con éxito",Toast.LENGTH_LONG).show();
                                        loadingThing.dismissDialog();


                                }
                                @Override
                                public void onFailure(Call<Avatar> call, Throwable t) {
                                    Toast.makeText(getActivity().getApplicationContext(), "Fallo al ingresar los datos, compruebe su red.", Toast.LENGTH_SHORT).show();
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
                                    Toast.makeText(getActivity(), "Fallo al subir imagen.",Toast.LENGTH_LONG).show();
                                    loadingThing.dismissDialog();

                                }
                            });
                }
            });

        }
        else{
            Toast.makeText(getActivity(), "selecciona una imagen", Toast.LENGTH_SHORT).show();
            loadingThing.dismissDialog();
            return;
        }

    }


    public void salirclase(){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        preferences.edit().clear().commit();
        Intent i = new Intent(getActivity(), Login.class);
        startActivity(i);
        getActivity().finish();
    }

    public void contactarclase(){
        Intent i = new Intent(getActivity(), ContactarF.class);
        startActivity(i);
        getActivity().finish();
    }

    public void RankingUsu(){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        int ID = preferences.getInt("id", 4);
        RankingUsuario rankingUsuario = new RankingUsuario(ID);
        Call<RankingUsuario> call = jsonPlaceHolderApi.rankingUsuario(rankingUsuario);
        call.enqueue(new Callback<RankingUsuario>() {
            @Override
            public void onResponse(Call<RankingUsuario> call, Response<RankingUsuario> response) {
                if (!response.isSuccessful()) {
                    //mJsonTxtView.setText("Codigo:" + response.code());
                    Toast.makeText(getActivity(), "No se pudieron cargar los puntos", Toast.LENGTH_SHORT).show();
                    return;
                }
                RankingUsuario rptas = response.body();
                int usui = rptas.usu_id();
                int orden = rptas.orden();
                rankingUsu.setText(""+orden);
                //Toast.makeText(getActivity(), "Cargando lista"+punts, Toast.LENGTH_SHORT).show();
                return;
            }
            @Override
            public void onFailure(Call<RankingUsuario> call, Throwable t) {
                Toast.makeText(getActivity(), "Fallo al ingresar los datos, compruebe su red.", Toast.LENGTH_SHORT).show();
                //mJsonTxtView.setText(t.getMessage());
                return;
            }
        });
    }

    private void PuntosUsu() {

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        int ID = preferences.getInt("id", 4);

        //Aqui enviar los datos
        //String resul = mTvResult.getText().toString();
        UsuarioPts usuarioPts = new UsuarioPts(ID);
        Call<UsuarioPts> call = jsonPlaceHolderApi.usuariopts(usuarioPts);
        call.enqueue(new Callback<UsuarioPts>() {
            @Override
            public void onResponse(Call<UsuarioPts> call, Response<UsuarioPts> response) {
                if (!response.isSuccessful()) {
                    //mJsonTxtView.setText("Codigo:" + response.code());
                    Toast.makeText(getActivity(), "No se pudieron cargar los puntos", Toast.LENGTH_SHORT).show();
                    return;
                }
                UsuarioPts rptas = response.body();
                int punts = rptas.Puntos();
                String Fecha = rptas.Fecha();
                String Hora = rptas.Hora();
                puntosUsu.setText(""+punts+" pts");

                //checkpoints();

                //Toast.makeText(getActivity(), "Cargando lista"+punts, Toast.LENGTH_SHORT).show();
                return;
            }
            @Override
            public void onFailure(Call<UsuarioPts> call, Throwable t) {
                Toast.makeText(getActivity(), "Fallo al ingresar los datos, compruebe su red.", Toast.LENGTH_SHORT).show();
                //mJsonTxtView.setText(t.getMessage());
                return;
            }
        });
    }

    public void checkpoints(){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        int ID = preferences.getInt("id", 4);
        int pts = preferences.getInt("points",0);

        UsuarioPts usuarioPts = new UsuarioPts(ID);
        Call<UsuarioPts> call = jsonPlaceHolderApi.usuariopts(usuarioPts);
        call.enqueue(new Callback<UsuarioPts>() {
            @Override
            public void onResponse(Call<UsuarioPts> call, Response<UsuarioPts> response) {
                if (!response.isSuccessful()) {
                    //mJsonTxtView.setText("Codigo:" + response.code());
                    Toast.makeText(getActivity(), "No se pudieron cargar los puntos", Toast.LENGTH_SHORT).show();
                    return;
                }
                UsuarioPts rptas = response.body();
                int punts = rptas.Puntos();
                String date = rptas.Fecha();
                String hrs = rptas.Hora();
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
                SharedPreferences.Editor editor = preferences.edit();
                editor.putInt("points", punts);
                editor.commit();

                if(pts<punts){

                    mDialog = new Dialog(getActivity());
                    mDialog.setContentView(R.layout.popuppunto);
                    mDialog.show();
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mDialog.dismiss();
                        }
                    },4000);

                }
                //Toast.makeText(getActivity(), "Cargando lista"+punts, Toast.LENGTH_SHORT).show();
                return;
            }
            @Override
            public void onFailure(Call<UsuarioPts> call, Throwable t) {
                Toast.makeText(getActivity(), "Fallo al ingresar los datos, compruebe su red.", Toast.LENGTH_SHORT).show();
                //mJsonTxtView.setText(t.getMessage());
                return;
            }
        });
    }

}