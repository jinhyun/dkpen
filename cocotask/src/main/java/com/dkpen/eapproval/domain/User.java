package com.dkpen.eapproval.domain;

import com.dkpen.user.domain.Role;

import javax.persistence.*;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_UID")
    private long uid;

    @Column(name = "USER_EMAIL")
    private String email;

    @Column(name = "USER_NAME")
    private String name;

    @Column(name = "USER_PASSWORD")
    private String passwordHash;

    @Column(name = "USER_ROLE")
    private Role role;

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public long getUid() {
        return uid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public Role getRole() {
        return role;
    }
}

