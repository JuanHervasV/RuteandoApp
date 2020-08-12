package com.notbytes.barcodereader.Model;

public class ValijaCerrar {
    private String estado;
    private String mensaje;
    private String IDValija;

    public ValijaCerrar(String iDValija){
        IDValija = iDValija;
    }

    public String Estado(){return estado;}

    public String Mensaje(){return mensaje;}

}
