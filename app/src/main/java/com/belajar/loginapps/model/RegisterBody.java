package com.belajar.loginapps.model;

import android.widget.RadioGroup;

import java.util.ArrayList;
import java.util.List;

public class RegisterBody {

    String username;
    String password;
    String name;
    String email;
    List<String> roles;
    boolean active;

    public RegisterBody(String username, String password, String name, String email, List<String> roles, RadioGroup active) {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public RegisterBody(String username, String password, String name, String email, List<String> roles, boolean active) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.email = email;
        this.roles = roles;
        this.active = active;
    }
}

