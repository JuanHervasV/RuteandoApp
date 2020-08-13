package com.example.ruteandoapp.Controlador;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ruteandoapp.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Progreso#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Progreso extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    String titulo[], descrip[];
    int images[] = {R.drawable.scharff_logo_s,R.drawable.camaras,R.drawable.extrasf};
    RecyclerView recyclerView;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Progreso() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Progreso.
     */
    // TODO: Rename and change types and number of parameters
    public static Progreso newInstance(String param1, String param2) {
        Progreso fragment = new Progreso();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        recyclerView = recyclerView.findViewById(R.id.retosrecycler);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        titulo = getResources().getStringArray(R.array.retos);
        descrip = getResources().getStringArray(R.array.descripcion_retos);

        RetosAdapterRecycler retosAdapterRecycler = new RetosAdapterRecycler(Progreso.this,titulo,descrip,images);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_progreso, container, false);
    }
}