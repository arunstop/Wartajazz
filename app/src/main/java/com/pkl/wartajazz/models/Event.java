package com.pkl.wartajazz.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Event {
    @SerializedName("event_id")
    String event_id;
    @SerializedName("event_name")
    String event_name;
    @SerializedName("location")
    String location;
    @SerializedName("date_start")
    String date_start;
    @SerializedName("date_end")
    String date_end;
    @SerializedName("date_create")
    String date_create;
    @SerializedName("author")
    String author;
    @SerializedName("htm")
    String htm;
    @SerializedName("poster")
    String poster;
    @SerializedName("details")
    List<EventDetail> details;

    public Event(String event_id, String event_name, String location, String date_start, String date_end, String date_create, String author, String htm, String poster, List<EventDetail> details) {
        this.event_id = event_id;
        this.event_name = event_name;
        this.location = location;
        this.date_start = date_start;
        this.date_end = date_end;
        this.date_create = date_create;
        this.author = author;
        this.htm = htm;
        this.poster = poster;
        this.details = details;
    }

    public String getEvent_id() {
        return event_id;
    }

    public String getEvent_name() {
        return event_name;
    }

    public String getLocation() {
        return location;
    }

    public String getDate_start() {
        return date_start;
    }

    public String getDate_end() {
        return date_end;
    }

    public String getDate_create() {
        return date_create;
    }

    public String getAuthor() {
        return author;
    }

    public String getHtm() {
        return htm;
    }

    public String getPoster() {
        return poster;
    }

    public List<EventDetail> getDetails() {
        return details;
    }
}
