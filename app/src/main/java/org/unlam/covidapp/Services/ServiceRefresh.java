package org.unlam.covidapp.Services;


import org.unlam.covidapp.dto.SoaRefreshResponse;

import retrofit2.Call;
import retrofit2.http.Header;
import retrofit2.http.PUT;

public interface ServiceRefresh {
    @PUT("api/refresh")
    Call<SoaRefreshResponse> actualizar(@Header("Authorization") String token_refresh);
}
