package com.pkl.wartajazz.models;

public class LoginResponse {

    private boolean error;
    private String message;
    private User user;

    public LoginResponse(boolean error, String message, User data) {
        this.error = error;
        this.message = message;
        this.user = data;
    }

    public boolean isError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public User getUser() {
        return user;
    }
}
