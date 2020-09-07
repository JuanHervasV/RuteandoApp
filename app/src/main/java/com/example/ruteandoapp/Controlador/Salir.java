package com.example.ruteandoapp.Controlador;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ruteandoapp.ContactarF;
import com.example.ruteandoapp.Login;
import com.example.ruteandoapp.R;
import com.example.ruteandoapp.io.APIRetrofitInterface;
import com.example.ruteandoapp.model.RankingUsuario;
import com.example.ruteandoapp.model.UsuarioPts;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

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
        salir = getActivity().findViewById(R.id.salir);
        contactar = getActivity().findViewById(R.id.contactos);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://200.37.50.53/ApiRuteando/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        jsonPlaceHolderApi = retrofit.create(APIRetrofitInterface.class);


        Button sal = (Button) view.findViewById(R.id.salir);
        Button conta = (Button) view.findViewById(R.id.contactos);
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
        String nombreusu = preferences.getString("usuario", "None");
        String apellidousu = preferences.getString("apellido","None");
        int pts = preferences.getInt("points",-1);
        nombreUsu.setText(""+nombreusu);
        apellidoUsu.setText(""+apellidousu);
        if (pts!=-1) {
            checkpoints();
        }
        PuntosUsu();
        RankingUsu();
        return view;

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