package com.example.ruteandoapp.io;

import com.example.ruteandoapp.model.UsuarioPts;
import com.example.ruteandoapp.model.UsuarioRanking;
import com.example.ruteandoapp.model.Vars;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface APIRetrofitInterface {

    @POST("Login")
    Call<Vars> login(@Body Vars vars);

    @GET("Listar")
    Call<List<Vars>> getTiendaGamarra();

    @POST("UsuarioRanking")
    Call<List<UsuarioRanking>> usuarioranking(@Body UsuarioRanking usuarioRanking);

    @POST("UsuarioPts")
    Call<UsuarioPts> usuariopts(@Body UsuarioPts usuarioPts);

}
