package com.belajar.loginapps.model;

import android.icu.text.AlphabeticIndex;

public class LoginResult {

    private boolean success;
    private String token;
    private Record record;

    public LoginResult() {
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Record getRecord() {
        return record;
    }

    public void setRecord(Record record) {
        this.record = record;
    }
}

