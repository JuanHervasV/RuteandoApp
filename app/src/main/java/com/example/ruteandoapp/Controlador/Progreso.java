package com.example.ruteandoapp.Controlador;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

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
import com.example.ruteandoapp.model.UsuarioPts;
import com.example.ruteandoapp.model.UsuarioRanking;
import com.example.ruteandoapp.model.Vars;

import org.w3c.dom.Text;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

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
    private APIRetrofitInterface jsonPlaceHolderApi;
    private TextView points;


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

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://200.37.50.53/ApiRuteando/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        jsonPlaceHolderApi = retrofit.create(APIRetrofitInterface.class);
        cargarpuntos();
        return inflater.inflate(R.layout.fragment_progreso, container, false);
    }


    private void cargarpuntos() {


        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        int ID = sharedPref.getInt("id", 1);

        //Aqui enviar los datos
        //String resul = mTvResult.getText().toString();
        UsuarioPts usuarioPts = new UsuarioPts(2);
        Call<UsuarioPts> call = jsonPlaceHolderApi.usuariopts(usuarioPts);
        call.enqueue(new Callback<UsuarioPts>() {
            @Override
            public void onResponse(Call<UsuarioPts> call, Response<UsuarioPts> response) {
                if (!response.isSuccessful()) {
                    //mJsonTxtView.setText("Codigo:" + response.code());
                    Toast.makeText(getActivity(), "Usuario/Contraseña incorrecta.", Toast.LENGTH_SHORT).show();
                    return;
                }
                UsuarioPts rptas = response.body();
                int punts = rptas.Puntos();
                String Fecha = rptas.Fecha();
                String Hora = rptas.Hora();
                points = getActivity().findViewById(R.id.pts);
                points.setText("" + punts + " pts");
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