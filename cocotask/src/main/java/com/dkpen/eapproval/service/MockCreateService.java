package com.dkpen.eapproval.service;

import com.dkpen.eapproval.domain.User;
import com.dkpen.eapproval.repository.UserRepository;
import com.dkpen.user.domain.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class MockCreateService {
    @Autowired
    UserRepository userRepository;

    @PostConstruct
    public void initCreateMock() {
        BCryptPasswordEncoder crypt = new BCryptPasswordEncoder();
        String password = crypt.encode("1234");

        User anna = new User();
        anna.setEmail("anna@dkpen.com");
        anna.setName("안나");
        anna.setPasswordHash(password);
        anna.setRole(Role.USER);

        User elsa = new User();
        elsa.setEmail("elsa@dkpen.com");
        elsa.setName("엘사");
        elsa.setPasswordHash(password);
        elsa.setRole(Role.USER);

        User olaf = new User();
        olaf.setEmail("olaf@dkpen.com");
        olaf.setName("올라프");
        olaf.setPasswordHash(password);
        olaf.setRole(Role.USER);

        User kristoff = new User();
        kristoff.setEmail("kristoff@dkpen.com");
        kristoff.setName("크리스토프");
        kristoff.setPasswordHash(password);
        kristoff.setRole(Role.USER);

        userRepository.save(anna);
        userRepository.save(elsa);
        userRepository.save(olaf);
        userRepository.save(kristoff);
    }
}
