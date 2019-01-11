package com.bimo.wartajazz.models;

public class User {

    private int id;
    private String email, join_date, fullname, phone;

    public User(int user_id, String email, String fullname, String phone, String joni_date) {
        this.id = user_id;
        this.email = email;
        this.fullname = fullname;
        this.phone = phone;
        this.join_date = joni_date;
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
}
