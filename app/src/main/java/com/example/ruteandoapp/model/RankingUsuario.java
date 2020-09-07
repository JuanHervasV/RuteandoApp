package com.example.ruteandoapp.model;

public class RankingUsuario {

    private int usuid;
    private int Puntos;
    private String Fecha;
    private String Hora;
    private int Orden;
    private int Usu_id;
    private String Rkn_Puntos;

    public int orden() {
        return Orden;
    }
    public int usu_id() {
        return Usu_id;
    }
    public String rkn_Puntos() {
        return Rkn_Puntos;
    }

    public RankingUsuario(int Usuid) {
        usuid= Usuid;
    }

}
