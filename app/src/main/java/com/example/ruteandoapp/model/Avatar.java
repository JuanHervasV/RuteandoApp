package com.example.ruteandoapp.model;

public class Avatar {

    private int usuid;
    private String avatar;
    private boolean estado;
    private String mensaje;

    public boolean Rpta() {
        return estado;
    }
    public String Mensaje() {
        return mensaje;
    }

    public Avatar(String Avatar, int Usuid )
    {
        avatar = Avatar;
        usuid= Usuid;
    }
}
