package org.unlam.covidapp.Services;

import org.unlam.covidapp.dto.SoaRequest;
import org.unlam.covidapp.dto.SoaResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ServiceLogin {
    @POST("api/login")
    Call<SoaResponse> login(@Body SoaRequest request);
}