package com.notbytes.barcodereader.Model;

public class ValidarGuia {

    private String anio;
    private String nro;
    private String Suc;
    private String Guias;
    private String Estado;
    private String guia;
    private String estado;
    private String Guia;
    private String Codigo;

    //Esto se ingresa como parametros--------------------------------------

    public ValidarGuia(String Anio, String Nro, String suc, String guia) {

        anio = Anio;
        nro = Nro;
        Suc = suc;
        Guia = guia;

    }

    //---------------------------------------------------------------------

    //Esto retorna---------------------------------------------------------

    public String estado(){return Estado;}

    public String Guias() {return guia;}

    public String codigo() {return Codigo;}

    //---------------------------------------------------------------------
}
