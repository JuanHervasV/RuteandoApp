package com.example.ruteandoapp.Controlador;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.ruteandoapp.Entidades.Persona;
import com.example.ruteandoapp.Login;
import com.example.ruteandoapp.Maintab;
import com.example.ruteandoapp.R;
import com.example.ruteandoapp.io.APIRetrofitInterface;
import com.example.ruteandoapp.model.UsuarioPts;
import com.example.ruteandoapp.model.UsuarioRanking;
import com.example.ruteandoapp.model.Vars;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONObject;

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
    private APIRetrofitInterface jsonPlaceHolderApi;

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

        //TestApi = findViewById(R.id.TestApi);


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
        rankingAdapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nombre = listaPersonas.get(recyclerView.getChildAdapterPosition(view)).getNombre();
                String puntos = listaPersonas.get(recyclerView.getChildAdapterPosition(view)).getDescrip();
                Toast.makeText(getContext(), "El colaborador "+nombre+" tiene "+puntos,Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ranking, container, false);
        recyclerView = view.findViewById(R.id.recyclerranking);
        listaPersonas = new ArrayList<>();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://200.37.50.53/ApiRuteando/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        jsonPlaceHolderApi = retrofit.create(APIRetrofitInterface.class);
        loadranking();
        //Cargar lista
        //cargarLista();
        //Mostrar datos
        mostrarDatos();
        return view;
    }

    private void loadranking() {
        int iddeusu = 2;
            UsuarioRanking usuarioRanking = new UsuarioRanking(iddeusu);
            Call<List<UsuarioRanking>> call = jsonPlaceHolderApi.usuarioranking(usuarioRanking);
            call.enqueue(new Callback<List<UsuarioRanking>>() {
                @Override
                public void onResponse(Call<List<UsuarioRanking>> call, Response<List<UsuarioRanking>> response) {
                    if (!response.isSuccessful()) {
                        Toast.makeText(getActivity(), "No correcto", Toast.LENGTH_SHORT).show();
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
                        UsuarioRanking abc = rptas.get(i);
                        int puntos = abc.Puntos();
                        String nombre = abc.Usuario();
                        int image = R.drawable.n2;
                        listaPersonas.add(new Persona(""+nombre,""+puntos+" puntos",image));
                        recyclerView.setAdapter(rankingAdapter);
                    }
                    Toast.makeText(getActivity(), "Cargando lista", Toast.LENGTH_SHORT).show();
                    return;
                }
                @Override
                public void onFailure(Call<List<UsuarioRanking>> call, Throwable t) {
                    Toast.makeText(getActivity(), "Fallo al ingresar los datos, compruebe su red.", Toast.LENGTH_SHORT).show();
                    return;
                }
            });
    }
}