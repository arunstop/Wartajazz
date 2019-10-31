package com.pkl.wartajazz.models;

import com.google.gson.annotations.SerializedName;

public class User {


    @SerializedName("user_id")
    String user_id;
    @SerializedName("username")
    String username;
    @SerializedName("email")
    String email;
    @SerializedName("password")
    String password;
    @SerializedName("role_id")
    String role_id;
    @SerializedName("token")
    String token;
    @SerializedName("fullname")
    String fullname;
    @SerializedName("phone_number")
    String phone_number;
    @SerializedName("address")
    String address;
    @SerializedName("join_date")
    String join_date;
    @SerializedName("thumbnail")
    String thumbnail;
    @SerializedName("provider_id")
    String provider_id;


    public User(String user_id,
                String username,
                String email,
                String password,
                String role_id,
                String token,
                String fullname,
                String phone_number,
                String address,
                String join_date,
                String thumbnail,
                String provider_id) {
        this.user_id = user_id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.role_id = role_id;
        this.token = token;
        this.fullname = fullname;
        this.phone_number = phone_number;
        this.address = address;
        this.join_date = join_date;
        this.thumbnail = thumbnail;
        this.provider_id = provider_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getRole_id() {
        return role_id;
    }

    public String getToken() {
        return token;
    }

    public String getFullname() {
        return fullname;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public String getAddress() {
        return address;
    }

    public String getJoin_date() {
        return join_date;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public String getProvider_id() {
        return provider_id;
    }
}
