package com.notbytes.barcodereader.Model;

public class ValidarMfto {

    private String Mfto;
    private String MftoAnio;
    private String MftoNro;
    private String Suc;
    private String PaisDes;
    private String CiuDes;
    private String Estado;

    public ValidarMfto(String mfto) {

        Mfto = mfto;
    }

    public String mftoAnio(){return MftoAnio;}

    public String mftoNro() {return  MftoNro;}

    public String suc(){return Suc;}

    public String paisDes(){return PaisDes;}

    public String ciuDes(){return CiuDes;}

    public String estado() {
        return Estado;
    }


}
