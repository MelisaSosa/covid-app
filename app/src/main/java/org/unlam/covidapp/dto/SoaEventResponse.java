package org.unlam.covidapp.dto;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SoaEventResponse {
    @SerializedName("success")
    private boolean success;
    @SerializedName("env")
    private String env;
    @SerializedName("event")
    private List<SoaEvent> event;
}
