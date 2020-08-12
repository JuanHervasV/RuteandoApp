package com.example.ruteandoapp.io;

import com.example.ruteandoapp.model.Vars;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface APIRetrofitInterface {

    @POST("Login")
    Call<Vars> login(@Body Vars vars);

}
