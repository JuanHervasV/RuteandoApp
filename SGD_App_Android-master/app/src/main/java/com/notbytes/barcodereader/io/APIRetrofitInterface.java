package com.notbytes.barcodereader.io;

import com.notbytes.barcodereader.Model.AdicionarGuias;
import com.notbytes.barcodereader.Model.AgregarIncidencia;
import com.notbytes.barcodereader.Model.ContadorGuiasMfto;
import com.notbytes.barcodereader.Model.GuiaSuc;
import com.notbytes.barcodereader.Model.LoginSGD;
import com.notbytes.barcodereader.Model.ManifiestoCerrar;
import com.notbytes.barcodereader.Model.ManifiestoContador;
import com.notbytes.barcodereader.Model.MftoValijas;
import com.notbytes.barcodereader.Model.Posts;
import com.notbytes.barcodereader.Model.ValidarGuia;
import com.notbytes.barcodereader.Model.ValidarMfto;
import com.notbytes.barcodereader.Model.ValijaAdicionar;
import com.notbytes.barcodereader.Model.ValijaCerrar;
import com.notbytes.barcodereader.Model.ValijaContador;
import com.notbytes.barcodereader.Model.ValijaGDNValidar;
import com.notbytes.barcodereader.Model.ValijaStatus;
import com.notbytes.barcodereader.Model.ValijaValidar;
import com.notbytes.barcodereader.Model.Vars;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface APIRetrofitInterface {

    @GET("posts")
    Call<List<Posts>> getPosts();

    @GET("Listar")
    Call<List<Vars>> getTiendaGamarra();

    @POST("Insertar")
    Call<Posts> createPost(@Body Posts posts);

    @POST("Login")
    Call<Vars> createPost(@Body Vars vars);

    @POST("ValidarMfto")
    Call<ValidarMfto> createPost(@Body ValidarMfto validarMfto);

    @POST("ValijaAdicionar")
    Call<ValijaAdicionar> createPost(@Body ValijaAdicionar valijaAdicionar);

    @POST("ValidarGuia")
    Call<ValidarGuia> createPost(@Body ValidarGuia validarGuia);

    @POST("ValijaCerrar")
    Call<ValijaCerrar> createPost(@Body ValijaCerrar valijaCerrar);

    @POST("ValijaStatus")
    Call<ValijaStatus> createPost(@Body ValijaStatus valijaStatus);

    @POST("ValijaContador")
    Call<ValijaContador> createPost(@Body ValijaContador valijaContador);

    @POST("MftoCerrar")
    Call<ManifiestoCerrar> createPost(@Body ManifiestoCerrar manifiestoCerrar);

    @POST("MftoContador")
    Call<ManifiestoContador> createPost(@Body ManifiestoContador manifiestoContador);

    @POST("MftoValijas")
    Call<List<MftoValijas>> mftoValijas();

    @POST("MftoValijas")
    Call<List<MftoValijas>> createPost(@Body MftoValijas mftoValijas);

    @POST("ValijaValidar")
    Call<ValijaValidar> createPost(@Body ValijaValidar valijaValidar);

    @POST("AdicionarGuias")
    Call<AdicionarGuias> createPost(@Body AdicionarGuias adicionarGuias);

    @POST("GuiaSuc")
    Call<GuiaSuc> createPost(@Body GuiaSuc guiaSuc);

    @POST("ValijaGDNValidar")
    Call<ValijaGDNValidar> createPost(@Body ValijaGDNValidar valijaGDNValidar);

    @POST("AgregarIncidencia")
    Call<AgregarIncidencia> createPost(@Body AgregarIncidencia agregarIncidencia);

    @POST("LoginSGD")
    Call<LoginSGD> createPost(@Body LoginSGD loginSGD);

    @POST("ContadorGuiasMfto")
    Call<ContadorGuiasMfto> createPost(@Body ContadorGuiasMfto contadorGuiasMfto);
}
