package com.dkpen.user.service;

import com.dkpen.eapproval.domain.User;
import com.dkpen.eapproval.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired UserRepository userRepository;

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
