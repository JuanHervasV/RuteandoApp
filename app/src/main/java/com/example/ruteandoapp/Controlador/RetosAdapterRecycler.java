package com.example.ruteandoapp.Controlador;

import android.content.Context;
import android.view.ContextMenu;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ruteandoapp.Maintab;

public class RetosAdapterRecycler extends RecyclerView.Adapter<RetosAdapterRecycler.MyViewHolder> {

    String data1[], data2[];
    int images[];
    Progreso context;


    public RetosAdapterRecycler(Progreso ct, String[] titulo, String[] descrip, int[] img){
        context = ct;
        data1 = titulo;
        data2 = descrip;
        images = img;

    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
