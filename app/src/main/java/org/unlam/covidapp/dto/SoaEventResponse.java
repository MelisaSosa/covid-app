package org.unlam.covidapp.dto;

import com.google.gson.annotations.SerializedName;

public class SoaEventResponse {
    @SerializedName("success")
    private boolean success;
    @SerializedName("env")
    private String env;
    @SerializedName("event")
    private SoaEvent event;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getEnv() {
        return env;
    }

    public void setEnv(String env) {
        this.env = env;
    }

    public SoaEvent getEvent() {
        return event;
    }

    public void setEvent(SoaEvent event) {
        this.event = event;
    }
}
