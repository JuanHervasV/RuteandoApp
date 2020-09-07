package com.example.ruteandoapp.model;

public class ListarRetos {
    private int param;
    private String NombreReto;
    private int Reto_id;
    private String Reto_Nombre;
    private String Reto_Descripcion;
    private int Reto_Puntos;
    private int Usu_reg;
    private int Activo;
    private String Image_URL;
    private String Reto_Tipo;

    public int Reto_id(){ return Reto_id;}
    public String Reto_Nombre(){return Reto_Nombre;}
    public String Reto_Descripcion(){return Reto_Descripcion;}
    public int Reto_Puntos(){return Reto_Puntos;}
    public String Reto_Tipo(){return Reto_Tipo;}
    public int Usu_reg(){return Usu_reg;}
    public int Activo(){return Activo;}
    public String Image_URL(){return Image_URL;}

    public ListarRetos(int Param) {
        param= Param;
    }
}
