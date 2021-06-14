package org.unlam.covidapp.dto;

import com.google.gson.annotations.SerializedName;

class SoaEvent {
    @SerializedName("type_events")
    private String typeEvents;
    @SerializedName("dni")
    private Long dni;
    @SerializedName("description")
    private String description;
    @SerializedName("id")
    private Long id;
    @SerializedName("msg")
    private String msg;

    public String getTypeEvents() {
        return typeEvents;
    }

    public void setTypeEvents(String typeEvents) {
        this.typeEvents = typeEvents;
    }

    public Long getDni() {
        return dni;
    }

    public void setDni(Long dni) {
        this.dni = dni;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
