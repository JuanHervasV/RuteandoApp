package com.example.ruteandoapp.Entidades;

public class Persona {
    private String nombre;
    private String descrip;
    private int imagen;

    public Persona(String nombre, String descrip, int imagen) {
        this.nombre = nombre;
        this.descrip = descrip;
        this.imagen = imagen;
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

    public int getImagen() {
        return imagen;
    }

    public void setImagen(int imagen) {
        this.imagen = imagen;
    }
}
