package com.example.ruteandoapp.model;

public class UsuarioPts {
    private int usuid;
    private int Puntos;
    private String Fecha;
    private String Hora;

    public int Puntos() {
        return Puntos;
    }
    public String Fecha() {
        return Fecha;
    }
    public String Hora() {
        return Hora;
    }

    public UsuarioPts(int Usuid) {
        usuid= Usuid;
    }


}
