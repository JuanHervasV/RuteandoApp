package com.example.ruteandoapp.Controlador;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.media.Image;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.preference.PreferenceManager;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ruteandoapp.Entidades.Persona;
import com.example.ruteandoapp.Login;
import com.example.ruteandoapp.Maintab;
import com.example.ruteandoapp.R;
import com.example.ruteandoapp.io.APIRetrofitInterface;
import com.example.ruteandoapp.model.RankingUsuario;
import com.example.ruteandoapp.model.UsuarioPts;
import com.example.ruteandoapp.model.UsuarioRanking;
import com.example.ruteandoapp.model.Vars;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class Ranking extends Fragment {

    RankingAdapter rankingAdapter;
    private RecyclerView recyclerView;
    ArrayList<Persona> listaPersonas;
    Dialog mDialog;
    private APIRetrofitInterface jsonPlaceHolderApi;
    private TextView usuario;
    private TextView puntos;
    private TextView ranking;
    private TextView letras;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public Ranking() {
        // Required empty public constructor
    }

    public static Ranking newInstance(String param1, String param2) {
        Ranking fragment = new Ranking();
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
    public void cargarLista(){

        listaPersonas.add(new Persona("Juan Hervas","44 puntos",R.drawable.n1));
        listaPersonas.add(new Persona("Carla Mirón","41 puntos",R.drawable.n2));
        listaPersonas.add(new Persona("José Villanueva","38 puntos",R.drawable.n3));
        listaPersonas.add(new Persona("Paula Castro","30 puntos",R.drawable.n4));
        listaPersonas.add(new Persona("Julio Pérez","24 puntos",R.drawable.n5));
    }
    public void mostrarDatos(){
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        rankingAdapter = new RankingAdapter(getContext(), listaPersonas);
        recyclerView.setAdapter(rankingAdapter);
        //recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        DividerItemDecoration itemDecorator = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        itemDecorator.setDrawable(ContextCompat.getDrawable(getContext(), R.drawable.rounded_cornerneutral));
        recyclerView.addItemDecoration(itemDecorator);

        rankingAdapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nombre = listaPersonas.get(recyclerView.getChildAdapterPosition(view)).getNombre();
                String puntos = listaPersonas.get(recyclerView.getChildAdapterPosition(view)).getDescrip();
                Toast.makeText(getContext(), ""+nombre+" tiene "+puntos,Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ranking, container, false);
        recyclerView = view.findViewById(R.id.recyclerranking);
        usuario = view.findViewById(R.id.NombreUsuario);
        puntos = view.findViewById(R.id.PuntosUsuario);
        ranking = view.findViewById(R.id.RankingUsuario);
        letras = view.findViewById(R.id.letras);
        listaPersonas = new ArrayList<>();

        letras.setText(Html.fromHtml("<strong>¡ten en cuenta!</strong> acá podrás ver el puntaje acumulado de cada scharffero"));
        Typeface typeface = ResourcesCompat.getFont(getContext(), R.font.carnasthin);
        letras.setTypeface(typeface);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://200.37.50.53/ApiRuteando/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        jsonPlaceHolderApi = retrofit.create(APIRetrofitInterface.class);
        mDialog = new Dialog(getActivity());
        mDialog.setContentView(R.layout.popuppunto);

        loadranking();
        //Cargar lista
        //cargarLista();
        //Mostrar datos
        mostrarDatos();
        //DatoRanking
        RankingUsu();
        //:p
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        int ID = preferences.getInt("id", 4);
        String nombreusu = preferences.getString("usuario", "None");
        int rkng = preferences.getInt("ranking",10);
        int pts = preferences.getInt("points",-1);
        if (pts!=-1) {
            checkpoints();
        }
        usuario.setText(nombreusu);
        puntos.setText(""+pts);
        //ranking.setText(rkng);

        return view;
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

    private void loadranking() {

        int iddeusu = 2;
            UsuarioRanking usuarioRanking = new UsuarioRanking(iddeusu);
            Call<List<UsuarioRanking>> call = jsonPlaceHolderApi.usuarioranking(usuarioRanking);
            call.enqueue(new Callback<List<UsuarioRanking>>() {
                @Override
                public void onResponse(Call<List<UsuarioRanking>> call, Response<List<UsuarioRanking>> response) {
                    if (!response.isSuccessful()) {

                        return;
                    }
                    List<UsuarioRanking> rptas = response.body();
                    int tamanio = rptas.size();
                    //UsuarioRanking abc = rptas.get(0);
                    /*int puntos = abc.Puntos();
                    String nombre = abc.Usuario();
                    listaPersonas.add(new Persona(""+nombre,""+puntos,R.drawable.n1));
                    recyclerView.setAdapter(rankingAdapter);*/

                    for (int i = 0 ; i<tamanio;i++){
                            int s = i;
                        if (s==0){
                            int image = R.drawable.uno;
                            UsuarioRanking abc = rptas.get(i);
                            int puntos = abc.Puntos();
                            String nombre = abc.Usuario();
                            int rank = 1;

                            listaPersonas.add(new Persona(""+nombre,""+puntos,image));
                            recyclerView.setAdapter(rankingAdapter);

                        }
                        else if(s==1){
                            int image = R.drawable.dos;
                            UsuarioRanking abc = rptas.get(i);
                            int puntos = abc.Puntos();
                            String nombre = abc.Usuario();
                            int rank = 2;

                            listaPersonas.add(new Persona(""+nombre,""+puntos,image));
                            recyclerView.setAdapter(rankingAdapter);


                        }
                        else if (s==2){
                            int image = R.drawable.tres;
                            UsuarioRanking abc = rptas.get(i);
                            int puntos = abc.Puntos();
                            String nombre = abc.Usuario();
                            int rank= 3;

                            listaPersonas.add(new Persona(""+nombre,""+puntos,image));
                            recyclerView.setAdapter(rankingAdapter);

                        }
                        else if(s==3){
                            int image = R.drawable.cuatro;
                            UsuarioRanking abc = rptas.get(i);
                            int puntos = abc.Puntos();
                            String nombre = abc.Usuario();
                            int rank = 4;

                            listaPersonas.add(new Persona(""+nombre,""+puntos,image));
                            recyclerView.setAdapter(rankingAdapter);

                        }
                        else if(s==4){
                            int image = R.drawable.img5;
                            UsuarioRanking abc = rptas.get(i);
                            int puntos = abc.Puntos();
                            String nombre = abc.Usuario();
                            int rank = 5;

                            listaPersonas.add(new Persona(""+nombre,""+puntos,image));
                            recyclerView.setAdapter(rankingAdapter);

                        }


                    }
                    //Toast.makeText(getActivity(), "Cargando lista", Toast.LENGTH_SHORT).show();
                    return;
                }
                @Override
                public void onFailure(Call<List<UsuarioRanking>> call, Throwable t) {
                    Toast.makeText(getActivity(), "Fallo al ingresar los datos, compruebe su red.", Toast.LENGTH_SHORT).show();
                    return;
                }
            });
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

                    return;
                }
                RankingUsuario rptas = response.body();
                int usui = rptas.usu_id();
                int orden = rptas.orden();
                ranking.setText(""+orden);
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
}