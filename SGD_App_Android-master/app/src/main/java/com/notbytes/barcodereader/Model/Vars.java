package com.notbytes.barcodereader.Model;

public class Vars {
    private String Codigo_barra;
    private String Usuario;
    private String Coordenadas;
    private String Estado;
    private String estado;
    private String codigoUsuario;
    private String CodigoUsuario;
    private String mensaje;
    private String Login;
    private String Password;

    public Vars(String login, String password) {
        Login = login;
        Password = password;
    }

    public String login() {
        return mensaje;
    }
    public String password() {
        return estado;
    }
    public String codigoUsuario(){ return CodigoUsuario;}
}
