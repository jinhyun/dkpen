package com.dkpen.user.domain;

import com.dkpen.eapproval.domain.User;
import com.dkpen.eapproval.dto.UserDTO;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;

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

    public static User getCurrentUser() {
        CurrentUser currentUser = (CurrentUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return currentUser.user;
    }

    public static UserDTO getCurrentUserDTO() {
        CurrentUser currentUser = (CurrentUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        UserDTO userDTO = new UserDTO();
        userDTO.setEmail(currentUser.user.getEmail());
        userDTO.setName(currentUser.user.getName());
        userDTO.setUid(currentUser.user.getUid());

        return userDTO;
    }
}
