package com.pkl.wartajazz.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Arunstop on 18-Feb-19.
 */

public class Obj {

    private String status;
    private Feed feed;
    @SerializedName("items")
    private List<Item> item;

    public Obj(String status, Feed feed, List<Item> item) {
        this.status = status;
        this.feed = feed;
        this.item = item;
    }

    public String getStatus() {
        return status;
    }

    public Feed getFeed() {
        return feed;
    }

    public List<Item> getItem() {
        return item;
    }
}
