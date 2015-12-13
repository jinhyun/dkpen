package com.dkpen.eapproval.service;

import com.dkpen.eapproval.domain.User;
import com.dkpen.eapproval.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class MockCreateService {
    @Autowired
    UserRepository userRepository;

    @PostConstruct
    public void initCreateMock() {
        User anna = new User();
        anna.setEmail("anna@nonamed.io");
        anna.setName("안나");

        User elsa = new User();
        elsa.setEmail("elsa@nonamed.io");
        elsa.setName("엘사");

        User olaf = new User();
        olaf.setEmail("olaf@nonamed.io");
        olaf.setName("올라프");

        User kristoff = new User();
        kristoff.setEmail("kristoff@nonamed.io");
        kristoff.setName("크리스토프");

        userRepository.save(anna);
        userRepository.save(elsa);
        userRepository.save(olaf);
        userRepository.save(kristoff);
    }
}
