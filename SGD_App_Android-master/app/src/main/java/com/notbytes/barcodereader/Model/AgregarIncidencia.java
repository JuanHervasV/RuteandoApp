package com.notbytes.barcodereader.Model;

public class AgregarIncidencia {
    private String GuiaDetId;
    private String Valija;
    private String User;
    private String Inccod;
    private String Mftonom;
    private String mensaje;
    private String estado;

    public AgregarIncidencia(String guiadetid, String valija, String user, String inccod, String mftonom){

        GuiaDetId = guiadetid;
        Valija = valija;
        User = user;
        Inccod = inccod;
        Mftonom = mftonom;

    }

    public String Estado(){return estado;}

    public String Mensaje(){return mensaje;}

}
