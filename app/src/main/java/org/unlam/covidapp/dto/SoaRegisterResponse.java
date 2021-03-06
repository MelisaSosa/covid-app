package org.unlam.covidapp.dto;

import com.google.gson.annotations.SerializedName;

public class SoaRegisterResponse {
    @SerializedName("success")
    private Boolean successs;
    @SerializedName("token")
    private String token;
    @SerializedName("token_refresh")
    private String token_refresh;

    public Boolean getSuccesss() {
        return successs;
    }

    public void setSuccesss(Boolean successs) {
        this.successs = successs;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getToken_refresh() {
        return token_refresh;
    }

    public void setToken_refresh(String token_refresh) {
        this.token_refresh = token_refresh;
    }
}
