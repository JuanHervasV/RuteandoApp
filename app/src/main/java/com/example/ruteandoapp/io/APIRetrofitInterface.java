package com.example.ruteandoapp.io;

import com.example.ruteandoapp.model.Avatar;
import com.example.ruteandoapp.model.AvatarSet;
import com.example.ruteandoapp.model.DarPuntos;
import com.example.ruteandoapp.model.DarSoloPuntos;
import com.example.ruteandoapp.model.InsertarToken;
import com.example.ruteandoapp.model.ListarRetos;
import com.example.ruteandoapp.model.RankingUsuario;
import com.example.ruteandoapp.model.UsuarioPts;
import com.example.ruteandoapp.model.UsuarioRanking;
import com.example.ruteandoapp.model.ValidarPuntos;
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

    @POST("ListarRetos")
    Call<List<ListarRetos>> listarRetos(@Body ListarRetos listarRetos);

    @POST("RankingUsuario")
    Call<RankingUsuario> rankingUsuario(@Body RankingUsuario rankingUsuario);

    @POST("DarPuntos")
    Call<DarPuntos> darPuntos(@Body DarPuntos darPuntos);

    @POST("DarSoloPuntos")
    Call<DarSoloPuntos> darSoloPuntos(@Body DarSoloPuntos darSoloPuntos);

    @POST("ValidarPuntos")
    Call<ValidarPuntos> validarPuntos(@Body ValidarPuntos validarPuntos);

    @POST("InsertarToken")
    Call<InsertarToken> insertarToken(@Body InsertarToken insertarToken);

    @POST("Avatar")
    Call<Avatar> avatar(@Body Avatar avatar);

    @POST("AvatarSet")
    Call<AvatarSet> avatarSet(@Body AvatarSet avatarSet);
}
