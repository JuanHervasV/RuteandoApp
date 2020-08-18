package com.example.ruteandoapp.Controlador;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ruteandoapp.Entidades.Persona;
import com.example.ruteandoapp.R;

import java.util.ArrayList;

public class RankingAdapter extends RecyclerView.Adapter<RankingAdapter.ViewHolder> implements View.OnClickListener{

    LayoutInflater inflater;
    ArrayList<Persona> model;
    //Listener
    private View.OnClickListener listener;

    public RankingAdapter(Context context, ArrayList<Persona> model){
        this.inflater = LayoutInflater.from(context);
        this.model = model;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.ranking_list, parent, false);
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
        int imagen = model.get(position).getImagen();

        holder.nombres.setText(nombre);
        holder.descrip.setText(descrip);
        holder.imagen.setImageResource(imagen);
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

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nombres = itemView.findViewById(R.id.idNombre);
            descrip = itemView.findViewById(R.id.idDescrip);
            imagen = itemView.findViewById(R.id.idImagen);

        }
    }
}
