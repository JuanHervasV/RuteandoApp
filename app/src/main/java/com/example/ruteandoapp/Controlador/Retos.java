package com.example.ruteandoapp.Controlador;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.StrictMode;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Toast;


import com.example.ruteandoapp.DesafioCuidando;
import com.example.ruteandoapp.DesafioFotocheck;
import com.example.ruteandoapp.Entidades.Persona;
import com.example.ruteandoapp.Entidades.RetosInfo;
import com.example.ruteandoapp.Login;
import com.example.ruteandoapp.R;
import com.example.ruteandoapp.RetoFotografia;
import com.example.ruteandoapp.io.APIRetrofitInterface;
import com.example.ruteandoapp.model.ListarRetos;
import com.example.ruteandoapp.model.UsuarioPts;
import com.example.ruteandoapp.model.UsuarioRanking;
import com.squareup.picasso.Picasso;


import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.ContentValues.TAG;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Retos#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Retos extends Fragment {

    RetosAdapter rankingAdapter;
    private RecyclerView recyclerViewRetos;
    ArrayList<RetosInfo> listaPersonas;
    private APIRetrofitInterface jsonPlaceHolderApi;
    ImageView image;
    Dialog mDialog;
    private long mLastClickTime = 0;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Retos() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Retos.
     */
    // TODO: Rename and change types and number of parameters
    public static Retos newInstance(String param1, String param2) {
        Retos fragment = new Retos();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public void cargarLista(){
        //listaPersonas.add(new RetosInfo("Reto fotografía","Consiste en tomar una fotografía con las indicaciones que te daremos.",R.drawable.fotografia));
        //listaPersonas.add(new RetosInfo("Reto fotocheck","Deberás de tomarle una fotografía a tu fotocheck para ganar puntos.",R.drawable.fotocheck));
        //listaPersonas.add(new RetosInfo("Reto cuidándonos","Consiste en contarnos mediante una fotografía y una breve descripción el cómo cuidas tus herramientas de trabajo.",R.drawable.cuidandoherramientas));
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

    public void mostrarDatos(){
        recyclerViewRetos.setLayoutManager(new LinearLayoutManager(getContext()));
        rankingAdapter = new RetosAdapter(getContext(), listaPersonas);
        recyclerViewRetos.setAdapter(rankingAdapter);
        rankingAdapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nombre = listaPersonas.get(recyclerViewRetos.getChildAdapterPosition(view)).getNombre();
                String descrip = listaPersonas.get(recyclerViewRetos.getChildAdapterPosition(view)).getDescrip();

                int tamanio = listaPersonas.size();
                RetosInfo abc = listaPersonas.get(1);

                /*xd

                    if (s==1){
                        Intent intent = new Intent();
                        intent.setClass(getActivity(), RetoFotografia.class);
                        Toast.makeText(getContext(), ""+nombre,Toast.LENGTH_LONG).show();
                        getActivity().startActivity(intent);
                    }
                    /*else if (s==1){
                        Intent intent = new Intent();
                        intent.setClass(getActivity(), DesafioFotocheck.class);
                        Toast.makeText(getContext(), ""+nombre,Toast.LENGTH_LONG).show();
                        getActivity().startActivity(intent);
                    }
                    else if(s==1){

                    }
*/

            }
        });
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
        View view = inflater.inflate(R.layout.fragment_retos, container, false);
        View view2 = inflater.inflate(R.layout.retos_list, container, false);
        recyclerViewRetos = view.findViewById(R.id.recyclerranking);
        image =view2.findViewById(R.id.idImagen);
        image.setBackgroundResource(R.drawable.fotoreto);
        //TestApi = findViewById(R.id.TestApi);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://200.37.50.53/ApiRuteando/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        jsonPlaceHolderApi = retrofit.create(APIRetrofitInterface.class);

        DividerItemDecoration itemDecorator = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        itemDecorator.setDrawable(ContextCompat.getDrawable(getContext(), R.drawable.rounded_cornerceleste));
        recyclerViewRetos.addItemDecoration(itemDecorator);

        listaPersonas = new ArrayList<>();
        mDialog = new Dialog(getActivity());
        mDialog.setContentView(R.layout.popuppunto);

        //mDialog.show();

        //Cargar lista
        //cargarLista();
        cargarRetos();
        //Mostrar datos
        //mostrarDatos();
        pasarActivityRetos();
        //Check points lol
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        int ID = preferences.getInt("id", 4);
        int pts = preferences.getInt("points",-1);
        if (pts!=-1) {
            checkpoints();
        }
        return view;
    }

    public void cargarRetos(){
        int iddeusu = 2;
        ListarRetos listarRetos = new ListarRetos(iddeusu);
        Call<List<ListarRetos>> call = jsonPlaceHolderApi.listarRetos(listarRetos);
        call.enqueue(new Callback<List<ListarRetos>>() {
                         @Override
                         public void onResponse(Call<List<ListarRetos>> call, Response<List<ListarRetos>> response) {
                             if (!response.isSuccessful()) {
                                 Toast.makeText(getActivity(), "No correcto", Toast.LENGTH_SHORT).show();
                                 return;
                             }
                             List<ListarRetos> rptas = response.body();
                             int tamanio = rptas.size();
                             //UsuarioRanking abc = rptas.get(0);
                    /*int puntos = abc.Puntos();
                    String nombre = abc.Usuario();
                    listaPersonas.add(new Persona(""+nombre,""+puntos,R.drawable.n1));
                    recyclerView.setAdapter(rankingAdapter);*/

                             for (int i = 0; i < tamanio; i++) {
                                 int s = i;

                                 ListarRetos abc = rptas.get(i);
                                 String nombre = abc.Reto_Nombre().toLowerCase();
                                 String descrip = abc.Reto_Descripcion().toLowerCase();
                                 int retoid = abc.Reto_id();

                                 String imgsf = abc.Image_URL();
                                 //String imgs = "http://l.yimg.com/a/i/us/we/52/21.gif";
                                 //image.setBackgroundResource(R.drawable.scharff_logo_blanco);

                                 //Lib Picasso
                                 Picasso.get().load(imgsf).placeholder(R.drawable.fotoreto).into(image);
                                 //Lib Glide
                                 //Glide.with(Retos.this).load(imgsf).centerCrop().placeholder(R.drawable.fotoreto).into(image);
                                 //TestJHIMage

                                 //

                                 Drawable myDrawable = image.getDrawable();

                                 listaPersonas.add(new RetosInfo("" + nombre, "" + descrip + "", myDrawable, retoid));
                                 recyclerViewRetos.setAdapter(rankingAdapter);
                                 SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
                                 SharedPreferences.Editor editor = preferences.edit();
                                 editor.putString("nombreReto", nombre);
                                 editor.putString("descripReto", descrip);
                                 editor.commit();
                             }
                //Toast.makeText(getActivity(), "Cargando lista", Toast.LENGTH_SHORT).show();
                return;
            }
            @Override
            public void onFailure(Call<List<ListarRetos>> call, Throwable t) {
                Toast.makeText(getActivity(), "Fallo al ingresar los datos, compruebe su red.", Toast.LENGTH_SHORT).show();
                return;
            }
        });
    }

    public void pasarActivityRetos(){
        int iddeusu = 2;
        recyclerViewRetos.setLayoutManager(new LinearLayoutManager(getContext()));
        rankingAdapter = new RetosAdapter(getContext(), listaPersonas);
        recyclerViewRetos.setAdapter(rankingAdapter);
        rankingAdapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (SystemClock.elapsedRealtime() - mLastClickTime < 2000) {
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();

                String nombrereeto = listaPersonas.get(recyclerViewRetos.getChildAdapterPosition(view)).getNombre();
                String descripreeto = listaPersonas.get(recyclerViewRetos.getChildAdapterPosition(view)).getDescrip();
                int retoidd = listaPersonas.get(recyclerViewRetos.getChildAdapterPosition(view)).getRetoID();

                //int tamanio = listaPersonas.size();
                //RetosInfo abc = listaPersonas.get(1);

                ListarRetos listarRetos = new ListarRetos(iddeusu);
                Call<List<ListarRetos>> call = jsonPlaceHolderApi.listarRetos(listarRetos);
                call.enqueue(new Callback<List<ListarRetos>>() {
                    @Override
                    public void onResponse(Call<List<ListarRetos>> call, Response<List<ListarRetos>> response) {
                        if (!response.isSuccessful()) {
                            return;
                        }
                        List<ListarRetos> rptas = response.body();
                        int tamanio = rptas.size();

                        for (int i = 0; i < tamanio; i++) {

                            ListarRetos abc = rptas.get(i);
                            String nombre = abc.Reto_Nombre();
                            String descrip = abc.Reto_Descripcion();
                            //Tipo del reto - foto - preg - etc - etc
                            String tipo = abc.Reto_Tipo();
                            //
                            int retoid = abc.Reto_id();

                            if (tipo != "1000") {
                                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
                                SharedPreferences.Editor editor = preferences.edit();
                                editor.putString("nombrereto", nombrereeto);
                                editor.putString("descripreto", descripreeto);
                                //editor.putInt("tiporeto", retoidd);
                                editor.commit();

                                Intent ia = new Intent();

                                //Create the bundle
                                Bundle bundlereto = new Bundle();
                                //Add your data from getFactualResults method to bundle
                                bundlereto.putInt("retoid", retoidd);
                                //Add the bundle to the intent
                                ia.putExtras(bundlereto);
                                //startActivity(i);

                                ia.setClass(getActivity(), RetoFotografia.class);
                                //Toast.makeText(getContext(), ""+nombre,Toast.LENGTH_LONG).show();
                                getActivity().startActivity(ia);


                            }
                            return;
                        }

                        return;
                    }
                    @Override
                    public void onFailure(Call<List<ListarRetos>> call, Throwable t) {
                        return;
                    }
                });

            }
        });
    }
}