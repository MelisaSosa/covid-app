package org.unlam.covidapp.Services;

import org.unlam.covidapp.dto.SoaEventRequest;
import org.unlam.covidapp.dto.SoaEventResponse;


import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Header;

public interface ServiceEvent {
    @POST("api/event")
    Call<SoaEventResponse> registrarEvento(@Header("Authorization") String token , @Body SoaEventRequest request);
}
