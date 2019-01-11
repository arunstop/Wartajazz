package com.bimo.wartajazz.models;

public class SignupResponse {

    private boolean error;
    private String message;

    public SignupResponse(boolean error, String message) {
        this.error = error;
        this.message = message;
    }

    public boolean isError() {
        return error;
    }

    public String getMsg() {
        return message;
    }
}
