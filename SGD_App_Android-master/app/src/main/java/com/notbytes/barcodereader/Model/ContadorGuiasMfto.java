package com.notbytes.barcodereader.Model;

public class ContadorGuiasMfto {

    private String MFTOANIO;
    private String SUCURSAL;
    private String MFTONRO;

    private String Total;
    private String Tot2;

    public ContadorGuiasMfto(String MftoAnio, String Sucursal, String MftoNro){
        MFTOANIO = MftoAnio;
        SUCURSAL = Sucursal;
        MFTONRO = MftoNro;
    }

    public String total(){return Total;}
    public String tot2(){return Tot2;}

}
