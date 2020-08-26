package com.example.ruteandoapp.Controlador;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.ruteandoapp.Entidades.Persona;
import com.example.ruteandoapp.Entidades.RetosInfo;
import com.example.ruteandoapp.R;
import com.example.ruteandoapp.RetoFotografia;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Retos#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Retos extends Fragment {

    RetosAdapter rankingAdapter;
    private RecyclerView recyclerViewRetos;
    ArrayList<RetosInfo> listaPersonas;

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
        listaPersonas.add(new RetosInfo("Reto fotografía","Consiste en tomar una fotografía con las indicaciones que te daremos.",R.drawable.fotografia));
        listaPersonas.add(new RetosInfo("Reto fotocheck","Deberás de tomarle una fotografía a tu fotocheck para ganar puntos.",R.drawable.fotocheck));
        listaPersonas.add(new RetosInfo("Reto cuidándonos","Consiste en contarnos mediante una fotografía y una breve descripción el cómo cuidas tus herramientas de trabajo.",R.drawable.cuidandoherramientas));

    }
    public void mostrarDatos(){
        recyclerViewRetos.setLayoutManager(new LinearLayoutManager(getContext()));
        rankingAdapter = new RetosAdapter(getContext(), listaPersonas);
        recyclerViewRetos.setAdapter(rankingAdapter);
        rankingAdapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), RetoFotografia.class);
                String nombre = listaPersonas.get(recyclerViewRetos.getChildAdapterPosition(view)).getNombre();
                Toast.makeText(getContext(), "Seleccionó: "+nombre,Toast.LENGTH_LONG).show();
                getActivity().startActivity(intent);
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
        recyclerViewRetos = view.findViewById(R.id.recyclerranking);
        listaPersonas = new ArrayList<>();
        //Cargar lista
        cargarLista();
        //Mostrar datos
        mostrarDatos();
        return view;
    }
}