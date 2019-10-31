package com.pkl.wartajazz.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class EventResponse {

    private boolean error;
    private String message;
    @SerializedName("data")
    private List<Event> event;

    public EventResponse(boolean error, String message, List<Event> event) {
        this.error = error;
        this.message = message;
        this.event = event;
    }

    public boolean isError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public List<Event> getEvent() {
        return event;
    }
}
