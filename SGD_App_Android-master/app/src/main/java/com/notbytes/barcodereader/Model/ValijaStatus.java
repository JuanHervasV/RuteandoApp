package com.notbytes.barcodereader.Model;

public class ValijaStatus {

        private String codigo;
        private String status;
        private String estado;
        private String mensaje;

    public ValijaStatus(String Codigo, String Status){

        codigo = Codigo;

        status = Status;

    }

    public String Estado(){return estado;}

    public String Mensaje(){return mensaje;}

}
