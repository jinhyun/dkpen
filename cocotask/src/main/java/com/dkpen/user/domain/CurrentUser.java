package com.dkpen.user.domain;

import com.dkpen.eapproval.domain.User;
import org.springframework.security.core.authority.AuthorityUtils;

public class CurrentUser extends org.springframework.security.core.userdetails.User {
    private User user;

    public CurrentUser(User user) {
        super(user.getEmail(), user.getPasswordHash(), AuthorityUtils.createAuthorityList(user.getRole().toString()));
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public Long getUid() {
        return user.getUid();
    }

    public Role getRole() {
        return user.getRole();
    }
}
