package com.example.ruteandoapp.model;

public class AvatarSet {

    private int usuid;

    private String Usu_Nom;
    private String Usu_Apep;
    private String Usu_Avatar;


    public String Usu_Nom() {
        return Usu_Nom;
    }
    public String Usu_Apep() {
        return Usu_Apep;
    }
    public String Usu_Avatar() {
        return Usu_Avatar;
    }

    public AvatarSet(int Usuid)

    {
        usuid= Usuid;
    }
}
