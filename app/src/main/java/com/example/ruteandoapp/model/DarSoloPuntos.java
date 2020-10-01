package com.example.ruteandoapp.model;

public class DarSoloPuntos {

    private int usuid;
    private boolean estado;
    private String mensaje;

    public boolean Rpta() {
        return estado;
    }
    public String Mensaje() {
        return mensaje;
    }

    public DarSoloPuntos(int Usuid)
    {
        usuid= Usuid;
    }
}
