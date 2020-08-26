package com.example.ruteandoapp.model;

public class UsuarioRanking {
    private int usuid;
    private int Posicion;
    private String Usuario;
    private int UsuId;
    private int Puntos;
    private String Fecha;
    private String Hora;
    private String Actual;

    public int Posicion() {
        return Posicion;
    }
    public int UsuId() { return UsuId;  }
    public String Usuario() {
        return Usuario;
    }
    public int Puntos(){ return Puntos;}
    public String Fecha(){return Fecha ;   }
    public String Hora(){ return Hora;}
    public String Actual(){return Actual;}

    public UsuarioRanking(int Usuid) {
        usuid= Usuid;
    }

}
