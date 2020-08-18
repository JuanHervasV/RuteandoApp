package com.example.ruteandoapp.Controlador;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.ruteandoapp.Entidades.Persona;
import com.example.ruteandoapp.R;

import java.lang.reflect.Array;
import java.util.ArrayList;


public class Ranking extends Fragment {

    RankingAdapter rankingAdapter;
    private RecyclerView recyclerView;
    ArrayList<Persona> listaPersonas;

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
        listaPersonas.add(new Persona("Juan","Colaborador de Scharff",R.drawable.retratodemo));
        listaPersonas.add(new Persona("Alberto","Colaborador de Scharff",R.drawable.retratodemo));
        listaPersonas.add(new Persona("Hervas","Colaborador de Scharff",R.drawable.retratodemo));
        listaPersonas.add(new Persona("Valderrama","Colaborador de Scharff",R.drawable.retratodemo));
        listaPersonas.add(new Persona("Extra 1","Colaborador de Scharff",R.drawable.retratodemo));
        listaPersonas.add(new Persona("Extra 2","Colaborador de Scharff",R.drawable.retratodemo));
        listaPersonas.add(new Persona("Extra 3","Colaborador de Scharff",R.drawable.retratodemo));

    }
    public void mostrarDatos(){
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        rankingAdapter = new RankingAdapter(getContext(), listaPersonas);
        recyclerView.setAdapter(rankingAdapter);
        rankingAdapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nombre = listaPersonas.get(recyclerView.getChildAdapterPosition(view)).getNombre();
                Toast.makeText(getContext(), "Selecciono: "+nombre,Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ranking, container, false);
        recyclerView = view.findViewById(R.id.recyclerranking);
        listaPersonas = new ArrayList<>();
        //Cargar lista
        cargarLista();
        //Mostrar datos
        mostrarDatos();
        return view;
    }
}