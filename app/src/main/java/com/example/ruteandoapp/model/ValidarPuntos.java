package com.example.ruteandoapp.model;

public class ValidarPuntos {

    private int retoId;
    private int usu_id;
    private String fotoURL;

    private boolean estado;
    private String mensaje;

    public boolean Rpta() {
        return estado;
    }
    public String Mensaje() {
        return mensaje;
    }

    public ValidarPuntos(int RetoId, int Usu_id, String FotoURL)
    {
        usu_id= Usu_id;
        retoId = RetoId;
        fotoURL = FotoURL;
    }
}
