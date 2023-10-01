package com.example.airlineticketservice.entity;

import jakarta.persistence.*;

@Entity
@IdClass(AuthorityId.class)
@Table(name="authorities")
public class Authority {
    @Id
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "username")
    private User username;

    @Id
    @Column(name = "authority")
    private String authority;

    public Authority() {
    }

    public Authority(User username, String authority) {
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
        return "Authority{" +
                "username=" + username +
                ", authority='" + authority + '\'' +
                '}';
    }
}
