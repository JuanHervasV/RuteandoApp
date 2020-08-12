package com.notbytes.barcodereader.Model;

public class ValijaAdicionar {

    private String Codigo;
    private String Mfto_Anio;
    private String MftoNro;
    private String Sucursal;
    private String Destino;
    private String Reg_Usu;
    private String mensaje;
    private String estado;
    private int ID;

    public String Estado(){return estado;}

    //public String Mensaje() {return  mensaje;}

    public int ValijaID(){return ID;}

    public ValijaAdicionar(String codigo, String mfto_anio, String mftoNro, String sucursal, String destino, String reg_Usu) {
        Codigo = codigo;
        Mfto_Anio = mfto_anio;
        MftoNro = mftoNro;
        Sucursal = sucursal;
        Destino = destino;
        Reg_Usu = reg_Usu;
    }


}
