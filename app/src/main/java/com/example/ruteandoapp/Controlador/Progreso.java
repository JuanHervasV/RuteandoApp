package com.example.ruteandoapp.Controlador;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
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
    Dialog mDialog;
    private FrameLayout fondoprogreso;


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
        FrameLayout fondoprogreso = getActivity().findViewById(R.id.progresofondo);

        // Inflate the layout for this fragment
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://200.37.50.53/ApiRuteando/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        jsonPlaceHolderApi = retrofit.create(APIRetrofitInterface.class);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        int ID = preferences.getInt("id", 4);
        int pts = preferences.getInt("points",-1);
        if (pts!=-1) {
            checkpoints();
        }
        cargarpuntos();

        return inflater.inflate(R.layout.fragment_progreso, container, false);
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


        private void cargarpuntos() {

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
                    points = getActivity().findViewById(R.id.pts);
                    points.setText(""+punts+" pts");

                    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putInt("points", punts);
                    editor.commit();

                    FrameLayout fondoprogreso = getActivity().findViewById(R.id.progresofondo);
                    if(punts==1){
                        fondoprogreso.setBackgroundResource(R.drawable.progresoback1);
                    }
                    else if(punts==2){
                        fondoprogreso.setBackgroundResource(R.drawable.progresoback2);
                    }
                    else if(punts==3){
                        fondoprogreso.setBackgroundResource(R.drawable.progresoback3);
                    }
                    else if(punts==4){
                        fondoprogreso.setBackgroundResource(R.drawable.progresoback4);
                    }
                    else if(punts==5){
                        fondoprogreso.setBackgroundResource(R.drawable.progresoback5);
                    }
                    else if(punts==6){
                        fondoprogreso.setBackgroundResource(R.drawable.progresoback6);
                    }
                    else if(punts==7){
                        fondoprogreso.setBackgroundResource(R.drawable.progresoback7);
                    }
                    else if(punts==8){
                        fondoprogreso.setBackgroundResource(R.drawable.progresoback8);
                    }
                    else if(punts==9){
                        fondoprogreso.setBackgroundResource(R.drawable.progresoback9);
                    }
                    else if(punts==10){
                        fondoprogreso.setBackgroundResource(R.drawable.progresoback10);
                    }
                    else if(punts==11){
                        fondoprogreso.setBackgroundResource(R.drawable.progresoback11);
                    }
                    else if(punts==12){
                        fondoprogreso.setBackgroundResource(R.drawable.progresoback12);
                    }
                    else if(punts==13){
                        fondoprogreso.setBackgroundResource(R.drawable.progresoback13);
                    }
                    else if(punts==14){
                        fondoprogreso.setBackgroundResource(R.drawable.progresoback14);
                    }
                    else if(punts==15){
                        fondoprogreso.setBackgroundResource(R.drawable.progresoback15);
                    }
                    else if(punts==16){
                        fondoprogreso.setBackgroundResource(R.drawable.progresoback16);
                    }
                    else if(punts==17){
                        fondoprogreso.setBackgroundResource(R.drawable.progresoback17);
                    }
                    else if(punts==18){
                        fondoprogreso.setBackgroundResource(R.drawable.progresoback18);
                    }
                    else if(punts==19){
                        fondoprogreso.setBackgroundResource(R.drawable.progresoback19);
                    }
                    else if(punts==20){
                        fondoprogreso.setBackgroundResource(R.drawable.progresoback20);
                    }
                    else if(punts==21){
                        fondoprogreso.setBackgroundResource(R.drawable.progresoback21);
                    }
                    else if(punts==22){
                        fondoprogreso.setBackgroundResource(R.drawable.progresoback22);
                    }
                    else if(punts==23){
                        fondoprogreso.setBackgroundResource(R.drawable.progresoback23);
                    }
                    else if(punts==24){
                        fondoprogreso.setBackgroundResource(R.drawable.progresoback24);
                    }
                    else if(punts==25){
                        fondoprogreso.setBackgroundResource(R.drawable.progresoback25);
                    }
                    else if(punts==26){
                        fondoprogreso.setBackgroundResource(R.drawable.progresoback26);
                    }
                    else if(punts==27){
                        fondoprogreso.setBackgroundResource(R.drawable.progresoback27);
                    }
                    else if(punts==28){
                        fondoprogreso.setBackgroundResource(R.drawable.progresoback28);
                    }
                    else if(punts==29){
                        fondoprogreso.setBackgroundResource(R.drawable.progresoback29);
                    }
                    else if(punts==30){
                        fondoprogreso.setBackgroundResource(R.drawable.progresoback30);
                    }
                    else if(punts==0){
                        fondoprogreso.setBackgroundResource(R.drawable.progresoback0);
                    }

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
        }