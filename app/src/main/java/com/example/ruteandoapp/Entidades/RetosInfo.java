package com.example.ruteandoapp.Entidades;

import android.graphics.drawable.Drawable;

public class RetosInfo {
    private String nombre;
    private String descrip;
    private Drawable imagen;
    private int retoID;

    public RetosInfo(String nombre, String descrip, Drawable imagen, int retoID) {
        this.nombre = nombre;
        this.descrip = descrip;
        this.imagen = imagen;
        this.retoID = retoID;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescrip() {
        return descrip;
    }

    public void setDescrip(String descrip) {
        this.descrip = descrip;
    }

    public Drawable getImagen() {      return imagen;    }

    public void setImagen(Drawable imagen) {      this.imagen = imagen;   }

    public int getRetoID(){ return retoID;}

    public void setRetoID(int retoID){ this.retoID = retoID;}
}
