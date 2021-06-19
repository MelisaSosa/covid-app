package org.unlam.covidapp.Services;

import org.unlam.covidapp.dto.SoaRegisterRequest;
import org.unlam.covidapp.dto.SoaRegisterResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ServiceRegistro {
    @POST("api/register")
    Call<SoaRegisterResponse> register(@Body SoaRegisterRequest request);

}