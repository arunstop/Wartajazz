package com.pkl.wartajazz.models;

public class User {

    private int id, role;
    private String email, join_date, fullname, phone, thumbnail;

    public User(int user_id, String email, String fullname, String phone, String joni_date, int role, String thumbnail) {
        this.id = user_id;
        this.email = email;
        this.fullname = fullname;
        this.phone = phone;
        this.join_date = joni_date;
        this.role = role;
        this.thumbnail = thumbnail;
    }

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getJoin_date() {
        return join_date;
    }

    public String getFullname() {
        return fullname;
    }

    public String getPhone() {
        return phone;
    }

    public int getRole() {
        return role;
    }

    public String getThumbnail() {
        return thumbnail;
    }
}
