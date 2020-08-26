package com.example.ruteandoapp.model;

public class Vars {

    private String estado;
    private String mensaje;
    private String Login;
    private String Password;
    private String usu;
    private String pwd;
    private String CodigoUsuario;
    private int UsuId;
    private String Nombre;
    private String Apep;


    public int UsuId() {
        return UsuId;
    }
    public String Nombre() {
        return Nombre;
    }
    public String Apep() {
        return Apep;
    }

    public Vars(String Usu, String Pwd) {
        usu= Usu;
        pwd= Pwd;
    }

}
