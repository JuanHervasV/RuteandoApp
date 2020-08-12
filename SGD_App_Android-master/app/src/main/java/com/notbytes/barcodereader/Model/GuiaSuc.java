package com.notbytes.barcodereader.Model;

public class GuiaSuc {
    private String IDGuia;
    private String SUC_COD;
    private String MFT_ANO;
    private String MFT_NRO;
    private String GUIA_DET_NRO_REF;
    private String GUIA_DET_ID;
    private String GUIA_DET_CON_CIU;

    public GuiaSuc(String idguia){
        IDGuia = idguia;
    }

    public String suc_code(){return SUC_COD;}
    public String mft_ano(){return MFT_ANO;}
    public String mft_nro(){return MFT_NRO;}
    public String guia_nro_ref(){return GUIA_DET_NRO_REF;}
    public String guia_det_id(){return GUIA_DET_ID;}
    public String guia_det_con_ciu(){return GUIA_DET_CON_CIU;}
}
