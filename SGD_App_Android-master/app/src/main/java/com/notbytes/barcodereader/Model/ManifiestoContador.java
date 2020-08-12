package com.notbytes.barcodereader.Model;

public class ManifiestoContador {

    private String Mfto_Nro;
    private String Suc_Cod;
    private String Mfto_Anio;
    private String Total;
    private String Tot2;


        public ManifiestoContador(String mfto_nro, String suc_cod, String mfto_anio){
            Mfto_Nro = mfto_nro;
            Suc_Cod = suc_cod;
            Mfto_Anio = mfto_anio;
        }

        public String total(){return Total;}

        public String tot2(){return Tot2;}

}
