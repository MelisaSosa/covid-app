package org.unlam.covidapp.Services;

import org.unlam.covidapp.dto.SoaRequest;
import org.unlam.covidapp.dto.SoaResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ServiceRegistro {
    @POST("api/register")
    Call<SoaResponse> register(@Body SoaRequest request);
}