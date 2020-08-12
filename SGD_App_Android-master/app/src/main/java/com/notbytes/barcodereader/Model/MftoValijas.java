package com.notbytes.barcodereader.Model;

public class MftoValijas{

    private String Mfto_Nro;
    private String Suc_cod;
    private String Mfto_anio;
    private String Valija;
    private int Total;
    private String Cerrado;

    public MftoValijas(String mfto_nro, String suc_cod , String mfto_anio){
        Mfto_Nro = mfto_nro;
        Suc_cod = suc_cod;
        Mfto_anio = mfto_anio;
    }

    public String Valija(){return Valija;}

    public int Total(){return Total;}

    public String Cerrado(){return Cerrado;}
}
