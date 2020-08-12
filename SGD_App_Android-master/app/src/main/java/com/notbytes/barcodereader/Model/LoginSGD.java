package com.notbytes.barcodereader.Model;

public class LoginSGD {

    private String estado;
    private String mensaje;
    private String Login;
    private String Password;

    public LoginSGD(String login, String password) {
        Login = login;
        Password = password;
    }

    public String Estado() {
        return estado;
    }

    public String Mensaje() {
        return mensaje;
    }
}
