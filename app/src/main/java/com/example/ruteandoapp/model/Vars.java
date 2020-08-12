package com.example.ruteandoapp.model;

public class Vars {

    private String estado;
    private String mensaje;
    private String Login;
    private String Password;
    private String login;
    private String password;
    private String CodigoUsuario;


    public String login() {
        return Login;
    }
    public String password() {
        return Password;
    }
    public String codigoUsuario() {
        return CodigoUsuario;
    }

    public Vars(String Login, String Password) {
        login= Login;
        password= Password;
    }

}
