package com.example.ruteandoapp.Controlador;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ruteandoapp.Entidades.Persona;
import com.example.ruteandoapp.Entidades.RetosInfo;
import com.example.ruteandoapp.R;
import com.example.ruteandoapp.io.APIRetrofitInterface;
import com.example.ruteandoapp.model.ListarRetos;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetosAdapter extends RecyclerView.Adapter<RetosAdapter.ViewHolder> implements View.OnClickListener{

    LayoutInflater inflater;
    ArrayList<RetosInfo> model;
    ImageView img;
    //Listener
    private View.OnClickListener listener;

    public RetosAdapter(Context context, ArrayList<RetosInfo> model){
        this.inflater = LayoutInflater.from(context);
        this.model = model;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.retos_list, parent, false);
        view.setOnClickListener(this);



        return new ViewHolder(view);
    }

    public void setOnClickListener(View.OnClickListener listener){
        this.listener = listener;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String nombre = model.get(position).getNombre();
        String descrip = model.get(position).getDescrip();
        //int imagen = model.get(position).getImagen();

        holder.nombres.setText(nombre);
        holder.descrip.setText(descrip);
        //holder.imagen.setImageResource(imagen);
    }

    @Override
    public int getItemCount() {

        return model.size();
    }

    @Override
    public void onClick(View view) {
        if(listener != null){
            listener.onClick(view);
        }

    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView nombres, descrip;
        ImageView imagen;
        private APIRetrofitInterface jsonPlaceHolderApi;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            //TestApi = findViewById(R.id.TestApi);
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://200.37.50.53/ApiRuteando/api/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            jsonPlaceHolderApi = retrofit.create(APIRetrofitInterface.class);

            nombres = itemView.findViewById(R.id.idNombre);
            descrip = itemView.findViewById(R.id.idDescrip);
            imagen = itemView.findViewById(R.id.idImagen);
            cargarImagenes();
            //String imgs = "https://i.imgur.com/twfE3bd.jpg";
            //Picasso.get().load(imgs).placeholder(R.drawable.scharff_logo_blanco).into(imagen);

        }

        public void cargarImagenes(){
            int iddeusu = 2;
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
                        String imgs = abc.Image_URL();
                        //Lib Picasso
                        Picasso.get().load(imgs).placeholder(R.drawable.scharff_logo_blanco).into(imagen);
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
    }
}
