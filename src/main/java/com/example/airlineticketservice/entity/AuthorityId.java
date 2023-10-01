package com.example.airlineticketservice.entity;

import java.io.Serializable;

public class AuthorityId implements Serializable {
    private User username;
    private String authority;

    public AuthorityId() {
    }

    public AuthorityId(User username, String authority) {
        this.username = username;
        this.authority = authority;
    }

    public User getUsername() {
        return username;
    }

    public void setUsername(User username) {
        this.username = username;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    @Override
    public String toString() {
        return "AuthorityId{" +
                "username=" + username +
                ", authority='" + authority + '\'' +
                '}';
    }
}
