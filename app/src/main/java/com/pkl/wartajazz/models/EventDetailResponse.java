package com.pkl.wartajazz.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class EventDetailResponse {

    private boolean error;
    private String message;
    @SerializedName("data")
    private Event event;

    public EventDetailResponse(boolean error, String message, Event event) {
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

    public Event getEvent() {
        return event;
    }
}
