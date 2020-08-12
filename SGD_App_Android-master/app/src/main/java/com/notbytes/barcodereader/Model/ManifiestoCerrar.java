package com.notbytes.barcodereader.Model;

public class ManifiestoCerrar {
    private int Rpta;
    private String Mfto_Nro;
    private String Suc_Cod;
    private String Mfto_Anio;
    private String usuario;
    private String Tipo;
    private String estado;
    private String mensaje;

    public ManifiestoCerrar(String mfto_nro, String suc_cod, String mfto_anio, String Usuario, String tipo){
        Mfto_Nro = mfto_nro;
        Suc_Cod = suc_cod;
        Mfto_Anio = mfto_anio;
        usuario = Usuario;
        Tipo = tipo;
    }
    
    public String Estado(){return estado;}

    public String Mensaje(){return mensaje;}

}
