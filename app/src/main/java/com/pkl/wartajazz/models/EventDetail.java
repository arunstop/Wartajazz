package com.pkl.wartajazz.models;

import com.google.gson.annotations.SerializedName;

public class EventDetail {
    @SerializedName("event_id")
    String event_id;
    @SerializedName("show_time")
    String show_time;
    @SerializedName("artist_name")
    String artist_name;
    @SerializedName("country_of_origin")
    String country_of_origin;

    public EventDetail(String event_id, String show_time, String artist_name, String country_of_origin) {
        this.event_id = event_id;
        this.show_time = show_time;
        this.artist_name = artist_name;
        this.country_of_origin = country_of_origin;
    }

    public String getEvent_id() {
        return event_id;
    }

    public String getShow_time() {
        return show_time;
    }

    public String getArtist_name() {
        return artist_name;
    }

    public String getCountry_of_origin() {
        return country_of_origin;
    }
}
